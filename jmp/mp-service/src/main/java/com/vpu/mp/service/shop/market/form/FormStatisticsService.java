package com.vpu.mp.service.shop.market.form;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upyun.UpException;
import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.exception.Assert;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.form.*;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.pojo.wxapp.share.FormPictorialRule;
import com.vpu.mp.service.pojo.wxapp.share.PictorialFormImgPx;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.image.postertraits.PictorialService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record6;
import org.jooq.Record8;
import org.jooq.SelectConditionStep;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

import static com.vpu.mp.db.shop.tables.Code.CODE;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.*;
import static com.vpu.mp.service.shop.order.store.StoreOrderService.HUNDRED;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.jooq.impl.DSL.*;

/**
 * @author liufei
 * @date 2019/8/7
 */
@Slf4j
@Service
public class FormStatisticsService extends ShopBaseService {
    @Autowired
    QrCodeService qrCodeService;
    @Autowired
    CouponGiveService couponGiveService;
    @Autowired
    PictorialService pictorialService;
    @Autowired
    private UserService user;
    @Autowired
    private ImageService imageService;
    /**
     * FORM_PAGE表单删除状态值，删除状态页面不展示
     */
    private static final Byte FP_DEL_STATUS = 3;
    /**
     * 分享二维码页面显示图片路径
     */
    private static final String PARAM = "page_id=";
    public static final String PLUS = "+";
    public static final String COPY_TEXT = "副本";

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
        SelectConditionStep<Record8<Integer, String, Timestamp, Integer, Byte, Byte, Timestamp, Timestamp>> conditionStep = db()
            .select(fp.PAGE_ID, fp.PAGE_NAME, fp.CREATE_TIME, fp.SUBMIT_NUM, fp.STATE.as("status")
                , fp.IS_FOREVER_VALID.as("validityPeriod"), fp.START_TIME, fp.END_TIME)
            .from(fp).where(trueCondition());
        if (param.getStatus() != null) {
            conditionStep = conditionStep.and(fp.STATE.eq(param.getStatus()));
        }
        if (StringUtils.isNoneBlank(param.getPageName())) {
            conditionStep = conditionStep.and(fp.PAGE_NAME.like(this.likeValue(param.getPageName())));
        }
        if (param.getStartTime() != null) {
            conditionStep = conditionStep.and(fp.CREATE_TIME.greaterOrEqual(new Timestamp(param.getStartTime().getTime())));
        }
        if (param.getEndTime() != null) {
            conditionStep = conditionStep.and(fp.CREATE_TIME.lessOrEqual(new Timestamp(param.getEndTime().getTime())));
        }
        conditionStep = conditionStep.and(fp.STATE.notEqual(FP_DEL_STATUS));
        return getPageResult(conditionStep.orderBy(fp.CREATE_TIME.desc()), param.getCurrentPage(), param.getPageRows(), FormInfoVo.class);
    }

    /**
     * 查看表单信息详情
     * 已发布的表单只可查看，不可编辑
     *
     * @param param 表单id
     * @return formpage表单详细信息
     */
    public FormDetailVo getFormDetailInfo(FormDetailParam param) {
        return db().selectFrom(fp)
            .where(fp.PAGE_ID.eq(param.getPageId()))
            .fetchOptionalInto(FormDetailVo.class)
            .orElseThrow(() -> new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST
                , String.join(StringUtils.SPACE, "Form", param.getPageId().toString())));
    }

    /**
     * 添加表单信息
     *
     * @param param 表单信息
     */
    public void addFormInfo(FormAddParam param) {
        FormPageRecord fpRecord = new FormPageRecord();
        FieldsUtil.assignNotNull(param, fpRecord);
        //设置初始反馈数量（表单实际反馈数量）为0（默认为0，0代表不限制），form_cfg中的get_times字段为总反馈数量限制
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
        if (db().fetchExists(fp, fp.PAGE_ID.eq(param.getPageId()))) {
            db().executeUpdate(fpRecord);
        } else {
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST
                , String.join(StringUtils.SPACE, "Form", param.getPageId().toString()));
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
        vo.setPagePath(QrCodeTypeEnum.FORM.getPathUrl(pathParam));
        return vo;
    }

    public ShareQrCodeVo getQrCode(int pageId) {
        String pathParam = PARAM + pageId;
        String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.FORM, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.FORM.getPathUrl(pathParam));
        return vo;
    }

    public void getFormPictorialCode(int pageId) {
        CodeRecord record = db().select().from(CODE)
            .where(CODE.TYPE.eq((short) 100))
            .and(CODE.PARAM_ID.eq(String.valueOf(pageId)))
            .fetchOneInto(CODE);

        // 获取表单海报图片路径
        Tuple2<Integer, String> pictorial = generateFormPictorial(pageId, 0);
    }

    /**
     * Generate form pictorial tuple 2.生成表单海报图片
     *
     * @param pageId the page id
     * @param userId the user id
     * @return the tuple 2 v1:：是否可编辑， v2：海报图片路径
     */
    public Tuple2<Integer, String> generateFormPictorial(int pageId, int userId) {
        // 获取表单信息
        FormPageRecord record = getFormRecord(pageId);
        String bgImg = getValueFromFormCfgByKey(record.getFormCfg(), BG_IMG);
        bgImg = StringUtils.isBlank(bgImg) ? FORM_DEFAULT_BG_IMG : bgImg;
        // 构建海报标识规则
        FormPictorialRule rule = FormPictorialRule.builder().page_name(record.getPageName()).bg_img(StringUtils.isBlank(bgImg) ? FORM_DEFAULT_BG_IMG : bgImg).build();
        // 判断是否需要重新生成表单海报
        PictorialRecord pictorialRecord = pictorialService.getPictorialFromDb(INTEGER_ZERO, pageId, (byte) 4);
        if (pictorialService.isNeedNewPictorial(Util.toJson(rule), pictorialRecord)) {
            log.debug("不需要重新生成表单海报，直接返回db中海报路径");
            return new Tuple2<>(0, pictorialRecord.getPath());
        }
        try {
            // 获取用户头像
            UserInfo userInfo = user.getUserInfo(userId);
            BufferedImage userAvator = ImageIO.read(new URL(imageService.getImgFullUrl(userInfo.getUserAvatar())));
            // 获取分享二维码
            ShareQrCodeVo qrCode = getQrCode(pageId);
            BufferedImage qrCodImg = ImageIO.read(new URL(imageService.getImgFullUrl(qrCode.getImageUrl())));
            // 背景图
            BufferedImage bgImgBuf = ImageIO.read(new URL(imageService.getImgFullUrl(bgImg)));
            // 创建海报图片
            BufferedImage pictorialImg = pictorialService.createFormPictorialBgImage(userAvator, qrCodImg, bgImgBuf, new PictorialFormImgPx());
            // 获取海报图片路径
            Tuple2<String, String> path = pictorialService.getImgDir(4, pictorialService.getImgFileName(String.valueOf(pageId), String.valueOf(0), String.valueOf(4)));
            // 将待分享图片上传到U盘云，并在数据库缓存记录
            pictorialService.uploadToUpanYun(pictorialImg, path.v1(), rule, pageId, null, INTEGER_ZERO);
            return new Tuple2<>(1, path.v1());
        } catch (IOException | UpException e) {
            log.error("表单海报图片创建失败：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
    }

    // 从表单配置json串中获取元素value值
    private String getValueFromFormCfgByKey(String cfg, String key) {
        if (StringUtils.isBlank(cfg)) {
            log.info("表单配置信息为空");
            return StringUtils.EMPTY;
        }
        try {
            JsonNode node = MAPPER.readTree(cfg);
            JsonNode value = node.get(key);
            if (Objects.isNull(value)) {
                log.info("表单配置信息中未找到{}元素", key);
                return StringUtils.EMPTY;
            }
            return value.asText();
        } catch (IOException e) {
            log.debug("表单配置信息反序列化失败：{}", ExceptionUtils.getStackTrace(e));
            return StringUtils.EMPTY;
        }
    }

    /**
     * Gets form record.获取表单记录
     *
     * @param pageId the page id
     * @return the form record
     */
    public FormPageRecord getFormRecord(int pageId) {
        FormPageRecord record = db().selectFrom(fp).where(fp.PAGE_ID.eq(pageId)).fetchOneInto(fp);
        Assert.notNull(record, JsonResultCode.CODE_DATA_NOT_EXIST, Assert.join(FORM_CHAR, pageId));
        return record;
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
        vo.setPageName(String.join(PLUS, vo.getPageName(), COPY_TEXT));
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
        SelectConditionStep<Record6<Integer, Integer, Integer, String, Timestamp, String>> conditionStep = db()
            .select(fsl.SUBMIT_ID, fsl.PAGE_ID, fsl.USER_ID, fsl.NICK_NAME, fsl.CREATE_TIME, u.MOBILE)
            .from(fsl).leftJoin(u).on(fsl.USER_ID.eq(u.USER_ID))
            .where(fsl.PAGE_ID.eq(param.getPageId()));
        if (StringUtils.isNoneBlank(param.getNickName())) {
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
        int pageId = param.getPageId();
        List<FeedBackDetailVo> list = db().select(fsd.SUBMIT_ID
            , fsd.MODULE_NAME, fsd.MODULE_TYPE, fsd.MODULE_VALUE
            , fsd.CUR_IDX.as("curIdx"))
            .from(fsd)
            .where(fsd.SUBMIT_ID.eq(param.getSubmitId()))
            .and(fsd.USER_ID.eq(param.getUserId()))
            .fetchInto(FeedBackDetailVo.class);

        list.stream().filter((e) -> ALL.containsKey(e.getModuleName()))
            .filter((e) -> SPECIAL.containsKey(e.getModuleName()))
            .forEach((e) -> e.setModuleValueList(findModuleValue(pageId, e.getCurIdx())));
        list.stream().filter((e) -> e.getModuleName().equals(M_UPLOAD_VIDEO)).forEach(e->e.setModuleValue(convertModuleVideo(e.getModuleValue())));

        return list;
    }

    private String getPageContent(int pageId) {
        return db().select(fp.PAGE_CONTENT)
            .from(fp).where(fp.PAGE_ID.eq(pageId))
            .fetchOneInto(String.class);
    }

    private String convertModuleVideo(String video) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ModuleUploadVideo moduleUploadVideo = null;
        try {
            moduleUploadVideo = objectMapper.readValue(video, ModuleUploadVideo.class);
            moduleUploadVideo.setVideoSrc(imageService.imageUrl(moduleUploadVideo.getVideoSrc()));
            moduleUploadVideo.setVideoImgSrc(imageService.imageUrl(moduleUploadVideo.getVideoImgSrc()));
            return objectMapper.writeValueAsString(moduleUploadVideo);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> findModuleValue(int pageId, String curIdx) {
        List<String> resultList = new ArrayList<>();
        String pageContent = getPageContent(pageId);
        log.debug("表单[{}]的页面内容为：{}", pageId, pageContent);
        try {
            JsonNode node = MAPPER.readTree(pageContent);
            JsonNode targetNode = node.get(curIdx);
            if (Objects.isNull(targetNode)) {
                log.error("表单[{}]页面内容缺失：缺少curIdx={}", pageId, curIdx);
                return resultList;
            }
            JsonNode listNode = targetNode.get(SELECT);
            if (Objects.isNull(listNode)) {
                log.error("表单[{}]页面内容缺失：元素curIdx={}内缺少{}元素", pageId, curIdx, SELECT);
                return resultList;
            }
            Iterator<JsonNode> iterator = listNode.elements();
            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                resultList.add(next.asText());
            }
        } catch (IOException e) {
            log.error("表单[{}]的页面内容反序列化失败！{}", pageId, ExceptionUtils.getStackTrace(e));
            throw new BusinessException(JsonResultCode.CODE_FAIL);
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
        int pageId = param.getPageId();
        FeedBackStatisticsVo vo = getFormInfo(pageId).fetchOneInto(FeedBackStatisticsVo.class);
        Assert.notNull(vo, JsonResultCode.CODE_DATA_NOT_EXIST, Assert.join("Form", pageId));

        vo.setParticipantsNum(getFormFeedUserNum(pageId));

        vo.setSexList(getFeedStatisticData(pageId, M_SEX));
        vo.setChooseList(getFeedStatisticData(pageId, M_CHOOSE));
        vo.setSlideList(getFeedStatisticData(pageId, M_SLIDE));

        vo.setSexTotal(sumVotes(vo.getSexList()));
        vo.setChooseTotal(sumVotes(vo.getChooseList()));
        vo.setSlideTotal(sumVotes(vo.getSlideList()));

        calPercentage(vo.getSexTotal(), vo.getSexList());
        calPercentage(vo.getChooseTotal(), vo.getChooseList());
        calPercentage(vo.getSlideTotal(), vo.getSlideList());

        return vo;
    }

    private void calPercentage(int total, List<FeedBackInnerVo> list) {
        list.forEach(e -> e.setPercentage(getIntPercentage(total, e.getVotes())));
    }

    private BigDecimal getIntPercentage(int total, int value) {
        return BigDecimal.valueOf(value).divide(BigDecimal.valueOf(total), 4, RoundingMode.HALF_UP)
            .multiply(HUNDRED);
    }

    private int sumVotes(List<FeedBackInnerVo> list) {
        return list.stream().map(FeedBackInnerVo::getVotes).reduce(Integer::sum).orElse(INTEGER_ZERO);
    }

    public List<FeedBackInnerVo> getFeedStatisticData(int pageId, String moduleName) {
        return db().select(min(fsd.MODULE_NAME).as("moduleName")
            , min(fsd.MODULE_TYPE).as("moduleType")
            , min(fsd.MODULE_VALUE).as("moduleValue")
            , count(fsd.MODULE_VALUE).as("votes"))
            .from(fsd)
            .where(fsd.PAGE_ID.eq(pageId)).and(fsd.MODULE_NAME.eq(moduleName))
            .groupBy(fsd.MODULE_VALUE).fetchInto(FeedBackInnerVo.class);
    }

    /**
     * Gets form feed user num.获取表单反馈用户数
     *
     * @param pageId the page id
     * @return the form feed user num
     */
    public int getFormFeedUserNum(int pageId) {
        return db().select(countDistinct(fsl.USER_ID))
            .from(fsl).where(fsl.PAGE_ID.eq(pageId))
            .fetchOptionalInto(Integer.class).orElse(INTEGER_ZERO);
    }

    /**
     * Gets form info.获取表单信息
     *
     * @param pageId the page id
     * @return the form info
     */
    public SelectConditionStep<FormPageRecord> getFormInfo(int pageId) {
        return db().selectFrom(fp).where(fp.PAGE_ID.eq(pageId));
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
