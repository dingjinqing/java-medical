package com.vpu.mp.service.shop.market.givegift;

import com.vpu.mp.db.shop.tables.GiveGiftReceive;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.givegift.GiveGiftConstant;
import com.vpu.mp.service.pojo.shop.market.givegift.receive.GiveGiftReceiveListParam;
import com.vpu.mp.service.pojo.shop.market.givegift.receive.GiveGiftReceiveListVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.main.Tables.ORDER_GOODS;
import static com.vpu.mp.db.main.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.GIVE_GIFT_CART;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.tables.GiveGiftReceive.GIVE_GIFT_RECEIVE;

/**
 * Table:GIVE_GIFT_RECEIVE
 *
 * @author 王帅, kdc
 */

@Service
public class GiveGiftReceiveService extends ShopBaseService {

    public final GiveGiftReceive TABLE = GIVE_GIFT_RECEIVE;

    @Autowired
    private GiveGiftCartService giveGiftCart;

    /**
     * 活动收到的礼物量
     *
     * @param actId
     * @return
     */
    public Integer getGetGiftOrderCt(Integer actId) {
        return db().selectCount()
                .from(GIVE_GIFT_RECEIVE)
                .leftJoin(GIVE_GIFT_CART).on(GIVE_GIFT_RECEIVE.GIFT_CART_ID.eq(GIVE_GIFT_RECEIVE.ID))
                .where(GIVE_GIFT_CART.GIVE_GIFT_ID.eq(actId)).fetchOne().component1();
    }

    /**
     * 获取收礼列表
     * @param param GiveGiftReceiveListParam
     * @return  PageResult<GiveGiftReceiveListVo>
     */
    public PageResult<GiveGiftReceiveListVo> giveGiftReceiveList(GiveGiftReceiveListParam param) {
        User receive = USER.as("receive");
        User giver = USER.as("giver");
        SelectConditionStep<? extends Record> select = db()
                .select(
                        receive.USER_ID.as("rUserId"),
                        receive.USERNAME.as("rUsername"),
                        receive.MOBILE.as("rMobile"),
                        giver.USER_ID,
                        giver.USERNAME,
                        giver.MOBILE,
                        GIVE_GIFT_RECEIVE.PRODUCT_ID,
                        GIVE_GIFT_RECEIVE.MAIN_ORDER_SN,
                        GIVE_GIFT_RECEIVE.CREATE_TIME,
                        GIVE_GIFT_RECEIVE.ORDER_SN,
                        GIVE_GIFT_RECEIVE.STATUS,
                        GIVE_GIFT_RECEIVE.STATUS_NAME,
                        ORDER_INFO.ORDER_STATUS,
                        ORDER_INFO.ORDER_STATUS_NAME,
                        GIVE_GIFT_CART.GIFT_TYPE,
                        ORDER_GOODS.GOODS_NAME
                )
                .from(GIVE_GIFT_RECEIVE)
                .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_SN.eq(GIVE_GIFT_RECEIVE.ORDER_SN))
                .leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(GIVE_GIFT_RECEIVE.ORDER_SN))
                .leftJoin(GIVE_GIFT_CART).on(GIVE_GIFT_CART.ID.eq(GIVE_GIFT_RECEIVE.GIFT_CART_ID))
                .leftJoin(receive).on(receive.USER_ID.eq(GIVE_GIFT_RECEIVE.USER_ID))
                .leftJoin(giver).on(giver.USER_ID.eq(GIVE_GIFT_CART.USER_ID))
                .where(GIVE_GIFT_CART.GIVE_GIFT_ID.eq(param.getActivityId()));
        buildSelect(select, param, receive, giver);

       return  getPageResult(select,param.getCurrentPage(),param.getPageRows(), GiveGiftReceiveListVo.class);
    }

    private void buildSelect(SelectConditionStep<? extends Record> select, GiveGiftReceiveListParam param, User receive, User giver) {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (param.getGoodsName() != null) {
            select.and(ORDER_GOODS.GOODS_NAME.like(likeValue(param.getGoodsName())));
        }
        if (param.getGoodsSn() != null) {
            select.and(ORDER_GOODS.GOODS_SN.like(likeValue(param.getGoodsSn())));
        }
        if (param.getReceiveMobile() != null) {
            select.and(receive.MOBILE.like(likeValue(param.getReceiveMobile())));
        }
        if (param.getReceiveUserName() != null) {
            select.and(receive.USERNAME.like(likeValue(param.getReceiveUserName())));
        }
        if (param.getGiveUserName() != null) {
            select.and(giver.USERNAME.like(param.getGiveUserName()));
        }
        if (param.getGiveMobile() != null) {
            select.and(giver.MOBILE.like(likeValue(param.getGiveMobile())));
        }
        if (param.getMainOrderSn()!=null){
            select.and(ORDER_INFO.MAIN_ORDER_SN.eq(param.getMainOrderSn()));
        }
        if (param.getReturnFinished()!=null&&param.getReturnFinished()>0){
            select.and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_RETURN_FINISHED)
                    .or(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_REFUND_FINISHED)));
        }

        if (param.getGiftReceiveStatus() != null && param.getGiftReceiveStatus() > -1) {
            switch (param.getGiftReceiveStatus()) {
                case 0:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(GiveGiftConstant.GIFT_RECEIVE_UNSUBMITTED_ADDRESS))
                            .and(GIVE_GIFT_RECEIVE.CREATE_TIME.gt(nowTime));
                    break;
                case 4:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(GiveGiftConstant.GIFT_RECEIVE_HAVE_GIFT))
                            .and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY));
                    break;
                case 5:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(GiveGiftConstant.GIFT_RECEIVE_HAVE_GIFT))
                            .and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_SHIPPED));
                    break;
                case 6:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(GiveGiftConstant.GIFT_RECEIVE_HAVE_GIFT))
                            .and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_FINISHED));
                    break;
                case 7:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(GiveGiftConstant.GIFT_RECEIVE_UNSUBMITTED_ADDRESS))
                            .and(GIVE_GIFT_RECEIVE.CREATE_TIME.le(nowTime));
                    break;
                default:
                    select.and(GIVE_GIFT_RECEIVE.STATUS.eq(param.getGiftReceiveStatus()));

            }
        }


    }
}
