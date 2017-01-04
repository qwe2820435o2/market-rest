package com.kris.rest.service.impl;

import com.kris.rest.component.JedisClient;
import com.kris.rest.mapper.TbItemDescMapper;
import com.kris.rest.mapper.TbItemMapper;
import com.kris.rest.pojo.TbItem;
import com.kris.rest.pojo.TbItemDesc;
import com.kris.rest.pojo.TbItemExample;
import com.kris.rest.service.ItemService;
import com.kris.rest.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kris
 * @create 2016-12-12 15:45
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper mItemDescMapper;
    @Autowired
    private JedisClient mJedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;

    @Override
    public TbItem getItemById(Long itemId) {
        //查询缓存，如果有缓存，直接返回
        try {
            String json = mJedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId);
            //判断数据是否存在
            if (StringUtils.isNoneBlank(json)) {
                //把json数据转换成java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品id查询商品基本信息
        //两种方式都可以
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

        //向redis中添加缓存
        //添加缓存原则是不能影响正常的业务逻辑
        try {
            //向redis中添加缓存
            mJedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId, JsonUtils.objectToJson(item));
            //设置key的过期时间
            mJedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;

    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        //查询缓存
        //查询缓存，如果有缓存，直接返回
        try {
            String json = mJedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY);
            //判断数据是否存在
            if (StringUtils.isNoneBlank(json)) {
                //把json数据转换成java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询商品详情
        TbItemDesc itemDesc = mItemDescMapper.selectByPrimaryKey(itemId);
        //添加缓存
        try {
            //向redis中添加缓存
            mJedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY, JsonUtils.objectToJson(itemDesc));
            //设置key的过期时间
            mJedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY,ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
