package com.vpu.mp.service.shop.store.verify;

import static com.vpu.mp.db.shop.tables.OrderVerifier.ORDER_VERIFIER;
import static com.vpu.mp.db.shop.tables.User.USER;

import com.vpu.mp.db.shop.tables.records.OrderVerifierRecord;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.pojo.shop.store.verifier.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.InsertValuesStep3;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;

import java.util.List;

/**
 * @author 王兵兵
 *
 * 2019年7月11日
 */
@Service


public class StoreVerifierService extends ShopBaseService{
	/**
	 *	 门店核销员列表分页查询
	 * @param param
	 * @return VerifierListVo
	 */
	public PageResult<VerifierListVo> getPageList(VerifierListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(ORDER_VERIFIER.USER_ID,USER.USERNAME,USER.MOBILE,ORDER_VERIFIER.VERIFY_ORDERS)
				.from(ORDER_VERIFIER)
				.leftJoin(USER).on(ORDER_VERIFIER.USER_ID.eq(USER.USER_ID));
		select = this.buildOptions(select, param);
		select.where(ORDER_VERIFIER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(ORDER_VERIFIER.CREATE_TIME);
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),VerifierListVo.class);
	}
	
	/**
	 *	 查询条件构建
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, VerifierListQueryParam param) {
		select.where(ORDER_VERIFIER.STORE_ID.eq(param.getStoreId()));
		if (!StringUtils.isEmpty(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if (!StringUtils.isEmpty(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		return select;
	}
	
	/**
	 * 添加核销员
	 * @param param
	 * @return
	 */
	public void addVerifiers(VerifierAddParam param) {
        this.transaction(()->{
            for(int userId : param.getUserIds()){
                OrderVerifierRecord record = db().newRecord(ORDER_VERIFIER);
                record.setStoreId(param.getStoreId());
                record.setUserId(userId);
                record.setDelFlag(DelFlag.NORMAL_VALUE);
                record.store();
            }
        });
	}

    /**
     * 删除核销员
     * @param verifier
     * @return
     */
    public void delStoreVerifier(VerifierSimpleParam verifier) {
        db().update(ORDER_VERIFIER).set(ORDER_VERIFIER.DEL_FLAG,DelFlag.DISABLE_VALUE).where(ORDER_VERIFIER.STORE_ID.eq(verifier.getStoreId()).and(ORDER_VERIFIER.USER_ID.eq(verifier.getUserId()))).execute();
    }

    /**
     *	 门店核销员列表导出
     * @param param
     * @return VerifierListVo
     */
    public Workbook exportStoreVerifierList(VerifierListQueryParam param, String lang) {
        SelectWhereStep<? extends Record> select = db().select(ORDER_VERIFIER.USER_ID,USER.USERNAME,USER.MOBILE,ORDER_VERIFIER.VERIFY_ORDERS)
            .from(ORDER_VERIFIER)
            .leftJoin(USER).on(ORDER_VERIFIER.USER_ID.eq(USER.USER_ID));
        select = this.buildOptions(select, param);
        select.where(ORDER_VERIFIER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(ORDER_VERIFIER.CREATE_TIME);
        List<VerifierExportVo> storeVerifierList =  select.fetchInto(VerifierExportVo.class);

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(storeVerifierList, VerifierExportVo.class);
        return workbook;
    }
	
}
