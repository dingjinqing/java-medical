package com.vpu.mp.dao.shop.message;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.UserAnnouncementDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.UserAnnouncementRecord;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.USER_ANNOUNCEMENT;
import static com.vpu.mp.service.pojo.shop.message.UserMessageConstant.USER_MESSAGE_STATUS_ALREADY_READ;

/**
 * @author 赵晓东
 * @description
 * @create 2020-08-07 17:14
 **/

@Repository
public class UserAnnouncementDao extends ShopBaseDao {

    /**
     * 获取该用户上次打开系统公告列表时间
     * @param userId 用户id
     * @return Timestamp
     */
    public Timestamp getLastUserAnnouncement(Integer userId){
        List<Timestamp> timestamps = db().select(USER_ANNOUNCEMENT.CREATE_TIME).from(USER_ANNOUNCEMENT)
            .where(USER_ANNOUNCEMENT.USER_ID.eq(userId))
            .orderBy(USER_ANNOUNCEMENT.CREATE_TIME.desc()).fetchInto(Timestamp.class);
        if (timestamps.size() != 0) {
            return timestamps.get(0);
        }
        return null;
    }

    /**
     * 在关联表中插入用户读取记录
     * @param userAnnouncementDo 关联表实体类
     */
    public void saveUserAnnouncement(UserAnnouncementDo userAnnouncementDo) {
        UserAnnouncementRecord userAnnouncementRecord = db().newRecord(USER_ANNOUNCEMENT);
        FieldsUtil.assign(userAnnouncementDo, userAnnouncementRecord);
        userAnnouncementRecord.insert();
    }

    /**
     * 更改该用户读取系统消息未读状态
     * @param userId 用户id
     */
    public void updateUserAnnouncement(Integer userId) {
        db().update(USER_ANNOUNCEMENT)
            .set(USER_ANNOUNCEMENT.MESSAGE_STATUS, USER_MESSAGE_STATUS_ALREADY_READ)
            .where(USER_ANNOUNCEMENT.USER_ID.eq(userId)).execute();
    }

}
