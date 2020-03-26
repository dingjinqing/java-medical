package com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 商品Excel导入操作记录查询参数
 * @author 李晓冰
 * @date 2020年03月25日
 */
@Data
public class GoodsVpuImportListParam extends BasePageParam {
    private Integer batchId;
    private Timestamp beginTime;
    private Timestamp endTime;
}
