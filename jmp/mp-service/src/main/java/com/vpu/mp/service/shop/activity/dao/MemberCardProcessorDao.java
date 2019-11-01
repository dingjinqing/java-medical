package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GradeCardForListInfo;
import org.jooq.Record2;
import org.jooq.Record3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 会员价和专享卡处理dao
 * @author 李晓冰
 * @date 2019年10月31日
 */
public class MemberCardProcessorDao extends ShopBaseService {

    /**
     * 根据传入的商品，平台分类，商家分类，品牌集合过滤出绑定了会员专享的数据
     * @param goodsIds 商品id集合
     * @param catIds 平台分类集合
     * @param sortIds 商家分类集合
     * @param brandIds 品牌集合
     * @return key:代表value值的类型 1 商品id，2 商家分类id，3 平台分类id， 4 品牌id  value: 对应类型的内容值
     */
    public  Map<Byte, List<Integer>> getExclusiveInfo(List<Integer> goodsIds,List<Integer> catIds,List<Integer> sortIds,List<Integer> brandIds){
        Map<Byte, List<Integer>> typeMap = db().select(GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE).from(GOODS_CARD_COUPLE)
            .where(GOODS_CARD_COUPLE.GCTA_ID.in(goodsIds).and(GOODS_CARD_COUPLE.TYPE.eq(CardConstant.COUPLE_TP_GOODS)))
            .or(GOODS_CARD_COUPLE.GCTA_ID.in(catIds).and(GOODS_CARD_COUPLE.TYPE.eq(CardConstant.COUPLE_TP_PLAT)))
            .or(GOODS_CARD_COUPLE.GCTA_ID.in(sortIds).and(GOODS_CARD_COUPLE.TYPE.eq(CardConstant.COUPLE_TP_STORE)))
            .or(GOODS_CARD_COUPLE.GCTA_ID.in(brandIds).and(GOODS_CARD_COUPLE.TYPE.eq(CardConstant.COUPLE_TP_BRAND)))
            .fetchGroups(GOODS_CARD_COUPLE.TYPE, GOODS_CARD_COUPLE.GCTA_ID);
        // 方便处理null问题
        typeMap.computeIfAbsent(CardConstant.COUPLE_TP_GOODS, k -> new ArrayList<>());
        typeMap.computeIfAbsent(CardConstant.COUPLE_TP_PLAT, k -> new ArrayList<>());
        typeMap.computeIfAbsent(CardConstant.COUPLE_TP_STORE, k -> new ArrayList<>());
        typeMap.computeIfAbsent(CardConstant.COUPLE_TP_BRAND, k -> new ArrayList<>());

        return typeMap;
    }

    /**
     * 获取集合内商品的等价卡价格信息
     * @param userId 用户id
     * @param goodsIds 商品集合id
     * @return key: 商品id，value {@link GradeCardForListInfo}
     */
    public Map<Integer, GradeCardForListInfo> getGoodsGradeCardForListInfo(Integer userId, List<Integer> goodsIds){
        // 获取会员等级
        Record2<Integer, String> userGradeCard = getUserGradeCard(userId);

        Map<Integer, GradeCardForListInfo> returnMap = new HashMap<>();
        if (userGradeCard == null) {
            return returnMap;
        }
        // 获取商品等级信息，按价格从小到大正序排序
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsGrades = db().select(GRADE_PRD.GOODS_ID, GRADE_PRD.PRD_ID, GRADE_PRD.GRADE_PRICE).from(GRADE_PRD).where(GRADE_PRD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(GRADE_PRD.GRADE.eq(userGradeCard.get(MEMBER_CARD.GRADE))).and(GRADE_PRD.GOODS_ID.in(goodsIds))
            .orderBy(GRADE_PRD.GRADE_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GRADE_PRD.GOODS_ID)));

        goodsGrades.forEach((key,value)->{
            GradeCardForListInfo info = new GradeCardForListInfo();
            info.setActivityPrice(value.get(0).get(GRADE_PRD.GRADE_PRICE));
            returnMap.put(key,info);
        });

        return returnMap;
    }

    /**
     * 获取用户的等级卡
     * @param userId 用户id值
     * @return Record2 :MEMBER_CARD.ID, MEMBER_CARD.GRADE
     */
    public Record2<Integer, String> getUserGradeCard(Integer userId) {
        Record2<Integer, String> gradeCard = db().select(MEMBER_CARD.ID, MEMBER_CARD.GRADE).from(USER_CARD).join(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID))
            .where(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(USER_CARD.USER_ID.eq(userId)).and(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE))
            .fetchAny();

        return gradeCard;
    }
}
