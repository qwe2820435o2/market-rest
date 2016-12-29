package com.kris.rest.controller;

import com.kris.rest.pojo.TaotaoResult;
import com.kris.rest.pojo.TbContent;
import com.kris.rest.service.ContentService;
import com.kris.rest.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 获取轮播图内容
 *
 * @author kris
 * @create 2016-12-27 18:00
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService mContentService;

    @RequestMapping("/content/{cid}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long cid) {
        try {
            List<TbContent> list = mContentService.getContentList(cid);
            return TaotaoResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/sync/content/{cid}")
    @ResponseBody
    public TaotaoResult syncContent(@PathVariable Long cid) {
        try {
            TaotaoResult result = mContentService.syncContent(cid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
