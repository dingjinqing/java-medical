package com.vpu.mp.service.pojo.wxapp.store.showmain;

import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/9/16
 **/
@Data
public class StoreMonthStatisticVo {
    /**
     * 待处理数量
     */
    private Integer waitHandleNum;
    /**
     * 已完成数量
     */
    private Integer finishedNum;
}
