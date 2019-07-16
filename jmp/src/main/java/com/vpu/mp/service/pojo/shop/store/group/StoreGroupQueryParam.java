package com.vpu.mp.service.pojo.shop.store.group;

import com.vpu.mp.service.foundation.Page;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * 
 * @author: 卢光耀
 * @date: 2019-07-11 09:49
 *
*/
@Data
public class StoreGroupQueryParam extends BasePageParam {

    private String groupName;

    private boolean needAccurateQuery = Boolean.FALSE;

    private Integer groupId;
}
