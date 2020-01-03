package com.vpu.mp.service.shop.market.gift;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.Gift;
import com.vpu.mp.db.shop.tables.GiftProduct;
import com.vpu.mp.db.shop.tables.GoodsSpecProduct;
import com.vpu.mp.db.shop.tables.records.GiftProductRecord;
import com.vpu.mp.db.shop.tables.records.GiftRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.gift.GiftDetailListParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftDetailListVo;
import com.vpu.mp.service.pojo.shop.market.gift.GiftListParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftListVo;
import com.vpu.mp.service.pojo.shop.market.gift.GiftParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftVo;
import com.vpu.mp.service.pojo.shop.market.gift.LevelParam;
import com.vpu.mp.service.pojo.shop.market.gift.ProductVo;
import com.vpu.mp.service.pojo.shop.market.gift.RuleJson;
import com.vpu.mp.service.pojo.shop.market.gift.RuleParam;
import com.vpu.mp.service.pojo.shop.market.gift.RuleVo;
import com.vpu.mp.service.pojo.shop.market.gift.UserAction;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.marketing.gift.OrderGiftProductVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.Tag.TAG;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.foundation.util.Util.listToString;
import static com.vpu.mp.service.foundation.util.Util.numberToString;
import static com.vpu.mp.service.foundation.util.Util.stringToList;
import static com.vpu.mp.service.foundation.util.Util.underLineStyleGson;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.jooq.impl.DSL.countDistinct;
import static org.jooq.impl.DSL.select;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 赠品
 *
 * @author 郑保乐
 */
@Service
@Primary
public class GiftService extends ShopBaseService {
    @Autowired
    private DomainConfig domainConfig;

    public static final Gift TABLE = Gift.GIFT;
    public static final GiftProduct SUB_TABLE = GiftProduct.GIFT_PRODUCT;
    public static final GoodsSpecProduct PRODUCT = GoodsSpecProduct.GOODS_SPEC_PRODUCT;

    private static final int MAX_RULE_SIZE = 3;

    /**赠送满足赠品条件的所有赠品*/
    protected static final Byte CONDITION_ALL = 0;

    /**只赠送其中优先级最高的活动赠品*/
    protected static final Byte CONDITION_PRIORITY = 1;

    /**
     * 添加赠品活动
     */
    public void addGift(GiftParam param) {
        validateRules(param);
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            // 保存活动表
            int giftId = insertGift(db, param);
            // 保存赠品规格表
            param.setId(giftId);
            insertProduct(db, param);
        });
    }

    /**
     * 保存赠品规格
     */
    private int insertGift(DSLContext db, GiftParam param) {
        transformParam(param);
        GiftRecord giftRecord = db.newRecord(TABLE, param);
        giftRecord.insert();
        return giftRecord.getId();
    }

    /**
     * 格式转换
     */
    private void transformParam(GiftParam param) {
        RuleParam rules = param.getRules();
        RuleJson json = getRuleJson(rules);
        String ruleJson = underLineStyleGson().toJson(json);
        String goodsId = listToString(param.getGoodsIds());
        if (isEmpty(goodsId)) {
            goodsId = null;
        }
        param.setGoodsId(goodsId);
        param.setRule(ruleJson);
    }

    /**
     * 赠品规则 json 转换
     */
    private RuleJson getRuleJson(RuleParam param) {
        RuleJson ruleJson = new RuleJson();
        ruleJson.setFullNumber(numberToString(param.getFullNumber()));
        ruleJson.setCardId(listToString(param.getCardId()));
        ruleJson.setFullPrice(numberToString(param.getFullPrice()));
        ruleJson.setMaxPayNum(numberToString(param.getMaxPayNum()));
        ruleJson.setMinPayNum(numberToString(param.getMinPayNum()));
        ruleJson.setPayTop(numberToString(param.getPayTop()));
        ruleJson.setUserAction(numberToString(param.getUserAction()));
        ruleJson.setTagId(listToString(param.getTagId()));
        ruleJson.setPayEndTime(param.getPayEndTime());
        ruleJson.setPayStartTime(param.getPayStartTime());
        return ruleJson;
    }

    /**
     * 保存赠品规格
     */
    private void insertProduct(DSLContext db, GiftParam param) {
        List<GiftProductRecord> giftList = getProductsOf(db, param);
        db.batchInsert(giftList).execute();
    }

    /**
     * 校验赠品规则参数
     * 判断提交的规则条数是否超过限制
     */
    private void validateRules(GiftParam param) {
        RuleParam ruleParam = param.getRules();
        List<List<Supplier<?>>> rules = ruleParam.getRules();
        int counteri = 0;
        for (List<Supplier<?>> suppliers : rules) {
            int size = suppliers.size();
            int counterj = 0;
            for (Supplier<?> supplier : suppliers) {
                if (null != supplier.get()) {
                    counterj++;
                }
            }
            if (size == counterj && 0 != size) {
                counteri++;
            }
            if (MAX_RULE_SIZE < counteri) {
                throw new IllegalArgumentException("Rules reached number limit: " + MAX_RULE_SIZE);
            }
        }
    }

    /**
     * 删除活动
     */
    public void deleteGift(Integer id) {

        db().update(TABLE).set(TABLE.STATUS, (byte) 0).set(TABLE.DEL_FLAG, (byte) 1).set(TABLE.DEL_TIME,
            Util.currentTimeStamp()).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 停用活动
     */
    public void disableGift(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, (byte) 0).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 启用活动
     */
    public void enableGift(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, (byte) 1).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 编辑活动 - 查询明细
     */
    public GiftVo getGiftDetail(Integer giftId) {
        GiftVo giftVo = db().selectFrom(TABLE).where(TABLE.ID.eq(giftId)).fetchOneInto(GiftVo.class);
        transformVo(giftVo);
        List<ProductVo> productVos = getGiftProduct(giftId);
        productVos.forEach(vo -> vo.setOfferNumber(getGiftOrderedNumber(vo.getProductId(), giftId)));
        giftVo.setGifts(productVos);
        return giftVo;
    }

    /**
     * 获取活动赠品
     */
    protected List<ProductVo> getGiftProduct(Integer... giftId) {
        return db().select(SUB_TABLE.ID,SUB_TABLE.GIFT_ID,SUB_TABLE.PRODUCT_ID,SUB_TABLE.PRODUCT_NUMBER,
                PRODUCT.PRD_IMG,PRODUCT.PRD_PRICE,PRODUCT.PRD_DESC,GOODS.GOODS_NAME,GOODS.GOODS_IMG)
            .select(PRODUCT.PRD_PRICE, PRODUCT.PRD_IMG, PRODUCT.PRD_NUMBER, PRODUCT.PRD_DESC)
            .select(GOODS.GOODS_NAME, GOODS.GOODS_IMG)
            .from(SUB_TABLE)
            .leftJoin(PRODUCT).on(PRODUCT.PRD_ID.eq(SUB_TABLE.PRODUCT_ID))
            .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(PRODUCT.GOODS_ID))
            .where(SUB_TABLE.GIFT_ID.in(giftId))
            .fetchInto(ProductVo.class);
    }

    /**
     * 获取活动赠品(下单)
     */
    protected List<OrderGiftProductVo> getOrderGiftProducts(Integer giftId) {
        return db().select(SUB_TABLE.GIFT_ID,SUB_TABLE.PRODUCT_ID,SUB_TABLE.PRODUCT_NUMBER,
            PRODUCT.PRD_IMG,PRODUCT.PRD_PRICE,PRODUCT.PRD_DESC,GOODS.GOODS_NAME,GOODS.GOODS_IMG)
            .select(PRODUCT.PRD_PRICE, PRODUCT.PRD_IMG, PRODUCT.PRD_NUMBER, PRODUCT.PRD_DESC, PRODUCT.PRD_SN)
            .select(GOODS.GOODS_ID,GOODS.GOODS_SN, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.DELIVER_TEMPLATE_ID, GOODS.SHOP_PRICE, GOODS.GOODS_WEIGHT, GOODS.CAT_ID, GOODS.SORT_ID)
            .from(SUB_TABLE)
            .leftJoin(PRODUCT).on(PRODUCT.PRD_ID.eq(SUB_TABLE.PRODUCT_ID))
            .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(PRODUCT.GOODS_ID))
            .where(SUB_TABLE.GIFT_ID.eq(giftId).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).and(SUB_TABLE.PRODUCT_NUMBER.gt(0)))
            .fetchInto(OrderGiftProductVo.class);
    }

   /**
     * 获取商品规格
     */
    public ProductVo getProductDetail(Integer giftId, Integer productId) {
        ProductVo vo = db()
            .select(PRODUCT.PRD_ID.as("productId"), PRODUCT.PRD_PRICE, PRODUCT.PRD_IMG, PRODUCT.PRD_NUMBER,
                PRODUCT.PRD_DESC)
            .select(GOODS.GOODS_NAME, GOODS.GOODS_IMG)
            .from(PRODUCT)
            .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(PRODUCT.GOODS_ID))
            .where(PRODUCT.PRD_ID.eq(productId))
            .fetchOneInto(ProductVo.class);
        if (vo!=null){
            vo.setOfferNumber(getGiftOrderedNumber(productId, giftId));
        }
        if(StringUtils.isNotEmpty(vo.getPrdImg())){
            vo.setPrdImg(domainConfig.imageUrl(vo.getPrdImg()));
        }else if(StringUtils.isNotEmpty(vo.getGoodsImg())){
            vo.setGoodsImg(domainConfig.imageUrl(vo.getGoodsImg()));
        }
        return vo;
    }

    /**
     * 出参格式转换
     */
    protected void transformVo(GiftVo giftVo) {
        giftVo.setGoodsIds(stringToList(giftVo.getGoodsId()));
        String rule = giftVo.getRule();
        RuleJson ruleJson = underLineStyleGson().fromJson(rule, RuleJson.class);
        RuleVo ruleVo = getRuleVo(ruleJson);
        giftVo.setRules(ruleVo);
    }

    /**
     * 赠品规则 json 转 vo
     */
    private RuleVo getRuleVo(RuleJson ruleJson) {
        RuleVo ruleVo = new RuleVo();
        ruleVo.setCardId(stringToListNullable(ruleJson.getCardId()));
        ruleVo.setTagId(stringToListNullable(ruleJson.getTagId()));
        ruleVo.setFullNumber(stringToInt(ruleJson.getFullNumber()));
        ruleVo.setFullPrice(stringToDouble(ruleJson.getFullPrice()));
        ruleVo.setMaxPayNum(stringToInt(ruleJson.getMaxPayNum()));
        ruleVo.setMinPayNum(stringToInt(ruleJson.getMinPayNum()));
        ruleVo.setPayStartTime(ruleJson.getPayStartTime());
        ruleVo.setPayEndTime(ruleJson.getPayEndTime());
        ruleVo.setPayTop(stringToInt(ruleJson.getPayTop()));
        ruleVo.setUserAction(stringToByte(ruleJson.getUserAction()));
        return ruleVo;
    }

    /**
     * 字符串转 List（null 参数则返回 null，而非空 List）
     */
    private List<Integer> stringToListNullable(String stringValue) {
        if (null == stringValue) {
            return null;
        }
        return stringToList(stringValue);
    }

    /**
     * 字符串转 Byte
     */
    private Byte stringToByte(String value) {
        if (null == value) {
            return null;
        }
        return Byte.valueOf(value);
    }

    /**
     * 字符串转 Double
     */
    private Double stringToDouble(String value) {
        if (null == value) {
            return null;
        }
        return Double.valueOf(value);
    }

    /**
     * 字符串转 Integer
     */
    private Integer stringToInt(String value) {
        if (null == value) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 编辑活动 - 更新
     */
    public void updateGift(GiftParam param) {
        Integer giftId = param.getId();
        Assert.notNull(giftId, "Missing parameter id");
        validateRules(param);
        transformParam(param);
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            updateGift(db, param);
            deleteProduct(db, giftId);
            insertProduct(db, param);
        });
    }

    /**
     * 更新活动
     */
    private void updateGift(DSLContext db, GiftParam param) {
        GiftRecord giftRecord = db.newRecord(TABLE, param);
        giftRecord.update();
    }

    /**
     * 删除旧的赠品规格
     */
    private void deleteProduct(DSLContext db, Integer giftId) {
        db.delete(SUB_TABLE).where(SUB_TABLE.GIFT_ID.eq(giftId)).execute();
    }

    /**
     * 获取规格 record
     */
    private List<GiftProductRecord> getProductsOf(DSLContext db, GiftParam param) {
        return param.getGifts().stream().map(gift -> {
            GiftProductRecord productRecord = db.newRecord(SUB_TABLE, gift);
            productRecord.setGiftId(param.getId());
            return productRecord;
        }).collect(Collectors.toList());
    }

    /**
     * 赠品活动列表
     */
    /**
     *
     * @param param GiftListParam
     * @return GiftListVo
     */
    public PageResult<GiftListVo> getPageList(GiftListParam param) {
        SelectConditionStep<?> query = getPageListQuery();
        buildOptions(query, param);
        query.orderBy(TABLE.CREATE_TIME.desc());
        PageResult<GiftListVo> page = getPageResult(query, param, GiftListVo.class);
        page.dataList.forEach(vo->{
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
        });
        return page;
    }

    /**
     * 列表查询
     */
    private SelectConditionStep<?> getPageListQuery() {
        return db().select(TABLE.ID, TABLE.NAME, TABLE.START_TIME, TABLE.END_TIME,
            TABLE.LEVEL, TABLE.STATUS, DSL.count(ORDER_GOODS.REC_ID).as("giftTimes"))
            .from(TABLE)
            .leftJoin(ORDER_GOODS).on(ORDER_GOODS.IS_GIFT.eq(1)
                .and(ORDER_GOODS.ACTIVITY_ID.eq(TABLE.ID)
                    .and(ORDER_GOODS.ACTIVITY_TYPE.eq(OrderConstant.GOODS_TYPE_GIFT))))
            .where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectConditionStep<?> query, GiftListParam param) {
        Byte status = param.getStatus();
        String name = param.getName();
        if (null != status && !status.equals(0)) {
            addStatusCondition(query, status);
        }
        if (isNotEmpty(name)) {
            query.and(TABLE.NAME.like(format("%s%%", name)));
        }
        query.groupBy(TABLE.ID, TABLE.NAME, TABLE.START_TIME, TABLE.END_TIME,
            TABLE.LEVEL, TABLE.STATUS);
    }

    /**
     * 状态转换
     */
    private void addStatusCondition(SelectConditionStep<?> query, Byte status) {
        switch (status) {
            case BaseConstant.NAVBAR_TYPE_ONGOING:
                query.and(TABLE.START_TIME.le(Util.currentTimeStamp()))
                        .and(TABLE.END_TIME.gt(Util.currentTimeStamp()));
                query.and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                break;
            case BaseConstant.NAVBAR_TYPE_NOT_STARTED:
                query.and(TABLE.START_TIME.gt(Util.currentTimeStamp()));
                query.and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                break;
            case BaseConstant.NAVBAR_TYPE_FINISHED:
                query.and(TABLE.END_TIME.lt(Util.currentTimeStamp()));
                query.and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                break;
            case BaseConstant.NAVBAR_TYPE_DISABLED:
                query.and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
                break;
            default:
        }
    }

    /**
     * 修改活动优先级
     */
    public void updateLevel(LevelParam param) {
        db().update(TABLE).set(TABLE.LEVEL, param.getLevel()).where(TABLE.ID.eq(param.getId())).execute();
    }

    /**
     * 赠送明细列表查询
     */
    public PageResult<GiftDetailListVo> getGiftDetailPageList(GiftDetailListParam param) {
        SelectConditionStep<?> query = db().select(ORDER_GOODS.ORDER_SN, USER.USER_ID, USER.USERNAME, USER.MOBILE,
            DSL.sum(ORDER_GOODS.GOODS_NUMBER).as("giftAmount"), ORDER_GOODS.CREATE_TIME).from(ORDER_GOODS)
            .leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .leftJoin(USER).on(USER.USER_ID.eq(ORDER_INFO.USER_ID))
            .where(ORDER_GOODS.IS_GIFT.eq(1));
        buildDetailOptions(query, param);
        query.groupBy(ORDER_GOODS.ORDER_SN, ORDER_GOODS.CREATE_TIME, USER.USER_ID, USER.USERNAME, USER.MOBILE)
            .orderBy(ORDER_GOODS.CREATE_TIME.desc());
        return getPageResult(query, param, GiftDetailListVo.class);
    }

    /**
     * 赠品明细查询条件
     */
    private void buildDetailOptions(SelectConditionStep<?> query, GiftDetailListParam param) {
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        String username = param.getUsername();
        String mobile = param.getMobile();
        Integer giftId = param.getGiftId();
        if (null != startTime) {
            query.and(ORDER_GOODS.CREATE_TIME.ge(startTime));
        }
        if (null != endTime) {
            query.and(ORDER_GOODS.CREATE_TIME.le(endTime));
        }
        if (isNotEmpty(username)) {
            query.and(USER.USERNAME.like(format("%s%%", username)));
        }
        if (isNotEmpty(mobile)) {
            query.and(USER.MOBILE.like(format("%s%%", mobile)));
        }
        query.and(ORDER_GOODS.ACTIVITY_ID.eq(giftId).and(ORDER_GOODS.ACTIVITY_TYPE.eq(OrderConstant.GOODS_TYPE_GIFT)));
    }

    /**
     * 获取会员卡列表
     */
    public List<UserAction> getMemberCardList() {
        return db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME.as("name")).from(MEMBER_CARD)
            .where(MEMBER_CARD.DEL_FLAG.eq((byte) 0)).fetchInto(UserAction.class);
    }

    /**
     * 获取用户标签列表
     */
    public List<UserAction> getUserTagList() {
        return db().select(TAG.TAG_ID.as("id"), TAG.TAG_NAME.as("name")).from(TAG)
            .fetchInto(UserAction.class);
    }

    /**
     * 获取赠品的已下单未发货单量（已确定将发出的赠品数量）
     */
    private Integer getGiftOrderedNumber(Integer productId, Integer giftId) {
        return db().select(countDistinct(ORDER_INFO.ORDER_SN))
            .from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY)
                .and(ORDER_INFO.ORDER_SN
                    .in(select(ORDER_GOODS.ORDER_SN).from(ORDER_GOODS)
                        .where(ORDER_GOODS.IS_GIFT.eq(1)
                            .and(ORDER_GOODS.PRODUCT_ID.eq(productId)
                                .and(ORDER_INFO.ACTIVITY_ID.eq(giftId))
                            )
                        )
                    )
                )
            ).fetchOneInto(Integer.class);
    }
}
