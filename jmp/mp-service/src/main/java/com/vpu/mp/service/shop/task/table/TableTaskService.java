package com.vpu.mp.service.shop.task.table;

import com.google.common.collect.Sets;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.db.main.Tables;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.account.UserSysVo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 同步表的操作
 *
 * @author zhaojianqiang
 * @time 下午2:28:30
 */
@Service
@Slf4j
public class TableTaskService extends ShopBaseService {
	@Autowired
	private UserService userService;

	@Autowired
    private OrderInfoService orderInfoService;
	@Autowired
    private InquiryOrderDao inquiryOrderDao;


	private static com.vpu.mp.db.shop.Tables SHOP_TABLES;

	public void userSys() {
		log.info("#####################开始同步店：" + getShopId() + "的user#####################");
		// userService.synchronize();
		UserSysVo vo = userService.synchronizeUser();
		log.info("店铺{}，用户总数：{}，更新成功：{}，更新失败：{}，插入成功：{}，插入失败：{}", getShopId(), vo.getSum(), vo.getUpdateSuccess(),
				vo.getUpdateFail(), vo.getInsertSuccess(), vo.getInsertFail());
		log.info("#####################结束同步店：" + getShopId() + "的user#####################");

		log.info("#####################开始同步店：" + getShopId() + "的userDetail#####################");
		// userService.synchronize();
		UserSysVo vo2 = userService.synchronizeUserDetail();
		log.info("店铺{}，用户详情总数：{}，更新成功：{}，更新失败：{}，插入成功：{}，插入失败：{}", getShopId(), vo2.getSum(), vo2.getUpdateSuccess(),
				vo2.getUpdateFail(), vo2.getInsertSuccess(), vo2.getInsertFail());
		log.info("#####################结束同步店：" + getShopId() + "的userDetail#####################");
	}


	public  void orderSynchronize(){
	    db().insertInto(Tables.ORDER_INFO_NEW)
            .select(
                db().selectFrom(com.vpu.mp.db.shop.Tables.ORDER_INFO).
                    where(
                        com.vpu.mp.db.shop.Tables.ORDER_INFO.CREATE_TIME.greaterOrEqual(DateUtils.getTimestampForStartTime(-1))
                        .and(com.vpu.mp.db.shop.Tables.ORDER_INFO.CREATE_TIME.le(DateUtils.getTimestampForEndTime(-1)))
                    )
            );
    }
    public void oldOrderSynchronize(List<String> orderSns){
	    List<String> returnedOrderIds = orderInfoService.getReturnedOnTheDay();
        Set<String> result = Sets.newHashSet();
        result.addAll(orderSns);
        result.addAll(returnedOrderIds);
        List<OrderInfoRecord> newRecords =  db().selectFrom(com.vpu.mp.db.shop.Tables.ORDER_INFO)
            .where(com.vpu.mp.db.shop.Tables.ORDER_INFO.ORDER_SN.in(result))
            .fetch(x->x.into(OrderInfoRecord.class));
        saas().orderService.updateOldData(newRecords);
    }

    /**
     * 同步问诊订单
     */
    public void inquiryOrderSynchronize(Integer shopId){
        log.info("【同步任务】---问诊订单数据同步到主库shopId:{}",shopId);
        //更新
        List<InquiryOrderDo> inquiryOrderDoList= inquiryOrderDao.getListByUpdateTime(DateUtils.getTimestampForStartTime(-1),DateUtils.getTimestampForEndTime(-1));
        saas().mainInquiryOrderService.inquiryOrderSynchronizeUpdate(inquiryOrderDoList,shopId);

        //新增
        List<InquiryOrderDo> newInquiryOrderDoList= inquiryOrderDao.getListByCreateTime(DateUtils.getTimestampForStartTime(-1),DateUtils.getTimestampForEndTime(-1));
        saas().mainInquiryOrderService.inquiryOrderSynchronizeInsert(newInquiryOrderDoList);


	}
}
