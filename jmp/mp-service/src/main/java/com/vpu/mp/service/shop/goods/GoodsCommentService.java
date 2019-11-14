package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.CommentGoods;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponDetailsVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.goods.comment.*;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.comment.*;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.ScoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.vpu.mp.db.shop.Tables.*;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * 商品评价
 *
 * @author liangchen
 * @date 2019年7月7日
 */
@Service
public class GoodsCommentService extends ShopBaseService {
  @Autowired private CouponGiveService couponGiveService;
  @Autowired private ScoreService scoreService;
  @Autowired private AccountService accountService;
  @Autowired protected HttpServletRequest request;
  public static final int THREE = 3;
  public static final int FOUR = 4;
  public static final int FIVE = 5;
  public static final byte GET_SOURCE = 3;

  /**
   * 分页获取评价信息
   *
   * @param param
   * @return
   */
  public PageResult<GoodsCommentVo> getPageList(GoodsCommentPageListParam param) {

    SelectConditionStep<? extends Record> select =
        (SelectConditionStep<? extends Record>)
            db().select(
                    COMMENT_GOODS.ID,
                    COMMENT_GOODS.ORDER_SN,
                    COMMENT_GOODS.COMMSTAR,
                    COMMENT_GOODS.COMM_NOTE,
                    COMMENT_GOODS.CREATE_TIME,
                    COMMENT_GOODS.ANONYMOUSFLAG,
                    COMMENT_GOODS.BOGUS_USERNAME,
                    GOODS.GOODS_NAME,
                    GOODS.GOODS_IMG,
                    USER.USERNAME,
                    USER.MOBILE,
                    COMMENT_AWARD.NAME,
                    COMMENT_AWARD.AWARD_TYPE,
                    COMMENT_AWARD.SCORE,
                    COMMENT_AWARD.ACCOUNT)
                .from(COMMENT_GOODS)
                .leftJoin(GOODS)
                .on(GOODS.GOODS_ID.eq(COMMENT_GOODS.GOODS_ID))
                .leftJoin(USER)
                .on(USER.USER_ID.eq(COMMENT_GOODS.USER_ID))
                .leftJoin(COMMENT_AWARD)
                .on(COMMENT_GOODS.COMMENT_AWARD_ID.eq(COMMENT_AWARD.ID))
        .where(COMMENT_GOODS.DEL_FLAG.eq(BYTE_ZERO));
    this.buildOptions(select, param);
    select.orderBy(COMMENT_GOODS.CREATE_TIME.desc());

    PageResult<GoodsCommentVo> pageResult =
        this.getPageResult(
            select, param.getCurrentPage(), param.getPageRows(), GoodsCommentVo.class);
    for (GoodsCommentVo vo : pageResult.dataList) {
      String content =
          db().select(COMMENT_GOODS_ANSWER.CONTENT)
              .from(COMMENT_GOODS_ANSWER)
              .where(COMMENT_GOODS_ANSWER.COMMENT_ID.eq(vo.getId()))
              .and(
                  COMMENT_GOODS_ANSWER.DEL_FLAG.eq(
                      (byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE))
              .fetchOptionalInto(String.class)
              .orElse(null);
      vo.setContent(content);
    }
    return pageResult;
  }

  /**
   * 根据过滤条件构造对应的sql语句
   *
   * @param select
   * @param param
   */
  private SelectConditionStep<?> buildOptions(
      SelectConditionStep<? extends Record> select, GoodsCommentPageListParam param) {
    SelectConditionStep<?> scs =
        select.and(
            COMMENT_GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

    if (!StringUtils.isBlank(param.getOrderSn())) {
      scs = scs.and(COMMENT_GOODS.ORDER_SN.like(this.likeValue(param.getOrderSn())));
    }

    if (!StringUtils.isBlank(param.getGoodsName())) {
      scs = scs.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
    }

    if (!StringUtils.isBlank(param.getMobile())) {
      scs = scs.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
    }

    if (param.getCommstar() != GoodsCommentPageListParam.COMMSTAR_DEFAULT_VALUE) {
      scs = scs.and(COMMENT_GOODS.COMMSTAR.eq(param.getCommstar()));
    }
    if (param.getAwardActivityId() != null) {
      scs.and(COMMENT_GOODS.COMMENT_AWARD_ID.eq(param.getAwardActivityId()));
    }
    return scs;
  }

  /**
   * 分页获取评价审核信息
   *
   * @param param
   * @return
   */
  public PageResult<GoodsCommentCheckListVo> getCheckPageList(GoodsCommentPageListParam param) {
      SelectConditionStep<? extends Record> selectFrom = db().select(
          COMMENT_GOODS.ID,
          COMMENT_GOODS.ORDER_SN,
          GOODS.GOODS_IMG,
          GOODS.GOODS_NAME,
          USER.USERNAME,
          USER.MOBILE,
          COMMENT_GOODS.COMMSTAR,
          COMMENT_GOODS.COMM_NOTE,
          COMMENT_GOODS_ANSWER.CONTENT,
          COMMENT_GOODS.CREATE_TIME,
          COMMENT_GOODS.ANONYMOUSFLAG,
          COMMENT_GOODS.FLAG,
          COMMENT_AWARD.AWARD_TYPE)
          .from(COMMENT_GOODS)
          .leftJoin(GOODS).on(COMMENT_GOODS.GOODS_ID.eq(GOODS.GOODS_ID))
          .leftJoin(USER).on(COMMENT_GOODS.USER_ID.eq(USER.USER_ID))
          .leftJoin(COMMENT_GOODS_ANSWER).on(COMMENT_GOODS.ID.eq(COMMENT_GOODS_ANSWER.COMMENT_ID))
          .leftJoin(COMMENT_AWARD).on(COMMENT_GOODS.COMMENT_AWARD_ID.eq(COMMENT_AWARD.ID))
          .where(COMMENT_GOODS_ANSWER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));

      SelectConditionStep<?> select = this.buildCheckOptions(selectFrom, param);

    select.orderBy(COMMENT_GOODS.CREATE_TIME.desc());

    PageResult<GoodsCommentCheckListVo> pageResult =
        this.getPageResult(
            select, param.getCurrentPage(), param.getPageRows(), GoodsCommentCheckListVo.class);

    return pageResult;
  }

  /**
   * 根据过滤条件构造对应的sql语句
   *
   * @param selectFrom
   * @param param
   * @return
   */
  private SelectConditionStep<?> buildCheckOptions(SelectConditionStep<? extends Record> selectFrom,GoodsCommentPageListParam param) {
    SelectConditionStep<?> scs =
        selectFrom.and(
            COMMENT_GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

    if (!StringUtils.isBlank(param.getOrderSn())) {
      scs = scs.and(COMMENT_GOODS.ORDER_SN.like(this.likeValue(param.getOrderSn())));
    }

    if (!StringUtils.isBlank(param.getGoodsName())) {
      scs = scs.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
    }

    if (!StringUtils.isBlank(param.getMobile())) {
      scs = scs.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
    }

    if (param.getCommstar() != GoodsCommentPageListParam.COMMSTAR_DEFAULT_VALUE) {
      scs = scs.and(COMMENT_GOODS.COMMSTAR.eq(param.getCommstar()));
    }

    if (param.getFlag() != GoodsCommentPageListParam.FLAG_DEFAULT_VALUE) {
      scs = scs.and(COMMENT_GOODS.FLAG.eq(param.getFlag()));
    }

    return scs;
  }

  /**
   * 假删除指定评价
   *
   * @param goodsCommentId
   * @return 数据库受影响行数
   */
  public int delete(GoodsCommentIdParam goodsCommentId) {
    return db().update(COMMENT_GOODS)
        .set(COMMENT_GOODS.DEL_FLAG, (byte) 1)
        .where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
        .execute();
  }

  /**
   * 评价回复
   *
   * @param goodsCommentAnswer
   * @return 数据库受影响行数
   */
  public int insertAnswer(GoodsCommentAnswerParam goodsCommentAnswer) {
    int result =
        db().insertInto(
                COMMENT_GOODS_ANSWER, COMMENT_GOODS_ANSWER.COMMENT_ID, COMMENT_GOODS_ANSWER.CONTENT)
            .values(goodsCommentAnswer.getCommentId(), goodsCommentAnswer.getContent())
            .execute();
    return result;
  }

  /**
   * 删除评价回复
   *
   * @param goodsCommentId
   * @return
   */
  public void delAnswer(GoodsCommentIdParam goodsCommentId) {
    db().update(COMMENT_GOODS_ANSWER)
        .set(COMMENT_GOODS_ANSWER.DEL_FLAG, (byte) 1)
        .where(COMMENT_GOODS_ANSWER.COMMENT_ID.eq(goodsCommentId.getId()))
        .and(COMMENT_GOODS_ANSWER.DEL_FLAG.eq((byte) 0))
        .execute();
  }

  /**
   * 修改评价审核状态
   *
   * @param goodsCommentId
   * @return
   */
  public int passflag(GoodsCommentIdParam goodsCommentId) {
    return db().update(COMMENT_GOODS)
        .set(COMMENT_GOODS.FLAG, (byte) GoodsCommentPageListParam.FLAG_PASS_VALUE)
        .where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
        .execute();
  }

  public int refuseflag(GoodsCommentIdParam goodsCommentId) {
    return db().update(COMMENT_GOODS)
        .set(COMMENT_GOODS.FLAG, (byte) GoodsCommentPageListParam.FLAG_REFUSE_VALUE)
        .where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
        .execute();
  }

  /**
   * 分页查询添加评论列表
   *
   * @param param
   * @return
   */
  public PageResult<GoodsCommentAddListVo> getAddList(GoodsCommentPageListParam param) {

    SelectConditionStep<?extends Record>
        selectFrom =
            db().select(
                    GOODS.GOODS_ID,
                    GOODS.GOODS_IMG,
                    GOODS.GOODS_NAME,
                    GOODS.GOODS_SN,
                    SORT.SORT_NAME,
                    GOODS.SHOP_PRICE,
                    GOODS.GOODS_NUMBER,
                    GOODS.GOODS_SALE_NUM,
                    GOODS_SUMMARY.UV,
                    GOODS_SUMMARY.PV)
                .from(GOODS)
                .leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
                .leftJoin(GOODS_SUMMARY).on(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID))
                .where(GOODS.DEL_FLAG.eq(BYTE_ZERO));

    SelectConditionStep<?> select = this.buildAddOptions(selectFrom, param);

    PageResult<GoodsCommentAddListVo> pageResult =
        this.getPageResult(
            select, param.getCurrentPage(), param.getPageRows(), GoodsCommentAddListVo.class);

    return pageResult;
  }

  /**
   * 根据过滤条件构造对应的sql语句
   *
   * @param selectFrom
   * @param param
   * @return
   */
  private SelectConditionStep<?> buildAddOptions(
      SelectConditionStep<?extends Record>
          selectFrom,
      GoodsCommentPageListParam param) {
    SelectConditionStep<?> scs =
        selectFrom.and(GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

    if (!StringUtils.isBlank(param.getGoodsName())) {
      scs = scs.and(GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
    }

    if (!StringUtils.isBlank(param.getSortName())) {
      scs = scs.and(SORT.SORT_NAME.eq(param.getSortName()));
    }

    return scs;
  }

  /**
   * 商家手动添加评价
   *
   * @param goodsCommentAddComm
   * @return 数据库受影响行数
   */
  public int addComment(GoodsCommentAddCommParam goodsCommentAddComm) {

    int result =
        db().insertInto(
                COMMENT_GOODS,
                COMMENT_GOODS.SHOP_ID,
                COMMENT_GOODS.GOODS_ID,
                COMMENT_GOODS.BOGUS_USERNAME,
                COMMENT_GOODS.BOGUS_USER_AVATAR,
                COMMENT_GOODS.CREATE_TIME,
                COMMENT_GOODS.COMMSTAR,
                COMMENT_GOODS.COMM_NOTE,
                COMMENT_GOODS.COMM_IMG,
                COMMENT_GOODS.ANONYMOUSFLAG,
                COMMENT_GOODS.IS_SHOP_ADD)
            .values(
                getShopId(),
                goodsCommentAddComm.getGoodsId(),
                goodsCommentAddComm.getBogusUsername(),
                goodsCommentAddComm.getBogusUserAvatar(),
                goodsCommentAddComm.getCreateTime(),
                goodsCommentAddComm.getCommstar(),
                goodsCommentAddComm.getCommNote(),
                goodsCommentAddComm.getCommImg(),
                goodsCommentAddComm.getAnonymousFlag(),
                NumberUtils.BYTE_ONE)
            .execute();

    return result;
  }

  /**
   * 小程序-获取商品评价列表（待评价/已评价）
   *
   * @param param 获取商品评价列表入参
   * @return commentListVo
   */
  public List<CommentListVo> commentList(CommentListParam param) {

    // 声明要用到的订单编号集合
    List<String> orderSn = new ArrayList<>();
    // 如果传入的订单编号不为空，直接遍历对应订单
    if (!StringUtils.isBlank(param.getOrderSn())) {
      orderSn.add(param.getOrderSn());
    }
    // 如果传入的订单编号为空，查找数据库中满足条件的订单
    else {
      // 查询当前用户在当前店铺下的所有满足状态要求的订单编号
      orderSn =
          db().select(ORDER_INFO.ORDER_SN)
              .from(ORDER_INFO)
              .where(ORDER_INFO.USER_ID.eq(param.getUserId()))
              .and(ORDER_INFO.SHOP_ID.eq(getShopId()))
              .and(
                  ORDER_INFO
                      .ORDER_STATUS
                      .eq(OrderConstant.ORDER_RECEIVED)
                      .or(ORDER_INFO.ORDER_STATUS.eq(OrderConstant.ORDER_FINISHED)))
              .fetchInto(String.class);
    }
    // 声明要用到的出参集合
    List<CommentListVo> commentListVo = new ArrayList<>();
    // 待评价商品
      if (param.getCommentFlag().equals(BYTE_ZERO)) {
      // 遍历订单查找商品行信息
      for (String orderSnTemp : orderSn) {
        List<CommentListVo> tempCommentList =
            db().select(
                    ORDER_GOODS.GOODS_ID,
                    ORDER_GOODS.GOODS_NAME,
                    ORDER_GOODS.GOODS_IMG,
                    ORDER_GOODS.ORDER_SN,
                    ORDER_INFO.CREATE_TIME,
                    ORDER_GOODS.COMMENT_FLAG)
                .from(ORDER_GOODS)
                .leftJoin(ORDER_INFO)
                .on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
                .where(ORDER_GOODS.ORDER_SN.eq(orderSnTemp))
                .and(ORDER_GOODS.SHOP_ID.eq(getShopId()))
                .and(ORDER_GOODS.COMMENT_FLAG.eq(param.getCommentFlag()))
                .and(ORDER_GOODS.GOODS_NUMBER.greaterThan(ORDER_GOODS.RETURN_NUMBER))
                .fetchInto(CommentListVo.class);
        // 当前订单下的所有满足条件的商品信息作为出参
        commentListVo.addAll(tempCommentList);
        // 为商品添加评价有礼相关信息
        selectCommentAward(commentListVo);
      }
    } else { // 已评价商品
      for (String orderSnTemp : orderSn) {
        List<CommentListVo> tempCommentList =
            db().select(
                    ORDER_GOODS.GOODS_ID,
                    ORDER_GOODS.GOODS_NAME,
                    ORDER_GOODS.GOODS_IMG,
                    ORDER_GOODS.ORDER_SN,
                    ORDER_INFO.CREATE_TIME,
                    COMMENT_GOODS.COMMSTAR,
                    COMMENT_GOODS.COMM_NOTE,
                    COMMENT_GOODS.COMM_IMG,
                    COMMENT_GOODS_ANSWER.CONTENT,
                    ORDER_GOODS.COMMENT_FLAG)
                .from(ORDER_GOODS)
                .leftJoin(ORDER_INFO)
                .on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
                .leftJoin(COMMENT_GOODS)
                .on(
                    ORDER_GOODS
                        .ORDER_SN
                        .eq(COMMENT_GOODS.ORDER_SN)
                        .and(ORDER_GOODS.GOODS_ID.eq(COMMENT_GOODS.GOODS_ID)))
                .leftJoin(COMMENT_GOODS_ANSWER)
                .on(
                    COMMENT_GOODS
                        .ID
                        .eq(COMMENT_GOODS_ANSWER.COMMENT_ID)
                        .and(COMMENT_GOODS_ANSWER.DEL_FLAG.eq(BYTE_ZERO)))
                .where(ORDER_GOODS.ORDER_SN.eq(orderSnTemp))
                .and(ORDER_GOODS.SHOP_ID.eq(getShopId()))
                .and(ORDER_GOODS.COMMENT_FLAG.eq(param.getCommentFlag()))
                .and(ORDER_GOODS.GOODS_NUMBER.greaterThan(ORDER_GOODS.RETURN_NUMBER))
                .fetchInto(CommentListVo.class);
        // 当前订单下的所有满足条件的商品信息作为出参
        commentListVo.addAll(tempCommentList);
        // 为商品添加评价有礼相关信息
        selectCommentAward(commentListVo);
      }
    }

    return commentListVo;
  }

  /**
   * 得到当前所有可用活动和其触发条件的信息
   *
   * @return 所有可用活动和其触发条件
   */
  public List<TriggerConditionVo> getAllActivities() {
    // 得到系统当前时间作为筛选依据
    Timestamp nowTime = new Timestamp(System.currentTimeMillis());
    // 筛选评价有礼活动类型
    List<TriggerConditionVo> triggerConditionVos =
        db().select(
                COMMENT_AWARD.ID,
                COMMENT_AWARD.GOODS_TYPE,
                COMMENT_AWARD.GOODS_IDS,
                COMMENT_AWARD.COMMENT_NUM)
            .from(COMMENT_AWARD)
            .where(COMMENT_AWARD.DEL_FLAG.eq(BYTE_ZERO))
            .and(COMMENT_AWARD.STATUS.eq(NumberUtils.BYTE_ONE))
            .and(COMMENT_AWARD.AWARD_NUM.greaterThan(COMMENT_AWARD.SEND_NUM))
            .and(
                COMMENT_AWARD
                    .IS_FOREVER
                    .eq(NumberUtils.BYTE_ONE)
                    .or(
                        COMMENT_AWARD
                            .START_TIME
                            .lessOrEqual(nowTime)
                            .and(COMMENT_AWARD.END_TIME.greaterOrEqual(nowTime))))
            .fetchInto(TriggerConditionVo.class);
    return triggerConditionVos;
  }

  /**
   * 得到当前商品所满足触发条件的所有活动的id集合
   *
   * @param goodsId 当前商品id
   * @param triggerConditionVos 触发条件信息
   * @return 当前商品所满足触发条件的所有活动的id集合
   */
  public Set<Integer> getAllActIds(Integer goodsId, List<TriggerConditionVo> triggerConditionVos) {
    // 声明一个集合来存放满足条件的活动id
    Set<Integer> actIds = new HashSet<>();
    for (TriggerConditionVo vo : triggerConditionVos) {
      // 触发条件：全部商品
      if (vo.getGoodsType().equals(NumberUtils.INTEGER_ONE)) {
        actIds.add(vo.getId());
      } // 触发条件：指定商品
      else if (vo.getGoodsType().equals(NumberUtils.INTEGER_TWO)) {
        String[] arr = vo.getGoodsIds().split(",");
        if (Arrays.asList(arr).contains(goodsId.toString())) {
          actIds.add(vo.getId());
        }
      } // 触发条件：评论数小于一定值
      else {
        Integer commNum =
            db().select(DSL.count(COMMENT_GOODS.ID))
                .from(COMMENT_GOODS)
                .where(COMMENT_GOODS.GOODS_ID.eq(goodsId))
                .and(COMMENT_GOODS.FLAG.eq(NumberUtils.BYTE_ONE))
                .and(COMMENT_GOODS.DEL_FLAG.eq(BYTE_ZERO))
                .fetchOptionalInto(Integer.class)
                .orElse(0);
        if (commNum < vo.getCommentNum()) {
          actIds.add(vo.getId());
        }
      }
    }
    return actIds;
  }

  /**
   * 根据优先级确定当前商品参加哪个评价有礼活动
   *
   * @param actIds 有所符合条件的活动id集合
   * @return 最终参与的活动的id
   */
  public Integer getTheActId(Set<Integer> actIds) {
    // 遍历筛选出来的评价有礼活动id，查找当前商品的活动奖励
    Integer actId =
        db().select(COMMENT_AWARD.ID)
            .from(COMMENT_AWARD)
            .where(COMMENT_AWARD.ID.in(actIds))
            .orderBy(COMMENT_AWARD.LEVE.desc())
            .limit(NumberUtils.INTEGER_ONE)
            .fetchOptionalInto(Integer.class)
            .orElseThrow(() -> new RuntimeException("未找到对应活动id"));
    return actId;
  }

  /**
   * 为商品添加评价有礼相关信息
   *
   * @param commentListVo 待添加评价有礼的商品行
   */
  public void selectCommentAward(List<CommentListVo> commentListVo) {
    // 得到当前所有可用活动和其触发条件的信息
    List<TriggerConditionVo> triggerConditionVos = getAllActivities();
    if (triggerConditionVos == null || triggerConditionVos.isEmpty()) {
      return;
    } else {
      // 判断每个商品行对应的评价有礼活动奖励
      for (CommentListVo forGoodsId : commentListVo) {
        Integer actId;
        // 如果是待评价的商品
          if (forGoodsId.getCommentFlag().equals(BYTE_ZERO)) {
          // 得到当前商品所满足触发条件的所有活动的id集合
          Set<Integer> actIds = getAllActIds(forGoodsId.getGoodsId(), triggerConditionVos);
          if (actIds == null || actIds.isEmpty()) {
            continue;
          } else {
            // 根据优先级确定当前商品参加哪个评价有礼活动
            actId = getTheActId(actIds);
          }
        }
        // 如果是已评价的商品
        else {
          actId =
              db().select(COMMENT_GOODS.COMMENT_AWARD_ID)
                  .from(COMMENT_GOODS)
                  .where(COMMENT_GOODS.GOODS_ID.eq(forGoodsId.getGoodsId()))
                  .and(COMMENT_GOODS.ORDER_SN.eq(forGoodsId.getOrderSn()))
                  .fetchOptionalInto(Integer.class)
                  .orElse(NumberUtils.INTEGER_ZERO);
          if (actId.equals(NumberUtils.INTEGER_ZERO)) {
            continue;
          }
        }
        forGoodsId.setId(actId);
        // 查询活动奖励和获得奖励所需要的评价条件
        AwardContentVo awardContentVos =
            db().select(
                    COMMENT_AWARD.AWARD_TYPE,
                    COMMENT_AWARD.SCORE,
                    COMMENT_AWARD.ACTIVITY_ID,
                    COMMENT_AWARD.ACCOUNT,
                    COMMENT_AWARD.AWARD_PATH,
                    COMMENT_AWARD.AWARD_IMG,
                    COMMENT_AWARD.COMMENT_TYPE,
                    COMMENT_AWARD.COMMENT_WORDS,
                    COMMENT_AWARD.HAS_PIC_NUM,
                    COMMENT_AWARD.HAS_FIVE_STARS)
                .from(COMMENT_AWARD)
                .where(COMMENT_AWARD.ID.eq(actId))
                .fetchOneInto(AwardContentVo.class);
        // 设置获得奖励所需要的评价条件
        forGoodsId.setCommentType(awardContentVos.getCommentType());
        forGoodsId.setCommentWords(awardContentVos.getCommentWords());
        forGoodsId.setHasPicNum(awardContentVos.getHasPicNum());
        forGoodsId.setHasFiveStars(awardContentVos.getHasFiveStars());
        // 设置活动奖励
        forGoodsId.setAwardType(awardContentVos.getAwardType());
        // 活动奖励1：赠送积分
        if (awardContentVos.getAwardType().equals(NumberUtils.INTEGER_ONE)) {
          forGoodsId.setAward(awardContentVos.getScore().toString());
        }
        // 活动奖励2：赠送优惠券
        else if (awardContentVos.getAwardType().equals(NumberUtils.INTEGER_TWO)) {
          String couponName =
              db().select(MRKING_VOUCHER.ACT_NAME)
                  .from(MRKING_VOUCHER)
                  .where(MRKING_VOUCHER.ID.eq(awardContentVos.getActivityId()))
                  .fetchOptionalInto(String.class)
                  .orElse("评价有礼优惠券");
          forGoodsId.setAward("id:" + awardContentVos.getActivityId() + "," + "name:" + couponName);
        }
        // 活动奖励3：赠送用户余额
        else if (awardContentVos.getAwardType().equals(THREE)) {
          forGoodsId.setAward(awardContentVos.getAccount().toString());
        }
        // 活动奖励4：赠送抽奖机会
        else if (awardContentVos.getAwardType().equals(FOUR)) {
          forGoodsId.setAward(awardContentVos.getActivityId().toString());
        }
        // 活动奖励5：自定义奖励
        else if (awardContentVos.getAwardType().equals(FIVE)) {
          forGoodsId.setAward(
              "path:"
                  + awardContentVos.getAwardPath()
                  + ","
                  + "img:"
                  + awardContentVos.getAwardImg());
        }
      }
    }
  }

  /**
   * 小程序-用户添加商品评价
   *
   * @param param 用户添加评价的入参
   */
  public void addCommentByUser(AddCommentParam param) throws MpException {
    // 先查询当前店铺评价审核配置
    String commConfig =
        db().select(SHOP_CFG.V)
            .from(SHOP_CFG)
            .where(SHOP_CFG.K.eq("comment"))
            .fetchOptionalInto(String.class)
            .orElse("0");
    // 审核配置转Integer类型
    Integer commCfg = Integer.valueOf(commConfig);
    // 设置商品评论表里的审核标识
    Byte flag;
    // 不用审核时评价状态直接为通过，否则为待审核
    if (commCfg.equals(NumberUtils.INTEGER_ZERO)) {
      flag = NumberUtils.BYTE_ONE;
    } else {
        flag = BYTE_ZERO;
    }
    // 为指定商品添加评论
    db().insertInto(
            COMMENT_GOODS,
            COMMENT_GOODS.SHOP_ID,
            COMMENT_GOODS.USER_ID,
            COMMENT_GOODS.GOODS_ID,
            COMMENT_GOODS.ORDER_SN,
            COMMENT_GOODS.COMMSTAR,
            COMMENT_GOODS.COMM_NOTE,
            COMMENT_GOODS.COMM_IMG,
            COMMENT_GOODS.ANONYMOUSFLAG,
            COMMENT_GOODS.FLAG)
        .values(
            getShopId(),
            param.getUserId(),
            param.getGoodsId(),
            param.getOrderSn(),
            param.getCommstar(),
            param.getCommNote(),
            param.getCommImg(),
            param.getAnonymousflag(),
            flag)
        .execute();
    // 添加评论后将order_goods表中comment_flag置为1
    db().update(ORDER_GOODS)
        .set(ORDER_GOODS.COMMENT_FLAG, NumberUtils.BYTE_ONE)
        .where(ORDER_GOODS.ORDER_SN.eq(param.getOrderSn()))
        .and(ORDER_GOODS.GOODS_ID.eq(param.getGoodsId()))
        .execute();
    // order表中的comment_flag也置为1
    db().update(ORDER_INFO)
        .set(ORDER_INFO.COMMENT_FLAG, NumberUtils.BYTE_ONE)
        .where(ORDER_INFO.ORDER_SN.eq(param.getOrderSn()))
        .execute();
    // 添加评价关联评价有礼-发放礼物
    if (null != param.getId()) {
      // 判断是否满足当前活动需要的评价条件
      AwardConditionVo awardCondition =
          db().select(
                  COMMENT_AWARD.COMMENT_TYPE,
                  COMMENT_AWARD.COMMENT_WORDS,
                  COMMENT_AWARD.HAS_PIC_NUM,
                  COMMENT_AWARD.HAS_FIVE_STARS)
              .from(COMMENT_AWARD)
              .where(COMMENT_AWARD.ID.eq(param.getId()))
              .fetchOneInto(AwardConditionVo.class);
      if (awardCondition.getCommentType().equals((byte)2)) {
        // 心得超过规定字数
        if (param.getCommNote().length() < awardCondition.getCommentWords()) {
          return;
        }
        // 晒图评论
        else if (awardCondition.getHasPicNum().equals(NumberUtils.BYTE_ONE)
            && StringUtils.isBlank(param.getCommImg())) {
          return;
        }
        // 五星好评
        else if (awardCondition.getHasFiveStars().equals(NumberUtils.BYTE_ONE)
            && param.getCommstar() != FIVE) {
          return;
        }
      }
      // 为参与评价有礼活动的商品设置活动id 此时已经经过满足评价条件的校验了
      db().update(COMMENT_GOODS)
          .set(COMMENT_GOODS.COMMENT_AWARD_ID, param.getId())
          .where(COMMENT_GOODS.GOODS_ID.eq(param.getGoodsId()))
          .and(COMMENT_GOODS.ORDER_SN.eq(param.getOrderSn()))
          .and(COMMENT_GOODS.DEL_FLAG.eq(BYTE_ZERO))
          .execute();
      // 活动奖励1：赠送积分
      if (param.getAwardType().equals(NumberUtils.INTEGER_ONE)) {
        // 给当前用户赠送积分
        Integer[] userIdArray = {param.getUserId()};
        scoreService.updateMemberScore(
            new ScoreParam() {
              {
                setUserId(userIdArray);
                setScore(Integer.valueOf(param.getAward()));
                  setScoreStatus(BYTE_ZERO);
                setDesc("score");
                setOrderSn(param.getOrderSn());
                setRemark("评价有礼获得");
              }
            },
            0,
            (byte) 11,
            (byte) 0);
      }
      // 活动奖励2：赠送优惠券
      else if (param.getAwardType().equals(NumberUtils.INTEGER_TWO)) {
        // 给当前用户赠送优惠券
          Integer shopId = getShopId();
          CouponGiveQueueParam giveCoupon = new CouponGiveQueueParam(){{
              setShopId(shopId);
              setActId(1);
              setGetSource(GET_SOURCE);
              setAccessMode(BYTE_ZERO);
              setCouponArray(new String[] {param.getAward()});
              setUserIds(new ArrayList<Integer>(){{add(param.getUserId());}});
          }};
          //调用定向发券中抽取出来的公共方法
          couponGiveService.handlerCouponGive(giveCoupon);
      }
      // 活动奖励3：赠送用户余额
      else if (param.getAwardType().equals(THREE)) {
        // 给当前用户赠送余额
        // 获取语言 用于国际化
        String language =
            org.springframework.util.StringUtils.isEmpty(request.getHeader("V-Lang"))
                ? ""
                : request.getHeader("V-Lang");
        accountService.addUserAccount(
            new AccountParam() {
              {
                setUserId(param.getUserId());
                setAmount(BigDecimal.valueOf(Double.parseDouble(param.getAward())));
                setOrderSn(param.getOrderSn());
              }
            },
            0,
            (byte) 8,
            (byte) 1,
            language);
      }
      // 活动奖励4：赠送抽奖机会
      else if (param.getAwardType().equals(FOUR)) {
        // 前端展示内容为：获得一次抽奖机会，可以选择进入抽奖页面
      }
      // 活动奖励5：自定义奖励
      else if (param.getAwardType().equals(FIVE)) {
        // 前端展示内容：查看神秘奖励，可以选择进入path链接
      }
    }
  }

  /**
   * 给当前用户赠送优惠券
   *
   * @param param 会员信息、活动信息、优惠券信息
   */
  public void giveCouponByAct(AddCommentParam param) {
    // 判断删除状态和库存
    Integer surplus =
        db().select(MRKING_VOUCHER.SURPLUS)
            .from(MRKING_VOUCHER)
            .where(MRKING_VOUCHER.ID.eq(Integer.valueOf(param.getAward())))
            .and(MRKING_VOUCHER.DEL_FLAG.eq(BYTE_ZERO))
            .fetchOptionalInto(Integer.class)
            .orElse(NumberUtils.INTEGER_ZERO);
    // 如果库存为0，返回信息库存不足
    if (surplus.equals(NumberUtils.INTEGER_ZERO)) {
      logger().info("所选优惠券库存不足");
    }
    // 库存足够，发券
    else {
      // 获取优惠券详情
      CouponDetailsVo couponDetails =
          db().select(
                  MRKING_VOUCHER.ACT_NAME,
                  MRKING_VOUCHER.ACT_CODE,
                  MRKING_VOUCHER.DENOMINATION,
                  MRKING_VOUCHER.START_TIME,
                  MRKING_VOUCHER.END_TIME,
                  MRKING_VOUCHER.VALIDITY_TYPE,
                  MRKING_VOUCHER.VALIDITY,
                  MRKING_VOUCHER.VALIDITY_HOUR,
                  MRKING_VOUCHER.VALIDITY_MINUTE)
              .from(MRKING_VOUCHER)
              .where(MRKING_VOUCHER.DEL_FLAG.eq(BYTE_ZERO))
              .and(MRKING_VOUCHER.ID.eq(Integer.valueOf(param.getAward())))
              .fetchOneInto(CouponDetailsVo.class);
      // 获取优惠券类型
      byte type;
      String voucher = "voucher";
      // 减价
      if (voucher.equals(couponDetails.getActCode())) {
        type = 0;
      }
      // 打折
      else {
        type = 1;
      }
      // 得到开始时间和结束时间
      Map<String, Timestamp> timeMap = couponGiveService.getCouponTime(couponDetails);
      // 为用户发券，入库
      db().insertInto(
              CUSTOMER_AVAIL_COUPONS,
              CUSTOMER_AVAIL_COUPONS.COUPON_SN,
              CUSTOMER_AVAIL_COUPONS.USER_ID,
              CUSTOMER_AVAIL_COUPONS.ACT_ID,
              CUSTOMER_AVAIL_COUPONS.TYPE,
              CUSTOMER_AVAIL_COUPONS.AMOUNT,
              CUSTOMER_AVAIL_COUPONS.ACT_DESC,
              CUSTOMER_AVAIL_COUPONS.ACCESS_ID,
              CUSTOMER_AVAIL_COUPONS.GET_SOURCE,
              CUSTOMER_AVAIL_COUPONS.START_TIME,
              CUSTOMER_AVAIL_COUPONS.END_TIME)
          .values(
              CouponGiveService.getCouponSn(),
              param.getUserId(),
              Integer.valueOf(param.getAward()),
              type,
              couponDetails.getDenomination(),
              couponDetails.getActName(),
              param.getId(),
              GET_SOURCE,
              timeMap.get("startTime"),
              timeMap.get("endTime"))
          .execute();
      // 发券成功后，优惠券库存减少
      Integer newSurplus = surplus - 1;
      db().update(MRKING_VOUCHER)
          .set(MRKING_VOUCHER.SURPLUS, newSurplus)
          .where(MRKING_VOUCHER.ID.eq(Integer.valueOf(param.getAward())))
          .and(MRKING_VOUCHER.DEL_FLAG.eq(BYTE_ZERO))
          .execute();
    }
  }

  /**
   * 统计商品评价数量
   *
   * @param goodsIds 待统计商品id集合
   * @param config 评价配置： 0不审，1先发后审，2先审后发
   * @return
   */
  public Map<Integer, Integer> statisticGoodsComment(List<Integer> goodsIds, Byte config) {
    Map<Integer, Integer> results = null;
    if (config == 2) {
      results =
          db().select(COMMENT_GOODS.GOODS_ID, DSL.count(COMMENT_GOODS.ID).as("num"))
              .from(COMMENT_GOODS)
              .where(COMMENT_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
              .and(COMMENT_GOODS.GOODS_ID.in(goodsIds))
              .groupBy(COMMENT_GOODS.GOODS_ID)
              .fetchMap(COMMENT_GOODS.GOODS_ID, DSL.field("num", Integer.class));
    } else {
      results =
          db().select(COMMENT_GOODS.GOODS_ID, DSL.count(COMMENT_GOODS.ID).as("num"))
              .from(COMMENT_GOODS)
              .where(COMMENT_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
              .and(COMMENT_GOODS.GOODS_ID.in(goodsIds))
              .and(COMMENT_GOODS.FLAG.eq((byte) 1))
              .groupBy(COMMENT_GOODS.GOODS_ID)
              .fetchMap(COMMENT_GOODS.GOODS_ID, DSL.field("num", Integer.class));
    }
    return results;
  }

  /**
   * 判断订单是否参与评价有礼活动
   *
   * @param goodsList
   * @return
   */
  public boolean orderIsCommentAward(List<CommentListVo> goodsList) {
    // 得到当前所有可用活动和其触发条件的信息
    List<TriggerConditionVo> allActivities = getAllActivities();
    if (CollectionUtils.isEmpty(allActivities)) {
      return false;
    }
    // 判断每个商品行对应的评价有礼活动奖励
    for (CommentListVo forGoodsId : goodsList) {
      // 得到当前商品所满足触发条件的所有活动的id集合
      Set<Integer> actIds = getAllActIds(forGoodsId.getGoodsId(), allActivities);
      if (actIds != null && !actIds.isEmpty()) {
        return true;
      }
    }
    return false;
  }

    /**
     * Review overdue integer.商品评价审核逾期
     *
     * @param nDays the n days
     * @return the integer
     */
    public Integer reviewOverdue(Integer nDays) {
        return db().fetchCount(CommentGoods.COMMENT_GOODS, CommentGoods.COMMENT_GOODS.DEL_FLAG.eq(BYTE_ZERO)
            .and(CommentGoods.COMMENT_GOODS.FLAG.eq(BYTE_ZERO))
            .and(CommentGoods.COMMENT_GOODS.CREATE_TIME.add(nDays).lessThan(Timestamp.valueOf(LocalDateTime.now()))));
    }
}
