package com.kris.rest.controller;

import com.kris.rest.pojo.ItemCatResult;
import com.kris.rest.service.ItemCatService;
import com.kris.rest.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.kris.rest.utils.JsonUtils.objectToJson;

/**
 * 获取商品类目
 *
 * @author kris
 * @create 2016-12-22 11:28
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService mItemCatService;

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback) {
        ItemCatResult result = mItemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            //需要把result转换成字符串
            String json = objectToJson(result);
            return  json;
        }
        //如果字符串不为空，需要支持jsonp调用
        //需要把result转换成字符串
        String json=JsonUtils.objectToJson(result);
        return callback+"("+json+");";
    }
}
