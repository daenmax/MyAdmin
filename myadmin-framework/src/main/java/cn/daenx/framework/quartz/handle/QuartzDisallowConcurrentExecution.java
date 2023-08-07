package cn.daenx.framework.quartz.handle;

import cn.daenx.framework.common.vo.other.SysJobVo;
import cn.daenx.framework.quartz.utils.AbstractQuartzJob;
import cn.daenx.framework.quartz.utils.JobInvokeUtil;
import org.quartz.JobExecutionContext;
import org.quartz.DisallowConcurrentExecution;

/**
 * 定时任务处理（禁止并发执行）
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJobVo sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
