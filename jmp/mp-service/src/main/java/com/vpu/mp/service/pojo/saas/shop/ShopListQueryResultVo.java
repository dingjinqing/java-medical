package com.vpu.mp.service.pojo.saas.shop;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class ShopListQueryResultVo {
	public Page page;
	public Integer sysId;
	@JsonProperty("shop_id")
	public Integer shopId;
	@JsonProperty("shop_name")
	public String shopName;
	@JsonProperty("shop_type")
	public String shopType;
	/**
	 * 小程序昵称
	 */
	@JsonProperty("nick_name")
	public String nickName;
	@JsonProperty("mobile")
	public String mobile;
	@JsonProperty("created")
	public Timestamp created;
	/**
	 * 店铺状态
	 */
	
	public Integer isUse;
	/**
	 * 续费到期时间
	 */
	@JsonProperty("expire_time")
	public Timestamp expireTime;
	/**
	 * 是否禁止
	 */
	@JsonProperty("is_enabled")
	public Byte isEnabled;
	/**
	 * 小程序是否授权
	 */
	@JsonProperty(value = "is_auth_ok")
	public Byte isAuthOk;
	/**
	 * 所属账号
	 */
	@JsonProperty("user_name")
	public String userName;
	/**
	 * 续费总金额
	 */
	public BigDecimal renewMoney;
	/**
	 * 店铺标识
	 */
	@JsonProperty("shop_flag")
	public Byte shopFlag;
	/**
	 * 底部导航
	 */
	@JsonProperty("hid_bottom")
	public Byte hidBottom;
}
