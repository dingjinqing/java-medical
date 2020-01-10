package com.vpu.mp.service.shop.decoration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.*;
import com.vpu.mp.service.pojo.shop.decoration.module.*;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.decorate.PageCfgVo;
import com.vpu.mp.service.shop.image.QrCodeService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.vpu.mp.db.shop.tables.PageClassification.PAGE_CLASSIFICATION;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

/**
 * @author: 王兵兵
 * @create: 2020-01-09 15:09
 **/
@Service
public class AdminDecorationService extends ShopBaseService {
    @Autowired
    private DomainConfig domainConfig;
    @Autowired
    private QrCodeService qrCode;

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
        System.out.println(param);
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
                        imageItem.setImageUrl(domainConfig.imageUrl(imageItem.getImageUrl()));
                    }
                    return moduleScrollImage;
                case ModuleConstant.M_IMAGE_GUIDE:
                    ModuleImageGuide moduleImageGuide = objectMapper.readValue(node.getValue().toString(), ModuleImageGuide.class);
                    for (ModuleImageGuide.NavItem navItem : moduleImageGuide.getNavGroup()) {
                        navItem.setNavSrc(domainConfig.imageUrl(navItem.getNavSrc()));
                    }
                    return moduleImageGuide;
                case ModuleConstant.M_IMAGE_ADVER:
                    ModuleImageAdver moduleImageAdver = objectMapper.readValue(node.getValue().toString(), ModuleImageAdver.class);
                    for (ModuleImageAdver.ImageAdItem item : moduleImageAdver.getImageList()){
                        item.setImage(domainConfig.imageUrl(item.getImage()));
                    }
                    return moduleImageAdver;
                case ModuleConstant.M_MAGIC_CUBE:
                    ModuleMagicCube moduleMagicCube = objectMapper.readValue(node.getValue().toString(), ModuleMagicCube.class);
                    for(ModuleMagicCube.BlockItem blockItem : moduleMagicCube.getData().values()){
                        blockItem.setImgUrl(domainConfig.imageUrl(blockItem.getImgUrl()));
                    }
                    return moduleMagicCube;
                case ModuleConstant.M_HOT_AREA:
                    ModuleHotArea moduleHotArea = objectMapper.readValue(node.getValue().toString(), ModuleHotArea.class);
                    moduleHotArea.getData().setBgImgUrl(domainConfig.imageUrl(moduleHotArea.getData().getBgImgUrl()));
                    return moduleHotArea;
                case ModuleConstant.M_TEXT_IMAGE:
                    ModuleTextImage moduleTextImage = objectMapper.readValue(node.getValue().toString(), ModuleTextImage.class);
                    moduleTextImage.setImgUrl(domainConfig.imageUrl(moduleTextImage.getImgUrl()));
                    return moduleTextImage;
                case ModuleConstant.M_TITLE:
                    ModuleTitle moduleTitle = objectMapper.readValue(node.getValue().toString(), ModuleTitle.class);
                    if(StringUtil.isNotEmpty(moduleTitle.getImgUrl())){
                        moduleTitle.setImgUrl(domainConfig.imageUrl(moduleTitle.getImgUrl()));
                    }
                    return moduleTitle;
                case ModuleConstant.M_VIDEO:
                    ModuleVideo moduleVideo = objectMapper.readValue(node.getValue().toString(), ModuleVideo.class);
                    if(StringUtil.isNotEmpty(moduleVideo.getVideoUrl())){
                        moduleVideo.setVideoUrl(domainConfig.videoUrl(moduleVideo.getVideoUrl()));
                        moduleVideo.setVideoImg(domainConfig.videoUrl(moduleVideo.getVideoImg()));
                    }else if(StringUtil.isNotEmpty(moduleVideo.getImgUrl())){
                        moduleVideo.setImgUrl(domainConfig.imageUrl(moduleVideo.getImgUrl()));
                    }
                    return moduleVideo;
                case ModuleConstant.M_MAP:
                    ModuleMap moduleMap = objectMapper.readValue(node.getValue().toString(), ModuleMap.class);
                    moduleMap.setImgPath(domainConfig.imageUrl(moduleMap.getImgPath()));
                    return moduleMap;
                case ModuleConstant.M_GOODS:
                    ModuleGoods moduleGoods = objectMapper.readValue(node.getValue().toString(), ModuleGoods.class);
                    if(moduleGoods.getOtherMessage().equals(0)){
                        if(StringUtil.isNotEmpty(moduleGoods.getImgUrl()) || StringUtil.isNotEmpty(moduleGoods.getTitle())){
                            moduleGoods.setGoodsModuleTitle((byte)1);
                        }else{
                            moduleGoods.setGoodsModuleTitle((byte)0);
                        }
                    }
                    return moduleGoods;
                case ModuleConstant.M_GOODS_GROUP:
                    ModuleGoodsGroup moduleGoodsGroup = objectMapper.readValue(node.getValue().toString(), ModuleGoodsGroup.class);
                    return moduleGoodsGroup;

                    /**
                     * TODO: 添加其他模块
                     */
            }
        }
        if(node.getKey().equals("page_cfg")){
            PageCfgVo pageCfg =  objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
            if(StringUtil.isNotEmpty(pageCfg.getPictorial().getShareImgPath())){
                pageCfg.getPictorial().setShareImgPath(domainConfig.imageUrl(pageCfg.getPictorial().getShareImgPath()));
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
            .set(XCX_CUSTOMER_PAGE.PAGE_ENABLED, (byte) 0)
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
        vo.setPagePath(QrCodeTypeEnum.INDEX.getPathUrl(pathParam));
        return vo;
    }

    /**
     * 保存页面，包含添加和更新
     *
     * @param page
     * @return
     */
    public boolean storePage(PageStoreParam page){
        try {
            //处理PageContent里的json数据，校验格式
            page.setPageContent(processPageContentBeforeSave(page.getPageContent()));
        }catch (IOException e){
            logger().error("装修页面保存格式错误：", e);
            return false;
        }

        recordPageChange(page);
        XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);

        record.setPageContent(page.getPageContent());
        record.setPageName(page.getPageName());
        record.setCatId(page.getCatId() == null ? 0 : page.getCatId());

        if (page.getPageState() == 1) {
            //保存并发布
            record.setPageState(page.getPageState());
            record.setPagePublishContent(page.getPageContent());
        }
        if (page.getPageId() != null) {
            XcxCustomerPageRecord oldRecord = this.getPageById(page.getPageId());
            record.setPageId((page.getPageId()));
            if (page.getPageState() == 3) {
                //回退到当前已发布版本
                record.setPageContent(oldRecord.getPagePublishContent());
            }
            return record.update() > 0;
        }

        return record.insert() > 0;
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
                        imageItem.setImageUrl(new URL(imageItem.getImageUrl()).getPath());
                    }
                    return moduleScrollImage;
                case ModuleConstant.M_IMAGE_GUIDE:
                    ModuleImageGuide moduleImageGuide = objectMapper.readValue(node.getValue().toString(), ModuleImageGuide.class);
                    for (ModuleImageGuide.NavItem navItem : moduleImageGuide.getNavGroup()) {
                        navItem.setNavSrc(new URL(navItem.getNavSrc()).getPath());
                    }
                    return moduleImageGuide;
                case ModuleConstant.M_IMAGE_ADVER:
                    ModuleImageAdver moduleImageAdver = objectMapper.readValue(node.getValue().toString(), ModuleImageAdver.class);
                    for (ModuleImageAdver.ImageAdItem item : moduleImageAdver.getImageList()){
                        item.setImage(new URL(item.getImage()).getPath());
                    }
                    return moduleImageAdver;
                case ModuleConstant.M_MAGIC_CUBE:
                    ModuleMagicCube moduleMagicCube = objectMapper.readValue(node.getValue().toString(), ModuleMagicCube.class);
                    for(ModuleMagicCube.BlockItem blockItem : moduleMagicCube.getData().values()){
                        blockItem.setImgUrl(new URL(blockItem.getImgUrl()).getPath());
                    }
                    return moduleMagicCube;
                case ModuleConstant.M_HOT_AREA:
                    ModuleHotArea moduleHotArea = objectMapper.readValue(node.getValue().toString(), ModuleHotArea.class);
                    moduleHotArea.getData().setBgImgUrl(new URL(moduleHotArea.getData().getBgImgUrl()).getPath());
                    return moduleHotArea;
                case ModuleConstant.M_TEXT_IMAGE:
                    ModuleTextImage moduleTextImage = objectMapper.readValue(node.getValue().toString(), ModuleTextImage.class);
                    moduleTextImage.setImgUrl(new URL(moduleTextImage.getImgUrl()).getPath());
                    return moduleTextImage;
                case ModuleConstant.M_TITLE:
                    ModuleTitle moduleTitle = objectMapper.readValue(node.getValue().toString(), ModuleTitle.class);
                    if(StringUtil.isNotEmpty(moduleTitle.getImgUrl())){
                        moduleTitle.setImgUrl(new URL(moduleTitle.getImgUrl()).getPath());
                    }
                    return moduleTitle;
                case ModuleConstant.M_VIDEO:
                    ModuleVideo moduleVideo = objectMapper.readValue(node.getValue().toString(), ModuleVideo.class);
                    if(StringUtil.isNotEmpty(moduleVideo.getVideoUrl())){
                        moduleVideo.setVideoUrl(new URL(moduleVideo.getVideoUrl()).getPath());
                        moduleVideo.setVideoImg(new URL(moduleVideo.getVideoImg()).getPath());
                    }else if(StringUtil.isNotEmpty(moduleVideo.getImgUrl())){
                        moduleVideo.setImgUrl(new URL(moduleVideo.getImgUrl()).getPath());
                    }
                    return moduleVideo;
                case ModuleConstant.M_MAP:
                    ModuleMap moduleMap = objectMapper.readValue(node.getValue().toString(), ModuleMap.class);
                    //TODO 抓取地图图片
                    return moduleMap;

                //TODO 其他保存前需要处理的模块

            }
        }
        if(node.getKey().equals("page_cfg")){
            PageCfgVo pageCfg =  objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
            if(StringUtil.isNotEmpty(pageCfg.getPictorial().getShareImgPath())){
                pageCfg.getPictorial().setShareImgPath(new URL(pageCfg.getPictorial().getShareImgPath()).getPath());
            }
            return pageCfg;
        }
        return objectMapper.readValue(node.getValue().toString(), Object.class);
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
}
