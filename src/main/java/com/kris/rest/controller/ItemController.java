package com.kris.rest.controller;

import com.kris.rest.pojo.TaotaoResult;
import com.kris.rest.pojo.TbItem;
import com.kris.rest.pojo.TbItemDesc;
import com.kris.rest.service.ItemService;
import com.kris.rest.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kris
 * @create 2016-12-12 15:47
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/base/{itemId}")
    @ResponseBody
    private TaotaoResult getItemById(@PathVariable Long itemId) {
        try {
            TbItem item = itemService.getItemById(itemId);
            return TaotaoResult.ok(item);

        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDescById(@PathVariable Long itemId) {
        try {
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            return TaotaoResult.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
