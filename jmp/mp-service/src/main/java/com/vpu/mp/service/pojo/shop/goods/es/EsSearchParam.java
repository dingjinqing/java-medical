package com.vpu.mp.service.pojo.shop.goods.es;

import com.vpu.mp.service.foundation.es.annotation.EsSearch;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * es 查询条件(admin)
 * @author 卢光耀
 * @date 2019/10/28 4:16 下午
 *
*/
@Data
public class EsSearchParam {

    private Integer currentPage;

    private Integer pageRows;

    private List<FieldProperty> searchList;
    /**
     * 聚合条件
     */
    private List<Fact> factList;

    private boolean queryByPage;

    private Sort sort;

}
