package com.vpu.mp.service.pojo.shop.market.freeshipping;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/31 10:23
 */
@Data
public class FreeShipQueryParam {


    private Page page;

    private  Integer id;
    /**
     * 活动状态
     */
    private Integer  navType;
}
