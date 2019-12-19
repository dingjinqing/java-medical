package com.vpu.mp.service.shop.task.wechat;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Result;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.coupon.CouponWxVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.store.service.order.StoreAppointmentRemindVo;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * 小程序公众号相关的定时任务
 * 
 * @author zhaojianqiang
 * @time 下午2:14:03
 */
@Service
@Slf4j
public class MaMpScheduleTaskService extends ShopBaseService {
	@Autowired
	private CouponService coupon;
	@Autowired
	private ServiceOrderService serviceOrder;
	
	

	/**
	 * 卡券到期提醒
	 * @param shopId
	 * @return
	 */
	public String expiringCouponNotify() {
		// 查找shopId对应的公众号
		String officeAppId = saas.shop.mp.findOffcialByShopId(getShopId());
		if (officeAppId == null) {
			logger().info("店铺" + getShopId() + "没有关注公众号");
			return null;
		}
		List<CouponWxVo> list = coupon.getExpiringCouponList();
		String page = "pages/couponlist/couponlist";
		String keyword1 = "尊敬的用户，您的优惠券";
		String keyword11 = "即将到期";
		for (CouponWxVo couponWxVo : list) {
			String couponName = couponWxVo.getActName();
			List<Integer> userIdList = new ArrayList<Integer>();
			MpOfficialAccountUserRecord wxUserInfo = checkMp(couponWxVo.getWxUnionId(), officeAppId);
			if(null==wxUserInfo) {
				continue;
			}
			userIdList.add(wxUserInfo.getRecId());
			logger().info("userIdList" + userIdList);
			String[][] data = new String[][] { { keyword1 + couponName + keyword11, "#173177" }, { "", "#173177" },
					{ couponName, "#173177" },
					{ DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, couponWxVo.getEndTime()), "#173177" },
					{ "", "#173177" } };
			RabbitMessageParam param = RabbitMessageParam.builder()
					.mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.COUPON_EXPIRE).data(data).build())
					.page(page).shopId(getShopId()).userIdList(userIdList).type(RabbitParamConstant.Type.MP_TEMPLE_TYPE)
					.build();
			logger().info("准备发");
			saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
					TaskJobEnum.SEND_MESSAGE.getExecutionType());
		}
		return null;

	}
	/**
	 * 校验MpOfficialAccountUserRecord
	 * @param WxUnionId
	 * @param officeAppId
	 * @return
	 */
	private MpOfficialAccountUserRecord checkMp(String WxUnionId,String officeAppId) {
		
		if (StringUtils.isEmpty(WxUnionId)) {
			logger().info("用户" + WxUnionId + "没有关注公众号");
			return null;
		}
		MpOfficialAccountUserRecord wxUserInfo = saas.shop.mpOfficialAccountUserService.getUserByUnionIdAndAppId(WxUnionId, officeAppId);
		if (wxUserInfo == null) {
			logger().info("表中没有数据" + WxUnionId);
			return null;
	
		}
		return wxUserInfo;
		
	}
	
	//预约服务提前一小时提醒
	public String sendAppointmentRemind() {
		String officeAppId = saas.shop.mp.findOffcialByShopId(getShopId());
		if (officeAppId == null) {
			logger().info("店铺" + getShopId() + "没有关注公众号");
			return null;
		}
		List<StoreAppointmentRemindVo> serviceOrderList = serviceOrder.getDealServiceOrder();
		logger().info("serviceOrderList大小"+serviceOrderList.size());
		String first = "您好，您的预约即将到期";
		String remake = "感谢您的使用";
		for (StoreAppointmentRemindVo serviceOrderRecord : serviceOrderList) {
			List<Integer> userIdList = new ArrayList<Integer>();
			MpOfficialAccountUserRecord wxUserInfo = checkMp(serviceOrderRecord.getWxUnionId(), officeAppId);
			if(null==wxUserInfo) {
				continue;
			}
			userIdList.add(wxUserInfo.getRecId());
			String page = "pages/appointinfo/appointinfo?order_sn=" + serviceOrderRecord.getOrderSn();
			String orderSn = serviceOrderRecord.getOrderSn();
			String serviceDate = serviceOrderRecord.getServiceDate();
			String servicePeriod = serviceOrderRecord.getServicePeriod();
			String[][] data = new String[][] { { first, "#173177" }, { orderSn, "#173177" },
					{ serviceDate + " " + servicePeriod.substring(0, 5), "#173177" }, { remake, "#173177" } };
			RabbitMessageParam param = RabbitMessageParam.builder()
					.mpTemplateData(
							MpTemplateData.builder().config(MpTemplateConfig.APPOINTMENT_REMINDER).data(data).build())
					.page(page).shopId(getShopId()).userIdList(userIdList).type(RabbitParamConstant.Type.MP_TEMPLE_TYPE)
					.build();
			logger().info("预约服务提前一小时提醒准备发");
			saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
					TaskJobEnum.SEND_MESSAGE.getExecutionType());
		}
		return null;
	}
}
