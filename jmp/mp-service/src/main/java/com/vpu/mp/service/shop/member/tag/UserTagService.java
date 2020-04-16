package com.vpu.mp.service.shop.member.tag;

import static com.vpu.mp.db.shop.Tables.TAG;
import static com.vpu.mp.db.shop.Tables.USER_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.UniqueKey;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TagRecord;
import com.vpu.mp.db.shop.tables.records.UserTagRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
/**
 * 	用户标签服务类
 * @author 黄壮壮
 *
 */
@Service
public class UserTagService extends ShopBaseService{
	/**
	 * 获取用户标签
	 * @return  List<TagVo> 不会为null
	 */
	public List<TagVo> getUserTag(Integer userId) {
		return db().select(TAG.TAG_ID,TAG.TAG_NAME)
			.from(USER_TAG)
			.leftJoin(TAG).on(USER_TAG.TAG_ID.eq(TAG.TAG_ID))
			.where(USER_TAG.USER_ID.eq(userId))
			.orderBy(USER_TAG.TAG_ID.asc())
			.fetchInto(TagVo.class);
	}
	
	
	/**
	 * 查询用户对应的标签
	 * @param userIds
	 * @return Map<Integer, List<TagVo>> 用户对应的标签，不会为null
	 */
	public Map<Integer, List<TagVo>> getUserTag(List<Integer> userIds) {
		Map<Integer, List<TagVo>> res = db().select(TAG.TAG_ID,TAG.TAG_NAME,USER_TAG.USER_ID)
				.from(USER_TAG)
				.leftJoin(TAG).on(USER_TAG.TAG_ID.eq(TAG.TAG_ID))
				.where(USER_TAG.USER_ID.in(userIds))
				.orderBy(USER_TAG.TAG_ID.asc())
				.fetchGroups(USER_TAG.USER_ID, TagVo.class);
		return res;
	}
	
	
	/**
	 * 	活动，如：领券，领卡，添加用户标签
	 * @param userId
	 * @param tagIdList
	 */
	public void addActivityTag(Integer userId,List<Integer> tagIdList,Short source,Integer activitiId) {
		logger().info("给用户打标签");
		
		//	在tagIdList中，用户已经绑定的标签ID列表
		Map<Integer, UserTagRecord> hasBindToUserTag = db().selectFrom(USER_TAG)
			.where(USER_TAG.USER_ID.eq(userId))
			.and(USER_TAG.TAG_ID.in(tagIdList))
			.fetchMap(USER_TAG.TAG_ID,UserTagRecord.class);
		
		//	标签具体信息
		Map<Integer, TagRecord> tagMap = db().selectFrom(TAG)
			.where(TAG.TAG_ID.in(tagIdList))
			.fetchMap(TAG.TAG_ID, TagRecord.class);
		//	更新标签
		List<UserTagRecord> updateRecords = new ArrayList<>();
		//	插入标签
		List<UserTagRecord> insertRecords = new ArrayList<>();
		
		for(Integer tagId: tagIdList) {
			TagRecord tagRecord = tagMap.get(tagId);
			if(tagRecord != null) {
				UserTagRecord userTag = new UserTagRecord();
				userTag.setUserId(userId);
				userTag.setTagId(tagId);
				userTag.setSource(source);
				userTag.setToolId(activitiId);
				
				if(hasBindToUserTag.get(tagId)!=null) {
					userTag.setTimes((short) (hasBindToUserTag.get(tagId).getTimes()+NumberUtils.SHORT_ONE));
					updateRecords.add(userTag);
				}else {
					insertRecords.add(userTag);
				}
			}
		}
		
		this.transaction(()->{
			if(updateRecords.size()>0) {
				db().batchUpdate(updateRecords).execute();
			}
			if(insertRecords.size()>0) {
				db().batchInsert(insertRecords).execute();
			}
		});
	}
}
