package com.vpu.mp.service.pojo.shop.goods.recommend;

import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.db.shop.tables.records.RecommendGoodsRecord;
import com.vpu.mp.service.foundation.Util;
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
	
	private Timestamp delTime;
	
	
	
	
	
	
	public static GoodsRecommend fromRecord(RecommendGoodsRecord record) {
		GoodsRecommend recommend = new GoodsRecommend();
		recommend.setId(record.getId());
		recommend.setRecommendName(record.getRecommendName());
		recommend.setRecommendType(record.getRecommendType());
		recommend.setStatus(record.getStatus());
		recommend.setDelFlag(record.getDelFlag());
		recommend.setDelTime(record.getDelTime());
		recommend.setUpdateTime(record.getUpdateTime());
		recommend.setCreateTime(record.getCreateTime());
		String usePageJson = record.getRecommendUsePage();
		List usePageList = Util.parseJSON(usePageJson, List.class);
		recommend.setRecommendUsePage(usePageList);
		if(PARTTYPE.equals(recommend.getRecommendType())) {
			String catIds = record.getRecommendCatId();
			if(catIds != null ) {
				String[] split = catIds.split(DELIMITER);
				List<Integer> catIdList = Util.valueOf(split);
				recommend.setRecommendCatIds(catIdList);
			}
			String sortIds = record.getRecommendSortId();
			if(sortIds != null) {
				String[] split = sortIds.split(DELIMITER);
				List<Integer> sortIdList = Util.valueOf(split);
				recommend.setRecommendSortIds(sortIdList);
			}
		}
		return recommend;
	}
	
	
}
