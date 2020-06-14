package com.vpu.mp.service.shop.member.card;

import java.util.ArrayList;
import java.util.List;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.member.dao.card.GradeCardDao;

import static com.vpu.mp.db.shop.Tables.*;


/**
 * 
 * @author 黄壮壮
 * 等级会员卡服务
 */
@Service
public class GradeCardService extends ShopBaseService{
	@Autowired
	private GradeCardDao dao;
	/**
	 * 获得所有未被删除的卡，包括停用卡
	 */
	public Result<MemberCardRecord> getAllNoDeleteCard() {
		return dao.getAllNoDeleteCard();
	}
	/**
	 * 获得所有未被删除等级卡的等级
	 */
	public List<String> getAllNoDeleteCardGrade() {
		List<String> res = new ArrayList<>();
		Result<MemberCardRecord> cardList = getAllNoDeleteCard();
		for(MemberCardRecord re: cardList) {
			res.add(re.getGrade());
		}
		return res;
	}

    /**
     * 清除历史的等价卡信息
     * @param userId
     */
	public void clearUserAllGrade(Integer userId,Integer cardId,boolean isFromAdmin){
	    logger().info("清空所有之前的等级卡为废除状态");
	    this.transaction(()->{
	        //  user_card 废除所有用户持有的等级卡
                db().update(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
                    .set(USER_CARD.FLAG, CardConstant.UCARD_FG_STOP)
                    .where(USER_CARD.USER_ID.eq(userId))
                    .and(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE));

            String cardName = db().select(MEMBER_CARD.CARD_NAME).from(MEMBER_CARD).where(MEMBER_CARD.ID.eq(cardId)).fetchAnyInto(String.class);
            String userName = db().select(USER.USERNAME).from(USER).where(USER.USER_ID.eq(userId)).fetchAnyInto(String.class);
            List<Integer> cardIds = db().select(USER_CARD.CARD_ID)
                .from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
                .where(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE))
                .fetchInto(Integer.class);
            if(cardIds != null && cardIds.size()>0){
                //  激活审核设置为审核失败，失败原因为“更换等价卡“
                StringBuilder desc = new StringBuilder();
                if(isFromAdmin){
                    desc.append("该审核记录自动不通过: 管理员操作该卡已经替换为"+cardName);
                }else{
                    desc.append("该审核记录自动不通过: "+userName+"领取卡了新的等级卡"+cardName);
                }

                db().update(CARD_EXAMINE)
                    .set(CARD_EXAMINE.REFUSE_DESC,desc.toString())
                    .set(CARD_EXAMINE.REFUSE_TIME,DateUtil.getLocalDateTime())
                    .set(CARD_EXAMINE.STATUS,CardVerifyConstant.VSTAT_REFUSED)
                    .where(CARD_EXAMINE.CARD_ID.in(cardIds).and(CARD_EXAMINE.USER_ID.eq(userId)));

            }
        });
    }
}
