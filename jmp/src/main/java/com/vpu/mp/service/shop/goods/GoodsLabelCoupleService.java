package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;

import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;

/**
 * @author 黄荣刚
 * @date 2019年7月5日
 *
 */
public class GoodsLabelCoupleService extends BaseService {

	/**
	 * 根据标签Id，查询标签的关联信息
	 * @param goodsLabel
	 * @return
	 */
	public List<GoodsLabelCouple> getList(GoodsLabel goodsLabel){
		if(goodsLabel == null) {
			return new ArrayList();
		}
		List<GoodsLabelCouple> list = db().select(GOODS_LABEL_COUPLE.ID, GOODS_LABEL_COUPLE.LABEL_ID, GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.TYPE)
				.from(GOODS_LABEL_COUPLE)
				.where(GOODS_LABEL_COUPLE.LABEL_ID.eq(goodsLabel.getId()))
				.fetchInto(GoodsLabelCouple.class);
		return list;
	}
	
	/**
	 * 将传入商品标签所关联的标签关联信息入库
	 * @param goodsLabel
	 */
	public void insert(GoodsLabel goodsLabel) {
		List<GoodsLabelCouple> labelCoupleList = goodsLabel.getAddGoodsLabelCoupleList();
		if(labelCoupleList == null || labelCoupleList.size()==0) {
			return ;
		}
		List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList<GoodsLabelCoupleRecord>(labelCoupleList.size());
		for (GoodsLabelCouple goodsLabelCouple : labelCoupleList) {
			goodsLabelCouple.setLabelId(goodsLabel.getId());
			labelCoupleRecordList.add(goodsLabelCouple.toRecord());
		}
		db().batchInsert(labelCoupleRecordList).execute();
	}
	/**
	 * 批量删除商品标签关联信息
	 * @param goodsLabelCoupleList
	 */
	public void batchDelete(List<GoodsLabelCouple> goodsLabelCoupleList) {
		if(goodsLabelCoupleList == null || goodsLabelCoupleList.size()==0) {
			return ;
		}
		List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList(goodsLabelCoupleList.size());
		for (GoodsLabelCouple goodsLabelCouple : goodsLabelCoupleList) {
			labelCoupleRecordList.add(goodsLabelCouple.toRecord());
		}
		db().batchDelete(labelCoupleRecordList).execute();
		return ;
	}
	/**
	 * 批量增加商品标签的关联信息
	 * @param goodsLabelCoupleList
	 */
	public void batchInsert(List<GoodsLabelCouple> goodsLabelCoupleList) {
//		if(goodsLabelCoupleList == null || goodsLabelCoupleList.size()==0) {
//			return ;
//		}
//		List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList(goodsLabelCoupleList.size());
//		for (GoodsLabelCouple goodsLabelCouple : goodsLabelCoupleList) {
//			labelCoupleRecordList.add(goodsLabelCouple.toRecord());
//		}
//		db().batchInsert(labelCoupleRecordList).execute();
//		return ;
		
		batchInsert(db(),goodsLabelCoupleList);
	}
	
	/**
	 *	重载batchInsert方法，对外提供统一事务处理接口
	 *@author 李晓冰
	 *@param db
	 *@param goodsLabelCoupleList
	 */
	public void batchInsert(DSLContext db,List<GoodsLabelCouple> goodsLabelCoupleList) {
		if(goodsLabelCoupleList == null || goodsLabelCoupleList.size()==0) {
			return ;
		}
		List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList(goodsLabelCoupleList.size());
		for (GoodsLabelCouple goodsLabelCouple : goodsLabelCoupleList) {
			labelCoupleRecordList.add(goodsLabelCouple.toRecord());
		}
		db.batchInsert(labelCoupleRecordList).execute();
	}
	
	
}
