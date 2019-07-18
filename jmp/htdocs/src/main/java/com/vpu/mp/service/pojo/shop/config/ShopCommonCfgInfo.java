package com.vpu.mp.service.pojo.shop.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月2日
 */
@Data
@NoArgsConstructor
public class ShopCommonCfgInfo {
	/**
	 *是否显示前端店铺logo
	 */
	@JsonProperty(value = "show_logo")
	public Byte showLogo;
	
	/**
	 *logo点击跳转链接
	 */
	@JsonProperty(value = "logo_link")
	public String logoLink;
	
	/**
	 *待付款订单取消时间（分钟）
	 */
	@JsonProperty(value = "cancel_time")
	public Integer cancelTime;
	
	/**
	 *是否开启发票功能：0关闭，1开启
	 */
	@JsonProperty(value = "invoice")
	public Byte invoice;
	
	/**
	 *是否强制用户绑定手机号
	 */
	@JsonProperty(value = "bind_mobile")
	public Byte bindMobile;
	
	/**
	 *销量展示开关
	 */
	@JsonProperty(value = "sales_number")
	public Byte salesNumber;
	
	/**
	 *下单填写真实姓名开关
	 */
	@JsonProperty(value = "order_real_name")
	public Byte orderRealName;
	
	/**
	 *下单填写身份证号开关
	 */
	@JsonProperty(value = "order_cid")
	public Byte orderCid;
	
	/**
	 *下单填写收货人真实姓名开关
	 */
	@JsonProperty(value = "consignee_real_name")
	public Byte consigneeRealName;
	
	/**
	 *下单填写收货人身份证号开关
	 */
	@JsonProperty(value = "consignee_cid")
	public Byte consigneeCid;
	
	/**
	 *下单填写自定义信息开关
	 */
	@JsonProperty(value = "custom")
	public Byte custom;
	
	/**
	 *下单填写自定义信息标题
	 */
	@JsonProperty(value = "custom_title")
	public String customTitle;
	
	/**
	 *是否显示划线价开关
	 */
	@JsonProperty(value = "del_market")
	public Byte delMarket;
	
	/**
	 *客服入口开关
	 */
	@JsonProperty(value = "custom_service")
	public Byte customService;
	
	/**
	 * 商品搜索页以及推荐商品列表中会显示购买按钮
	 */
	@JsonProperty(value = "show_cart")
	public ShowCartConfig showCart;
	
	/**
	 *服务条款名称
	 */
	@JsonProperty(value = "service_name")
	public String serviceName;
	
	/**
	 *首次下单是否默认勾选服务条款
	 */
	@JsonProperty(value = "service_choose")
	public Byte serviceChoose;
	
	/**
	 *服务条款开关
	 */
	@JsonProperty(value = "service_terms")
	public Byte serviceTerms;
	
	/**
	 *店铺分享配置
	 */
	@JsonProperty(value = "share_config")
	public ShopShareConfig shareConfig;
	
	/**
	 *店铺风格
	 */
	@JsonProperty(value = "shop_style")
	public ShopStyleConfig shopStyle;
}
