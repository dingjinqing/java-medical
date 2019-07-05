package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author luguangyao
 *
 */
@RestController
public class AdminStoreController extends AdminBaseController{
    /**
     * 门店分类-列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/list")
    public JsonResult getStoreGroupByPage(@RequestBody(required = false) StoreGroupQueryParam param,
                                          HttpServletRequest request) {
        PageResult<StoreGroup> storeGroupPageResult = shop().store.getStoreGroupPageList(param);
        return success(storeGroupPageResult);
    }
}
