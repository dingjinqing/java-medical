package com.vpu.mp.service.shop.member.dao;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;
import org.jooq.tools.StringUtils;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;

import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_EXPIRED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_DELETE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import java.sql.Timestamp;

/**
* @author 黄壮壮
* @Date: 2019年9月24日
* @Description: 会员卡dao访问
*/
@Service
public class CardDaoService extends ShopBaseService {
	
	public PageResult<CardHolderVo> getAllCardHolder(CardHolderParam param) {

		User invitedUser = USER.as("a");
		SelectJoinStep<?> select = db().select(USER_CARD.USER_ID,USER.USERNAME,USER.MOBILE,invitedUser.USERNAME.as("invitedName"),USER_CARD.CREATE_TIME,USER_CARD.CARD_NO,USER_CARD.FLAG)
			.from( USER_CARD.leftJoin(USER
										.leftJoin(invitedUser).on(USER.INVITE_ID.eq(invitedUser.USER_ID))
					
									).on(USER_CARD.USER_ID.eq(USER.USER_ID))	
				);
		
		buildOptions(param, select);
		select.where(USER_CARD.CARD_ID.eq(param.getCardId()));
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), CardHolderVo.class);
	}
	/**
	 * 构建查询参数
	 * @param param
	 * @param select
	 */
	private void buildOptions(CardHolderParam param, SelectJoinStep<?> select) {
		/** - 会员id */
		if(param.getUserId()!=null) {
			select.where(USER_CARD.USER_ID.eq(param.getUserId()));
		}
		/** - 昵称 */
		if(!StringUtils.isBlank(param.getUsername())) {
			String likeValue = likeValue(param.getUsername());
			select.where(USER.USERNAME.like(likeValue));
		}
		/** -手机号 */
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}
		/** - 会员卡号 */
		if(!StringUtils.isBlank(param.getCardNo())) {
			select.where(USER_CARD.CARD_NO.eq(param.getCardNo()));
		}
		/** - 卡状态 */
		if(param.getFlag() != null) {
			/** - 状态为过期 */
			if(param.getFlag().equals(CARD_EXPIRED)) {
				select.where(USER_CARD.EXPIRE_TIME.le(DateUtil.getLocalDateTime()).or(USER_CARD.EXPIRE_TIME.isNull()));
			}else if(param.getFlag().equals(CARD_USING) || param.getFlag().equals(CARD_DELETE)) {
				select.where(USER_CARD.FLAG.eq(param.getFlag()));
			}
		}
		/** - 领卡时间  开始范围 */
		if(param.getFirstDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.ge(param.getFirstDateTime()));
		}
		
		/** - 领卡时间  结束范围 */
		if(param.getSecondDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.le(param.getSecondDateTime()));
		}
	}

}
