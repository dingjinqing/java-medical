package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import lombok.Data;

/**
 *  活动处理器待处理的信息(商品或规格)基类
 *  @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public abstract class AbstractCapsule {
    private Integer capsuleId;
    private String capsuleName;
}
