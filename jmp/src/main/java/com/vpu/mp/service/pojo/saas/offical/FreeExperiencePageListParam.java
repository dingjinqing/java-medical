package com.vpu.mp.service.pojo.saas.offical;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

@Data
public class FreeExperiencePageListParam {
	public String company;
	public String contact;
	public String startTime;
	public String endTime;
	public Integer provinceId;
	public Integer searchShopId;
	public Page page;
	
	/**
	 *  已处理： 1 ； 未处理： 0
	 */
	public Byte isDeal;   
}
