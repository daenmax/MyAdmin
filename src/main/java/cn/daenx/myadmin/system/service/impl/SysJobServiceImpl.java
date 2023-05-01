package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysJob;
import cn.daenx.myadmin.system.mapper.SysJobMapper;
import cn.daenx.myadmin.system.service.SysJobService;
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService{

}
