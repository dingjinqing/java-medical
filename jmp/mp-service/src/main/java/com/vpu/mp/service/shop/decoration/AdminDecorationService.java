package com.vpu.mp.service.shop.decoration;

import com.UpYun;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.HttpsUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RegexUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.config.StorageConfig;
import com.vpu.mp.config.TxMapLBSConfig;
import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.image.ImageDefault;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.decorate.DecorationTemplatePojo;
import com.vpu.mp.service.pojo.saas.shop.version.VersionName;
import com.vpu.mp.service.pojo.shop.config.SuspendWindowConfig;
import com.vpu.mp.service.pojo.shop.decoration.*;
import com.vpu.mp.service.pojo.shop.decoration.module.*;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.decorate.PageCfgVo;
import com.vpu.mp.service.shop.config.SuspendWindowConfigService;
import com.vpu.mp.service.shop.image.QrCodeService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.vpu.mp.db.shop.tables.PageClassification.PAGE_CLASSIFICATION;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

/**
 * @author: 王兵兵
 * @create: 2020-01-09 15:09
 **/
@Service
public class AdminDecorationService extends ShopBaseService implements ImageDefault {
    @Autowired
    private DomainConfig domainConfig;
    @Autowired
    private QrCodeService qrCode;
    @Autowired
    private TxMapLBSConfig txMapLBSConfig;
    @Autowired
    private UpYunConfig upYunConfig;
    @Autowired
    private StorageConfig storageConfig;
    @Autowired
    private SuspendWindowConfigService suspendWindowConfigService;

    /**
     * 静态图API
     */
    public static final String QQ_MAP_API_STATICMAP_URL = "https://apis.map.qq.com/ws/staticmap/v2";

    /**
     * 验证格式
     * @param json
     * @return
     */
    private static final boolean validJson(String json){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取装修页面
     *
     * @param pageId
     * @return
     */
    public XcxCustomerPageRecord getPageById(Integer pageId) {
        return db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)));
    }

    /**
     * 获取装修页面数量
     *
     * @return
     */
    public int getPageCount() {
        Byte enabled = 1;
        return db().fetchCount(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ENABLED.eq(enabled));
    }

    /**
     * 装修页面列表
     *
     * @param param
     * @return
     */
    public PageResult<XcxCustomerPageVo> getPageList(XcxCustomerPageVo param) {
        if (getPageCount() == 0) {
            this.addDefaultPage();
        }
        SelectWhereStep<? extends Record> select = db()
            .select(XCX_CUSTOMER_PAGE.PAGE_ID, XCX_CUSTOMER_PAGE.PAGE_NAME, XCX_CUSTOMER_PAGE.CREATE_TIME,
                XCX_CUSTOMER_PAGE.PAGE_TYPE, XCX_CUSTOMER_PAGE.CAT_ID, PAGE_CLASSIFICATION.NAME)
            .from(XCX_CUSTOMER_PAGE
                .leftJoin(PAGE_CLASSIFICATION)
                .on(XCX_CUSTOMER_PAGE.CAT_ID.eq(PAGE_CLASSIFICATION.ID)));
        select = buildOptions(select, param);
        select.orderBy(XCX_CUSTOMER_PAGE.PAGE_TYPE.desc(), XCX_CUSTOMER_PAGE.CREATE_TIME.desc());
        return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), XcxCustomerPageVo.class);
    }

    /**
     * 查询条件
     *
     * @param select
     * @param param
     * @return
     */
    public SelectWhereStep<? extends Record> buildOptions(
        SelectWhereStep<? extends Record> select, XcxCustomerPageVo param) {
        Byte enabled = 1;
        select.where(XCX_CUSTOMER_PAGE.PAGE_ENABLED.eq(enabled));
        // 页面内容
        if (param.getPageName() != null) {
            select.where(XCX_CUSTOMER_PAGE.PAGE_NAME.contains(param.getPageName()));
        }

        // 页面分类
        if (param.getCatId() != null && param.getCatId() > 0) {
            select.where(XCX_CUSTOMER_PAGE.CAT_ID.eq(param.getCatId()));
        }

        return select;
    }

    /**
     * 添加默认装修页
     *
     * @return
     */
    public int addDefaultPage() {
        return db()
            .insertInto(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_NAME, XCX_CUSTOMER_PAGE.PAGE_TYPE,
                XCX_CUSTOMER_PAGE.PAGE_TPL_TYPE)
            .values("首页", (byte) 1, (byte) 3)
            .execute();
    }

    /**
     * 添加页面
     *
     * @param param
     * @return
     */
    public Integer addPage(XcxCustomerPageVo param) {
        if(validJson(param.getPageContent()) && validJson(param.getPagePublishContent())){
            XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);
            this.assign(param, record);
            record.insert();
            int pageId = record.getPageId();
            return pageId;
        }
        return 0;
    }

    /**
     * 设置首页(事务处理)
     *
     * @param param
     * @return
     */
    public boolean setIndex(PageIdParam param) {
        this.transaction(() -> {
            db().update(XCX_CUSTOMER_PAGE)
                .set(XCX_CUSTOMER_PAGE.PAGE_TYPE, (byte) 0)
                .where(XCX_CUSTOMER_PAGE.PAGE_TYPE.eq((byte) 1))
                .execute();
            db().update(XCX_CUSTOMER_PAGE)
                .set(XCX_CUSTOMER_PAGE.PAGE_TYPE, (byte) 1)
                .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((param.getPageId())))
                .execute();
        });
        return true;
    }

    /**
     * 编辑-获取装修页面数据
     *
     * @param param
     * @return
     */
    public PageVo getPageInfo(PageIdParam param) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        XcxCustomerPageRecord page = db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ID.eq(param.getPageId()));
        Map<String, Object> pageContent = processPageContentBeforeGet(page.getPageContent(),objectMapper);
        PageVo vo = new PageVo();
        vo.setPageId(page.getPageId());
        vo.setPageName(page.getPageName());
        vo.setCatId(page.getCatId());
        vo.setPageType(page.getPageType());
        vo.setPageTplType(page.getPageTplType());
        try {
            vo.setPageCfg((PageCfgVo) pageContent.get("page_cfg"));
            vo.setPageContent(objectMapper.writeValueAsString(pageContent));
            vo.setPagePublishContent(vo.getPageContent());
            return vo;
        } catch (IOException e) {
            logger().error("装修",e);
            return null;
        }
    }

    private Map<String, Object> processPageContentBeforeGet(String pageContent,ObjectMapper objectMapper){
        pageContent = StringUtils.isBlank(pageContent) ? "{}" : pageContent;
        Map<String, Object> result = new LinkedHashMap<>();

        try {
            JsonNode root = objectMapper.readTree(pageContent);
            Iterator<Map.Entry<String, JsonNode>> elements = root.fields();

            while (elements.hasNext()) {
                Map.Entry<String, JsonNode> node = elements.next();
                String key = node.getKey();
                Object element = this.processModuleForGet(objectMapper, node);
                result.put(key, element);
            }

        } catch (Exception e) {
            logger().error("装修转换错误:",e);
        }
        return result;
    }

    /**
     * 处理模块，一些模块需要要转换
     *
     * @param objectMapper
     * @param node
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public Object processModuleForGet(ObjectMapper objectMapper, Map.Entry<String, JsonNode> node)
        throws JsonParseException, JsonMappingException, IOException {
        if (node.getKey().startsWith("c_")) {
            String moduleName = node.getValue().get("module_name").asText();
            switch (moduleName) {
                case ModuleConstant.M_SCROLL_IMAGE:
                    ModuleScrollImage moduleScrollImage = objectMapper.readValue(node.getValue().toString(), ModuleScrollImage.class);
                    for (ModuleScrollImage.ImageItem imageItem : moduleScrollImage.getImgItems()) {
                        imageItem.setImageUrl(imageUrl(imageItem.getImageUrl()));
                    }
                    return moduleScrollImage;
                case ModuleConstant.M_IMAGE_GUIDE:
                    ModuleImageGuide moduleImageGuide = objectMapper.readValue(node.getValue().toString(), ModuleImageGuide.class);
                    for (ModuleImageGuide.NavItem navItem : moduleImageGuide.getNavGroup()) {
                        navItem.setNavSrc(imageUrl(navItem.getNavSrc()));
                    }
                    return moduleImageGuide;
                case ModuleConstant.M_IMAGE_ADVER:
                    ModuleImageAdver moduleImageAdver = objectMapper.readValue(node.getValue().toString(), ModuleImageAdver.class);
                    for (ModuleImageAdver.ImageAdItem item : moduleImageAdver.getImageList()){
                        item.setImage(imageUrl(item.getImage()));
                    }
                    return moduleImageAdver;
                case ModuleConstant.M_MAGIC_CUBE:
                    ModuleMagicCube moduleMagicCube = objectMapper.readValue(node.getValue().toString(), ModuleMagicCube.class);
                    for(ModuleMagicCube.BlockItem blockItem : moduleMagicCube.getData().values()){
                        blockItem.setImgUrl(imageUrl(blockItem.getImgUrl()));
                    }
                    return moduleMagicCube;
                case ModuleConstant.M_HOT_AREA:
                    ModuleHotArea moduleHotArea = objectMapper.readValue(node.getValue().toString(), ModuleHotArea.class);
                    moduleHotArea.getData().setBgImgUrl(imageUrl(moduleHotArea.getData().getBgImgUrl()));
                    return moduleHotArea;
                case ModuleConstant.M_TEXT_IMAGE:
                    ModuleTextImage moduleTextImage = objectMapper.readValue(node.getValue().toString(), ModuleTextImage.class);
                    moduleTextImage.setImgUrl(imageUrl(moduleTextImage.getImgUrl()));
                    return moduleTextImage;
                case ModuleConstant.M_TITLE:
                    ModuleTitle moduleTitle = objectMapper.readValue(node.getValue().toString(), ModuleTitle.class);
                    if(StringUtil.isNotEmpty(moduleTitle.getImgUrl())){
                        moduleTitle.setImgUrl(imageUrl(moduleTitle.getImgUrl()));
                    }
                    return moduleTitle;
                case ModuleConstant.M_VIDEO:
                    ModuleVideo moduleVideo = objectMapper.readValue(node.getValue().toString(), ModuleVideo.class);
                    if(StringUtil.isNotEmpty(moduleVideo.getVideoUrl())){
                        moduleVideo.setVideoUrl(domainConfig.videoUrl(moduleVideo.getVideoUrl()));
                        moduleVideo.setVideoImg(domainConfig.videoUrl(moduleVideo.getVideoImg()));
                    }
                    if(StringUtil.isNotEmpty(moduleVideo.getImgUrl())){
                        moduleVideo.setImgUrl(imageUrl(moduleVideo.getImgUrl()));
                    }
                    return moduleVideo;
                case ModuleConstant.M_MAP:
                    ModuleMap moduleMap = objectMapper.readValue(node.getValue().toString(), ModuleMap.class);
                    moduleMap.setImgPath(imageUrl(moduleMap.getImgPath()));
                    return moduleMap;
                case ModuleConstant.M_GOODS:
                    ModuleGoods moduleGoods = saas.getShopApp(getShopId()).mpDecoration.convertGoodsForModule(objectMapper, node, null);
                    if (moduleGoods.getOtherMessage().equals(0)) {
                        if (StringUtil.isNotEmpty(moduleGoods.getImgUrl()) || StringUtil.isNotEmpty(moduleGoods.getTitle())) {
                            moduleGoods.setGoodsModuleTitle((byte) 1);
                        } else {
                            moduleGoods.setGoodsModuleTitle((byte) 0);
                        }
                    }
                    if (StringUtil.isNotEmpty(moduleGoods.getImgUrl())) {
                        moduleGoods.setImgUrl(imageUrl(moduleGoods.getImgUrl()));
                    }
                    if (StringUtil.isNotEmpty(moduleGoods.getImgTitleUrl())) {
                        moduleGoods.setImgTitleUrl(imageUrl(moduleGoods.getImgTitleUrl()));
                    }
                    return moduleGoods;
                case ModuleConstant.M_GOODS_GROUP:
                    ModuleGoodsGroup moduleGoodsGroup = saas.getShopApp(getShopId()).mpDecoration.convertGoodsGroupForModule(objectMapper, node, null);
                    if (moduleGoodsGroup.getOtherMessage() == null && moduleGoodsGroup.getShowPrice() == 0) {
                        moduleGoodsGroup.setOtherMessage((byte) 0);
                        moduleGoodsGroup.setShowMarket((byte) 1);
                    }
                    return moduleGoodsGroup;
                case ModuleConstant.M_BARGAIN:
                    ModuleBargain moduleBargain = objectMapper.readValue(node.getValue().toString(), ModuleBargain.class);
                    moduleBargain = saas.getShopApp(getShopId()).bargain.getPageIndexBargain(moduleBargain);
                    return moduleBargain;
                case ModuleConstant.M_SECKILL:
                    ModuleSeckill moduleSecKill = objectMapper.readValue(node.getValue().toString(), ModuleSeckill.class);
                    moduleSecKill = saas.getShopApp(getShopId()).seckill.getPageIndexSeckill(moduleSecKill);
                    return moduleSecKill;
                case ModuleConstant.M_INTEGRAL:
                    ModuleIntegral moduleIntegral = objectMapper.readValue(node.getValue().toString(), ModuleIntegral.class);
                    moduleIntegral = saas.getShopApp(getShopId()).integralConvertService.getPageIndexIntegral(moduleIntegral);
                    return moduleIntegral;
                case ModuleConstant.M_CARD:
                    ModuleCard moduleCard = objectMapper.readValue(node.getValue().toString(), ModuleCard.class);
                   if(StringUtil.isNotEmpty(moduleCard.getBgImg())){
                       moduleCard.setBgImg(imageUrl(moduleCard.getBgImg()));
                   }
                    return moduleCard;
                case ModuleConstant.M_SHOP:
                    ModuleShop moduleShop = objectMapper.readValue(node.getValue().toString(), ModuleShop.class);
                    if(StringUtil.isNotEmpty(moduleShop.getShopBgPath())){
                        moduleShop.setShopBgPath(imageUrl(moduleShop.getShopBgPath()));
                    }
                    if(StringUtil.isNotEmpty(moduleShop.getBgUrl())){
                        moduleShop.setBgUrl(imageUrl(moduleShop.getBgUrl()));
                    }
                    return moduleShop;
                case ModuleConstant.M_GROUP_DRAW:
                    ModuleGroupDraw moduleGroupDraw = objectMapper.readValue(node.getValue().toString(), ModuleGroupDraw.class);
                    moduleGroupDraw = saas.getShopApp(getShopId()).groupDraw.getPageIndexGroupDraw(moduleGroupDraw);
                    return moduleGroupDraw;


                    /**
                     * TODO: 添加其他模块
                     */
                    /**
                     * TODO: 基于店铺等级的模块权限校验
                     */

                default:
            }
        }
        if("page_cfg".equals(node.getKey())){
            PageCfgVo pageCfg =  objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
            if(StringUtil.isNotEmpty(pageCfg.getPageBgImage())){
                pageCfg.setPageBgImage(imageUrl(pageCfg.getPageBgImage()));
            }
            if(StringUtil.isNotEmpty(pageCfg.getPictorial().getShareImgPath())){
                pageCfg.getPictorial().setShareImgPath(imageUrl(pageCfg.getPictorial().getShareImgPath()));
            }
            return pageCfg;
        }
        return objectMapper.readValue(node.getValue().toString(), Object.class);
    }

    /**
     * 获取页面分类信息
     *
     * @return
     */
    public List<PageClassificationVo> getPageCate() {
        List<PageClassificationVo> list = db().select(PAGE_CLASSIFICATION.ID, PAGE_CLASSIFICATION.NAME)
            .from(PAGE_CLASSIFICATION)
            .fetch().into(PageClassificationVo.class);
        return list;
    }

    /**
     * 删除装修页面
     *
     * @param param
     * @return
     */
    public int delXcxPage(PageClassificationVo param) {
        int result = db()
            .update(XCX_CUSTOMER_PAGE)
            .set(XCX_CUSTOMER_PAGE.PAGE_ENABLED, BaseConstant.NO)
            .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq(param.getPageId()))
            .execute();
        return result;
    }

    /**
     * 保存页面分类数据
     *
     * @param param
     * @param param
     * @return
     */
    public int setPageCate(PageClassificationVo param) {

        int result = db().update(XCX_CUSTOMER_PAGE)
            .set(XCX_CUSTOMER_PAGE.CAT_ID, param.getId())
            .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq(param.getPageId()))
            .execute();
        return result;
    }

    /**
     * 批量设置页面分类（事务处理）
     *
     * @param param
     * @return
     */
    public boolean batchSetPageCate(BatchSetPageCateParam param) {
        List<String> pageIds = Arrays.asList(param.getPageIds().split(","));
        this.transaction(() -> {
            for (String pageId : pageIds) {
                int setPageId = Integer.parseInt(pageId);
                db().update(XCX_CUSTOMER_PAGE)
                    .set(XCX_CUSTOMER_PAGE.CAT_ID, param.getId())
                    .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq(setPageId))
                    .execute();
            }
        });
        return true;
    }

    /**
     * 获取首页
     *
     * @return
     */
    public XcxCustomerPageRecord getIndex() {
        return db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_TYPE.eq((byte) 1));
    }

    /**
     * 复制已有页面
     *
     * @param pageId
     * @return
     */
    public Boolean copyDecoration(Integer pageId) {
        XcxCustomerPageRecord source = this.getPageById(pageId);
        XcxCustomerPageRecord page = db().newRecord(XCX_CUSTOMER_PAGE);
        page.setPageName(source.getPageName() + "+copy");
        page.setPageContent(source.getPageContent());
        page.setShopId(source.getShopId());
        page.setPageType((byte) 0);
        page.setPageEnabled(source.getPageEnabled());
        page.setPageTplType(source.getPageTplType());
        page.setPageContent(source.getPageContent());
        page.setPagePublishContent(source.getPagePublishContent());
        page.setPageState(source.getPageState());
        page.setCatId(source.getCatId());
        page.insert();
        return true;
    }

    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer pageId) {

        String pathParam = "page=" + pageId;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.INDEX, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.INDEX.getUrl());
        return vo;
    }

    /**
     * 保存页面，包含添加和更新
     *
     * @param page
     * @return
     */
    public int storePage(PageStoreParam page){
        try {
            //处理PageContent里的json数据，校验格式
            page.setPageContent(processPageContentBeforeSave(page.getPageContent()));
        }catch (IOException e){
            logger().error("装修页面保存格式错误：", e);
            return 0;
        }

        //记录页面变化
        recordPageChange(page);

        XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);
        record.setPageContent(page.getPageContent());
        record.setPageName(page.getPageName());
        record.setCatId(page.getCatId() == null ? 0 : page.getCatId());


        if(page.getPageState() == 3){
            //回退到当前已发布版本

            if(page.getPageId() != null && page.getPageId() > 0){
                XcxCustomerPageRecord oldRecord = this.getPageById(page.getPageId());
                record.setPageContent(oldRecord.getPagePublishContent());
            }
        }else if (page.getPageState() == 1) {
            //保存并发布

            record.setPageState(page.getPageState());
            record.setPagePublishContent(page.getPageContent());
        }

        //入库
        if(page.getPageId() != null && page.getPageId() > 0){
            record.setPageId(page.getPageId());
            if(record.update() > 0){
                return record.getPageId();
            }
        }else {
            if(record.insert() > 0){
                page.setPageId(record.getPageId());
                return record.getPageId();
            }
        }
        return 0;
    }

    /**
     * 预览
     * @param param
     * @return
     */
    public String getPreviewCode(PageStoreParam param){
        if(this.storePage(param) > 0){
            String pathParam="page_id="+param.getPageId();
            String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.INDEX, pathParam);
            return imageUrl;
        }
        return null;
    }

    /**
     * 处理装修的JSON，保存的域名、地图模块等
     *
     * @param pageContent
     * @return
     */
    protected String processPageContentBeforeSave(String pageContent) throws IOException {
        pageContent = StringUtils.isBlank(pageContent) ? "{}" : pageContent;
        Map<String, Object> result = new LinkedHashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode root = objectMapper.readTree(pageContent);
        Iterator<Map.Entry<String, JsonNode>> elements = root.fields();

        while (elements.hasNext()) {
            Map.Entry<String, JsonNode> node = elements.next();
            String key = node.getKey();
            Object element = this.confirmPageContent(objectMapper, node);
            result.put(key, element);
        }

        return objectMapper.writeValueAsString(result);
    }

    private Object confirmPageContent(ObjectMapper objectMapper, Map.Entry<String, JsonNode> node) throws IOException {
        if (node.getKey().startsWith("c_")) {
            String moduleName = node.getValue().get("module_name").asText();
            switch (moduleName) {
                case ModuleConstant.M_SCROLL_IMAGE:
                    ModuleScrollImage moduleScrollImage = objectMapper.readValue(node.getValue().toString(), ModuleScrollImage.class);
                    for (ModuleScrollImage.ImageItem imageItem : moduleScrollImage.getImgItems()) {
                        imageItem.setImageUrl(RegexUtil.getUri(imageItem.getImageUrl()));
                    }
                    return moduleScrollImage;
                case ModuleConstant.M_IMAGE_GUIDE:
                    ModuleImageGuide moduleImageGuide = objectMapper.readValue(node.getValue().toString(), ModuleImageGuide.class);
                    for (ModuleImageGuide.NavItem navItem : moduleImageGuide.getNavGroup()) {
                        navItem.setNavSrc(RegexUtil.getUri(navItem.getNavSrc()));
                    }
                    return moduleImageGuide;
                case ModuleConstant.M_IMAGE_ADVER:
                    ModuleImageAdver moduleImageAdver = objectMapper.readValue(node.getValue().toString(), ModuleImageAdver.class);
                    for (ModuleImageAdver.ImageAdItem item : moduleImageAdver.getImageList()){
                        item.setImage(RegexUtil.getUri(item.getImage()));
                    }
                    return moduleImageAdver;
                case ModuleConstant.M_MAGIC_CUBE:
                    ModuleMagicCube moduleMagicCube = objectMapper.readValue(node.getValue().toString(), ModuleMagicCube.class);
                    for(ModuleMagicCube.BlockItem blockItem : moduleMagicCube.getData().values()){
                        blockItem.setImgUrl(RegexUtil.getUri(blockItem.getImgUrl()));
                    }
                    return moduleMagicCube;
                case ModuleConstant.M_HOT_AREA:
                    ModuleHotArea moduleHotArea = objectMapper.readValue(node.getValue().toString(), ModuleHotArea.class);
                    moduleHotArea.getData().setBgImgUrl(RegexUtil.getUri(moduleHotArea.getData().getBgImgUrl()));
                    return moduleHotArea;
                case ModuleConstant.M_TEXT_IMAGE:
                    ModuleTextImage moduleTextImage = objectMapper.readValue(node.getValue().toString(), ModuleTextImage.class);
                    moduleTextImage.setImgUrl(RegexUtil.getUri(moduleTextImage.getImgUrl()));
                    return moduleTextImage;
                case ModuleConstant.M_TITLE:
                    ModuleTitle moduleTitle = objectMapper.readValue(node.getValue().toString(), ModuleTitle.class);
                    if(StringUtil.isNotEmpty(moduleTitle.getImgUrl())){
                        moduleTitle.setImgUrl(RegexUtil.getUri(moduleTitle.getImgUrl()));
                    }
                    return moduleTitle;
                case ModuleConstant.M_VIDEO:
                    checkAuth(VersionName.SUB_2_M_VIDEO);
                    ModuleVideo moduleVideo = objectMapper.readValue(node.getValue().toString(), ModuleVideo.class);
                    if(StringUtil.isNotEmpty(moduleVideo.getVideoUrl())) {
                        moduleVideo.setVideoUrl(RegexUtil.getUri(moduleVideo.getVideoUrl()));
                        moduleVideo.setVideoImg(RegexUtil.getUri(moduleVideo.getVideoImg()));
                    }
                    if(StringUtil.isNotEmpty(moduleVideo.getImgUrl())){
                        moduleVideo.setImgUrl(RegexUtil.getUri(moduleVideo.getImgUrl()));
                    }
                    return moduleVideo;
                case ModuleConstant.M_SHOP:
                    ModuleShop moduleShop = objectMapper.readValue(node.getValue().toString(), ModuleShop.class);
                    if(StringUtil.isNotEmpty(moduleShop.getShopBgPath())){
                        moduleShop.setShopBgPath(RegexUtil.getUri(moduleShop.getShopBgPath()));
                    }
                    if(StringUtil.isNotEmpty(moduleShop.getBgUrl())){
                        moduleShop.setBgUrl(RegexUtil.getUri(moduleShop.getBgUrl()));
                    }
                    return moduleShop;
                case ModuleConstant.M_MAP:
                    ModuleMap moduleMap = objectMapper.readValue(node.getValue().toString(), ModuleMap.class);
                    String imgPath = getStaticMapImg(moduleMap.getLatitude(),moduleMap.getLongitude());
                    moduleMap.setImgPath(imgPath);
                    return moduleMap;
                case ModuleConstant.M_CARD:
                	checkAuth(VersionName.SUB_2_M_MEMBER_CARD);
                    ModuleCard moduleCard = objectMapper.readValue(node.getValue().toString(), ModuleCard.class);
                    if(StringUtil.isNotEmpty(moduleCard.getBgImg())){
                        moduleCard.setBgImg(RegexUtil.getUri(moduleCard.getBgImg()));
                    }
                    return moduleCard;
                case ModuleConstant.M_GROUP_DRAW:
                	checkAuth(VersionName.SUB_2_M_GROUP_DRAW);
                    ModuleGroupDraw moduleGroupDraw = objectMapper.readValue(node.getValue().toString(), ModuleGroupDraw.class);
                    if(StringUtil.isNotEmpty(moduleGroupDraw.getModuleImg())){
                        moduleGroupDraw.setModuleImg(RegexUtil.getUri(moduleGroupDraw.getModuleImg()));
                    }
                    return moduleGroupDraw;
                case ModuleConstant.M_INTEGRAL:
                	checkAuth(VersionName.SUB_2_M_INTEGRAL_GOODS);
                    ModuleIntegral moduleIntegral = objectMapper.readValue(node.getValue().toString(), ModuleIntegral.class);
                    if(!moduleIntegral.getIntegralGoods().isEmpty()){
                        for(ModuleIntegral.IntegralGoods g : moduleIntegral.getIntegralGoods()){
                            if(StringUtil.isNotEmpty(g.getGoodsImg())){
                                g.setGoodsImg(RegexUtil.getUri(g.getGoodsImg()));
                            }
                        }
                    }
                    return moduleIntegral;
                case ModuleConstant.M_COUPON:
                	checkAuth(VersionName.SUB_2_M_VOUCHER);
                	return objectMapper.readValue(node.getValue().toString(), Object.class);

                case ModuleConstant.M_BARGAIN:
                    checkAuth(VersionName.SUB_2_M_BARGAIN);
                    return objectMapper.readValue(node.getValue().toString(), Object.class);

                case ModuleConstant.M_SECKILL:
                    checkAuth(VersionName.SUB_2_M_SECKILL_GOODS);
                    return objectMapper.readValue(node.getValue().toString(), Object.class);

                case ModuleConstant.M_PIN_INTEGRATION:
                    checkAuth(VersionName.SUB_2_M_PIN_INTEGRATION);
                    return objectMapper.readValue(node.getValue().toString(), Object.class);

                case ModuleConstant.M_GOODS:
                    ModuleGoods moduleGoods = objectMapper.readValue(node.getValue().toString(), ModuleGoods.class);
                    if (StringUtil.isNotEmpty(moduleGoods.getImgUrl())) {
                        moduleGoods.setImgUrl(RegexUtil.getUri(moduleGoods.getImgUrl()));
                    }
                    if (StringUtil.isNotEmpty(moduleGoods.getImgTitleUrl())) {
                        moduleGoods.setImgTitleUrl(RegexUtil.getUri(moduleGoods.getImgTitleUrl()));
                    }
                    return moduleGoods;
                //TODO 其他保存前需要处理的模块
                default:

            }
        }
        if("page_cfg".equals(node.getKey())){
            PageCfgVo pageCfg =  objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
            if(StringUtil.isNotEmpty(pageCfg.getPageBgImage())){
                pageCfg.setPageBgImage(RegexUtil.getUri(pageCfg.getPageBgImage()));
            }
            if(StringUtil.isNotEmpty(pageCfg.getPictorial().getShareImgPath())){
                pageCfg.getPictorial().setShareImgPath(RegexUtil.getUri(pageCfg.getPictorial().getShareImgPath()));
            }
            return pageCfg;
        }
        return objectMapper.readValue(node.getValue().toString(), Object.class);
    }

    /**
     * 店铺权限的一些校验
     * @param moduName
     */
	private void checkAuth(String moduName) {
		String[] auth1 = saas.shop.version.verifyVerPurview(getShopId(), moduName);
		logger().info("{}权限：{}",moduName,auth1[0]);
		Assert.isTrue(auth1[0].equals("true"), moduName+"have no auth");
	}

    /**
     * 记录页面变化部分
     *
     * @param page
     */
    protected void recordPageChange(PageStoreParam page) {
        //TODO 记录页面变化部分
    }

    /**
     * 根据页面名称模糊查询满足条件的页面ID
     *
     * @param sourcePage
     * @return
     */
    public List<Integer> getIdByName(String sourcePage) {
        List<Integer> idList = db().select(XCX_CUSTOMER_PAGE.PAGE_ID).from(XCX_CUSTOMER_PAGE)
            .where(XCX_CUSTOMER_PAGE.PAGE_NAME.like(this.likeValue(sourcePage))).fetchInto(Integer.class);
        return idList;
    }

    protected String getStaticMapImg(String latitude,String longitude){
        Map<String, Object> param = new HashMap<>(8);
        param.put("size","375*150");
        param.put("key",txMapLBSConfig.getKey());
        param.put("center",latitude + "," + longitude);
        param.put("zoom",16);
        param.put("format","png");
        param.put("scale",2);
        param.put("maptype","roadmap");
        param.put("markers","size:large|color:blue|label:A|" + latitude + "," + longitude);

        String relativePath = "upload/" + this.getShopId() + "/map/" + DateUtils.dateFormat(DateUtils.DATE_FORMAT_SHORT) + "/";
        String path = fullPath(relativePath);
        String fileName = "map_" + DateUtils.dateFormat(DateUtils.DATE_FORMAT_FULL_NO_UNDERLINE) + "_" + Math.round((Math.random()+1) * 1000) + ".png";
        mkdir(path);
        HttpsUtils.get(QQ_MAP_API_STATICMAP_URL,param,true,path,fileName);

        try {
            String fullFilePath = path + fileName;
            File file = new File(fullFilePath);
            uploadToUpYun(relativePath + fileName,file);
            //上传完成删除本地图片
            //file.delete();
        } catch (Exception e) {
            logger().error("图片加载错误",e);
            return "";
        }
        return relativePath + fileName;
    }

    @Override
    public String imageUrl(String relativePath) {
        return domainConfig.imageUrl(relativePath);
    }

    @Override
    public String fullPath(String relativePath) {
        return storageConfig.storagePath(relativePath);
    }

    @Override
    public UpYun getUpYunClient() {
        return new UpYun(upYunConfig.getServer(), upYunConfig.getName(), upYunConfig.getPassword());
    }

    /**
     * 将页面模板数据处理成前端可以直接用的
     * @param templateId
     * @return
     */
    public DecorationTemplatePojo covertTemplate(int templateId){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        DecorationTemplateRecord decorationTemplateRecord = saas.shop.decoration.getRow(templateId);
        String pageContent = StringUtils.isBlank(decorationTemplateRecord.getPageContent()) ? "{}" : decorationTemplateRecord.getPageContent();
        Map<String, Object> result = new LinkedHashMap<>();

        try {
            JsonNode root = objectMapper.readTree(pageContent);
            Iterator<Map.Entry<String, JsonNode>> elements = root.fields();

            while (elements.hasNext()) {
                Map.Entry<String, JsonNode> node = elements.next();
                String key = node.getKey();
                Object element = this.processTemplateModuleForGet(objectMapper, node);
                result.put(key, element);
            }
        } catch (Exception e) {
            logger().error("装修模板转换错误:",e);
        }

        try {
            DecorationTemplatePojo vo = new DecorationTemplatePojo();
            vo.setPageName(decorationTemplateRecord.getPageName());
            vo.setPageId(decorationTemplateRecord.getPageId());
            vo.setPageImg(decorationTemplateRecord.getPageImg());
            vo.setPageContent(objectMapper.writeValueAsString(result));
            return vo;
        } catch (IOException e) {
            logger().error("装修模板",e);
            return null;
        }
    }

    private Object processTemplateModuleForGet(ObjectMapper objectMapper, Map.Entry<String, JsonNode> node) throws IOException, ClassNotFoundException {
        if (node.getKey().startsWith("c_")) {
            String moduleName = node.getValue().get("module_name").asText();

            String moduleClassName = Util.underlineToHump(moduleName.split("_",2)[1]);
            moduleClassName = moduleClassName.substring(0, 1).toUpperCase() + moduleClassName.substring(1);
            moduleClassName = "com.vpu.mp.service.pojo.shop.decoration.module." + "Module" + moduleClassName;
            Class m = Class.forName(moduleClassName);
            return objectMapper.readValue(node.getValue().toString(), m);
        }
        if ("page_cfg".equals(node.getKey())) {
            PageCfgVo pageCfg = objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
            if (pageCfg.getPictorial() != null && StringUtil.isNotEmpty(pageCfg.getPictorial().getShareImgPath())) {
                pageCfg.getPictorial().setShareImgPath(imageUrl(pageCfg.getPictorial().getShareImgPath()));
            }
            return pageCfg;
        }
        return objectMapper.readValue(node.getValue().toString(), Object.class);
    }

    /**
     * 保存悬浮窗草稿
     *
     * @param param
     */
    public boolean setSuspendWindowConfigDraft(SuspendWindowConfig param) {
        if (StringUtil.isNotBlank(param.getMainBefore())) {
            param.setMainBefore(RegexUtil.getUri(param.getMainBefore()));
        }
        if (StringUtil.isNotBlank(param.getMainAfter())) {
            param.setMainAfter(RegexUtil.getUri(param.getMainAfter()));
        }
        for (SuspendWindowConfig.ChildIcon c : param.getChildrenArr()) {
            if (StringUtil.isNotBlank(c.getImg())) {
                c.setImg(RegexUtil.getUri(c.getImg()));
            }
        }

        suspendWindowConfigService.setSuspendCfgDraft(param);
        return true;
    }

    /**
     * 保存悬浮窗
     *
     * @param param
     */
    public boolean setSuspendWindowConfig(SuspendWindowConfig param) {
        if (StringUtil.isNotBlank(param.getMainBefore())) {
            param.setMainBefore(RegexUtil.getUri(param.getMainBefore()));
        }
        if (StringUtil.isNotBlank(param.getMainAfter())) {
            param.setMainAfter(RegexUtil.getUri(param.getMainAfter()));
        }
        for (SuspendWindowConfig.ChildIcon c : param.getChildrenArr()) {
            if (StringUtil.isNotBlank(c.getImg())) {
                c.setImg(RegexUtil.getUri(c.getImg()));
            }
        }
        suspendWindowConfigService.setSuspendCfgDraft(param);
        suspendWindowConfigService.setSuspendCfg(param);
        return true;
    }

    /**
     * 取悬浮窗草稿
     *
     * @return
     */
    public SuspendWindowConfig getSuspendWindowConfigDraft() {
        SuspendWindowConfig res = suspendWindowConfigService.getSuspendCfgDraft();
        if (StringUtil.isNotBlank(res.getMainBefore())) {
            res.setMainBefore(domainConfig.imageUrl(res.getMainBefore()));
        }
        if (StringUtil.isNotBlank(res.getMainAfter())) {
            res.setMainAfter(domainConfig.imageUrl(res.getMainAfter()));
        }
        for (SuspendWindowConfig.ChildIcon c : res.getChildrenArr()) {
            if (StringUtil.isNotBlank(c.getImg())) {
                c.setImg(domainConfig.imageUrl(c.getImg()));
            }
        }
        for (Integer pageId : res.getPageIds()) {
            if (pageId > 1) {
                XcxCustomerPageRecord record = getPageById(pageId);
                if (record == null || record.getPageEnabled().equals(BaseConstant.NO)) {
                    res.getPageIds().remove(pageId);
                }
            }
        }
        return res;
    }
}
