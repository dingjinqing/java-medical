package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreParam;
import com.vpu.mp.service.pojo.wxapp.store.ValidCon;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
* @author 黄壮壮
* @Date: 2019年11月26日
* @Description:
*/
@RestController
public class AdminUserCardController extends AdminBaseController {
	/**
	 * 用户卡 - 删除
	 */
	@PostMapping("/api/admin/user/card/delete")
	public JsonResult deleteUserCard(@RequestBody UserCardParam userCard) {
		logger().info("删除用户会员卡");
		shop().userCard.repealCardByCardNo(userCard.getCardNo());
		return this.success("删除用户会员卡成功");
	}

    /**
     * Gets store valid card list.获取用户在门店有效会员卡列表
     *
     * @param param the param
     * @return the store valid card list
     */
    @PostMapping("/api/admin/user/card/available")
    public JsonResult getStoreValidCardList(@RequestBody @Validated(ValidCon.class) StoreParam param) {
        return this.success(shop().userCard.userCardDao.getStoreValidCardList(param.getUserId(), param.getStoreId()));
    }
}
