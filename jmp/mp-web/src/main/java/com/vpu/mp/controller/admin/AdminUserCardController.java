package com.vpu.mp.controller.admin;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.pojo.shop.member.account.CardConsumeParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean.UserCardConsumeBeanBuilder;
import com.vpu.mp.service.pojo.shop.store.store.StoreParam;
import com.vpu.mp.service.pojo.wxapp.store.ValidCon;

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
    
    /**
     *	 增加减少会员卡余额和次数
     */
    @PostMapping("/api/admin/user/card/consume")
    public JsonResult chargeConsume(@RequestBody CardConsumeParam param) {
    	logger().info("增加减少会员卡余额和次数");
    	System.out.println(param);
    	UserCardConsumeBeanBuilder consumer = UserCardConsumeBean.builder();
    	Byte cardType = param.getCardType();
    	Boolean isContinue = true;
    	
    	if(CardUtil.isLimitCard(cardType)) {
    		// 限次卡
    		if(NumberUtils.BYTE_ONE.equals(param.getType())) {
    			// 兑换商品次数、
    			consumer.exchangCount(param.getReduce().intValue())
    					.countDis(param.getCountDis());
    			if(param.getReduce().intValue()<0) {
    				isContinue = false;
    			}
    		}else {
    			// 兑换门店次数
    			consumer.count(param.getReduce().intValue())
    					.countDis(param.getCountDis());
    		}
    	}else {
    		// 普通卡
    		consumer.money(param.getReduce())
    				.moneyDis(param.getMoneyDis());
    	}
    	
    	UserCardConsumeBean bean = consumer.type(cardType)
	    			.userId(param.getUserId())
	    			.cardId(param.getCardId())
	    			.message(param.getMessage())
	    			.payment("")
	    			.cardNo(param.getCardNo())
	    			.build();
    	shop().userCard.cardConsumer(bean, 0, (byte)10, (byte)2, param.getType(), isContinue);
    	return success();
    }
    
    
    
    
}
