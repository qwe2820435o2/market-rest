package com.kris.rest.service.impl;

import com.kris.rest.component.JedisClient;
import com.kris.rest.mapper.TbContentMapper;
import com.kris.rest.pojo.TaotaoResult;
import com.kris.rest.pojo.TbContent;
import com.kris.rest.pojo.TbContentExample;
import com.kris.rest.service.ContentService;
import com.kris.rest.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kris
 * @create 2016-12-27 17:55
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Autowired
    private TbContentMapper mContentMapper;

    @Autowired
    private JedisClient mJedisClient;

    @Override
    public List<TbContent> getContentList(Long cid) {
        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        try {
            //从redis中取缓存数据
            String json = mJedisClient.hget(REDIS_CONTENT_KEY, cid + "");
            if (!StringUtils.isBlank(json)) {
                //把json转为list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据cid查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = mContentMapper.selectByExampleWithBLOBs(example);

        //返回结果之前，向缓存添加数据
        try {
            //为了规范key，可以使用hash
            //定义一个保存内容的key，hash中每个项都是cid
            //value是list，需要把list转换成json数据
            mJedisClient.hset(REDIS_CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public TaotaoResult syncContent(Long cid) {
        mJedisClient.hdel(REDIS_CONTENT_KEY, cid + "");
        return TaotaoResult.ok();
    }
}
