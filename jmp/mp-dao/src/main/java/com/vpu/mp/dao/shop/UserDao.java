package com.vpu.mp.dao.shop;

import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author 赵晓东
 * @Create 2020-07-22 16:09
 **/

import static com.vpu.mp.db.shop.Tables.*;

@Repository
public class UserDao extends ShopBaseDao {

    /**
     * 根据用户名和手机查询用户id，再讲该用户权限设置为医师
     * @return UserDo
     */
    public Integer updateDoctorAuth(Integer userId) {
        return db().update(USER).set(USER.USER_TYPE, (byte) 1)
            .where(USER.USER_ID.eq(userId))
            .execute();
    }

    public UserDo getUserById(Integer userId){
        UserDo userDo= db().select().from(USER).where(USER.USER_ID.eq(userId)).fetchOneInto(UserDo.class);
        return userDo;
    }

}
