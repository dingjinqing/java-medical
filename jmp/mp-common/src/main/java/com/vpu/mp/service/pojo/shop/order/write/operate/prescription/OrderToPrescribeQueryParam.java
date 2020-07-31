package com.vpu.mp.service.pojo.shop.order.write.operate.prescription;

import com.vpu.mp.common.foundation.util.Page;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Data
public class OrderToPrescribeQueryParam extends AbstractOrderOperateQueryParam {
    private Integer orderId;
    private Byte auditType = OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE;
    private Byte auditStatus =OrderConstant.MEDICAL_AUDIT_DEFAULT;

    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;

    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
