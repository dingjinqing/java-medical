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
public class SpecVal {
    private Integer specValId;
    private Integer specId;
    private Integer goodsId;
    private String specValName;

    /**
     * 规格值集合转换为对应的Map
     * @param specVals 代转换规格名集合
     * @return Map,key是名字，value是实体类
     */
    public static Map<String,SpecVal> mapNameToSpecVal(List<SpecVal> specVals){
        Map<String, SpecVal> collect = specVals.stream().filter(specVal -> StrUtil.isNotBlank(specVal.getSpecValName()))
            .collect(Collectors.toMap(SpecVal::getSpecValName, Function.identity(), (x1, x2) -> x1));

        return collect;
    }
}
