package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.department.DepartmentListParam;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;

import java.util.*;
import com.vpu.mp.service.shop.ShopApplication;

public class AdminDepartmentController extends AdminBaseController{
    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }
    /**
     * 科室列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctor/department/list")
    public JsonResult departmentList(@RequestBody DepartmentListParam param) {
        PageResult<DepartmentListVo> departmentList = shop().departmentService.getDepartmentList(param);
        return this.success(departmentList);
    }

    /**
     * 科室列表根据父节点
     * @param parentId
     * @return
     */
    @PostMapping("/api/admin/doctor/department/child/list")
    public JsonResult departmentListByParentId(@RequestBody Integer parentId) {
        List<DepartmentListVo> departmentList = shop().departmentService.listDepartmentsByParentId(parentId);
        return this.success(departmentList);
    }

//    /**
//     * 科室选择框下拉列表（非树形数据，仅返回一级数据）
//     * @return param {@link DepartmentListVo}
//     */
//    @GetMapping("/api/admin/doctor/department/select/list")
//    public JsonResult getSelectList(){
//        return success(shop().departmentService.listDepartmentsByParentId(0));
//    }

    /**
     *  科室新增
     * @param param {@link DepartmentOneParam}
     */
    @PostMapping("/api/admin/doctor/department/add")
    public JsonResult insert(@RequestBody DepartmentOneParam param) {
        if (param.getName()==null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_IS_NULL);
        }

        boolean isExist = shop().departmentService.isNameExist(null,param.getName());
        if (isExist) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_EXIST);
        }

        shop().departmentService.insertDepartment(param);

        return success();
    }

    /**
     * 普通分类修改
     * @param param {@link DepartmentOneParam}
     */
    @PostMapping("/api/admin/doctor/department/update")
    public JsonResult update(@RequestBody DepartmentOneParam param,Integer oldParentId) {
        if (param.getId() == null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_ID_IS_NULL);
        }
        boolean isExist = shop().departmentService.isNameExist(param.getId(),param.getName());
        if (isExist) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_EXIST);
        }
        shop().departmentService.updateDepartment(param,oldParentId);
        return success();
    }

    /**
     * 根据id获取普通商家分类
     * @param departmentId 普通商家分类id
     */
    @GetMapping("/api/admin/goods/doctor/department/{departmentId}")
    public JsonResult getSort(Integer departmentId) {
        if (departmentId == null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_ID_IS_NULL);
        }
        return success(shop().departmentService.getOneInfo(departmentId));
    }

    /**
     * 删除商家分类
     * @param departmentId 分类id
     */
    @GetMapping("/api/admin/goods/sort/delete/{departmentId}")
    public JsonResult delete(Integer departmentId) {
        if (departmentId == null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_ID_IS_NULL);
        }
        shop().departmentService.delete(departmentId);
        return success();
    }

//    /**
//     * 判断字符串集合内是否存在重复数据
//     * @param sortNames 字符串集合
//     * @return true 存在， false 不存在
//     */
//    private boolean isSortNamesRepeat(List<String> sortNames){
//        Set<String> set = new HashSet<>(sortNames);
//        return set.size()!=sortNames.size();
//    }
}
