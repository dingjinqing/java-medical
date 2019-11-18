package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.UserAddressRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.member.address.AddressParam;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 孔德成
 * @date 2019/11/15 9:10
 */
@RestController
@RequestMapping("/api/wxapp/address")
@Slf4j
public class WxAppAddressController extends WxAppBaseController {



    @PostMapping("/choose")
    public JsonResult chooseAddress(@RequestBody @Valid AddressParam param){
        WxAppSessionUser user =wxAppAuth.user();
        UserAddressRecord address = shop().addressService.chooseAddress(user.getUserId(), param.getWxAddress());
        if (address==null){
            return fail();
        }
        return success(address.into(UserAddressVo.class));
    }

}
