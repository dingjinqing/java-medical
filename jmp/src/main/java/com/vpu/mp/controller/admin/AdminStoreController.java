package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
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
     * 门店分组-列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/list")
    public JsonResult getStoreGroupByPage(@RequestBody StoreGroupQueryParam param) {
        PageResult<StoreGroup> storeGroupPageResult = shop().store.getStoreGroupPageList(param);
        return success(storeGroupPageResult);
    }

    /**
     * 门店分组-新增
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/add")
    public JsonResult addStoreGroup(@RequestBody StoreGroupQueryParam param) {
        boolean isExist = shop().store.isStoreGroupExist(param);
        if ( isExist ){
            int count = shop().store.insertStoreGroup(param);
            if (count > 0){
                return success();
            }else{
                return fail();
            }
        }else{
            return fail(JsonResultCode.CODE_STORE_GROUP_NAME_EXIST);
        }
    }
    /**
     * 门店分组-修改
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/update")
    public JsonResult updateStoreGroup(@RequestBody StoreGroupQueryParam param) {
        boolean isExist = shop().store.isStoreGroupExist(param);
        if ( isExist ){
            int count = shop().store.updateStoreGroup(param);
            if (count > 0){
                return success();
            }else{
                return fail();
            }
        }else{
            return fail(JsonResultCode.CODE_STORE_GROUP_NAME_EXIST);
        }
    }
    /**
     * 门店分组-删除
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/del")
    public JsonResult delStoreGroup(@RequestBody StoreGroupQueryParam param) {
        shop().store.deleteStoreGroup(param);
        return success();

    }
}
