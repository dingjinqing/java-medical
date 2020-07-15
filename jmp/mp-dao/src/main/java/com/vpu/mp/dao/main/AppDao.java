package com.vpu.mp.dao.main;

import com.vpu.mp.common.pojo.main.table.AppDo;
import com.vpu.mp.dao.foundation.base.MainBaseDao;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.main.Tables.APP;

/**
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Repository
public class AppDao extends MainBaseDao {

    /**
     * 根据appId获取对应信息
     * @param appId
     * @return
     */
    public AppDo getAppDo(String appId){
        return db().selectFrom(APP).where(APP.APP_ID.eq(appId)).fetchAnyInto(AppDo.class);
    }
}
