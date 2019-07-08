package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.applets.page.PageClassificationIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description 小程序管理-页面分类
 * @author lixinguo,liufei
 * @date 2019-07-03
 */
@RestController
public class AdminPageClassificationController extends AdminBaseController {
    private Logger logger = LoggerFactory.getLogger(AdminPageClassificationController.class);

    /**
     * 添加页面分类
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/add")
    public JsonResult insert(@RequestBody PageClassificationIn pageIn){
        if(shop().pageClassification.isRowExist(pageIn.getPageName())){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_EXIST);
        }
        return shop().pageClassification.addRow(pageIn.getPageName()) > 0 ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_INSERT_FAILED);
    }

    /**
     * 修改页面分类名称，id码值不变
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/updateCategoryName")
    public JsonResult updateCategoryName(@RequestBody PageClassificationIn pageIn){
        if(!shop().pageClassification.isRowExist(pageIn.getPageName())){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_NOT_EXIST);
        }
        return shop().pageClassification.setName(pageIn.getPageId(),pageIn.getPageName()) > 0 ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_UPDATE_FAILED);
    }

    /**
     * 根据ID删除分类，同时将该分类下的页面重新归属于未分类类别，码值为0
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/deleteCategoryById")
    public JsonResult deleteCategoryById(@RequestBody PageClassificationIn pageIn){
        if(!shop().pageClassification.isRowExist(pageIn.getPageName())){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_NOT_EXIST);
        }
        return shop().pageClassification.rmAndResetCategory(pageIn.getPageId()) ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_DELETE_FAILED);
    }
}
