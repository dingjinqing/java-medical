package com.vpu.mp.service.pojo.saas.category;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysCatevo {
	public Short catId;
	public String catName;
	public Short parentId;
	public Integer level;
	public Integer hasChild;
	public Integer first;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer goodsNumber;
}
