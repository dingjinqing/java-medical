package com.vpu.mp.service.pojo.shop.store.account;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
@Data
public class StoreAccountVo {
	private Integer accountId;
	private Integer shopId;
	private Integer sysId;
	private String mobile;
	private String accountName;
	private Timestamp createTime;
	private Byte accountType;
	private Byte status;
	private Byte delFlag;
	//private String accountPasswd;
	private String storeList;
	private Timestamp updateTime;

	private Integer storeNum;
	private List<Integer> storeLists;
}
