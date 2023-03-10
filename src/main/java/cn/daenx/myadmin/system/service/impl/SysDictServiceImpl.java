package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictMapper;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.service.SysDictService;
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService{

}
