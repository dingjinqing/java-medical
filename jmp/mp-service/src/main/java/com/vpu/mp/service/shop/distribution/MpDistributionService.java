package com.vpu.mp.service.shop.distribution;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.DistributorApplyRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;

/**
 * mp分销模块service
 * @param param
 */
@Service
public class MpDistributionService extends ShopBaseService{
	/**
	 * 用户申请成为分销员
	 * @param param
	 * @return
	 */
	public int distributorApply(DistributorApplyParam param) {
		System.out.println(param);
		DistributorApplyRecord record = new DistributorApplyRecord();
		assign(param, record);
		
		//TODO :分组
		//默认分组：有上级的属于上级分组，无上级的取后台配置默认分组
				
		return db().executeInsert(record);
		
	}
}
