package com.vpu.mp.service.shop.market.gift;

import com.vpu.mp.db.shop.tables.Gift;
import com.vpu.mp.db.shop.tables.GiftProduct;
import com.vpu.mp.db.shop.tables.records.GiftProductRecord;
import com.vpu.mp.db.shop.tables.records.GiftRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.gift.GiftParam;
import com.vpu.mp.service.pojo.shop.market.gift.ProductParam;
import com.vpu.mp.service.pojo.shop.market.gift.RuleParam;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        List<ProductParam> gifts = param.getGifts();
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            // 保存活动表
            int giftId = insertGift(db, param);
            // 保存赠品规格表
            insertProduct(db, giftId, gifts);
        });
    }

    /**
     * 保存赠品规格
     */
    private int insertGift(DSLContext db, GiftParam param) {
        String ruleJson = Util.underLineStyleGson().toJson(param.getRules());
        String goodsId = Util.listToString(param.getGoodsIds());
        param.setGoodsId(goodsId);
        param.setRule(ruleJson);
        GiftRecord giftRecord = db.newRecord(TABLE, param);
        giftRecord.insert();
        return giftRecord.getId();
    }

    /**
     * 保存赠品规格
     */
    private void insertProduct(DSLContext db, Integer giftId, List<ProductParam> gifts) {
        List<GiftProductRecord> giftList =
            gifts.stream().map(gift -> {
                GiftProductRecord productRecord = db.newRecord(SUB_TABLE, gift);
                productRecord.setGiftId(giftId);
                return productRecord;
            }).collect(Collectors.toList());
        db.batchInsert(giftList);
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
}
