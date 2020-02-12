package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 2/11/2020
 */
@Data
@Builder
public class TableData {
    List<String> refDate;
    List<String> goodsName;
    List<List<Object>> arrayData;
}
