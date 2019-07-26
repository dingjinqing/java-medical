package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.member.card.NormalMemberCardParam;
/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 会员卡管理
 */
@RestController
@RequestMapping(value="/api/admin/member/card")
public class AdminMemberCardController extends BaseController {
	private final Byte NORMAL_TYPE = 0;
	private final Byte LIMIT_NUM_TYPE = 1;
	private final Byte RANK_TYPE = 2;
	private final Byte NEED_BUY = 1;
	private final Byte PICK_UP_CODE = 2;
	private final String BUTTON_ON = "on";
	private final Byte FIX_DATETIME = 0;
	private final String FINAL_TIME=" 23:59:59";
	
	@PostMapping("/add")
	public JsonResult createMemberCard(@RequestBody NormalMemberCardParam card) {
		//TODO
		System.out.println(card);
		
		Byte cardType = card.getCardType();
	
		//判断卡类型
		if(cardType == NORMAL_TYPE || cardType == LIMIT_NUM_TYPE) {
			//判断领取类型
			Byte isPay = card.getIsPay();
			
			//TODO
			if(isPay == NEED_BUY) {
				
			}else if(isPay == PICK_UP_CODE) {
				
			}
		}
		
		
		if(cardType == NORMAL_TYPE || cardType == RANK_TYPE) {
			Byte payOwnGood = (byte) (card.getPayOwnGood().equals(BUTTON_ON)? 1:0);
		}
		
		
		
		//处理普通会员卡
		if(cardType == NORMAL_TYPE) {
			//是否是固定时间段
			if(card.getExpiredType() == FIX_DATETIME) {
				String endTime = card.getEndTime().substring(0, 10).concat(FINAL_TIME);
			}
			
			
			
			
			
		}
		
		
		
		return this.success();
	}
	
}
