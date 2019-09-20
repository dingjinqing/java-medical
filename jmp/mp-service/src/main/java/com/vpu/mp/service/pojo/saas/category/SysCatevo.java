package com.vpu.mp.service.pojo.saas.category;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysCatevo {
	public Integer catId;
	public String catName;
	public Integer parentId;
	public Short level;
	public Integer hasChild;
	public Integer first;
	private Timestamp createTime;
	private Timestamp updateTime;
    /**
     * 数据库平台分类对应店家内的商品数量（不包含子孙商品数量）
     */
	private Integer goodsNumber;
    /**
     * 数据库平台分类对应店家内的商品数量（包含子孙商品数量的总和）
     */
	private Integer goodsNumberSum;
}
