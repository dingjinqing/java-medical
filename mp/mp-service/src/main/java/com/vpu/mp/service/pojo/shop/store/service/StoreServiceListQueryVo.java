package com.vpu.mp.service.pojo.shop.store.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceListQueryVo {
	private Integer id;
	private String serviceName;
	private String serviceImg;
	private BigDecimal servicePrice;
	private String catName;
	private Integer saleNum;
	private Timestamp createTime;
	private Byte serviceType;
	private String technicianTitle;
	private Byte serviceShelf;
	
}
