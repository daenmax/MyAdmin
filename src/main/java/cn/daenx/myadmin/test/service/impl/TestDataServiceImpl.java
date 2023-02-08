package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.test.mapper.TestDataMapper;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData> implements TestDataService{

}
