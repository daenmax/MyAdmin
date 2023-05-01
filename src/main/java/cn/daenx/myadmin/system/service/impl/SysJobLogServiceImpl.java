package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysJobLog;
import cn.daenx.myadmin.system.mapper.SysJobLogMapper;
import cn.daenx.myadmin.system.service.SysJobLogService;
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService{

}
