package com.vpu.mp.service.pojo.wxapp.order.inquiry.vo;

import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRefundListDo;
import lombok.Data;

import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/7/27
 **/
@Data
public class InquiryOrderDetailVo extends InquiryOrderDo {
    private String userName;
    private String userMobile;
    private Integer patientAge;
    private List<String> imgUrlList;
    private List<InquiryOrderRefundListDo> refundList;
}
