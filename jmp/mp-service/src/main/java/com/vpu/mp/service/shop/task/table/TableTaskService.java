package com.vpu.mp.service.shop.task.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.account.UserSysVo;
import com.vpu.mp.service.shop.user.user.UserService;

import lombok.extern.slf4j.Slf4j;

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
}
