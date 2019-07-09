package com.vpu.mp.service.pojo.shop.goods.label;

import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月5日
 *
 */
@Data
@AllArgsConstructor
public class GoodsLabelCouple {
	
	//李晓冰添加关联商品码值
	public static final Byte GOODS_LABEL_CODE=1;

	private Integer id;
	private Integer labelId;
	private Integer gtaId;
	private Byte type;
	
	public GoodsLabelCoupleRecord toRecord(){
		GoodsLabelCoupleRecord labelCoupleRecord = new GoodsLabelCoupleRecord();
		labelCoupleRecord.setId(this.id);
		labelCoupleRecord.setLabelId(this.labelId);
		labelCoupleRecord.setGtaId(this.gtaId);
		labelCoupleRecord.setType(this.type);
		return labelCoupleRecord;
	}
}
