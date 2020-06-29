package com.vpu.jmd.service.shop.bo;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 导出文件对应的类
 *
 * @author zhaojianqiang
 * @date 2020年6月10日上午9:50:43
 */
@ExcelSheet
@Data
public class ShopListFileVo {
	/** 账号ID */
	@ExcelColumn(columnIndex = 0, columnName = "shop.export.sysId")
	private Integer sysId;

	/** 所属账户 */
	@ExcelColumn(columnIndex = 1, columnName = "shop.export.auserName")
	private String auserName;

	/** 店铺ID */
	@ExcelColumn(columnIndex = 2, columnName = "shop.export.shopId")
	private Integer shopId;

	/** 店铺类型 */
	@ExcelColumn(columnIndex = 3, columnName = "shop.export.shopType")
	private String shopType;

	/** 店铺名称 */
	@ExcelColumn(columnIndex = 4, columnName = "shop.export.shopName")
	private String shopName;

	/** 手机号 */
	@ExcelColumn(columnIndex = 5, columnName = "shop.export.mobile")
	private String mobile;

	/** 创建时间 */
	@ExcelColumn(columnIndex = 6, columnName = "shop.export.created")
	private String created;

	/** 续费到期时间 */
	@ExcelColumn(columnIndex = 7, columnName = "shop.export.expireTime")
	private Timestamp expireTime;

	/** 是否禁用 */
	@ExcelColumn(columnIndex = 8, columnName = "shop.export.isEnableds")
	private String isEnableds;
	@ExcelIgnore
	private Byte isEnabled;

	/** 支付方式 */
	@ExcelColumn(columnIndex = 9, columnName = "shop.export.isSubMerchants")
	private String isSubMerchants;
	@ExcelIgnore
	private Byte isSubMerchant;

	/** 店铺标识 */
	@ExcelColumn(columnIndex = 10, columnName = "shop.export.shopFlags")
	private String shopFlags;
	@ExcelIgnore
	private Byte shopFlag;

	/** 底部logo是否隐藏 */
	@ExcelColumn(columnIndex = 11, columnName = "shop.export.hidBottoms")
	private String hidBottoms;
	@ExcelIgnore
	private Byte hidBottom;

	/** 开户类型 */
	@ExcelColumn(columnIndex = 12, columnName = "shop.export.openAccountTypes")
	private String openAccountTypes;
	@ExcelIgnore
	private Byte openAccountType;

	/** 续费总金额 */
	@ExcelColumn(columnIndex = 13, columnName = "shop.export.renewMoney")
	private BigDecimal renewMoney;

	/** 续费总次数 */
	@ExcelColumn(columnIndex = 14, columnName = "shop.export.renewNum")
	private Integer renewNum;

	/** 首次续费时间 */
	@ExcelColumn(columnIndex = 15, columnName = "shop.export.firstRenewTime")
	private Timestamp firstRenewTime;

	/** 退款总金额 */
	@ExcelColumn(columnIndex = 16, columnName = "shop.export.refundMoney")
	private BigDecimal refundMoney;

	/** 退款总次数 */
	@ExcelColumn(columnIndex = 17, columnName = "shop.export.refundNum")
	private Integer refundNum;

	/** 赠送次数 */
	@ExcelColumn(columnIndex = 18, columnName = "shop.export.sendNum")
	private Integer sendNum;

	/** 试用总次数 */
	@ExcelColumn(columnIndex = 19, columnName = "shop.export.tryNum")
	private Integer tryNum;

	/** 地理位置 */
	@ExcelColumn(columnIndex = 20, columnName = "shop.export.address")
	private String address;

	@ExcelIgnore
	private Integer provinceCode;
	@ExcelIgnore
	private String provinceName;
	@ExcelIgnore
	private Integer cityCode;
	@ExcelIgnore
	private String cityName;
	@ExcelIgnore
	private Integer districtCode;
	@ExcelIgnore
	private String districtName;
}
