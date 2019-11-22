package com.vpu.mp.service.shop.user.cart;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.user.cart.dao.UserCartDao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 孔德成
 * @date 2019/10/15 10:35
 */
@Service
public class UserCartService extends ShopBaseService {
	@Autowired UserCartDao userCartDao;
	/**
	 * 在时间之后有加购行为的用户id列表
	 */
	public List<Integer> getUserIdFromCartStartTime(Timestamp time) {
		return userCartDao.getUserIdFromCartStartTime(time);
	}
	
	/**
	 * 在时间之前有加购行为的用户Id列表
	 */
	public List<Integer> getUserIdUtilToCartEndTime(Timestamp time) {
		return userCartDao.getUserIdUtilToCartEndTime(time);
	}
}
