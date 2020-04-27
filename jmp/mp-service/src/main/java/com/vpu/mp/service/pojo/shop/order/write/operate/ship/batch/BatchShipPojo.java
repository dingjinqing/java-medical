package com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 批量发货对象
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class BatchShipPojo {
    /** 订单号 */
    @ExcelColumn(columnIndex = 0, columnName = "order.batch.ship.orderSn")
    @ExcelColumnNotNull
    private String orderSn;

    /** 快递公司 */
    @ExcelColumn(columnIndex = 1, columnName = "order.batch.ship.express")
    private String express;

    /** 快递单号 */
    @ExcelColumn(columnIndex = 2, columnName = "order.batch.ship.trackingNo")
    private String trackingNo;
}
