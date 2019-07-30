package com.vpu.mp.service.pojo.shop.goods.label;

import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.service.pojo.shop.goods.GoodsView;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月11日
 *
 */
@Data
public class GoodsLabelVo {

	private Integer id;
	private String name;
	private Byte goodsDetail;
	private Byte goodsList;
	private Byte isAll;
	private Short level;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short listPattern;
	private Byte goodsSelect;
	
	private List<GoodsView> goodsViewList;
	private List<Integer> catIdList;
	private List<Integer> sortIdList;
}
