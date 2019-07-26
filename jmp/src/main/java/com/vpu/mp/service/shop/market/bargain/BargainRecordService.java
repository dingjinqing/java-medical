package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.User.USER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordExportVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryVo;

import io.netty.util.internal.StringUtil;

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

	/**
	 * 根据状态取发起砍价的数量
	 * @param bargainId
	 * @param status
	 * @return
	 */
	public Integer getBargainRecordNumberByStatus(int bargainId,byte status) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.STATUS.eq(status)).and(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
	
	/**
	 * 某活动的发起砍价数量
	 * @param bargainId
	 * @return
	 */
	public Integer getBargainRecordNumber(int bargainId) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
	
	/**
	 * 发起记录的分页列表
	 * @param param
	 * @return
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
		if(!StringUtil.isNullOrEmpty(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		if(!StringUtil.isNullOrEmpty(param.getMobile())) {
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
	 * @param record
	 * @return
	 */
	public BigDecimal getBargainRecordSurplusMoney(BargainRecordPageListQueryVo record) {
		if(record.getBargainType() == BargainService.BARGAIN_TYPE_FIXED) {
			return record.getGoodsPrice().subtract(record.getExpectationPrice()).subtract(record.getBargainMoney());
		}else if(record.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM) {
			return record.getGoodsPrice().subtract(record.getFloorPrice()).subtract(record.getBargainMoney());
		}
		return BigDecimal.ZERO;
	}
	
	public Workbook exportBargainRecordList(BargainRecordPageListQueryParam param) throws IOException {
		
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
		List<BargainRecordExportVo> bargainRecordList =  select.fetchInto(BargainRecordExportVo.class);

        Workbook workbook=ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(bargainRecordList,BargainRecordExportVo.class);

        return workbook;
	}
}
