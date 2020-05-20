package com.vpu.mp.service.shop.member.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.shop.member.dao.card.GradeCardDao;
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
	 * 获取系统中所有可用的等级卡信息
	 * @return List<CardBasicVo> 等级卡基本信息列表
	 */
	public List<CardBasicVo> getAllAvailableGradeCards(){
		logger().info("获取可用等级卡会员卡的基本信息");
		return dao.getAllAvailableGradeCards();
	}
	
	/**
	 * 	查询有效等级卡，简单信息
	 * @return 等级卡List,该等价卡包括Id,name,grade信息
	 */
	public List<Map<String, Object>> getAllValidGradeCardList() {
		return dao.getAllValidGradeCardList();
	}
}
