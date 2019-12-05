package com.vpu.mp.service.shop.task.goods;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static com.vpu.mp.db.shop.tables.FootprintRecord.FOOTPRINT_RECORD;
import static com.vpu.mp.db.shop.tables.UserLoginRecord.USER_LOGIN_RECORD;

/**
 * 删除三个月前的足迹
 * @author: 王兵兵
 * @create: 2019-12-04 17:21
 **/
@Service
public class FootprintDeleteTaskService extends ShopBaseService {

    //足迹和登录记录保存的时长（自然月）
    private static final int RECORD_SAVED_MONTHS = 3;

    public void deleteFootprint(){
        db().delete(FOOTPRINT_RECORD).where(FOOTPRINT_RECORD.UPDATE_TIME.lt(monthsAgo())).execute();
    }

    public void deleteLoginRecord(){
        db().delete(USER_LOGIN_RECORD).where(USER_LOGIN_RECORD.UPDATE_TIME.lt(monthsAgo())).execute();
    }

    private Timestamp monthsAgo(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -RECORD_SAVED_MONTHS);
        return new Timestamp(calendar.getTime().getTime());
    }
}
