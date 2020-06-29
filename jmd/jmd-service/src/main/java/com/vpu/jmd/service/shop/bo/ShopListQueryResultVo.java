package com.vpu.jmd.service.shop.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.pojo.saas.shop.child.SystemManagerVo;
import com.vpu.mp.service.pojo.saas.shop.child.SystemShopTagVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
	public Integer shopId;
	public String shopName;
	public String shopType;
	/**
	 * 小程序昵称
	 */
	public String nickName;
	public String mobile;
	public Timestamp created;
	/**
	 * 店铺状态
	 */

	public Integer isUse;
	/**
	 * 续费到期时间
	 */
	public Timestamp expireTime;
	/**
	 * 是否禁止
	 */
	public Byte isEnabled;
	/**
	 * 小程序是否授权
	 */
	public Byte isAuthOk;
	/**
	 * 所属账号
	 */
	public String userName;
	/**
	 * 续费总金额
	 */
	public BigDecimal renewMoney;
	/**
	 * 店铺标识
	 */
	public Byte shopFlag;
	/**
	 * 底部导航
	 */
	public Byte hidBottom;

	private String receiveMobile;
	private String shopPhone;
	private String shopNotice;
	private String shopWx;
	private String shopEmail;
	private String shopQq;
	private String memberKey;
	private String tenancyName;
	private Timestamp endTime;
	private String currency;
	private String shopLanguage;
	private String shopExpireStatus;
	@JsonIgnore
	private String versionConfig;
	private List<String> specialInfo;
	private String principalName;

	private String appId;

	/** 失败次数 */
	private Integer failCount = 0;

	private Byte openAccountType;
	private Byte autoSubmitAudit;
	private Byte storeSwitch;
	private Byte ipuhuoSwitch;
	private String accountId;
	private List<SystemManagerVo> accountIds;
	private String afterSaleId;
	private List<SystemManagerVo> afterSaleIds;
	/** 续费次数 */
	private Integer renewNum = 0;

	private List<SystemShopTagVo> shopTags;

	private Integer provinceCode;
	private String provinceName;
	private Integer cityCode;
	private String cityName;
	private Integer districtCode;
	private String districtName;

	private Timestamp funExpireTime;

	private Byte isSubMerchant;
	/** 所属账户*/
	private String auserName;
}
