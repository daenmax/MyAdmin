package cn.daenx.framework.quartz.handle;

import cn.daenx.framework.common.domain.vo.system.other.SysJobVo;
import cn.daenx.framework.quartz.utils.AbstractQuartzJob;
import cn.daenx.framework.quartz.utils.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJobVo sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
