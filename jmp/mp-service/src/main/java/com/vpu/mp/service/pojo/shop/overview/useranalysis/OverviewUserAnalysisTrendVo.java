package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import java.util.Date;

import lombok.Data;

/**
 * 客户概况及趋势
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisTrendVo {

	private Date refDate;
	private Integer loginData;
	private Integer userData;
	private Integer orderUserData;
	
	private Integer loginDataTotoal;
	private Integer userDataTotoal;
	private Integer orderUserDataTotoal;
	
	private Double loginDataRate;
	private Double userDataRate;
	private Double orderUserDataRate;
	
}
