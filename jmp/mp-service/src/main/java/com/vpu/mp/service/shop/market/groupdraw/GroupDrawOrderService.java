package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderExport;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.order.OrderListVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.OrderMust.ORDER_MUST;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author 郑保乐
 */
@Service
public class GroupDrawOrderService extends ShopBaseService {

	public PageResult<OrderListVo> getGroupDrawOrderList(OrderListParam param) {
		SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Byte, String, Integer, Byte, Boolean>> select = getSelect(
				param);
		return getPageResult(select, param, OrderListVo.class);
	}

	private SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Byte, String, Integer, Byte, Boolean>> getSelect(
			OrderListParam param) {
		SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Byte, String, Integer, Byte, Boolean>> select = db()
				.select(JOIN_GROUP_LIST.ORDER_SN, ORDER_GOODS.GOODS_NAME, ORDER_GOODS.GOODS_IMG,
						JOIN_GROUP_LIST.USER_ID, ORDER_GOODS.ORDER_ID, ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE,
						ORDER_INFO.CONSIGNEE.as("consigneeRealName"), JOIN_GROUP_LIST.IS_WIN_DRAW,
						ORDER_INFO.COMPLETE_ADDRESS, DSL.count(JOIN_DRAW_LIST.USER_ID).as("codeCount"),
						ORDER_INFO.ORDER_STATUS,
						DSL.iif(JOIN_GROUP_LIST.STATUS.eq((byte) 1), true, false).as("grouped"))
				.from(JOIN_GROUP_LIST).leftJoin(ORDER_INFO).on(JOIN_GROUP_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
				.leftJoin(JOIN_DRAW_LIST)
				.on(JOIN_GROUP_LIST.USER_ID.eq(JOIN_DRAW_LIST.USER_ID)
						.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(JOIN_DRAW_LIST.GROUP_DRAW_ID)))
				.leftJoin(ORDER_GOODS).on(JOIN_GROUP_LIST.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where();
		buildOptions(select, param);
		select.orderBy(JOIN_GROUP_LIST.CREATE_TIME.desc());
		return select;
	}
	
	public List<OrderListVo> getGroupDrawOrderListNoPage(OrderListParam param) {
		return  getSelect(param).fetchInto(OrderListVo.class);
	}

	private void buildOptions(
			SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Byte, String, Integer, Byte, Boolean>> select,
			OrderListParam param) {
		Integer groupDrawId = param.getGroupDrawId();
		String goodsName = param.getGoodsName();
		String consigneeName = param.getConsigneeName();
		String mobile = param.getMobile();
		Timestamp createTime = param.getCreateTime();
		Byte orderStatus = param.getOrderStatus();
		String orderSn = param.getOrderSn();
		select.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId));
		if (isNotEmpty(mobile)) {
			select.and(ORDER_INFO.MOBILE.like(this.likeValue(mobile)));
		}
		if (isNotEmpty(orderSn)) {
			select.and(JOIN_GROUP_LIST.ORDER_SN.like(this.likeValue(orderSn)));
		}
		if (isNotEmpty(goodsName)) {
			select.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(goodsName)));
		}
		if (isNotEmpty(consigneeName)) {
			select.and(ORDER_INFO.CONSIGNEE.like(this.likeValue(consigneeName)));
		}
		if (orderStatus != -1) {
			select.and(ORDER_INFO.ORDER_STATUS.eq(orderStatus));
		}
		if (null != createTime) {
			select.and(DSL.date(ORDER_INFO.CREATE_TIME).eq(new Date(createTime.getTime())));
		}
		if(null!=param.getProvinceCode()) {
			select.and(ORDER_INFO.PROVINCE_CODE.eq(param.getProvinceCode()));
		}
		if(null!=param.getCityCode()) {
			select.and(ORDER_INFO.CITY_CODE.eq(param.getCityCode()));
		}
		if(null!=param.getDistrictCode()) {
			select.and(ORDER_INFO.DISTRICT_CODE.eq(param.getDistrictCode()));
		}
		select.groupBy(JOIN_GROUP_LIST.USER_ID, JOIN_GROUP_LIST.ORDER_SN, JOIN_GROUP_LIST.GOODS_ID,
				ORDER_GOODS.GOODS_IMG, ORDER_GOODS.GOODS_NAME, ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE,
				ORDER_INFO.CONSIGNEE, JOIN_GROUP_LIST.CREATE_TIME, JOIN_GROUP_LIST.IS_WIN_DRAW, ORDER_INFO.ORDER_STATUS,
				ORDER_GOODS.ORDER_ID, JOIN_GROUP_LIST.STATUS, ORDER_INFO.COMPLETE_ADDRESS);
	}

	/**
	 * 订单导出
	 * 
	 * @param param 查询信息
	 * @param lang  语言
	 * @return 表格信息
	 */
	public Workbook orderExport(OrderListParam param, String lang) {
		List<OrderExport> orderExport = new ArrayList<>();
		List<OrderListVo> tempList = getGroupDrawOrderListNoPage(param);
		tempList.forEach(item -> {
			OrderExport tempExport = new OrderExport();
			tempExport.setOrderSn(item.getOrderSn());
			tempExport.setGoodsName(item.getGoodsName());
			if (item.getGrouped()) {
				tempExport.setGrouped("是");
			} else {
				tempExport.setGrouped("否");
			}
			tempExport.setUserInfo(item.getConsigneeRealName() + ":" + item.getMobile());
			if (item.getIsWinDraw()) {
				tempExport.setIsWinDraw("是");
			} else {
				tempExport.setIsWinDraw("否");
			}
			tempExport.setCreateTime(item.getCreateTime());
			tempExport.setCodeCount(item.getCodeCount());
			switch (item.getOrderStatus()) {
			case OrderConstant.ORDER_WAIT_PAY:
				tempExport.setOrderStatus("待付款");
				break;
			case OrderConstant.ORDER_CANCELLED:
				tempExport.setOrderStatus("客户已取消");
				break;
			case OrderConstant.ORDER_CLOSED:
				tempExport.setOrderStatus("卖家关闭");
				break;
			case OrderConstant.ORDER_WAIT_DELIVERY:
				tempExport.setOrderStatus("待发货");
				break;
			case OrderConstant.ORDER_SHIPPED:
				tempExport.setOrderStatus("已发货");
				break;
			case OrderConstant.ORDER_FINISHED:
				tempExport.setOrderStatus("已完成");
				break;
			case OrderConstant.ORDER_RETURN_FINISHED:
				tempExport.setOrderStatus("完成退货");
				break;
			case OrderConstant.ORDER_REFUND_FINISHED:
				tempExport.setOrderStatus("退款成功");
				break;
			case OrderConstant.ORDER_PIN_PAYED_GROUPING:
				tempExport.setOrderStatus("拼团中");
				break;
			case OrderConstant.ORDER_PIN_SUCCESSS:
				tempExport.setOrderStatus("已成团");
				break;
			case OrderConstant.ORDER_GIVE_GIFT_FINISHED:
				tempExport.setOrderStatus("礼单已完成");
				break;
			default:
				tempExport.setOrderStatus("订单完成");
			}
			orderExport.add(tempExport);
		});
		// 表格导出
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(orderExport, OrderExport.class);
		return workbook;
	}
}
