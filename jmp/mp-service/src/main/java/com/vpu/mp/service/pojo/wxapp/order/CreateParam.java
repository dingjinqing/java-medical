package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 下单参数
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class CreateParam extends OrderBeforeParam{
    /**发票id*/
    private Integer invoiceId;
    /**买家留言*/
    private String message;
    /**实付金额*/
    private BigDecimal orderAmount;
    /**自提日期*/
    private String pickupDate;
    /**自提时间*/
    private String pickupTime;
    /**trueName 欧派真实姓名*/
    private String trueName;
    /**idCard 欧派身份证号码*/
    private String idCard;
    /**pos扫码购门店ID*/
    private Integer scanStoreId;
    /**必填信息*/
    private OrderMustParam must;
    public void intoRecord(OrderInfoRecord orderRecord){
        //user
        orderRecord.setUserId(getWxUserInfo().getUserId());
        orderRecord.setUserOpenid(getWxUserInfo().getWxUser().getOpenId());
        orderRecord.setAddMessage(getMessage());
        orderRecord.setDeliverType(getDeliverType());
        orderRecord.setPickupdateTime(getPickupDate() + " " + getPickupTime());
        orderRecord.setTrueName(getTrueName());
        orderRecord.setIdCard(getIdCard());
        //扫码购
        if(getScanStoreId() != null && getScanStoreId() > 0){
            orderRecord.setPosFlag(OrderConstant.yes);
        }
    }
}
