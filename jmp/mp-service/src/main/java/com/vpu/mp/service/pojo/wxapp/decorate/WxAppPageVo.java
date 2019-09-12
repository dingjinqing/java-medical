package com.vpu.mp.service.pojo.wxapp.decorate;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.wxapp.config.ShareConfig;
import com.vpu.mp.service.pojo.wxapp.coupon.SendCoupon;
import com.vpu.mp.service.pojo.wxapp.coupon.ShopCollectInfo;

import lombok.Data;

@Data
public class WxAppPageVo {
	@JsonProperty(value = "page_info")
	Map<String,Object> pageInfo;
	
	@JsonProperty(value = "page_id")
	Integer pageId;
	
	@JsonProperty(value = "page_name")
	String pageName;
	
	@JsonProperty(value = "page_type")
	Byte pageType;
	
	@JsonProperty(value = "is_first_page")
	Byte isFirstPage;
	
	@JsonProperty(value = "scene")
	Integer sceneId;
	
	@JsonProperty(value = "send_coupon")
	SendCoupon sendCoupon = new SendCoupon();
	
	@JsonProperty(value = "is_pictorial_show")
	Byte isPictorialShow = 0;
	
	@JsonProperty(value = "shop_collect")
	ShopCollectInfo collectInfo = new ShopCollectInfo();
	
	@JsonProperty(value = "share_info")
	ShareConfig shareInfo = new ShareConfig();
}
