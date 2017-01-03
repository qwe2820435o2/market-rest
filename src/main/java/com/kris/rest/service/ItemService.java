package com.kris.rest.service;

import com.kris.rest.pojo.TbItem;
import com.kris.rest.pojo.TbItemDesc;

/**
 * @author kris
 * @create 2016-12-12 15:44
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);
}
