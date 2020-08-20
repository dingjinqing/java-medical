package com.vpu.mp.service.pojo.shop.store.account;

import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import lombok.Data;

import java.util.List;

/**
 * @author 赵晓东
 * @description
 * @create 2020-08-20 17:23
 **/
@Data
public class StoreAccountAddParam {

    /**
     * 门店id
     */
    private Integer storeId;
    /**
     * 门店分组列表
     */
    private List<StoreGroup> storeGroups;
}
