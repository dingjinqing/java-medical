package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsDetailMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsDetailMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpVo;
import com.vpu.mp.service.shop.activity.factory.GoodsDetailMpProcessorFactory;
import com.vpu.mp.service.shop.activity.factory.GoodsListMpProcessorFactory;
import com.vpu.mp.service.shop.activity.factory.ProcessorFactoryBuilder;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修内商品相关
 */
@Service
public class GoodsMpService extends ShopBaseService {

    @Autowired
    public GoodsBrandSortMpService goodsBrandSortMp;

    @Autowired
    ImageService imageService;
    @Autowired
    ConfigService configService;
    @Autowired
    GoodsLabelMpService goodsLabelMpService;

    @Autowired
    ProcessorFactoryBuilder processorFactoryBuilder;

    @Autowired
    protected UpYunConfig upYunConfig;

    /**
     * 装修页面 商品列表模块中获取配置后的商品集合数据
     * @param param  装修页面配置的商品获取过滤条件
     * @param userId
     * @return 对应的商品集合信息
     */
    public List<GoodsListMpVo> getPageIndexGoodsList(GoodsListMpParam param, Integer userId) {
        // 手动推荐展示但是未指定商品数据
        if (0 != param.getRecommendType()&&(param.getGoodsItems() == null || param.getGoodsItems().size() == 0)) {
            return new ArrayList<>();
        }
        Condition condition =buildPageIndexCondition(param);

        List<ActivityGoodsListCapsule> goodsListCapsules;
        // 自动推荐拼接排序条件
        if (0 != param.getRecommendType()) {
            goodsListCapsules = findActivityGoodsListCapsulesDao(condition,null,null,null,param.getGoodsItems());
        } else {
            List<SortField<?>> orderFields = new ArrayList<>();
            if (GoodsListMpParam.SALE_NUM_SORT.equals(param.getSortType())) {
                orderFields.add(GOODS.GOODS_SALE_NUM.desc());
            } else if (GoodsListMpParam.SHOP_PRICE_SORT.equals(param.getSortType())) {
                orderFields.add(GOODS.SHOP_PRICE.asc());
            } else {
                orderFields.add(GOODS.GOODS_ID.desc());
            }
            goodsListCapsules = findActivityGoodsListCapsulesDao(condition,orderFields,null,param.getGoodsNum(),null);
        }
        disposeGoodsList(goodsListCapsules,userId);

        List<GoodsListMpVo> goodsListMpVos = convertGoodsCapsuleTGoodsListMpVo(goodsListCapsules);

        return goodsListMpVos;
    }

    /**
     * 创建自动推荐商品过滤条件
     * @param param 过滤参数
     * @return 拼接后的条件
     */
    private Condition buildPageIndexCondition(GoodsListMpParam param) {
        // 获取在售商品且商品数量大于0
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));

        // 是否展示售罄
        Byte soldOutGoods = configService.shopCommonConfigService.getSoldOutGoods();
        if (soldOutGoods == 1) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }
        if (0 != param.getRecommendType()) {
            condition = condition.and(GOODS.GOODS_ID.in(param.getGoodsItems()));
            return condition;
        }

        if (!StringUtils.isBlank(param.getKeywords())) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeywords())));
        }

        if (param.getMinPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.gt(param.getMinPrice()));
        }
        if (param.getMaxPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.lt(param.getMaxPrice()));
        }

        // TODO: PHP 处理category参数字段，目前阶段未使用到

        // 指定商品范围选项过滤数据
        if (GoodsListMpParam.SORT_AREA.equals(param.getGoodsArea())) {
            condition = condition.and(GOODS.SORT_ID.in(param.getGoodsAreaData()));
        } else if (GoodsListMpParam.CAT_AREA.equals(param.getGoodsArea())) {
            condition = condition.and(GOODS.CAT_ID.in(param.getGoodsAreaData()));
        } else if (GoodsListMpParam.BRAND_AREA.equals(param.getGoodsArea())) {
            condition = condition.and(GOODS.BRAND_ID.in(param.getGoodsAreaData()));
        } else if (GoodsListMpParam.LABEL_AREA.equals(param.getGoodsArea())) {
            List<Integer> allIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
            // 如果存在关联所有商品的标签则就不用再进行过滤了
            if (allIds.size() == 0) {
                List<Integer> catIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
                List<Integer> sortIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
                List<Integer> goodsIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
                condition = condition.and(GOODS.CAT_ID.in(catIds).or(GOODS.SORT_ID.in(sortIds)).or(GOODS.GOODS_ID.in(goodsIds)));
            }
        }
        // 商品活动过滤
        if (GoodsListMpParam.GOODS_TYPE_IS_CARD_EXCLUSIVE.equals(param.getGoodsType())) {
            condition = condition.and(GOODS.IS_CARD_EXCLUSIVE.eq(GoodsConstant.CARD_EXCLUSIVE));
        } else {
            if (param.getGoodsType() != null) {
                condition = condition.and(GOODS.GOODS_TYPE.eq(param.getGoodsType()));
            }
        }

        return condition;
    }

    /**
     * 通过商品id集合回去对应的数据信息
     * @param goodsIds 商品id集合
     * @param userId 用户id
     * @param currentPages 当前页
     * @param pagesRows 查询数据条数
     * @return {@link com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpParam}集
     */
    public List<GoodsListMpVo> getGoodsListNormal(List<Integer> goodsIds,Integer userId,Integer currentPages,Integer pagesRows) {
        if (goodsIds == null) {
            return new ArrayList<>();
        }
        if (currentPages != null) {
            currentPages-=1;
        }
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.GOODS_ID.in(goodsIds));
        List<ActivityGoodsListCapsule> goodsListCapsules = findActivityGoodsListCapsulesDao(condition, null, currentPages, pagesRows, goodsIds);
        disposeGoodsList(goodsListCapsules,userId);
        List<GoodsListMpVo> goodsListMpVos = convertGoodsCapsuleTGoodsListMpVo(goodsListCapsules);
        return goodsListMpVos;
    }

    /**
     * 处理获取的推荐商品规格，评价，标签，活动tag,最终划线价和商品价格
     * @param goodsListCapsules 通过过滤条件获取的商品信息
     * @param userId            用户id 可为null(在admin页面装修的时候传入的就是null)
     */
    private void disposeGoodsList(List<ActivityGoodsListCapsule> goodsListCapsules, Integer userId) {
        GoodsListMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsListMpProcessorFactory.class);
        // 处理规格，评价，标签，活动tag,最终划线价和商品价格
        processorFactory.doProcess(goodsListCapsules, userId);
    }

    /**
     * ActivityGoodsListCapsule 转换为 GoodsListMpVo
     * @param goodsListCapsules 待转换数据
     * @return 转换后的数据
     */
    private List<GoodsListMpVo> convertGoodsCapsuleTGoodsListMpVo(List<ActivityGoodsListCapsule> goodsListCapsules) {
        List<GoodsListMpVo> goodsListMpVos = new ArrayList<>();
        if (goodsListCapsules == null || goodsListCapsules.size() == 0) {
            return goodsListMpVos;
        }

        for (ActivityGoodsListCapsule t : goodsListCapsules) {
            GoodsListMpVo vo = t.convertToGoodsListMpVo();
            vo.setGoodsImg(getImgFullUrlUtil(vo.getGoodsImg()));
            goodsListMpVos.add(vo);
        }
        return goodsListMpVos;
    }

    /**
     * 小程序端获取商品详情信息
     * @param param {@link com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsDetailMpParam}
     */
    public GoodsDetailMpVo getGoodsDetailMp(GoodsDetailMpParam param){
        GoodsDetailMpCapsule goodsDetailMpCapsule = getGoodsDetailMpInfoDao(param.getGoodsId());
        GoodsDetailMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsDetailMpProcessorFactory.class);
        processorFactory.doProcess(goodsDetailMpCapsule,param.getUserId());
        return convertGoodsDetailCapsuleTGoodsDetailMpVo(goodsDetailMpCapsule);
    }

    /**
     * GoodsDetailMpCapsule 转换为 GoodsDetailMpVo
     * @param goodsDetailMpCapsule
     * @return
     */
    private GoodsDetailMpVo convertGoodsDetailCapsuleTGoodsDetailMpVo(GoodsDetailMpCapsule goodsDetailMpCapsule){
        GoodsDetailMpVo goodsDetailMpVo = goodsDetailMpCapsule.convertToGoodsDetailMpVo();

        List<String> goodsImgs = goodsDetailMpVo.getGoodsImgs();
        List<String> fullImgs = new ArrayList<>();
        goodsImgs.forEach(img->fullImgs.add(getImgFullUrlUtil(img)));
        goodsDetailMpVo.setGoodsImgs(fullImgs);

        goodsDetailMpVo.setGoodsVideoImg(getVideoFullUrlUtil(goodsDetailMpVo.getGoodsVideoImg(),false));
        goodsDetailMpVo.setGoodsVideo(getVideoFullUrlUtil(goodsDetailMpVo.getGoodsVideo(),true));

        goodsDetailMpVo.getProducts().forEach(prd->prd.setPrdImg(getImgFullUrlUtil(prd.getPrdImg())));

        return goodsDetailMpVo;
    }
    /**
     * 将相对路劲修改为全路径
     * @param relativePath 相对路径
     * @return null或全路径
     */
    private String getImgFullUrlUtil(String relativePath) {
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return imageService.imageUrl(relativePath);
        }
    }

    /**
     *  商品视频和快照图片相对路径转换全路径
     * @param relativePath 相对路径
     * @param videoOrSnapShop true: 视频，false: 快照
     * @return 全路径
     */
    private String getVideoFullUrlUtil(String relativePath,boolean videoOrSnapShop){
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return videoOrSnapShop ? upYunConfig.videoUrl(relativePath) : upYunConfig.imageUrl(relativePath);
        }
    }

    /**
     * 根据过滤条件获取商品列表中的商品信息，
     * 返回结果的顺序和goodsIds的顺序一致,若果查询的结果在goodsId中不存在则默认添加至返回列表的末尾
     * @param condition 过滤条件
     * @param orderFields 排序结合
     * @param offset 分页开始位置 如果limit为null则不会进行分页，如果offset为null,则默认为从0开始
     * @param limit 分页数量为null表示不进行分页
     * @param goodsIds 指定的商品id顺序
     * @return {@link com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule}
     */
    public List<ActivityGoodsListCapsule>  findActivityGoodsListCapsulesDao(Condition condition, List<SortField<?>> orderFields,Integer offset,Integer limit, List<Integer> goodsIds){

        if (condition != null) {
            condition = condition.and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        }

        if (orderFields == null || orderFields.size() == 0) {
            orderFields = new ArrayList<>();
            orderFields.add(GOODS.GOODS_ID.asc());
        }

        Select<?> select =null;

        SelectSeekStepN<Record12<Integer, String, Byte, BigDecimal, BigDecimal, Integer, Integer, String, Integer, Integer, Integer, Integer>> record12 =
            db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE, GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
            GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
            GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID)
            .from(GOODS).where(condition).orderBy(orderFields);
        select = record12;

        // 拼接分页
        if (limit != null) {
            offset = offset==null?0:offset;
            select = record12.limit(offset,limit);
        }

        List<ActivityGoodsListCapsule> returnList;

        if (goodsIds != null) {
            Map<Integer, ActivityGoodsListCapsule> map = select.fetchMap(GOODS.GOODS_ID, ActivityGoodsListCapsule.class);
            returnList = goodsIds.stream().filter(id -> map.get(id) != null).map(map::remove).collect(Collectors.toList());
            returnList.addAll(map.values());
        } else {
            returnList = select.fetchInto(ActivityGoodsListCapsule.class);
        }
        return returnList;
    }

    /**
     * 获取商品基本信息详情
     * @param goodsId 商品id（该商品可能已删除或下架）
     * @return {@link com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule}
     */
    public GoodsDetailMpCapsule getGoodsDetailMpInfoDao(Integer goodsId) {
        GoodsDetailMpCapsule capsule = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE, GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_NUMBER,
            GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID, GOODS_BRAND.BRAND_NAME, GOODS.DEL_FLAG, GOODS.IS_ON_SALE,
            GOODS.GOODS_IMG,GOODS.GOODS_VIDEO_ID, GOODS.GOODS_VIDEO, GOODS.GOODS_VIDEO_IMG, GOODS.GOODS_VIDEO_SIZE,
            GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM,GOODS.IS_CARD_EXCLUSIVE)
            .from(GOODS).leftJoin(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID))
            .where(GOODS.GOODS_ID.eq(goodsId)).fetchAny().into(GoodsDetailMpCapsule.class);
        // 图片处理
        List<String> imgs = db().select().from(GOODS_IMG).where(GOODS_IMG.IMG_ID.eq(goodsId)).fetch(GOODS_IMG.IMG_URL);
        capsule.getGoodsImgs().add(capsule.getGoodsImg());
        capsule.getGoodsImgs().addAll(imgs);
        //处理视频长度和宽度
        if (capsule.getGoodsVideoId() != null) {
            Record3<Integer, Integer,Integer> record = db().select(UPLOADED_VIDEO.VIDEO_HEIGHT, UPLOADED_VIDEO.VIDEO_WIDTH,UPLOADED_VIDEO.VIDEO_SIZE).from(UPLOADED_VIDEO)
                .where(UPLOADED_VIDEO.VIDEO_ID.eq(capsule.getGoodsVideoId()).and(UPLOADED_VIDEO.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))).fetchAny();
            if (record != null) {
                capsule.setVideoHeight(record.get(UPLOADED_VIDEO.VIDEO_HEIGHT));
                capsule.setVideoWidth(record.get(UPLOADED_VIDEO.VIDEO_WIDTH));
                capsule.setGoodsVideoSize(record.get(UPLOADED_VIDEO.VIDEO_SIZE) *1.0/1024/1024);
            }
        }
        return capsule;
    }
}
