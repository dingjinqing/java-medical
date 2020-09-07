package com.vpu.mp.service.pojo.shop.anchor;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2020/9/4 17:48
 */
@Data
public class AnchorPointsListParam  extends BasePageParam {

    /**
     * 埋点事件
     */
    private  String event;
    /**
     * 页面地址
     */
    private String page;
    /**
     * 平台
     */
    private String platform;
    /**
     * 终端
     */
    private String device;
    /**
     * 门店
     */
    private Integer storeId;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 锚点类型
     */
    private  String key;
    /**
     * 值
     */
    private String value;

}
