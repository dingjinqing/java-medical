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
     * @param name 用户姓名
     * @param mobail 手机号
     * @return UserDo
     */
    public UserDo updateDoctorAuth(String name, String mobail) {
        UserDo userDo = db().select().from(USER).where(USER.USERNAME.eq(name)
            .and(USER.MOBILE.eq(mobail))).fetchAnyInto(UserDo.class);
         db().update(USER).set(USER.USER_TYPE, (byte) 1)
            .where(USER.USERNAME.eq(name).and(USER.MOBILE.eq(mobail)))
            .execute();
         return userDo;
    }

    public UserDo getUserById(Integer userId){
        UserDo userDo= db().select().from(USER).where(USER.USER_ID.eq(userId)).fetchOneInto(UserDo.class);
        return userDo;
    }

}
