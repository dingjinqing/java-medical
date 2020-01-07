package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.asset.AssetDetailParam;
import com.vpu.mp.service.pojo.shop.overview.asset.RevenueProfileParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author liufei
 * @date 2019/8/2
 * @description 资产管理
 */
@RestController
public class AdminAssetManagementController extends AdminBaseController {
    /**
     * 营收概况
     */
    @PostMapping("/api/admin/assetmanagement/revenueprofile")
    public JsonResult revenueprofile(@RequestBody RevenueProfileParam param) {
        if (param.getTradeContent()==0) {
            return success(shop().assetService.revenueprofile(param));
        }
        if (param.getTradeContent()==1){
            return success(shop().assetService.revenueprofileScore(param));
        }
        return success();
    }

    /**
     * 查看明细
     */
    @PostMapping("/api/admin/assetmanagement/assetManageDetail")
    public JsonResult assetManageDetail(@RequestBody AssetDetailParam param) {
        return success(shop().assetService.assetManageDetail(param));
    }

    /**
     * 资产管理明细导出excel
     */
    @PostMapping("/api/admin/assetmanagement/export2Excel")
    public void export2Excel(@RequestBody @Validated AssetDetailParam param, HttpServletResponse response) {
        try (Workbook workbook = shop().assetService.export2Excel(param)) {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String fileName = "资产管理明细" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setLocale(Locale.ENGLISH);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
