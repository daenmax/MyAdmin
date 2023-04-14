package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysConfigMapper;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.service.SysConfigService;
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService{

}
