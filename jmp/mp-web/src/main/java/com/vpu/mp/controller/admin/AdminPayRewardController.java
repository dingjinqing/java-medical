package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.pojo.shop.market.payreward.PayRewardIdParam;
import com.vpu.mp.service.pojo.shop.market.payreward.PayRewardListParam;
import com.vpu.mp.service.pojo.shop.market.payreward.PayRewardListVo;
import com.vpu.mp.service.pojo.shop.market.payreward.PayRewardParam;
import com.vpu.mp.service.pojo.shop.market.payreward.PayRewardVo;
import com.vpu.mp.service.pojo.shop.market.payreward.record.PayRewardLotteryParam;

/**
 * @author 孔德成
 * @date 2019/8/12 18:12
 */
@RestController
@RequestMapping("/api/admin/market/payreward")
public class AdminPayRewardController  extends AdminBaseController{


    /**
     *  添加支付有礼活动
     * @param param payReward 添加参数
     * @return 成功失败
     */
    @PostMapping("/add")
    public JsonResult addPayReward(@RequestBody PayRewardParam param){
        Boolean flag = shop().payReward.addPayReward(param);
        if(!flag){
            return fail();
        }
        return success();
    }


    /**
     * 删除活动
     * @return  失败信息
     */
    @PostMapping("/delete")
    public JsonResult deletePayReward(@RequestBody PayRewardIdParam param){
        shop().payReward.deletePayReward(param.getId());
        return success();
    }

    /**
     * 更新活动信息
     * @return 失败信息
     */
    @PostMapping("/update")
    public JsonResult updatePayReward(@RequestBody PayRewardParam param){
        shop().payReward.updatePayReward(param);
        return success();
    }


    /**
     * 根据id获取活动信息
     * @param param id
     * @return 活动信息
     */
    @PostMapping("/get")
    public JsonResult getPayReward(@RequestBody PayRewardIdParam param){
        PayRewardVo payReward = shop().payReward.getPayRewardId(param.getId());
        return success(payReward);
    }

    /**
     * 支付有礼列表
     * @param param 查询参数
     * @return JsonResult
     */
    @PostMapping("/list")
    public JsonResult getPayRewardList(@RequestBody PayRewardListParam param){
        PageResult<PayRewardListVo> payRewardList = shop().payReward.getPayRewardList(param);
        return success(payRewardList);
    }

    /**
     * 活动停用启用
     * @param param id
     * @return JsonResult
     */
    @PostMapping("/change/status")
    public JsonResult changeStatus(@RequestBody PayRewardIdParam param){
        shop().payReward.changeStatus(param);
        return success();
    }


    /**
     *  获取优惠券的列表详情
     *
     * @return JsonResult
     */
    @PostMapping("/coupon/list")
    public JsonResult getCouponHoldList(@RequestBody  CouponHoldListParam param){
        PageResult<CouponHoldListVo> couponHoldList = shop().payReward.getCouponHoldList(param);
        return success(couponHoldList);
    }

    /**
     * 获取抽奖列表
     *
     * @return JsonResult
     */
    @PostMapping("/lottery/list")
    public JsonResult getLotteryList(@RequestBody  PayRewardLotteryParam param){
        PageResult<LotteryRecordPageListVo> lotteryList = shop().payReward.getLotteryList(param);
        return success(lotteryList);
    }



}
