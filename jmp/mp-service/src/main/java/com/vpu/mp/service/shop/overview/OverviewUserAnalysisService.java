package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.overview.useranalysis.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
  public TrendVo getTrend(DateParam param) {
    // 得到两个结束日期
    getPastTime(param);
    // 得到上一个时间段的数据
    TrendTotalVo beforeVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getOldTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(TrendTotalVo.class);
    // 得到下一个时间段的数据
    TrendTotalVo afterVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(TrendTotalVo.class);
    // 得到每天的数据
    List<TrendDailyVo> trendDailyVo =
        db().select(
                USER_SUMMARY_TREND.REF_DATE,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(
                USER_SUMMARY_TREND.REF_DATE.between(
                    new Date(param.getOldTime().getTime()), new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(NumberUtils.BYTE_ONE))
            .orderBy(USER_SUMMARY_TREND.REF_DATE.asc())
            .fetchInto(TrendDailyVo.class);
    trendDailyVo.remove(0);
    // 得到变化率
    double loginDataRate = getChangeRate(beforeVo.getLoginData(), afterVo.getLoginData());
    double userDataRate = getChangeRate(beforeVo.getUserData(), afterVo.getUserData());
    double orderUserDataRate =
        getChangeRate(beforeVo.getOrderUserData(), afterVo.getOrderUserData());
    // 返回出参接收数据
    return new TrendVo() {
      {
        setTrendDailyVo(trendDailyVo);
        // 设置总数
        setLoginDataTotal(afterVo.getLoginData());
        setUserDataTotal(afterVo.getUserData());
        setOrderUserDataTotal(afterVo.getOrderUserData());
        // 设置变化率
        setLoginDataRate(loginDataRate);
        setUserDataRate(userDataRate);
        setOrderUserDataRate(orderUserDataRate);
      }
    };
  }

  /**
   * 通用方法-得到N天前的日期
   *
   * @param type N天前
   * @return 对应日期(String型)
   */
  private String getNewDate(Byte type) {
    // 设置日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar c = Calendar.getInstance();
    // Calendar.DATE=5 代表对日期操作，减去N，得到N天前
    c.add(Calendar.DATE, -1);
    // 转换成Date型
    java.util.Date time = c.getTime();
    // 转换并返回指定时间格式的String型时间
    return sdf.format(time);
  }

  /**
   * 通用方法-得到上一个N天前的日期
   *
   * @param type N天前
   * @return 对应日期(String型)
   */
  private String getOldDate(Byte type) {
    // 设置日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar c = Calendar.getInstance();
    // Calendar.DATE=5 代表对日期操作，减去N，得到N天前
    c.add(Calendar.DATE, -(type + 1));
    // 转换成Date型
    java.util.Date time = c.getTime();
    // 转换并返回指定时间格式的String型时间
    return sdf.format(time);
  }
  /**
   * 通用方法-得到两段时间的结束时间
   *
   * @param param 段时间
   */
  private void getPastTime(DateParam param) {
    try {
      String oldTimeString = getOldDate(param.getType());
      String newTimeString = getNewDate(param.getType());
      // 将String日期转为yyyyMMdd格式的Date型日期
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      // 将设置好的Date型日期放入param，进行db操作
      param.setOldTime(sdf.parse(oldTimeString));
      param.setNewTime(sdf.parse(newTimeString));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 计算数据变化率
   *
   * @param a 旧数据
   * @param b 新数据
   * @return 变化率
   */
  @SuppressWarnings("deprecation")
  private Double getChangeRate(Integer a, Integer b) {
    if (a == null) {
      // 旧数据为空，结果为0
      return NumberUtils.DOUBLE_ZERO;
    } else {
      if (b == null) {
        b = NumberUtils.INTEGER_ZERO;
      }
      // 除数为0，令结果为100%
      if (a.equals(NumberUtils.INTEGER_ZERO)) {
        return NumberUtils.DOUBLE_ONE;
      } else {
        // 公式计算结果
        return (double) Math.round((double) (b - a) / (double) a * 10000) / 10000;
      }
    }
  }
  /**
   * double型计算数据变化率
   *
   * @param a 旧数据
   * @param b 新数据
   * @return 变化率
   */
  @SuppressWarnings("deprecation")
  private Double getChangeRate(Double a, Double b) {
    if (a == null) {
      // 旧数据为空，结果为0
      return NumberUtils.DOUBLE_ZERO;
    } else {
      if (b == null) {
        b = NumberUtils.DOUBLE_ZERO;
      }
      // 除数为0，令结果为100%
      if (a.equals(NumberUtils.DOUBLE_ZERO)) {
        return NumberUtils.DOUBLE_ONE;
      } else {
        // 公式计算结果
        return (double) Math.round((b - a) / a * 10000) / 10000;
      }
    }
  }
  /**
   * bigdecimal型计算数据变化率
   *
   * @param a 旧数据
   * @param b 新数据
   * @return 变化率
   */
  @SuppressWarnings("deprecation")
  private Double getChangeRate(BigDecimal a, BigDecimal b) {
    if (a == null) {
      // 旧数据为空，结果为0
      return NumberUtils.DOUBLE_ZERO;
    } else {
      if (b == null) {
        b = BigDecimal.valueOf(NumberUtils.DOUBLE_ZERO);
      }
      // 除数为0，令结果为100%
      if (a.compareTo(BigDecimal.ZERO) == 0) {
        return NumberUtils.DOUBLE_ONE;
      } else {
        // 公式计算结果
        Double c = (b.subtract(a)).divide(a, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return c;
      }
    }
  }

  /**
   * 用户活跃
   *
   * @param param 查看最近N天的数据(默认N=1) 对应时间段的开始日期和结束日期
   * @return 各类型会员数量及占比
   */
  public ActiveTotalVo getActive(DateParam param) {
    // 得到两个结束日期
    getPastTime(param);
    // 累加得到不同类型会员总数
    ActiveTotalVo totalVo =
        db().select(
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.COUPON_DATA,
                USER_SUMMARY_TREND.CART_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA,
                USER_SUMMARY_TREND.USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(ActiveTotalVo.class);
    // 得到每日数据
    List<ActiveDailyVo> activeDailyVo =
        db().select(
                USER_SUMMARY_TREND.REF_DATE,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.COUPON_DATA,
                USER_SUMMARY_TREND.CART_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(
                USER_SUMMARY_TREND.REF_DATE.between(
                    new Date(param.getOldTime().getTime()), new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(NumberUtils.BYTE_ONE))
            .fetchInto(ActiveDailyVo.class);
    activeDailyVo.remove(0);
    totalVo.setActiveDailyVo(activeDailyVo);
    // 计算占比
    // 会员数占比
    double loginDataRate = getRate(totalVo.getLoginData(), totalVo.getUserData());
    totalVo.setLoginDataRate(loginDataRate);
    // 领券会员数占比
    double couponDataRate = getRate(totalVo.getCouponData(), totalVo.getLoginData());
    totalVo.setCouponDataRate(couponDataRate);
    // 加购会员数占比
    double cartDataRate = getRate(totalVo.getCartData(), totalVo.getLoginData());
    totalVo.setCartDataRate(cartDataRate);
    // 成交会员数占比
    double orderUserDataRate = getRate(totalVo.getOrderUserData(), totalVo.getLoginData());
    totalVo.setOrderUserDataRate(orderUserDataRate);
    // 返回结果
    return totalVo;
  }

  /**
   * 计算数据占比
   *
   * @param a 被除数
   * @param b 除数
   * @return 占比（4位小数形式）
   */
  @SuppressWarnings("deprecation")
  private Double getRate(Integer a, Integer b) {
    // 数据为空时令返回结果为0
    if (a == null || b == null) {
      return NumberUtils.DOUBLE_ZERO;
    }
    // 除数为0时令返回结果为0
    else if (b.equals(NumberUtils.INTEGER_ZERO)) {
      return NumberUtils.DOUBLE_ZERO;
    } else {
      // 公式计算结果
      return (double) Math.round(((double) a / (double) b * 10000)) / 10000;
    }
  }
  /**
   * 会员统计
   *
   * @param param 起止时间
   * @return 数据和变化率
   */
  public VipVo getVip(DateParam param) {
    // 得到两个结束日期
    getPastTime(param);
    // 得到上一段时间的数据
    VipVo beforeVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.REG_USER_DATA,
                USER_SUMMARY_TREND.UPGRADE_USER_DATA,
                USER_SUMMARY_TREND.CHARGE_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getOldTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(VipVo.class);
    // 得到后一段时间的数据
    VipVo afterVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.REG_USER_DATA,
                USER_SUMMARY_TREND.UPGRADE_USER_DATA,
                USER_SUMMARY_TREND.CHARGE_USER_DATA)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(VipVo.class);
    // 计算变化率
    // 累积会员数变化率
    double userDataRate = getChangeRate(beforeVo.getUserData(), afterVo.getUserData());
    afterVo.setUserDataRate(userDataRate);
    // 新增会员数变化率
    double regUserDataRate = getChangeRate(beforeVo.getRegUserData(), afterVo.getRegUserData());
    afterVo.setRegUserDataRate(regUserDataRate);
    // 升级会员数变化率
    double upgradeUserDataRate =
        getChangeRate(beforeVo.getUpgradeUserData(), afterVo.getUpgradeUserData());
    afterVo.setUpgradeUserDataRate(upgradeUserDataRate);
    // 储值会员数变化率
    double chargeUserDataRate =
        getChangeRate(beforeVo.getChargeUserData(), afterVo.getChargeUserData());
    afterVo.setChargeUserDataRate(chargeUserDataRate);
    // 返回结果
    return afterVo;
  }

  /**
   * 成交用户分析
   *
   * @param param 起止时间
   * @return List<OverviewUserAnalysisOrderVo>
   */
  public OrderVo getOrder(DateParam param) {

    // 得到两个结束日期
    getPastTime(param);
    // 计算上一段时间数据
    OrderTotalVo beforeVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA,
                USER_SUMMARY_TREND.OLD_ORDER_USER_DATA,
                USER_SUMMARY_TREND.NEW_ORDER_USER_DATA,
                USER_SUMMARY_TREND.TOTAL_PAID_MONEY,
                USER_SUMMARY_TREND.OLD_PAID_MONEY,
                USER_SUMMARY_TREND.NEW_PAID_MONEY)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getOldTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(OrderTotalVo.class);
    // 计算客户数占比
    // 全部成交客户占比
    beforeVo.setOrderUserDataRate(getRate(beforeVo.getOrderUserData(), beforeVo.getUserData()));
    // 新成交占比
    beforeVo.setNewOrderUserDataRate(
        getRate(beforeVo.getNewOrderUserData(), beforeVo.getOrderUserData()));
    // 老成交占比
    beforeVo.setOldOrderUserDataRate(
        getRate(beforeVo.getOldOrderUserData(), beforeVo.getOrderUserData()));
    // 计算客单价
    // 全部客户单价
    beforeVo.setUnitPrice(getUnitPrice(beforeVo.getTotalPaidMoney(), beforeVo.getOrderUserData()));
    // 新客户单价
    beforeVo.setNewUnitPrice(
        getUnitPrice(beforeVo.getNewPaidMoney(), beforeVo.getNewOrderUserData()));
    // 老客户单价
    beforeVo.setOldUnitPrice(
        getUnitPrice(beforeVo.getOldPaidMoney(), beforeVo.getOldOrderUserData()));
    // 计算访问付款转化率
    // 全部成交客户转化率
    beforeVo.setOrderUserDataRate(getRate(beforeVo.getOrderUserData(), beforeVo.getLoginData()));
    // 新成交转化率
    beforeVo.setNewOrderUserDataRate(
        getRate(beforeVo.getNewOrderUserData(), beforeVo.getLoginData()));
    // 老成交转化率
    beforeVo.setOldOrderUserDataRate(
        getRate(beforeVo.getOldOrderUserData(), beforeVo.getLoginData()));

    // 计算下一段时间数据
    OrderTotalVo afterVo =
        db().select(
                USER_SUMMARY_TREND.USER_DATA,
                USER_SUMMARY_TREND.LOGIN_DATA,
                USER_SUMMARY_TREND.ORDER_USER_DATA,
                USER_SUMMARY_TREND.OLD_ORDER_USER_DATA,
                USER_SUMMARY_TREND.NEW_ORDER_USER_DATA,
                USER_SUMMARY_TREND.TOTAL_PAID_MONEY,
                USER_SUMMARY_TREND.OLD_PAID_MONEY,
                USER_SUMMARY_TREND.NEW_PAID_MONEY)
            .from(USER_SUMMARY_TREND)
            .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
            .fetchOneInto(OrderTotalVo.class);
    // 计算客户数占比
    // 全部成交客户占比
    afterVo.setOrderUserDataRate(getRate(afterVo.getOrderUserData(), afterVo.getUserData()));
    // 新成交占比
    afterVo.setNewOrderUserDataRate(
        getRate(afterVo.getNewOrderUserData(), afterVo.getOrderUserData()));
    // 老成交占比
    afterVo.setOldOrderUserDataRate(
        getRate(afterVo.getOldOrderUserData(), afterVo.getOrderUserData()));
    // 计算客单价
    // 全部客户单价
    afterVo.setUnitPrice(getUnitPrice(afterVo.getTotalPaidMoney(), afterVo.getOrderUserData()));
    // 新客户单价
    afterVo.setNewUnitPrice(getUnitPrice(afterVo.getNewPaidMoney(), afterVo.getNewOrderUserData()));
    // 老客户单价
    afterVo.setOldUnitPrice(getUnitPrice(afterVo.getOldPaidMoney(), afterVo.getOldOrderUserData()));
    // 计算访问付款转化率
    // 全部成交客户转化率
    afterVo.setTransRate(getRate(afterVo.getOrderUserData(), afterVo.getLoginData()));
    // 新成交转化率
    afterVo.setNewTransRate(getRate(afterVo.getNewOrderUserData(), afterVo.getLoginData()));
    // 老成交转化率
    afterVo.setOldTransRate(getRate(afterVo.getOldOrderUserData(), afterVo.getLoginData()));

    // 计算变化率
    OrderChangeRateVo changeRateVo =
        new OrderChangeRateVo() {
          {
            // 客户数
            setOrderUserDataTrend(
                getChangeRate(beforeVo.getOrderUserData(), afterVo.getOldOrderUserData()));
            setNewOrderUserDataTrend(
                getChangeRate(beforeVo.getNewOrderUserData(), afterVo.getNewOrderUserData()));
            setOldOrderUserDataTrend(
                getChangeRate(beforeVo.getOldOrderUserData(), afterVo.getOldOrderUserData()));
            // 客户数占比
            setOrderUserDataRateTrend(
                getChangeRate(beforeVo.getOrderUserDataRate(), afterVo.getOrderUserDataRate()));
            setNewOrderUserDataRateTrend(
                getChangeRate(
                    beforeVo.getNewOrderUserDataRate(), afterVo.getNewOrderUserDataRate()));
            setOldOrderUserDataRateTrend(
                getChangeRate(
                    beforeVo.getOldOrderUserDataRate(), afterVo.getOldOrderUserDataRate()));
            // 客单价
            setUnitPriceTrend(getChangeRate(beforeVo.getUnitPrice(), afterVo.getUnitPrice()));
            setNewUnitPriceTrend(
                getChangeRate(beforeVo.getNewUnitPrice(), afterVo.getNewUnitPrice()));
            setOldUnitPriceTrend(
                getChangeRate(beforeVo.getOldUnitPrice(), afterVo.getOldUnitPrice()));
            // 付款金额
            setTotalPaidMoneyTrend(
                getChangeRate(beforeVo.getTotalPaidMoney(), afterVo.getTotalPaidMoney()));
            setNewPaidMoneyTrend(
                getChangeRate(beforeVo.getNewPaidMoney(), afterVo.getNewPaidMoney()));
            setOldPaidMoneyTrend(
                getChangeRate(beforeVo.getOldPaidMoney(), afterVo.getOldPaidMoney()));
            // 转化率
            setTransRateTrend(getChangeRate(beforeVo.getTransRate(), afterVo.getTransRate()));
            setNewTransRateTrend(
                getChangeRate(beforeVo.getNewTransRate(), afterVo.getNewTransRate()));
            setOldTransRateTrend(
                getChangeRate(beforeVo.getOldTransRate(), afterVo.getOldTransRate()));
          }
        };
    // 新成交客户数每日数据
    List<OrderDailyVo> newVo =
        db().select(
                USER_SUMMARY_TREND.REF_DATE,
                USER_SUMMARY_TREND.NEW_ORDER_USER_DATA.as("order_user_data"),
                USER_SUMMARY_TREND.NEW_PAID_MONEY.as("paid_money"),
                USER_SUMMARY_TREND.LOGIN_DATA)
            .from(USER_SUMMARY_TREND)
            .where(
                USER_SUMMARY_TREND.REF_DATE.between(
                    new Date(param.getOldTime().getTime()), new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(NumberUtils.BYTE_ONE))
            .fetchInto(OrderDailyVo.class);
    newVo.remove(0);
    Iterator<OrderDailyVo> tempNewVo = newVo.iterator();
    while (tempNewVo.hasNext()) {
      OrderDailyVo newDailyVo = tempNewVo.next();
      // 计算客单价
      BigDecimal unitPrice = getUnitPrice(newDailyVo.getPaidMoney(), newDailyVo.getOrderUserData());
      newDailyVo.setUnitPrice(unitPrice);
      // 计算转化率
      Double transRate = getRate(newDailyVo.getOrderUserData(), newDailyVo.getLoginData());
      newDailyVo.setTransRate(transRate);
    }
    // 老成交客户数每日数据
    List<OrderDailyVo> oldVo =
        db().select(
                USER_SUMMARY_TREND.REF_DATE,
                USER_SUMMARY_TREND.OLD_ORDER_USER_DATA.as("order_user_data"),
                USER_SUMMARY_TREND.OLD_PAID_MONEY.as("paid_money"),
                USER_SUMMARY_TREND.LOGIN_DATA)
            .from(USER_SUMMARY_TREND)
            .where(
                USER_SUMMARY_TREND.REF_DATE.between(
                    new Date(param.getOldTime().getTime()), new Date(param.getNewTime().getTime())))
            .and(USER_SUMMARY_TREND.TYPE.eq(NumberUtils.BYTE_ONE))
            .fetchInto(OrderDailyVo.class);
    oldVo.remove(0);
    Iterator<OrderDailyVo> tempOldVo = oldVo.iterator();
    while (tempOldVo.hasNext()) {
      OrderDailyVo oldDailyVo = tempOldVo.next();
      // 计算客单价
      BigDecimal unitPrice = getUnitPrice(oldDailyVo.getPaidMoney(), oldDailyVo.getOrderUserData());
      oldDailyVo.setUnitPrice(unitPrice);
      // 计算转化率
      Double transRate = getRate(oldDailyVo.getOrderUserData(), oldDailyVo.getLoginData());
      oldDailyVo.setTransRate(transRate);
    }
    // 最终出参
    OrderVo orderVo =
        new OrderVo() {
          {
            setDailyOldVo(oldVo);
            setDailyNewVo(newVo);
            setDataVo(afterVo);
            setChangeRateVo(changeRateVo);
          }
        };
    return orderVo;
  }

  /**
   * 计算单价
   *
   * @param a 付款金额
   * @param b 客户人数
   * @return 单价（2位小数形式）
   */
  @SuppressWarnings("deprecation")
  private BigDecimal getUnitPrice(BigDecimal a, Integer b) {
    // 数据为空时令返回结果为0
    if (a == null || b == null) {
      return BigDecimal.valueOf(NumberUtils.DOUBLE_ZERO);
    }
    // 除数为0时令返回结果为0
    else if (b.equals(NumberUtils.INTEGER_ZERO)) {
      return BigDecimal.valueOf(NumberUtils.DOUBLE_ZERO);
    } else {
      return a.divide(BigDecimal.valueOf(b), 2, BigDecimal.ROUND_HALF_UP);
    }
  }

  /**
   * 客户复购趋势
   *
   * @param param 周数和周日时间
   * @return 四周复购率
   */
  public RebuyVo getRebuyTrend(RebuyParam param) {
    try {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      // 周日转date型
      java.util.Date sunday =  dateFormat.parse(param.getSunday());
      param.setFourthDate(sunday);
      // 周一转date型
      java.util.Date monday = dateFormat.parse(param.getMonday());
      // 得到今天
      Calendar c = Calendar.getInstance();
      java.util.Date time = c.getTime();
      String today = dateFormat.format(time);

      // 得到前三周时间（date型）
      param.setThirdDate(
          new java.util.Date(param.getFourthDate().getTime() - (1000 * 60 * 60 * 24 * 7)));
      param.setSecondDate(
          new java.util.Date(param.getThirdDate().getTime() - (1000 * 60 * 60 * 24 * 7)));
      param.setFirstDate(
          new java.util.Date(param.getSecondDate().getTime() - (1000 * 60 * 60 * 24 * 7)));
      // 得到第四周的数据
      RebuyWeekVo fourthVo = new RebuyWeekVo();
      // 今天是周日
      if (param.getMonday().equals(today)) {
        fourthVo.setOldOrderUserData(NumberUtils.INTEGER_ZERO);
        fourthVo.setOrderUserData(NumberUtils.INTEGER_ZERO);
      }
      //
      else {
        fourthVo =
            db().select(
                DSL.sum(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA).as("old_order_user_data"),
                DSL.sum(USER_SUMMARY_TREND.ORDER_USER_DATA).as("order_user_data"))
                .from(USER_SUMMARY_TREND)
                .where(USER_SUMMARY_TREND.REF_DATE.between(new Date(monday.getTime()),new Date(time.getTime()- (1000 * 60 * 60 * 24 ))))
                .and(USER_SUMMARY_TREND.TYPE.eq(NumberUtils.BYTE_ONE))
                .fetchOneInto(RebuyWeekVo.class);
      }
      // 得到当前周
      fourthVo.setWeekNum(param.getWeekNum());
      // 得到当前复购率
      fourthVo.setRebuyRate(getRate(fourthVo.getOldOrderUserData(), fourthVo.getOrderUserData()));
      // 得到第三周的数据
      RebuyWeekVo thirdVo =
          db().select(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA, USER_SUMMARY_TREND.ORDER_USER_DATA)
              .from(USER_SUMMARY_TREND)
              .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getThirdDate().getTime())))
              .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
              .fetchOneInto(RebuyWeekVo.class);
      // 得到当前周
      thirdVo.setWeekNum(param.getWeekNum() - NumberUtils.INTEGER_ONE);
      // 得到当前复购率
      thirdVo.setRebuyRate(getRate(thirdVo.getOldOrderUserData(), thirdVo.getOrderUserData()));
      // 得到第二周的数据
      RebuyWeekVo secondVo =
          db().select(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA, USER_SUMMARY_TREND.ORDER_USER_DATA)
              .from(USER_SUMMARY_TREND)
              .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getSecondDate().getTime())))
              .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
              .fetchOneInto(RebuyWeekVo.class);
      // 得到当前周
      secondVo.setWeekNum(thirdVo.getWeekNum() - NumberUtils.INTEGER_ONE);
      // 得到当前复购率
      secondVo.setRebuyRate(getRate(secondVo.getOldOrderUserData(), secondVo.getOrderUserData()));
      // 得到第一周的数据
      RebuyWeekVo firstVo =
          db().select(USER_SUMMARY_TREND.OLD_ORDER_USER_DATA, USER_SUMMARY_TREND.ORDER_USER_DATA)
              .from(USER_SUMMARY_TREND)
              .where(USER_SUMMARY_TREND.REF_DATE.eq(new Date(param.getFirstDate().getTime())))
              .and(USER_SUMMARY_TREND.TYPE.eq(param.getType()))
              .fetchOneInto(RebuyWeekVo.class);
      // 得到当前周
      firstVo.setWeekNum(secondVo.getWeekNum() - NumberUtils.INTEGER_ONE);
      // 得到当前复购率
      firstVo.setRebuyRate(getRate(firstVo.getOldOrderUserData(), firstVo.getOrderUserData()));
      // 整合在一个集合里
        RebuyWeekVo finalFourthVo = fourthVo;
        List<RebuyWeekVo> rebuyWeekVo =
          new ArrayList<RebuyWeekVo>() {
            {
              add(firstVo);
              add(secondVo);
              add(thirdVo);
              add(finalFourthVo);
            }
          };
      // 返回集合
      RebuyVo rebuyVo =
          new RebuyVo() {
            {
              setRebuyWeekVo(rebuyWeekVo);
            }
          };
      return rebuyVo;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
  }
