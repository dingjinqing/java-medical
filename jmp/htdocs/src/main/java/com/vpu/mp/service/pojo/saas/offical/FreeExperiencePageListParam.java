package com.vpu.mp.service.pojo.saas.offical;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

@Data
public class FreeExperiencePageListParam {
	static final Page DEFAULTPAGE = new Page();
	public String company;
	public String contact;
	public String startTime;
	public String endTime;
	public Integer provinceId;
	public Integer searchShopId;
	public Page page = DEFAULTPAGE;
	
	/**
	 *  已处理： 1 ； 未处理： 0
	 */
	public Byte isDeal;   
}
