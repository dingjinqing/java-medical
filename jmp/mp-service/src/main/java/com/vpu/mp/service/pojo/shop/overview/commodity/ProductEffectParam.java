package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author:liufei
 * @Date:2019/7/22
 * @Description:
 */
@Data
/*@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)*/
public class ProductEffectParam extends ProductOverviewParam {
    /** 排序字段 */
    public String orderByField;
    /** 排序类型 */
    public String orderByType;
    public int currentPage;
    public int pageRows;
}
