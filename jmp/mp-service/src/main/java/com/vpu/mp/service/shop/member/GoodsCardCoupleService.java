package com.vpu.mp.service.shop.member;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.*;

/**
 *  会员卡商品(分类,商家分类...)关联类
 * @author 孔德成
 * @date 2019/10/30 15:31
 */
@Service
public class GoodsCardCoupleService  extends ShopBaseService {

    @Autowired
    public UserCardDaoService userCardDao;


    /**
     *
     * 根据会员卡等级获取会员卡关联表
     * @param grade
     * @return
     */
    public Map<Byte, List<Integer>> getGradeCardCoupleGoodsList(String grade){
        return db().select(GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE).from(GOODS_CARD_COUPLE)
                .leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(GOODS_CARD_COUPLE.CARD_ID))
                .where(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE))
                .and(MEMBER_CARD.FLAG.eq(CardConstant.MCARD_FLAG_USING))
                .and(MEMBER_CARD.GRADE.le(grade))
                .and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(MEMBER_CARD.PAY_OWN_GOOD.eq(CardConstant.PAY_OWN_GOOD_YES))
                .fetch()
                .intoGroups(GOODS_CARD_COUPLE.TYPE, GOODS_CARD_COUPLE.GCTA_ID);
    }

    /**
     *  获取普通会员卡关联表
     * @param userId
     * @return type为key, id为value
     */
    public Map<Byte, List<Integer>> getGeneralCardCoupleGoodsList(Integer userId){
        return db().select(GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE).from(GOODS_CARD_COUPLE)
                .leftJoin(USER_CARD).on(GOODS_CARD_COUPLE.CARD_ID.eq(USER_CARD.CARD_ID))
                .leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(GOODS_CARD_COUPLE.CARD_ID))
                .where(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_NORMAL))
                .and(USER_CARD.FLAG.eq(CardConstant.UCARD_FG_USING))
                .and(USER_CARD.CARD_ID.eq(userId))
                .and(USER_CARD.EXPIRE_TIME.isNotNull().or(USER_CARD.EXPIRE_TIME.gt(DateUtil.getLocalDateTime())))
                .and(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_NO)
                        .or(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull())))
                .fetch()
                .intoGroups(GOODS_CARD_COUPLE.TYPE, GOODS_CARD_COUPLE.GCTA_ID);
    }

    /**
     *  获取用户的会员关联商品表数据
     * @param userId
     * @return
     */
    public Map<Byte, List<Integer>> getGoodsCardCouple(Integer userId){
        // 获取会员等级
        String userCardGrade = userCardDao.getUserCardGrade(userId);
        // 获取会员专享商品
        Map<Byte, List<Integer>> gradeCoupleGoods =new HashMap<>();
        if (userCardGrade!=null){
            gradeCoupleGoods = getGradeCardCoupleGoodsList(userCardGrade);
        }
        Map<Byte, List<Integer>> cardCoupleGoods = getGeneralCardCoupleGoodsList(userId);
        gradeCoupleGoods.forEach((key,vaule)->{
            if (cardCoupleGoods.containsKey(key)){
                vaule.addAll(cardCoupleGoods.get(key));
                cardCoupleGoods.put(key,vaule);
            }else {
                cardCoupleGoods.put(key,vaule);
            }
        });
        return cardCoupleGoods;

    }
}
