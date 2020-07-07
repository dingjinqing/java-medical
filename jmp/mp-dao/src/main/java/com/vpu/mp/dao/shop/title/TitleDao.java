package com.vpu.mp.dao.shop.title;

import static com.vpu.mp.db.shop.Tables.DOCTOR_TITLE;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.DoctorTitle;
import com.vpu.mp.db.shop.tables.records.DoctorTitleRecord;
import com.vpu.mp.service.pojo.shop.title.TitleListParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;
import org.jooq.Condition;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TitleDao extends ShopBaseDao{

    /**
     * 职称列表
     *
     * @param param
     * @return
     */
    public PageResult<TitleOneParam> getDepartmentList(TitleListParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR_TITLE.ID, DOCTOR_TITLE.NAME, DOCTOR_TITLE.CREATE_TIME)
            .from(DOCTOR_TITLE);
        select.where(DOCTOR_TITLE.IS_DELETE.eq((byte) 0));
        buildOptions(select, param);
        PageResult<TitleOneParam> departmentList = this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), TitleOneParam.class);
        return departmentList;
    }

    /**
     * 职称搜索查询
     *
     * @param select
     * @param param
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, TitleListParam param) {
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取一条职称的信息
     *
     * @param titleId
     * @return
     */
    public TitleOneParam getOneInfo(Integer titleId) {
        TitleOneParam info = db().select().from(DOCTOR_TITLE).where(DOCTOR_TITLE.ID.eq(titleId))
            .fetchOne().into(TitleOneParam.class);
        return info;
    }

    /**
     * 编辑保存
     *
     * @param param
     * @return
     */
    public int updateTitle(TitleOneParam param) {
        DoctorTitleRecord record = new DoctorTitleRecord();
        FieldsUtil.assign(param, record);
        return db().executeUpdate(record);
    }

    /**
     * 添加测评活动信息
     *
     * @param param
     * @return
     */
    public int insertTitle(TitleOneParam param) {
        DoctorTitleRecord record = new DoctorTitleRecord();
        FieldsUtil.assign(param, record);
        return db().executeInsert(record);
    }

    /**
     * 删除
     *
     * @param titleId
     * @return
     */
    public int deleteTitle(Integer titleId) {
        int res = db().update(DOCTOR_TITLE).set(DOCTOR_TITLE.IS_DELETE, (byte) 1).where(DOCTOR_TITLE.ID.eq(titleId))
            .execute();
        return res;
    }

    /**
     * 职称是否存在，用来新增检查
     * @param titleId 科室ID
     * @param name 科室名称
     * @return true 存在 false 不存在
     */
    public boolean isNameExist(Integer titleId,String name) {
        Condition condition = DOCTOR_TITLE.NAME.eq(name);
        if (titleId != null) {
            condition = condition.and(DOCTOR_TITLE.ID.ne(titleId));
        }
        int count = db().fetchCount(DOCTOR_TITLE, condition);
        return count>0;
    }

    /**
     * 获取职称列表
     *
     * @return
     */
    public List<TitleOneParam> listTitles() {
        List<TitleOneParam> titleList = db().select().from(DOCTOR_TITLE).where(DOCTOR_TITLE.IS_DELETE.eq((byte) 0))
            .fetch().into(TitleOneParam.class);
        return titleList;
    }
}
