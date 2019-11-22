package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.ShopApplication;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.GroupBuyList.GROUP_BUY_LIST;
import static com.vpu.mp.db.shop.tables.GroupDraw.GROUP_DRAW;
import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.service.foundation.util.Util.currentTimeStamp;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawUserService extends ShopBaseService {

    /** 拼团中 **/
    private static final byte GROUP_ONGOING = 0;
    /** 已成团 **/
    private static final byte GROUPED = 1;
    /** 未成团 **/
    private static final byte NOT_GROUPED = 2;
    /** 已开奖 **/
    private static final byte DREW = 1;
    /** 开奖失败 **/
    private static final byte DRAW_FAIL = 2;
    /** 已中奖 **/
    private static final byte WIN_DRAW = 1;

    private final RabbitTemplate rabbit;
    private final ShopApplication shop;

    public GroupDrawUserService(RabbitTemplate rabbit, ShopApplication shop) {
        this.rabbit = rabbit;
        this.shop = shop;
    }

    /**
     * 生成团记录
     */
    private void generateGroupInfo(OrderInfoRecord orderInfo, Integer groupId, Integer status) {
        String orderSn = orderInfo.getOrderSn();
        Byte isGrouper = (byte) (null == groupId ? 1 : 0);
        if (null == groupId) {
            groupId = generateGroupId();
        }
        List<OrderGoodsRecord> orderGoods = getOrderGoods(orderSn);
        // 记录邀请用户
        GroupDrawInviteRecord availableInviteUser = shop.groupDrawInvite.getAvailableInviteUser(getGroupDrawId(orderSn),
            orderGoods.get(0).getGoodsId(), orderInfo.getUserId());
        Integer inviteUserId = availableInviteUser == null ? 0 : availableInviteUser.getUserId();
        // todo 记录邀请用户
    }

    private Integer getGroupDrawId(String orderSn) {
        return (Integer) db().select(GROUP_BUY_LIST.ACTIVITY_ID).from(GROUP_BUY_LIST)
            .where(GROUP_BUY_LIST.ORDER_SN.eq(orderSn)).fetchOne(0);
    }

    /**
     * 生成团id
     */
    private Integer generateGroupId() {
        return (Integer) db().select(DSL.max(JOIN_GROUP_LIST.GROUP_ID)).fetchOne().get(0) + 1;
    }

    /**
     * 成团
     */
    private void successGroup(Integer groupDrawId, Integer groupId) {
        GroupDrawRecord groupDraw = getGroupDraw(groupDrawId);
        List<JoinGroupListRecord> groupUserList = getOnGoingGroupList(groupDrawId, groupId);
        if (groupDraw.getLimitAmount() <= groupUserList.size()) {
            List<String> orderSns = groupUserList.stream().map(JoinGroupListRecord::getOrderSn).collect(Collectors.toList());
            // 更新订单状态
            updateOrderGroupedStatus(orderSns);
            // 更新成团状态
            updateGroupInfo(groupDrawId, groupId);
            // todo 发送拼团成功模板消息
        }
    }

    /**
     * 更新团信息
     */
    private void updateGroupInfo(Integer groupDrawId, Integer groupId) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.STATUS, GROUPED)
            .set(JOIN_GROUP_LIST.END_TIME, Util.currentTimeStamp()).where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
            .and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId).and(JOIN_GROUP_LIST.STATUS.eq(GROUP_ONGOING))))
            .execute();
    }

    /**
     * 更新订单状态
     */
    private void updateOrderGroupedStatus(List<String> orderSns) {
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_STATUS, OrderConstant.ORDER_PIN_SUCCESSS)
            .where(ORDER_INFO.ORDER_SN.in(orderSns)).execute();
    }

    /**
     * 获取拼团中的参团记录
     */
    private List<JoinGroupListRecord> getOnGoingGroupList(Integer groupDrawId, Integer groupId) {
        return db().selectFrom(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
            .and(JOIN_GROUP_LIST.STATUS.eq(GROUP_ONGOING).and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId))))
            .fetchInto(JOIN_GROUP_LIST);
    }

    /**
     * 获取拼团抽奖活动
     */
    private GroupDrawRecord getGroupDraw(Integer groupDrawId) {
        return db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchOneInto(GROUP_DRAW);
    }

    /**
     * 处理拼团抽奖
     */
    public void dealOpenGroupDraw() {
        List<GroupDrawRecord> goodsGroupDrawList = getOpenGroupDrawList();
        goodsGroupDrawList.forEach(goodsGroupDraw -> {
            Integer goodsGroupDrawId = goodsGroupDraw.getId();
            Short minJoinNum = goodsGroupDraw.getMinJoinNum();
            // 抽奖记录置为已开奖
            updateGroupDrawStatus(goodsGroupDraw.getId());
            // 活动商品
            List<Integer> goodsIds = Util.stringToList(goodsGroupDraw.getGoodsId());
            goodsIds.forEach(goodsId -> {
                int joinUserNum = getJoinUserNumByGoodsId(goodsGroupDrawId, goodsId, null);
                if (0 != joinUserNum) {
                    // 已成团该商品参与用户数
                    joinUserNum = getJoinUserNumByGoodsId(goodsGroupDrawId, goodsId, GROUPED);
                    if (minJoinNum <= joinUserNum) {
                        // 人数达到开奖条件
                        openDraw(goodsGroupDrawId, goodsId);
                    } else {
                        // 不满足开奖条件，更新开奖状态
                        updateGroupDrawStatus(goodsGroupDrawId, goodsId);
                    }
                    // 拼团失败更新开奖状态
                    updateOnGoingGroupDrawStatus(goodsGroupDrawId, goodsId);
                    // 获得参与抽奖用户
                    List<JoinGroupListRecord> groupUserList = getGroupListByGoodsId(goodsGroupDrawId, goodsId, null);
                    // 订单号
                    List<String> orderSns =
                        groupUserList.stream().map(JoinGroupListRecord::getOrderSn).collect(Collectors.toList());
                    // 更新订单状态为 "待发货"
                    updateOrderWaitDelivery(orderSns);
                    // todo 同步订单状态到 CRM
                    List<Integer> couponUserIds = new LinkedList<>();
                    List<Integer> couponIds = Util.stringToList(goodsGroupDraw.getRewardCouponId());
                    groupUserList.forEach(groupUser -> {
                        if (WIN_DRAW == groupUser.getIsWinDraw()) {
                            updateProductNumber(groupUser.getOrderSn());
                        } else {
                            // todo 未中奖退款

                            // todo 发送中奖模板消息
                            if (!couponIds.isEmpty()) {
                                couponUserIds.add(groupUser.getUserId());
                            }
                        }
                    });
                    // 批量送券
                    giveVoucher(couponIds, couponUserIds);
                }
            });
        });
    }

    /**
     * 更新库存
     */
    private void updateProductNumber(String orderSn) {
        List<OrderGoodsRecord> orderGoods = getOrderGoods(orderSn);
        OrderGoodsRecord goods = orderGoods.get(0);
        Integer productId = goods.getProductId();
        GoodsSpecProductRecord goodsSpecProduct = getGoodsSpecProduct(productId);
        Integer productNumber = goodsSpecProduct.getPrdNumber();
        if (0 < productNumber) {
            OrderInfoRecord orderInfo = getOrderInfo(orderSn);
            updateGoodsSkuAfterWaitDeliver(orderInfo);
        }
    }

    /**
     * todo 更新库存
     */
    private void updateGoodsSkuAfterWaitDeliver(OrderInfoRecord orderInfo) {

    }

    /**
     * 获取订单信息
     */
    private OrderInfoRecord getOrderInfo(String orderSn) {
        return db().selectFrom(ORDER_INFO).where(ORDER_INFO.ORDER_SN.eq(orderSn)).fetchOneInto(ORDER_INFO);
    }

    /**
     * 获取单品
     */
    private GoodsSpecProductRecord getGoodsSpecProduct(Integer productId) {
        return db().selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId))
            .fetchOneInto(GOODS_SPEC_PRODUCT);
    }

    /**
     * 获取订单中的商品
     */
    private List<OrderGoodsRecord> getOrderGoods(String orderSn) {
        return db().selectFrom(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetch()
            .into(OrderGoodsRecord.class);
    }

    /**
     * 更新订单状态为待发货
     */
    private void updateOrderWaitDelivery(List<String> orderSns) {
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_STATUS, ORDER_WAIT_DELIVERY)
            .where(ORDER_INFO.ORDER_SN.in(orderSns))
            .execute();
    }

    /**
     * 拼团失败更新状态
     */
    private void updateOnGoingGroupDrawStatus(Integer goodsGroupDrawId, Integer goodsId) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.STATUS, NOT_GROUPED)
            .set(JOIN_GROUP_LIST.END_TIME, currentTimeStamp())
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(goodsGroupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)))
            .execute();
    }

    /**
     * 不满足开奖条件，更新状态
     */
    private void updateGroupDrawStatus(Integer goodsGroupDrawId, Integer goodsId) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.DRAW_STATUS, DRAW_FAIL)
            .set(JOIN_GROUP_LIST.DRAW_TIME, currentTimeStamp())
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(goodsGroupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)))
            .execute();
    }

    /**
     * 获取某个商品的参与用户数
     */
    private Integer getJoinUserNumByGoodsId(Integer groupDrawId, Integer goodsId, Byte status) {
        SelectConditionStep<Record1<Integer>> select = db().selectCount().from(JOIN_GROUP_LIST)
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
        if (null != status) {
            select.and(JOIN_GROUP_LIST.STATUS.eq(status));
        }
        return (Integer) select.fetchOne().get(0);
    }

    /**
     * 更新开奖状态
     */
    private void updateGroupDrawStatus(Integer id) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.IS_WIN_DRAW, (byte) 1)
            .where(JOIN_GROUP_LIST.ID.eq(id));
    }

    /**
     * 获取待开奖活动
     */
    private List<GroupDrawRecord> getOpenGroupDrawList() {
        return db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.END_TIME.le(currentTimeStamp()))
            .fetchInto(GroupDrawRecord.class);
    }

    /**
     * 开奖
     */
    private void openDraw(Integer groupDrawId, Integer goodsId) {
        // 该商品的已成团
        List<Integer> groupIds = getGroupListByGoodsId(groupDrawId, goodsId, GROUPED)
            .stream().map(JoinGroupListRecord::getGroupId).collect(Collectors.toList());
        // 抽奖记录
        List<Integer> drawIds = getUserByGroupIds(groupDrawId, goodsId, groupIds);
        // 开奖
        int winIndex = randomItemFrom(drawIds);
        Integer winDrawId = drawIds.get(winIndex);
        // 状态更新
        updateDraw(winDrawId);
        JoinDrawListRecord winDrawRecord = getDrawRecordById(winDrawId);
        Integer winDrawGroupId = winDrawRecord.getGroupId();
        Integer winDrawUserId = winDrawRecord.getUserId();
        updateDrawStatus(groupDrawId, winDrawGroupId, winDrawUserId);
        updateGroupInfoByGoodsId(groupDrawId, goodsId);
    }

    /**
     * 更新团开奖状态
     */
    private void updateGroupInfoByGoodsId(Integer groupDrawId, Integer goodsId) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.DRAW_STATUS, DREW)
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
    }

    /**
     * 更新团中奖状态
     */
    private void updateDrawStatus(Integer groupDrawId, Integer winDrawGroupId, Integer winDrawUserId) {
        db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.IS_WIN_DRAW, WIN_DRAW)
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GROUP_ID.eq(winDrawGroupId)
                .and(JOIN_GROUP_LIST.USER_ID.eq(winDrawUserId)))).execute();
    }

    /**
     * 获取抽奖记录
     */
    private JoinDrawListRecord getDrawRecordById(Integer winDrawId) {
        return db().selectFrom(JOIN_DRAW_LIST).where(JOIN_DRAW_LIST.ID.eq(winDrawId))
            .fetchOneInto(JoinDrawListRecord.class);
    }

    /**
     * 更新中奖状态
     */
    private void updateDraw(Integer winDrawId) {
        db().update(JOIN_DRAW_LIST).set(JOIN_DRAW_LIST.IS_WIN_DRAW, WIN_DRAW)
            .where(JOIN_DRAW_LIST.ID.eq(winDrawId)).execute();
    }

    /**
     * 获取list中的随机项
     */
    private int randomItemFrom(List<Integer> items) {
        int size = items.size();
        return new Random().nextInt(size);
    }

    /**
     * 获取团内用户
     */
    private List<Integer> getUserByGroupIds(Integer groupDrawId, Integer goodsId, List<Integer> groupIds) {
        return db().selectFrom(JOIN_DRAW_LIST).where(JOIN_DRAW_LIST.GROUP_DRAW_ID.eq(groupDrawId)
            .and(JOIN_DRAW_LIST.GOODS_ID.eq(goodsId).and(JOIN_DRAW_LIST.GROUP_ID.in(groupIds))))
            .fetch().into(JoinDrawListRecord.class).stream().map(JoinDrawListRecord::getUserId).collect(Collectors.toList());
    }

    /**
     * 获取某个商品的参团记录
     */
    private List<JoinGroupListRecord> getGroupListByGoodsId(Integer groupDrawId, Integer goodsId, Byte groupStatus) {
        SelectConditionStep<JoinGroupListRecord> select = db().selectFrom(JOIN_GROUP_LIST)
            .where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
                .and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
        if (null != groupStatus) {
            select.and(JOIN_GROUP_LIST.STATUS.eq(groupStatus));
        } else {
            select.and(JOIN_GROUP_LIST.STATUS.gt(GROUP_ONGOING));
        }
        return select.fetchInto(JoinGroupListRecord.class);
    }


    /**
     * 未中奖送券
     */
    private void giveVoucher(List<Integer> coupOnIds, List<Integer> userIds) {
        String[] couponIdArray = new String[coupOnIds.size()];
        for (int i = 0; i < coupOnIds.size(); i++) {
            couponIdArray[i] = String.valueOf(coupOnIds.get(i));
        }
        CouponGiveQueueParam message = new CouponGiveQueueParam();
        message.setUserIds(userIds);
        message.setCouponArray(couponIdArray);
        rabbit.convertAndSend(RabbitConfig.QUEUE_COUPON_SEND, message);
    }
}
