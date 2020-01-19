package com.vpu.mp.service.shop.market.groupdraw;

import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.GroupDraw.GROUP_DRAW;
import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.service.foundation.util.Util.currentTimeStamp;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.db.shop.tables.records.JoinDrawListRecord;
import com.vpu.mp.db.shop.tables.records.JoinGroupListRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.coupon.CouponMpService;
import com.vpu.mp.service.shop.order.action.ReturnService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.atomic.AtomicOperation;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawUserService extends ShopBaseService {
	@Autowired
	private GroupDrawService drawService;

	/** 拼团中 **/
	private static final byte GROUP_ONGOING = 0;
	/** 已成团 **/
	private static final byte GROUPED = 1;
	/** 未成团 **/
	private static final byte NOT_GROUPED = 2;
	/** 已开奖 **/
	private static final byte DREW = 1;
	/** 开奖失败 **/
	private static final byte DRAW_FAIL = 2;
	/** 已中奖 **/
	private static final byte WIN_DRAW = 1;
	@Autowired
	private CouponMpService couponMpService;
	@Autowired
	private ReturnService returnService;
	@Autowired
	private AtomicOperation optOperation;
	@Autowired
	private OrderGoodsService orderGoodsService;

	private static final byte ZERO = 0;
	private static final byte ONE = 0;

	/**
	 * 获取拼团抽奖活动
	 */
	private GroupDrawRecord getGroupDraw(Integer groupDrawId) {
		return db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchOneInto(GROUP_DRAW);
	}

	/**
	 * 处理拼团抽奖
	 */
	public void dealOpenGroupDraw() {
		logger().info("处理拼团抽奖");
		List<GroupDrawRecord> goodsGroupDrawList = getOpenGroupDrawList();
		goodsGroupDrawList.forEach(goodsGroupDraw -> {
			Integer goodsGroupDrawId = goodsGroupDraw.getId();
			Short minJoinNum = goodsGroupDraw.getMinJoinNum();
			// 抽奖记录置为已开奖
			updateGroupDrawStatus(goodsGroupDraw.getId());
			// 活动商品
			List<Integer> goodsIds = Util.stringToList(goodsGroupDraw.getGoodsId());
			goodsIds.forEach(goodsId -> {
				int joinUserNum = getJoinUserNumByGoodsId(goodsGroupDrawId, goodsId, null);
				if (joinUserNum > 0) {
					// 已成团该商品参与用户数
					joinUserNum = getJoinUserNumByGoodsId(goodsGroupDrawId, goodsId, GROUPED);
					if (minJoinNum <= joinUserNum) {
						// 人数达到开奖条件 开奖
						startDraw(goodsGroupDrawId, goodsId);
					} else {
						// 不满足开奖条件，更新开奖状态
						updateGroupDrawStatus(goodsGroupDrawId, goodsId);
					}
					// 拼团失败更新开奖状态
					updateOnGoingGroupDrawStatus(goodsGroupDrawId, goodsId);
					// 获得参与抽奖用户
					List<JoinGroupListRecord> groupUserList = getGroupUserListByGoodsId(goodsGroupDrawId, goodsId,
							null);
					// 订单号
					List<String> orderSns = groupUserList.stream().map(JoinGroupListRecord::getOrderSn)
							.collect(Collectors.toList());
					// 更新订单状态为 "待发货"
					updateOrderWaitDelivery(orderSns);
					// TODO 同步订单状态到 CRM
					List<Integer> couponUserIds = new LinkedList<>();
					String couponIds = goodsGroupDraw.getRewardCouponId();
					groupUserList.forEach(groupUser -> {
						if (WIN_DRAW == groupUser.getIsWinDraw()) {
							// 更新库存
							updateProductNumber(groupUser.getOrderSn());
						} else {
							refundMoney(groupUser.getOrderSn());
							if (!couponIds.isEmpty()) {
								couponUserIds.add(groupUser.getUserId());
							}
							sendDrawResultMessage(groupUser.getUserId(), groupUser.getGroupDrawId(),
									groupUser.getGroupId(), ZERO);
						}
					});
					// 批量送券
					giveVoucher(couponIds, couponUserIds);
				}
			});
		});
	}

	private void refundMoney(String orderSn) {
		logger().info("订单号" + orderSn + "未中奖退款");
		RefundParam param = new RefundParam();
		param.setReturnType(OrderConstant.RT_ONLY_MONEY);
		param.setAction((byte) OrderServiceCode.RETURN.ordinal());// 1是退款
		param.setOrderSn(orderSn);
		OrderInfoRecord orderInfo = db().selectFrom(ORDER_INFO).where(ORDER_INFO.ORDER_SN.eq(orderSn)).fetchAny();
		if (null == orderInfo) {
			return;
		}
		param.setOrderId(orderInfo.getOrderId());
		param.setIsMp(OrderConstant.IS_MP_AUTO);
		param.setReturnMoney(orderInfo.getMoneyPaid().add(orderInfo.getScoreDiscount()).add(orderInfo.getUseAccount())
				.add(orderInfo.getMemberCardBalance()));
		ExecuteResult execute = returnService.execute(param);
		if (null == execute || !execute.isSuccess()) {
			logger().info("订单号" + orderSn + "退款失败");
		}
	}

	/**
	 * 更新库存
	 */
	private void updateProductNumber(String orderSn) {
		logger().info("订单号" + orderSn + "更新库存");
		OrderInfoRecord orderInfo = db().selectFrom(ORDER_INFO).where(ORDER_INFO.ORDER_SN.eq(orderSn)).fetchAny();
		if (null == orderInfo) {
			return;
		}
		List<OrderGoodsBo> goodsBo = orderGoodsService.getByOrderId(orderInfo.getOrderId()).into(OrderGoodsBo.class);
		try {
			optOperation.updateStockandSales(orderInfo, goodsBo, false);
		} catch (MpException e) {
			logger().info("订单号" + orderSn + "更新库存失败");
			logger().info(e.getMessage(), e);
		}
	}

	/**
	 * 获取订单信息
	 */
	private OrderInfoRecord getOrderInfo(String orderSn) {
		return db().selectFrom(ORDER_INFO).where(ORDER_INFO.ORDER_SN.eq(orderSn)).fetchOneInto(ORDER_INFO);
	}

	/**
	 * 获取单品
	 */
	private GoodsSpecProductRecord getGoodsSpecProduct(Integer productId) {
		return db().selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId))
				.fetchOneInto(GOODS_SPEC_PRODUCT);
	}

	/**
	 * 获取订单中的商品
	 */
	private List<OrderGoodsRecord> getOrderGoods(String orderSn) {
		return db().selectFrom(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetch()
				.into(OrderGoodsRecord.class);
	}

	/**
	 * 更新订单状态为待发货
	 */
	private void updateOrderWaitDelivery(List<String> orderSns) {
		db().update(ORDER_INFO).set(ORDER_INFO.ORDER_STATUS, ORDER_WAIT_DELIVERY)
				.where(ORDER_INFO.ORDER_SN.in(orderSns)).execute();
	}

	/**
	 * 拼团失败更新状态
	 */
	private void updateOnGoingGroupDrawStatus(Integer goodsGroupDrawId, Integer goodsId) {
		db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.STATUS, NOT_GROUPED)
				.set(JOIN_GROUP_LIST.END_TIME, currentTimeStamp())
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(goodsGroupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId))
						.and(JOIN_GROUP_LIST.STATUS.eq(GROUP_ONGOING)))
				.execute();
	}

	/**
	 * 不满足开奖条件，更新状态
	 */
	private void updateGroupDrawStatus(Integer goodsGroupDrawId, Integer goodsId) {
		logger().info("不满足开奖条件，更新开奖状态");
		db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.DRAW_STATUS, DRAW_FAIL)
				.set(JOIN_GROUP_LIST.DRAW_TIME, currentTimeStamp())
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(goodsGroupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)))
				.execute();
	}

	/**
	 * 获取某个商品的参与用户数
	 */
	private Integer getJoinUserNumByGoodsId(Integer groupDrawId, Integer goodsId, Byte status) {
		SelectConditionStep<Record1<Integer>> select = db().selectCount().from(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
		if (null != status) {
			select.and(JOIN_GROUP_LIST.STATUS.eq(status));
		} else {
			select.and(JOIN_GROUP_LIST.STATUS.ge(ZERO));
		}
		return (Integer) select.fetchOne().get(0);
	}

	/**
	 * 更新开奖状态
	 */
	private void updateGroupDrawStatus(Integer id) {
		db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.IS_WIN_DRAW, (byte) 1).where(JOIN_GROUP_LIST.ID.eq(id));
	}

	/**
	 * 获取待开奖活动
	 */
	private List<GroupDrawRecord> getOpenGroupDrawList() {
		return db().selectFrom(GROUP_DRAW)
				.where(GROUP_DRAW.END_TIME.le(currentTimeStamp()).and(GROUP_DRAW.IS_DRAW.eq(ZERO)))
				.fetchInto(GroupDrawRecord.class);
	}

	/**
	 * 开奖
	 */
	private void startDraw(Integer groupDrawId, Integer goodsId) {
		logger().info("开奖");
		// 该商品的已成团
		List<Integer> groupIds = getGroupListByGoodsId(groupDrawId, goodsId, GROUPED).stream()
				.map(JoinGroupListRecord::getGroupId).collect(Collectors.toList());
		// 抽奖记录
		List<Integer> drawIds = getUserByGroupIds(groupDrawId, goodsId, groupIds);
		// 开奖
		int winIndex = randomItemFrom(drawIds);
		Integer winDrawId = drawIds.get(winIndex);
		// 状态更新
		updateDraw(winDrawId);
		JoinDrawListRecord winDrawRecord = getDrawRecordById(winDrawId);
		Integer winDrawGroupId = winDrawRecord.getGroupId();
		Integer winDrawUserId = winDrawRecord.getUserId();
		updateDrawStatus(groupDrawId, winDrawGroupId, winDrawUserId);
		updateGroupInfoByGoodsId(groupDrawId, goodsId);
		// 发送模板消息
		logger().info("拼团成功的用户：" + winDrawUserId + "发送中奖信息");
		sendDrawResultMessage(winDrawUserId, groupDrawId, winDrawGroupId, ONE);

	}

	/**
	 * 发送中奖结果
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param groupId
	 * @param isWinDraw
	 */
	public void sendDrawResultMessage(Integer userId, Integer groupDrawId, Integer groupId, Byte isWinDraw) {
		logger().info("发送中奖结果" + isWinDraw);
		GroupDrawRecord groupDrawInfo = getGroupDraw(groupDrawId);
		JoinGroupListRecord userJoinGroup = drawService.getUserJoinGroupInfo(userId, groupDrawId, groupId, true);
		Integer goodsId = userJoinGroup.getGoodsId();
		GoodsRecord good = drawService.getGood(goodsId);
		String goodsName = good.getGoodsName();
		String page = "pages/pinlotteryinfo/pinlotteryinfo?group_id=" + userJoinGroup.getGroupId() + "&group_draw_id="
				+ userJoinGroup.getGroupDrawId() + "&goods_id=" + goodsId;
		goodsName = goodsName.length() > 20 ? goodsName.substring(0, 19) + "等" : goodsName;
		String msg = Objects.equals(isWinDraw, ONE) ? "恭喜您中奖了" : "很遗憾您未中奖";
		String marketName = groupDrawInfo.getName();
		String[][] data = new String[][] { { marketName }, { Util.getdate("yyyy-MM-dd HH:mm:ss") }, { msg } };
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(userId);
		RabbitMessageParam param = RabbitMessageParam.builder()
				.maTemplateData(
						MaTemplateData.builder().config(SubcribeTemplateCategory.DRAW_RESULT).data(data).build())
				.page(page).shopId(getShopId()).userIdList(arrayList)
				.type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
		saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
				TaskJobEnum.SEND_MESSAGE.getExecutionType());

	}

	/**
	 * 更新团开奖状态
	 */
	private void updateGroupInfoByGoodsId(Integer groupDrawId, Integer goodsId) {
		db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.DRAW_STATUS, DREW)
				.set(JOIN_GROUP_LIST.DRAW_TIME, DateUtil.getSqlTimestamp())
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
	}

	/**
	 * 更新团中奖状态
	 */
	private void updateDrawStatus(Integer groupDrawId, Integer winDrawGroupId, Integer winDrawUserId) {
		db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.IS_WIN_DRAW, WIN_DRAW)
				.set(JOIN_GROUP_LIST.DRAW_STATUS, WIN_DRAW)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(
						JOIN_GROUP_LIST.GROUP_ID.eq(winDrawGroupId).and(JOIN_GROUP_LIST.USER_ID.eq(winDrawUserId))))
				.execute();
	}

	/**
	 * 获取抽奖记录
	 */
	private JoinDrawListRecord getDrawRecordById(Integer winDrawId) {
		return db().selectFrom(JOIN_DRAW_LIST).where(JOIN_DRAW_LIST.ID.eq(winDrawId))
				.fetchOneInto(JoinDrawListRecord.class);
	}

	/**
	 * 更新中奖状态
	 */
	private void updateDraw(Integer winDrawId) {
		db().update(JOIN_DRAW_LIST).set(JOIN_DRAW_LIST.IS_WIN_DRAW, WIN_DRAW).where(JOIN_DRAW_LIST.ID.eq(winDrawId))
				.execute();
	}

	/**
	 * 获取list中的随机项
	 */
	private int randomItemFrom(List<Integer> items) {
		int size = items.size();
		return new Random().nextInt(size);
	}

	/**
	 * 获取团内用户的id
	 */
	private List<Integer> getUserByGroupIds(Integer groupDrawId, Integer goodsId, List<Integer> groupIds) {
		return db().selectFrom(JOIN_DRAW_LIST)
				.where(JOIN_DRAW_LIST.GROUP_DRAW_ID.eq(groupDrawId)
						.and(JOIN_DRAW_LIST.GOODS_ID.eq(goodsId).and(JOIN_DRAW_LIST.GROUP_ID.in(groupIds))))
				.fetch().into(JoinDrawListRecord.class).stream().map(JoinDrawListRecord::getId)
				.collect(Collectors.toList());
	}

	/**
	 * 获取某个商品的参团记录
	 */
	private List<JoinGroupListRecord> getGroupListByGoodsId(Integer groupDrawId, Integer goodsId, Byte groupStatus) {
		SelectConditionStep<JoinGroupListRecord> select = db().selectFrom(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
		if (null != groupStatus) {
			select.and(JOIN_GROUP_LIST.STATUS.eq(groupStatus));
		} else {
			select.and(JOIN_GROUP_LIST.STATUS.ge(GROUP_ONGOING));
		}
		return select.fetchInto(JoinGroupListRecord.class);
	}

	/**
	 * 通过商品获得团列表
	 * 
	 * @param groupDrawId
	 * @param goodsId
	 * @param isWinDraw
	 * @return
	 */
	public List<JoinGroupListRecord> getGroupUserListByGoodsId(Integer groupDrawId, Integer goodsId, Byte isWinDraw) {
		SelectConditionStep<JoinGroupListRecord> select = db().selectFrom(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId)));
		if (null != isWinDraw) {
			select.and(JOIN_GROUP_LIST.IS_WIN_DRAW.eq(isWinDraw));
		}
		return select.fetchInto(JoinGroupListRecord.class);
	}

	/**
	 * 未中奖送券
	 */
	private void giveVoucher(String coupOnIds, List<Integer> userIds) {
		logger().info("未中奖送券");
		String[] split = coupOnIds.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : split) {
			Byte couponGetStatus = couponMpService.couponGetStatus(new mpGetCouponParam(Integer.valueOf(string), null));
			if (Objects.equals(couponGetStatus, ZERO)) {
				list.add(string);
			} else {
				logger().info("优惠券" + string + "状态：" + couponGetStatus);
			}
		}
		String[] array = list.toArray(new String[0]);
		CouponGiveQueueParam newParam = new CouponGiveQueueParam(userIds, 0, array, BaseConstant.ACCESS_MODE_ISSUE,
				BaseConstant.GET_SOURCE_ACT);
		saas.taskJobMainService.dispatchImmediately(newParam, CouponGiveQueueParam.class.getName(), getShopId(),
				TaskJobsConstant.TaskJobEnum.GIVE_COUPON.getExecutionType());
	}
}
