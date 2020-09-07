package com.vpu.mp.service.address;

import com.vpu.mp.dao.shop.address.UserAddressDao;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 赵晓东
 * @description
 * @create 2020-09-07 09:59
 **/
@Service
public class UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;

    /**
     * 根据地址id查询地址详情
     * @param addressId 地址id
     * @return UserAddressVo 地址详情
     */
    public UserAddressVo getUserAddressByAddressId(Integer addressId) {
        return userAddressDao.getUserAddressInfoByAddressId(addressId);
    }

}
