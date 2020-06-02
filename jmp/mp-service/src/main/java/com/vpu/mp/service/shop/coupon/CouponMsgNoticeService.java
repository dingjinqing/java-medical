package com.vpu.mp.service.shop.coupon;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * 优惠券相关消息通知
 * @author liangchen
 * @date 2020.05.19
 */
@Service
public class CouponMsgNoticeService extends ShopBaseService {
    /**
     * 领取/发放优惠券后发送消息通知
     * @param userId 用户id
     * @param couponName 优惠券名称
     * @param startTime 领取时间
     * @param endTime 有效日期
     * @param couponDesc 优惠内容
     */
    public void sendCouponMsgNotice(Integer userId, String couponName, Timestamp startTime,Timestamp endTime,String couponDesc){
        logger().info("开始处理当前卡券领取成功消息,用户id为：{},优惠券名称为：{}",userId,couponName);
        String shopName = saas().shop.getShopNickName(getShopId());
        String firstData = "您好，您的"+couponDesc+"优惠券已成功领取";
        //公众号消息
        String[][] mpData = new String[][] {
            {firstData},
            {shopName},
            {couponName},
            {endTime.toString()},
            {startTime.toString()},
            {"请及时使用"}
        };
        List<Integer> userIdList = Collections.singletonList(userId);
        RabbitMessageParam msgParam = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.GET_COUPON).data(mpData).build())
            .page("pages/coupon/coupon")
            .shopId(getShopId())
            .userIdList(userIdList)
            .type(MessageTemplateConfigConstant.GET_COUPON).build();
        saas().taskJobMainService.dispatchImmediately(msgParam,RabbitMessageParam.class.getName(),getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }
}
