package com.kris.rest.service;

import com.kris.rest.pojo.TbContent;

import java.util.List;

/**
 * @author kris
 * @create 2016-12-27 17:53
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);
}
