package com.vpu.mp.service.shop.market.presale;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.presale.DetailListVo;
import com.vpu.mp.service.pojo.shop.market.presale.OrderExcelVo;
import com.vpu.mp.service.pojo.shop.market.presale.OrderListParam;
import com.vpu.mp.service.pojo.shop.market.presale.OrderListVo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record13;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.OrderMust.ORDER_MUST;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.foundation.data.CurrencySymbol.RMB;
import static com.vpu.mp.service.foundation.util.Util.getCurrencyAmount;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 定金膨胀活动订单
 *
 * @author 郑保乐
 */
@Service
public class PreSaleOrderService extends ShopBaseService {

    /**
     * 获取活动明细
     */
    public PageResult<DetailListVo> getPreSaleDetail(OrderListParam param) {
        SelectConditionStep<?> query = orderQuery(param);
        return getPageResult(query, param, DetailListVo.class);
    }

    /**
     * 获取活动订单列表
     */
    public PageResult<OrderListVo> getOrderList(OrderListParam param) {

        SelectConditionStep<?> query = orderQuery(param);
        return getPageResult(query, param, OrderListVo.class);
    }

    /**
     * 查询订单
     */
    private SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Short,
        String, BigDecimal, BigDecimal, BigDecimal>> orderQuery(OrderListParam param) {
        SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp, String, String, Short,
            String, BigDecimal, BigDecimal, BigDecimal>> query = db().select(ORDER_INFO.ORDER_SN,
            ORDER_GOODS.GOODS_NAME, ORDER_GOODS.GOODS_IMG, ORDER_INFO.USER_ID, ORDER_GOODS.ORDER_ID,
            ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE, ORDER_MUST.CONSIGNEE_REAL_NAME, ORDER_INFO.GOODS_AMOUNT,
            ORDER_INFO.ORDER_STATUS_NAME, ORDER_INFO.ORDER_AMOUNT, ORDER_INFO.MONEY_PAID, ORDER_INFO.SHIPPING_FEE)
            .from(ORDER_INFO)
            .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_ID.eq(ORDER_INFO.ORDER_ID))
            .leftJoin(ORDER_MUST).on(ORDER_MUST.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
            .leftJoin(USER).on(USER.USER_ID.eq(ORDER_INFO.USER_ID))
            .where(ORDER_INFO.GOODS_TYPE.likeRegex(OrderInfoService.getGoodsTypeToSearch(new Byte[] {BaseConstant.ACTIVITY_TYPE_PRE_SALE}))
                .and(ORDER_INFO.ACTIVITY_ID.eq(param.getId())));
        buildOptions(query, param);
        query.orderBy(ORDER_INFO.CREATE_TIME.desc());
        return query;
    }

    private void buildOptions(SelectConditionStep<Record13<String, String, String, Integer, Integer, Timestamp,
        String, String, Short, String, BigDecimal, BigDecimal, BigDecimal>> select, OrderListParam param) {
        String goodsName = param.getGoodsName();
        String consigneeName = param.getConsigneeName();
        String mobile = param.getMobile();
        Timestamp createTime = param.getCreateTime();
        String orderStatusName = param.getOrderStatusName();
        String orderSn = param.getOrderSn();
        String username = param.getUsername();
        if (isNotEmpty(orderSn)) {
            select.and(ORDER_INFO.ORDER_SN.like(format("%s%%", orderSn)));
        }
        if (isNotEmpty(mobile)) {
            select.and(ORDER_INFO.MOBILE.like(format("%s%%", mobile)));
        }
        if (isNotEmpty(goodsName)) {
            select.and(ORDER_GOODS.GOODS_NAME.like(format("%%%s%%", goodsName)));
        }
        if (isNotEmpty(consigneeName)) {
            select.and(ORDER_MUST.CONSIGNEE_REAL_NAME.like(format("%s%%", consigneeName)));
        }
        if (null != createTime) {
            select.and(DSL.date(ORDER_INFO.CREATE_TIME).eq(new Date(createTime.getTime())));
        }
        if (isNotEmpty(orderStatusName)) {
            select.and(ORDER_INFO.ORDER_STATUS_NAME.eq(orderStatusName));
        }
        if (isNotEmpty(username)) {
            select.and(USER.USERNAME.like(format("%s%%", username)));
        }
        select.groupBy(ORDER_INFO.ORDER_SN, ORDER_GOODS.GOODS_NAME, ORDER_GOODS.GOODS_IMG,
            ORDER_INFO.USER_ID, ORDER_GOODS.ORDER_ID,
            ORDER_INFO.CREATE_TIME, ORDER_INFO.MOBILE, ORDER_MUST.CONSIGNEE_REAL_NAME,
            ORDER_INFO.GOODS_AMOUNT, ORDER_INFO.ORDER_STATUS_NAME);
    }

    /**
     * 导出订单 Excel
     */
    public Workbook exportOrderList(OrderListParam param, String lang) {
        SelectConditionStep<?> query = orderQuery(param);
        List<OrderExcelVo> orderRows = query.fetchInto(OrderExcelVo.class);
        transformRows(orderRows, lang);
        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
        excelWriter.writeModelList(orderRows, OrderExcelVo.class);
        return workbook;
    }

    /**
     * excel 文本格式化
     */
    private void transformRows(List<OrderExcelVo> orderRows, String lang) {
        orderRows.forEach(row -> {
            // 收货人信息
            row.setConsigneeInfo(row.getConsigneeRealName() + "; " + row.getMobile());
            // 下单时间
            row.setOrderTime(Util.getStandardDate(row.getCreateTime()));
            // 快递费用
            BigDecimal shippingFee = row.getShippingFee();
            String shippingFeeString = "";
            String symbol = RMB.getSymbol();
            if (0 < shippingFee.compareTo(BigDecimal.valueOf(0))) {
                String including = translateIncluding(lang);
                shippingFeeString = format(" (%s: %s%s) ", including, symbol, getCurrencyAmount(shippingFee));
            }
            // 支付金额（含快递费）
            row.setMoney(symbol + getCurrencyAmount(row.getMoneyPaid()) + shippingFeeString);
        });
    }

    /**
     * 国际化 "含运费"
     */
    private String translateIncluding(String lang) {
        return Util.translateMessage(
            lang, JsonResultMessage.INCLUDING_SHIPPING_FEE, "", "excel");
    }
}
