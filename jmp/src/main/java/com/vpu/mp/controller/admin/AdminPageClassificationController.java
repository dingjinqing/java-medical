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

    @PostMapping("/api/admin/applets/pageclassification/safeAdd")
    public JsonResult safeInsert(@RequestBody PageClassificationIn pageIn){
        boolean[] result = shop().pageClassification.checkExistThenInsert(pageIn.getPageName());
        return result[0] ? fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_EXIST) : result[1] ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_INSERT_FAILED);
    }

    @PostMapping("/api/admin/applets/pageclassification/add")
    public JsonResult insert(@RequestBody PageClassificationIn pageIn){
        if(shop().pageClassification.isRowExist(pageIn.getPageName())){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_EXIST);
        }
        return shop().pageClassification.addRow(pageIn.getPageName()) > 0 ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_INSERT_FAILED);
    }
}
