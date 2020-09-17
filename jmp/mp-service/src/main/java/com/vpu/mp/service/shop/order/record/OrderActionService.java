package com.vpu.mp.service.shop.order.record;

import com.vpu.mp.db.shop.tables.OrderAction;
import com.vpu.mp.db.shop.tables.records.OrderActionRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.tables.OrderAction.ORDER_ACTION;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

;

/**
 * 	订单状态变化记录
 * @author 王帅
 *
 */
@Service
public class OrderActionService extends ShopBaseService{
	public final OrderAction TABLE = ORDER_ACTION;
	
	public void addRecord(OrderInfoVo order , OrderOperateQueryParam param, byte beforeStatus ,String desc) {
		OrderActionRecord record = db().newRecord(TABLE);
		record.setOrderId(order.getOrderId());
		record.setOrderSn(order.getOrderSn());
		record.setShopId(getShopId());
		record.setUserId(order.getUserId());
		record.setOrderStatus(beforeStatus);
		record.setActionNote(desc);
		if(param.getAdminInfo() != null) {
			record.setActionUser(param.getAdminInfo().getSysId() + "," + param.getAdminInfo().getUserName());
		}
		if(param.getWxUserInfo() != null){
			record.setUserOpenid(param.getWxUserInfo().getWxUser().getOpenId());
		}
        if(param.getIsMp() != null && OrderConstant.IS_MP_AUTO == param.getIsMp()){
            record.setActionUser("cron");
            record.setActionNote("自动任务," + record.getActionNote());
        }else if (param.getIsMp() != null && OrderConstant.IS_MP_MQ == param.getIsMp()){
            record.setActionUser("mq");
            record.setActionNote("mq," + record.getActionNote());
        }
		record.insert();
	}
	
	public void addRecord(OrderInfoRecord order , OrderOperateQueryParam param, byte beforeStatus ,String desc) {
		OrderActionRecord record = db().newRecord(TABLE);
		record.setOrderId(order.getOrderId());
		record.setOrderSn(order.getOrderSn());
		record.setShopId(getShopId());
		record.setUserId(order.getUserId());
		record.setOrderStatus(beforeStatus);
        record.setActionNote(desc);
        if(param.getAdminInfo() != null) {
            record.setActionUser(param.getAdminInfo().getSysId() + "," + param.getAdminInfo().getUserName());
        }
        if(param.getWxUserInfo() != null){
            record.setUserOpenid(param.getWxUserInfo().getWxUser().getOpenId());
        }
        if(param.getIsMp() != null && OrderConstant.IS_MP_AUTO == param.getIsMp()){
            record.setActionUser("cron");
            record.setActionNote("自动任务," + record.getActionNote());
        }else if (param.getIsMp() != null && OrderConstant.IS_MP_MQ == param.getIsMp()){
            record.setActionUser("mq");
            record.setActionNote("mq," + record.getActionNote());
        }else if (param.getIsMp() != null && OrderConstant.IS_MP_POS == param.getIsMp()){
            record.setActionUser("pos");
            record.setActionNote("pos" + record.getActionNote());
        }
		record.insert();
	}

    /**
     * 数量单数
     * @param userId
     * @param orderStatus
     * @return
     */
	public Integer getCountNumByUserIdOrderStatus(Integer userId, Byte orderStatus, Integer storesId){
	    return db().selectCount().from(TABLE)
            .leftJoin(ORDER_INFO).on(TABLE.ORDER_ID.eq(ORDER_INFO.ORDER_ID))
            .where(TABLE.USER_ID.eq(userId)).and(TABLE.ORDER_STATUS.eq(orderStatus))
            .and(ORDER_INFO.STORE_ID.eq(storesId))
            .fetchAnyInto(Integer.class);
    }
    /**
     * 时间查询数量单数
     * @param userId
     * @param orderStatus
     * @return
     */
    public Integer getCountNumByUserIdOrderStatusAndTime(Integer userId, Byte orderStatus, List<Integer> storesIds, Timestamp startTime,Timestamp endTime){
        return db().selectCount().from(TABLE)
            .leftJoin(ORDER_INFO).on(TABLE.ORDER_ID.eq(ORDER_INFO.ORDER_ID))
            .where(TABLE.USER_ID.eq(userId)).and(TABLE.ORDER_STATUS.eq(orderStatus))
            .and(ORDER_INFO.STORE_ID.in(storesIds))
            .and(ORDER_INFO.CREATE_TIME.ge(startTime))
            .and(ORDER_INFO.CREATE_TIME.le(endTime))
            .fetchAnyInto(Integer.class);
    }
}
