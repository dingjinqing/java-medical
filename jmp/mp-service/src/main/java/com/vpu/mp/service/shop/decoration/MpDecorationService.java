package com.vpu.mp.service.shop.decoration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.decoration.module.*;
import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;
import com.vpu.mp.service.pojo.wxapp.config.ShareConfig;
import com.vpu.mp.service.pojo.wxapp.coupon.CouponPageDecorationVo;
import com.vpu.mp.service.pojo.wxapp.coupon.ShopCollectInfo;
import com.vpu.mp.service.pojo.wxapp.decorate.PageCfgVo;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageModuleParam;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageParam;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.member.card.MemberCardPageDecorationVo;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.user.user.UserService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

/**
 * @author lixinguo
 */

@Service
public class MpDecorationService extends ShopBaseService {

    @Autowired
    protected ConfigService config;

    @Autowired
    protected UserService user;

    @Autowired
    protected MemberService member;

    @Autowired
    protected GoodsMpService goodsMpService;


    @Autowired
    private DomainConfig domainConfig;

    public int setPageCatId(Integer pageId, Integer catId) {
        return db().update(XCX_CUSTOMER_PAGE)
            .set(XCX_CUSTOMER_PAGE.CAT_ID, catId)
            .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)))
            .execute();
    }

    public int removeRow(Integer pageId) {
        return db().deleteFrom(XCX_CUSTOMER_PAGE)
            .where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)))
            .execute();
    }

    /**
     * 得到一个空页面
     *
     * @return
     */
    public XcxCustomerPageRecord getEmptyPage() {
        XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);
        record.setPageContent("{}");
        return record;
    }

    /**
     * 过滤页面内容
     *
     * @param pageContent
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, Object>> filterPageContent(String pageContent) {
        if (pageContent == null) {
            return null;
        }
        Map<String, Map<String, Object>> result = Util.parseJson(pageContent, Map.class);
        for (Map.Entry<String, Map<String, Object>> entry : result.entrySet()) {
            Map<String, Object> v = entry.getValue();
            filterActivityModules(v);
            filterImageModules(v);
        }
        return result;
    }

    protected void filterActivityModules(Map<String, Object> v) {

    }

    protected void filterImageModules(Map<String, Object> v) {

    }

    /**
     * 得到店铺版本模块
     *
     * @return
     */
    public Map<String, Integer> getVersionModules() {
        VersionConfig config = saas().shop.version.mergeVersion(this.getShopId());
        List<String> sub2 = config.mainConfig.sub2;
        Map<String, Integer> moduleMap = new HashMap<String, Integer>(8);
        String[] modules = {"m_member_card", "m_voucher", "m_bargain", "m_video",
            "m_integral_goods", "m_seckill_goods", "m_group_draw", "m_pin_integration"};
        for (String module : modules) {
            moduleMap.put(module, sub2.contains(module) ? 1 : 0);
        }
        return moduleMap;
    }

    /**
     * 克隆系统模板
     *
     * @param templateId
     * @return
     */
    public XcxCustomerPageRecord cloneTemplate(Integer templateId) {
        DecorationTemplateRecord record = saas.shop.decoration.getRow(templateId);
        XcxCustomerPageRecord page = db().newRecord(XCX_CUSTOMER_PAGE);
        page.setPageContent(record.getPageContent());
        page.insert();
        return page;
    }

    /**
     * 获取分类下页面个数
     *
     * @param catId
     * @return
     */
    public int getPageCount(Integer catId) {
        return db().fetchCount(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.CAT_ID.eq(catId));
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
     * 获取装修页面
     *
     * @param pageId
     * @return
     */
    public XcxCustomerPageRecord getPageById(Integer pageId) {
        return db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)));
    }

    /**
     * 得到前端装修页面信息
     *
     * @param param
     * @return
     */
    public WxAppPageVo getPageInfo(WxAppPageParam param) {
        XcxCustomerPageRecord record = null;
        Integer pageId = param.getPageId();
        String pageContent;
        if (pageId == null || pageId == 0) {
            record = this.getIndex();
            pageId = record.getPageId();
            pageContent = param.getSceneId() != null && param.getSceneId() > 0 ? record.getPageContent()
                : record.getPagePublishContent();

        } else {
            record = this.getPageById(pageId);
            pageContent = record.getPagePublishContent();
        }

        ShopRecord shop = saas.shop.getShopById(this.getShopId());
        UserRecord userRecord = user.getUserByUserId(param.getUserId());

        Map<String, Object> pageInfo = convertPageContent(pageContent, userRecord);
        WxAppPageVo page = new WxAppPageVo();
        page.setPageInfo(pageInfo);
        page.setIsFirstPage(record.getPageType());
        page.setPageId(pageId);
        page.setSceneId(param.getSceneId());
        page.setPageName(record.getPageName());
        page.setPageType(record.getPageType());
//		this.setCollectInfo(page.getCollectInfo(), shop, userRecord);
        this.setShareConfig(page.getShareInfo());
        return page;
    }


    /**
     * 设置用户收藏有礼信息
     *
     * @param collectInfo
     * @param shop
     */
    protected void setCollectInfo(ShopCollectInfo collectInfo, ShopRecord shop, UserRecord userRecord) {
        CollectGiftParam collect = config.collectGiftConfigService.collectGiftConfig();
        collectInfo.setShopName(shop.getShopName());
        collectInfo.setLookCollectTime(userRecord.getLookCollectTime());
        collectInfo.setGetCollectGift(userRecord.getGetCollectGift());
        FieldsUtil.assignNotNull(collect, collectInfo);
    }

    /**
     * 设置店铺分享配置
     *
     * @param shareConfig
     */
    protected void setShareConfig(ShareConfig shareConfig) {
        ShopShareConfig shopShareConfig = config.shopCommonConfigService.getShareConfig();
        if (shopShareConfig != null) {
            FieldsUtil.assignNotNull(shopShareConfig, shareConfig);
        }
        shareConfig.setShareTitle(shareConfig.getShareDoc());
    }

    /**
     * 转换装修页面内容
     *
     * @param pageContent
     * @return
     */
    protected Map<String, Object> convertPageContent(String pageContent, UserRecord user) {
        return convertPageContent(pageContent, null, user);
    }

    /**
     * 转换装修页面内容
     *
     * @param pageContent
     * @param keyIdx
     * @param user
     * @return
     */
    protected Map<String, Object> convertPageContent(String pageContent, String keyIdx, UserRecord user) {
        pageContent = StringUtils.isBlank(pageContent) ? "{}" : pageContent;
        Map<String, Object> result = new LinkedHashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode root = objectMapper.readTree(pageContent);
            Iterator<Entry<String, JsonNode>> elements = root.fields();

            while (elements.hasNext()) {
                Entry<String, JsonNode> node = elements.next();
                String key = node.getKey();
                if (keyIdx == null || key.equals(keyIdx)) {
                    Object element = this.convertModule(objectMapper, node, user);
                    result.put(key, element);
                }
            }
        } catch (Exception e) {
            logger().error("装修转换错误:",e);
        }
        return result;
    }

    /**
     * 转换模块，一些模块需要要转换，得到当前上数据
     *
     * @param objectMapper
     * @param node
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public Object convertModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user)
        throws JsonParseException, JsonMappingException, IOException {
        if (node.getKey().startsWith("c_")) {
            String moduleName = node.getValue().get("module_name").asText();
            switch (moduleName) {
                case ModuleConstant.M_GOODS_GROUP:
                    return this.convertGoodsGroupForIndex(objectMapper, node, user);
                case ModuleConstant.M_GOODS:
                    return this.convertGoodsForIndex(objectMapper, node, user);
                case ModuleConstant.M_COUPON:
                    return this.convertCouponForIndex(objectMapper, node, user);
                case ModuleConstant.M_CARD:
                    return this.convertCardForIndex(objectMapper, node, user);
                case ModuleConstant.M_BARGAIN:
                    return this.convertBargainForIndex(objectMapper, node, user);
                case ModuleConstant.M_SECKILL:
                    return this.convertSeckillForIndex(objectMapper, node, user);
                case ModuleConstant.M_IMAGE_ADVER:
                    return this.convertImageAdverForIndex(objectMapper, node, user);
                case ModuleConstant.M_PIN_INTEGRATION:
                    return this.convertGroupIntegrationForIndex(objectMapper, node, user);
                case ModuleConstant.M_GROUP_DRAW:
                    return this.convertGroupDrawForIndex(objectMapper, node, user);
                case ModuleConstant.M_SCROLL_IMAGE:
                    return this.convertScrollImageForIndex(objectMapper, node, user);
                case ModuleConstant.M_VIDEO:
                    return this.convertVideoForIndex(objectMapper, node, user);
                case ModuleConstant.M_IMAGE_GUIDE:
                    return this.convertImageGuideForIndex(objectMapper, node, user);
                case ModuleConstant.M_MAGIC_CUBE:
                    return this.convertMagicCubeForIndex(objectMapper, node, user);
                case ModuleConstant.M_HOT_AREA:
                    return this.convertHotAreaForIndex(objectMapper, node, user);
                case ModuleConstant.M_TEXT_IMAGE:
                    return this.convertTextImageForIndex(objectMapper, node, user);
                case ModuleConstant.M_TITLE:
                    return this.convertTitleForIndex(objectMapper, node, user);
                case ModuleConstant.M_MAP:
                    return this.convertMapForIndex(objectMapper, node, user);
                /**
                 * TODO: 添加其他模块，一些不需要转换的模块，可以走最后默认的转换。
                 */
            }
        }
        if(node.getKey().equals("page_cfg")){
            return this.convertPageCfgIndex(objectMapper, node, user);
        }
        return objectMapper.readValue(node.getValue().toString(), Object.class);
    }


    /**
     * 转换商品分组模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    public ModuleGoodsGroup convertGoodsGroupForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGoodsGroup element = objectMapper.readValue(node.getValue().toString(), ModuleGoodsGroup.class);
        // TODO: 转换实时信息
        return element;
    }

    /**
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGoods convertGoodsForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGoods moduleGoods = objectMapper.readValue(node.getValue().toString(), ModuleGoods.class);
        moduleGoods.setNeedRequest(true);
        return moduleGoods;
    }

    /**
     * 优惠券需要setNeedRequest
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleCoupon convertCouponForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleCoupon moduleCoupon = objectMapper.readValue(node.getValue().toString(), ModuleCoupon.class);
        moduleCoupon.setNeedRequest(true);
        return moduleCoupon;
    }

    /**
     * 会员卡需要setNeedRequest
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleCard convertCardForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleCard moduleCard = objectMapper.readValue(node.getValue().toString(), ModuleCard.class);
        moduleCard.setNeedRequest(true);
        return moduleCard;
    }

    /**
     * 砍价需要setNeedRequest
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleBargain convertBargainForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleBargain moduleBargain = objectMapper.readValue(node.getValue().toString(), ModuleBargain.class);
        moduleBargain.setNeedRequest(true);
        return moduleBargain;
    }

    /**
     * 秒杀需要setNeedRequest
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleSecKill convertSeckillForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleSecKill moduleSecKill = objectMapper.readValue(node.getValue().toString(), ModuleSecKill.class);
        moduleSecKill.setNeedRequest(true);
        return moduleSecKill;
    }

    /**
     * 图片广告模块处理
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleImageAdver convertImageAdverForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleImageAdver moduleImageAdver = objectMapper.readValue(node.getValue().toString(), ModuleImageAdver.class);
        boolean isNewUser = saas.getShopApp(getShopId()).readOrder.orderInfo.isNewUser(user.getUserId());
        Iterator<ModuleImageAdver.ImageAdItem> it = moduleImageAdver.getImageList().iterator();
        while (it.hasNext()){
            ModuleImageAdver.ImageAdItem img = it.next();
            if(img.getCanShow() == 1 && !isNewUser){
               it.remove();
            }
            img.setImage(domainConfig.imageUrl(img.getImage()));
        }
        return moduleImageAdver;
    }

    /**
     * 轮播图模块处理
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleScrollImage convertScrollImageForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleScrollImage moduleScrollImage = objectMapper.readValue(node.getValue().toString(), ModuleScrollImage.class);
        boolean isNewUser = saas.getShopApp(getShopId()).readOrder.orderInfo.isNewUser(user.getUserId());
        Iterator<ModuleScrollImage.ImageItem> it = moduleScrollImage.getImgItems().iterator();
        while (it.hasNext()){
            ModuleScrollImage.ImageItem img = it.next();
            if(img.getCanShow() == 1 && !isNewUser){
                it.remove();
            }
            img.setImageUrl(domainConfig.imageUrl(img.getImageUrl()));
        }
        return moduleScrollImage;
    }

    /**
     * 视频模块处理
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleVideo convertVideoForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleVideo moduleVideo = objectMapper.readValue(node.getValue().toString(), ModuleVideo.class);
        moduleVideo.setVideoUrl(domainConfig.videoUrl(moduleVideo.getVideoUrl()));
        if(StringUtil.isNotEmpty(moduleVideo.getVideoImg())){
            moduleVideo.setVideoImg(domainConfig.videoUrl(moduleVideo.getVideoImg()));
        }
        if(StringUtil.isNotEmpty(moduleVideo.getImgUrl())){
            moduleVideo.setImgUrl(domainConfig.imageUrl(moduleVideo.getImgUrl()));
        }
        return moduleVideo;
    }

    /**
     * 图片导航模块处理
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleImageGuide convertImageGuideForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleImageGuide moduleImageGuide = objectMapper.readValue(node.getValue().toString(), ModuleImageGuide.class);
        moduleImageGuide.getNavGroup().forEach(navItem -> {
            navItem.setNavSrc(domainConfig.imageUrl(navItem.getNavSrc()));
        });
        return moduleImageGuide;
    }

    /**
     * 魔方多图模块处理
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleMagicCube convertMagicCubeForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleMagicCube moduleMagicCube = objectMapper.readValue(node.getValue().toString(), ModuleMagicCube.class);
        moduleMagicCube.getData().forEach((s, blockItem) -> {
            blockItem.setImgUrl(domainConfig.imageUrl(blockItem.getImgUrl()));
        });
        return moduleMagicCube;
    }

    /**
     * 热区模块处理
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleHotArea convertHotAreaForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleHotArea moduleHotArea = objectMapper.readValue(node.getValue().toString(), ModuleHotArea.class);
        moduleHotArea.getData().setBgImgUrl(domainConfig.imageUrl(moduleHotArea.getData().getBgImgUrl()));
        return moduleHotArea;
    }

    /**
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleTextImage convertTextImageForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleTextImage moduleTextImage = objectMapper.readValue(node.getValue().toString(), ModuleTextImage.class);
        moduleTextImage.setImgUrl(domainConfig.imageUrl(moduleTextImage.getImgUrl()));
        return moduleTextImage;
    }

    /**
     * 标题模块
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleTitle convertTitleForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleTitle moduleTitle = objectMapper.readValue(node.getValue().toString(), ModuleTitle.class);
        if(StringUtil.isNotEmpty(moduleTitle.getImgUrl())){
            moduleTitle.setImgUrl(domainConfig.imageUrl(moduleTitle.getImgUrl()));
        }
        return moduleTitle;
    }

    /**
     * 地图模块
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleMap convertMapForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleMap moduleMap = objectMapper.readValue(node.getValue().toString(), ModuleMap.class);
        if(StringUtil.isNotEmpty(moduleMap.getImgPath())){
            moduleMap.setImgPath(domainConfig.imageUrl(moduleMap.getImgPath()));
        }
        return moduleMap;
    }

    /**
     * 瓜分积分
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGroupIntegration convertGroupIntegrationForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGroupIntegration moduleGroupIntegration = objectMapper.readValue(node.getValue().toString(), ModuleGroupIntegration.class);
        moduleGroupIntegration.setNeedRequest(true);
        return moduleGroupIntegration;
    }

    /**
     * 拼团抽奖
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGroupDraw convertGroupDrawForIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGroupDraw moduleGroupDraw = objectMapper.readValue(node.getValue().toString(), ModuleGroupDraw.class);
        moduleGroupDraw.setNeedRequest(true);
        return moduleGroupDraw;
    }

    /**
     * page_cfg模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private PageCfgVo convertPageCfgIndex(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        return objectMapper.readValue(node.getValue().toString(), PageCfgVo.class);
    }

    /**
     * 获取指定装修模块数据
     *
     * @param param 请求模块参数 {@link com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageModuleParam}
     * @return 对应模块的数据内容
     */
    public Object getPageModuleInfo(WxAppPageModuleParam param) {
        UserRecord userRecord = user.getUserByUserId(param.getUserId());
        XcxCustomerPageRecord pageRecord;
        if (param.getPageId() == null || param.getPageId() == 0) {
            pageRecord = this.getIndex();
        } else {
            pageRecord = this.getPageById(param.getPageId());
        }

        String pageContent = param.getSceneId() != null && param.getSceneId() > 0 ? pageRecord.getPageContent() : pageRecord.getPagePublishContent();
        DistributionParam distributionCfg = config.distributionCfg.getDistributionCfg();
        // 是否是分销员
        boolean isDistributor = 1 == userRecord.getIsDistributor();

        if (distributionCfg != null && DistributionConfigService.ENABLE_STATUS.equals(distributionCfg.getStatus()) && isDistributor) {
            pageContent = pageContent.replace("pages/distribution/distribution", "pages/distributionspread/distributionspread");
        }
        Object o = getDetailDecoratePageModule(pageContent, param.getModuleIndex(), userRecord);

        return o;
    }

    /**
     * @param pageContent
     * @param keyIdx
     * @param user
     */
    private Object getDetailDecoratePageModule(String pageContent, String keyIdx, UserRecord user) {
        pageContent = StringUtils.isBlank(pageContent) ? "{}" : pageContent;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode root = objectMapper.readTree(pageContent);
            Iterator<Entry<String, JsonNode>> elements = root.fields();

            while (elements.hasNext()) {
                Entry<String, JsonNode> node = elements.next();
                String key = node.getKey();
                if (key.equals(keyIdx) && key.startsWith("c_")) {
                    String moduleName = node.getValue().get("module_name").asText();
                    switch (moduleName) {
                        case ModuleConstant.M_GOODS:
                            return this.convertGoodsForModule(objectMapper, node, user);
                        case ModuleConstant.M_COUPON:
                            return this.convertCouponForModule(objectMapper, node, user);
                        case ModuleConstant.M_CARD:
                            return this.convertMemberCardForModule(objectMapper, node, user);
                        case ModuleConstant.M_BARGAIN:
                            return this.convertBargainForModule(objectMapper, node, user);
                        case ModuleConstant.M_SECKILL:
                            return this.convertSeckillForModule(objectMapper, node, user);
                        case ModuleConstant.M_PIN_INTEGRATION:
                            return  this.convertPinIntegrationForModule(objectMapper, node, user);
                        case ModuleConstant.M_GROUP_DRAW:
                            return  this.convertGroupDrawForModule(objectMapper, node, user);
                        //TODO case
                    }
                }
            }
        } catch (Exception e) {
            logger().error("小程序首页装修模块内容转换错误,pageContent:{}", pageContent);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGoods convertGoodsForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGoods moduleGoods = objectMapper.readValue(node.getValue().toString(), ModuleGoods.class);
        Integer userId = user.getUserId();
        GoodsListMpParam param = new GoodsListMpParam();
        param.setRecommendType(moduleGoods.getRecommendType());
        if (moduleGoods.getGoodsItems() == null) {
            param.setGoodsItems(new ArrayList<>());
        } else {
            List<Integer> ids = moduleGoods.getGoodsItems().stream().map(ModuleGoods.PhpPointGoodsConverter::getGoodsId).collect(Collectors.toList());
            param.setGoodsItems(ids);
        }
        param.setKeywords(moduleGoods.getKeywords());
        param.setMinPrice(moduleGoods.getMinPrice());
        param.setMaxPrice(moduleGoods.getMaxPrice());
        param.setGoodsArea(moduleGoods.getGoodsArea());
        param.setGoodsType(moduleGoods.getGoodsType());
        param.setSortType(moduleGoods.getSortType());
        param.setGoodsNum(moduleGoods.getGoodsNum());
        param.setFromPage(EsGoodsConstant.GOODS_LIST_PAGE);
        // 转换实时信息
        List<? extends GoodsListMpVo> pageIndexGoodsList = goodsMpService.getPageIndexGoodsList(param, userId);
        moduleGoods.setGoodsListData(pageIndexGoodsList);

        return moduleGoods;
    }

    /**
     * 优惠券模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private List<CouponPageDecorationVo> convertCouponForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleCoupon moduleCoupon = objectMapper.readValue(node.getValue().toString(), ModuleCoupon.class);
        Integer userId = user.getUserId();

        // 转换实时信息
        return saas.getShopApp(getShopId()).mpCoupon.getPageIndexCouponList(moduleCoupon, userId);
    }

    /**
     * 会员卡模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private MemberCardPageDecorationVo convertMemberCardForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleCard moduleCard = objectMapper.readValue(node.getValue().toString(), ModuleCard.class);
        Integer userId = user.getUserId();

        // 转换实时信息
        return member.card.getPageIndexMemberCard(moduleCard.getCardId(), userId);
    }

    /**
     * 砍价模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleBargain convertBargainForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleBargain moduleBargain = objectMapper.readValue(node.getValue().toString(), ModuleBargain.class);

        // 转换实时信息
        return saas.getShopApp(getShopId()).bargain.getPageIndexBargain(moduleBargain);
    }

    /**
     * 秒杀模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleSecKill convertSeckillForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleSecKill moduleSecKill = objectMapper.readValue(node.getValue().toString(), ModuleSecKill.class);

        // 转换实时信息
        return saas.getShopApp(getShopId()).seckill.getPageIndexSeckill(moduleSecKill);
    }

    /**
     * 瓜分积分模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGroupIntegration convertPinIntegrationForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGroupIntegration moduleGroupIntegration = objectMapper.readValue(node.getValue().toString(), ModuleGroupIntegration.class);

        // 转换实时信息
        return saas.getShopApp(getShopId()).groupIntegration.getPageIndexGroupIntegration(moduleGroupIntegration,user.getUserId());
    }

    /**
     * 拼团抽奖模块
     *
     * @param objectMapper
     * @param node
     * @param user
     * @return
     * @throws IOException
     */
    private ModuleGroupDraw convertGroupDrawForModule(ObjectMapper objectMapper, Entry<String, JsonNode> node, UserRecord user) throws IOException {
        ModuleGroupDraw ModuleGroupDraw = objectMapper.readValue(node.getValue().toString(), ModuleGroupDraw.class);

        // 转换实时信息
        return saas.getShopApp(getShopId()).groupDraw.getPageIndexGroupDraw(ModuleGroupDraw);
    }

}
