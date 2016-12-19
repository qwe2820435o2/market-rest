package com.kris.rest.service.impl;

import com.kris.rest.mapper.TbItemMapper;
import com.kris.rest.pojo.TbItem;
import com.kris.rest.pojo.TbItemExample;
import com.kris.rest.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kris
 * @create 2016-12-12 15:45
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper itemMapper;


    @Override
    public TbItem getItemById(Long itemId) {
        //TbItem item = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        //创建查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        //判断list中是否为空
        TbItem item = null;
        if (list != null && list.size() > 0) {
            item = list.get(0);
        }
        return item;

    }
}
