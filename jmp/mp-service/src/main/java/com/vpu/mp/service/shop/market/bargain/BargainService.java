package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.market.bargain.Bargain;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainAddParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUpdateParam;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisDataVo;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisTotalVo;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Service
public class BargainService extends ShopBaseService  {
	
	/**
	 *  砍价发起记录
	 */
	@Autowired public BargainRecordService bargainRecord;
	
	/**
	 *  帮忙砍价的用户
	 */
	@Autowired public BargainUserService bargainUser;
	
	/**
	 * 启用状态 
	 */
	public static final byte STATUS_NORMAL = 1;
	/**
	 * 停用状态 
	 */
	public static final byte STATUS_DISABLED = 0;
	
	/**
	 * 活动类型 固定人数
	 */
	public static final byte BARGAIN_TYPE_FIXED = 0;
	/**
	 * 活动类型 砍到区间内结算 
	 */
	public static final byte BARGAIN_TYPE_RANDOM = 1;
	
	/**
	 * 砍价活动列表分页查询
	 *
	 */
	public PageResult<BargainPageListQueryVo> getPageList(BargainPageListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN.ID,BARGAIN.BARGAIN_NAME,BARGAIN.BARGAIN_TYPE,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.STATUS,
				BARGAIN.GOODS_ID,BARGAIN.STOCK,
				GOODS.GOODS_NAME,GOODS.GOODS_NUMBER
				).
				from(BARGAIN).
				leftJoin(GOODS).on(BARGAIN.GOODS_ID.eq(GOODS.GOODS_ID));
		if(param.getState() > 0) {
			/** 状态过滤*/
			Timestamp now = Util.getLocalDateTime();
			switch(param.getState()) {
			case (byte)1:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.lt(now)).and(BARGAIN.END_TIME.gt(now));
				break;
			case (byte)2:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.gt(now));
				break;
			case (byte)3:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.END_TIME.lt(now));
				break;
			case (byte)4:
				select.where(BARGAIN.STATUS.eq(STATUS_DISABLED));
			break;
			default:
				
			}
		}
		select.where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(BARGAIN.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainPageListQueryVo.class);
	}
	
	/**
	 * 新建砍价活动
	 *
	 */
	public boolean addBargain(BargainAddParam param) {
		BargainRecord record = new BargainRecord();
		assign(param,record);
		if(param.getShareConfig() != null) {
			record.setShareConfig(Util.toJson(param.getShareConfig()));
		}
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 更新砍价活动
	 *
	 */
	public boolean updateBargain(BargainUpdateParam param) {
		BargainRecord record = new BargainRecord();
		assign(param,record);
		if(param.getShareConfig() != null) {
			record.setShareConfig(Util.toJson(param.getShareConfig()));
		}
		return db().executeUpdate(record) > 0 ? true : false;
	}
	
	/**
	 * 取单个砍价活动信息
	 *
	 *
	 */
	public Bargain getBargainByIsd(Integer bargainId) {
		Bargain bargain = db().select(BARGAIN.fields()).from(BARGAIN).where(BARGAIN.ID.eq(bargainId)).fetchOne().into(Bargain.class);
		if(bargain != null) {
			GoodsView goods = db().select(GOODS.GOODS_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,GOODS.GOODS_NUMBER,GOODS.SHOP_PRICE).from(GOODS).where(GOODS.GOODS_ID.eq(bargain.getGoodsId())).fetchOne().into(GoodsView.class	);
			bargain.setGoods(goods);
			return bargain;
		}else {
			return null;
		}
	}

	/**
	 * 砍价效果分析的echarts图表数据
	 *
	 *
	 */
	public BargainAnalysisDataVo getBargainAnalysisData(BargainAnalysisParam param){
		Map<Date,Integer> recordMap = saas().getShopApp(getShopId()).bargain.bargainRecord.getRecordAnalysis(param);
		Map<Date,Integer> userMap = saas().getShopApp(getShopId()).bargain.bargainRecord.getBargainUserAnalysis(param);
		Map<Date,Integer> orderMap = saas().getShopApp(getShopId()).readOrder.getBargainOrderAnalysis(param);
		Map<Date,Integer> sourceMap = saas().getShopApp(getShopId()).member.getBargainUserAnalysis(param);

		Date temDate = new Date(param.getStartTime().getTime());
		Date endTime = new Date(param.getEndTime().getTime());
		endTime = getNextDay(endTime);

		BargainAnalysisDataVo bargainAnalysisDataVo = new BargainAnalysisDataVo();

		/** 组装输出数据格式 */
		while(temDate.before(endTime)){
			/**发起砍价用户数*/
			if(recordMap.get(temDate) != null && recordMap.get(temDate) > 0){
				bargainAnalysisDataVo.getRecordNumber().add(recordMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getRecordNumber().add(0);
			}

			/**帮砍价用户数*/
			if(userMap.get(temDate) != null && userMap.get(temDate) > 0){
				bargainAnalysisDataVo.getUserNumber().add(userMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getUserNumber().add(0);
			}

			/**活动订单数*/
			if(orderMap.get(temDate) != null && orderMap.get(temDate) > 0){
				bargainAnalysisDataVo.getOrderNumber().add(orderMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getOrderNumber().add(0);
			}

			/**活动拉新用户数*/
			if(sourceMap.get(temDate) != null && sourceMap.get(temDate) > 0){
				bargainAnalysisDataVo.getSourceNumber().add(orderMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getSourceNumber().add(0);
			}

			/**日期列表*/
			bargainAnalysisDataVo.getDateList().add(temDate);

			temDate = getNextDay(temDate);
		}
		BargainAnalysisTotalVo total = new BargainAnalysisTotalVo();
		total.setRecordTotal(bargainAnalysisDataVo.getRecordNumber().stream().mapToInt(Integer::intValue).sum());
		total.setUserTotal(bargainAnalysisDataVo.getUserNumber().stream().mapToInt(Integer::intValue).sum());
		total.setOrderTotal(bargainAnalysisDataVo.getOrderNumber().stream().mapToInt(Integer::intValue).sum());
		total.setSourceTotal(bargainAnalysisDataVo.getSourceNumber().stream().mapToInt(Integer::intValue).sum());
		bargainAnalysisDataVo.setTotal(total);
		return bargainAnalysisDataVo;
	}

	/**
	 * 取holdDate的一下天
	 * @param holdDate java.sql.Date类型
	 * @return java.sql.Date
	 */
	private Date getNextDay(Date holdDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(holdDate);
		calendar.add(Calendar.DATE, 1);
		return new Date(calendar.getTime().getTime());
	}
}
