package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.COMMENT_GOODS;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
public class GoodsCommentProcessorDao extends BaseShopConfigService {

    final public static String K_COMMENT = "comment";
    final public static String K_COMMENT_STATE = "comment_state";
    /**审核配置对应码  0不审，1先发后审，2先审后发*/
    public static final Byte NOT_AUDIT = 0;
    public static final Byte PUBLISH_FIRST = 1;
    /**显示未填写心得评论配置值*/
    public static final Byte SHOW_STATE = 1;

    /**
     * 获取集合内商品评级数量
     * 评价状态为 0:未审批,1:审批通过,2:审批未通过
     *  配置项为0：用户发表评价时评价状态自动为1,查询数据时查询状态为0和1的记录
     *  配置项为1: 用户发表评价时状态为0，查询数据时查询状态为0和1的记录
     *  配置项为2：用户发表评价时状态为1，查询数据时查询状态为1的记录
     *  评价配置： 0不审，1先发后审，2先审后发
     * 是否显示未填写心得评价开关：K_COMMENT_STATE 0:不展示未填写心得的评论 1:显示未填写心得的评论 （心得：COMMENT_GOODS：comm_note）
     * @param goodsIds 商品id集合
     */
    public Map<Integer, Long> getGoodsCommentNumInfo(List<Integer> goodsIds) {
        Byte commentConfig = get(K_COMMENT, Byte.class, (byte) 0);
        Byte commentStateConfig = get(K_COMMENT_STATE,Byte.class,(byte)0);

        Condition auditCondition = DSL.noCondition();
        if (NOT_AUDIT.equals(commentConfig) || PUBLISH_FIRST.equals(commentConfig)) {
            auditCondition = auditCondition.and(COMMENT_GOODS.FLAG.eq(GoodsConstant.NOT_AUDIT).or(COMMENT_GOODS.FLAG.eq(GoodsConstant.PASS_AUDIT)));
        } else {
            auditCondition = auditCondition.and(COMMENT_GOODS.FLAG.eq(GoodsConstant.PASS_AUDIT));
        }

        if (SHOW_STATE.equals(commentStateConfig)) {
            auditCondition = auditCondition.and(COMMENT_GOODS.COMM_NOTE.isNotNull());
        }

        Map<Integer, Long> commentNumMap = db().select(COMMENT_GOODS.GOODS_ID).from(COMMENT_GOODS)
            .where(COMMENT_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(COMMENT_GOODS.GOODS_ID.in(goodsIds))
            .and(auditCondition)
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(COMMENT_GOODS.GOODS_ID), Collectors.counting()));

        return commentNumMap;
    }
}
