package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.CART;
import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.GIVE_VOUCHER;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_TAG;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDeleteParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveGrantParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListConditionVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;

/**
 * 优惠券管理
 * 
 * @author liangchen
 * @date 2019年7月29日
 */
@Service

public class CouponGiveService extends ShopBaseService implements BaseRabbitHandler {
	private final RabbitmqSendService rabbitmqSendService;

	public CouponGiveService(RabbitmqSendService rabbitmqSendService) {
		this.rabbitmqSendService = rabbitmqSendService;
	}

	/**
	 * 优惠券发放情况分页列表
	 * 
	 * @param param
	 * @return listVo
	 */
	public PageResult<CouponGiveListVo> getCouponGiveList(CouponGiveListParam param) {
		try {
			/* 查询活动信息 */
			SelectLimitStep<Record5<String, Timestamp, String, Byte, Byte>> couponGiveListVo = db()
					.select(GIVE_VOUCHER.ACT_NAME, GIVE_VOUCHER.CREATE_TIME, GIVE_VOUCHER.SEND_CONDITION,
							GIVE_VOUCHER.SEND_ACTION, GIVE_VOUCHER.SEND_STATUS)
					.from(GIVE_VOUCHER);
			/* 模糊查询 */
			if (!StringUtils.isNullOrEmpty(param.getActName())) {
				couponGiveListVo = ((SelectWhereStep<Record5<String, Timestamp, String, Byte, Byte>>) couponGiveListVo)
						.where(GIVE_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName())));
			}
			/* 整合分页信息 */
			PageResult<CouponGiveListVo> listVo = this.getPageResult(couponGiveListVo, param.getCurrentPage(),
					param.getPageRows(), CouponGiveListVo.class);
			/* 整合活动对应优惠券信息 */
			for (CouponGiveListVo vo : listVo.getDataList()) {
				/* 解析得到活动中包含的优惠券 */
				String dataList = vo.getSendCondition();
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode;
				jsonNode = objectMapper.readTree(dataList);
				String voucherId = jsonNode.get("coupon_ids").toString();
				String id = voucherId.replace("\"", "");
				String[] idArray = id.split(",");
				/* 优惠券信息 */
				List<CouponGiveListConditionVo> tempListVo = new ArrayList<CouponGiveListConditionVo>();
				for (String selectId : idArray) {
					Optional<CouponGiveListConditionVo> couponVo = db()
							.select(MRKING_VOUCHER.ACT_NAME.as("coupon_name"), MRKING_VOUCHER.LEAST_CONSUME,
									MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.VALIDITY)
							.from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(Integer.valueOf(selectId)))
							.fetchOptionalInto(CouponGiveListConditionVo.class);
					tempListVo.add(couponVo.isPresent() ? couponVo.get() : null);
				}
				/* 完善某一活动对应的优惠券信息 */
				vo.setCouponGiveListConditionVo(tempListVo);
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
	 * @param param
	 * @return
	 */
	public PageResult<CouponGiveDetailVo> getDetail(CouponGiveDetailParam param) {
		SelectConditionStep<Record11<String, String, String, Byte, Byte, String, Timestamp, Timestamp, Timestamp, Timestamp, Byte>> detailVo = db()
				.select(USER.USERNAME, USER.MOBILE, MRKING_VOUCHER.ACT_NAME.as("coupon_name"),
						CUSTOMER_AVAIL_COUPONS.ACCESS_MODE, CUSTOMER_AVAIL_COUPONS.IS_USED,
						CUSTOMER_AVAIL_COUPONS.ORDER_SN, CUSTOMER_AVAIL_COUPONS.START_TIME,
						CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.CREATE_TIME,
						CUSTOMER_AVAIL_COUPONS.USED_TIME, CUSTOMER_AVAIL_COUPONS.DEL_FLAG)
				.from(USER, MRKING_VOUCHER, CUSTOMER_AVAIL_COUPONS)
				.where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(param.getActId()))
				.and(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(USER.USER_ID)).and(MRKING_VOUCHER.ID.eq(param.getCouponId()));
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			detailVo = detailVo.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			detailVo = detailVo.and(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (param.getIsUsed() != CouponGiveDetailParam.ISUSED_DEFAULT_VALUE) {
			detailVo = detailVo.and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) param.getIsUsed()));
		}
		/* 整合分页信息 */
		PageResult<CouponGiveDetailVo> listVo = this.getPageResult(detailVo, param.getCurrentPage(),
				param.getPageRows(), CouponGiveDetailVo.class);
		return listVo;
	}

	/**
	 * 发放优惠券
	 * 
	 * @param param
	 * @return
	 */
	public void insertGrant(CouponGiveGrantParam param) {

		try {
			/* 完成一次发券活动 */
			ObjectMapper objectMapper = new ObjectMapper();
			String condition = objectMapper.writeValueAsString(param.getCouponGiveGrantInfoParams());

			db().insertInto(GIVE_VOUCHER, GIVE_VOUCHER.ACT_NAME, GIVE_VOUCHER.SEND_CONDITION, GIVE_VOUCHER.CARD_ID,
					GIVE_VOUCHER.TAG_ID, GIVE_VOUCHER.USER, GIVE_VOUCHER.HAVE_PAY, GIVE_VOUCHER.NO_PAY,
					GIVE_VOUCHER.MAX_COUNT, GIVE_VOUCHER.MIN_COUNT, GIVE_VOUCHER.MAX_AVE_PRICE,
					GIVE_VOUCHER.MIN_AVE_PRICE, GIVE_VOUCHER.SEND_ACTION, GIVE_VOUCHER.START_TIME)
					.values(param.getActName(), condition, param.getCardId(), param.getTagId(), param.getUser(),
							param.getHavePay(), param.getNoPay(), param.getMaxCount(), param.getMinCount(),
							param.getMaxAvePrice(), param.getMinAvePrice(), param.getSendAction(), param.getStartTime())
					.execute();
			int actId = db().select(GIVE_VOUCHER.ID).from(GIVE_VOUCHER).orderBy(GIVE_VOUCHER.CREATE_TIME.desc())
					.limit(1).fetchOptionalInto(Integer.class).orElse(0);
			List<Integer> userIds = new ArrayList<>();
			/* 将发券活动写入用户-优惠券对应表 */

			/* 加购人群 */
			Date today = new Date();
			Timestamp cartDay = Util.getEarlyTimeStamp(today, -30);

			List<Record1<Integer>> cartUserIds = db().select(CART.USER_ID).from(CART)
					.where(CART.CREATE_TIME.greaterOrEqual(cartDay)).fetch();
			for (Record1<Integer> cartUserId : cartUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCartBox().equals(1)
						&& !userIds.contains(cartUserId.value1())) {
					userIds.add(cartUserId.value1());
				}
			}

			/* 购买指定商品人群 */
			String goodsIds = param.getCouponGiveGrantInfoParams().getGoodsIds().toString();
			String[] goodArray = goodsIds.split(",");

			for (String goodId : goodArray) {
				List<Record1<Integer>> orderIds = db().select(ORDER_GOODS.ORDER_ID).from(ORDER_GOODS)
						.where(ORDER_GOODS.GOODS_ID.eq(Integer.valueOf(goodId))).fetch();
				for (Record1<Integer> orderId : orderIds) {
					int userId = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
							.where(ORDER_INFO.ORDER_ID.eq(orderId.value1())).fetchOptionalInto(Integer.class).orElse(0);
					if (param.getCouponGiveGrantInfoParams().getGoodsBox().equals(1) && !userIds.contains(userId)) {
						userIds.add(userId);
					}
				}
			}
			/* 持有会员卡人群 */
			String cardIds = param.getCardId();
			String[] cardArray = cardIds.split(",");
			for (String cardId : cardArray) {
				List<Record1<Integer>> cardUserIds = db().select(USER_CARD.USER_ID).from(USER_CARD)
						.where(USER_CARD.FLAG.eq((byte) 0)).and(USER_CARD.CARD_ID.eq(Integer.valueOf(cardId))).fetch();
				for (Record1<Integer> userId : cardUserIds) {
					if (param.getCouponGiveGrantInfoParams().getCardBox().equals(1)
							&& !userIds.contains(userId.value1())) {
						userIds.add(userId.value1());
					}
				}
			}
			/* 属于标签人群 */
			String tagIds = param.getTagId();
			String[] tagArray = tagIds.split(",");
			for (String tagId : tagArray) {
				List<Record1<Integer>> tagUserIds = db().select(USER_TAG.USER_ID).from(USER_TAG)
						.where(USER_TAG.IS_DELETE.eq((byte) 0)).and(USER_TAG.TAG_ID.eq(Integer.valueOf(tagId))).fetch();
				for (Record1<Integer> userId : tagUserIds) {
					if (param.getCouponGiveGrantInfoParams().getTagBox().equals(1)
							&& !userIds.contains(userId.value1())) {
						userIds.add(userId.value1());
					}
				}
			}
			/* 选择指定的会员 */
			String memberIds = param.getUser();
			String[] memberArray = memberIds.split(",");
			for (String memberId : memberArray) {
				if (param.getCouponGiveGrantInfoParams().getMemberBox().equals(1)
						&& !userIds.contains(Integer.valueOf(memberId))) {
					userIds.add(Integer.valueOf(memberId));
				}
			}
			/* N天内有交易记录 */

			Timestamp havePayDay = Util.getEarlyTimeStamp(today, -param.getHavePay());
			List<Record1<Integer>> havePayUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
					.and(ORDER_INFO.CREATE_TIME.greaterOrEqual(havePayDay)).fetch();

			for (Record1<Integer> havePayUserId : havePayUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(havePayUserId.value1())) {
					userIds.add(havePayUserId.value1());
				}
			}

			/* N天内无交易记录 */

			Timestamp noPayDay = Util.getEarlyTimeStamp(today, -param.getNoPay());
			List<Record1<Integer>> noPayUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2))
					.and(ORDER_INFO.CREATE_TIME.greaterOrEqual(noPayDay)).fetch();
			for (Record1<Integer> noId : noPayUserIds) {
				List<Record1<Integer>> noPayId = db().select(USER.USER_ID).from(USER)
						.where(USER.USER_ID.notEqual(noId.value1())).fetch();
				for (Record1<Integer> noPayUserId : noPayId) {
					if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
							&& !userIds.contains(noPayUserId.value1())) {
						userIds.add(noPayUserId.value1());
					}
				}
			}

			/* 累计购买次数大于N次 min */

			List<Record1<Integer>> minCountUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID)
					.having(DSL.count(ORDER_INFO.ORDER_ID).greaterThan(param.getMinCount())).fetch();
			for (Record1<Integer> minCountUserId : minCountUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(minCountUserId.value1())) {
					userIds.add(minCountUserId.value1());
				}
			}

			/* 累计购买次数小于N次 max */

			List<Record1<Integer>> maxCountUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID)
					.having(DSL.count(ORDER_INFO.ORDER_ID).lessThan(param.getMaxCount())).fetch();
			for (Record1<Integer> maxCountUserId : maxCountUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(maxCountUserId.value1())) {
					userIds.add(maxCountUserId.value1());
				}
			}

			/* 购买商品均价大于N元 min */
			List<Record1<Integer>> minAvePriceUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID)
					.having(DSL.avg(ORDER_INFO.ORDER_AMOUNT).greaterThan(param.getMinAvePrice())).fetch();
			for (Record1<Integer> minAvePriceUserId : minAvePriceUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(minAvePriceUserId.value1())) {
					userIds.add(minAvePriceUserId.value1());
				}
			}
			/* 购买商品均价小于N元 max */

			List<Record1<Integer>> maxAvePriceUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO)
					.where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID)
					.having(DSL.avg(ORDER_INFO.ORDER_AMOUNT).lessThan(param.getMaxAvePrice())).fetch();
			for (Record1<Integer> maxAvePriceUserId : maxAvePriceUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(maxAvePriceUserId.value1())) {
					userIds.add(maxAvePriceUserId.value1());
				}
			}

			/* 指定时间内有登陆记录 */
			List<Record1<Integer>> loginRecordUserIds = db().select(USER_LOGIN_RECORD.USER_ID).from(USER_LOGIN_RECORD)
					.where(USER_LOGIN_RECORD.CREATE_TIME.between(
							Timestamp.valueOf(param.getCouponGiveGrantInfoParams().getPointStartTime()),
							Timestamp.valueOf(param.getCouponGiveGrantInfoParams().getPointEndTme())))
					.fetch();
			for (Record1<Integer> loginRecordUserId : loginRecordUserIds) {
				if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)
						&& !userIds.contains(loginRecordUserId.value1())) {
					userIds.add(loginRecordUserId.value1());
				}
			}
			/* 队列 */
			String couponIds = param.getCouponGiveGrantInfoParams().getCouponIds().toString();
			String[] couponArray = couponIds.split(",");
			rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_MARKETING, RabbitConfig.BINDING_EXCHANGE_COUPON_KEY,
					new CouponGiveQueueParam(getShopId(), userIds, actId, couponArray));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
	
	@RabbitListener(queues = RabbitConfig.QUEUE_COUPON_SEND, containerFactory = "simpleRabbitListenerContainerFactory")
	@RabbitHandler
	public void handler(@Payload CouponGiveQueueParam param,Message message, Channel channel) {
		try {
		/* 操作当前店铺 */
		switchShopDb(param.getShopId());
		/* 插入user-coupon关联表 */
		for (String couponId : param.getCouponArray()) {
			String couponName = db().select(MRKING_VOUCHER.ACT_NAME).from(MRKING_VOUCHER)
					.where(MRKING_VOUCHER.ID.eq(Integer.valueOf(couponId))).fetchOptionalInto(String.class)
					.orElse(null);
			BigDecimal denomination = db().select(MRKING_VOUCHER.DENOMINATION).from(MRKING_VOUCHER)
					.where(MRKING_VOUCHER.ID.eq(Integer.valueOf(couponId))).fetchOptionalInto(BigDecimal.class)
					.orElse(null);
			for (Integer userId : param.getUserIds()) {
				db().insertInto(CUSTOMER_AVAIL_COUPONS, CUSTOMER_AVAIL_COUPONS.ACT_ID, CUSTOMER_AVAIL_COUPONS.USER_ID,
						CUSTOMER_AVAIL_COUPONS.ACT_DESC, CUSTOMER_AVAIL_COUPONS.AMOUNT,
						CUSTOMER_AVAIL_COUPONS.COUPON_SN).values(param.getActId(), userId, couponName, denomination, getCouponSn())
						.execute();
			}
		}
			this.success(channel, message.getMessageProperties().getDeliveryTag());
		} catch (IOException e) {
			try {
				this.failReturn(channel, message.getMessageProperties().getDeliveryTag());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/* 生成优惠券编号 */
	public static String getCouponSn() {
		System.currentTimeMillis();
		String sn = "C" + System.currentTimeMillis();
		return sn;
	}

	/**
	 * 优惠券弹窗
	 *
	 * @param param
	 * @return popListVo
	 */
	public List<CouponGivePopVo> popWindows(CouponGivePopParam param) {
		/* 添加模糊查询条件 */
		if (StringUtils.isNullOrEmpty(param.getActName())) {
			param.setActName("");
		}
		/* 查询，并筛选出正确的使用限制条件 */
		List<CouponGivePopVo> popVo = db()
				.select(MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.LEAST_CONSUME,
						MRKING_VOUCHER.REMAIN_AMOUNT, MRKING_VOUCHER.USE_CONSUME_RESTRICT)
				.from(MRKING_VOUCHER)
				.where(MRKING_VOUCHER.USE_CONSUME_RESTRICT.eq((byte) 1)
						.and(MRKING_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName()))))
				.or(MRKING_VOUCHER.USE_CONSUME_RESTRICT.eq((byte) 0).and(MRKING_VOUCHER.LEAST_CONSUME.eq(0))
						.and(MRKING_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName()))))
				.fetchInto(CouponGivePopVo.class);

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
		db().update(CUSTOMER_AVAIL_COUPONS).set(CUSTOMER_AVAIL_COUPONS.DEL_FLAG, (byte) 1)
				.where(CUSTOMER_AVAIL_COUPONS.ID.eq(param.getId())).execute();
	}
}
