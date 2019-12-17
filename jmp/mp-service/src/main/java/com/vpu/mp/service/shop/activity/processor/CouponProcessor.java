package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.CouponDetailMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.CouponListMpVo;
import com.vpu.mp.service.shop.activity.dao.CouponProcessorDao;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
@Slf4j
public class CouponProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor{
    @Autowired
    CouponProcessorDao couponProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_COUPON_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_COUPON;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getActivityType())).collect(Collectors.toList());

        Timestamp now = DateUtil.getLocalDateTime();
        availableCapsules.forEach(capsule->{
            Record5<Integer, String, BigDecimal, Byte, BigDecimal> couponInfo = couponProcessorDao.getGoodsCouponClosestInfo(capsule.getGoodsId(), capsule.getCatId(), capsule.getSortId(), now);
            if (couponInfo == null) {
                return;
            }
            CouponListMpVo info = new CouponListMpVo();
            info.setActCode(couponInfo.get(MRKING_VOUCHER.ACT_CODE));
            info.setDenomination(couponInfo.get(MRKING_VOUCHER.DENOMINATION));
            info.setUseConsumeRestrict(couponInfo.get(MRKING_VOUCHER.USE_CONSUME_RESTRICT));
            info.setLeastConsume(couponInfo.get(MRKING_VOUCHER.LEAST_CONSUME));
            capsule.getGoodsActivities().add(info);
        });
    }
    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        log.debug("商品详情-优惠券查询");
        List<MrkingVoucherRecord> goodsCouponForDetail = couponProcessorDao.getGoodsCouponForDetail(param.getGoodsId(), param.getCatId(), param.getSortId(), DateUtil.getLocalDateTime());
        List<Integer> couponIds = goodsCouponForDetail.stream().map(MrkingVoucherRecord::getId).collect(Collectors.toList());
        log.debug("商品详情-可使用优惠券ids:{}",couponIds.toString());
        Map<Integer, Integer> userCouponsAlreadyNum = couponProcessorDao.getUserCouponsAlreadyNum(param.getUserId(), couponIds);
        log.debug("商品详情-用户已拥有优惠券数量:{}",userCouponsAlreadyNum.toString());

        List<CouponDetailMpVo> coupons = new ArrayList<>();

        goodsCouponForDetail.forEach(record->{
            CouponDetailMpVo vo =new CouponDetailMpVo(record);
            int receivePer = record.getReceivePerPerson();
            //获取用户已拥有该券的数量，0表示未领取
            int already = userCouponsAlreadyNum.get(record.getId());
            vo.setAlreadyHas(already>0);
            if (receivePer == 0) {
                vo.setCanFetch(true);
            } else {
                if (receivePer>already) {
                    vo.setCanFetch(true);
                } else {
                    vo.setCanFetch(false);
                }
            }
            coupons.add(vo);
        });
        capsule.setCoupons(coupons);
    }

}
