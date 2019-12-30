package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.wxapp.collectGift.SetCollectGiftVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponDetailVo;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.coupon.MpCouponService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.USER;

/**
 *	收藏有礼开关配置
 * @author liangchen
 * @date 2019年8月20日
 */
@Service
@Slf4j
public class CollectGiftConfigService extends BaseShopConfigService{
    @Autowired
    public MemberService member;

    @Autowired
    public ScoreCfgService score;

    @Autowired
    public MpCouponService mpCoupon;

    @Autowired
    public CouponService coupon;
	/**	收藏有礼K值 */
	private static final String K_COLLECT_GIFT = "collect_gift";
	
	/**
	 * 	返回收藏有礼配置信息
	 *	返回开关配置状态，默认为关
	 * @return param 收藏有礼配置信息(object)
	 */
	public CollectGiftParam collectGiftConfig(){
		CollectGiftParam param = this.getJsonObject(K_COLLECT_GIFT,CollectGiftParam.class);
		if (param == null) {
			param = new CollectGiftParam();
			this.setJsonObject(K_COLLECT_GIFT, param);
		}
		return param;
	}

	/**
	 *	开关控制
	 */
	public void updateStatus() {
		//	查看当前状态
		CollectGiftParam param = this.getJsonObject(K_COLLECT_GIFT,CollectGiftParam.class);
		log.info("收藏有礼开关配置对应value值："+param);
		log.info("收藏有礼开关配置对应开关状态on_off："+param.getOnOff());
		int nowStatus = param.getOnOff();
		//	根据当前状态选择开或关
		if (0==nowStatus) {
			param.setOnOff(1);
			this.setJsonObject(K_COLLECT_GIFT,param);
		} else {
			param.setOnOff(0);
			this.setJsonObject(K_COLLECT_GIFT, param);
		}
	}
	
	/**
	 *	修改收藏有礼配置信息
	 * @param param 收藏有礼配置
	 */
	
	public void updateCollectGiftConfig(CollectGiftParam param) {
		this.setJsonObject(K_COLLECT_GIFT, param);
	}

    /**
     *小程序端收藏有礼是否有效
     * @param userId
     * @return
     */
    public CollectGiftParam collectGiftConfig(Integer userId) {
        //收藏有礼开关是否开启
        CollectGiftParam param = this.getJsonObject(K_COLLECT_GIFT,CollectGiftParam.class);
        if (param == null) {
            param = new CollectGiftParam();
            this.setJsonObject(K_COLLECT_GIFT, param);
        }
        //判断活动是否生效
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
        if(!(param.getStartTime().before(nowDate) && nowDate.before(param.getEndTime()))){
            param.setOnOff(0);
        }
        //判断当前用户是否第一次参与收藏有礼活动
        Integer into = db().select(USER.GET_COLLECT_GIFT).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().into(Integer.class);
        if(into == 1){//已参与，不展示支付有礼图标
            param.setOnOff(0);
        }
        return param;
    }

	public SetCollectGiftVo setRewards(Integer userId){
	    //收藏有礼对应活动规则
        CollectGiftParam info = this.collectGiftConfig();

        SetCollectGiftVo setResultVo = new SetCollectGiftVo();
        List<AvailCouponDetailVo> couponList = new ArrayList<>();
        //发放积分
        if(info.getScore() > 0){
            /** -交易明细类型 */
            Byte tradeType = 4;
            /** -资金流向 */
            Byte tradeFlow = 1;
            ScoreParam scoreParam = new ScoreParam();
            scoreParam.setScore(info.getScore());
            scoreParam.setScoreStatus(ScoreStatusConstant.USED_SCORE_STATUS);
            scoreParam.setDesc("score");
            scoreParam.setRemarkCode(RemarkTemplate.COLLECT_HAS_GIFT.code);
            //scoreParam.setRemark("收藏有礼");
            Integer subAccountId = 0;
            try {
                member.score.updateMemberScore(scoreParam,subAccountId,userId, tradeType,tradeFlow,"");
                setResultVo.setScore(info.getScore());
            } catch (MpException e) {
                logger().info("积分更新失败");
            }
        }
        //发放优惠券
        if(info.getCouponIds() != ""){
            List<Integer> ids = Util.splitValueToList(info.getCouponIds());
            for(Integer id : ids){
                Timestamp nowDate = new Timestamp(System.currentTimeMillis());
                //判断领取限制
                mpGetCouponParam param = new mpGetCouponParam();
                param.setCouponId(id);
                CouponListVo couponData = mpCoupon.getCouponData(param);
                //通过alias_code查看优惠券是否存在
                if (StringUtils.isEmpty(couponData)) {
                    setResultVo.setMsg((byte)1);
                    return setResultVo;
                }
                //是否过期
                if (couponData.getValidityType() == 0 && couponData.getEndTime().before(nowDate)) {
                    setResultVo.setMsg((byte)2);
                    return setResultVo;
                }
                //是否停用
                if (couponData.getEnabled() == 0) {
                    setResultVo.setMsg((byte)3);
                    return setResultVo;
                }
                //库存判断
                if (couponData.getLimitSurplusFlag() == 0 && couponData.getSurplus() <= 0) {
                    setResultVo.setMsg((byte)4);
                    return setResultVo;
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
                coupon.couponGiveService.handlerCouponGive(couponParam);

                //返回积分，优惠券相关信息
                AvailCouponDetailVo couponInfo = db().select(MRKING_VOUCHER.ID,MRKING_VOUCHER.ACT_CODE, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.TYPE)
                    .from(MRKING_VOUCHER)
                    .where(MRKING_VOUCHER.ID.eq(id)).fetchOne().into(AvailCouponDetailVo.class);
                couponList.add(couponInfo);

            }
            setResultVo.setCouponDetail(couponList);
            db().update(USER).set(USER.GET_COLLECT_GIFT,(byte)1).where(USER.USER_ID.eq(userId)).execute();
        }
        setResultVo.setMsg((byte)0);
        return setResultVo;
    }
}
