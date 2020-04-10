package com.vpu.mp.service.pojo.shop.market;

import com.vpu.mp.service.foundation.util.Page;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/7/23 15:38
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MarketOrderListParam{

    /**
     * 营销活动主键
     */
    @NotNull
    protected Integer activityId;

    protected String goodsName;
    protected String orderSn;
    protected Byte[] orderStatus;

    /**
     * 手机号
     */
    protected String mobile;
    /** 收货人姓名 */
    protected String consignee;
    /** 下单时间开始 */
    protected Timestamp createTimeStart;
    /** 下单时间结束 */
    protected Timestamp createTimeEnd;

    /** 国别、省、市、区 */
    protected Integer countryCode;
    protected Integer provinceCode;
    protected Integer cityCode;
    protected Integer districtCode;

    /**
     * 	分页信息
     */
    protected Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    protected Integer pageRows = Page.DEFAULT_PAGE_ROWS;

    /**
     * 前端查询订单状态与OrderConstant的订单状态不对应，需要处理一下
     */
    public void initOrderStatus(){
        if(orderStatus.length == 1 && orderStatus[0] == 8){
            //售后完成
            orderStatus = new Byte[]{8, 10};
        }else if(orderStatus.length == 1 && (orderStatus[0] == 9 || orderStatus[0] == 10 || orderStatus[0] == 11)){
            //同城配送的状态，先过滤掉
            orderStatus= new Byte[]{127};
        }else if(orderStatus.length == 1 && orderStatus[0] == 7){
            //售后中
            orderStatus = new Byte[]{7, 9};
        }
    }
}
