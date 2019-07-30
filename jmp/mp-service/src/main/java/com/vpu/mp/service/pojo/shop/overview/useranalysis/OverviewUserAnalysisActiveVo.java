package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import java.util.Date;

import lombok.Data;

/**
 * 用户活跃
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisActiveVo {

	private Date refDate;
	private Integer loginData;
	private Integer couponData;
	private Integer cartData;
	private Integer orderUserData;
	
	private Integer loginDataTotal;
	private Integer couponDataTotal;
	private Integer cartDataTotal;
	private Integer orderUserDataTotal;
	private Integer userDataTotal;
	
	private Double loginDataRate;
	private Double couponDataRate;
	private Double cartDataRate;
	private Double orderUserDataRate;
	
}
