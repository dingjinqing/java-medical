package com.vpu.mp.dao.shop;

import com.vpu.mp.common.foundation.data.DistributionConstant;
import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author 赵晓东
 * @Create 2020-07-22 16:09
 **/

import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.User.USER;

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
    /**
     * 查询分销员等级列表下的分销员id列表
     * @param levelIds 分销员等级列表
     * @return 分销员id列表
     */
    public List<Integer> listUserIdByDistributorLevel(List<Integer> levelIds) {
        return db().select(USER.USER_ID).from(USER)
            .where(USER.IS_DISTRIBUTOR.eq(DistributionConstant.IS_DISTRIBUTOR))
            .and(USER.DISTRIBUTOR_LEVEL.in(levelIds)).fetchInto(Integer.class);
    }
    /**
     * 根据用户名和手机查询用户id，再讲该用户权限设置为医师
     * @return UserDo
     */
    public Integer updateUserDoctorAuth(Integer userId,Byte type) {
        return db().update(USER).set(USER.USER_TYPE, type)
            .where(USER.USER_ID.eq(userId))
            .execute();
    }

    /**
     * 医师解绑将用户类型设置为患者
     * @param userId
     */
    public void unbundlingUserType(Integer userId) {
        db().update(USER).set(USER.USER_TYPE, (byte)0)
            .where(USER.USER_ID.eq(userId)).execute();
    }

    /**
     * 更新用户类型
     * @param userId
     * @param userType
     */
    public void updateUserType(Integer userId,Byte userType) {
        db().update(USER).set(USER.USER_TYPE, userType)
            .where(USER.USER_ID.eq(userId)).execute();
    }

}
