package com.vpu.mp.service.shop.market.gift;

import com.vpu.mp.db.shop.tables.Gift;
import com.vpu.mp.db.shop.tables.GiftProduct;
import com.vpu.mp.db.shop.tables.records.GiftProductRecord;
import com.vpu.mp.db.shop.tables.records.GiftRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.gift.GiftListParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftListVo;
import com.vpu.mp.service.pojo.shop.market.gift.GiftParam;
import com.vpu.mp.service.pojo.shop.market.gift.RuleParam;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GiveGiftReceive.GIVE_GIFT_RECEIVE;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.service.pojo.shop.market.gift.GiftListParam.*;
import static com.vpu.mp.service.pojo.shop.market.gift.GiftListVo.ABLE;
import static com.vpu.mp.service.pojo.shop.market.gift.GiftListVo.DISABLE;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 赠品
 *
 * @author 郑保乐
 */
@Service
public class GiftService extends ShopBaseService {

    private static final Gift TABLE = Gift.GIFT;
    private static final GiftProduct SUB_TABLE = GiftProduct.GIFT_PRODUCT;

    private static final int MAX_RULE_SIZE = 3;

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
        transform(param);
        GiftRecord giftRecord = db.newRecord(TABLE, param);
        giftRecord.insert();
        return giftRecord.getId();
    }

    /**
     * 格式转换
     */
    private void transform(GiftParam param) {
        String ruleJson = Util.underLineStyleGson().toJson(param.getRules());
        String goodsId = Util.listToString(param.getGoodsIds());
        param.setGoodsId(goodsId);
        param.setRule(ruleJson);
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
     * 编辑活动 - 更新
     */
    public void updateGift(GiftParam param) {
        Integer giftId = param.getId();
        Assert.notNull(giftId, "Missing parameter id");
        validateRules(param);
        transform(param);
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
    public PageResult<GiftListVo> getPageList(GiftListParam param) {
        SelectConditionStep<?> query = db().select(TABLE.ID, TABLE.NAME, TABLE.START_TIME, TABLE.END_TIME,
            TABLE.STATUS, DSL.count(GIVE_GIFT_RECEIVE.ID).as("giftTimes")).from(TABLE)
            .leftJoin(ORDER_GOODS).on(ORDER_GOODS.IS_GIFT.eq(1).and(ORDER_GOODS.GIFT_ID.eq(TABLE.ID)))
            .where(TABLE.DEL_FLAG.eq((byte) 0));
        buildOptions(query, param);
        PageResult<GiftListVo> page = getPageResult(query, param, GiftListVo.class);
        page.getDataList().forEach(row -> row.setStatus(getStatusOf(row)));
        return page;
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectConditionStep<?> query, GiftListParam param) {
        Byte status = param.getStatus();
        String name = param.getName();
        if (null != status) {
            addStatusCondition(query, status);
        }
        if (isNotEmpty(name)) {
            query.and(TABLE.NAME.like(format("%s%%", name)));
        }
    }

    /**
     * 状态转换
     */
    private void addStatusCondition(SelectConditionStep<?> query, Byte status) {
        switch (status) {
            case NOT_STARTED:
                query.and(TABLE.START_TIME.gt(Util.currentTimeStamp()));
                break;
            case ONGOING:
                query.and(TABLE.START_TIME.le(Util.currentTimeStamp()))
                    .and(TABLE.END_TIME.gt(Util.currentTimeStamp()));
                break;
            case EXPIRED:
                query.and(TABLE.END_TIME.lt(Util.currentTimeStamp()));
                break;
            case DISABLED:
                query.and(TABLE.STATUS.eq(DISABLE));
                break;
            default:
                throw new IllegalArgumentException("Unexpected status: " + status);
        }
        if (DISABLED != status) {
            query.and(TABLE.STATUS.eq(ABLE));
        }
    }

    /**
     * 获取活动的状态
     */
    private byte getStatusOf(GiftListVo vo) {
        Byte status = vo.getStatus();
        if (DISABLE == status) {
            return DISABLED;
        } else {
            Timestamp startDate = vo.getStartTime();
            Timestamp endDate = vo.getEndTime();
            if (startDate.after(Util.currentTimeStamp())) {
                return NOT_STARTED;
            } else if (endDate.after(Util.currentTimeStamp())) {
                return ONGOING;
            } else {
                return EXPIRED;
            }
        }
    }
}
