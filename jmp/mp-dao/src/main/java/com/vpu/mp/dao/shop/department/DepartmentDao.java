package com.vpu.mp.dao.shop.department;

import static com.vpu.mp.db.shop.Tables.DEPARTMENT;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.Department;
import com.vpu.mp.db.shop.tables.records.DepartmentRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentListParam;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;
import org.jooq.Condition;

import java.sql.Timestamp;
import java.util.List;

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
        select.where(DEPARTMENT.IS_DELETE.eq((byte) 0)).and(DEPARTMENT.LEVEL.eq((int) 1));
        buildOptions(select, param);
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
            .fetchOne().into(DepartmentOneParam.class);
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
    public int insertDepartment(DepartmentOneParam param) {
        DepartmentRecord record = new DepartmentRecord();
        FieldsUtil.assign(param, record);
        return db().executeInsert(record);
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
            List<DepartmentListVo> departmentList = db().select().from(DEPARTMENT).where(DEPARTMENT.PARENT_ID.eq(departmentId))
                .fetch().into(DepartmentListVo.class);
            return departmentList;
        } else {
            List<DepartmentListVo> departmentList = db().select().from(DEPARTMENT)
                .fetch().into(DepartmentListVo.class);
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
        int departmentNum = db().selectCount().from(DEPARTMENT).where(DEPARTMENT.PARENT_ID.eq(departmentId)).fetchOne()
            .into(int.class);
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
        return db().select(DEPARTMENT.ID, DEPARTMENT.NAME).from(DEPARTMENT).where(DEPARTMENT.ID.in(departmentIds)).fetchInto(DepartmentOneParam.class);
    }
}
