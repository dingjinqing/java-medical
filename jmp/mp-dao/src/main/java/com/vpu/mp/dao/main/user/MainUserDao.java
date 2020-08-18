package com.vpu.mp.dao.main.user;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.service.pojo.saas.user.MainUserPageListParam;
import com.vpu.mp.service.pojo.saas.user.MainUserVo;
import org.jooq.Condition;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.main.Tables.USER;

/**
 * @author 李晓冰
 * @date 2020年08月17日
 */
@Repository
public class MainUserDao extends MainBaseDao {

    public PageResult<MainUserVo> getPageList(MainUserPageListParam pageListParam) {
        Condition condition = buildPageListCondition(pageListParam);

        SelectSeekStep1<UserRecord, Long> select = db().selectFrom(USER).where(condition).orderBy(USER.ID.asc());
        PageResult<MainUserVo> pageResult = this.getPageResult(select, pageListParam.getCurrentPage(), pageListParam.getPageRows(), MainUserVo.class);
        return pageResult;
    }

    private Condition buildPageListCondition(MainUserPageListParam pageListParam) {
        Condition condition = USER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE);
        if (pageListParam.getShopId() != null) {
            condition = condition.and(USER.SHOP_ID.eq(pageListParam.getShopId()));
        }
        return condition;
    }
}
