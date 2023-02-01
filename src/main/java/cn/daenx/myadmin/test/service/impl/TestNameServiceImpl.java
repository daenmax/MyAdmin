package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.test.mapper.TestNameMapper;
import cn.daenx.myadmin.test.po.TestName;
import cn.daenx.myadmin.test.service.TestNameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TestNameServiceImpl extends ServiceImpl<TestNameMapper, TestName> implements TestNameService{

}
