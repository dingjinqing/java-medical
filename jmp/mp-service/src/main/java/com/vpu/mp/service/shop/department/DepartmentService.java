package com.vpu.mp.service.shop.department;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        transaction(() -> {
            //是二级分类
            int level = updateParentIsLeaf(param);
            param.setLevel(level);
            int departmentId = departmentDao.insertDepartment(param);
            param.setId(departmentId);
        });
        return param.getId();
    }

    public Integer updateDepartment(DepartmentOneParam param,Integer oldParentId) {
        transaction(() -> {
            int level = updateParentIsLeaf(param);
            param.setLevel(level);
            departmentDao.updateDepartment(param);
            if (oldParentId != null && oldParentId != 0) {
                int i = departmentDao.countDepartment(oldParentId);
                if (i == 0) {
                    departmentDao.updateDepartmentIsLeaf(oldParentId,(byte) 1);
                }
            }
        });
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
                List<DepartmentListVo> listTemp = new ArrayList<>();
                listTemp.add(list);
                list.setChildDepartmentList(listTemp);
            } else {
                List<DepartmentListVo> departmentList = departmentDao.listDepartmentByParentId(list.getId());
                list.setChildDepartmentList(departmentList);
            }
        }
        return listDepartmentTree;
    }
}
