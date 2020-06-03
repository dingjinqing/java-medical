package com.vpu.mp.service.shop.card.msg;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.member.MemberBasicInfoVo;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaSubscribeData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
/**
 * 	会员卡操作相关消息推送
 * @author 黄壮壮
 *
 */
@Service
public class CardMsgNoticeService extends ShopBaseService {
	@Autowired private UserCardService userCardSvc;

	/**
	 *  会员卡等级变动通知
	 * @param userId 用户Id
	 * @param oldCard 被替换的旧卡
	 * @param newCard 替换的新卡
	 * @param option 操作备注说明
	 */
	public void cardGradeChangeMsg(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard, String option) {
		String cardNo = userCardSvc.getCardNoByUserAndCardId(userId,newCard.getId());
		List<Integer> arrayList = Collections.<Integer>singletonList(userId);
		//	订阅消息
		String[][] maData=null;
		//	公众号消息
		String[][] mpData=null;
		if(newCard.getGrade().compareTo(oldCard.getGrade())>0) {
			//	升级数据
			maData = new String[][] {
				{newCard.getCardName()},
				{Util.getdate("yyyy-MM-dd HH:mm:ss")},
				{"升级成功"}
			};
			mpData = new String[][] {
				{"等级卡升级通知"},
				{newCard.getCardName()},
                {"通过"},
                {"升级成功"}
			};
		}else {
			//	降级数据
			maData = new String[][] {
				{newCard.getCardName()},
				{Util.getdate("yyyy-MM-dd HH:mm:ss")},
				{"降级成功"}
			};

			mpData = new String[][] {
				{"等级卡降级通知"},
				{newCard.getCardName()},
				{"降级级成功"},
				{option}
			};
		}

		MaSubscribeData data = MaSubscribeData.builder().data307(maData).build();
		RabbitMessageParam param2 = RabbitMessageParam.builder()
				.maTemplateData(
						MaTemplateData.builder().config(SubcribeTemplateCategory.USER_GRADE).data(data).build())
				.mpTemplateData(
						MpTemplateData.builder().config(MpTemplateConfig.MEMBER_LEVEL_UP).data(mpData).build())
				.page("pages/cardinfo/cardinfo?card_no="+cardNo).shopId(getShopId())
				.userIdList(arrayList)
				.type(MessageTemplateConfigConstant.MEMBER_LEVEL_UP).build();
		saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobEnum.SEND_MESSAGE.getExecutionType());
	}

	/**
	 * 	发卡通知
	 * @param cardNo 卡号
	 * @param memberCard 会员卡信息
	 * @param user	用户信息
	 * @param expireTime 过期时间
	 * @param cardTypeText 卡类型说明
	 */
	public void sendCardMsgNotice(String cardNo, MemberCardRecord memberCard, MemberBasicInfoVo user, String expireTime,
			String cardTypeText) {
		//	公众号消息
		String[][] mpData = new String[][] {
			{""},
			{memberCard.getCardName()},
			{cardTypeText},
			{user.getMobile()==null?"":user.getMobile()},
			{expireTime},
			{""}
		};
		List<Integer> arrayList = Collections.<Integer>singletonList(user.getUserId());
		RabbitMessageParam param2 = RabbitMessageParam.builder()
				.mpTemplateData(
						MpTemplateData.builder().config(MpTemplateConfig.GET_CARD).data(mpData).build())
				.page("pages/cardinfo/cardinfo?cardNo="+cardNo).shopId(getShopId())
				.userIdList(arrayList)
				.type(MessageTemplateConfigConstant.SUCCESS_MEMBER_CARD_GET).build();
		saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobEnum.SEND_MESSAGE.getExecutionType());
	}
}
