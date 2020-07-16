package com.vpu.mp.service.shop.department;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.vpu.mp.common.foundation.util.Util;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class DepartmentService extends ShopBaseService {
    @Autowired
    protected DepartmentDao departmentDao;
    public static final int ZERO = 0;

    public PageResult<DepartmentListVo> getDepartmentList(DepartmentListParam param) {
        PageResult<DepartmentListVo> departmentList = departmentDao.getDepartmentList(param);
        return departmentList;
    }

    public List<DepartmentListVo> listDepartmentsByParentId(Integer departmentId){
        List<DepartmentListVo> departmentList = departmentDao.listDepartmentByParentId(departmentId);
        return departmentList;
    }

    public boolean isNameExist(Integer departmentId,String name) {
        boolean flag = departmentDao.isNameExist(departmentId,name);
        return flag;
    }

    public Integer insertDepartment(DepartmentOneParam param) {
        int level = updateParentIsLeaf(param);
        param.setLevel(level);
        departmentDao.insertDepartment(param);
        return param.getId();
    }

    public Integer updateDepartment(DepartmentOneParam param,Integer oldParentId) {
        int level = updateParentIsLeaf(param);
        param.setLevel(level);
        departmentDao.updateDepartment(param);
        if (oldParentId != null && oldParentId != 0) {
            int i = departmentDao.countDepartment(oldParentId);
            if (i == 0) {
                departmentDao.updateDepartmentIsLeaf(oldParentId,(byte) 1);
            }
        }
        updateDepartmentLevel(param);
        return param.getId();
    }

    public DepartmentOneParam getOneInfo(Integer departmentId){
        DepartmentOneParam departmentInfo = departmentDao.getOneInfo(departmentId);
        return departmentInfo;
    }

    public int delete(Integer departmentId){
        int id = departmentDao.deleteDepartment(departmentId);
        return id;
    }

    /**
     * 更新父节点isLeaf并返回
     * @param param
     * @return
     */
    public void updateDepartmentLevel(DepartmentOneParam param){
        departmentDao.updateDepartmentLevel(param.getId(),param.getLevel());
        List<DepartmentOneParam> departments = departmentDao.getChildDepartment(param.getId());
        for(DepartmentOneParam department: departments) {
            department.setLevel(param.getLevel()+1);
            updateDepartmentLevel(department);
        }
    }

    /**
     * 更新父节点isLeaf并返回
     * @param param
     * @return
     */
    public int updateParentIsLeaf(DepartmentOneParam param){
        int level;
        if (param.getParentId() != null && param.getParentId() != 0) {
            departmentDao.updateDepartmentIsLeaf(param.getParentId(),(byte) 0);
            DepartmentOneParam parentDepartment = departmentDao.getOneInfo(param.getParentId());
            level = parentDepartment.getLevel()+1;
        } else {
            level = DepartmentConstant.ROOT_LEVEL;
        }
        return level;
    }

    /**
     *
     * @return
     */
    public List<DepartmentOneParam> getListByIds(List<Integer> departmentIds) {
        return departmentDao.getListByIds(departmentIds);
    }

    public List<DepartmentListVo> listDepartmentTree() {
        List<DepartmentListVo> listDepartmentTree = departmentDao.listDepartmentByParentId(0);
        for (DepartmentListVo list : listDepartmentTree) {
            if (DepartmentConstant.LEAF.equals(list.getIsLeaf())) {
                list.setChildDepartmentList(listDepartmentListVo(list));
            } else {
                List<DepartmentListVo> departmentList = departmentDao.listDepartmentByParentId(list.getId());
                list.setChildDepartmentList(departmentList);
            }
        }
        return listDepartmentTree;
    }

    public List<DepartmentListVo> listDepartmentListVo(DepartmentListVo department){
        List<DepartmentListVo> listTemp = new ArrayList<>();
        DepartmentListVo listSingle = new DepartmentListVo();
        listSingle.setId(department.getId());
        listSingle.setCode(department.getCode());
        listSingle.setName(department.getName());
        listSingle.setIsLeaf(department.getIsLeaf());
        listSingle.setLevel(department.getLevel());
        listSingle.setParentId(department.getParentId());
        listTemp.add(listSingle);
        return listTemp;
    }

    public DepartmentOneParam getDepartmentByCode(String code) {
        return departmentDao.getDepartmentByCode(code);
    }

    /**
     * 更新/新增科室
     * @param department
     */
    public void synchroDepartment(DepartmentOneParam department) {
        if(getDepartmentByCode(department.getCode()) == null) {
            insertDepartment(department);
        } else {
            DepartmentOneParam oldDepartment = getDepartmentByCode(department.getCode());
            department.setId(oldDepartment.getId());
            updateDepartment(department,oldDepartment.getParentId());
        }
    }

    public void fetchDepartments(String json) {
        List<DepartmentFetchOneParam> DepartmentFetchOneParam = Util.parseJson(json, new TypeReference<List<DepartmentFetchOneParam>>() {
        });
        for (DepartmentFetchOneParam list : DepartmentFetchOneParam) {
            DepartmentOneParam department = new DepartmentOneParam();
            department.setName(list.getDepartName());
            department.setCode(list.getDepartCode());
            if(list.getPid() == "-1") {
                department.setParentId(0);
            } else {
                department.setParentId(getDepartmentIdNew(list.getPid()));
            }
            if (list.getState() > 1) department.setIsDelete((byte) 1);
            synchroDepartment(department);
        }
    }

    public Integer getDepartmentIdNew(String code) {
        DepartmentOneParam departmentInfo = getDepartmentByCode(code);
        if(departmentInfo == null) {
            DepartmentOneParam departmentTemp = new DepartmentOneParam();
            departmentTemp.setCode(code);
            insertDepartment(departmentTemp);
            return departmentTemp.getId();
        } else {
            return departmentInfo.getId();
        }
    }
}
