package com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu;

import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微铺宝商品excel模板导入
 * @author 李晓冰
 * @date 2020年03月18日
 */
@Data
public class GoodsVpuExcelImportParam {
    /**excel文件*/
    private MultipartFile file;
    /**是否是更新操作*/
    private Boolean isUpdate;

    /**excel类型，controller层设置*/
    private ExcelTypeEnum excelTypeEnum;
    /**语言类型，controller层设置*/
    private String lang;
}
