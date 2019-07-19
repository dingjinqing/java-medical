package com.vpu.mp.service.pojo.shop.decoration;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

/**
 * 
 * @author 常乐
 * 2019年7月13日
 */
@Data
public class XcxLinkListVo {
	public String appid;
	public String shopId;
	public String title;
	public String pathName;
	@URL 
	public String linkPath; 
	public Integer linkAction;
}
