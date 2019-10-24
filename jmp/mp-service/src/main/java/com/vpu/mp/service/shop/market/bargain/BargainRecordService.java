package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;
import static com.vpu.mp.db.shop.tables.BargainUserList.BARGAIN_USER_LIST;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainRecordListQueryParam;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainRecordListQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordExportVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisParam;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Service
public class BargainRecordService extends ShopBaseService {
	
	/**
	 * 状态：正在砍价 
	 */
	public static final byte STATUS_IN_PROCESS = 0;
	/**
	 *  状态：砍价成功 
	 */
	public static final byte STATUS_SUCCESS = 1;
	/**
	 *  状态：砍价失败 
	 */
	public static final byte STATUS_FAILED = 2;
	
	private static final String LANGUAGE_TYPE_EXCEL= "excel";

	/**
	 * 根据状态取发起砍价的数量
	 *
	 */
	public Integer getBargainRecordNumberByStatus(int bargainId,byte status) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.STATUS.eq(status)).and(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
	
	/**
	 * 某活动的发起砍价数量
	 *
	 *
	 */
	public Integer getBargainRecordNumber(int bargainId) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
	
	/**
	 * 发起记录的分页列表
	 *
	 *
	 */
	public PageResult<BargainRecordPageListQueryVo> getRecordPageList(BargainRecordPageListQueryParam param){
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN_RECORD.ID,GOODS.GOODS_NAME,BARGAIN_RECORD.GOODS_PRICE,USER.USERNAME,USER.MOBILE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
				BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS ,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE			
				).
				from(BARGAIN_RECORD).
				leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
				leftJoin(USER).on(BARGAIN_RECORD.USER_ID.eq(USER.USER_ID)).
				leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID));
		select = this.buildOptions(select, param);
		select.where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainRecordPageListQueryVo.class);
	}
	
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, BargainRecordPageListQueryParam param) {
		if (param == null) {
			return select;
		}
		if(!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if(param.getStatus() > -1) {
			select.where(BARGAIN_RECORD.STATUS.eq(param.getStatus()));
		}
		if(param.getStartTime() != null) {
			select.where(BARGAIN_RECORD.CREATE_TIME.gt(param.getStartTime()));
		}
		if(param.getEndTime() != null) {
			select.where(BARGAIN_RECORD.CREATE_TIME.lt(param.getEndTime()));
		}
		return select;
	}
	
	/**
	 * 算出待砍金额
	 *
	 */
	public BigDecimal getBargainRecordSurplusMoney(BargainRecordPageListQueryVo record) {
		if(record.getBargainType() == BargainService.BARGAIN_TYPE_FIXED) {
			return record.getGoodsPrice().subtract(record.getExpectationPrice()).subtract(record.getBargainMoney());
		}else if(record.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM) {
			return record.getGoodsPrice().subtract(record.getFloorPrice()).subtract(record.getBargainMoney());
		}
		return BigDecimal.ZERO;
	}
	
	public Workbook exportBargainRecordList(BargainRecordPageListQueryParam param, String lang) {
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN_RECORD.ID,GOODS.GOODS_NAME,BARGAIN_RECORD.GOODS_PRICE,USER.USERNAME,USER.MOBILE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
				BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE			
				).
				from(BARGAIN_RECORD).
				leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
				leftJoin(USER).on(BARGAIN_RECORD.USER_ID.eq(USER.USER_ID)).
				leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID));
		select = this.buildOptions(select, param);
		select.where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
		List<BargainRecordExportVo> bargainRecordList =  select.fetchInto(BargainRecordExportVo.class);
		
		/**循环处理状态和待砍金额列*/
		for(BargainRecordExportVo vo : bargainRecordList) {
			switch(vo.getStatus()) {
				case 0:
					vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_IN_PROGRESS,LANGUAGE_TYPE_EXCEL));
					break;
				case 1:
					vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_SUCCESS,LANGUAGE_TYPE_EXCEL));
					break;
				case 2:
					vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_FAIL,LANGUAGE_TYPE_EXCEL));
					break;
				default:
			}
			if(vo.getBargainType() == BargainService.BARGAIN_TYPE_FIXED) {
				vo.setSurplusMoney(vo.getGoodsPrice().subtract(vo.getExpectationPrice()).subtract(vo.getBargainMoney()));
			}else if(vo.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM) {
				vo.setSurplusMoney(vo.getGoodsPrice().subtract(vo.getFloorPrice()).subtract(vo.getBargainMoney()));
			}
		}
		
		Workbook workbook=ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(bargainRecordList,BargainRecordExportVo.class);
        return workbook;
	}

	/**
	 * 发起砍价的人次数据分析
	 *
	 */
	public Map<Date,Integer> getRecordAnalysis(BargainAnalysisParam param){
		Map<Date,Integer> map =  db().select(date(BARGAIN_RECORD.CREATE_TIME).as("date"),count().as("number")).from(BARGAIN_RECORD).
				where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).
				and(BARGAIN_RECORD.CREATE_TIME.between(param.getStartTime(),param.getEndTime())).
				groupBy(date(BARGAIN_RECORD.CREATE_TIME)).fetch().intoMap(date(BARGAIN_RECORD.CREATE_TIME).as("date"),count().as("number"));
		return map;
	}

	/**
	 * 帮砍价的用户数据分析
	 *
	 */
	public Map<Date,Integer> getBargainUserAnalysis(BargainAnalysisParam param){
		Map<Date,Integer> map =  db().select(date(BARGAIN_USER_LIST.CREATE_TIME).as("date"),count().as("number")).from(BARGAIN_USER_LIST).
				leftJoin(BARGAIN_RECORD).on(BARGAIN_USER_LIST.RECORD_ID.eq(BARGAIN_RECORD.ID)).
				where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).
				and(BARGAIN_RECORD.CREATE_TIME.between(param.getStartTime(),param.getEndTime())).
				groupBy(date(BARGAIN_USER_LIST.CREATE_TIME)).fetch().intoMap(date(BARGAIN_USER_LIST.CREATE_TIME).as("date"),count().as("number"));
		return map;
	}

    /**
     * 小程序端-个人中心-我的砍价列表
     *
     *
     */
    public PageResult<BargainRecordListQueryVo> getRecordPageList(Integer userId,BargainRecordListQueryParam param){
        SelectWhereStep<? extends Record> select = db().select(
            BARGAIN_RECORD.ID,GOODS.GOODS_NAME,BARGAIN_RECORD.GOODS_PRICE,USER.USERNAME,USER.MOBILE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
            BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS ,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE
        ).
            from(BARGAIN_RECORD).
            leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
            leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID)).
            leftJoin(ORDER_INFO).on(BARGAIN_RECORD.ORDER_SN.eq(ORDER_INFO.ORDER_SN));

        select.where(BARGAIN_RECORD.USER_ID.eq(userId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        select.orderBy(BARGAIN_RECORD.CREATE_TIME.desc());
        PageResult<BargainRecordListQueryVo> list = getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainRecordListQueryVo.class);

        for(BargainRecordListQueryVo bargainRecord : list.dataList){
            if(bargainRecord.getGoodsNumber() < bargainRecord.getStock()){
                bargainRecord.setStock(bargainRecord.getGoodsNumber());
            }
            if(bargainRecord.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM){
                BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney());
                if(remainMoney.compareTo(bargainRecord.getExpectationPrice()) < 0){
                    bargainRecord.setExpectationPrice(remainMoney);
                }
            }
        }
        return list;
    }
}
