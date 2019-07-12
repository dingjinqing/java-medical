package com.vpu.mp.service.pojo.shop.goods.label;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月5日
 *
 */
@Data
public class GoodsLabel {
	private Integer id;
	private String name;
	private Byte goodsDetail;
	private Byte goodsList;
	private Byte isAll;
	private Short level;
	private Short listPattern;
	private Byte goodsSelect;
	
	private List<Integer> goodsId;
	private List<Integer> sortId;
	private List<Integer> catId;
	
}
