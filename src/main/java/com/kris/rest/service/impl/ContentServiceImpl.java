package com.kris.rest.service.impl;

import com.kris.rest.mapper.TbContentMapper;
import com.kris.rest.pojo.TbContent;
import com.kris.rest.pojo.TbContentExample;
import com.kris.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kris
 * @create 2016-12-27 17:55
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper mContentMapper;

    @Override
    public List<TbContent> getContentList(Long cid) {
        //根据cid查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = mContentMapper.selectByExampleWithBLOBs(example);

        return list;
    }
}
