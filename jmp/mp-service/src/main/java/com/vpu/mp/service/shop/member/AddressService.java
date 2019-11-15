package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.USER_ADDRESS;

import java.util.List;

import com.vpu.mp.db.shop.tables.UserAddress;
import com.vpu.mp.service.pojo.shop.member.address.AddressParam;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;

import jodd.util.StringUtil;
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

    /**
     * 王帅
     * 获取地址
     * @param addressId 地址id
     * @param userId 用户id
     * @return 地址
     */
	public UserAddressVo get(Integer addressId, Integer userId){
	    if(addressId == null || userId== null){
	        return null;
        }
		UserAddressVo address = db().select().from(USER_ADDRESS).where(USER_ADDRESS.ADDRESS_ID.eq(addressId).and(USER_ADDRESS.USER_ID.eq(userId))).fetchAnyInto(UserAddressVo.class);
		if(address != null && (StringUtil.isBlank(address.getLat()) || StringUtil.isBlank(address.getLng()))){
			//TODO 经纬度
		}
		return address;
	}

	/**
	 */
	public void addAddress(AddressParam param){


	}




}
