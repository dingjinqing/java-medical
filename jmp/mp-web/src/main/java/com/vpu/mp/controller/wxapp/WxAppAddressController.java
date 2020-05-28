package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.UserAddressRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.member.address.AddressIdParam;
import com.vpu.mp.service.pojo.shop.member.address.AddressListVo;
import com.vpu.mp.service.pojo.shop.member.address.AddressParam;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.vpu.mp.service.shop.member.AddressService.USER_ADDRESS_MAX_COUNT;

/**
 * @author 孔德成
 * @date 2019/11/15 9:10
 */
@RestController
@RequestMapping("/api/wxapp/address")
@Slf4j
public class WxAppAddressController extends WxAppBaseController {


    /**
     * 选择地址信息
     * @param param
     * @return
     */
    @PostMapping("/choose")
    public JsonResult chooseAddress(@RequestBody @Valid AddressParam param){
        WxAppSessionUser user =wxAppAuth.user();
        UserAddressRecord address = shop().addressService.chooseAddress(user.getUserId(), param.getWxAddress());
        if (address==null){
            return fail();
        }
        return success(address.into(UserAddressVo.class));
    }

    /**
     * 获取用户地址列表
     */
    @PostMapping("/list")
    public JsonResult getAddressList(){
        WxAppSessionUser user = wxAppAuth.user();
        AddressListVo addressList = shop().addressService.getAddressList(user.getUserId());
        return success(addressList);
    }

    /**
     * 获取地址信息
     */
    @PostMapping("/get")
    public JsonResult getAddress(@RequestBody @Valid AddressIdParam param){
        Integer userId = wxAppAuth.user().getUserId();
        UserAddressVo address = shop().addressService.getAddressById(userId, param.getAddressId());
        if (address==null){
            return fail();
        }
        return success();
    }

    /***
     * 新增地址信息
     * @return
     */
    @PostMapping("/add")
    public JsonResult addAddress(){
        WxAppSessionUser user =wxAppAuth.user();
        Integer addressUserNotDeleteCount = shop().addressService.getAddressUserNotDeleteCount(user.getUserId());
        if (addressUserNotDeleteCount>USER_ADDRESS_MAX_COUNT){
            log.info("用户{}不能添加地址数量上限{}",user.getUserId(),USER_ADDRESS_MAX_COUNT);
            return fail(JsonResultCode.USER_ADDRESS_COUNT_MORE_THAN_MAX,USER_ADDRESS_MAX_COUNT);
        }
        return null;
    }

    /**
     * 添加微信地址
     * @return
     */
    @PostMapping("/wxadd")
    public JsonResult addWxAddress(@RequestBody @Valid AddressParam param){
        WxAppSessionUser user =wxAppAuth.user();
        Integer addressUserNotDeleteCount = shop().addressService.getAddressUserNotDeleteCount(user.getUserId());
        if (addressUserNotDeleteCount>USER_ADDRESS_MAX_COUNT){
            log.info("用户{}不能添加地址数量上限{}",user.getUserId(),USER_ADDRESS_MAX_COUNT);
            return fail(JsonResultCode.USER_ADDRESS_COUNT_MORE_THAN_MAX,USER_ADDRESS_MAX_COUNT);
        }
        UserAddressRecord address = shop().addressService.chooseAddress(user.getUserId(), param.getWxAddress());
        if (address==null){
            return fail();
        }
        return success(address.into(UserAddressVo.class));
    }

    /**
     *编辑地址信息
     * @return
     */
    @PostMapping("/update")
    public JsonResult updateAddress(){
        return null;
    }

    /**
     * 删除地址
     * @return
     */
    @PostMapping("/remove")
    public JsonResult removeAddress(){
        return null;
    }

    /**
     * 选择默认的地址
     * @return
     */
    @PostMapping("/default")
    public JsonResult defaultAddress(){
        return null;
    }

}
