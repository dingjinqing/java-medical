package com.vpu.mp.service.shop.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.MrkingVoucher;
import com.vpu.mp.db.shop.tables.records.CustomerAvailCouponsRecord;
import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.coupon.give.*;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectLimitStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 优惠券管理
 *
 * @author liangchen
 * @date 2019年7月29日
 */
@Service
@Slf4j
public class CouponGiveService extends ShopBaseService {

  @Autowired private CouponHoldService couponHold;

  @Autowired private RabbitmqSendService rabbitmqSendService;

  private static final MrkingVoucher MV = MrkingVoucher.MRKING_VOUCHER.as("MV");

  /** 获取方式，0：发放 */
  private static final Byte ACCESS_MODE = 0;
  /** 活动类型 定向发券 值为9 */
  private static final Byte GET_SOURCE = 9;

  /**
   * 优惠券发放情况分页列表
   *
   * @param param 选填项：活动名称
   * @return listVo 对应的发券活动信息
   */
  public PageResult<CouponGiveListVo> getCouponGiveList(CouponGiveListParam param) {
    try {
      // 查询活动信息
      SelectLimitStep<Record> couponGiveListVo =
          db().select().from(GIVE_VOUCHER).orderBy(GIVE_VOUCHER.ID.desc());
      // 模糊查询
      if (!StringUtils.isNullOrEmpty(param.getActName())) {
        couponGiveListVo =
            ((SelectWhereStep<Record>) couponGiveListVo)
                .where(GIVE_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName())));
      }
      // 整合分页信息
      PageResult<CouponGiveListVo> listVo =
          this.getPageResult(
              couponGiveListVo,
              param.getCurrentPage(),
              param.getPageRows(),
              CouponGiveListVo.class);
      // 整合活动对应优惠券信息
      for (CouponGiveListVo vo : listVo.getDataList()) {
        //解析得到活动中包含的优惠券
        String dataList = vo.getSendCondition();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        jsonNode = objectMapper.readTree(dataList);
        String voucherId = jsonNode.get("coupon_ids").toString();
        String id = voucherId.replace("\"", "");
        String[] idArray = id.split(",");
        // 优惠券信息
        List<CouponGiveListConditionVo> tempListVo = new ArrayList<>();
        for (String selectId : idArray) {
          Optional<CouponGiveListConditionVo> couponVo =
              db().select(
                      MRKING_VOUCHER.ID.as("couponId"),
                      MRKING_VOUCHER.ACT_NAME.as("coupon_name"),
                      MRKING_VOUCHER.ACT_CODE,
                      MRKING_VOUCHER.USE_CONSUME_RESTRICT,
                      MRKING_VOUCHER.LEAST_CONSUME,
                      MRKING_VOUCHER.DENOMINATION,
                      MRKING_VOUCHER.START_TIME,
                      MRKING_VOUCHER.END_TIME,
                      MRKING_VOUCHER.VALIDITY_TYPE,
                      MRKING_VOUCHER.VALIDITY,
                      MRKING_VOUCHER.VALIDITY_HOUR,
                      MRKING_VOUCHER.VALIDITY_MINUTE)
                  .from(MRKING_VOUCHER)
                  .where(MRKING_VOUCHER.ID.eq(Integer.valueOf(selectId)))
                  .fetchOptionalInto(CouponGiveListConditionVo.class);
          tempListVo.add(couponVo.orElse(null));
        }

        // 完善某一活动对应的优惠券信息
        vo.setCouponGiveListConditionVo(tempListVo);
        // 会员卡信息
        if (!StringUtils.isNullOrEmpty(vo.getCardId())) {
          String[] cardId = vo.getCardId().split(",");
          List<String> cardIdList = new ArrayList<>();
          for (String cId : cardId) {
            cardIdList.add(cId);
          }
          List<String> cardName =
              db().select(MEMBER_CARD.CARD_NAME)
                  .from(MEMBER_CARD)
                  .where(MEMBER_CARD.ID.in(cardIdList))
                  .fetchInto(String.class);
          // 将信息返回
          vo.setCardName(cardName);
        }
        // 标签信息
        if (!StringUtils.isNullOrEmpty(vo.getTagId())) {
          String[] tagId = vo.getTagId().split(",");
          List<String> tagIdList = new ArrayList<>();
          for (String tId : tagId) {
            tagIdList.add(tId);
          }
          List<String> tagName =
              db().select(TAG.TAG_NAME)
                  .from(TAG)
                  .where(TAG.TAG_ID.in(tagIdList))
                  .fetchInto(String.class);
          // 将信息返回
          vo.setTagName(tagName);
        }
      }

      return listVo;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 优惠券明细
   *
   * @param param 发券活动信息和优惠券信息
   * @return 优惠券-用户 明细
   */
  public PageResult<CouponHoldListVo> getDetail(CouponGiveDetailParam param) {

    CouponHoldListParam couponParam = new CouponHoldListParam();
    couponParam.setActId(param.getActId());
    couponParam.setMobile(param.getMobile());
    couponParam.setUsername(param.getUsername());
    couponParam.setStatus(param.getIsUsed().byteValue());
    couponParam.setCurrentPage(param.getCurrentPage());
    couponParam.setPageRows(param.getPageRows());
    couponParam.setAccessId(param.getAccessId());
    couponParam.setGetSource(GET_SOURCE);
    return couponHold.getCouponHoldList(couponParam);
  }

  /**
   * 发放优惠券
   *
   * @param param 发券活动条件
   */
  public void insertGrant(CouponGiveGrantParam param) {

    try {
      // 完成一次发券活动
      ObjectMapper objectMapper = new ObjectMapper();
      String condition = objectMapper.writeValueAsString(param.getCouponGiveGrantInfoParams());

      db().insertInto(
              GIVE_VOUCHER,
              GIVE_VOUCHER.ACT_NAME,
              GIVE_VOUCHER.SEND_CONDITION,
              GIVE_VOUCHER.CARD_ID,
              GIVE_VOUCHER.TAG_ID,
              GIVE_VOUCHER.USER,
              GIVE_VOUCHER.HAVE_PAY,
              GIVE_VOUCHER.NO_PAY,
              GIVE_VOUCHER.MAX_COUNT,
              GIVE_VOUCHER.MIN_COUNT,
              GIVE_VOUCHER.MAX_AVE_PRICE,
              GIVE_VOUCHER.MIN_AVE_PRICE,
              GIVE_VOUCHER.SEND_ACTION,
              GIVE_VOUCHER.START_TIME,
              GIVE_VOUCHER.ACT_ID)
          .values(
              param.getActName(),
              condition,
              param.getCardId(),
              param.getTagId(),
              param.getUser(),
              param.getHavePay(),
              param.getNoPay(),
              param.getMaxCount(),
              param.getMinCount(),
              param.getMaxAvePrice(),
              param.getMinAvePrice(),
              param.getSendAction(),
              param.getStartTime(),
              NumberUtils.INTEGER_ZERO)
          .execute();
      int actId =
          db().select(GIVE_VOUCHER.ID)
              .from(GIVE_VOUCHER)
              .orderBy(GIVE_VOUCHER.CREATE_TIME.desc())
              .limit(1)
              .fetchOptionalInto(Integer.class)
              .orElse(0);
      Set<Integer> userIds = new HashSet<>();
      // 将发券活动写入用户-优惠券对应表

      // 加购人群
      Date today = new Date();
      Timestamp cartDay = Util.getEarlyTimeStamp(today, -30);
      if (param.getCouponGiveGrantInfoParams().getCartBox().equals(1)) {
        List<Record1<Integer>> cartUserIds =
            db().select(CART.USER_ID)
                .from(CART)
                .where(CART.CREATE_TIME.greaterOrEqual(cartDay))
                .fetch();
        for (Record1<Integer> cartUserId : cartUserIds) {
          userIds.add(cartUserId.value1());
        }
      }

      // 购买指定商品人群
      if (param.getCouponGiveGrantInfoParams().getGoodsBox().equals(1)) {
        String goodsIds = param.getCouponGiveGrantInfoParams().getGoodsIds();
        String[] goodArray = goodsIds.split(",");

        for (String goodId : goodArray) {
          List<Record1<Integer>> orderIds =
              db().select(ORDER_GOODS.ORDER_ID)
                  .from(ORDER_GOODS)
                  .where(ORDER_GOODS.GOODS_ID.eq(Integer.valueOf(goodId)))
                  .fetch();
          for (Record1<Integer> orderId : orderIds) {
            int userId =
                db().select(ORDER_INFO.USER_ID)
                    .from(ORDER_INFO)
                    .where(ORDER_INFO.ORDER_ID.eq(orderId.value1()))
                    .fetchOptionalInto(Integer.class)
                    .orElse(0);
            userIds.add(userId);
          }
        }
      }

      // 持有会员卡人群
      if (param.getCouponGiveGrantInfoParams().getCardBox().equals(1)) {
        String cardIds = param.getCardId();
        String[] cardArray = cardIds.split(",");
        for (String cardId : cardArray) {
          List<Record1<Integer>> cardUserIds =
              db().select(USER_CARD.USER_ID)
                  .from(USER_CARD)
                  .where(USER_CARD.FLAG.eq((byte) 0))
                  .and(USER_CARD.CARD_ID.eq(Integer.valueOf(cardId)))
                  .fetch();
          for (Record1<Integer> userId : cardUserIds) {
            userIds.add(userId.value1());
          }
        }
      }

      // 属于标签人群
      if (param.getCouponGiveGrantInfoParams().getTagBox().equals(1)) {
        String tagIds = param.getTagId();
        String[] tagArray = tagIds.split(",");
        for (String tagId : tagArray) {
          List<Record1<Integer>> tagUserIds =
              db().select(USER_TAG.USER_ID)
                  .from(USER_TAG)
                  .where(USER_TAG.TAG_ID.eq(Integer.valueOf(tagId)))
                  .fetch();
          for (Record1<Integer> userId : tagUserIds) {
            userIds.add(userId.value1());
          }
        }
      }

      // 选择指定的会员
      if (param.getCouponGiveGrantInfoParams().getMemberBox().equals(1)) {
        String memberIds = param.getUser();
        String[] memberArray = memberIds.split(",");
        for (String memberId : memberArray) {
          userIds.add(Integer.valueOf(memberId));
        }
      }

      // N天内有交易记录
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getHavePay() != null) {
          Timestamp havePayDay = Util.getEarlyTimeStamp(today, -param.getHavePay());
          List<Record1<Integer>> havePayUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .and(ORDER_INFO.CREATE_TIME.greaterOrEqual(havePayDay))
                  .fetch();
          for (Record1<Integer> havePayUserId : havePayUserIds) {
            userIds.add(havePayUserId.value1());
          }
        }
      }

      // N天内无交易记录
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getNoPay() != null) {
          Timestamp noPayDay = Util.getEarlyTimeStamp(today, -param.getNoPay());
          List<Record1<Integer>> noPayUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .and(ORDER_INFO.CREATE_TIME.greaterOrEqual(noPayDay))
                  .fetch();
          for (Record1<Integer> noId : noPayUserIds) {
            List<Record1<Integer>> noPayId =
                db().select(USER.USER_ID)
                    .from(USER)
                    .where(USER.USER_ID.notEqual(noId.value1()))
                    .fetch();
            for (Record1<Integer> noPayUserId : noPayId) {
              userIds.add(noPayUserId.value1());
            }
          }
        }
      }

      // 累计购买次数大于N次 min
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getMinCount() != null) {
          List<Record1<Integer>> minCountUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .groupBy(ORDER_INFO.USER_ID)
                  .having(DSL.count(ORDER_INFO.ORDER_ID).greaterThan(param.getMinCount()))
                  .fetch();
          for (Record1<Integer> minCountUserId : minCountUserIds) {
            userIds.add(minCountUserId.value1());
          }
        }
      }

      // 累计购买次数小于N次 max
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getMaxCount() != null) {
          List<Record1<Integer>> maxCountUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .groupBy(ORDER_INFO.USER_ID)
                  .having(DSL.count(ORDER_INFO.ORDER_ID).lessThan(param.getMaxCount()))
                  .fetch();
          for (Record1<Integer> maxCountUserId : maxCountUserIds) {
            userIds.add(maxCountUserId.value1());
          }
        }
      }

      // 购买商品均价大于N元 min
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getMinAvePrice() != null) {
          List<Record1<Integer>> minAvePriceUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .groupBy(ORDER_INFO.USER_ID)
                  .having(
                      (DSL.sum(ORDER_INFO.ORDER_AMOUNT).divide(DSL.sum(ORDER_INFO.GOODS_AMOUNT)))
                          .greaterThan(param.getMinAvePrice()))
                  .fetch();
          for (Record1<Integer> minAvePriceUserId : minAvePriceUserIds) {
            userIds.add(minAvePriceUserId.value1());
          }
        }
      }

      // 购买商品均价小于N元 max
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getMaxAvePrice() != null) {
          List<Record1<Integer>> maxAvePriceUserIds =
              db().select(ORDER_INFO.USER_ID)
                  .from(ORDER_INFO)
                  .where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
                  .groupBy(ORDER_INFO.USER_ID)
                  .having(
                      (DSL.sum(ORDER_INFO.ORDER_AMOUNT).divide(DSL.sum(ORDER_INFO.GOODS_AMOUNT)))
                          .lessThan(param.getMaxAvePrice()))
                  .fetch();
          for (Record1<Integer> maxAvePriceUserId : maxAvePriceUserIds) {
            userIds.add(maxAvePriceUserId.value1());
          }
        }
      }

      //指定时间内有登陆记录
      if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
        if (param.getCouponGiveGrantInfoParams().getPointStartTime() != null
            && param.getCouponGiveGrantInfoParams().getPointEndTme() != null) {
          List<Record1<Integer>> loginRecordUserIds =
              db().select(USER_LOGIN_RECORD.USER_ID)
                  .from(USER_LOGIN_RECORD)
                  .where(
                      USER_LOGIN_RECORD.CREATE_TIME.between(
                          param.getCouponGiveGrantInfoParams().getPointStartTime(),
                          param.getCouponGiveGrantInfoParams().getPointEndTme()))
                  .fetch();
          for (Record1<Integer> loginRecordUserId : loginRecordUserIds) {
            userIds.add(loginRecordUserId.value1());
          }
        }
      }

      // 队列
      List<Integer> userIdList = new ArrayList<>(userIds);
      String couponIds = param.getCouponGiveGrantInfoParams().getCouponIds().toString();
      String[] couponArray = couponIds.split(",");

      //            rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_MARKETING,
      // RabbitConfig.BINDING_EXCHANGE_COUPON_KEY, TaskJobEnum.GIVE_COUPON);
      CouponGiveQueueParam newParam =
          new CouponGiveQueueParam(
              userIdList, actId, couponArray, ACCESS_MODE, GET_SOURCE);
      if (param.getSendAction() == 0) {
        saas.taskJobMainService.dispatchImmediately(
            newParam,
            CouponGiveQueueParam.class.getName(),
            getShopId(),
            TaskJobEnum.GIVE_COUPON.getExecutionType());
      }
      if (param.getSendAction() == 1) {
        saas.messageTemplateService.createCouponTaskJob(
            getShopId(), newParam, param.getStartTime());
      }

      // 一次发券活动完成后，将发放状态修改为已发放
      db().update(GIVE_VOUCHER)
          .set(GIVE_VOUCHER.SEND_STATUS, NumberUtils.BYTE_ONE)
          .where(GIVE_VOUCHER.ID.eq(actId))
          .execute();

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  /**
   * 定向发券，后台异步执行
   *
   * @param param
   */
  public List<Integer> handlerCouponGive(CouponGiveQueueParam param) {
    List<Integer> successList = new ArrayList<>();
    // 插入user-coupon关联表
    for (String couponId : param.getCouponArray()) {
      // 得当当前优惠券信息
      CouponDetailsVo couponDetails =
          db().select(
                  MRKING_VOUCHER.SURPLUS,
                  MRKING_VOUCHER.ACT_CODE,
                  MRKING_VOUCHER.ACT_NAME,
                  MRKING_VOUCHER.DENOMINATION,
                  MRKING_VOUCHER.START_TIME,
                  MRKING_VOUCHER.END_TIME,
                  MRKING_VOUCHER.VALIDITY_TYPE,
                  MRKING_VOUCHER.VALIDITY,
                  MRKING_VOUCHER.VALIDITY_HOUR,
                  MRKING_VOUCHER.VALIDITY_MINUTE)
              .from(MRKING_VOUCHER)
              .where(MRKING_VOUCHER.ID.eq(Integer.valueOf(couponId)))
              .and(MRKING_VOUCHER.DEL_FLAG.eq(NumberUtils.BYTE_ZERO))
              .fetchOneInto(CouponDetailsVo.class);
      //查询结果为空直接返回
        if (couponDetails==null){
            String couponInfo = "优惠券ID:"+couponId;
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST,couponInfo);
        }
      // 判断优惠券类型 减价or打折
      byte type = 0;
      if (VOUCHER.equalsIgnoreCase(couponDetails.getActCode())) {
        type = 0;
      } else if (DISCOUNT.equalsIgnoreCase(couponDetails.getActCode())) {
        type = 1;
      }
      // 得到开始时间和结束时间
      Map<String, Timestamp> timeMap = getCouponTime(couponDetails);
      // 判断当前券的库存

      // 发券入库
      for (Integer userId : param.getUserIds()) {
        // 如果库存为0，返回信息库存不足
        if (couponDetails.getSurplus().equals(NumberUtils.INTEGER_ZERO)) {
          logger().info("所选优惠券库存不足");
          break;
        }
        // 库存足够，发券
        else {
          Integer rows =
              db().insertInto(
                      CUSTOMER_AVAIL_COUPONS,
                      CUSTOMER_AVAIL_COUPONS.TYPE,
                      CUSTOMER_AVAIL_COUPONS.ACT_ID,
                      CUSTOMER_AVAIL_COUPONS.USER_ID,
                      CUSTOMER_AVAIL_COUPONS.ACT_DESC,
                      CUSTOMER_AVAIL_COUPONS.AMOUNT,
                      CUSTOMER_AVAIL_COUPONS.COUPON_SN,
                      CUSTOMER_AVAIL_COUPONS.ACCESS_ID,
                      CUSTOMER_AVAIL_COUPONS.START_TIME,
                      CUSTOMER_AVAIL_COUPONS.END_TIME,
                      CUSTOMER_AVAIL_COUPONS.ACCESS_MODE,
                      CUSTOMER_AVAIL_COUPONS.GET_SOURCE)
                  .values(
                      type,
                      Integer.valueOf(couponId),
                      userId,
                      couponDetails.getActName(),
                      couponDetails.getDenomination(),
                      getCouponSn(),
                      param.getActId(),
                      timeMap.get("startTime"),
                      timeMap.get("endTime"),
                      param.getAccessMode(),
                      param.getGetSource())
                  .execute();
          // 得到成功条数
          successList.add(rows);
          // 优惠券库存-1
          db().update(MRKING_VOUCHER)
              .set(MRKING_VOUCHER.SURPLUS, (couponDetails.getSurplus() - 1))
              .where(MRKING_VOUCHER.ID.eq(Integer.parseInt(couponId)))
              .execute();
          // 当前对象中优惠券剩余量-1
          couponDetails.setSurplus(couponDetails.getSurplus() - 1);
        }
      }
    }
    return successList;
  }

  /**
   * 得到优惠券有效期
   *
   * @param couponDetails 优惠券信息
   * @return 优惠券有效期
   */
  public Map<String, Timestamp> getCouponTime(CouponDetailsVo couponDetails) {

    // 定义开始时间和结束时间作为最后的参数
    Timestamp startTime;
    Timestamp endTime;
    // 判断发送类型，得到发送时间
    if (couponDetails.getValidityType().equals(NumberUtils.BYTE_ZERO)) {
      startTime = couponDetails.getStartTime();
      endTime = couponDetails.getEndTime();
    } else {
      // 设置日期格式
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // 得到系统当前时间作为开始时间
      String startTimeString = simpleDateFormat.format(System.currentTimeMillis());
      startTime = Timestamp.valueOf(startTimeString);
      // 计算结束时间
      long days = couponDetails.getValidity().longValue() * 1000 * 60 * 60 * 24;
      long hours = couponDetails.getValidityHour().longValue() * 1000 * 60 * 60;
      long minutes = couponDetails.getValidityMinute().longValue() * 1000 * 60;
      // 当前时间加上天、时、分得到结束时间
      long endTimeLong = System.currentTimeMillis() + days + hours + minutes;
      String endTimeString = simpleDateFormat.format(endTimeLong);
      endTime = Timestamp.valueOf(endTimeString);
    }
    return new HashMap<String, Timestamp>(4) {
      {
        put("startTime", startTime);
        put("endTime", endTime);
      }
    };
  }

  /** 生成优惠券编号 */
  public static String getCouponSn() {
    return "C" + System.currentTimeMillis();
  }

  /**
   * 优惠券弹窗
   *
   * @param param
   * @return popListVo
   */
  public List<CouponGivePopVo> popWindows(CouponGivePopParam param) {
    /* 查询，并筛选出正确的使用限制条件 */
    SelectWhereStep<? extends Record> select =
        db().select(
                MRKING_VOUCHER.ID,
                MRKING_VOUCHER.ACT_CODE,
                MRKING_VOUCHER.ACT_NAME,
                MRKING_VOUCHER.DENOMINATION,
                MRKING_VOUCHER.LEAST_CONSUME,
                MRKING_VOUCHER.USE_CONSUME_RESTRICT,
                MRKING_VOUCHER.SURPLUS,
                MRKING_VOUCHER.VALIDITY_TYPE,
                MRKING_VOUCHER.VALIDITY,
                MRKING_VOUCHER.VALIDITY_HOUR,
                MRKING_VOUCHER.VALIDITY_MINUTE,
                MRKING_VOUCHER.START_TIME,
                MRKING_VOUCHER.END_TIME,
                MRKING_VOUCHER.RECOMMEND_GOODS_ID,
                MRKING_VOUCHER.RECOMMEND_CAT_ID,
                MRKING_VOUCHER.RECOMMEND_SORT_ID,
                MRKING_VOUCHER.USE_SCORE,
                MRKING_VOUCHER.SCORE_NUMBER,
                MRKING_VOUCHER.LIMIT_SURPLUS_FLAG)
            .from(MRKING_VOUCHER);
    select
        .where(MRKING_VOUCHER.TYPE.eq(NumberUtils.BYTE_ZERO))
        .and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
    if (StringUtil.isNotEmpty(param.getActName())) {
      select.where(MRKING_VOUCHER.ACT_NAME.contains(param.getActName()));
    }
    select.orderBy(MRKING_VOUCHER.CREATE_TIME.desc());
    List<CouponGivePopVo> popVo = select.fetchInto(CouponGivePopVo.class);

    return popVo;
  }

  /**
   * 废除优惠券
   *
   * @param param
   * @return
   */
  public void deleteCoupon(CouponGiveDeleteParam param) {
    /* 假删除实现废除某个用户的某张优惠券 */
    db().update(CUSTOMER_AVAIL_COUPONS)
        .set(CUSTOMER_AVAIL_COUPONS.IS_USED, (byte) 3)
        .where(CUSTOMER_AVAIL_COUPONS.ID.eq(param.getId()))
        .execute();
  }

  /** 优惠券类型，打折还是优惠;0为减价，1为打折 */
  private static final String DISCOUNT = "discount";

  private static final String VOUCHER = "voucher";

  /**
   * 参与表单反馈领取优惠券
   *
   * @param couponId 优惠券活动id
   * @param userId 用户id
   * @return 优惠券编号
   */
  public String collectingCoupons(Integer couponId, Integer userId) {
    Optional<MrkingVoucherRecord> result =
        db().selectFrom(MV).where(MV.ID.eq(couponId)).fetchOptional();
    MrkingVoucherRecord record =
        result.orElseThrow(() -> new RuntimeException("Coupon_id Info doesn't exist"));
    CustomerAvailCouponsRecord couponsRecord = new CustomerAvailCouponsRecord();
    couponsRecord.setUserId(userId);
    couponsRecord.setAccessId(couponId);
    couponsRecord.setAccessMode((byte) 1);
    couponsRecord.setActDesc(record.getActName());
    couponsRecord.setActId(couponId);
    couponsRecord.setAmount(record.getDenomination());
    couponsRecord.setStartTime(record.getStartTime());
    couponsRecord.setEndTime(record.getEndTime());
    // 1表示表单送券
    couponsRecord.setGetSource((byte) 1);
    // 优惠券类型，打折还是优惠;0为减价，1为打折
    if (DISCOUNT.equals(record.getActCode())) {
      couponsRecord.setActType(1);
      couponsRecord.setType((byte) 1);
    } else if (VOUCHER.equals(record.getActCode())) {
      couponsRecord.setActType(0);
      couponsRecord.setType((byte) 0);
    }
    // 0：未使用
    couponsRecord.setIsUsed((byte) 0);
    // 0：未删除
    couponsRecord.setDelFlag((byte) 0);
    String couponSn = getCouponSn();
    couponsRecord.setCouponSn(couponSn);
    db().executeInsert(couponsRecord);
    return couponSn;
  }
}
