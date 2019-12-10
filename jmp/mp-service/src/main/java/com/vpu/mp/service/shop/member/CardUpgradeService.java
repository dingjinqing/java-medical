package com.vpu.mp.service.shop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.member.dao.CardUpgradeDao;

/**
* @author 黄壮壮
* @Date: 2019年10月31日
* @Description:  会员卡升级过程
*/
@Service
public class CardUpgradeService extends ShopBaseService{
	@Autowired
	private CardUpgradeDao cardUpgradeDao;
	public void recordCardUpdateGrade(Integer userId,MemberCardRecord oldCard, MemberCardRecord newCard,String option) {
		logger().info("保存会员卡升级信息");
		cardUpgradeDao.insert(userId,oldCard,newCard,option);
	}

}
