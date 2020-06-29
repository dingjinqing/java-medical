package com.vpu.jmd.service.shop.bo.audit;

import com.vpu.mp.service.pojo.saas.shop.child.SystemManagerVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月3日上午10:46:24
 */
@Data
public class SystemAuditListVo {
	private Integer id;
	private String appId;
	private Integer shopId;
	private Integer templateId;
	private Integer accountId;
	private Byte source;
	private Timestamp createTime;
	private Timestamp auditTime;
	private Timestamp wxAuditTime;
	private Timestamp closeTime;
	private Byte auditStatus;
	private String wxAuditFailReason;
	private String category;
	private String shopName;
	private String shopType;
	private Timestamp expireTime;
	private Byte isEnabled;
	private String nickName;
	private String principalName;
	private String userName;
	private String company;
	private String accountName;
	private String mpCategory;
	private Timestamp publishTime;
	private String defaultAccountId;
	private Byte packageVersion;
	private String afterSaleId;
	private List<MpCategoryInner> categoryList;
	private Integer successNum = 0;
	private Integer failedNum = 0;
	private Integer allNum = 0;
	private Byte preOperateStatus;
	/** 备注数量 */
	private Integer remarkNum;

	private List<SystemManagerVo> accountIds;
	private List<SystemManagerVo> afterSaleIds;
}
