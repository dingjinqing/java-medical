package com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

/**
 * @author 王帅
 * @date 2020年03月26日
 */
@Data
@ExcelSheet
public class BatchShipFailModel {
    /** 订单号 */
    @ExcelColumn(columnIndex = 0, columnName = "order.batch.ship.orderSn")
    private String orderSn;
    /** 快递公司 */
    @ExcelColumn(columnIndex = 1, columnName = "order.batch.ship.express")
    private String shippingName;
    /** 快递单号 */
    @ExcelColumn(columnIndex = 2, columnName = "order.batch.ship.trackingNo")
    private String shippingNo;
    @ExcelColumn(columnIndex = 3, columnName = "order.batch.ship.failReason")
    private String  failReason;
}
