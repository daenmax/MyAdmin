package cn.daenx.myadmin.common.exception;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.vo.DataScopeParam;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
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

/**
 * 数据权限拦截器
 */
@Aspect
@Component
//@Intercepts注解用于拦截StatementHandler对象的预编译方法。类似的还有：拦截Executor对象的查找方法，自行百度
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class DataScopeInterceptor implements DataPermissionHandler {
    //线程局部变量
    //ThreadLocal的作用主要是做数据隔离，填充的数据只属于当前线程
    //用于实现以下功能：虽然每次执行SQL都会进入该拦截器，但是拦截器里可以判断mapper方法上是否有@DataScope注解，如果没有，那就直接放行，不进行修改SQL
    ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(cn.daenx.myadmin.common.annotation.DataScope)")
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
     * @param where
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
        //判断是否是管理员，如果是的话，直接放行，不修改SQL
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if (loginUser.isAdmin()) {
            return where;
        }
        //生成权限SQL
        String sql = makeScopeSql(dataScopeParam, loginUser);
        if (ObjectUtil.isEmpty(sql)) {
            return where;
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
        //修改SQL，待完成………………
        String sql = "";


        sql = "(" + sql + ")";
        return sql;
    }
}
