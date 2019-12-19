package com.vpu.mp.service.shop.market.payaward;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.PayAwardRecord;
import com.vpu.mp.db.shop.tables.records.PayAwardRecordRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.goods.spec.ProductSmallInfoVo;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardContentBo;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardIdParam;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardListParam;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardListVo;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardOrderVo;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardParam;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardVo;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListParam;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListVo;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.PAY_AWARD;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_IS_FOREVER;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_NOT_FOREVER;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_DISABLE;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_NORMAL;

/**
 * 支付有礼
 *
 * @author 孔德成
 * @date 2019/8/12 17:57
 * @update 2019-10-31 13:52:13
 */
@Service
public class PayAwardService extends ShopBaseService {


    @Autowired
    PayAwardRecordService payAwardRecordService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderReadService orderReadService;
    @Autowired
    private OrderInfoService orderInfoService;
    /**
     * 添加
     *
     * @param param 参数
     */
    public Boolean addPayAward(PayAwardParam param) {
        PayAwardRecord payAward = db().newRecord(PAY_AWARD, param);
        payAward.setAwardList(Util.toJsonNotNull(param.getAwardList()));
        payAward.setId(null);
        return payAward.insert() > 0;
    }

    /**
     * 获取当前进行的活动
     *
     * @return
     */
    public PayAwardRecord getNowPayAward() {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        PayAwardRecord record = db().selectFrom(PAY_AWARD)
                .where(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .and(PAY_AWARD.START_TIME.ge(nowTime))
                .and(PAY_AWARD.END_TIME.le(nowTime))
                .fetchOne();
        return record;
    }

    /**
     * 删除
     *
     * @param id 活动id
     */
    public void deletePayAward(Integer id) {
        db().update(PAY_AWARD).set(PAY_AWARD.DEL_FLAG, DelFlag.DISABLE_VALUE).where(PAY_AWARD.ID.eq(id)).execute();
    }

    /**
     * 更新
     *
     * @param param 参数
     */
    public Boolean updatePayAward(PayAwardParam param) {
        if (param.getId() != null) {
            PayAwardRecord payAward = db().newRecord(PAY_AWARD, param);
            payAward.setAwardList(Util.toJsonNotNull(param.getAwardList()));
            payAward.update();
            return true;
        }
        return false;
    }

    /**
     * 根据id获取活动
     *
     * @param id
     * @return
     */
    public PayAwardVo getPayAwardId(Integer id) {
        PayAwardRecord record = db().selectFrom(PAY_AWARD).where(PAY_AWARD.ID.eq(id)).fetchOne();
        return recordToPayAwardVo(record);
    }

    /**
     * PayAwardRecord转
     * @param record PayAwardRecord
     * @return PayAwardVo
     */
    private PayAwardVo recordToPayAwardVo(PayAwardRecord record) {
        PayAwardVo payAwardVo = record.into(PayAwardVo.class);
        if (record.getAwardList() != null && !record.getAwardList().isEmpty()) {
            payAwardVo.setAwardContentList(Util.json2Object(record.getAwardList(), new TypeReference<List<PayAwardContentBo>>() {
            }, false));
            payAwardVo.setAwardList(null);
        }
        payAwardVo.getAwardContentList().forEach(award->{
            if (award.getGiftType().equals(PayAwardConstant.GIVE_TYPE_ORDINARY_COUPON)||award.getGiftType().equals(PayAwardConstant.GIVE_TYPE_SPLIT_COUPON)){
                List<CouponView> couponViewByIds =couponService.getCouponViewByIds(award.getCouponIds());
                award.setCouponView(couponViewByIds);
            }else if (award.getGiftType().equals(PayAwardConstant.GIVE_TYPE_SPLIT_GOODS)){
                ProductSmallInfoVo product = goodsService.getProductVoInfoByProductId(award.getProductId());
                award.setProduct(product);
            }
        });
        return payAwardVo;
    }

    /**
     * 获取支付有礼列表
     *
     * @param param
     * @return
     */
    public PageResult<PayAwardListVo> getPayAwardList(PayAwardListParam param) {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        SelectConditionStep<? extends Record> select = db()
                .select(PAY_AWARD.ID, PAY_AWARD.ACTIVITY_NAMES, PAY_AWARD.TIME_TYPE, PAY_AWARD.START_TIME,
                        PAY_AWARD.END_TIME, PAY_AWARD.ACT_FIRST, PAY_AWARD.STATUS, PAY_AWARD.AWARD_LIST,
                        PAY_AWARD.GOODS_AREA_TYPE, PAY_AWARD.MIN_PAY_MONEY)
                .from(PAY_AWARD)
                .where(PAY_AWARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        switch (param.getNavType()) {
            case BaseConstant.NAVBAR_TYPE_ONGOING:
                select.and(PAY_AWARD.TIME_TYPE.eq(ACTIVITY_IS_FOREVER).or(
                        PAY_AWARD.TIME_TYPE.eq(ACTIVITY_NOT_FOREVER)
                                .and(PAY_AWARD.START_TIME.lt(nowTime))
                                .and(PAY_AWARD.END_TIME.gt(nowTime))))
                        .and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL));
                break;
            case BaseConstant.NAVBAR_TYPE_NOT_STARTED:
                select.and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                      .and(PAY_AWARD.TIME_TYPE.eq(ACTIVITY_NOT_FOREVER))
                      .and(PAY_AWARD.START_TIME.gt(nowTime));
                break;
            case BaseConstant.NAVBAR_TYPE_FINISHED:
                select.and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                      .and(PAY_AWARD.TIME_TYPE.eq(ACTIVITY_NOT_FOREVER))
                      .and(PAY_AWARD.END_TIME.lt(nowTime));
                break;
            case BaseConstant.NAVBAR_TYPE_DISABLED:
                select.and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_DISABLE));
                break;
            default:
        }
        select.orderBy(PAY_AWARD.ACT_FIRST.desc(), PAY_AWARD.CREATE_TIME.desc());
        PageResult<PayAwardListVo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), PayAwardListVo.class);
        pageResult.getDataList().forEach(payAward -> {
            if (payAward.getAwardList() != null && !payAward.getAwardList().isEmpty()) {
                payAward.setAwardContentList(Util.json2Object(payAward.getAwardList(), new TypeReference<List<PayAwardContentBo>>() {
                }, false));
                payAward.setAwardList(null);
            }
            payAward.setCurrentState(Util.getActStatus(payAward.getStatus(), payAward.getStartTime(), payAward.getEndTime(), payAward.getTimeType()));
        });
        return pageResult;
    }

    /**
     * 停用启用
     *
     * @param param
     */
    public void changeStatus(PayAwardIdParam param) {
        PayAwardRecord record = db().newRecord(PAY_AWARD);
        record.setId(param.getId());
        record.refresh();
        if (ACTIVITY_STATUS_NORMAL.equals(record.getStatus())) {
            record.setStatus(ACTIVITY_STATUS_DISABLE);
        } else {
            record.setStatus(ACTIVITY_STATUS_NORMAL);
        }
        record.update();
    }

    /**
     * 支付有礼列表
     *
     * @param param
     * @return
     */
    public PageResult<PayAwardRecordListVo> getPayRewardRecordList(PayAwardRecordListParam param) {
        return payAwardRecordService.getPayRewardRecordList(param);

    }

    /**
     * 获取正在进行中的活动
     * @return
     * @param date
     */
    public PayAwardVo  getGoingPayAward(Timestamp date){
        PayAwardRecord record = db().selectFrom(PAY_AWARD)
                .where(PAY_AWARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .and(
                        PAY_AWARD.TIME_TYPE.eq(ACTIVITY_IS_FOREVER)
                                .or(
                                        PAY_AWARD.TIME_TYPE.eq(ACTIVITY_NOT_FOREVER)
                                                .and(PAY_AWARD.START_TIME.le(date))
                                                .and(PAY_AWARD.END_TIME.gt(date))
                                )
                ).orderBy(PAY_AWARD.ACT_FIRST.desc(), PAY_AWARD.CREATE_TIME.desc())
                .fetchOne();
         return recordToPayAwardVo(record);
    }

    /**
     * 获取活动
     * @param payAwardId
     * @return
     */
    public PayAwardVo  getPayAwardById(Integer payAwardId){
        PayAwardRecord payAwardRecord = db().selectFrom(PAY_AWARD).where(PAY_AWARD.ID.eq(payAwardId)).fetchOne();
        return recordToPayAwardVo(payAwardRecord);
    }


        /**
         * 获取订单的支付有礼活动
         * @param orderSn
         */
    public PayAwardOrderVo getOrderPayAward(String orderSn){
        //查询orderSN支付有礼活动记录
        PayAwardRecordRecord payAwardRecord = payAwardRecordService.getPayAwardRecordByOrderSn(orderSn);
        if (payAwardRecord==null){
            logger().info("订单orderSn:{},没有参与支付有礼活动",orderSn);
            return null;
        }
        //获取正在进行的活动
        PayAwardVo goingPayAward = getGoingPayAward(payAwardRecord.getCreateTime());
        if (goingPayAward==null){
            logger().info("当前没有进行中的支付有礼活动");
            return null;
        }
        if (!payAwardRecord.getId().equals(payAwardRecord.getAwardId())){
            logger().info("活动不一致");
            return null;
        }
        PayAwardVo payAward = getPayAwardById(payAwardRecord.getAwardId());
        PayAwardContentBo payAwardContentBo =payAward.getAwardContentList().get(payAwardRecord.getAwardTimes());
        PayAwardOrderVo payAwardOrderVo = new PayAwardOrderVo();
        payAwardOrderVo.setPayAwardContentBo(payAwardContentBo);
        payAwardOrderVo.setPayAwardSize(payAward.getAwardContentList().size());
        payAwardOrderVo.setCurrentAwardTimes(payAwardRecord.getAwardTimes());
        return payAwardOrderVo;
    }


}
