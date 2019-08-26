package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 8/26/2019
 * @description
 */
@Data
@ExcelSheet
public class RedemptionOrderExportVo {
    /**
     * 订单号
     */
    @ExcelColumn(columnIndex = 0, columnName = JsonResultMessage.REDEMPTION_ORDER_SN)
    private String orderSn;

    /**
     * 辅助参数
     */
    @JsonIgnore
    @ExcelIgnore
    private String concatId;
    @JsonIgnore
    @ExcelIgnore
    private String concatName;
    @JsonIgnore
    @ExcelIgnore
    private String concatNumber;
    @JsonIgnore
    @ExcelIgnore
    private String activityIds;
    @JsonIgnore
    @ExcelIgnore
    private String activityRules;
    @JsonIgnore
    @ExcelIgnore
    private String concatImg;

    /**
     * 主商品列表-导出excel时的形式为json字符串
     */
    @ExcelColumn(columnIndex = 1, columnName = JsonResultMessage.REDEMPTION_MAIN_GOODS)
    private String mainGoodsString;
    /**
     * 换购商品列表-导出excel时的形式为json字符串
     * jsons对象类型为：{@code List<RedemptionGoodsInfo>}
     */
    @ExcelColumn(columnIndex = 2, columnName = JsonResultMessage.REDEMPTION_REDEMPTION_GOODS)
    private String redemptionGoodsString;

    /**
     * 下单时间
     */
    @ExcelColumn(columnIndex = 3, columnName = JsonResultMessage.REDEMPTION_CREATE_TIME)
    private Timestamp createTime;
    /**
     * 收货人信息 姓名，手机号
     */
    @ExcelIgnore
    private String consignee;
    @ExcelIgnore
    private String mobile;
    /**
     * 收货人信息-导出excel时的形式为字符串：姓名---手机号
     */
    @ExcelColumn(columnIndex = 4, columnName = JsonResultMessage.REDEMPTION_RECEIVER_INFO)
    private String receiverInfo;
    /**
     * 订单状态
     */
    @ExcelColumn(columnIndex = 5, columnName = JsonResultMessage.REDEMPTION_ORDER_STATUS)
    private String orderStatusName;

}
