package cn.daenx.myadmin.quartz.handle;

import cn.daenx.myadmin.quartz.utils.AbstractQuartzJob;
import cn.daenx.myadmin.quartz.utils.JobInvokeUtil;
import cn.daenx.myadmin.system.po.SysJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
