package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.test.mapper.TbUserMapper;
import cn.daenx.myadmin.test.po.TbUser;
import cn.daenx.myadmin.test.service.TbUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
@DS("slave")
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService{

}
