package com.vpu.mp.service.pojo.shop.store.service.order;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月18日
 */
@Data
public class ServiceOrderPageListQueryVo {
	private PageResult<ServiceOrderListQueryVo> pageList;
	private ServiceOrderCountingDataVo countingData;
    private String technicianTitle;
}
