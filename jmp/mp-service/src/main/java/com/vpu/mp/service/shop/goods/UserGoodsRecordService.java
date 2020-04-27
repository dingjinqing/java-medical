package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.UserGoodsRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserLoginRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import com.vpu.mp.service.shop.user.user.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.UserGoodsRecord.USER_GOODS_RECORD;

/**
 * @author: 王兵兵
 * @create: 2020-04-26 17:13
 * 浏览记录
 **/
@Service
public class UserGoodsRecordService extends ShopBaseService {
    @Autowired
    private UserLoginRecordService userLoginRecordService;


    /**
     *
     * @param param
     */
    public void addUserGoodsRecord(GoodsDetailMpParam param){
        UserGoodsRecordRecord todayRecord = getTodayRecord(param.getUserId(),param.getGoodsId());
        if(todayRecord != null){
            todayRecord.setCount((short)(todayRecord.getCount()+1));
            todayRecord.update();
        }else{
            UserLoginRecordRecord userLoginRecordRecord = userLoginRecordService.getUserLoginRecord(param.getUserId());

            UserGoodsRecordRecord insertRecord = db().newRecord(USER_GOODS_RECORD);
            insertRecord.setUserId(param.getUserId());
            insertRecord.setGoodsId(param.getGoodsId());
            insertRecord.setActiveId(param.getActivityId());
            insertRecord.setActiveType(param.getActivityType() == null ? 0 : (short)param.getActivityType());
            insertRecord.setUserIp(userLoginRecordRecord.getUserIp());
            insertRecord.setProvinceCode(userLoginRecordRecord.getProvinceCode());
            insertRecord.setCityCode(userLoginRecordRecord.getCityCode());
            insertRecord.setDistrictCode(userLoginRecordRecord.getDistrictCode());
            insertRecord.setLat(userLoginRecordRecord.getLat());
            insertRecord.setLng(userLoginRecordRecord.getLng());
            insertRecord.setCount((short)1);
            insertRecord.insert();
        }
    }

    /**
     *
     * @param userId
     * @param goodsId
     * @return
     */
    private UserGoodsRecordRecord getTodayRecord(Integer userId,Integer goodsId){
        return db().selectFrom(USER_GOODS_RECORD)
            .where(USER_GOODS_RECORD.CREATE_TIME.ge(DateUtil.getLocalTimeDateBySelf("yyyy-MM-dd 00:00:00")))
            .and(USER_GOODS_RECORD.GOODS_ID.eq(goodsId))
            .and(USER_GOODS_RECORD.USER_ID.eq(userId))
            .fetchAny();
    }
}
