package com.kris.rest.service.impl;

import com.kris.rest.mapper.TbItemCatMapper;
import com.kris.rest.pojo.CatNode;
import com.kris.rest.pojo.ItemCatResult;
import com.kris.rest.pojo.TbItemCat;
import com.kris.rest.pojo.TbItemCatExample;
import com.kris.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kris
 * @create 2016-12-21 16:56
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper mItemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {
        //调用递归方法查询商品分类列表
        List itemCatList = getItemCatList(0l);
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setData(itemCatList);
        return itemCatResult;
    }

    private List getItemCatList(Long parentId) {
        //根据parentId查询列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = mItemCatMapper.selectByExample(example);
        List resultList = new ArrayList();
        for (TbItemCat tbItemCat : list) {
            //如果是父节点
            if (tbItemCat.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/\"+tbItemCat.getId()+\".html");
                //如果当前节点为第一级节点
                if (tbItemCat.getParentId() == 0) {
                    node.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItems(getItemCatList(tbItemCat.getId()));
                //把node添加到列表
                resultList.add(node);
            } else {
                //如果是叶子节点
                String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                resultList.add(item);
            }
        }
        return resultList;
    }
}
