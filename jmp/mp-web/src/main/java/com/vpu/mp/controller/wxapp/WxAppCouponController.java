package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant;
import com.vpu.mp.service.pojo.wxapp.coupon.*;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.dao.ScoreDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户优惠券
 * @author 常乐
 * @date 2019年9月24日
 */
@RestController
@RequestMapping("/api/wxapp/coupon")
public class WxAppCouponController extends WxAppBaseController {
	@Autowired
	private ScoreDaoService scoreDao;

	@Autowired
	private ScoreService score;

	/**
	 * 用户优惠券列表
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult availCoupon(@RequestBody AvailCouponParam param) {
		AvailCouponListVo list = new AvailCouponListVo();
		Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(userId);
		PageResult<AvailCouponVo> couponList = shop().coupon.getCouponByUser(param);
		list = shop().coupon.getEachStatusNum(userId,list);
		list.setCouponList(couponList);
		return this.success(list);
	}
	
	/**
	 * 优惠券详情
	 * @param param
	 * @return
	 */
	@PostMapping("/detail")
	public JsonResult availCouponDetail(@RequestBody AvailCouponDetailParam param) {
		AvailCouponDetailVo couponDetail = shop().coupon.getCouponDetail(param);
		return this.success(couponDetail);
	}

    /**
     * 积分兑换优惠券，兑换详情页
     * @param param
     * @return
     */
    @PostMapping("/detail/byScore")
    public JsonResult CouponDetailByScore(@RequestBody AvailCouponDetailParam param) {
        AvailCouponDetailVo couponDetail = shop().coupon.getCouponDetailByScore(param);
        return this.success(couponDetail);
    }

	/**
	 * 用户立即领取优惠券
	 * @return
	 */
	@PostMapping("/get")
	public JsonResult getCoupon(@RequestBody mpGetCouponParam param) {
		Integer userId = wxAppAuth.user().getUserId();
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());
		System.out.println(userId);
		//判断领取限制
		CouponListVo couponData = shop().mpCoupon.getCouponData(param);
		System.out.println(couponData);
		//通过alias_code查看优惠券是否存在
		if (StringUtils.isEmpty(couponData)) {
			return this.success("领取失败");
		}

		//是否过期
		if (couponData.getValidity() <= 0 && couponData.getValidityHour() <= 0 && couponData.getValidityMinute() <= 0 && couponData.getEndTime().before(nowDate)) {
			return this.success("优惠券已过期");
		}

		//是否停用
		if (couponData.getEnabled() == 0) {
			return this.success("优惠券已停用");
		}

		//库存判断
		if (couponData.getLimitSurplusFlag() == 0 && couponData.getSurplus() <= 0) {
			return this.success("优惠券库存不足");
		}

		//积分兑换判断
		if (couponData.getUseScore() == 1 && couponData.getScoreNumber() > 0) {
			int availCoupon = scoreDao.calculateAvailableScore(userId);

			//查看用户可用积分
			if (couponData.getScoreNumber() > availCoupon) {
				return this.success("积分不足 ");
			} else {
				ScoreParam scoreParam = new ScoreParam();
				scoreParam.setScore(couponData.getScoreNumber());
				scoreParam.setScoreStatus(ScoreStatusConstant.USED_SCORE_STATUS);
				scoreParam.setDesc("score");
				scoreParam.setRemark("领取优惠券");

				Integer subAccountId = 0;

				/** -交易明细类型 */
				Byte tradeType = 4;
				/** -资金流向 */
				Byte tradeFlow = 1;

				try {
					score.updateMemberScore(scoreParam,subAccountId,userId, tradeType,tradeFlow,"");
				} catch (MpException e) {
					logger().info("积分更新失败");
					return fail(e.getErrorCode().getMessage());
				}
				return this.success();
			}
		}

		//判断优惠券领取限制
		if(couponData.getReceivePerPerson().intValue() != 0){//有限制领取
			Integer alreadyGet = shop().mpCoupon.couponAlreadyGet(userId, couponData.getId());
			System.out.println(11);
			System.out.println(alreadyGet);
				if(couponData.getReceivePerPerson() > alreadyGet){
					//添加优惠券到用户，调用定向发券通用方法
					CouponGiveQueueParam couponParam = new CouponGiveQueueParam();
					List<Integer> userIds = new ArrayList();
					 String[] couponArray = {couponData.getId().toString()};
					userIds.add(userId);

					couponParam.setUserIds(userIds);
					couponParam.setActId(0);
					couponParam.setCouponArray(couponArray);
					couponParam.setAccessMode((byte) 1);
					couponParam.setGetSource((byte) 5);
					List<Integer> res = shop().coupon.couponGiveService.handlerCouponGive(couponParam);
				}

		}
		return this.success();
	}
}
