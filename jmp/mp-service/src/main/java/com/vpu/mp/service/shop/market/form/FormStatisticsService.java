package com.vpu.mp.service.shop.market.form;

import com.vpu.mp.db.shop.tables.FormPage;
import com.vpu.mp.db.shop.tables.FormSubmitDetails;
import com.vpu.mp.db.shop.tables.FormSubmitList;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.FormPageRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitDetailsRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitListRecord;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.form.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@Slf4j
@Service
public class FormStatisticsService extends ShopBaseService {
    /**
     * FORM_PAGE表单删除状态值，删除状态页面不展示
     */
    private static final Byte FP_DEL_STATUS = 3;
    private static FormPage fp = FormPage.FORM_PAGE.as("fp");
    private static FormSubmitDetails fsd = FormSubmitDetails.FORM_SUBMIT_DETAILS.as("fsd");
    private static FormSubmitList fsl = FormSubmitList.FORM_SUBMIT_LIST.as("fsl");
    private static User u = User.USER.as("u");

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
        conditionStep = conditionStep.and(fp.STATE.notEqual(FP_DEL_STATUS));
        return getPageResult(conditionStep, param.getCurrentPage(), param.getPageRows(), FormInfoVo.class);
    }

    /**
     * 查看表单信息详情
     * 已发布的表单只可查看，不可编辑
     *
     * @param param 使用表单id，店铺id，用户id作为查询条件
     * @return formpage，formsubmitdetail，formsubmitlist三表汇总表单详细信息
     */
    public FormDetailVo getFormDetailInfo(FormDetailParam param) {
        try {
            int pageId = param.getPageId();
            int shopId = param.getShopId();
            int userId = param.getUserId();

            CompletableFuture<FormDetailVo> fpFuture = CompletableFuture.supplyAsync(() -> db().selectFrom(fp).where(fp.PAGE_ID.eq(pageId)).and(fp.SHOP_ID.eq(shopId)).fetchOptionalInto(FormDetailVo.class).orElse(new FormDetailVo()));
            FormDetailVo vo = fpFuture.get();
            CompletableFuture<FormList> fslFuture = CompletableFuture.supplyAsync(() -> db().selectFrom(fsl).where(fsl.PAGE_ID.eq(pageId)).and(fsl.SHOP_ID.eq(shopId)).and(fsl.USER_ID.eq(userId)).fetchOptionalInto(FormList.class).orElse(new FormList()));
            vo.setFormList(fslFuture.get());
            fslFuture.thenAccept((e) -> vo.setDetails(db().selectFrom(fsd).where(fsd.PAGE_ID.eq(pageId)).and(fsd.SUBMIT_ID.eq(e.getSubmitId())).fetchInto(FormDetail.class)));

            return vo;
        } catch (InterruptedException | ExecutionException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    /**
     * 添加表单信息
     *
     * @param param 表单信息
     */
    public void addFormInfo(FormUAParam param) {
        param.setPageId(null);
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);
        FormSubmitListRecord fslRecord = new FormSubmitListRecord();
        FieldsUtil.assignNotNull(param.getFormList(), fslRecord);
        FormSubmitDetailsRecord fsdRecord = new FormSubmitDetailsRecord();

        int[] temp = {-1, -1};
        log.debug("FormPage,FormSubmitList,FormSubmitDetails三表原子插入");
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.executeInsert(fpRecord);
            temp[0] = db.lastID().intValue();
            log.debug("获取刚刚FormPage表插入成功后生成的自增主键值：last_insert_id---{}", temp[0]);
            fslRecord.setPageId(temp[0]);
            fslRecord.setSubmitId(null);
            db.executeInsert(fslRecord);
            temp[1] = db.lastID().intValue();
            log.debug("获取刚刚FormSubmitList表插入成功后生成的自增主键值：last_insert_id---{}", temp[1]);
            for (FormDetail detail : param.getDetails()) {
                FieldsUtil.assignNotNull(detail, fsdRecord);
                fsdRecord.setSubmitId(temp[1]);
                fsdRecord.setRecId(null);
                db.executeInsert(fsdRecord);
                fsdRecord.reset();
            }
        });
    }

    /**
     * 更新表单信息
     *
     * @param param 表单信息
     */
    public void updateFormInfo(FormUAParam param) {
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);
        FormSubmitListRecord fslRecord = new FormSubmitListRecord();
        FieldsUtil.assignNotNull(param.getFormList(), fslRecord);
        FormSubmitDetailsRecord fsdRecord = new FormSubmitDetailsRecord();
        log.debug("FormPage,FormSubmitList,FormSubmitDetails三表原子更新");
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            if (db.fetchExists(fp, fp.PAGE_ID.eq(param.getPageId()))) {
                db.executeUpdate(fpRecord);
            }
            if (db.fetchExists(fsl, fsl.SUBMIT_ID.eq(param.getFormList().getSubmitId()))) {
                db.executeUpdate(fslRecord);
            }
            for (FormDetail detail : param.getDetails()) {
                if (db.fetchExists(fsd, fsd.REC_ID.eq(detail.getRecId()))) {
                    FieldsUtil.assignNotNull(detail, fsdRecord);
                    db.executeUpdate(fsdRecord);
                    fsdRecord.reset();
                }
            }
        });
    }

    //TODO 分享
    public void shareForm() {

    }

    /**
     * 发布/关闭/删除
     * 均是改变status状态值操作
     */
    public void changeFormStatus(FormStatusParam param) {
        db().update(fp).set(fp.STATE, param.getFormStatus()).where(fp.PAGE_ID.eq(param.getPageId())).execute();
    }

    /**
     * 表单复制
     *
     * @param param 表单唯一标识pageId
     */
    public FormDetailVo copyForm(FormDetailParam param) {
        return getFormDetailInfo(param);

    }

    /**
     * 反馈列表
     *
     * @param param 默认必要条件pageId和shopId，可选条件时间和昵称
     * @return 分页反馈列表信息
     */
    public PageResult<FormFeedVo> feedBackList(FormFeedParam param) {
        return getPageResult(getFeedBackStep(param), param.getCurrentPage(), param.getPageRows(), FormFeedVo.class);
    }

    public SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> getFeedBackStep(FormFeedParam param) {
        SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> conditionStep = db().select(fsl.SUBMIT_ID, fsl.PAGE_ID, fsl.USER_ID, fsl.NICK_NAME, fsl.CREATE_TIME, u.MOBILE).from(fsl).leftJoin(u).on(fsl.USER_ID.eq(u.USER_ID)).where(fsl.PAGE_ID.eq(param.getPageId())).and(fsl.SHOP_ID.eq(param.getShopId()));
        if (param.getNickName() != null && !"".equals(param.getNickName())) {
            conditionStep = conditionStep.and(fsl.NICK_NAME.eq(param.getNickName()));
        }
        if (param.getStartTime() != null) {
            conditionStep = conditionStep.and(fsl.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime())));
        }
        if (param.getEndTime() != null) {
            conditionStep = conditionStep.and(fsl.CREATE_TIME.lessOrEqual(new Timestamp(param.getEndTime().getTime())));
        }
        return conditionStep;
    }

    /**
     * 反馈列表导出
     *
     * @param param 导出数据筛选条件
     */
    public Workbook exportFeedBack(FormFeedParam param) {
        List<FormFeedVo> list = getFeedBackStep(param).fetchInto(FormFeedVo.class);
        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(list, FormFeedVo.class);
        return workbook;
    }

}
