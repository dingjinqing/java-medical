package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.UserAddressRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import com.vpu.mp.service.pojo.shop.member.address.*;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
        param.setUserId(user.getUserId());
        param.setLang(getLang());
        ChooseAddressVo address = shop().addressService.chooseAddress(param);
        if (address==null){
            return fail();
        }
        return success(address);
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
        UserAddressRecord address = shop().addressService.getAddressById(userId, param.getAddressId());
        if (address==null){
            return fail();
        }
        return success(address.into(UserAddressVo.class));
    }

    /***
     * 新增地址信息
     * @return
     */
    @PostMapping("/add")
    public JsonResult addAddress(@RequestBody @Valid AddressAddParam param){
        WxAppSessionUser user =wxAppAuth.user();
        Integer addressUserNotDeleteCount = shop().addressService.getAddressUserNotDeleteCount(user.getUserId());
        if (addressUserNotDeleteCount>USER_ADDRESS_MAX_COUNT){
            log.info("用户{}不能添加地址数量上限{}",user.getUserId(),USER_ADDRESS_MAX_COUNT);
            return fail(JsonResultCode.USER_ADDRESS_COUNT_MORE_THAN_MAX,USER_ADDRESS_MAX_COUNT);
        }
        param.setUserId(user.getUserId());
        int i = shop().addressService.addAddress(param);
        if (i>0){
            return success();
        }
        return fail();
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
    public JsonResult updateAddress(@RequestBody @Validated(UpdateGroup.class) AddressAddParam param){
        int i = shop().addressService.updateAddress(param);
        if (i>0){
            return success();
        }
        return fail();
    }

    /**
     * 删除地址
     */
    @PostMapping("/remove")
    public JsonResult removeAddress(@RequestBody @Validated AddressIdParam param){
        Integer userId = wxAppAuth.user().getUserId();
        int i = shop().addressService.removeAddress(userId, param.getAddressId());
        if (i>0){
            return success();
        }
        return fail();
    }

    /**
     * 选择默认的地址
     */
    @PostMapping("/default")
    public JsonResult defaultAddress(@RequestBody @Validated AddressIdParam param){
        Integer userId = wxAppAuth.user().getUserId();
        int i = shop().addressService.defaultAddress(userId, param.getAddressId());
        if (i>0){
            return success();
        }
        return fail();
    }

    /**
     * 获取地址信息
     */
    @PostMapping("/getLocation")
    public JsonResult getLocationAddressInfo(@RequestBody @Validated LocationParam param){
        AddressCode addressCode = shop().addressService.getLocationAddressInfo(param.getLat(), param.getLng());
        if (addressCode!=null){
            return success(addressCode);
        }
        return fail();
    }

    @PostMapping("/database")
    public JsonResult getJson(@RequestBody @Validated AddressDataParam param) {
        String path = String.format("static/mp/address/addressData%s.json", param.getIndex());
        log.info("读取地址文件{}", path);
        return success(Util.loadResource(path));
    }

}
