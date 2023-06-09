package cn.daenx.myadmin.framework.quartz.handle;

import cn.daenx.myadmin.framework.quartz.utils.AbstractQuartzJob;
import cn.daenx.myadmin.framework.quartz.utils.JobInvokeUtil;
import cn.daenx.myadmin.system.domain.po.SysJob;
import org.quartz.JobExecutionContext;
import org.quartz.DisallowConcurrentExecution;

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
