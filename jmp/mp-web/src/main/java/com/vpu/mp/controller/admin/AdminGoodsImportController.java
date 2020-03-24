package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportParam;
import com.vpu.mp.service.pojo.shop.image.DownloadImageBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 李晓冰
 * @date 2020年03月19日
 */
@RestController
@Slf4j
public class AdminGoodsImportController extends AdminBaseController{

    @RequestMapping("/api/admin/goods/vpu/excel/import")
    public JsonResult goodsVpuExcelImport(GoodsVpuExcelImportParam param){
        MultipartFile file = param.getFile();

        if (file == null) {
            return fail();
        }

        logger().debug("微铺宝excel商品导入："+file.getName());
        ExcelTypeEnum excelTypeEnum = ExcelUtil.checkFile(file);
        if (excelTypeEnum == null) {
            logger().debug("导入格式错误!");
            return fail(JsonResultCode.CODE_EXCEL_ERRO);
        }
        param.setLang(getLang());
        param.setExcelTypeEnum(excelTypeEnum);

        JsonResultCode jsonResultCode = shop().goodsImportService.goodsVpuExcelImport(param);
        if (JsonResultCode.CODE_SUCCESS.equals(jsonResultCode)) {
            return success();
        } else {
            return fail(jsonResultCode);
        }
    }

    @RequestMapping("/api/admin/goods/vpu/excel/text")
    public JsonResult test(@RequestBody GoodsVpuExcelImportParam param) throws IOException {
        DownloadImageBo downloadImageBo = shop().image.downloadImgAndUpload(param.getUrl());
        System.out.println(Util.toJson(downloadImageBo));
        return success(downloadImageBo);
    }
}
