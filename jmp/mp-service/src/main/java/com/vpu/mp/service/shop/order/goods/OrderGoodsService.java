package com.vpu.mp.service.shop.order.goods;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.market.MarketOrderGoodsListVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundVo.RefundVoGoods;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsMpVo;

/**
 * Table:ORDER_GOODS
 * @author 王帅
 *
 */
@Service
public class OrderGoodsService extends ShopBaseService{

	public final OrderGoods TABLE = ORDER_GOODS;
	/** 商品数量 发货数量 退款成功数量*/
	public static byte TOTAL_GOODSNUMBER = 0,TOTAL_SENDNUMBER = 1,TOTAL_SUCCESSRETURNNUMBER = 2;

	/**
	 * 	通过订单id[]查询其下商品
	 * @param arrayToSearch
	 * @return  Result<?>
	 */
	public Result<?> getByOrderIds(Integer... arrayToSearch) {
		Result<?> goods = db().select(TABLE.ORDER_ID,TABLE.REC_ID,TABLE.ORDER_SN,TABLE.GOODS_ID,TABLE.GOODS_NAME,TABLE.GOODS_SN,TABLE.GOODS_NUMBER,TABLE.GOODS_PRICE,TABLE.MARKET_PRICE,TABLE.GOODS_ATTR,TABLE.PRODUCT_SN,TABLE.PRODUCT_ID,TABLE.GOODS_IMG,TABLE.MAIN_REC_ID,TABLE.STRA_ID,TABLE.PER_DISCOUNT,TABLE.GOODS_SCORE,TABLE.GIFT_ID).from(TABLE)
			.where(TABLE.ORDER_ID.in(arrayToSearch))
			.orderBy(TABLE.ORDER_ID.desc())
			.fetch();
		return goods;
	}

    /**
     * 通过订单id[]查询其下商品
     * @param orderId id
     * @return
     */
	public Result<OrderGoodsRecord> getByOrderId(Integer orderId) {
		return db().selectFrom(TABLE).where(TABLE.ORDER_ID.eq(orderId)).fetch();
	}

	/**
	 * 单个订单商品
	 * @param orderId
	 * @return map<recId,obj>
	 */
	public Map<Integer, OrderGoodsMpVo> getKeyMapByIds(Integer orderId) {
		return db().selectFrom(TABLE).where(TABLE.ORDER_ID.eq(orderId)).fetchMap(TABLE.REC_ID, OrderGoodsMpVo.class);
	}
	/**
	 * 	通过订单sn[]查询其下商品
	 * @param orderSns
	 * @return Map<String, List<RefundVoGoods>>
	 */
	public Map<String, List<RefundVoGoods>> getByOrderSns(List<String> orderSns) {
		return db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.REC_ID,TABLE.GOODS_NAME,TABLE.GOODS_NUMBER,TABLE.RETURN_NUMBER,TABLE.GOODS_PRICE,TABLE.GOODS_ATTR,TABLE.DISCOUNTED_GOODS_PRICE,TABLE.PRODUCT_ID,TABLE.IS_CAN_RETURN,TABLE.IS_GIFT,TABLE.DISCOUNTED_TOTAL_PRICE,TABLE.GOODS_ID,TABLE.MARKET_PRICE,TABLE.SEND_NUMBER,TABLE.GOODS_IMG).from(TABLE)
			.where(TABLE.ORDER_SN.in(orderSns))
			.fetchGroups(TABLE.ORDER_SN,RefundVoGoods.class);
	}
	/**
	 *	计算子订单商品数量(主订单返回的map->size=0)
	 * @param goods
	 * @param currentOrder
	 * @param isMain
	 * @return HashMap<recid, sum>
	 */
	public HashMap<Integer, Integer> countSubOrderGoods(Map<String, List<RefundVoGoods>> goods , OrderListInfoVo currentOrder , Boolean isMain) {
		if(isMain) {
			HashMap<Integer, Integer> count = new HashMap<Integer,Integer>();
			for (Entry<String, List<RefundVoGoods>> entry : goods.entrySet()) {
				if(!currentOrder.getOrderSn().equals(entry.getKey())) {
					for (RefundVoGoods oneGoods : entry.getValue()) {
						if(count.get(oneGoods.getRecId()) == null) {
							count.put(oneGoods.getRecId(), oneGoods.getGoodsNumber());
						}else {
							count.put(oneGoods.getRecId(), count.get(oneGoods.getRecId()) + oneGoods.getGoodsNumber());
						}
					}
				}
			}
		}
		return new HashMap<Integer,Integer>(0);
	}

    /**
     * 退款完成后，更新状态及退款退货数量
     * @param orderSn
     * @param returnGoods
     * @param returnOrderRecord
     */
	public void updateInReturn(String orderSn , List<ReturnOrderGoodsRecord> returnGoods , ReturnOrderRecord returnOrderRecord) {
		//退款商品recIds
		List<Integer> recIds = returnGoods.stream().map(ReturnOrderGoodsRecord::getRecId).collect(Collectors.toList());
		//退款退货商品对应的orderGoods商品
		Result<OrderGoodsRecord> orderGoods = db().selectFrom(TABLE).where(TABLE.ORDER_SN.eq(orderSn).and(TABLE.REC_ID.in(recIds))).fetch();
		//退款商品map(key->recId)
		Map<Integer, ReturnOrderGoodsRecord> returnGoodsMap = returnGoods.stream().collect(Collectors.toMap(ReturnOrderGoodsRecord::getRecId,Function.identity()));
		for (OrderGoodsRecord goods : orderGoods) {
			switch (returnOrderRecord.getRefundStatus()) {
			//退款订单状态为完成
			case OrderConstant.REFUND_STATUS_FINISH:
				//状态
				goods.set(TABLE.REFUND_STATUS, returnOrderRecord.getRefundStatus());
				//此次退款退货数量
				int returnNum = returnGoodsMap.get(goods.getRecId()) == null ? 0 : returnGoodsMap.get(goods.getRecId()).getGoodsNumber();
				//修改退货退款商品数量
				goods.setReturnNumber(goods.getReturnNumber() + returnNum);
				break;
			default:
				goods.set(TABLE.REFUND_STATUS, returnOrderRecord.getRefundStatus());
				break;
			}
		}
		db().batchUpdate(orderGoods).execute();
	}

	/**
	 * 	判断当前订单是否可以退商品（有无可退商品数量）
	 * 	(有状态依赖于ordergoods表的商品数量与已经退货退款数量)
	 * @param orderSn
	 * @return
	 */
	public boolean canReturnGoodsNumber(String orderSn) {
		if(db().fetchCount(TABLE, TABLE.ORDER_SN.eq(orderSn).and(TABLE.GOODS_NUMBER.gt(TABLE.RETURN_NUMBER))) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 	计算订单商品数量 ，发货数量， 退款成功数量
	 * @param orderSn
	 * @return
	 */
	public int[] getTotalNumber(String orderSn) {
		Record3<BigDecimal, BigDecimal, BigDecimal> result = db().select(DSL.sum(TABLE.GOODS_NUMBER),DSL.sum(TABLE.SEND_NUMBER),DSL.sum(TABLE.RETURN_NUMBER)).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetchOne();
		int[] total = {TOTAL_GOODSNUMBER ,TOTAL_SENDNUMBER ,TOTAL_SUCCESSRETURNNUMBER};
		if(result != null) {
			total[TOTAL_GOODSNUMBER] = result.value1() != null ? result.value1().intValue() : 0;
			total[TOTAL_SENDNUMBER] = result.value2() != null ? result.value2().intValue() : 0;
			total[TOTAL_SUCCESSRETURNNUMBER] = result.value3() != null ? result.value3().intValue() : 0;
		}
		return total;
	}
	/**
	 * 	计算处于部分发货的情况下是否可以发货
	 * @param orderSn
	 * @return
	 */
	public boolean isCanDeliverOrder(String orderSn) {
		if(db().fetchCount(TABLE,
				TABLE.ORDER_SN.eq(orderSn).
				and(TABLE.GOODS_NUMBER.gt(TABLE.RETURN_NUMBER)).
				and(TABLE.SEND_NUMBER.eq((0))
				)) > 0 ) {
			return true;
		}
		return false;
	}

	public Result<Record> selectWhere(Condition where) {
		return db().select(TABLE.asterisk()).from(TABLE).where(where).fetch();
	}

	public List<OrderGoodsVo> getReturnGoods(String orderSn) {
		return selectWhere(TABLE.ORDER_SN.eq(orderSn).and(TABLE.RETURN_NUMBER.gt(0))).into(OrderGoodsVo.class);
	}

    /**
     * 	营销活动订单-根据orderSn取订单行信息
     * @param orderSn
     * @return  List<MarketOrderGoodsListVo>
     */
    public List<MarketOrderGoodsListVo> getMarketOrderGoodsByOrderSn(String orderSn) {
        return db().select(TABLE.GOODS_ID,TABLE.GOODS_NAME,TABLE.GOODS_IMG,TABLE.GOODS_PRICE).from(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetchInto(MarketOrderGoodsListVo.class);
    }

	/**
	 * 根据订单号查询商品
	 * @param orderSn
	 * @return
	 */
	public Result<? extends Record> getGoodsInfoByOrderSn(String orderSn){
		Result<Record6<Integer, String, String, String, BigDecimal, Integer>> record6s = db()
				.select(TABLE.GOODS_ID, TABLE.GOODS_SN, TABLE.GOODS_NAME, TABLE.GOODS_IMG, TABLE.GOODS_PRICE, TABLE.GOODS_NUMBER)
				.from(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetch();
		return record6s;
	}

	/**
	 * 根据订单号查询商品
	 * @param orderSn
	 * @return
	 */
	public Result<OrderGoodsRecord> getOrderGoods(String orderSn) {
		return db().selectFrom(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetch();
	}

    /**
     * 初始化数据
     * @param goods 输入参数
     */
    public OrderGoodsBo initOrderGoods(OrderBeforeParam.Goods goods) {
        logger().info("initOrderGoods初始化数据开始");
        OrderGoodsBo bo = OrderGoodsBo.builder().
            goodsId(goods.getGoodsId()).
            goodsName(goods.getGoodsInfo().getGoodsName()).
            goodsSn(goods.getGoodsInfo().getGoodsSn()).
            productId(goods.getProductId()).
            productSn(goods.getProductInfo().getPrdSn()).
            goodsNumber(goods.getGoodsNumber()).
            marketPrice(goods.getProductInfo().getPrdMarketPrice()).
            goodsPrice(goods.getProductInfo().getPrdPrice()).
            goodsAttr(goods.getProductInfo().getPrdDesc()).
            //TODO 需要考虑
            goodsAttrId("").
            goodsImg(goods.getGoodsInfo().getGoodsImg()).
            straId(goods.getStraId()).
            perDiscount(goods.getPerDiscount()).
            //TODO 需要考虑 是否赠品
            isGift(0).
            //TODO 需要考虑 赠品的关联商品
            rGoods("").
            //TODO 需要考虑 商品积分
            goodsScore(0).
            //TODO 需要考虑 商品成长值
            goodsGrowth(0).
            goodsType(goods.getGoodsInfo().getGoodsType()).
            discountedGoodsPrice(goods.getProductPrice()).
            discountedTotalPrice(BigDecimalUtil.multiply(goods.getProductPrice(), new BigDecimal(goods.getGoodsNumber()))).
            costPrice(goods.getProductInfo().getPrdCostPrice()).
            //TODO 逐级计算折扣
            discountDetail(StringUtils.EMPTY).
            deliverTemplateId(goods.getGoodsInfo().getDeliverTemplateId()).
            //TODO 规格质量
            goodsWeight(goods.getGoodsInfo().getGoodsWeight()).
            //TODO 后续处理
            userCoupon(null).
            catId(goods.getGoodsInfo().getCatId()).
            sortId(goods.getGoodsInfo().getSortId()).
            brandId(goods.getGoodsInfo().getBrandId()).
            goodsPriceAction(goods.getGoodsPriceAction()).
            purchasePriceId(null).
            purchasePriceRuleId(null).
            reducePriceId(null).
            firstSpecialId(null).
            isCardExclusive(goods.getGoodsInfo().getIsCardExclusive()).
            promoteInfo(null).
            build();
        logger().info("initOrderGoods初始化数据结束，参数为：",bo.toString());
        return bo;
    }

    /**
     * 商品入库
     * @param order 订单
     * @param bos 商品
     */
    public void addRecord(OrderInfoRecord order, List<OrderGoodsBo> bos){
        boolean isAllNotReturn = true;
        List<OrderGoodsRecord> records = new ArrayList<>(bos.size());
        for (OrderGoodsBo bo : bos) {
            bo.setOrderId(order.getOrderId());
            bo.setOrderSn(order.getOrderSn());
            if(bo.getIsCanReturn() != null && bo.getIsCanReturn() == OrderConstant.YES){
                isAllNotReturn = false;
            }
            records.add(db().newRecord(TABLE, bo));
        }
        if(isAllNotReturn){
            order.setReturnTypeCfg(OrderConstant.CFG_RETURN_TYPE_N);
            order.store();
        }
        db().batchInsert(records).execute();
    }


	/**
	 *  购买商品记录(三个月内)
	 * @param userId  用户ID
	 * @param keyWord 关键字
	 * @param currentPages 当前页
	 * @param pageRows 每页行数
	 * @return Record
	 * @author kdc
	 */
    public Result<? extends Record> buyingHistoryGoodsList(Integer userId, String keyWord, Integer currentPages, Integer pageRows){
		Timestamp timestamp = DateUtil.getTimeStampPlus(-3, ChronoUnit.MONTHS);
		SelectConditionStep<? extends Record> select = db().select(TABLE.GOODS_ID, DslPlus.dateFormatDay(TABLE.CREATE_TIME).as("date"))
				.from(TABLE)
				.leftJoin(GOODS).on(GOODS.GOODS_ID.eq(TABLE.GOODS_ID))
				.leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(TABLE.ORDER_SN))
				.where(ORDER_INFO.USER_ID.eq(userId))
				.and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(ORDER_INFO.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY))
				.and(ORDER_INFO.CREATE_TIME.gt(timestamp));
		if (!org.apache.commons.lang3.StringUtils.isBlank(keyWord)){
			select.and(GOODS.GOODS_NAME.like(likeValue(keyWord)));
		}
		return select.orderBy(ORDER_GOODS.CREATE_TIME.desc()).limit(pageRows*(currentPages - 1), pageRows).fetch();
	}

	/**
	 *  购买商品记录(三个月内)
	 * @param userId  用户ID
	 * @param keyWord 关键字
	 * @return Record
	 * @author kdc
	 */
	public Integer buyingHistoryGoodsCount(Integer userId, String keyWord){
		Timestamp timestamp = DateUtil.getTimeStampPlus(-3, ChronoUnit.MONTHS);
		SelectConditionStep<Record1<Integer>> select = db().selectCount()
				.from(TABLE)
				.leftJoin(GOODS).on(GOODS.GOODS_ID.eq(TABLE.GOODS_ID))
				.leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(TABLE.ORDER_SN))
				.where(ORDER_INFO.USER_ID.eq(userId))
				.and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(ORDER_INFO.ORDER_STATUS.ge(OrderConstant.ORDER_WAIT_DELIVERY))
				.and(ORDER_INFO.CREATE_TIME.gt(timestamp));

		return select.fetchOne().value1();
	}
	public List<Integer> getZhixiaoGoodsIds(){
        Timestamp local = Timestamp.valueOf(LocalDate.now().minusDays(30).atStartOfDay());

        return db().selectDistinct()
            .from(TABLE)
            .where(TABLE.CREATE_TIME.greaterThan(local))
            .fetch()
            .getValues(TABLE.GOODS_ID,Integer.class);
    }
}
