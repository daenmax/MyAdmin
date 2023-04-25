package cn.daenx.myadmin.test.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.test.mapper.TestDataTreeMapper;
import cn.daenx.myadmin.test.po.TestDataTree;
import cn.daenx.myadmin.test.service.TestDataTreeService;
@Service
public class TestDataTreeServiceImpl extends ServiceImpl<TestDataTreeMapper, TestDataTree> implements TestDataTreeService{

}
