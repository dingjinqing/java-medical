package com.vpu.mp.service.pojo.shop.sku.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class Spec {
    private Integer  specId;
    private Integer goodsId;
    private String specName;

    /**
     * 规格值
     */
    List<SpecVal> specVals;

    /**
     * 规格名集合转换为对应的Map
     * @param specs 代转换规格名集合
     * @return Map,key是名字，value是实体类
     */
    public static Map<String,Spec> mapNameToSpec(List<Spec> specs){
        Map<String, Spec> collect = specs.stream().filter(spec -> StrUtil.isNotBlank(spec.getSpecName()))
            .collect(Collectors.toMap(Spec::getSpecName, Function.identity(), (x1, x2) -> x1));

        return collect;
    }
}
