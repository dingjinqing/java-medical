package com.vpu.mp.service.shop.market.freeshipping;

import com.vpu.mp.db.main.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.freeshipping.*;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.main.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.FreeShipping.FREE_SHIPPING;
import static com.vpu.mp.db.shop.tables.FreeShippingRule.FREE_SHIPPING_RULE;
import static com.vpu.mp.service.foundation.data.BaseConstant.*;
import static com.vpu.mp.service.shop.market.freeshipping.FreeShippingRuleService.*;

/**
 * 免邮费
 *
 * @author 孔德成
 * @date 2019/7/29 17:04
 */
@Service
public class FreeShippingService extends ShopBaseService {

    @Autowired
    public FreeShippingRuleService ruleService;
    @Autowired
    public FreeShippingGoodsService freeShipGoods;
    @Autowired
    QrCodeService qrCodeService;


    /**
     * 获取商品使用满包邮规则
     *
     * @param goodsId
     */
    public void getGoodsFreeShipRule(Integer goodsId) {
        GoodsRecord goods = db().selectFrom(GOODS)
                .where(GOODS.GOODS_ID.eq(goodsId))
                .fetchOne();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Result<FreeShippingRecord> freeShippingList = db()
                .selectFrom(FREE_SHIPPING)
                .where(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_IS_FOREVER)
                        .or(DSL.field(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_NOT_FOREVER)
                                .and(FREE_SHIPPING.START_TIME.lt(date)
                                        .and(FREE_SHIPPING.END_TIME.gt(date))))))
                .and(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(FREE_SHIPPING.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .fetch();
        freeShippingList.forEach(freeShip -> {
            Result<FreeShippingRuleRecord> ruleList = ruleService.getFreeShippingRule(freeShip.getId());
            if (freeShip.getType().equals(GOODS_AREA_TYPE_ALL)) {
                ruleList.forEach(freeShipRule -> {
                    FreeShippingGoodsRuleVo ruleVo = new FreeShippingGoodsRuleVo();
                    ruleVo.setAction(5);
                    ruleVo.setIdentityId(freeShipRule.getId());
                    ruleVo.setShippingId(freeShipRule.getShippingId());
                    ruleVo.setConType(freeShipRule.getConType());
                });

            } else {
                if (freeShip.getRecommendGoodsId().contains(goodsId.toString()) ||
                        (goods.getSortId() != null && freeShip.getRecommendSortId().contains(goods.getSortId().toString())) ||
                        (goods.getCatId() != null && freeShip.getRecommendCatId().contains(goods.getCatId().toString()))) {
                    ruleList.forEach(freeShipRule -> {
                        FreeShippingGoodsRuleVo ruleVo = new FreeShippingGoodsRuleVo();
                        ruleVo.setAction(5);
                        ruleVo.setIdentityId(freeShipRule.getId());
                        ruleVo.setShippingId(freeShipRule.getShippingId());
                        ruleVo.setConType(freeShipRule.getConType());
                    });
                }
            }
        });
    }


    /**
     * 根据规则获取活动
     *
     * @param ruleId
     * @return
     */
    public Record getInfoByRule(Integer ruleId) {
        return db()
                .select()
                .from(FREE_SHIPPING_RULE)
                .leftJoin(FREE_SHIPPING)
                .on(FREE_SHIPPING_RULE.SHIPPING_ID.eq(FREE_SHIPPING.ID))
                .where(FREE_SHIPPING_RULE.ID.eq(ruleId))
                .orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING.CREATE_TIME.desc())
                .fetchOne();
    }


    /**
     * 新增满包邮
     *
     * @param param
     */
    public void addFreeShipping(FreeShippingParam param) {
        transaction(() -> {
            param.setId(null);
            FreeShippingRecord record = db().newRecord(FREE_SHIPPING, param);
            record.setStatus(ACTIVITY_STATUS_NORMAL);
            record.insert();
            param.getRuleList().forEach(rule -> {
                FreeShippingRuleRecord ruleRecord = db().newRecord(FREE_SHIPPING_RULE, rule);
                ruleRecord.setShippingId(record.getId());
                ruleRecord.insert();
            });
        });

    }

    /**
     * 更新满包邮
     *
     * @param param
     */
    public void updateFreeShipping(FreeShippingParam param) {
        //开启事务
        transaction(() -> {
            FreeShippingRecord record = db().newRecord(FREE_SHIPPING, param);
            record.update();
            List<Integer> ruleList = new ArrayList<>();
            //更新规则
            param.getRuleList().forEach(rule -> {
                FreeShippingRuleRecord ruleRecord = db().newRecord(FREE_SHIPPING_RULE, rule);
                ruleRecord.setShippingId(record.getId());
                if (ruleRecord.getId() == null) {
                    ruleRecord.insert();
                } else {
                    ruleRecord.update();
                }
                ruleList.add(ruleRecord.getId());
            });
            //删除多余的规则
            ruleService.deleteByid(record.getId(), ruleList);
        });

    }


    /**
     * 获取满包邮
     *
     * @param param
     * @return
     */
    public PageResult<FreeShippingVo> getFreeShippingList(FreeShipQueryParam param) {
        SelectConditionStep<Record> select = db()
                .select(FREE_SHIPPING.asterisk())
                .from(FREE_SHIPPING)
                .where(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        buildOption(select, param);
        select.orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING.ID.desc());
        PageResult<FreeShippingVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), FreeShippingVo.class);
        result.getDataList().forEach(freeShipping -> {
            List<FreeShippingRuleVo> ruleVoList = ruleService.getFreeShippingRule(freeShipping.getId()).into(FreeShippingRuleVo.class);
            freeShipping.setRuleList(ruleVoList);
            Byte actStatus = Util.getActStatus(freeShipping.getStatus(), freeShipping.getStartTime(), freeShipping.getEndTime(), freeShipping.getExpireType());
            freeShipping.setCurrentStatus(actStatus);
        });
        return result;
    }

    private void buildOption(SelectConditionStep<Record> select, FreeShipQueryParam param) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (param.getNavType() != null) {
            switch (param.getNavType()) {
                case NAVBAR_TYPE_ONGOING:
                    //进行中的
                    select.and(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_IS_FOREVER))
                            .or(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_NOT_FOREVER)
                                    .and(FREE_SHIPPING.START_TIME.le(timestamp))
                                    .and(FREE_SHIPPING.END_TIME.ge(timestamp)))
                            .and(FREE_SHIPPING.STATUS.eq((ACTIVITY_STATUS_NORMAL)));
                    break;
                case NAVBAR_TYPE_NOT_STARTED:
                    //未开始
                    select.and(FREE_SHIPPING.START_TIME.gt(timestamp))
                            .and(FREE_SHIPPING.STATUS.eq(ACTIVITY_STATUS_NORMAL));
                    break;
                case NAVBAR_TYPE_FINISHED:
                    //已过期
                    select.and(FREE_SHIPPING.END_TIME.lt(timestamp))
                            .and(FREE_SHIPPING.STATUS.eq(ACTIVITY_STATUS_NORMAL));
                    break;
                case NAVBAR_TYPE_DISABLED:
                    //已停用
                    select.and(FREE_SHIPPING.STATUS.eq(ACTIVITY_STATUS_DISABLE));
                    break;
                default:
            }
        }
    }

    /**
     * 根据规则id获取满包邮活动
     *
     * @param ruleId
     * @return
     */
    public FreeShippingRecord getFreeShippingByRuleId(Integer ruleId) {
        return db().select(FREE_SHIPPING.asterisk()).from(FREE_SHIPPING)
                .leftJoin(FREE_SHIPPING_RULE).on(FREE_SHIPPING_RULE.SHIPPING_ID.eq(FREE_SHIPPING.ID))
                .where(FREE_SHIPPING_RULE.ID.eq(ruleId))
                .orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING_RULE.CREATE_TIME.desc())
                .fetchOneInto(FreeShippingRecord.class);
    }

    /**
     * 获取满包邮活动
     *
     * @param id
     * @return
     */
    public FreeShippingRecord getFreeShippingById(Integer id) {
        return db().selectFrom(FREE_SHIPPING).where(FREE_SHIPPING.ID.eq(id)).fetchOne();
    }

    /**
     * 改变状态
     *
     * @param id
     * @return
     */
    public void changeStatus(Integer id) {
        FreeShippingRecord record = getFreeShippingById(id);
        if (record.getStatus().equals(ACTIVITY_STATUS_NORMAL)) {
            db().update(FREE_SHIPPING)
                    .set(FREE_SHIPPING.STATUS, ACTIVITY_STATUS_DISABLE)
                    .where(FREE_SHIPPING.ID.eq(id))
                    .execute();
        } else {
            db().update(FREE_SHIPPING)
                    .set(FREE_SHIPPING.STATUS, ACTIVITY_STATUS_NORMAL)
                    .where(FREE_SHIPPING.ID.eq(id))
                    .execute();
        }
    }


    /**
     * 分享满包邮活动
     *
     * @param ruleId
     * @return
     */
    public ShareQrCodeVo shareFreeShipping(Integer ruleId) {
        String pathParam=String.format("ruleId=%d", ruleId);
        String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.FULL_SHIP, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.FULL_SHIP.getUrl());
        return vo;
    }

    /**
     * 删除满包邮
     *
     * @param id
     * @return
     */
    public int deleteFreeShipping(Integer id) {
        ruleService.deleteByid(id, null);
        return db().update(FREE_SHIPPING)
                .set(FREE_SHIPPING.DEL_FLAG, DelFlag.DISABLE_VALUE)
                .where(FREE_SHIPPING.ID.eq(id))
                .execute();
    }

    /**
     * 获取满包邮活动
     *
     * @param timestamp
     */
    public List<FreeShippingVo> getValidFreeList(Timestamp timestamp) {
        List<FreeShippingVo> freeShippingVos = db().selectFrom(FREE_SHIPPING).where(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(FREE_SHIPPING.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .and(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_IS_FOREVER)
                        .or(FREE_SHIPPING.EXPIRE_TYPE.eq(ACTIVITY_NOT_FOREVER)
                                .and(FREE_SHIPPING.START_TIME.le(timestamp))
                                .and(FREE_SHIPPING.END_TIME.ge(timestamp))))
                .orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING.CREATE_TIME.desc())
                .fetchInto(FreeShippingVo.class);
        freeShippingVos.forEach(freeShipping -> {
            List<FreeShippingRuleVo> ruleVoList = ruleService.getFreeShippingRule(freeShipping.getId()).into(FreeShippingRuleVo.class);
            freeShipping.setRuleList(ruleVoList);
            Byte actStatus = Util.getActStatus(freeShipping.getStatus(), freeShipping.getStartTime(), freeShipping.getEndTime(), freeShipping.getExpireType());
            freeShipping.setCurrentStatus(actStatus);
        });
        return freeShippingVos;
    }

    /**
     * 判断是否符合满包邮条件
     * @param address  地址
     * @param tolalNumberAndPrice 数量和金额
     * @param ruleList 规则列表
     * @return
     */
    public boolean checkedFreeshipCondition(UserAddressVo address, BigDecimal[] tolalNumberAndPrice, List<FreeShippingRuleVo> ruleList) {
        if (address==null){
            return false;
        }
        for (FreeShippingRuleVo rule : ruleList) {
            List<Integer> districtCode = Util.stringToList(rule.getArea());
            if (matchDistrictCode(address.getDistrictCode(),districtCode)) {
                logger().info("满包邮-在包邮地区");
                if ((rule.getConType().equals(CONTYPE_NUM)||rule.getConType().equals(CONTYPE_NUM_MONEY)) && tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER].intValue() >= rule.getNum()) {
                    logger().info("满{}件包邮,商品数量{}",rule.getNum().toString(),tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER].toString());
                    return true;
                }
                if ((rule.getConType().equals(CONTYPE_MONEY)||rule.getConType().equals(CONTYPE_NUM_MONEY))&&tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE].compareTo(rule.getMoney())>=0){
                    logger().info("满{}元包邮,商品价格{}",rule.getMoney().toString(),tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE].toString());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *  判断是否匹配当前区县码
     * @param districtCode  区县码
     * @param areaList 地区列表
     * @return true, false
     */
    public boolean matchDistrictCode(Integer districtCode,List<Integer> areaList ){
         if (districtCode!=null){
             Integer deliverCodeProvince = districtCode/1000*1000;
             Integer deliverCodeCity = districtCode/100*100;
             if (areaList.contains(districtCode)){
                 return true;
             }
             if (areaList.contains(deliverCodeCity)){
                 return true;
             }
             if (areaList.contains(deliverCodeProvince)){
                 return true;
             }
             /**
              * 全国
              */
             return areaList.contains(0);
         }
         return false;
    }
}
