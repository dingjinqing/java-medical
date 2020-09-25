package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticAddVo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticPayVo;
import com.vpu.mp.service.pojo.wxapp.store.showmain.StoreOrderListParam;
import com.vpu.mp.service.pojo.wxapp.store.showmain.StoreOrderListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ReturnOrderGoods.RETURN_ORDER_GOODS;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;

/**
 * @author yangpengcheng
 * @date 2020/7/28
 **/
@Repository
public class OrderInfoDao extends ShopBaseDao {
    /**
     *
     * @param orderId
     * @param auditStatus
     */
    public void updateAuditStatus(Integer orderId,Byte auditStatus){
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_AUDIT_STATUS,auditStatus).where(ORDER_INFO.ORDER_ID.eq(orderId)).execute();
    }

    public PageResult<OrderInfoVo>  listOrderInfo(OrderToPrescribeQueryParam param){
        SelectJoinStep<? extends Record> select = db().select().from(ORDER_INFO);
        if(param.getOrderStatus()!=null){
            select.where(ORDER_INFO.ORDER_STATUS.eq(param.getOrderStatus()));
        }
        if (param.getAuditType() != null) {
            select.where(ORDER_INFO.ORDER_AUDIT_TYPE.eq(param.getAuditType()));
        }
        if (param.getAuditStatus() != null) {
            select.where(ORDER_INFO.ORDER_AUDIT_STATUS.eq(param.getAuditStatus()));
        }
        if(param.getOrderId()!=null){
            select.where(ORDER_INFO.ORDER_ID.eq(param.getOrderId()));
        }
        select.orderBy(ORDER_INFO.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(),OrderInfoVo.class);
    }
    /**
     * 订单状态改为待发货
     * @param orderId 订单id
     * @param prescriptionCode 处方号
     */
    public void updateAuditedToWaitDelivery(Integer orderId, String prescriptionCode) {
        db().update(ORDER_INFO)
                .set(ORDER_INFO.ORDER_AUDIT_STATUS, OrderConstant.MEDICAL_AUDIT_PASS)
                .set(ORDER_INFO.ORDER_STATUS,OrderConstant.ORDER_WAIT_DELIVERY)
                .where(ORDER_INFO.ORDER_ID.eq(orderId))
                .execute();
    }

    /**
     * 药品销售报表
     * @return
     */
    public Map<Date, MedicalOrderReportVo> orderSalesReport(Timestamp startTime, Timestamp  endTime){
        return db().select(
                //日期
                date(ORDER_INFO.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME),
                //销售金额  微信+余额+运费
                sum((ORDER_INFO.MONEY_PAID.add(ORDER_INFO.USE_ACCOUNT))).as(ActiveDiscountMoney.ORDER_AMOUNT),
                //微信
                sum(ORDER_INFO.MONEY_PAID).as(ActiveDiscountMoney.MONEY_PAID),
                //余额
                sum(ORDER_INFO.USE_ACCOUNT).as(ActiveDiscountMoney.USE_ACCOUNT),
                //运费
                sum(ORDER_INFO.SHIPPING_FEE).as(ActiveDiscountMoney.SHIPPING_FEE),
                 //销售单数
                count(ORDER_INFO.ORDER_ID).as(ActiveDiscountMoney.ORDER_NUMBER),
                //处方药订单数量
                sum(ORDER_INFO.ORDER_MEDICAL_TYPE).as(ActiveDiscountMoney.PRESCRIPTION_ORDER_NUMBER),
                //处方药金额
                sum(DSL.iif(ORDER_INFO.ORDER_MEDICAL_TYPE.eq(BaseConstant.YES),ORDER_INFO.MONEY_PAID.add(ORDER_INFO.USE_ACCOUNT), BigDecimal.ZERO)).as(ActiveDiscountMoney.PRESCRIPTION_ORDER_ACCOUNT)

        )
                .from(ORDER_INFO)
                .where(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(ORDER_INFO.CREATE_TIME))
                .orderBy(ORDER_INFO.CANCELLED_TIME)
                .fetchMap(date(ORDER_INFO.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReportVo.class);
    }

    /**
     * 根据用户id查询订单
     * @param userId 用户id
     * @return List<OrderInfoDo>
     */
    public List<OrderInfoDo> selectOrderInfoByUserId(Integer userId) {
        return db().select().from(ORDER_INFO)
            .where(ORDER_INFO.USER_ID.eq(userId))
            .and(ORDER_INFO.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchInto(OrderInfoDo.class);
    }

    /**
     * 根据订单id查询订单sn号
     * @param orderId 订单id
     * @return String
     */
    public String selectOrderSnByOrderId(Integer orderId) {
        return db().select(ORDER_INFO.ORDER_SN)
            .from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_ID.eq(orderId)).fetchAnyInto(String.class);
    }

    public List<OrderInfoDo> listUpdateOrderByYesterday(Timestamp beginTime, Timestamp endTime) {
      return  db().selectFrom(ORDER_INFO)
                .where(ORDER_INFO.UPDATE_TIME.ge(beginTime)).and(ORDER_INFO.UPDATE_TIME.le(endTime))
                .and(ORDER_INFO.CREATE_TIME.le(beginTime))
                .fetchInto(OrderInfoDo.class);
    }

    public List<OrderInfoDo> listCreateOrderByYesterday(Timestamp beginTime, Timestamp endTime) {
        return  db().selectFrom(ORDER_INFO)
                .where(ORDER_INFO.CREATE_TIME.ge(beginTime)).and(ORDER_INFO.CREATE_TIME.le(endTime))
                .fetchInto(OrderInfoDo.class);
    }

    /**
     * 检查核销码是否正确
     * @param verifyCode 核销码
     * @param orderSn 订单OrderSn
     * @return boolean
     * @author 赵晓东
     */
    public boolean checkVerifyCode(String verifyCode, String orderSn) {
        OrderInfoDo orderInfoDo = db().select().from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_SN.eq(orderSn))
            .and(ORDER_INFO.VERIFY_CODE.eq(verifyCode)).fetchAnyInto(OrderInfoDo.class);
        return orderInfoDo != null;
    }

    /**
     * 获取门店支付统计数据
     * @param param
     * @return
     */
    public StatisticPayVo getStoreOrderPayData(StatisticParam param) {
        return db().select(DSL.count(ORDER_INFO.ORDER_ID).as("orderNum"),
            DSL.countDistinct(ORDER_INFO.USER_ID).as("userNum"),
            DSL.sum(ORDER_INFO.MONEY_PAID.add(ORDER_INFO.USE_ACCOUNT).add(ORDER_INFO.MEMBER_CARD_BALANCE)).as("totalMoneyPaid")
        ).from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY))
            .and(ORDER_INFO.STORE_ID.eq(param.getStoreId()))
            .and(ORDER_INFO.PAY_TIME.ge(param.getStartTime()))
            .and(ORDER_INFO.PAY_TIME.le(param.getEndTime()))
            .fetchAnyInto(StatisticPayVo.class);
    }

    /**
     * 获取门店下单统计数据
     * @param param
     * @return
     */
    public StatisticAddVo getStoreOrderAddData(StatisticParam param) {
        return db().select(
            DSL.count(ORDER_INFO.ORDER_ID).as("orderNum"),
            DSL.countDistinct(ORDER_INFO.USER_ID).as("userNum")
        ).from(ORDER_INFO)
            .where(ORDER_INFO.STORE_ID.eq(param.getStoreId()))
            .and(ORDER_INFO.CREATE_TIME.ge(param.getStartTime()))
            .and(ORDER_INFO.CREATE_TIME.le(param.getEndTime()))
            .fetchAnyInto(StatisticAddVo.class);
    }

    /**
     * 获取门店配送单待发货单量
     * @param storeIds
     * @return
     */
    public Integer getStoreOrderWaitDeliver(List<Integer> storeIds) {
        return db().select(
            DSL.count(ORDER_INFO.ORDER_ID).as("orderNum")
        ).from(ORDER_INFO)
            .where(ORDER_INFO.STORE_ID.in(storeIds))
            .and(ORDER_INFO.DELIVER_TYPE.eq(OrderConstant.CITY_EXPRESS_SERVICE))
            .and(ORDER_INFO.DEL_FLAG.eq(OrderConstant.NORMAL_DEL))
            .and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY))
            .fetchAnyInto(Integer.class);
    }

    /**
     * 获取门店自提单待核销单量
     * @param storeIds
     * @return
     */
    public Integer getStoreOrderWaitVerify(List<Integer> storeIds) {
        return db().select(
            DSL.count(ORDER_INFO.ORDER_ID).as("orderNum")
        ).from(ORDER_INFO)
            .where(ORDER_INFO.STORE_ID.in(storeIds))
            .and(ORDER_INFO.DELIVER_TYPE.eq(OrderConstant.DELIVER_TYPE_SELF))
            .and(ORDER_INFO.DEL_FLAG.eq(OrderConstant.NORMAL_DEL))
            .and(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_DELIVERY))
            .fetchAnyInto(Integer.class);
    }

    /**
     * 获取订单数量
     * @param storeId
     * @param orderStatusList
     * @return
     */
    public Integer countNumByStoreIdOrderStatus(Integer storeId, List<Byte> orderStatusList){
        SelectConditionStep<? extends Record> select = db().selectCount().from(ORDER_INFO).where(ORDER_INFO.STORE_ID.eq(storeId))
            .and(ORDER_INFO.ORDER_STATUS.in(orderStatusList));
        return select.fetchAnyInto(Integer.class);
    }
    /**
     * 时间查询获取订单数量
     * @param storeId
     * @param orderStatusList
     * @return
     */
    public Integer countNumByStoreIdOrderStatusAndTime(List<Integer> storeId, List<Byte> orderStatusList, Timestamp startTime, Timestamp endTime){
        SelectConditionStep<? extends Record> select = db().selectCount().from(ORDER_INFO).where(ORDER_INFO.STORE_ID.in(storeId))
            .and(ORDER_INFO.ORDER_STATUS.in(orderStatusList));
        if(startTime!=null){
            select.and(ORDER_INFO.CREATE_TIME.ge(startTime));
        }
        if(startTime!=null){
            select.and(ORDER_INFO.CREATE_TIME.le(endTime));
        }
        return select.fetchAnyInto(Integer.class);
    }

    /**
     * 订单状态门店id查询
     * @param param
     * @return
     */
    public PageResult<StoreOrderListVo> getStoreClerkOrderList(StoreOrderListParam param){
        SelectJoinStep<? extends Record> select = db().select(ORDER_INFO.ORDER_ID,ORDER_INFO.ORDER_SN,ORDER_INFO.ORDER_STATUS,ORDER_INFO.MOBILE,ORDER_INFO.ADDRESS_ID,ORDER_INFO.COMPLETE_ADDRESS)
            .from(ORDER_INFO);
        select.where(ORDER_INFO.STORE_ID.eq(param.getStoreId())).and(ORDER_INFO.ORDER_STATUS.in(param.getOrderStatusList()))
            .orderBy(ORDER_INFO.CREATE_TIME.desc());
        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreOrderListVo.class);
    }


    public OrderInfoDo getByOrderId(Integer orderId) {
        return db().select().from(ORDER_INFO).where(ORDER_INFO.ORDER_ID.eq(orderId)).fetchAnyInto(OrderInfoDo.class);
    }

}
