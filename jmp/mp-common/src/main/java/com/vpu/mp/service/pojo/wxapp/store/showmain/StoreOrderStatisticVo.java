package com.vpu.mp.service.pojo.wxapp.store.showmain;

import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/9/16
 **/
@Data
public class StoreOrderStatisticVo {
    private Integer storeId;
    private String storeName;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 维度
     */
    private String longitude;
    /**
     * 省
     */
    private String provinceCode;
    /**
     * 市
     */
    private String cityCode;
    /**
     * 区
     */
    private String districtCode;
    /**
     * 营业状态
     */
    private Byte businessState;
    /**
     * 待接订单数量
     */
    private Integer waitHandleOrderNum;

}
