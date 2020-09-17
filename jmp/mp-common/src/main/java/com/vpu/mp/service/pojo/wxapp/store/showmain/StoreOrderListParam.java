package com.vpu.mp.service.pojo.wxapp.store.showmain;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/9/17
 **/
@Data
public class StoreOrderListParam extends BasePageParam {
    /**
     * 待处理 已完成
     */
    private Byte status;
    private Integer userId;
}
