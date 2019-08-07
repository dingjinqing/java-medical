package com.vpu.mp.service.shop.market.form;

import com.vpu.mp.db.shop.tables.FormPage;
import com.vpu.mp.db.shop.tables.FormSubmitDetails;
import com.vpu.mp.db.shop.tables.FormSubmitList;
import com.vpu.mp.db.shop.tables.records.FormPageRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitDetailsRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitListRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.form.FormDetail;
import com.vpu.mp.service.pojo.shop.market.form.FormInfoVo;
import com.vpu.mp.service.pojo.shop.market.form.FormSearchParam;
import com.vpu.mp.service.pojo.shop.market.form.FormUAParam;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@Service
public class FormStatisticsService extends ShopBaseService {
    private static FormPage fp = FormPage.FORM_PAGE.as("fp");
    private static FormSubmitDetails fsd = FormSubmitDetails.FORM_SUBMIT_DETAILS.as("fsd");
    private static FormSubmitList fsl = FormSubmitList.FORM_SUBMIT_LIST.as("fsl");

    /**
     * 分页查询表单信息
     *
     * @param param 筛选条件
     * @return 分页结果集
     */
    public PageResult<FormInfoVo> selectFormInfo(FormSearchParam param) {
        SelectConditionStep<Record5<String, Timestamp, Integer, Byte, Byte>> conditionStep = db().select(fp.PAGE_NAME, fp.CREATE_TIME, fp.SUBMIT_NUM, fp.STATE.as("status"), fp.IS_FOREVER_VALID.as("validityPeriod")).from(fp).where();
        if (param.getStatus() > -1) {
            conditionStep = conditionStep.and(fp.STATE.eq(param.getStatus()));
        }
        if (param.getPageName() != null && !"".equals(param.getPageName())) {
            conditionStep = conditionStep.and(fp.PAGE_NAME.eq(param.getPageName()));
        }
        if (param.getStartTime() != null) {
            conditionStep = conditionStep.and(fp.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime())));
        }
        if (param.getEndTime() != null) {
            conditionStep = conditionStep.and(fp.CREATE_TIME.lessOrEqual(new Timestamp(param.getEndTime().getTime())));
        }
        return getPageResult(conditionStep, param.getCurrentPage(), param.getPageRows(), FormInfoVo.class);
    }

    /**
     * 添加表单信息
     * @param param 表单信息
     */
    public void addFormInfo(FormUAParam param){
        param.setPageId(null);
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param,fpRecord);
        FormSubmitListRecord fslRecord = new FormSubmitListRecord();
        FieldsUtil.assignNotNull(param.getFormList(),fslRecord);
        FormSubmitDetailsRecord fsdRecord = new FormSubmitDetailsRecord();

        int[] temp = {-1,-1};
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.executeInsert(fpRecord);
            temp[0] = db.execute("select LAST_INSERT_ID()");
            fslRecord.setPageId(temp[0]);
            fslRecord.setSubmitId(null);
            db.executeInsert(fslRecord);
            temp[1] = db.execute("select LAST_INSERT_ID()");
            for (FormDetail detail : param.getDetails()){
                FieldsUtil.assignNotNull(detail,fsdRecord);
                fsdRecord.setSubmitId(temp[1]);
                fsdRecord.setRecId(null);
                db.executeInsert(fsdRecord);
                fsdRecord.reset();
            }
        });
    }

    /**
     * 更新表单信息
     * @param param 表单信息
     */
    public void updateFormInfo(FormUAParam param){
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param,fpRecord);
        FormSubmitListRecord fslRecord = new FormSubmitListRecord();
        FieldsUtil.assignNotNull(param.getFormList(),fslRecord);
        FormSubmitDetailsRecord fsdRecord = new FormSubmitDetailsRecord();

        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.selectCount().from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(Integer.class).orElseThrow(()->new Exception("Form information does not exist !"));
            db.executeUpdate(fpRecord);
            db.selectCount().from(fsl).where(fsl.SUBMIT_ID.eq(param.getFormList().getSubmitId())).fetchOptionalInto(Integer.class).orElseThrow(()->new Exception("Form information does not exist !"));
            db.executeUpdate(fslRecord);
            for (FormDetail detail : param.getDetails()){
                db.selectCount().from(fsd).where(fsd.REC_ID.eq(detail.getRecId())).fetchOptionalInto(Integer.class).orElseThrow(()->new Exception("Form information does not exist !"));
                FieldsUtil.assignNotNull(detail,fsdRecord);
                db.executeInsert(fsdRecord);
                fsdRecord.reset();
            }
        });

    }
}
