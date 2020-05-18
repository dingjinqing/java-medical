package com.vpu.mp.service.shop.order.info;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.member.order.UserCenterNumBean;
import com.vpu.mp.service.pojo.shop.member.order.UserOrderBean;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderQueryVo;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportQueryParam;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderBo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderInfoMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Condition;
import org.jooq.DatePart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GroupBuyList.GROUP_BUY_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.PartOrderGoodsShip.PART_ORDER_GOODS_SHIP;
import static com.vpu.mp.db.shop.tables.ReturnOrder.RETURN_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserTag.USER_TAG;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_CLOSED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_PIN_SUCCESSS;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_REFUNDING;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_REFUND_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_RETURNING;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_RETURN_FINISHED;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_DEFAULT_STATUS;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_STATUS_AUDITING;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.REFUND_STATUS_AUDIT_PASS;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.RT_GOODS;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.RT_ONLY_MONEY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.SHOP_HELPER_OVERDUE_DELIVERY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.SHOP_HELPER_REMIND_DELIVERY;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

/**
 * Table:order_info
 *
 * @author 王帅
 *
 */
@Primary
@Service
public class OrderInfoService extends ShopBaseService {

	public final OrderInfo TABLE = ORDER_INFO;

	/**
	 * 支付种类（细分）PAY_SUBDIVISION
	 */
	/** 积分抵扣金额 */
	public final String PS_SCORE_DISCOUNT = TABLE.SCORE_DISCOUNT.getName();
	/** 用户消费余额 */
	public final String PS_USE_ACCOUNT = TABLE.USE_ACCOUNT.getName();
	/** 会员卡消费金额 */
	public final String PS_MEMBER_CARD_BALANCE = TABLE.MEMBER_CARD_BALANCE.getName();
	/** TODO 数据库没字段子单金额 */
	public final String PS_MONEY_PAID = TABLE.MONEY_PAID.getName();
	/** 补款金额 */
	public final String PS_BK_ORDER_MONEY = TABLE.BK_ORDER_MONEY.getName();

	public final String[] PAY_SUBDIVISION = { PS_BK_ORDER_MONEY, PS_MEMBER_CARD_BALANCE, PS_USE_ACCOUNT,
			PS_SCORE_DISCOUNT, PS_MONEY_PAID };

	/** like通配符 */
	public final static char LIKE = '%';

	/**
	 * @param orderId 订单id
	 * @param clz     class
	 * @return oneOrder
	 */
	public <T> T getByOrderId(Integer orderId, Class<T> clz) {
		T order = db().select(TABLE.asterisk()).from(TABLE).where(TABLE.ORDER_ID.eq(orderId)).fetchOneInto(clz);
		return order;
	}

	public OrderInfoRecord getRecord(Integer orderId) {
		return db().selectFrom(TABLE).where(TABLE.ORDER_ID.eq(orderId)).fetchOne();
	}

	/**
	 * @param <T>     <? extends OrderListVo>
	 * @param orderSn 订单号
	 * @param clz
	 * @return oneOrder
	 */
	public <T> List<T> getByOrderSn(String orderSn, Class<T> clz) {
		return db().select(TABLE.asterisk(), USER.USERNAME).from(TABLE).leftJoin(USER)
				.on(TABLE.USER_ID.eq(USER.USER_ID))
				.where(TABLE.ORDER_SN.eq(orderSn).or(TABLE.MAIN_ORDER_SN.eq(orderSn))).orderBy(TABLE.ORDER_ID.asc())
				.fetchInto(clz);

	}

	public OrderInfoRecord getOrderByOrderSn(String orderSn) {
		return db().fetchAny(TABLE, TABLE.ORDER_SN.eq(orderSn));
	}

	/**
	 * 订单综合查询:通过条件获得订单号
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<String> getOrderSns(OrderPageListQueryParam param, OrderQueryVo result) {
	    // 数量查询
        calculateOrderCount(param, result);
		SelectJoinStep<Record1<String>> mainOrder = db().select(TABLE.ORDER_SN).from(TABLE);
		// 存在子单但是显示不易子单为主所以查询需过滤子单
		mainOrder.where(TABLE.ORDER_SN.eq(TABLE.MAIN_ORDER_SN).or(TABLE.MAIN_ORDER_SN.eq("")));
		buildOptions(mainOrder, param);
		mainOrder.orderBy(TABLE.ORDER_ID.desc());
		// 得到订单号
		return getPageResult(mainOrder, param.getCurrentPage(), param.getPageRows(), String.class);
	}

	/**
	 * 计算部分订单数量
	 * @param result
	 */
	private void calculateOrderCount(OrderPageListQueryParam param, OrderQueryVo result) {
		Map<Byte, Integer> count = new HashMap<>(OrderConstant.SEARCH_RETURN_COMPLETED);
		for (int i = 1; i <= OrderConstant.SEARCH_RETURN_COMPLETED; i++) {
			count.put(Integer.valueOf(i).byteValue(), calculateOrderCount(param, Integer.valueOf(i).byteValue()));
		}
		result.setCount(count);
	}

	private Integer calculateOrderCount(OrderPageListQueryParam param, byte type) {
		SelectJoinStep<Record1<String>> select = db().select(TABLE.ORDER_SN).from(TABLE);
		// 存在子单但是显示不易子单为主所以查询需过滤子单
		select.where(TABLE.ORDER_SN.eq(TABLE.MAIN_ORDER_SN).or(TABLE.MAIN_ORDER_SN.eq("")));
		buildOptions(select, param);
		switch (type) {
		case OrderConstant.SEARCH_WAIT_DELIVERY:
			// 待发货
			return db().fetchCount(select.where(TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY).and(
					TABLE.DELIVER_TYPE.in(OrderConstant.DELIVER_TYPE_COURIER, OrderConstant.CITY_EXPRESS_SERVICE))));
		case OrderConstant.SEARCH_WAIT_TAKEDELIVER:
			// 待核销
			return db().fetchCount(select.where(TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY)
					.and(TABLE.DELIVER_TYPE.in(OrderConstant.DELIVER_TYPE_SELF))));
		case OrderConstant.SEARCH_RETURNING:
			// 退款中
			return db().fetchCount(select.where(TABLE.REFUND_STATUS.in(OrderConstant.REFUND_STATUS_AUDITING,
					OrderConstant.REFUND_STATUS_AUDIT_PASS, OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING)));
		case OrderConstant.SEARCH_RETURN_COMPLETED:
			// 退完成
			return db().fetchCount(select.where(TABLE.REFUND_STATUS.in(OrderConstant.REFUND_STATUS_FINISH)));
        default:
            return 0;
		}
	}

    /**
     * 构造综合查询条件
     *
     * @param select
     * @param param
     * @return
     */
    public SelectWhereStep<?> buildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
        return buildOptions(select,param,false);
    }

	/**
	 * 构造综合查询条件
	 * 
	 * @param select
	 * @param param
     * @param joined select 已经连接过order_goods和user
	 * @return
	 */
	public SelectWhereStep<?> buildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param,boolean joined) {
		// 输入商品名称需要join order_goods表
		if (!StringUtils.isBlank(param.goodsName) || !StringUtils.isBlank(param.productSn)) {
		    if(!joined){
                select.innerJoin(ORDER_GOODS).on(ORDER_INFO.ORDER_ID.eq(ORDER_GOODS.ORDER_ID));
            }
			if (!StringUtils.isBlank(param.goodsName)) {
				select.where(ORDER_GOODS.GOODS_NAME.like(likeValue(param.goodsName)));
			}
			if (!StringUtils.isBlank(param.productSn)) {
				select.where(ORDER_GOODS.PRODUCT_SN.like(likeValue(param.productSn)));
			}
		}
		if (!StringUtils.isBlank(param.orderSn)) {
			select.where(ORDER_INFO.ORDER_SN.contains(param.orderSn));
		}
		if (param.orderStatus != null && param.orderStatus.length != 0) {
            List<Byte> status = Lists.newArrayList(param.orderStatus);
            Condition condition = DSL.noCondition();
            if(status.contains(ORDER_RETURNING) || status.contains(ORDER_REFUNDING)) {
                select.leftJoin(RETURN_ORDER).on(TABLE.ORDER_ID.eq(RETURN_ORDER.ORDER_ID));
                if(status.contains(ORDER_REFUNDING)) {
                    condition = condition.or(RETURN_ORDER.RETURN_TYPE.eq(RT_ONLY_MONEY).and(RETURN_ORDER.REFUND_STATUS.eq(REFUND_STATUS_APPLY_REFUND_OR_SHIPPING)));
                    status.remove(Byte.valueOf(ORDER_REFUNDING));
                }
                if(status.contains(ORDER_RETURNING)) {
                    condition = condition.or(RETURN_ORDER.RETURN_TYPE.eq(RT_GOODS).and(RETURN_ORDER.REFUND_STATUS.in(REFUND_DEFAULT_STATUS, REFUND_STATUS_AUDITING ,REFUND_STATUS_AUDIT_PASS , REFUND_STATUS_APPLY_REFUND_OR_SHIPPING)));
                    status.remove(Byte.valueOf(ORDER_RETURNING));
                }
            }
            if(CollectionUtils.isNotEmpty(status)) {
                condition = condition.or(TABLE.ORDER_STATUS.in(status));
            }
            select.where().and(condition);
		}
		if (param.goodsType != null && param.goodsType.length != 0) {
			select.where(ORDER_INFO.GOODS_TYPE.likeRegex(getGoodsTypeToSearch(param.getGoodsType())));
		}
		if (param.deliverType != null) {
			select.where(ORDER_INFO.DELIVER_TYPE.eq(param.deliverType));
		}
		// 昵称、会员标签tag需要连表查询
		if (!StringUtils.isBlank(param.userName) || (param.tagIds != null && param.tagIds.length != 0)) {
            if(!joined){
                select.innerJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
            }
			if (!StringUtils.isBlank(param.userName)) {
				select.where(USER.USERNAME.like(likeValue(param.userName)));
			}
			if (param.tagIds != null && param.tagIds.length != 0) {
				select.where(USER_TAG.TAG_ID.in(param.tagIds));
			}
		}
		if(param.getUserId() != null){
            select.where(ORDER_INFO.USER_ID.eq(param.getUserId()));
        }
		if (!StringUtils.isBlank(param.source)) {
			select.where(ORDER_INFO.SOURCE.eq(param.source));
		}
		if (param.storeId != null) {
			select.where(ORDER_INFO.STORE_ID.eq(param.storeId));
		}
		if (!StringUtils.isBlank(param.verifyCode)) {
			select.where(ORDER_INFO.VERIFY_CODE.eq(param.verifyCode));
		}
		if (!StringUtils.isBlank(param.consignee)) {
			select.where(ORDER_INFO.CONSIGNEE.contains(param.consignee));
		}
		if (!StringUtils.isBlank(param.getMobile())) {
			select.where(ORDER_INFO.MOBILE.contains(param.getMobile()));
		}
		if (param.countryCode != null) {
			select.where(ORDER_INFO.COUNTRY_CODE.eq(param.countryCode));
		}
		if (param.provinceCode != null) {
			select.where(ORDER_INFO.PROVINCE_CODE.eq(param.provinceCode));
		}
		if (param.cityCode != null) {
			select.where(ORDER_INFO.CITY_CODE.eq(param.cityCode));
		}
		if (param.districtCode != null) {
			select.where(ORDER_INFO.DISTRICT_CODE.eq(param.districtCode));
		}
		if (param.createTimeStart != null) {
			select.where(ORDER_INFO.CREATE_TIME.ge(param.createTimeStart));
		}
		if (param.createTimeEnd != null) {
			select.where(ORDER_INFO.CREATE_TIME.le(param.createTimeEnd));
		}
		if (param.finishedTimeStart != null) {
			select.where(ORDER_INFO.FINISHED_TIME.ge(param.finishedTimeStart));
		}
		if (param.finishedTimeEnd != null) {
			select.where(ORDER_INFO.FINISHED_TIME.le(param.finishedTimeEnd));
		}
		if (param.getIsStar() != null) {
			select.where(TABLE.STAR_FLAG.eq(param.getIsStar()));
		}
		if(!StringUtils.isBlank(param.getShippingNo())) {
            select.leftJoin(PART_ORDER_GOODS_SHIP).on(PART_ORDER_GOODS_SHIP.ORDER_SN.eq(TABLE.ORDER_SN)).where(PART_ORDER_GOODS_SHIP.SHIPPING_NO.like(likeValue(param.getShippingNo())));
        }
		// 拼团退款失败订单
		if (param.pinStatus != null && param.pinStatus.length != 0) {
			select.innerJoin(GROUP_BUY_LIST).on(ORDER_INFO.ORDER_SN.eq(GROUP_BUY_LIST.ORDER_SN));
			select.where(GROUP_BUY_LIST.STATUS.in(param.pinStatus));
		}
		// 支付方式
		if (param.payWay != null) {
			switch (param.payWay) {
			case OrderConstant.SEARCH_PAY_WAY_USE_ACCOUNT:
				select.where(TABLE.USE_ACCOUNT.greaterThan(BigDecimal.ZERO));
				break;
			case OrderConstant.SEARCH_PAY_WAY_SCORE_DISCOUNT:
				select.where(TABLE.SCORE_DISCOUNT.greaterThan(BigDecimal.ZERO));
				break;
			case OrderConstant.SEARCH_PAY_WAY_SCORE_EXCHANGE:
				select.where(ORDER_INFO.GOODS_TYPE.likeRegex(getGoodsTypeToSearch(new Byte[] {BaseConstant.ACTIVITY_TYPE_INTEGRAL})));
				break;
			case OrderConstant.SEARCH_PAY_WAY_COD:
				select.where(TABLE.PAY_CODE.eq(OrderConstant.PAY_CODE_COD));
				break;
			case OrderConstant.SEARCH_PAY_WAY_EVENT_PRIZE:
				select.where(ORDER_INFO.GOODS_TYPE.likeRegex(getGoodsTypeToSearch(new Byte[] {BaseConstant.ACTIVITY_TYPE_MY_PRIZE})));
				break;
			case OrderConstant.SEARCH_PAY_WAY_WXPAY:
				select.where(TABLE.PAY_CODE.eq(OrderConstant.PAY_CODE_WX_PAY));
				break;
			default:
				break;
			}
		}
        if (param.getOrderIds() != null && param.getOrderIds().length != 0) {
            select.where(ORDER_INFO.ORDER_ID.in(param.getOrderIds()));
        }
        //店铺助手操作
        if(param.getShopHelperAction() != null) {
            if(param.getShopHelperAction().equals(SHOP_HELPER_OVERDUE_DELIVERY)) {
                select.where(TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY)
                    .and(TABLE.CREATE_TIME.add(param.getShopHelperActionDays()).lessThan(Timestamp.valueOf(LocalDateTime.now()))));
            }else if(param.getShopHelperAction().equals(SHOP_HELPER_REMIND_DELIVERY)) {
                select.where(TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY).and(TABLE.ORDER_REMIND.greaterThan(BYTE_ZERO)));
            }
        }
		// 构造营销活动查询条件
		activeBuildOptions(select, param);
		return select;
	}

	/**
	 * 订单goodsType查询构造
	 * 
	 * @param goodsType
	 */
	public static String getGoodsTypeToSearch(Byte[] goodsType) {
		StringBuilder sbr = new StringBuilder();
		for (Byte one : goodsType) {
			// Prefix
			sbr.append("\\[");
			// 查找条件
			sbr.append(one);
			// suffix
			sbr.append("\\]");
			// 与条件
			sbr.append("|");
		}
		return sbr.deleteCharAt(sbr.length() - 1).toString();
	}
    /**
     * 订单goodsType insert构造
     * @param orderType 订单类型
     */
    public static String getGoodsTypeToInsert(List<Byte> orderType) {
        if(CollectionUtils.isEmpty(orderType)){
            orderType.add(BaseConstant.ACTIVITY_TYPE_GENERAL);
        }
        //distinct
        orderType = orderType.stream().distinct().collect(Collectors.toList());
        if(orderType.size() > 1) {
            orderType.remove(BaseConstant.ACTIVITY_TYPE_GENERAL);
        }
        //sort A->Z
        orderType.sort(Byte::compareTo);
        StringBuilder sbr = new StringBuilder();
        for (Byte one : orderType) {
            //Prefix
            sbr.append("[");
            //查找条件
            sbr.append(one);
            //suffix
            sbr.append("]");
        }
        return sbr.toString();
    }

    /**
     * 转化订单类型
     * @param orderType
     * @return
     */
    public static String[] orderTypeToArray(String orderType) {
        return orderType.substring(1, orderType.length() - 1 ).split("\\]\\[");
    }

    /**
     * 转化订单类型
     * @param orderType
     * @return
     */
    public static Byte[] orderTypeToByte(String orderType) {
        String[] split = orderTypeToArray(orderType);
        Byte[] bytes = new Byte[split.length];
        int i = 0;
        for (String str : split) {
            bytes[i] = Byte.valueOf(str);
        }
        return bytes;
    }

	/**
	 * 构造营销订查询条件
	 *
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<?> activeBuildOptions(SelectJoinStep<?> select, OrderPageListQueryParam param) {
		if (param.activityId != null) {
		    if (param.goodsType != null && param.goodsType.length != 0 && Arrays.asList(param.getGoodsType()).contains(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)) {
                //首单特惠的活动ID记录在订单商品行内
                select.where(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)).and(ORDER_GOODS.ACTIVITY_ID.eq(param.getActivityId()));
            } else if (param.goodsType != null && param.goodsType.length != 0 && Arrays.asList(param.getGoodsType()).contains(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)) {
                //限时降价的活动ID记录在订单商品行内
                select.where(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)).and(ORDER_GOODS.ACTIVITY_ID.eq(param.getActivityId()));
            } else {
                select.where(ORDER_INFO.ACTIVITY_ID.eq(param.activityId));
            }
		}
		if(param.getRoomId()!=null) {
			select.where(ORDER_INFO.ROOM_ID.eq(param.getRoomId()));
		}
		return select;
	}

	/**
	 * TODO 去* 通过传入的field获取对应记录
	 *
	 * @param <T>
	 * @param where
	 * @param clz
	 * @return
	 */
	public <T> List<T> getOrdersByCondition(Condition where, Class<T> clz) {
		return db().select(TABLE.asterisk(), USER.USERNAME, USER.MOBILE.as("userMobile")).from(TABLE).innerJoin(USER)
				.on(ORDER_INFO.USER_ID.eq(USER.USER_ID)).where(where).orderBy(TABLE.ORDER_ID.asc()).fetchInto(clz);
	}

	/**
	 * 按照主订单分组，正常订单的key为orderSn
	 * 
	 * @param orderSn
	 * @return
	 */
	public Map<String, List<OrderListInfoVo>> getOrders(List<String> orderSn) {
		List<OrderListInfoVo> orders = db().select(ORDER_INFO.asterisk()).from(ORDER_INFO)
				.where(ORDER_INFO.MAIN_ORDER_SN.in(orderSn).or(TABLE.ORDER_SN.in(orderSn)))
				.orderBy(ORDER_INFO.ORDER_ID.desc()).fetchInto(OrderListInfoVo.class);
		orders.forEach(order -> {
			if (StringUtils.isBlank(order.getMainOrderSn())) {
				order.setLogicMainOrderSn(order.getOrderSn());
			} else {
				order.setLogicMainOrderSn(order.getMainOrderSn());
			}
		});
		Map<String, List<OrderListInfoVo>> result = new HashMap<String, List<OrderListInfoVo>>();
		for (OrderListInfoVo order : orders) {
			if (result.get(order.getLogicMainOrderSn()) != null) {
				result.get(order.getLogicMainOrderSn()).add(order);
			} else {
				ArrayList<OrderListInfoVo> orderList = new ArrayList<OrderListInfoVo>();
				orderList.add(order);
				result.put(order.getLogicMainOrderSn(), orderList);
			}
		}
		return result;
	}

	/**
	 * 过滤子订单数量>0时,过滤主订单下已被拆除的商品行（通过减小数量为0则不展示）
	 * 
	 * @param order 主订单（已经在主订单下添加了子订单及其商品）, goods主订单商品行
	 * @param goods 商品
	 * @return
	 */
	public OrderListInfoVo filterMainOrderGoods(OrderListInfoVo order, List<OrderGoodsVo> goods) {
		List<? extends OrderListInfoVo> cOrders = order.getChildOrders();
		// 构造Map<Integer(子订单规格号), Integer(数量)>
		Map<Integer, Integer> childGoodsCount = new HashMap<>(goods.size());
		for (OrderListInfoVo oneOrder : cOrders) {
			for (OrderGoodsVo tempGoods : oneOrder.getGoods()) {
				Integer tempCount = childGoodsCount.get(tempGoods.getMainRecId());
				if (tempCount == null) {
					// 第一次goodsNumber
					childGoodsCount.put(tempGoods.getMainRecId(), tempGoods.getGoodsNumber());
				} else {
					// 后续加goodsNumber
					childGoodsCount.put(tempGoods.getMainRecId(), tempCount + tempGoods.getGoodsNumber());
				}
			}
		}
		// 主订单减去子订单相应商品(数量为0不展示)
		Iterator<OrderGoodsVo> iter = goods.iterator();
		while (iter.hasNext()) {
			OrderGoodsVo orderOoods = iter.next();
			Integer count = childGoodsCount.get(orderOoods.getRecId());
			if (count != null) {
				int finalCount = orderOoods.getGoodsNumber() - count;
				if (finalCount > 0) {
					orderOoods.setGoodsNumber(finalCount);
				} else {
					iter.remove();
				}
			}
		}
		order.setGoods(goods);
		return order;
	}

	/**
	 * 得到实际支付金额（判读是否包含运费）
	 * 
	 * @param order
	 * @param isIncludeShipingFee 是否包含运费
	 * @return
	 */
	public BigDecimal getOrderFinalAmount(OrderListInfoVo order, boolean isIncludeShipingFee) {
		if(order.getBkOrderMoney()==null){
			return order.getMoneyPaid().add(order.getScoreDiscount())
					.add(order.getMemberCardBalance()).add(order.getUseAccount())
					.subtract(isIncludeShipingFee?BigDecimal.ZERO:order.getShippingFee());
		}
		return order.getBkOrderMoney().add(order.getMoneyPaid()).add(order.getScoreDiscount())
				.add(order.getMemberCardBalance()).add(order.getUseAccount())
				// TODO 少这个字段
				// .add(order.getSubGoodsPrice())
				.subtract(isIncludeShipingFee ? BigDecimal.ZERO : order.getShippingFee());
	}

	/**
	 * 当前订单为子订单需要替换支付信息与用户信息(子订单无补款信息,不需复制)
	 * 
	 * @param currentOrder
	 */
	public void replaceOrderInfo(OrderListInfoVo currentOrder) {
		if (isSubOrder(currentOrder)) {
			OrderListInfoVo mainOrder = db().select(TABLE.asterisk()).from(TABLE)
					.where(TABLE.ORDER_SN.eq(currentOrder.getMainOrderSn())).fetchOneInto(OrderListInfoVo.class);
			currentOrder.setMemberCardBalance(mainOrder.getMemberCardBalance());
			currentOrder.setUseAccount(mainOrder.getUseAccount());
			currentOrder.setScoreDiscount(mainOrder.getScoreDiscount());
			currentOrder.setMoneyPaid(mainOrder.getMoneyPaid());
			currentOrder.setUserId(mainOrder.getUserId());
			currentOrder.setCardNo(mainOrder.getCardNo());
			currentOrder.setMemberCardId(mainOrder.getMemberCardId());
		}
	}

	/**
	 * 是否为拆单逻辑下的主订单
	 * 
	 * @param currentOrder
	 * @return
	 */
	public Boolean isMainOrder(OrderListInfoVo currentOrder) {
		if (!StringUtils.isBlank(currentOrder.getMainOrderSn())
				&& currentOrder.getOrderSn().equals(currentOrder.getMainOrderSn())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 是否为拆单逻辑下的子订单
	 * 
	 * @param currentOrder
	 * @return
	 */
	public Boolean isSubOrder(OrderListInfoVo currentOrder) {
		if (!StringUtils.isBlank(currentOrder.getMainOrderSn())
				&& !currentOrder.getOrderSn().equals(currentOrder.getMainOrderSn())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 通过主订单号获取orders
	 * 
	 * @param currentOrder
	 * @param isMain
	 * @return List<OrderListInfoVo>
	 */
	public List<OrderListInfoVo> getChildOrders(OrderListInfoVo currentOrder, Boolean isMain) {
		// 是否为主订单
		if (isMain) {
			return db().select(TABLE.asterisk()).from(TABLE).where(TABLE.MAIN_ORDER_SN.eq(currentOrder.getMainOrderSn())
					.and(TABLE.MAIN_ORDER_SN.notEqual(TABLE.ORDER_SN))).fetchInto(OrderListInfoVo.class);
		}
		return new ArrayList<OrderListInfoVo>(0);
	}

	/**
	 * 将order支付信息构造成map方便计算
	 * 
	 * @param currentOrder
	 * @return Map<String, BigDecimal>
	 */
	public Map<String, BigDecimal> getPayInfoMap(OrderListInfoVo currentOrder) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>(PAY_SUBDIVISION.length);
		map.put(PS_BK_ORDER_MONEY, currentOrder.getBkOrderMoney());
		map.put(PS_MEMBER_CARD_BALANCE, currentOrder.getMemberCardBalance());
		map.put(PS_USE_ACCOUNT, currentOrder.getUseAccount());
		map.put(PS_SCORE_DISCOUNT, currentOrder.getScoreDiscount());
		map.put(PS_MONEY_PAID, currentOrder.getMoneyPaid());
		return map;
	}

	/**
	 *
	 * @param currentOrder
	 * @param amount       当前订单最终支付金额(包含运费)
	 * @param map          退款数据汇总
	 * @return Map<String , BigDecimal>
	 */
	public Map<String, BigDecimal> getCanReturn(OrderListInfoVo currentOrder, BigDecimal amount,
			LinkedHashMap<String, BigDecimal> map) {
		// 将order支付信息构造成map方便计算
		Map<String, BigDecimal> payInfoMap = getPayInfoMap(currentOrder);
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
			// 某key已支付-已退=可退
			BigDecimal currentKeyCanReturn = payInfoMap.get(entry.getKey()).subtract(entry.getValue());
			// 可退金额<=0
			if (BigDecimalUtil.compareTo(currentKeyCanReturn, BigDecimal.ZERO) < 1) {
				entry.setValue(BigDecimal.ZERO);
				continue;
			}
			// TODO 这里逻辑有问题，但不会出现这种情况，稍后修改
			// 设置当前key的可退金额（增加双重校验，实际上实际退款时已经判断，不会出现amount<currentKeyCanReturn）
			entry.setValue(BigDecimalUtil.compareTo(amount, currentKeyCanReturn) > 0 ? currentKeyCanReturn : amount);
			// 设置下次循环的amount
			amount = BigDecimalUtil.compareTo(amount, currentKeyCanReturn) > 0 ? amount.subtract(entry.getValue())
					: BigDecimal.ZERO;
		}
		return map;
	}

	/**
	 * 退*时更新订单信息（完成状态输入时必须order与canReturnGoodsNumber）
	 * 
	 * @param returnOrder
	 * @param order                ==null时不参与returnOrder==REFUND_STATUS_FINISH分支
	 * @param canReturnGoodsNumber 是否存在可退商品数量；==null时不参与returnOrder==REFUND_STATUS_FINISH分支
	 */
	public void updateInReturn(ReturnOrderRecord returnOrder, OrderInfoVo order, Boolean canReturnGoodsNumber) {
		logger().info("退款退货时更新订单信息start");
		UpdateSetMoreStep<OrderInfoRecord> set = db().update(TABLE).set(TABLE.REFUND_STATUS,
				returnOrder.getRefundStatus());
		// 退款退货订单处与 1买家仅退款 2买家提交物流 3仅退运费 4手动退款
		switch (returnOrder.getRefundStatus()) {
		case OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING:
			if (OrderConstant.RT_ONLY_MONEY == returnOrder.getReturnType()) {
				set.set(TABLE.REFUND_TIME, DateUtil.getSqlTimestamp());
			} else {
				set.set(TABLE.RETURN_TIME, DateUtil.getSqlTimestamp());
			}
			break;
		case OrderConstant.REFUND_STATUS_FINISH:
			if (order == null || canReturnGoodsNumber == null) {
				// 未输入默认不处理
				break;
			}
			if (canReturnGoodsNumber) {
				// 完成状态且存在可退数量跳出
				break;
			}
			// 退款退货订单完成时更新orderinfo订单信息
			if (OrderConstant.ORDER_WAIT_DELIVERY == order.getOrderStatus()) {
				set.set(TABLE.ORDER_STATUS, OrderConstant.ORDER_REFUND_FINISHED);
				set.set(TABLE.REFUND_FINISH_TIME, DateUtil.getSqlTimestamp());
			} else {
				set.set(TABLE.ORDER_STATUS, OrderConstant.ORDER_RETURN_FINISHED);
				set.set(TABLE.RETURN_FINISH_TIME, DateUtil.getSqlTimestamp());
			}
			// TODO 返利更新逻辑未知
			// 优惠卷
			if (OrderConstant.FANLI_TYPE_DISTRIBUTION_ORDER == order.getFanliType()) {
				set.set(TABLE.SETTLEMENT_FLAG, OrderConstant.SETTLEMENT_NOT);
				set.set(TABLE.FANLI_MONEY, BigDecimal.ZERO);
			}
			break;
		default:
			break;
		}
		set.where(TABLE.ORDER_SN.eq(returnOrder.getOrderSn())).execute();
		logger().info("退款退货时更新订单信息end");
	}

	public void setOrderstatus(String orderSn, byte orderStatus) {
		OrderInfoRecord order = getOrderByOrderSn(orderSn);
		switch (orderStatus) {
		// 发货
		case OrderConstant.ORDER_SHIPPED:
			order.setOrderStatus(OrderConstant.ORDER_SHIPPED);
			order.setShippingTime(DateUtil.getSqlTimestamp());
			break;
		// 取消
		case OrderConstant.ORDER_CANCELLED:
			order.setOrderStatus(OrderConstant.ORDER_CANCELLED);
			order.setCancelledTime(DateUtil.getSqlTimestamp());
			// 返利订单特殊处理
			if (order.getFanliType() == OrderConstant.FANLI_TYPE_DISTRIBUTION_ORDER) {
				order.setSettlementFlag(OrderConstant.SETTLEMENT_NOT);
				order.setFanliMoney(BigDecimal.ZERO);
			}
			break;
		// 关闭
		case OrderConstant.ORDER_CLOSED:
			order.setOrderStatus(OrderConstant.ORDER_CLOSED);
			order.setClosedTime(DateUtil.getSqlTimestamp());
			break;
		// 核销 收货
		case OrderConstant.ORDER_RECEIVED:
			order.setOrderStatus(OrderConstant.ORDER_RECEIVED);
			order.setConfirmTime(DateUtil.getSqlTimestamp());
			break;
		// 完成
		case OrderConstant.ORDER_FINISHED:
			order.setOrderStatus(OrderConstant.ORDER_FINISHED);
			order.setFinishedTime(DateUtil.getSqlTimestamp());
			break;
        case OrderConstant.ORDER_WAIT_DELIVERY:
            order.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
            break;
		default:
			return;
		}
		order.update();
	}

	/**
	 * 设置订单支付方式数组
	 * 
	 * @param order
	 */
	public void setPayCodeList(OrderListInfoVo order) {
		ArrayList<Byte> payCodes = new ArrayList<Byte>(OrderConstant.SEARCH_PAY_WAY_WXPAY);
		//订单类型
        List<Byte> orderType = Arrays.asList(OrderInfoService.orderTypeToByte(order.getGoodsType()));
        if (BigDecimalUtil.compareTo(order.getUseAccount(), null) > 0
				|| BigDecimalUtil.compareTo(order.getMemberCardBalance(), null) > 0) {
			/** 余额 */
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_USE_ACCOUNT);
		}
		if (BigDecimalUtil.compareTo(order.getScoreDiscount(), null) > 0) {
			/** 积分支付 */
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_SCORE_DISCOUNT);
		}
		if(orderType.contains(BaseConstant.ACTIVITY_TYPE_INTEGRAL)) {
			/**积分兑换*/
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_SCORE_EXCHANGE);
		}
		if (OrderConstant.PAY_CODE_COD.equals(order.getPayCode())) {
			/** 货到付款 */
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_COD);
		}
		if (orderType.contains(BaseConstant.ACTIVITY_TYPE_MY_PRIZE)) {
			/** 活动奖品 */
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_EVENT_PRIZE);
		}
		if (OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
			/** 微信支付 */
			payCodes.add(OrderConstant.SEARCH_PAY_WAY_WXPAY);
		}
		order.setPayCodeList(payCodes);
	}

	/**
	 * 提醒发货
	 * 
	 * @param order
	 */
	public void remind(OrderInfoMpVo order) {
		db().update(TABLE).set(TABLE.ORDER_REMIND, (byte) (order.getOrderRemind() + 1))
				.set(TABLE.ORDER_REMIND_TIME, DateUtil.getSqlTimestamp()).where(TABLE.ORDER_ID.eq(order.getOrderId()))
				.execute();
	}

	/**
	 * 延长发货
	 * 
	 * @param order
	 */
	public void extendReceive(OrderInfoMpVo order) {
		db().update(TABLE).set(TABLE.EXTEND_RECEIVE_ACTION, order.getExtendReceiveAction())
				.set(TABLE.EXTEND_RECEIVE_TIME, order.getExtendReceiveTime())
				.where(TABLE.ORDER_ID.eq(order.getOrderId())).execute();
	}

	/**
	 * 删除
	 * 
	 * @param order
	 */
	public void delete(OrderListMpVo order) {
		db().update(TABLE).set(TABLE.DEL_FLAG, DelFlag.DISABLE.getCode())
				.set(TABLE.DEL_TIME, DateUtil.getSqlTimestamp()).where(TABLE.ORDER_ID.eq(order.getOrderId())).execute();
	}

	/**
	 * 获取上次订单地址
	 * 
	 * @param userId
	 * @return UserAddressVo
	 */
	public UserAddressVo getLastOrderAddress(Integer userId) {
		return db().
				select(TABLE.CONSIGNEE, TABLE.PROVINCE_NAME, TABLE.PROVINCE_CODE, TABLE.CITY_NAME, TABLE.CITY_CODE, TABLE.DISTRICT_NAME, TABLE.DISTRICT_CODE, TABLE.CONSIGNEE, TABLE.ADDRESS, TABLE.MOBILE, TABLE.ADDRESS_ID).
				from(TABLE).
				where(TABLE.USER_ID.eq(userId)).
				orderBy(TABLE.ORDER_ID.desc()).
				limit(1).
				fetchAnyInto(UserAddressVo.class);
	}

    /**
     * 创建订单
     */
	public OrderInfoRecord addRecord(String orderSn, CreateParam param, CreateOrderBo orderBo, List<OrderGoodsBo> goodsBos, OrderBeforeVo beforeVo){
        OrderInfoRecord order = db().newRecord(TABLE);
        //基础信息
        order.setOrderSn(orderSn);
        order.setShopId(getShopId());
        //param赋值
        param.intoRecord(order);
        //before order赋值
        beforeVo.intoRecord(order);
        //orderBo赋值
        orderBo.intoRecord(order);
        //订单类型
        order.setGoodsType(getGoodsTypeToInsert(orderBo.getOrderType()));
        //补款状态
        if(BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(param.getActivityType())) {
            order.setBkOrderPaid(
                beforeVo.getMoneyPaid().compareTo(BigDecimal.ZERO) > 0 ?
                    OrderConstant.BK_PAY_NO :
                    (BigDecimalUtil.compareTo(beforeVo.getBkOrderMoney(), null) > 0 ?
                        OrderConstant.BK_PAY_FRONT : OrderConstant.BK_PAY_FINISH));
        }else {
            order.setBkOrderPaid(OrderConstant.BK_PAY_NO);
        }
        //TODO 推广信息
        order.setIsPromote((byte)0);
        //主订单号
        if(orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GIVE_GIFT)){
            order.setMainOrderSn(order.getOrderSn());
        }
        //会员卡
        if(beforeVo.getDefaultMemberCard() != null){
            order.setMemberCardId(beforeVo.getDefaultMemberCard().getInfo().getCardId());
            order.setCardNo(beforeVo.getDefaultMemberCard().getInfo().getCardNo());
        }
        //门店
        if (orderBo.getStore() != null) {
            order.setStoreId(orderBo.getStore().getStoreId());
            order.setStoreName(orderBo.getStore().getStoreName());
            //TODO
            order.setVerifyCode("");
        }
        //必填信息初始化orderSn
        if(param.getMust() != null) {
            param.getMust().setOrderSn(orderSn);
        }
        order.setCurrency(saas().shop.getCurrency(getShopId()));
        return order;
    }

    /**
     * 微信支付完成支付数据更新到订单
     * @param prepayId 预支付id
     * @param orderId 订单id
     * @param orderSn bk特殊
     */
    public void updatePrepayId(String prepayId, Integer orderId, String orderSn){
	    if(orderSn.endsWith(OrderConstant.BK_SN_SUFFIX)) {
            db().update(TABLE).set(TABLE.BK_PREPAY_ID, prepayId)
                .set(TABLE.BK_ORDER_SN, orderSn)
                .where(TABLE.ORDER_ID.eq(orderId)).execute();
        }else {
            db().update(TABLE).set(TABLE.PREPAY_ID, prepayId).where(TABLE.ORDER_ID.eq(orderId)).execute();
        }
    }

    /**
     * 自动任务获取可关闭订单(过滤定金未支付订单)
     */
    public Result<OrderInfoRecord> getCanAutoCloseOrders( ){
        return db().selectFrom(TABLE).where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY).
            and(TABLE.EXPIRE_TIME.le(DateUtil.getSqlTimestamp())).
            and(TABLE.TK_ORDER_TYPE.eq(OrderConstant.TK_NORMAL)).
            and(TABLE.BK_ORDER_PAID.eq(OrderConstant.BK_PAY_NO))).
            fetch();
    }

    /**
     * 自动任务获取可收货订单
     */
    public Result<OrderInfoRecord> getCanAutoReceiveOrders( ){
        return db().selectFrom(TABLE).where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_SHIPPED).
            and(TABLE.DELIVER_TYPE.in(OrderConstant.DELIVER_TYPE_COURIER, OrderConstant.CITY_EXPRESS_SERVICE)).
            and(TABLE.TK_ORDER_TYPE.eq(OrderConstant.TK_NORMAL)).
            and(DSL.timestampAdd(TABLE.SHIPPING_TIME, TABLE.RETURN_DAYS_CFG, DatePart.DAY).le(DSL.now()))).
            fetch();
    }

    /**
     * 自动任务获取可完成订单
     */
    public Result<OrderInfoRecord> autoFinishOrders( ){
        return db().selectFrom(TABLE).where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_RECEIVED).
            and(TABLE.TK_ORDER_TYPE.eq(OrderConstant.TK_NORMAL)).
            and(DSL.timestampAdd(TABLE.SHIPPING_TIME, TABLE.ORDER_TIMEOUT_DAYS, DatePart.DAY).le(DSL.now()))).
            fetch();
    }

    /**
     * 得到即将过期未支付的订单列表。每1分钟执行一次，取还有10分钟就要过期未支付的订单列表
     */
    public Result<OrderInfoRecord> getExpiringNoPayOrderList(){
        Instant now = Instant.now();
        return db().selectFrom(TABLE).where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY).
            and(TABLE.EXPIRE_TIME.between(
                Timestamp.from(now.plusSeconds(10 * 60)),
                Timestamp.from(now.plusSeconds(11 * 60)))).
            and(TABLE.BK_ORDER_PAID.eq(OrderConstant.BK_PAY_NO)).
            and(TABLE.TK_ORDER_TYPE.eq(OrderConstant.TK_NORMAL)).
            and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).
            fetch();
    }

    /**
     * 获取赠品订单数
     * @param orderSns sns
     */
    public Integer getGiftOrderCount(List<String> orderSns){
        return db().selectCount().from(TABLE).where(
            TABLE.ORDER_SN.in(orderSns)
                //TODO 待付款锁库存.and(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY).and(TABLE.i))
                .and(TABLE.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY))
        ).fetchAnyInto(Integer.class);
    }

    /**
     * 获取活动指定商品当前用户购买次数
     * @param userId userid
     * @param goodsId goodsId
     * @return count
     */
    public Integer getGoodsBuyNum(Integer userId, List<Integer> goodsId){
        Condition condition = TABLE.USER_ID.eq(userId).and(TABLE.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY));
        SelectJoinStep<Record1<Integer>> select = db().selectCount().from(TABLE);
        if(CollectionUtils.isNotEmpty(goodsId)){
            select.leftJoin(ORDER_GOODS).on(TABLE.ORDER_SN.eq(ORDER_GOODS.ORDER_SN));
        }
        return select.where(condition).fetchAnyInto(Integer.class);
    }

    /**
     * 获取预售活动已购买数量
     * @param userId
     * @param preSaleId
     * @return
     */
    public Integer getPreSaletUserBuyNumber(Integer userId, Integer preSaleId){
        Integer sum = db().select(sum(ORDER_GOODS.GOODS_NUMBER)).from(ORDER_GOODS)
            .leftJoin(TABLE).on(TABLE.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .where(TABLE.USER_ID.eq(userId)
                .and(TABLE.ORDER_STATUS.notIn(OrderConstant.ORDER_CANCELLED, ORDER_CLOSED))
                .and(TABLE.BK_ORDER_PAID.gt(OrderConstant.BK_PAY_NO))
                .and(TABLE.ACTIVITY_ID.eq(preSaleId))
                .and(ORDER_GOODS.IS_GIFT.eq(OrderConstant.IS_GIFT_N))
                .and(TABLE.GOODS_TYPE.likeRegex(getGoodsTypeToSearch(new Byte[]{BaseConstant.ACTIVITY_TYPE_PRE_SALE}))))
            .fetchAnyInto(Integer.class);
        return sum == null ? 0 : sum;
    }

    public Byte getOrderIsReturnCoupon(Integer orderId) {
        return db().select(TABLE.IS_REFUND_COUPON).where(TABLE.ORDER_ID.eq(orderId)).fetchOneInto(Byte.class);
    }

    public void insteadPayOrderSetMoney(OrderInfoRecord order, BigDecimal money){
        order.setMoneyPaid(money);
        order.update();
    }

    public void updateStockLock(OrderInfoRecord order, Byte isLock) {
        order.setIsLock(isLock);
        order.update();
    }

    /**
     * 计算改会员卡在当前周期使用次数
     * @param userId userId
     * @param cardId cardId
     * @param freeLimit -1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日
     * @return freeLimit = -1 / 0 / 1 -> 0
     */
    public int getCardFreeShipSum(Integer userId, Integer cardId, Byte freeLimit) {
        logger().info("计算改会员卡在当前周期使用次数");
    	if(freeLimit <= CardFreeship.shipType.SHIP_IN_EFFECTTIME.getType()) {
            return 0;
        }
        Timestamp[] cardFreeShipInterval = getCardFreeShipInterval(freeLimit);
        
        if(cardFreeShipInterval == null) {
        	logger().info("cardFreeShipInterval为null");
        	return 0; 
        }
        int result = db().
	            selectCount().
	            from(TABLE).
	            where(TABLE.USER_ID.eq(userId).
	                and(TABLE.IS_FREESHIP_CARD.eq(OrderConstant.YES)).
	                and(TABLE.MEMBER_CARD_ID.eq(cardId)).
	                and(TABLE.ORDER_STATUS.gt(OrderConstant.ORDER_CLOSED)).
	                and(TABLE.CREATE_TIME.ge(cardFreeShipInterval[0])).
	                and(TABLE.CREATE_TIME.le(cardFreeShipInterval[1]))).
	            fetchOne(0,int.class);
        return  result;
    }

    /**
     * 获取指定条件时间周期
     * @param freeLimit 2：年，3：季，4：月，5：周，6：日的
     * @return Timestamp[2] = {start, end}
     */
    private Timestamp[] getCardFreeShipInterval(Byte freeLimit) {
        LocalDate startDate = null, endDate = null;
        LocalDate currentDate = LocalDate.now();
        //初始化当前年月日
        int year = currentDate.getYear(), month = currentDate.getMonthValue(), day = currentDate.getDayOfMonth();
        if(CardFreeship.shipType.SHIP_YEAR.getType() == freeLimit) {
            //年
            startDate = LocalDate.of(year, 1 ,1);
            endDate = LocalDate.of(year, 12, 1).with(TemporalAdjusters.lastDayOfMonth());
        }else if(CardFreeship.shipType.SHIP_SEASON.getType() == freeLimit) {
            //季
            startDate = LocalDate.of(year, (((month -1) / 3 + 1)* 3 -2),1);
            endDate = LocalDate.of(year, (((month -1) / 3 + 1)* 3) ,1).with(TemporalAdjusters.lastDayOfMonth());
        }else if(CardFreeship.shipType.SHIP_MONTH.getType() == freeLimit) {
            //月
            startDate = LocalDate.of(year, month, 1);
            endDate = LocalDate.of(year, month ,1).with(TemporalAdjusters.lastDayOfMonth());
        }else if(CardFreeship.shipType.SHIP_WEEK.getType() == freeLimit) {
            //周(周一开始)
            //the day-of-week, from 1 (Monday) to 7 (Sunday)
            int dayOfWeek = currentDate.getDayOfWeek().getValue();
            startDate = currentDate.plusDays(-dayOfWeek + 1);
            endDate = currentDate.plusDays(7 -dayOfWeek);
        }else if(CardFreeship.shipType.SHIP_DAY.getType() == freeLimit) {
            //天
            startDate = currentDate;
            endDate = currentDate;
        }else {
            return null;
        }
        Timestamp[] startAndEnd =  new Timestamp[2];
        startAndEnd[0] = Timestamp.valueOf(LocalDateTime.of(startDate, DateUtil.minTime));
        startAndEnd[1] = Timestamp.valueOf(LocalDateTime.of(endDate, DateUtil.maxTime));
        return startAndEnd;
    }

    /******************************************分割线以下与订单模块没有*直接*联系*********************************************/
	/**
	 * 根据用户id获取累计消费金额
	 */
	public BigDecimal getAllConsumpAmount(Integer userId) {
		return getConsumeOrder(userId).getTotalMoneyPaid();
	}

	
	/**
	 * 获取用户消费 订单
	 * @param userId
	 */
	public UserOrderBean getConsumeOrder(Integer userId){
		logger().info("获取用户消费订单");
		 UserOrderBean order = db().select(DSL.count(TABLE.ORDER_ID).as("orderNum"),
				DSL.sum(TABLE.MONEY_PAID.add(TABLE.USE_ACCOUNT).add(TABLE.MEMBER_CARD_BALANCE)).as("totalMoneyPaid"))
			.from(TABLE)
			.where(TABLE.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY)
				.or(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY).and(TABLE.BK_ORDER_PAID.greaterThan(OrderConstant.BK_PAY_NO)))
			  )
			.and(TABLE.ORDER_SN.eq(TABLE.MAIN_ORDER_SN).or(TABLE.MAIN_ORDER_SN.eq("")))
			.and(TABLE.IS_COD.eq(OrderConstant.IS_COD_NO)
					.or(TABLE.IS_COD.eq(OrderConstant.IS_COD_YES).and(TABLE.SHIPPING_TIME.isNotNull()))
				)
			.and(TABLE.USER_ID.eq(userId))
			.fetchAnyInto(UserOrderBean.class);
		 
		 // 门店买单订单
		 UserOrderBean storeOrder = saas().getShopApp(getShopId()).store.reservation.storeOrderService.getConsumerOrder(userId);
		 
		 // 门店服务订单
		 UserOrderBean serviceOrder = saas().getShopApp(getShopId()).store.serviceOrder.getConsumerOrder(userId);
		 
		 // 会员卡续费订单
		 UserOrderBean cardRenew = saas().getShopApp(getShopId()).userCard.getConsumerOrder(userId);
		 
		 Integer orderNum = order.getOrderNum()+storeOrder.getOrderNum()+serviceOrder.getOrderNum()+cardRenew.getOrderNum();
		 BigDecimal totalMoneyPaid = BigDecimal.ZERO;
		 List<BigDecimal> tmp = Arrays.<BigDecimal>asList(order.getTotalMoneyPaid(),storeOrder.getTotalMoneyPaid(),serviceOrder.getTotalMoneyPaid(),cardRenew.getTotalMoneyPaid());
		 for(BigDecimal val: tmp) {
			 totalMoneyPaid = BigDecimalUtil.add(totalMoneyPaid, val);
		 }
		 return UserOrderBean.builder().orderNum(orderNum).totalMoneyPaid(totalMoneyPaid).build();
	}
	
	/**
	 * 获取用户所有消费订单
	 */
	public UserOrderBean getAllConsumeOrder(Integer userId){
		logger().info("获取用户所有消费订单");
		UserOrderBean order = getConsumeOrder(userId);
		UserOrderBean cardOrder = saas().getShopApp(getShopId()).memberCardOrder.getConsumeOrder(userId);
		
		Integer orderNum = order.getOrderNum()+cardOrder.getOrderNum();
		BigDecimal orderMoney = BigDecimalUtil.add(order.getTotalMoneyPaid(),cardOrder.getTotalMoneyPaid());
		BigDecimal unitPrice = BigDecimal.ZERO;
		if(orderNum>0) {
			unitPrice = BigDecimalUtil.divide(orderMoney, BigDecimal.valueOf(orderNum));
		}
		return UserOrderBean.builder()
					.orderNum(orderNum)
					.totalMoneyPaid(orderMoney)
					.unitPrice(unitPrice)
					.build();
	}
	
	
	
	
	
	/**
	 * 会员卡消费余额
	 * 
	 * @param userId
	 * @return
	 */
	public BigDecimal getCardConsumpAmount(Integer userId) {

		Record1<BigDecimal> record = getCardConsumpAmountSql(userId);
		if (record != null) {
			return record.into(BigDecimal.class);
		} else {
			return BigDecimal.ZERO;
		}
	}

	public Record1<BigDecimal> getCardConsumpAmountSql(Integer userId) {
		return db().select(sum(ORDER_INFO.MEMBER_CARD_BALANCE)).from(ORDER_INFO).where(ORDER_INFO.USER_ID.eq(userId))
				.and(ORDER_INFO.ORDER_STATUS.eq(ORDER_FINISHED)).fetchAny();
	}

	/**
	 * 最近下单时间
	 */
	public LocalDateTime lastOrderTime(Integer userId) {
		logger().info("获取用户最近下单时间");
		// 普通订单
		Timestamp orderTime = lastNormalOrderTime(userId);
		// 虚拟订单
		 Timestamp cardOrderTime = saas().getShopApp(getShopId()).memberCardOrder.lastOrderTime(userId);
		// 门店订单
		 Timestamp storeStoreTime = saas().getShopApp(getShopId()).store.reservation.storeOrderService.lastOrderTime(userId);
		// 服务订单
		 Timestamp serviceTime = saas().getShopApp(getShopId()).store.serviceOrder.lastOrderTime(userId);
		 
		 List<Timestamp> times = Arrays.<Timestamp>asList(orderTime,cardOrderTime,storeStoreTime,serviceTime);
		 LocalDateTime res = null;
		 for(Timestamp t: times) {
			 if(t != null) {
				 LocalDateTime cur = t.toLocalDateTime();
				 if(res == null) {
					 res = cur;
				 }else {
					 if(cur.isAfter(res)) {
						 res = cur;
					 }
				 }
			 }
		 }
		 logger().info("最近下单时间： "+res);
		 return res;
	}
	
	/**
	 * 普通订单最近下单时间
	 */
	public Timestamp lastNormalOrderTime(Integer userId) {
		logger().info("普通订单最近下单时间");
		return db().select(TABLE.CREATE_TIME)
			.from(TABLE)
			.where(TABLE.USER_ID.eq(userId))
			.orderBy(TABLE.CREATE_TIME.desc())
			.fetchAnyInto(Timestamp.class);
	}
	

	/**
	 * 获取一个组订单的退款数量
	 * 
	 * @param mainOrderSn
	 * @return
	 */
	public Integer getReturnNumByMainOrderSn(String mainOrderSn) {
		return db().selectCount().from(TABLE)
				.where(TABLE.ORDER_STATUS.eq(ORDER_RETURN_FINISHED).or(TABLE.ORDER_STATUS.eq(ORDER_REFUND_FINISHED)))
				.and(TABLE.MAIN_ORDER_SN.notEqual(TABLE.ORDER_SN)).and(TABLE.MAIN_ORDER_SN.eq(mainOrderSn)).fetchOne()
				.component1();
	}

	public UserCenterNumBean getUserCenterNum(Integer userId, Integer orderSort, Integer[] orderStatus,
			Integer[] refundStatus) {

		SelectConditionStep<? extends Record> select = db()
				.select(sum(ORDER_INFO.ORDER_AMOUNT).as("orderAmount"), count().as("count"), ORDER_INFO.CREATE_TIME)
				.from(ORDER_INFO).where(ORDER_INFO.USER_ID.eq(userId));

		List<Byte> orderStatusList = new ArrayList<>(
				Arrays.asList(ORDER_FINISHED, ORDER_RETURN_FINISHED, ORDER_REFUND_FINISHED));

		if (orderStatus.length > 0) {
			if (orderSort == 4 && refundStatus.length > 0) {
				select.and(ORDER_INFO.ORDER_STATUS.notIn(orderStatusList));
				select.and(ORDER_INFO.REFUND_STATUS.in(Arrays.asList(refundStatus)));
			} else {
				select.and(ORDER_INFO.ORDER_STATUS.in(Arrays.asList(orderStatus)));
				select.and(ORDER_INFO.REFUND_STATUS.eq(REFUND_DEFAULT_STATUS));
			}
		}
		select.and(ORDER_INFO.DEL_FLAG.eq((byte) 0));
		select.orderBy(ORDER_INFO.CREATE_TIME.desc());

		return select.fetchAnyInto(UserCenterNumBean.class);
	}

	/**
	 * 获得用户购买商品数
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getUserBuyGoodsNum(Integer userId) {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(3L));
		return db().selectCount().from(ORDER_GOODS).leftJoin(ORDER_INFO)
				.on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
				.where(ORDER_INFO.USER_ID.eq(userId).and(ORDER_INFO.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY))
						.and(ORDER_INFO.CREATE_TIME.gt(timestamp)))
				.fetchOne().into(Integer.class);
	}

	/**
	 * 订单导出数据的条数
	 * 
	 * @param param
	 * @return
	 */
	public int getExportOrderListSize(OrderExportQueryParam param) {
		SelectJoinStep<? extends Record> select = db().selectCount().from(ORDER_INFO).innerJoin(ORDER_GOODS)
				.on(ORDER_INFO.ORDER_ID.eq(ORDER_GOODS.ORDER_ID)).leftJoin(USER)
				.on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
		select.where(ORDER_INFO.ORDER_SN.notEqual(ORDER_INFO.MAIN_ORDER_SN));
		buildOptions(select, param,true);
		return select.fetchOne().into(Integer.class);
	}

	/**
	 * 订单导出的原始数据
	 * 
	 * @param param
	 * @return
	 */
	public List<OrderExportVo> getExportOrderList(OrderExportQueryParam param) {
		SelectJoinStep<? extends Record> select = db()
				.select(ORDER_INFO.fields()).
        select(ORDER_GOODS.REC_ID, ORDER_GOODS.GOODS_ID, ORDER_GOODS.GOODS_SN,
						ORDER_GOODS.PRODUCT_ID, ORDER_GOODS.PRODUCT_SN, ORDER_GOODS.GOODS_NAME,
						ORDER_GOODS.GOODS_NUMBER, ORDER_GOODS.MARKET_PRICE, ORDER_GOODS.GOODS_PRICE,
						ORDER_GOODS.DISCOUNTED_GOODS_PRICE, ORDER_GOODS.GOODS_ATTR, ORDER_GOODS.SEND_NUMBER,
						ORDER_GOODS.RETURN_NUMBER, USER.USERNAME.as("user_name"), USER.MOBILE.as("user_mobile"),
						USER.SOURCE.as("user_source"), USER.INVITE_SOURCE, USER.INVITE_ACT_ID)
				.from(ORDER_INFO).innerJoin(ORDER_GOODS).on(ORDER_INFO.ORDER_ID.eq(ORDER_GOODS.ORDER_ID)).leftJoin(USER)
				.on(ORDER_INFO.USER_ID.eq(USER.USER_ID));
		select.where(ORDER_INFO.ORDER_SN.notEqual(ORDER_INFO.MAIN_ORDER_SN));
		buildOptions(select, param,true);
		select.orderBy(ORDER_INFO.ORDER_ID.desc());

		List<OrderExportVo> list = select
				.limit(param.getExportRowStart() - 1, param.getExportRowEnd() - param.getExportRowStart() + 1)
				.fetchInto(OrderExportVo.class);
		return list;
	}

	/**
	 * 是否是新下单用户
	 * 
	 * @param userId  用户id
	 * @param waitPay 是否统计代付款 (默认true)
	 * @return true (没有订单)新用户 false (有订单)不是新用户
	 */
	public Boolean isNewUser(Integer userId, Boolean waitPay) {
		SelectConditionStep<Record1<Integer>> selectConditionStep = db().selectCount().from(TABLE)
				.where(TABLE.IS_COD.eq((byte) 0).or(TABLE.IS_COD.eq((byte) 1).and(TABLE.SHIPPING_TIME.isNotNull())))
				.and(TABLE.USER_ID.eq(userId));
		if (waitPay) {
			selectConditionStep
					.and(TABLE.ORDER_STATUS.notIn(OrderConstant.ORDER_CANCELLED, OrderConstant.ORDER_CLOSED));
		} else {
			selectConditionStep.and(TABLE.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY));
		}
		return selectConditionStep.fetchOne().component1() == 0;
	}

	public boolean isNewUser(Integer userId) {
		return isNewUser(userId, true);
	}

	/**
	 * 某用户指定时间段内订单数(开区间)
	 * 
	 * @return
	 */
	public int getUserOrderNumber(Integer userId, Timestamp startTime, Timestamp endTime) {
		if (userId == null || userId <= 0) {
			return 0;
		}
		if (startTime == null && endTime != null) {
			return db().selectCount().from(TABLE).where(TABLE.USER_ID.eq(userId).and(TABLE.CREATE_TIME.lt(endTime))
					.and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).fetchOne().into(Integer.class);
		}
		if (startTime != null && endTime == null) {
			return db().selectCount().from(TABLE).where(TABLE.USER_ID.eq(userId).and(TABLE.CREATE_TIME.gt(startTime))
					.and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).fetchOne().into(Integer.class);
		}
		if (startTime != null && endTime != null) {
			return db().selectCount().from(TABLE)
					.where(TABLE.USER_ID.eq(userId).and(TABLE.CREATE_TIME.gt(startTime))
							.and(TABLE.CREATE_TIME.lt(endTime)).and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)))
					.fetchOne().into(Integer.class);
		}
		if (startTime == null && endTime == null) {
			return db().selectCount().from(TABLE)
					.where(TABLE.USER_ID.eq(userId).and(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).fetchOne()
					.into(Integer.class);
		}

		return 0;
	}

	/**
	 * 该时间之后有下单的用户ID列表
	 */
	public List<Integer> getUserIdFromBuyStartTime(Timestamp time) {
		return db().select(TABLE.USER_ID).from(TABLE).where(TABLE.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY)).and(TABLE.CREATE_TIME.ge(time))
				.groupBy(TABLE.USER_ID).fetchInto(Integer.class);

	}

	/**
	 * 该时间之前有下单的用户ID列表
	 */
	public List<Integer> getUserIdUtilToBuyEndTime(Timestamp time) {
		return db().select(TABLE.USER_ID).from(TABLE).where(TABLE.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY))
				.and(TABLE.CREATE_TIME.le(time)).groupBy(TABLE.USER_ID).fetchInto(Integer.class);

	}

	/**
	 * 获取大于等于该购买次数的用户Id列表
	 */
	public List<Integer> getUserIdGreateThanBuyCountLow(Integer cnt) {
		return db().select(TABLE.USER_ID)
				.from(TABLE)
				.where(TABLE.ORDER_STATUS.greaterThan(ORDER_CLOSED))
				.groupBy(TABLE.USER_ID).having(DSL.count(TABLE.USER_ID).ge(cnt))
				.fetchInto(Integer.class);

	}

	/**
	 * 获取小于等于该购买次数的用户Id列表
	 */
	public List<Integer> getUserIdLessThanBuyCountHight(Integer cnt) {
		return db().select(TABLE.USER_ID)
				.from(TABLE)
				.where(TABLE.ORDER_STATUS.greaterThan(ORDER_CLOSED))
				.groupBy(TABLE.USER_ID)
				.having(DSL.count(TABLE.USER_ID).le(cnt)).fetchInto(Integer.class);
		
	}

	/**
	 * 通过商品id获取购买过该商品的用户id列表
	 */
	public List<Integer> getUserIdHasBuyTheGoods(List<Integer> goodsIdList) {
		return db().select(TABLE.USER_ID).from(ORDER_GOODS.leftJoin(TABLE).on(ORDER_GOODS.ORDER_SN.eq(TABLE.ORDER_SN)))
				.where(TABLE.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY)).and(ORDER_GOODS.GOODS_ID.in(goodsIdList))
				.groupBy(TABLE.USER_ID).fetch().getValues(TABLE.USER_ID, Integer.class);
	}

    /**
     * 批量改为待发货
     * @param orderSnList
     */
	public void batchChangeToWaitDeliver(List<String> orderSnList){
	    if(orderSnList != null && orderSnList.size() > 0){
	        db().update(TABLE).set(TABLE.ORDER_STATUS, ORDER_WAIT_DELIVERY).where(TABLE.ORDER_SN.in(orderSnList)).execute();
        }
    }

    /**
     * 批量改为拼团成功
     * @param orderSnList
     */
	public void batchChangeToGroupBuySuccess(List<String> orderSnList){
	    if(orderSnList != null && orderSnList.size() > 0){
	        db().update(TABLE).set(TABLE.ORDER_STATUS, ORDER_PIN_SUCCESSS).where(TABLE.ORDER_SN.in(orderSnList)).execute();
        }
    }

    /**
     * Overdue delivery integer.发货逾期
     *
     * @param nDays the n days
     * @return the integer
     */
    public Integer overdueDelivery(Integer nDays) {
        return db().fetchCount(TABLE, TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY)
            .and(TABLE.CREATE_TIME.add(nDays).lessThan(Timestamp.valueOf(LocalDateTime.now()))));
    }

    /**
     * Overdue delivery integer.发货逾期订单id集合
     *
     * @param nDays the n days
     * @return the integer
     */
    public Set<Integer> overdueDeliverySet(Integer nDays) {
        Condition condition = TABLE.ORDER_STATUS.eq(ORDER_WAIT_DELIVERY)
            .and(TABLE.CREATE_TIME.add(nDays).lessThan(Timestamp.valueOf(LocalDateTime.now())));
        return db().select(TABLE.ORDER_ID).from(TABLE).where(condition).fetchSet(TABLE.ORDER_ID);
    }

    /**
     * Remind overdue order int.提醒发货订单数
     *
     * @return the int
     */
    public int remindOverdueOrder() {
        Condition condition = TABLE.ORDER_STATUS.eq(BYTE_THREE).and(TABLE.ORDER_REMIND.greaterThan(BYTE_ZERO));
        return db().fetchCount(TABLE, condition);
    }

    /**
     * Remind overdue order int.提醒发货订单id列表
     *
     * @return the int
     */
    public Set<Integer> remindOverdueOrderSet() {
        Condition condition = TABLE.ORDER_STATUS.eq(BYTE_THREE).and(TABLE.ORDER_REMIND.greaterThan(BYTE_ZERO));
        return db().select(TABLE.ORDER_ID).from(TABLE).where(condition).fetchSet(TABLE.ORDER_ID);
    }

    /**
     * 获得待支付尾款的订单
     * @param pinGroupId
     * @return
     */
    public Result<OrderInfoRecord> getNoPayOrderByIdentityId(Integer pinGroupId) {
		return db().selectFrom(TABLE).where(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY)
				.and(TABLE.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_DEPOSIT)
						.and(TABLE.BK_ORDER_PAID.eq(OrderConstant.BK_PAY_FRONT)).and(TABLE.ACTIVITY_ID.eq(pinGroupId)
								.and(TABLE.GOODS_TYPE.eq(String.valueOf(BaseConstant.ACTIVITY_TYPE_PRE_SALE)))))).fetch();

    }

    /**
     * 取orderId
     * @param orderSn
     * @return
     */
    public Integer getOrderIdBySn(String orderSn){
        return db().select(TABLE.ORDER_ID).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetchOptionalInto(Integer.class).orElse(null);
    }
    
    /**
     * 获得待付款活动订单
     * @param userId
     * @param goodsId
     * @param goodsType
     * @param activityId
     * @return
     */
    public OrderInfoRecord getWaitPayOrderByActivityId(Integer userId,Integer goodsId,Byte goodsType,Integer activityId) {
		return  db().select(TABLE.asterisk()).from(TABLE, ORDER_GOODS)
				.where(TABLE.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)
						.and(TABLE.ACTIVITY_ID.eq(activityId)
								.and(TABLE.ORDER_STATUS.eq(OrderConstant.ORDER_WAIT_PAY)
										.and(TABLE.USER_ID.eq(userId)
												.and(ORDER_GOODS.GOODS_ID.eq(goodsId)
														.and(DslPlus.findInSet(goodsType, TABLE.GOODS_TYPE)))))))
				.fetchAnyInto(TABLE);
    }
    
    /**
     * 	获得用户兑换卡未完成订单
     * @return OrderInfoRecord || null 如果没有数据
     */
    public OrderInfoRecord getNotFinishedOrderOfCard(String cardNo) {
    	logger().info("获取用户兑换卡尾完成的订单");
    	Condition condition = TABLE.CARD_NO.eq(cardNo)
    			.and(TABLE.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY))
    			.and(TABLE.ORDER_STATUS.notIn(OrderConstant.ORDER_FINISHED,OrderConstant.ORDER_RETURN_FINISHED,OrderConstant.ORDER_REFUND_FINISHED))
    			.and(TABLE.GOODS_TYPE.likeRegex(getGoodsTypeToSearch(new Byte[]{BaseConstant.ACTIVITY_TYPE_EXCHANG_ORDER})));
		
    	return db().fetchOne(TABLE, condition);
    }

}
