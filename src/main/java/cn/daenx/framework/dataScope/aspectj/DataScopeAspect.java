package cn.daenx.framework.dataScope.aspectj;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.vo.system.other.SysRoleVo;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.common.vo.DataScopeParam;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

/**
 * 数据权限拦截器
 */
@Aspect
@Component
//@Intercepts注解用于拦截StatementHandler对象的预编译方法。类似的还有：拦截Executor对象的查找方法，自行百度
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class DataScopeAspect implements DataPermissionHandler {

    //线程局部变量
    //ThreadLocal的作用主要是做数据隔离，填充的数据只属于当前线程
    //用于实现以下功能：虽然每次执行SQL都会进入该拦截器，但是拦截器里可以判断mapper方法上是否有@DataScope注解，如果没有，那就直接放行，不进行修改SQL
    ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(cn.daenx.framework.dataScope.annotation.DataScope)")
    public void dataScope() {
    }

    @After("dataScope()")
    public void clearThreadLocal() {
        threadLocal.remove();
    }

    @Before("dataScope()")
    public void dataScopeBefore(JoinPoint joinPoint) {
        DataScope ds = getAnnotation(joinPoint);
        DataScopeParam dataScopeParam = new DataScopeParam();
        if (ds == null || StringUtils.isBlank(ds.alias())) {
            threadLocal.set(null);
            return;
        }
        dataScopeParam.setAlias(ds.alias());
        dataScopeParam.setField(ds.field());
        threadLocal.set(dataScopeParam);
    }

    private DataScope getAnnotation(JoinPoint joinPoint) {
        org.aspectj.lang.Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DataScope annotation = method.getAnnotation(DataScope.class);
        return annotation;
    }

    /**
     * 拦截器，用于修改SQL，加入数据权限where条件
     *
     * @param where             注意，这个where只能获取到你sql最外层的where
     * @param mappedStatementId
     * @return
     */
    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        //判断mapper方法上是否有注解，如果没有，直接放行，不修改SQL
        DataScopeParam dataScopeParam = threadLocal.get();
        if (dataScopeParam == null) {
            return where;
        }
        //判断是否是超级管理员，如果是的话，直接放行，不修改SQL
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser == null) {
            return where;
        }
        if (loginUser.getIsAdmin()) {
            return where;
        }
        //生成权限SQL
        String sql = makeScopeSql(dataScopeParam, loginUser);
        if (ObjectUtil.isEmpty(sql)) {
            return where;
        }
        if (where == null) {
            return new Column(sql);
        }
        AndExpression andExpression = new AndExpression(where, new Column(sql));
        return andExpression;
    }

    /**
     * 生成权限SQL
     *
     * @param dataScopeParam
     * @param loginUser
     * @return
     */
    private String makeScopeSql(DataScopeParam dataScopeParam, SysLoginUserVo loginUser) {
        String alias = dataScopeParam.getAlias();
        String field = dataScopeParam.getField();
        List<SysRoleVo> roleList = loginUser.getRoles();
        String userId = loginUser.getId();
        String deptId = loginUser.getDeptId();
        StringBuilder sql = new StringBuilder();
        String prex = alias + "." + field;
        Boolean init = false;
        for (SysRoleVo sysRole : roleList) {
            //只有状态正常的角色，才可以使用
            if (sysRole.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
                if (init) {
                    sql.append(" or ");
                }
                sql.append(prex);
                //数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限
                String dataScope = sysRole.getDataScope();
                //下面之所以多拼接了一层 select x1.id from () x1，是为了解决当更新 sys_user 和 sys_dept 表时，会报'You can’t specify target table ‘xxx’ for update in FROM clause'
                //意思是：不能在同一语句中，先select出同一表中的某些值，再update这个表，即不能依据某字段值做判断再来更新某字段的值。
                //解决方案就是，再套一层娃，相当于先把where查询结果放到了临时表x1里
                if (SystemConstant.DATA_SCOPE_SELF.equals(dataScope)) {
                    //本人数据
                    sql.append(" = ")
                            .append("'").append(userId).append("'");
                } else if (SystemConstant.DATA_SCOPE_DEPT.equals(dataScope)) {
                    //本部门数据
                    sql.append(" in ").append("(select x1.id from ")
                            .append("(select sys_user.id from sys_user join sys_dept on sys_user.dept_id = sys_dept.id where sys_dept.id ='")
                            .append(deptId).append("')").append(" x1)");
                } else if (SystemConstant.DATA_SCOPE_DEPT_DOWN.equals(dataScope)) {
                    //本部门及以下数据
                    sql.append(" in ").append("(select x1.id from ")
                            .append("(select sys_user.id from sys_user join sys_dept on sys_user.dept_id = sys_dept.id where sys_dept.id in ");
                    sql.append("(SELECT sdp.dept_id  FROM sys_dept_parent sdp  WHERE sdp.parent_id = '").append(deptId).append("')");
                    sql.append(")").append(" x1)");
                } else if (SystemConstant.DATA_SCOPE_ALL.equals(dataScope)) {
                    //全部数据
                    return new StringBuilder().toString();
                } else if (SystemConstant.DATA_SCOPE_CUSTOM.equals(dataScope)) {
                    //自定义权限
                    sql.append("='").append(userId).append("'").append(" or " + prex).append(" in ").append("(select x1.id from ")
                            .append("(select sys_user.id from sys_user join sys_dept on sys_user.dept_id = sys_dept.id where sys_dept.id in")
                            .append("( select sys_role_dept.dept_id from sys_role_dept where sys_role_dept.role_id='").append(sysRole.getId())
                            .append("'))").append(" x1)");
                }
                init = true;
            }

        }
        return sql.toString();
    }
}
