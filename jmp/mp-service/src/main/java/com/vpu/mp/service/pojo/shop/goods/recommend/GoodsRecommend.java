package com.vpu.mp.service.pojo.shop.goods.recommend;

import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.service.pojo.shop.goods.GoodsView;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月9日
 *
 */
@Data
public class GoodsRecommend {
	/** 全部商品 */
	public final static Byte ALLTYPE = 0;
	
	/** 指定商品 */
	public final static Byte PARTTYPE = 1;
	
	/** 激活状态 */
	public final static Byte STATUS_ACTIVE = 0;
	
	/** 暂时停用状态 */
	public final static Byte STATUS_PAUSE = 1;
	
	/** 商品、商家分类、平台分类 ID分隔符 */
	public static final String DELIMITER = ",";

	private Integer id;
	private String recommendName;
	
	/** 类型：全部商品、指定商品 */
	private Byte recommendType ;
	
	/** 当类型为指定商品时 选择的商品列表集合 */
	private List<GoodsView> recommendGoods;
	/** 当类型为指定商品时 选择的商家分类列表ID集合 */
	private List<Integer> recommendSortIds;
	/** 当类型为指定商品时 选择的平台分类列表ID集合 */
	private List<Integer> recommendCatIds;
	
	/** 应用页面 */
	private List<String> recommendUsePage; 
	
	/** 启用状态 */
	private Byte status;
	
	/** 删除标志 1表示已删除*/
	private Byte delFlag;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	
	
	
	
	
	
	
}
