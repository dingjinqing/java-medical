package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderPrescriptionVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.OrderGoodsSimpleAuditVo;
import org.jooq.Record2;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

/**
 * @author 孔德成
 * @date 2020/7/13 14:41
 */
@Repository
public class OrderGoodsDao extends ShopBaseDao {

    /**
     * 根据订单号获取订单商品的规格id
     *
     * @param orderSn 订单号
     * @return 规格ids
     */
    public List<Integer> getProductIdByOrderSn(String orderSn) {
        return db().select(ORDER_GOODS.PRODUCT_ID).from(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetch(ORDER_GOODS.PRODUCT_ID);
    }

    /**
     * 待审核处方列表
     *
     * @return
     */
    public PageResult<OrderPrescriptionVo> listGoodsOldPrescription(PrescriptionQueryParam param) {
        SelectJoinStep<Record2<Integer, String>> from = db()
                .select(ORDER_GOODS.ORDER_ID, ORDER_GOODS.PRESCRIPTION_OLD_CODE)
                .from(ORDER_GOODS);
        if (param.getAuditType() != null) {
            from.where(ORDER_GOODS.MEDICAL_AUDIT_TYPE.eq(param.getAuditType()));
        }
        if (param.getAuditStatus() != null) {
            from.where(ORDER_GOODS.MEDICAL_AUDIT_STATUS.eq(param.getAuditStatus()));
        }
        from.groupBy(ORDER_GOODS.ORDER_ID, ORDER_GOODS.PRESCRIPTION_OLD_CODE)
        .orderBy(ORDER_GOODS.CREATE_TIME.desc());
        return getPageResult(from, param.getCurrentPage(), param.getPageRows(), OrderPrescriptionVo.class);
    }

    /**
     * 待审核处方数量
     * @return
     */
    public Integer countAuditOrder(){
         return db().selectCount()
                .from(ORDER_GOODS)
                 .where(ORDER_GOODS.MEDICAL_AUDIT_STATUS.eq(OrderConstant.MEDICAL_AUDIT_DEFAULT))
                 .and(ORDER_GOODS.MEDICAL_AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT))
                 .groupBy(ORDER_GOODS.ORDER_ID, ORDER_GOODS.PRESCRIPTION_OLD_CODE)
                 .orderBy(ORDER_GOODS.CREATE_TIME.desc()).fetchAnyInto(Integer.class);
    }

    /**
     *  @param orderIdList
     * @param prescriptionCodeList
     * @return
     */
    public Map<Integer, List<OrderGoodsDo>> mapOrderGoodsByOrderId(List<Integer> orderIdList, List<String> prescriptionCodeList) {
        return db().select()
                .from(ORDER_GOODS)
                .where(ORDER_GOODS.ORDER_ID.in(orderIdList))
                .and(ORDER_GOODS.PRESCRIPTION_OLD_CODE.in(prescriptionCodeList))
                .and(ORDER_GOODS.MEDICAL_AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT))
                .and(ORDER_GOODS.MEDICAL_AUDIT_STATUS.eq(OrderConstant.MEDICAL_AUDIT_DEFAULT))
                .orderBy(ORDER_GOODS.CREATE_TIME.desc())
                .fetchGroups(ORDER_GOODS.ORDER_ID, OrderGoodsDo.class);

    }


    /**
     * 更改处方号
     * @param orderId
     * @param prescriptionCode
     */
    public void updatePrescriptionCode(Integer orderId,String prescriptionCode) {
        db().update(ORDER_GOODS).set(ORDER_GOODS.PRESCRIPTION_CODE, prescriptionCode).where(ORDER_GOODS.ORDER_ID.eq(orderId)).execute();
    }


    /**
     * 审核通过 更新订单商品状态
     * @param prescriptionCode
     */
    public void updateAuditedToWaitDelivery(List<Integer> recIds,String prescriptionCode){
        db().update(ORDER_GOODS)
                .set(ORDER_GOODS.PRESCRIPTION_CODE,prescriptionCode)
                .where(ORDER_GOODS.REC_ID.in(recIds)).execute();

    }

    /**
     * 审核订单商品状态
     * @param recIds
     * @param auditStatus
     */
    public void updateAuditStatusByRecIds(List<Integer> recIds, byte auditStatus) {
        db().update(ORDER_GOODS)
                .set(ORDER_GOODS.MEDICAL_AUDIT_STATUS,auditStatus)
                .where(ORDER_GOODS.REC_ID.in(recIds)).execute();
    }

    /**
     * 获取审核订单信息
     * @param orderId 订单id
     * @return
     */
    public List<OrderGoodsSimpleAuditVo> listSimpleAuditByOrderId(Integer orderId) {
        return db().select(ORDER_GOODS.REC_ID,ORDER_GOODS.PRESCRIPTION_OLD_CODE,ORDER_GOODS.PRESCRIPTION_CODE,
                ORDER_GOODS.MEDICAL_AUDIT_TYPE,ORDER_GOODS.MEDICAL_AUDIT_STATUS,ORDER_GOODS.GOODS_ID,
                ORDER_GOODS.GOODS_NUMBER)
                .from(ORDER_GOODS)
                .where(ORDER_GOODS.ORDER_ID.eq(orderId))
                .fetchInto(OrderGoodsSimpleAuditVo.class);
    }


    public Integer medicalSalesReport(){
        db().select().from(ORDER_GOODS).where().fetch();

        return null;
    }
}
