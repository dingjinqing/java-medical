package com.vpu.mp.service.pojo.shop.goods.recommend;

import java.util.List;

import com.vpu.mp.service.pojo.shop.goods.GoodsView;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月10日
 *
 */
@Data
public class GoodsRecommendPojo {
	private Integer id;
	private String recommendName;
	private String recommendType;
	private List<GoodsView> recommendGoodsList;
	private List<Integer>recommendSortIdList;
	private List<Integer>recommendCatIdList;
	
	private List<String>recommendUsePageList;
	
}
