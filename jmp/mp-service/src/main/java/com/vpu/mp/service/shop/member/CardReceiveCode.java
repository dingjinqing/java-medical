package com.vpu.mp.service.shop.member;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardBatchRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.member.dao.CardReceiveCodeDao;

/**
* @author 黄壮壮
* @Date: 2019年11月8日
* @Description: 
*/
@Service
public class CardReceiveCode extends ShopBaseService {
	@Autowired private CardReceiveCodeDao cardReceiveCodeDao;
	
	/**
	 * 更新卡领取批次
	 */
	public void updateCardBatch(Integer cardId,List<Integer> batchIdList) {
		logger().info("更新会员卡领取批次");
		Set<Integer> alreadyBatchIds = getBatchIdByCardId(cardId);
		Set<Integer> newBatchIds = listToSet(batchIdList);
		
		Set<Integer> commonSet = findCommonSet(alreadyBatchIds,newBatchIds);
		Set<Integer> delBatchSet = removeCommonSet(alreadyBatchIds, commonSet);
		Set<Integer> addBatchSet = removeCommonSet(newBatchIds,commonSet);
		transaction(()->{
			addCardIdToCardBatch(cardId,addBatchSet);
			delBatchId(delBatchSet);
		});
		
	}
	
	public void addCardIdToCardBatch(Integer cardId,Set<Integer> batchIdSet) {
		cardReceiveCodeDao.addCardIdToCardBatch(cardId, batchIdSet);
	}
	
	public void delBatchId(Set<Integer> batchIdSet) {
		cardReceiveCodeDao.deleteBatchId(batchIdSet);
	}
	
	private Set<Integer> getBatchIdByCardId(Integer cardId){
		List<CardBatchRecord> cBatchList = getAvailableCardBatchByCardId(cardId);
		if(cBatchList == null) {
			return new HashSet<Integer>();
		}
		return cBatchList.stream().map(CardBatchRecord::getId).collect(Collectors.toSet());
	}
	
	
	/**
	 * 获取可用的批次
	 */
	public List<CardBatchRecord> getAvailableCardBatchByCardId(Integer cardId) {
		return cardReceiveCodeDao.getCardBatchByCardId(cardId);
	}
	
	
	private <T> Set<T> listToSet(List<T> list){
		if(list==null) {
			return new HashSet<T>();
		}
		return list.stream().collect(Collectors.toSet());
	}
	/**
	 * 找到公共元素
	 */
	private <T> Set<T> findCommonSet(Set<T> setOne,Set<T> setTwo){
		if(setOne==null || setTwo == null) {
			return new HashSet<T>();
		}
		Set<T> commonSet = new HashSet<>(setOne);
		commonSet.retainAll(setTwo);
		return commonSet;
	}
	
	private <T> Set<T> removeCommonSet(Set<T> set,Set<T> commonSet){
		assert set!=null && commonSet!=null;
		set.removeAll(commonSet);
		return set;
	}
}
