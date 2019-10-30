package com.vpu.mp.service.pojo.shop.goods.es;

import lombok.Data;

import java.util.List;

/**
 * es 查询条件
 * @author 卢光耀
 * @date 2019/10/28 4:16 下午
 *
*/
@Data
public class EsSearchParam {

    /**
     * 聚合条件
     */
    private List<Fact> factList;

}
