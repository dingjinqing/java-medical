package com.vpu.mp.service.shop.market.form;

import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.FormPageRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.form.*;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.vpu.mp.db.shop.tables.Code.CODE;
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
        db().executeInsert(fpRecord);
    }

    /**
     * 更新表单信息
     *
     * @param param 表单信息
     */
    public void updateFormInfo(FormAddParam param) {
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);

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

    private SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> getFeedBackStep(FormFeedParam param) {
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
     * TODO 导出数量控制
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

    /**
     * 反馈信息详情
     *
     * @param param 表单id和用户id
     * @return 反馈信息详情列表
     */
    public List<FeedBackDetailVo> feedBackDetail(FeedBackDetailParam param) {
        List<FeedBackDetailVo> list = db().select(fsd.SUBMIT_ID, fsd.MODULE_NAME, fsd.MODULE_TYPE, fsd.MODULE_VALUE, fsd.CUR_IDX).from(fsd).where(fsd.PAGE_ID.eq(param.getPageId())).and(fsd.USER_ID.eq(param.getUserId())).fetchInto(FeedBackDetailVo.class);

        for (FeedBackDetailVo vo : list) {
            String moduleName = vo.getModuleName();
            if (!FormConstant.all.containsKey(moduleName)) {
                continue;
            }
            if (FormConstant.special.containsKey(moduleName)) {
                try {
                    int curIdx = vo.getCurIdx();
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

    private List<String> findModuleValue(JsonNode node, int curIdx) {
        List<String> resultList = new ArrayList<>();
        String nodeKey = "c_" + curIdx;
        log.debug("page_content中module对应的key值为：{}" + nodeKey);
        JsonNode targetNode = node.get(nodeKey);
        JsonNode listNode = targetNode.get(SELECT);
        Iterator<JsonNode> iterator = listNode.elements();
        while (iterator.hasNext()) {
            JsonNode next = iterator.next();
            resultList.add(next.toString());
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
            FeedBackStatisticsVo vo = db().select(fp.SUBMIT_NUM, fp.IS_FOREVER_VALID, fp.STATE).from(fp).where(fp.PAGE_ID.eq(param.getPageId())).fetchOptionalInto(FeedBackStatisticsVo.class).orElse(new FeedBackStatisticsVo());
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
                if (M_SEX.equals(next.get(MODULE_NAME).toString())) {
                    getSpecialList(next, sexList);
                }
                if (M_CHOOSE.equals(next.get(MODULE_NAME).toString())) {
                    getSpecialList(next, chooseList);
                }
                if (M_SLIDE.equals(next.get(MODULE_NAME).toString())) {
                    getSpecialList(next, slideList);
                }
            }
            vo.setSexList(CompletableFuture.supplyAsync(() -> calPercentage(sexList, M_SEX, param.getPageId(), vo)).get());
            vo.setSlideList(CompletableFuture.supplyAsync(() -> calPercentage(slideList, M_SLIDE, param.getPageId(), vo)).get());
            vo.setChooseList(CompletableFuture.supplyAsync(() -> calPercentage(chooseList, M_CHOOSE, param.getPageId(), vo)).get());
            return vo;
        } catch (IOException | InterruptedException | ExecutionException e) {
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

    private void getSpecialList(JsonNode next, List<FeedBackInnerVo> list) {
        JsonNode listNode = next.get(SELECT);
        Iterator<JsonNode> it = listNode.elements();
        int value = 1;
        while (it.hasNext()) {
            JsonNode type = it.next();
            list.add(new FeedBackInnerVo(String.valueOf(value++), type.toString()));
        }
    }

    /**
     * 获取图片，海报，二维码下载地址
     */
    public String imgDownloadUrl(ImgDownloadParam param) {
        return db().select(CODE.QRCODE_IMG).from(CODE).where(CODE.TYPE_URL.eq(param.getTypeUrl())).and(CODE.TYPE.eq(param.getType())).and(CODE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchAny(CODE.QRCODE_IMG);
    }
}
