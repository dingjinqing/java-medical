package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author liufei
 * @date 10/18/19
 */
@Data
public class StoreInfoVo {
    private Integer storeId;
    private String storeName;
    private String manager;
    private String mobile;
    private String storeImgs;
    private Byte businessState;
    private Byte businessType;
    private String openingTime;
    private String closeTime;
    private String provinceCode;
    private String cityCode;
    private String districtCode;
    private String latitude;
    private String longitude;
    private String address;
    private Integer group;
    private String service;
    private String content;
    private Integer posShopId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Short autoPick;
    private Byte delFlag;
    /**
     * The Scan buy.是否支持扫码购.1是,0否
     */
    public Byte scanBuy;

    /**
     * The Distance.门店距离 单位KM
     */
    public Double distance;

    public List<StoreServiceListQueryVo> allService;

    public Map<StoreServiceCategoryListQueryVo, List<StoreServiceListQueryVo>> serviceCat;

}
