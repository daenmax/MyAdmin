package cn.daenx.myadmin.quartz.exception;

import cn.daenx.myadmin.quartz.utils.AbstractQuartzJob;
import cn.daenx.myadmin.quartz.utils.JobInvokeUtil;
import cn.daenx.myadmin.system.po.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
