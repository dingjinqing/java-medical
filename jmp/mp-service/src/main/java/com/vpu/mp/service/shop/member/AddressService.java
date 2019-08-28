package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.USER_ADDRESS;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
/**
* @author 黄壮壮
* @Date: 2019年8月16日
* @Description: 用户地址服务
*/
@Service
public class AddressService extends ShopBaseService {
	
	/** 
	 * 获取id用户的详细地址信息
	 */
	public List<String> getUserAddressById(Integer userId) {
		logger().info("获取用户"+userId+"的详细地址信息");
		List<String> addressList = db().select(USER_ADDRESS.COMPLETE_ADDRESS)
			.from(USER_ADDRESS)
			.where(USER_ADDRESS.USER_ID.eq(userId))
			.fetch()
			.into(String.class);
		
		addressList.forEach(logger()::info);
		return addressList;
	}
}
