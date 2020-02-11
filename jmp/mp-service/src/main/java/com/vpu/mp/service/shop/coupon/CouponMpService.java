package com.vpu.mp.service.shop.coupon;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleCoupon;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.wxapp.coupon.CouponPageDecorationVo;
import com.vpu.mp.service.shop.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * @author: 王兵兵
 * @create: 2019-11-19 19:03
 **/
@Service
public class CouponMpService extends ShopBaseService {
    @Autowired
    public MemberService member;

    @Autowired
    public CouponService coupon;
    /**
     * 获取装修模块优惠券列表
     * @param moduleCoupon
     * @param userId
     * @return
     */
    public List<CouponPageDecorationVo> getPageIndexCouponList(ModuleCoupon moduleCoupon, int userId){
        List<CouponPageDecorationVo> couponList = new ArrayList<>();
        for(ModuleCoupon.Coupon coupon : moduleCoupon.getCouponAyy()){
            CouponPageDecorationVo couponVo = getCouponPageDecorationVo(coupon.getCouponId());

            //赋值该优惠券的可用状态，优先显示已领取
            if(couponVo == null){
                couponVo = new CouponPageDecorationVo();
                //已领取或领取达到极限
                couponVo.setStatus((byte)6);
            } else if(couponVo.getReceivePerPerson() > 0){
                //用户已 领取/发放 优惠券数
                int getCouponAmount = getUserCouponAmount(coupon.getCouponId(),userId);
                if(getCouponAmount >= couponVo.getReceivePerPerson()){
                    //已领取或领取达到极限
                    couponVo.setStatus((byte)5);
                }
            }else if(couponVo.getEnabled().equals(BaseConstant.COUPON_ENABLED_DISABLED)){
                //停用
                couponVo.setStatus((byte)4);
            }else if(couponVo.getValidityType().equals(BaseConstant.COUPON_VALIDITY_TYPE_FIXED)){
                if(DateUtil.getLocalDateTime().after(couponVo.getEndTime())){
                    //已过期
                    couponVo.setStatus((byte)2);
                }
            }else if(couponVo.getSurplus() == 0 && couponVo.getLimitSurplusFlag().equals(BaseConstant.COUPON_LIMIT_SURPLUS_FLAG_LIMITED)){
                //库存不足
                couponVo.setStatus((byte)3);
            }

            couponList.add(couponVo);
        }
        return couponList;
    }

    /**
     * 用户已 领取/发放 优惠券数
     * @param couponId
     * @param userId
     * @return
     */
    public int getUserCouponAmount(int couponId,int userId){
        return db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId))).fetchSingle().into(Integer.class);
    }

    private CouponPageDecorationVo getCouponPageDecorationVo(int couponId){
        Optional<CouponPageDecorationVo> vo =  db().select().from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(couponId)).fetchOptionalInto(CouponPageDecorationVo.class);
        return vo.orElse(null);
    }

    /**
     * 查询优惠券基本信息
     * @param param
     * @return
     */
    public CouponListVo getCouponData(mpGetCouponParam param){
        CouponListVo couponData = db().select().from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(param.getCouponId()))
            .fetchOneInto(CouponListVo.class);
        return couponData;
    }

    /**
     * 用户已领取某优惠券数量
     * @param userId
     * @param couponId
     */
    public Integer couponAlreadyGet(Integer userId,Integer couponId){
        int res = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId)).and(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId))
            .fetchOneInto(Integer.class);
        return res;
    }

    /**
     * 领取优惠券到用户
     * @param param 0：领取成功；1：优惠券不存在；2：优惠券过期；3：优惠券体用；4：库存为0；5：可用积分不足；6：积分更新失败；7；领取次数达上限
     * @return
     */
    public Byte fetchCoupon(mpGetCouponParam param){
        CouponListVo couponData = this.getCouponData(param);
        Integer userId = param.getUserId();
        Byte couponGetStatus = this.couponGetStatus(param);
        Byte fetchCouponStatus = couponGetStatus;
        if(couponGetStatus != 0){
            return fetchCouponStatus;
        }
        //积分兑换判断
        if (couponData.getUseScore() == 1 && couponData.getScoreNumber() > 0) {
            int availCoupon = member.score.getTotalAvailableScoreById(userId);
            //查看用户可用积分
            if (couponData.getScoreNumber() > availCoupon) {
                //可用积分不足
                fetchCouponStatus = 5;
            } else {
                ScoreParam scoreParam = new ScoreParam();
                scoreParam.setScore(-(couponData.getScoreNumber()));
                scoreParam.setScoreStatus(ScoreStatusConstant.USED_SCORE_STATUS);
                scoreParam.setDesc("score");
                scoreParam.setRemarkCode(RemarkTemplate.RECEIVE_COUPON.code);
                scoreParam.setUserId(userId);
                //scoreParam.setRemark("领取优惠券");
                Integer subAccountId = 0;

                /** -交易明细类型 */
                Byte tradeType = 4;
                /** -资金流向 */
                Byte tradeFlow = 1;
                try {
                	member.score.updateMemberScore(scoreParam,subAccountId, tradeType,tradeFlow);
                } catch (MpException e) {
                    logger().info("积分更新失败");
                    fetchCouponStatus =6;
                }
            }
        }
        CouponGiveQueueParam couponParam = new CouponGiveQueueParam();
        List<Integer> userIds = new ArrayList();
        String[] couponArray = {couponData.getId().toString()};
        userIds.add(userId);
        couponParam.setUserIds(userIds);
        couponParam.setActId(0);
        couponParam.setCouponArray(couponArray);
        couponParam.setAccessMode((byte) 1);
        couponParam.setGetSource((byte) 5);
        //判断优惠券领取限制
        if(couponData.getReceivePerPerson().intValue() != 0){//有限制领取
            Integer alreadyGet = this.couponAlreadyGet(userId, couponData.getId());
            if(couponData.getReceivePerPerson() > alreadyGet){
                //添加优惠券到用户，调用定向发券通用方法
                coupon.couponGiveService.handlerCouponGive(couponParam);
            }else{
                //领取次数达上限
                fetchCouponStatus = 7;
            }
        }else{
            coupon.couponGiveService.handlerCouponGive(couponParam);
        }
        return fetchCouponStatus;
    }

    /**
     * 优惠券是否可领取状态
     * @param param 0：可正常领取；1：优惠券不存在；2：优惠券过期；3：优惠券体用；4：库存为0
     * @return
     */
    public Byte couponGetStatus(mpGetCouponParam param){
        Timestamp nowDate = DateUtil.getLocalDateTime();
        //判断领取限制
        CouponListVo couponData = this.getCouponData(param);
        Byte couponGetStatus;
        //通过alias_code查看优惠券是否存在
        if (StringUtils.isEmpty(couponData)) {
           couponGetStatus = 1;
        }else if(couponData.getValidity() <= 0 && couponData.getValidityHour() <= 0 && couponData.getValidityMinute() <= 0 && couponData.getEndTime().before(nowDate)){//是否过期
            //是否过期
            couponGetStatus = 2;
        } else if (couponData.getEnabled() == 0) {
            //是否停用
            couponGetStatus = 3;
        } else if (couponData.getLimitSurplusFlag() == 0 && couponData.getSurplus() <= 0) {
            //库存判断
            couponGetStatus = 4;
        }else{
            //正常领取
            couponGetStatus = 0;
        }
        return couponGetStatus;
    }

    /**
     * 删除优惠券
     * @param couponId
     * @return
     */
    public Integer delCoupon(Integer couponId){
        int res = db().update(CUSTOMER_AVAIL_COUPONS).set(CUSTOMER_AVAIL_COUPONS.DEL_FLAG, (byte) 1)
            .where(CUSTOMER_AVAIL_COUPONS.ID.eq(couponId)).execute();
        return res;

    }
}
