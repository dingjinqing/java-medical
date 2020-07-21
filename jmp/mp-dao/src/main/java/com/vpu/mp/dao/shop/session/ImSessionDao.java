package com.vpu.mp.dao.shop.session;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.IM_SESSION;

/**
 * 会话处理Dao
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Repository
public class ImSessionDao extends ShopBaseDao {

    /**
     * 根据id获取对应的会话信息
     * @param id
     * @return
     */
    public ImSessionDo getById(Integer id) {
        return db().selectFrom(IM_SESSION).where(IM_SESSION.ID.eq(id).and(IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(ImSessionDo.class);
    }
}
