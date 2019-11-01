package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 *  活动处理器待处理的信息(商品或规格)基类
 *  @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public abstract class AbstractCapsule {
    private Integer capsuleId;
    private String capsuleName;

    /** 商品已被哪些processor处理过（商品列表里面将处理的营销码值存入） */
    private Set<Byte> processedTypes = new HashSet<>();
}
