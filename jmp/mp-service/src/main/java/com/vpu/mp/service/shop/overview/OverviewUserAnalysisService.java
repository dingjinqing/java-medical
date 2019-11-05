package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.overview.useranalysis.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.USER_SUMMARY_TREND;

/**
 * 用户统计模块
 * 
 * @author liangchen
 * @date 2019年7月18日
 */
@Service
public class OverviewUserAnalysisService extends ShopBaseService {

	/**
	 * 查询客户概况及趋势
	 * 
	 * @param param 开始日期和结束日期
	 * @return 单天数据/总数据/数据变化率
	 */
	public OverviewUserAnalysisTrendTotalVo getTrend(OverviewUserAnalysisDateParam param) {

		try {
            //得到上一个一天/一周/一月
			String startTimeString;
			String endTimeString;
			String lastNum = param.getLastNum();
			//上一个时间段开始日期
			startTimeString = "1".equals(lastNum) ? getStartDate(lastNum) : lastNum;
			startTimeString = "7".equals(lastNum) ? getStartDate(lastNum) : startTimeString;
			startTimeString = "30".equals(lastNum) ? getStartDate(lastNum) : startTimeString;
            //上一个时间段结束日期
			endTimeString = "1".equals(lastNum) ? getEndDate(lastNum) : lastNum;
			endTimeString = "7".equals(lastNum) ? getEndDate(lastNum) : endTimeString;
			endTimeString = "30".equals(lastNum) ? getEndDate(lastNum) : endTimeString;
            //格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//开始日期转Date型
			java.util.Date startTime;
			startTime = sdf.parse(startTimeString);
            //结束日期转Date型
			java.util.Date endTime;
			endTime = sdf.parse(endTimeString);
            //得到上一个时间段的数据
			OverviewUserAnalysisTrendBeforeVo beforeVo = db()
					.select(DSL.sum(USER_SUMMARY_TREND.LOGIN_DATA).as("loginDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserDataTotal"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE.between(new Date(startTime.getTime()),
							new Date(endTime.getTime())))
					.fetchOneInto(OverviewUserAnalysisTrendBeforeVo.class);
            //得到下一个时间段的数据
			OverviewUserAnalysisTrendAfterVo afterVo = db()
					.select(DSL.sum(USER_SUMMARY_TREND.LOGIN_DATA).as("loginDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserDataTotal"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
							.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
					.fetchOneInto(OverviewUserAnalysisTrendAfterVo.class);
            //得到每天的数据
			List<OverviewUserAnalysisTrendVo> overviewUserAnalysisTrendVos = db()
					.select(USER_SUMMARY_TREND.REF_DATE, USER_SUMMARY_TREND.LOGIN_DATA, USER_SUMMARY_TREND.USER_DATA,
							USER_SUMMARY_TREND.ORDER_USER_DATA)
					.from(USER_SUMMARY_TREND)
					.where(USER_SUMMARY_TREND.REF_DATE.between(new Date(param.getStartTime().getTime()),
							new Date(param.getEndTime().getTime())))
					.orderBy(USER_SUMMARY_TREND.REF_DATE.asc()).fetchInto(OverviewUserAnalysisTrendVo.class);
            //得到变化率
            double loginDataRate = getChangeRate(beforeVo.getLoginDataTotal(),afterVo.getLoginDataTotal());
            double userDataRate = getChangeRate(beforeVo.getUserDataTotal(),afterVo.getUserDataTotal());
            double orderUserDataRate = getChangeRate(beforeVo.getOrderUserDataTotal(),afterVo.getOrderUserDataTotal());
            //返回出参接收数据
            return new OverviewUserAnalysisTrendTotalVo(){{
                setTrendVo(overviewUserAnalysisTrendVos);
                //设置总数
                setLoginDataTotal(afterVo.getLoginDataTotal());
                setUserDataTotal(afterVo.getUserDataTotal());
                setOrderUserDataTotal(afterVo.getOrderUserDataTotal());
                //设置变化率
                setLoginDataRate(loginDataRate);
                setUserDataRate(userDataRate);
                setOrderUserDataRate(orderUserDataRate);
            }};
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * 计算数据变化率
     * @param a 旧数据
     * @param b 新数据
     * @return 变化率
     */
    public double getChangeRate(Integer a,Integer b){
	    if (a==null){
	        //旧数据为空，结果为0
	        return NumberUtils.DOUBLE_ZERO;
        }else {
	        if (b==null){
	            b=NumberUtils.INTEGER_ZERO;
            }
	        //除数为0，令结果为100%
	        if(a.equals(NumberUtils.INTEGER_ZERO)){
	            return NumberUtils.DOUBLE_ONE;
            }else {
	            //公式计算结果
                return (double) (b-a)/(double)a;
            }
        }

    }
	/**
	 * 得到几天前一段时间的开始日期
	 * 
	 * @param  days 上一段N天前
	 * @return  yyyyMMdd
	 */
	public String getStartDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -2 * Integer.parseInt(days));
		java.util.Date time = c.getTime();
		String preDay = sdf.format(time);
		System.out.println("preDay" + preDay);
		return preDay;
	}
    /**
     * 得到几天前一段时间的结束日期
     *
     * @param  days 上一段N天前
     * @return  yyyyMMdd
     */
	public String getEndDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -Integer.valueOf(days) - 1);
		java.util.Date time = c.getTime();
		String preDay = sdf.format(time);
		System.out.println("preDay" + preDay);
		return preDay;
	}

	/**
	 * 用户活跃
	 * 
	 * @param param
	 * @return List<OverviewUserAnalysisActiveVo>
	 */
	public List<OverviewUserAnalysisActiveVo> getActive(OverviewUserAnalysisDateParam param) {
		List<OverviewUserAnalysisActiveTotalVo> totalVo = db()
				.select(DSL.sum(USER_SUMMARY_TREND.LOGIN_DATA).as("loginDataTotal"),
						DSL.sum(USER_SUMMARY_TREND.COUPON_DATA).as("couponDataTotal"),
						DSL.sum(USER_SUMMARY_TREND.CART_DATA).as("cartDataTotal"),
						DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserDataTotal"),
						DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userDataTotal"))
				.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisActiveTotalVo.class);

		List<OverviewUserAnalysisActiveVo> overviewUserAnalysisActiveVos = db()
				.select(USER_SUMMARY_TREND.REF_DATE, USER_SUMMARY_TREND.LOGIN_DATA, USER_SUMMARY_TREND.COUPON_DATA,
						USER_SUMMARY_TREND.CART_DATA, USER_SUMMARY_TREND.ORDER_USER_DATA)
				.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisActiveVo.class);
		for (int i = 0; i < overviewUserAnalysisActiveVos.size(); i++) {

			double loginDataRate = (double) (totalVo.get(0).getLoginDataTotal())
					/ (double) (totalVo.get(0).getUserDataTotal());
			overviewUserAnalysisActiveVos.get(i).setLoginDataRate(loginDataRate);

			double couponDataRate = (double) (totalVo.get(0).getCouponDataTotal())
					/ (double) (totalVo.get(0).getLoginDataTotal());
			overviewUserAnalysisActiveVos.get(i).setCouponDataRate(couponDataRate);

			double cartDataRate = (double) (totalVo.get(0).getCartDataTotal())
					/ (double) (totalVo.get(0).getLoginDataTotal());
			overviewUserAnalysisActiveVos.get(i).setCartDataRate(cartDataRate);

			double orderUserDataRate = (double) (totalVo.get(0).getOrderUserDataTotal())
					/ (double) (totalVo.get(0).getLoginDataTotal());
			overviewUserAnalysisActiveVos.get(i).setOrderUserDataRate(orderUserDataRate);

			overviewUserAnalysisActiveVos.get(i).setLoginDataTotal(totalVo.get(0).getLoginDataTotal());
			overviewUserAnalysisActiveVos.get(i).setCouponDataTotal(totalVo.get(0).getCouponDataTotal());
			overviewUserAnalysisActiveVos.get(i).setCartDataTotal(totalVo.get(0).getCartDataTotal());
			overviewUserAnalysisActiveVos.get(i).setOrderUserDataTotal(totalVo.get(0).getCartDataTotal());
			overviewUserAnalysisActiveVos.get(i).setUserDataTotal(totalVo.get(0).getUserDataTotal());

		}

		return overviewUserAnalysisActiveVos;

	}

	/**
	 * 会员统计
	 * 
	 * @param param
	 * @return List<OverviewUserAnalysisVipVo>
	 */
	public List<OverviewUserAnalysisVipVo> getVip(OverviewUserAnalysisDateParam param) {

		try {

			String startTimeString;
			String endTimeString;
			String lastNum = param.getLastNum();
			startTimeString = "1".equals(lastNum) ? getStartDate(lastNum) : lastNum;
			startTimeString = "7".equals(lastNum) ? getStartDate(lastNum) : startTimeString;
			startTimeString = "30".equals(lastNum) ? getStartDate(lastNum) : startTimeString;

			endTimeString = "1".equals(lastNum) ? getEndDate(lastNum) : lastNum;
			endTimeString = "7".equals(lastNum) ? getEndDate(lastNum) : endTimeString;
			endTimeString = "30".equals(lastNum) ? getEndDate(lastNum) : endTimeString;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			java.util.Date startTime;
			startTime = sdf.parse(startTimeString);

			java.util.Date endTime;
			endTime = sdf.parse(endTimeString);

			List<OverviewUserAnalysisVipBeforeVo> beforeVo = db()
					.select(DSL.sum(USER_SUMMARY_TREND.REG_USER_DATA).as("regUserData"), 
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userData"),
							DSL.sum(USER_SUMMARY_TREND.UPGRADE_USER_DATA).as("upgradeUserData"), 
							DSL.sum(USER_SUMMARY_TREND.CHARGE_USER_DATA).as("chargeUserData"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE.between(new Date(startTime.getTime()),
							new Date(endTime.getTime())))
					.fetchInto(OverviewUserAnalysisVipBeforeVo.class);
			List<OverviewUserAnalysisVipVo> overviewUserAnalysisVipVos = db()
					.select(DSL.sum(USER_SUMMARY_TREND.REG_USER_DATA).as("regUserData"), 
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userData"),
							DSL.sum(USER_SUMMARY_TREND.UPGRADE_USER_DATA).as("upgradeUserData"), 
							DSL.sum(USER_SUMMARY_TREND.CHARGE_USER_DATA).as("chargeUserData"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
							.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
					.fetchInto(OverviewUserAnalysisVipVo.class);
			for (int i = 0; i < overviewUserAnalysisVipVos.size(); i++) {

				double regUserDataRate = (double) (overviewUserAnalysisVipVos.get(i).getRegUserData()
						- beforeVo.get(i).getRegUserData()) / (double) (beforeVo.get(i).getRegUserData());
				overviewUserAnalysisVipVos.get(i).setRegUserDataRate(regUserDataRate);

				double userDataRate = (double) (overviewUserAnalysisVipVos.get(i).getUserData()
						- beforeVo.get(i).getUserData()) / (double) (beforeVo.get(i).getUserData());
				overviewUserAnalysisVipVos.get(i).setUserDataRate(userDataRate);

				double upgradeUserDataRate = (double) (overviewUserAnalysisVipVos.get(i).getUpgradeUserData()
						- beforeVo.get(i).getUpgradeUserData()) / (double) (beforeVo.get(i).getUpgradeUserData());
				overviewUserAnalysisVipVos.get(i).setUpgradeUserDataRate(upgradeUserDataRate);

				double chargeUserDataRate = (double) (overviewUserAnalysisVipVos.get(i).getChargeUserData()
						- beforeVo.get(i).getChargeUserData()) / (double) (beforeVo.get(i).getChargeUserData());
				overviewUserAnalysisVipVos.get(i).setChargeUserDataRate(chargeUserDataRate);
			}

			return overviewUserAnalysisVipVos;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 成交用户分析
	 * 
	 * @param param
	 * @return List<OverviewUserAnalysisOrderVo>
	 */
	public List<OverviewUserAnalysisOrderVo> getOrder(OverviewUserAnalysisDateParam param) {

		try {

			String startTimeString;
			String endTimeString;
			String lastNum = param.getLastNum();
			startTimeString = "1".equals(lastNum) ? getStartDate(lastNum) : lastNum;
			startTimeString = "7".equals(lastNum) ? getStartDate(lastNum) : startTimeString;
			startTimeString = "30".equals(lastNum) ? getStartDate(lastNum) : startTimeString;

			endTimeString = "1".equals(lastNum) ? getEndDate(lastNum) : lastNum;
			endTimeString = "7".equals(lastNum) ? getEndDate(lastNum) : endTimeString;
			endTimeString = "30".equals(lastNum) ? getEndDate(lastNum) : endTimeString;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			java.util.Date startTime;
			startTime = sdf.parse(startTimeString);

			java.util.Date endTime;
			endTime = sdf.parse(endTimeString);
			
			List<OverviewUserAnalysisOrderTotalBeforeVo> beforeVo = db()
					.select(DSL.sum(USER_SUMMARY_TREND.LOGIN_DATA).as("loginDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.NEW_ORDER_USER_DATA).as("newOrderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.TOTAL_PAID_MONEY).as("totalPaidMoneyTotal"),
							DSL.sum(USER_SUMMARY_TREND.OLD_PAID_MONEY).as("oldPaidMoneyTotal"),
							DSL.sum(USER_SUMMARY_TREND.NEW_PAID_MONEY).as("newPaidMoneyTotal"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
							.between(new Date(startTime.getTime()), new Date(endTime.getTime())))
					.fetchInto(OverviewUserAnalysisOrderTotalBeforeVo.class);
			
			List<OverviewUserAnalysisOrderTotalVo> totalVo = db()
					.select(DSL.sum(USER_SUMMARY_TREND.LOGIN_DATA).as("loginDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.USER_DATA).as("userDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.NEW_ORDER_USER_DATA).as("newOrderUserDataTotal"),
							DSL.sum(USER_SUMMARY_TREND.TOTAL_PAID_MONEY).as("totalPaidMoneyTotal"),
							DSL.sum(USER_SUMMARY_TREND.OLD_PAID_MONEY).as("oldPaidMoneyTotal"),
							DSL.sum(USER_SUMMARY_TREND.NEW_PAID_MONEY).as("newPaidMoneyTotal"))
					.from(USER_SUMMARY_TREND).where(USER_SUMMARY_TREND.REF_DATE
							.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
					.fetchInto(OverviewUserAnalysisOrderTotalVo.class);
			
			List<OverviewUserAnalysisOrderVo> overviewUserAnalysisOrderVos = db()
					.select(USER_SUMMARY_TREND.LOGIN_DATA,
							USER_SUMMARY_TREND.REF_DATE,
							USER_SUMMARY_TREND.ORDER_USER_DATA,
							USER_SUMMARY_TREND.ORDER_USER_DATA,
							USER_SUMMARY_TREND.OLD_ORDER_USER_DATA,
							USER_SUMMARY_TREND.NEW_ORDER_USER_DATA,
							USER_SUMMARY_TREND.TOTAL_PAID_MONEY,
							USER_SUMMARY_TREND.OLD_PAID_MONEY,
							USER_SUMMARY_TREND.NEW_PAID_MONEY)
					.from(USER_SUMMARY_TREND)
					.where(USER_SUMMARY_TREND.REF_DATE
							.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
					.fetchInto(OverviewUserAnalysisOrderVo.class);
			for (int i = 0; i < overviewUserAnalysisOrderVos.size(); i++) {
				/** 总客户数 */
				overviewUserAnalysisOrderVos.get(i).setOrderUserDataTotal(totalVo.get(0).getOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setOldOrderUserDataTotal(totalVo.get(0).getOldOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setNewOrderUserDataTotal(totalVo.get(0).getNewOrderUserDataTotal());
				/** 总付款金额 */
				overviewUserAnalysisOrderVos.get(i).setTotalPaidMoneyTotal(totalVo.get(0).getTotalPaidMoneyTotal());
				overviewUserAnalysisOrderVos.get(i).setOldPaidMoneyTotal(totalVo.get(0).getOldPaidMoneyTotal());
				overviewUserAnalysisOrderVos.get(i).setNewPaidMoneyTotal(totalVo.get(0).getNewPaidMoneyTotal());
				/** 客户数占比 */
				double orderUserDataRate = 
						(double)(totalVo.get(0).getOrderUserDataTotal())/(double)(totalVo.get(0).getUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setOrderUserDataRate(orderUserDataRate);
				double oldOrderUserDataRate = 
						(double)(totalVo.get(0).getOldOrderUserDataTotal())/(double)(totalVo.get(0).getOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setOldOrderUserDataRate(oldOrderUserDataRate);
				double newOrderUserDataRate = 
						(double)(totalVo.get(0).getNewOrderUserDataTotal())/(double)(totalVo.get(0).getOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setNewOrderUserDataRate(newOrderUserDataRate);
				/** 客单价总数 */
				double priceTotal = 
						(double)(totalVo.get(0).getTotalPaidMoneyTotal())/(double)(totalVo.get(0).getOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setPriceTotal(priceTotal);
				double oldPriceTotal = 
						(double)(totalVo.get(0).getOldPaidMoneyTotal())/(double)(totalVo.get(0).getOldOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setOldPriceTotal(oldPriceTotal);
				double newPriceTotal = 
						(double)(totalVo.get(0).getNewPaidMoneyTotal())/(double)(totalVo.get(0).getNewOrderUserDataTotal());
				overviewUserAnalysisOrderVos.get(i).setNewPriceTotal(newPriceTotal);
				/** 客单价json */
				double oldPrice = 
						(double)(overviewUserAnalysisOrderVos.get(i).getOldPaidMoney())/(double)(overviewUserAnalysisOrderVos.get(i).getOldOrderUserData());
				overviewUserAnalysisOrderVos.get(i).setOldPrice(oldPrice);
				double newPrice = 
						(double)(overviewUserAnalysisOrderVos.get(i).getNewPaidMoney())/(double)(overviewUserAnalysisOrderVos.get(i).getNewOrderUserData());
				overviewUserAnalysisOrderVos.get(i).setNewPrice(newPrice);
				/** 转化率json */
				double oldTransRate =
						(double)(overviewUserAnalysisOrderVos.get(i).getOldOrderUserData())/(double)(overviewUserAnalysisOrderVos.get(i).getLoginData());
				overviewUserAnalysisOrderVos.get(i).setOldTransRate(oldTransRate);
				double newTransRate =
						(double)(overviewUserAnalysisOrderVos.get(i).getNewOrderUserData())/(double)(overviewUserAnalysisOrderVos.get(i).getLoginData());
				overviewUserAnalysisOrderVos.get(i).setNewTransRate(newTransRate);
				/** 转化率总数 */
				double transRateTotal =
						(double)(totalVo.get(0).getOrderUserDataTotal())/(double)(totalVo.get(0).getLoginDataTotal());
				overviewUserAnalysisOrderVos.get(i).setTransRateTotal(transRateTotal);
				double oldTransRateTotal =
						(double)(totalVo.get(0).getOldOrderUserDataTotal())/(double)(totalVo.get(0).getLoginDataTotal());
				overviewUserAnalysisOrderVos.get(i).setOldTransRateTotal(oldTransRateTotal);
				double newTransRateTotal =
						(double)(totalVo.get(0).getNewOrderUserDataTotal())/(double)(totalVo.get(0).getLoginDataTotal());
				overviewUserAnalysisOrderVos.get(i).setNewTransRateTotal(newTransRateTotal);
				/**
				 * 前后对比计算百分比
				 */
				/** 客户数涨幅 */
				double orderUserDataRange = 
						((double)totalVo.get(0).getOrderUserDataTotal()-(double)beforeVo.get(0).getOrderUserDataTotal())/
						(double)beforeVo.get(0).getOrderUserDataTotal();
				overviewUserAnalysisOrderVos.get(i).setOrderUserDataRange(orderUserDataRange);
				double oldOrderUserDataRange = 
						((double)totalVo.get(0).getOldOrderUserDataTotal()-(double)beforeVo.get(0).getOldOrderUserDataTotal())/
						(double)beforeVo.get(0).getOldOrderUserDataTotal();
				overviewUserAnalysisOrderVos.get(i).setOldOrderUserDataRange(oldOrderUserDataRange);
				double newOrderUserDataRange = 
						((double)totalVo.get(0).getNewOrderUserDataTotal()-(double)beforeVo.get(0).getNewOrderUserDataTotal())/
						(double)beforeVo.get(0).getNewOrderUserDataTotal();
				overviewUserAnalysisOrderVos.get(i).setNewOrderUserDataRange(newOrderUserDataRange);
				/** 付款金额涨幅 */
				double totalPaidMoneyRange = 
						((double)totalVo.get(0).getTotalPaidMoneyTotal()-(double)beforeVo.get(0).getTotalPaidMoneyTotal())/
						(double)beforeVo.get(0).getTotalPaidMoneyTotal();
				overviewUserAnalysisOrderVos.get(i).setTotalPaidMoneyRange(totalPaidMoneyRange);
				double oldPaidMoneyRange = 
						((double)totalVo.get(0).getOldPaidMoneyTotal()-(double)beforeVo.get(0).getOldPaidMoneyTotal())/
						(double)beforeVo.get(0).getOldPaidMoneyTotal();
				overviewUserAnalysisOrderVos.get(i).setOldPaidMoneyRange(oldPaidMoneyRange);
				double newPaidMoneyRange = 
						((double)totalVo.get(0).getNewPaidMoneyTotal()-(double)beforeVo.get(0).getNewPaidMoneyTotal())/
						(double)beforeVo.get(0).getNewPaidMoneyTotal();
				overviewUserAnalysisOrderVos.get(i).setNewPaidMoneyRange(newPaidMoneyRange);
				/** 客户数占比涨幅 */
				double orderUserDataRateBefore = 
						(double)(beforeVo.get(0).getOrderUserDataTotal())/(double)(beforeVo.get(0).getUserDataTotal());
				double oldOrderUserDataRateBefore = 
						(double)(beforeVo.get(0).getOldOrderUserDataTotal())/(double)(beforeVo.get(0).getOrderUserDataTotal());
				double newOrderUserDataRateBefore = 
						(double)(beforeVo.get(0).getNewOrderUserDataTotal())/(double)(beforeVo.get(0).getOrderUserDataTotal());
				double orderUserDataRateRange = 
						(orderUserDataRate-orderUserDataRateBefore)/orderUserDataRateBefore;
				overviewUserAnalysisOrderVos.get(i).setOrderUserDataRateRange(orderUserDataRateRange);
				double oldOrderUserDataRateRange = 
						(orderUserDataRateBefore-oldOrderUserDataRateBefore)/oldOrderUserDataRateBefore;
				overviewUserAnalysisOrderVos.get(i).setOldOrderUserDataRateRange(oldOrderUserDataRateRange);
				double newOrderUserDataRateRange = 
						(newOrderUserDataRateBefore-newOrderUserDataRateBefore)/newOrderUserDataRateBefore;
				overviewUserAnalysisOrderVos.get(i).setNewOrderUserDataRateRange(newOrderUserDataRateRange);
				/** 客单价涨幅 */
				double priceTotalBefore = 
						(double)(beforeVo.get(0).getTotalPaidMoneyTotal())/(double)(beforeVo.get(0).getOrderUserDataTotal());
				double oldPriceTotalBefore = 
						(double)(beforeVo.get(0).getOldPaidMoneyTotal())/(double)(beforeVo.get(0).getOldOrderUserDataTotal());
				double newPriceTotalBefore = 
						(double)(beforeVo.get(0).getNewPaidMoneyTotal())/(double)(beforeVo.get(0).getNewOrderUserDataTotal());
				double priceRange = 
						(priceTotal-priceTotalBefore)/priceTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setPriceRange(priceRange);
				double oldPriceRange = 
						(oldPriceTotal-oldPriceTotalBefore)/oldPriceTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setOldPriceRange(oldPriceRange);
				double newPriceRange = 
						(newPriceTotal-newPriceTotalBefore)/newPriceTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setNewPriceRange(newPriceRange);
				/** 访问转化率涨幅 */
				double transRateTotalBefore =
						(double)(beforeVo.get(0).getOrderUserDataTotal())/(double)(beforeVo.get(0).getLoginDataTotal());
				double oldTransRateTotalBefore =
						(double)(beforeVo.get(0).getOldOrderUserDataTotal())/(double)(beforeVo.get(0).getLoginDataTotal());
				double newTransRateTotalBefore =
						(double)(beforeVo.get(0).getNewOrderUserDataTotal())/(double)(beforeVo.get(0).getLoginDataTotal());
				double transRateRange = 
						(transRateTotal-transRateTotalBefore)/transRateTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setTransRateRange(transRateRange);
				double oldTransRateRange = 
						(oldTransRateTotal-oldTransRateTotalBefore)/oldTransRateTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setOldTransRateRange(oldTransRateRange);
				double newTransRateRange = 
						(newTransRateTotal-newTransRateTotalBefore)/newTransRateTotalBefore;
				overviewUserAnalysisOrderVos.get(i).setNewTransRateRange(newTransRateRange);
			}

			return overviewUserAnalysisOrderVos;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *客户复购趋势
	 *@param param
	 *@return List<OverviewUserAnalysisRebuyVo>
	 */
	public List<OverviewUserAnalysisRebuyVo> getRebuyTrend(OverviewUserAnalysisRebuyParam param) {
		
		List<OverviewUserAnalysisRebuyLastVo>  lastVo = db()
				.select(DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserData"),
						DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserData"))
				.from(USER_SUMMARY_TREND)
				.where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getStartTime().getTime()), new Date(param.getEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisRebuyLastVo.class);
		List<OverviewUserAnalysisRebuyThirdVo>  thirdVo = db()
				.select(DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserData"),
						DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserData"))
				.from(USER_SUMMARY_TREND)
				.where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getThirdStartTime().getTime()), new Date(param.getThirdEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisRebuyThirdVo.class);
		
		List<OverviewUserAnalysisRebuySecondVo>  secondVo = db()
				.select(DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserData"),
						DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserData"))
				.from(USER_SUMMARY_TREND)
				.where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getSecondStartTime().getTime()), new Date(param.getSecondEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisRebuySecondVo.class);
		
		List<OverviewUserAnalysisRebuyFirstVo>  firstVo = db()
				.select(DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("oldOrderUserData"),
						DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("orderUserData"))
				.from(USER_SUMMARY_TREND)
				.where(USER_SUMMARY_TREND.REF_DATE
						.between(new Date(param.getFirstStartTime().getTime()), new Date(param.getFirstEndTime().getTime())))
				.fetchInto(OverviewUserAnalysisRebuyFirstVo.class);
		
		List<OverviewUserAnalysisRebuyVo> overviewUserAnalysisRebuyVos = new ArrayList<>(4);
		
		double lastRebuyRate = (double)lastVo.get(0).getOldOrderUserData()/(double)lastVo.get(0).getOrderUserData();
		overviewUserAnalysisRebuyVos.add(new OverviewUserAnalysisRebuyVo(param.getStartTime(),param.getEndTime(),lastRebuyRate));
		double thirdRebuyRate = (double)thirdVo.get(0).getOldOrderUserData()/(double)thirdVo.get(0).getOrderUserData();
		overviewUserAnalysisRebuyVos.add(new OverviewUserAnalysisRebuyVo(param.getThirdStartTime(),param.getThirdEndTime(),thirdRebuyRate));
		double secondRebuyRate = (double)secondVo.get(0).getOldOrderUserData()/(double)secondVo.get(0).getOrderUserData();
		overviewUserAnalysisRebuyVos.add(new OverviewUserAnalysisRebuyVo(param.getSecondStartTime(),param.getSecondEndTime(),secondRebuyRate));
		double firstRebuyRate = (double)firstVo.get(0).getOldOrderUserData()/(double)firstVo.get(0).getOrderUserData();
		overviewUserAnalysisRebuyVos.add(new OverviewUserAnalysisRebuyVo(param.getFirstStartTime(),param.getFirstEndTime(),firstRebuyRate));
		return overviewUserAnalysisRebuyVos;		
	}
}
