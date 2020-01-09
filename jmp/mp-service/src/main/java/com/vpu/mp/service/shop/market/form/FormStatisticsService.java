package com.vpu.mp.service.shop.market.form;

import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.FormPageRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitDetailsRecord;
import com.vpu.mp.db.shop.tables.records.FormSubmitListRecord;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.form.*;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.image.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.*;
import static org.jooq.impl.DSL.countDistinct;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@Slf4j
@Service
public class FormStatisticsService extends ShopBaseService {
    @Autowired
    QrCodeService qrCodeService;
    @Autowired
    CouponGiveService couponGiveService;
    /**
     * FORM_PAGE表单删除状态值，删除状态页面不展示
     */
    private static final Byte FP_DEL_STATUS = 3;
    /**
     * 分享二维码页面显示图片路径
     */
    private static final String PARAM = "page_id=";

    private static FormPage fp = FormPage.FORM_PAGE.as("fp");
    private static FormSubmitDetails fsd = FormSubmitDetails.FORM_SUBMIT_DETAILS.as("fsd");
    private static FormSubmitList fsl = FormSubmitList.FORM_SUBMIT_LIST.as("fsl");
    private static User u = User.USER.as("u");
    private static CustomerAvailCoupons cac = CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS.as("cac");

    /**
     * 分页查询表单信息
     *
     * @param param 筛选条件
     * @return 分页结果集
     */
    public PageResult<FormInfoVo> selectFormInfo(FormSearchParam param) {
        SelectConditionStep<Record5<String, Timestamp, Integer, Byte, Byte>> conditionStep = db().select(fp.PAGE_NAME, fp.CREATE_TIME, fp.SUBMIT_NUM, fp.STATE.as("status"), fp.IS_FOREVER_VALID.as("validityPeriod")).from(fp).where();
        if (param.getStatus() != null) {
            conditionStep = conditionStep.and(fp.STATE.eq(param.getStatus()));
        }
        if (param.getPageName() != null && !"".equals(param.getPageName())) {
            conditionStep = conditionStep.and(fp.PAGE_NAME.like(this.likeValue(param.getPageName())));
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
     * @param param 表单id
     * @return formpage表单详细信息
     */
    public FormDetailVo getFormDetailInfo(FormDetailParam param) {
        return db().selectFrom(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(FormDetailVo.class).orElse(new FormDetailVo());
    }

    /**
     * 添加表单信息
     *
     * @param param 表单信息
     */
    public void addFormInfo(FormAddParam param) {
        param.setPageId(null);
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);
        //设置初始反馈数量为0
        fpRecord.setSubmitNum(0);
        db().executeInsert(fpRecord);
    }

    /**
     * 更新表单信息
     *
     * @param param 表单信息
     */
    public void updateFormInfo(FormUpdateParam param) {
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);
        //TODO 空字串处理，不插库
        if (db().fetchExists(fp, fp.PAGE_ID.eq(param.getPageId()))) {
            db().executeUpdate(fpRecord);
        }
    }

    /**
     * 分享,获取小程序二维码
     *
     * @param param 表单id
     * @return 图片路径
     */
    public ShareQrCodeVo shareForm(FormDetailParam param) {
        String pathParam = PARAM + param.getPageId();
        String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.FORM, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;

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
    public FormCopyVo copyForm(FormDetailParam param) {
        FormCopyVo vo = new FormCopyVo();
        FieldsUtil.assignNotNull(getFormDetailInfo(param),vo);
        return vo;

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

    private SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> getFeedBackStep(FormFeedParam param) {
        SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> conditionStep = db().select(fsl.SUBMIT_ID, fsl.PAGE_ID, fsl.USER_ID, fsl.NICK_NAME, fsl.CREATE_TIME, u.MOBILE).from(fsl).leftJoin(u).on(fsl.USER_ID.eq(u.USER_ID)).where(fsl.PAGE_ID.eq(param.getPageId())).and(fsl.SHOP_ID.eq(param.getShopId()));
        if (param.getNickName() != null && !"".equals(param.getNickName())) {
            conditionStep = conditionStep.and(fsl.NICK_NAME.like(param.getNickName()));
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
     * TODO 导出数量控制
     *
     * @param param 导出数据筛选条件
     */
    public Workbook exportFeedBack(FormFeedParam param) {
        List<FormFeedExportVo> list = getFeedBackStep(param).fetchInto(FormFeedExportVo.class);
        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(list, FormFeedExportVo.class);
        return workbook;
    }

    /**
     * 反馈信息详情
     *
     * @param param 表单id和用户id
     * @return 反馈信息详情列表
     */
    public List<FeedBackDetailVo> feedBackDetail(FeedBackDetailParam param) {
        List<FeedBackDetailVo> list = db().select(fsd.SUBMIT_ID, fsd.MODULE_NAME, fsd.MODULE_TYPE, fsd.MODULE_VALUE, fsd.CUR_IDX.as("curIdx")).from(fsd).where(fsd.PAGE_ID.eq(param.getPageId())).and(fsd.USER_ID.eq(param.getUserId())).fetchInto(FeedBackDetailVo.class);

        for (FeedBackDetailVo vo : list) {
            String moduleName = vo.getModuleName();
            if (!ALL.containsKey(moduleName)) {
                continue;
            }
            if (SPECIAL.containsKey(moduleName)) {
                try {
                    String curIdx = vo.getCurIdx();
                    String pageContent = db().select(fp.PAGE_CONTENT).from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(String.class).orElse("");
                    JsonNode node = MAPPER.readTree(pageContent);
                    vo.setModuleValueList(findModuleValue(node, curIdx));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private List<String> findModuleValue(JsonNode node, String curIdx) {
        List<String> resultList = new ArrayList<>();
        log.debug("page_content中module对应的key值为：{}" + curIdx);
        JsonNode targetNode = node.get(curIdx);
        JsonNode listNode = targetNode.get(SELECT);
        Iterator<JsonNode> iterator = listNode.elements();
        while (iterator.hasNext()) {
            JsonNode next = iterator.next();
            resultList.add(next.asText());
        }
        return resultList;
    }

    /**
     * 反馈统计
     *
     * @param param 表单id
     * @return 统计数据返回，只有性别，下拉，选项三项
     */
    public FeedBackStatisticsVo feedBackStatistics(FormDetailParam param) {
        try {
            FeedBackStatisticsVo vo = db().select(fp.SUBMIT_NUM, fp.IS_FOREVER_VALID.as("validityPeriod"), fp.STATE.as("status")).from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(FeedBackStatisticsVo.class).orElse(new FeedBackStatisticsVo());
            Integer num = db().select(countDistinct(fsl.USER_ID)).from(fsl).where(fsl.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(Integer.class).orElse(0);
            vo.setParticipantsNum(num);

            String pageContent = db().select(fp.PAGE_CONTENT).from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(String.class).orElse("");
            JsonNode node = MAPPER.readTree(pageContent);

            List<FeedBackInnerVo> sexList = new ArrayList<>();
            List<FeedBackInnerVo> slideList = new ArrayList<>();
            List<FeedBackInnerVo> chooseList = new ArrayList<>();

            Iterator<JsonNode> iterator = node.elements();
            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                if (M_SEX.equals(next.get(MODULE_NAME).asText())) {
                    sexList = getSpecialList(next, sexList);
                }
                if (M_CHOOSE.equals(next.get(MODULE_NAME).asText())) {
                    chooseList = getSpecialList(next, chooseList);
                }
                if (M_SLIDE.equals(next.get(MODULE_NAME).asText())) {
                    slideList = getSpecialList(next, slideList);
                }
            }
            vo.setSexList(calPercentage(sexList, M_SEX, param.getPageId(), vo));
            vo.setSlideList(calPercentage(slideList, M_SLIDE, param.getPageId(), vo));
            vo.setChooseList(calPercentage(chooseList, M_CHOOSE, param.getPageId(), vo));
            return vo;
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    private List<FeedBackInnerVo> calPercentage(List<FeedBackInnerVo> target, String moduleName, Integer pageId, FeedBackStatisticsVo vo) {
        for (FeedBackInnerVo innerVo : target) {
            innerVo.setVotes(db().select(countDistinct(fsd.USER_ID)).from(fsd).where(fsd.PAGE_ID.eq(pageId)).and(fsd.MODULE_NAME.eq(moduleName)).and(fsd.MODULE_VALUE.eq(innerVo.getModuleValue())).fetchOptionalInto(Integer.class).orElse(0));
            innerVo.setPercentage(Double.valueOf(innerVo.getVotes()) / vo.getParticipantsNum());
        }
        return target;
    }

    private List<FeedBackInnerVo> getSpecialList(JsonNode next, List<FeedBackInnerVo> list) {
        JsonNode listNode = next.get(SELECT);
        if (listNode == null) {
            return new ArrayList<>();
        }
        Iterator<JsonNode> it = listNode.elements();
        int value = 1;
        while (it.hasNext()) {
            JsonNode type = it.next();
            list.add(new FeedBackInnerVo(String.valueOf(value++), type.asText()));
        }
        return list;
    }

    /**
     * 添加反馈信息
     *
     * @param param 反馈信息详情
     */
    public void addFeedBackInfo(FeedBackInfoParam param) {
        FormSubmitListRecord listRecord = new FormSubmitListRecord();
        listRecord.setPageId(param.getPageId());
        listRecord.setUserId(param.getUserId());
        db().transaction(configuration -> {
//            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> addFeedList(param, listRecord));
//            future.thenAccept((e) -> addFeedDetail(param, e));
            Integer submitId = addFeedList(param, listRecord);
            addFeedDetail(param, submitId);
            db().update(fp).set(fp.SUBMIT_NUM,fp.SUBMIT_NUM.add(1)).execute();
        });
    }

    /**
     * 添加反馈信息详情
     *
     * @param param    反馈信息详情
     * @param submitId 表单反馈提交id
     */
    private void addFeedDetail(FeedBackInfoParam param, Integer submitId) {
        List<FormSubmitDetailsRecord> records = new ArrayList<>();
        param.getDetailList().forEach((e) -> {
            FormSubmitDetailsRecord detailsRecord = new FormSubmitDetailsRecord();
            detailsRecord.setSubmitId(submitId);
            detailsRecord.setPageId(param.getPageId());
            detailsRecord.setUserId(param.getUserId());
            FieldsUtil.assignNotNull(e, detailsRecord);
            records.add(detailsRecord);
        });
        db().batchInsert(records).execute();
    }

    /**
     * 添加反馈信息列表
     *
     * @param param      反馈信息详情
     * @param listRecord 批量待插入数据集合
     * @return 自增主键生成的表单反馈提交id
     */
    private Integer addFeedList(FeedBackInfoParam param, FormSubmitListRecord listRecord) {
        Map<String, Object> uResult = db().select(u.USERNAME.as("nick_name"), u.WX_OPENID.as("open_id")).from(u).where(u.USER_ID.eq(param.getUserId())).fetchOptionalMap().orElse(new HashMap<>());
        listRecord.setNickName(uResult.get("nick_name").toString());
        listRecord.setOpenId(uResult.get("open_id").toString());
        Map<String, Object> fpResult = db().select(fp.SHOP_ID, fp.FORM_CFG).from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalMap().orElse(new HashMap<>());
        listRecord.setOpenId(fpResult.get("shop_id").toString());
        String formCfg = fpResult.get("form_cfg").toString();
        getCouponList(formCfg, listRecord);
        db().executeInsert(listRecord);
        return db().lastID().intValue();
    }

    /**
     * 解析优惠卷列表配置取到优惠卷活动id---coupon_id
     *
     * @param formCfg    优惠卷列表配置json串
     * @param listRecord 优惠券反馈列表
     */
    private void getCouponList(String formCfg, FormSubmitListRecord listRecord) {
        if (StringUtils.isBlank(formCfg)) {
            return;
        }
        try {
            JsonNode cfg = MAPPER.readTree(formCfg);
            listRecord.setSendScore(cfg.get(SEND_SCORE_NUM).intValue());
            Iterator<JsonNode> iterator = cfg.get(SEND_COUPON_LIST).elements();
            StringBuffer sBuffer = new StringBuffer();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                Integer couponId = node.get(COUPON_ID).asInt();
                sBuffer.append(getCouponSn(couponId, listRecord.getUserId()) + ",");
            }
            listRecord.setSendCoupons(sBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取优惠卷编号
     *
     * @param couponId 优惠券活动id
     * @param userId   用户id
     * @return 优惠券编号列表字符串
     */
    private String getCouponSn(Integer couponId, Integer userId) {
        String couponSn = couponGiveService.collectingCoupons(couponId, userId);
        log.debug("反馈信息提交后会给用户插入一条相应的领取优惠券记录并生成优惠券编号 [{}] 供后续使用", couponSn);
        String[] couponArray = db().select(cac.COUPON_SN).from(cac).where(cac.USER_ID.eq(userId)).and(cac.ACT_ID.eq(couponId)).fetchArray(cac.COUPON_SN, String.class);
        return String.join(",", couponArray);
    }

}
