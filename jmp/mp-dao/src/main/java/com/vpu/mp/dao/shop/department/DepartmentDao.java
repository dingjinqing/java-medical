package com.vpu.mp.dao.shop.department;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DepartmentRecord;
import com.vpu.mp.service.pojo.shop.department.*;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.DEPARTMENT;

/**
 * @author chenjie
 */
@Repository
public class DepartmentDao extends ShopBaseDao {
    public static final Integer ROOT_ID = 0;

    /**
     * 科室列表
     *
     * @param param
     * @return
     */
    public PageResult<DepartmentListVo> getDepartmentList(DepartmentListParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select(DEPARTMENT.ID, DEPARTMENT.CODE, DEPARTMENT.CREATE_TIME,
                DEPARTMENT.NAME, DEPARTMENT.PARENT_ID, DEPARTMENT.PARENT_IDS,DEPARTMENT.LEVEL,DEPARTMENT.IS_LEAF)
            .from(DEPARTMENT);
        select.where(DEPARTMENT.IS_DELETE.eq((byte) 0)).and(DEPARTMENT.LEVEL.eq((Integer) 1));
        buildOptions(select, param);
        select.orderBy(DEPARTMENT.ID.desc());
        PageResult<DepartmentListVo> departmentList = this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), DepartmentListVo.class);
        return departmentList;
    }

    /**
     * 科室搜索查询
     *
     * @param select
     * @param param
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, DepartmentListParam param) {
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取一条科室的信息
     *
     * @param departmentId
     * @return
     */
    public DepartmentOneParam getOneInfo(Integer departmentId) {
        DepartmentOneParam info = db().select().from(DEPARTMENT).where(DEPARTMENT.ID.eq(departmentId))
            .fetchOneInto(DepartmentOneParam.class);
        return info;
    }

    /**
     * 编辑保存
     *
     * @param param
     * @return
     */
    public int updateDepartment(DepartmentOneParam param) {
        DepartmentRecord record = new DepartmentRecord();
        FieldsUtil.assign(param, record);
        return db().executeUpdate(record);
    }

    /**
     * 添加测评活动信息
     *
     * @param param
     * @return
     */
    public void insertDepartment(DepartmentOneParam param) {
        DepartmentRecord record = db().newRecord(DEPARTMENT);
        FieldsUtil.assign(param, record);
        record.insert();
        param.setId(record.getId());
    }

    /**
     * 删除
     *
     * @param departmentId
     * @return
     */
    public int deleteDepartment(Integer departmentId) {
        int res = db().update(DEPARTMENT).set(DEPARTMENT.IS_DELETE, (byte) 1).where(DEPARTMENT.ID.eq(departmentId))
            .execute();
        return res;
    }

    /**
     *  更新科室叶子节点状态
     *
     * @param departmentId
     * @param isLeaf 1叶子节点 0非叶子节点
     * @return
     */
    public int updateDepartmentIsLeaf(Integer departmentId,Byte isLeaf) {
        int res = db().update(DEPARTMENT).set(DEPARTMENT.IS_LEAF, isLeaf)
            .where(DEPARTMENT.ID.eq(departmentId)).execute();
        return res;
    }

    /**
     * 获取科室列表By父节点
     *
     * @param departmentId
     * @return
     */
    public List<DepartmentListVo> listDepartmentByParentId(Integer departmentId) {

        if (ROOT_ID.equals(departmentId)) {
            List<DepartmentListVo> departmentList = db().select().from(DEPARTMENT).where(DEPARTMENT.LEVEL.eq(1).and(DEPARTMENT.IS_DELETE.eq((byte)0)))
                .fetchInto(DepartmentListVo.class);
            return departmentList;
        } else {
            List<DepartmentListVo> departmentList = db().select().from(DEPARTMENT).where(DEPARTMENT.PARENT_ID.eq(departmentId).and(DEPARTMENT.IS_DELETE.eq((byte)0)))
                .fetchInto(DepartmentListVo.class);
            return departmentList;
        }
    }

    /**
     * 科室子节点数目
     *
     * @param departmentId
     * @return
     */
    public int countDepartment(Integer departmentId) {
        int departmentNum = db().selectCount().from(DEPARTMENT).where(DEPARTMENT.PARENT_ID.eq(departmentId).and(DEPARTMENT.IS_DELETE.eq((byte)0))).fetchOneInto(int.class);
        return departmentNum;
    }

    /**
     * 科室是否存在，用来新增检查
     * @param departmentId 科室ID
     * @param name 科室名称
     * @return true 存在 false 不存在
     */
    public boolean isNameExist(Integer departmentId,String name) {
        Condition condition = DEPARTMENT.NAME.eq(name);
        if (departmentId != null) {
            condition = condition.and(DEPARTMENT.ID.ne(departmentId));
        }
        int count = db().fetchCount(DEPARTMENT, condition);
        return count>0;
    }

    /**
     *
     * @return
     */
    public List<DepartmentOneParam> getListByIds(List<Integer> departmentIds) {
        return db().select(DEPARTMENT.ID, DEPARTMENT.NAME).from(DEPARTMENT).where(DEPARTMENT.ID.in(departmentIds).and(DEPARTMENT.IS_DELETE.eq((byte) 0))).fetchInto(DepartmentOneParam.class);
    }

    public List<Integer> getDepartmentIdsByName(String name) {
        return db().select(DEPARTMENT.ID).from(DEPARTMENT).where(DEPARTMENT.IS_DELETE.eq((byte) 0).and(DEPARTMENT.NAME.like(likeValue(name)))).fetchInto(Integer.class);
    }

    /**
     * 获取一条科室的信息(根据code)
     *
     * @param code
     * @return
     */
    public DepartmentOneParam getDepartmentByCode(String code) {
        return db().select().from(DEPARTMENT).where(DEPARTMENT.CODE.eq(code))
            .fetchOneInto(DepartmentOneParam.class);
    }

    /**
     * 科室子节点
     *
     * @param departmentId
     * @return
     */
    public List<DepartmentOneParam> getChildDepartment(Integer departmentId) {
        List<DepartmentOneParam> departments = db().select().from(DEPARTMENT).where(DEPARTMENT.PARENT_ID.eq(departmentId)).fetchInto(DepartmentOneParam.class);
        return departments;
    }

    public void updateDepartmentLevel(Integer id, Integer level) {
        db().update(DEPARTMENT)
            .set(DEPARTMENT.LEVEL, level)
            .where(DEPARTMENT.ID.eq(id))
            .execute();
    }

    /**
     * 获取子节点科室信息
     * @param name
     * @return
     */
    public List<DepartmentOneParam> listDepartmentsByName(String name) {
        Condition condition = DEPARTMENT.IS_DELETE.eq((byte) 0).and(DEPARTMENT.IS_LEAF.eq(DepartmentConstant.LEAF));
        if (name != null && name != "") {
            condition = condition.and(DEPARTMENT.NAME.like(likeValue(name)));
        }
        return db().select().from(DEPARTMENT).where(condition).fetchInto(DepartmentOneParam.class);
    }

    /**
     * 根据id集合查询对应信息
     * @param departmentIds id集合
     * @return 科室信息集合
     */
    public List<DepartmentSimpleVo> listDepartmentInfo(List<Integer> departmentIds) {
        return db().select(DEPARTMENT.ID,DEPARTMENT.NAME).from(DEPARTMENT).where(DEPARTMENT.ID.in(departmentIds).and(DEPARTMENT.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(DepartmentSimpleVo.class);
    }

    /**
     * 获取子节点科室信息
     * @param param
     * @return
     */
    public List<DepartmentOneParam> listDepartmentsByOptions(DepartmentListParam param) {
        Condition condition = DEPARTMENT.IS_DELETE.eq((byte) 0).and(DEPARTMENT.IS_LEAF.eq(DepartmentConstant.LEAF));
        if (param.getKeyword() != null && param.getKeyword() != "") {
            condition = condition.and(DEPARTMENT.NAME.like(likeValue(param.getKeyword())));
        }
        if (param.getDepartmentIds() != null) {
            condition = condition.and(DEPARTMENT.ID.in(param.getDepartmentIds()));
        }
        return db().select().from(DEPARTMENT).where(condition).fetchInto(DepartmentOneParam.class);
    }

}
