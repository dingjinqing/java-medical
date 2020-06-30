package com.vpu.mp.service.shop.assess;

import static com.vpu.mp.db.shop.Tables.ASSESS_ACTIVITY;
import static com.vpu.mp.db.shop.Tables.ASSESS_RECORD;
import static com.vpu.mp.db.shop.Tables.ASSESS_RESULT;
import static com.vpu.mp.db.shop.Tables.ASSESS_TOPIC;

import java.sql.Timestamp;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.db.shop.tables.records.AssessActivityRecord;
import com.vpu.mp.db.shop.tables.records.AssessResultRecord;
import com.vpu.mp.db.shop.tables.records.AssessTopicRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.assess.AssessActivityListParam;
import com.vpu.mp.service.pojo.shop.assess.AssessActivityListVo;
import com.vpu.mp.service.pojo.shop.assess.AssessActivityOneParam;
import com.vpu.mp.service.pojo.shop.assess.AssessResultOneParam;
import com.vpu.mp.service.pojo.shop.assess.AssessTopicOneParam;

/**
 * 测评活动
 * @author 常乐
 * 2019年8月15日
 */
@Service
public class AssessService extends ShopBaseService{
	/**
	 * 测评活动列表
	 * @param param
	 * @return
	 */
	public PageResult<AssessActivityListVo> getActivityList(AssessActivityListParam param) {
		SelectJoinStep<? extends Record> select = db().select(ASSESS_ACTIVITY.ID ,ASSESS_ACTIVITY.ACT_NAME,ASSESS_ACTIVITY.CREATE_TIME,ASSESS_ACTIVITY.START_TIME,
				ASSESS_ACTIVITY.END_TIME,ASSESS_ACTIVITY.PUB_FLAG).from(ASSESS_ACTIVITY);
		buildOptions(select,param);
		PageResult<AssessActivityListVo> activityList = this.getPageResult(select,param.getCurrentPage(),param.getPageRows(), AssessActivityListVo.class);
				System.out.println(activityList);
		for(AssessActivityListVo list : activityList.dataList) {
			//测评题目数
			int topicNum = db().selectCount().from(ASSESS_TOPIC).where(ASSESS_TOPIC.ASSESS_ID.eq(list.getId())).fetchOne().into(Integer.class);
			System.out.println(topicNum);
			//测评结果数
			int resultNum = db().selectCount().from(ASSESS_RESULT).where(ASSESS_RESULT.ASSESS_ID.eq(list.getId())).fetchOne().into(Integer.class);
			
			//反馈总数
			int recordNum = db().selectCount().from(ASSESS_RECORD).where(ASSESS_RECORD.ASSESS_ID.eq(list.getId())).fetchOne().into(Integer.class);
			list.setTopicNum(topicNum);
			list.setResultNum(resultNum);
			list.setRecordNum(recordNum);
		}
		return activityList;
	}
	
	/**
	 *测评活动列表条件查询
	 * @param select
	 * @param param
	 */
	public void buildOptions(SelectJoinStep<? extends Record> select,AssessActivityListParam param) {
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());
		if(param.getNav() != null) {
			switch(param.getNav()) {
				//进行中
				case 1:
					select.where(ASSESS_ACTIVITY.START_TIME.le(nowDate)).and(ASSESS_ACTIVITY.END_TIME.ge(nowDate));
					break;
				//未开始
				case 2:
					select.where(ASSESS_ACTIVITY.START_TIME.ge(nowDate));
					break;
				//已过期
				case 3:
					select.where(ASSESS_ACTIVITY.END_TIME.le(nowDate));
					break;
				//已停用
				case 4:
					select.where(ASSESS_ACTIVITY.IS_BLOCK.eq((byte) 1));
					break;
			}
		}
	}
	
	/**
	 * 获取一条测评活动信息
	 * @param assessId
	 * @return
	 */
	public AssessActivityOneParam getOneInfo(Integer assessId) {
		AssessActivityOneParam info = db().select().from(ASSESS_ACTIVITY)
				.where(ASSESS_ACTIVITY.ID.eq(assessId))
				.fetchOne().into(AssessActivityOneParam.class);
		return info;
	}
	
	/**
	 * 编辑保存
	 * @param param
	 * @return
	 */
	public int saveAssess(AssessActivityOneParam param) {
		AssessActivityRecord record = new AssessActivityRecord();
		assign(param,record);
		return db().executeUpdate(record);
	}
	
	/**
	 * 添加测评活动信息
	 * @param param
	 * @return
	 */
	public int addAssess(AssessActivityOneParam param) {
		AssessActivityRecord record = new AssessActivityRecord();
		assign(param,record);
		return db().executeInsert(record);
	}
	
	/**
	 * 停用活动
	 * @param assessId
	 * @return
	 */
	public int pauseAssess(Integer assessId) {
		int res = db().update(ASSESS_ACTIVITY)
				.set(ASSESS_ACTIVITY.IS_BLOCK,(byte)1)
				.where(ASSESS_ACTIVITY.ID.eq(assessId))
				.execute();
		return res;
	}
	
	/**
	 * 启用活动
	 * @param assessId
	 * @return
	 */
	public int openAssess(Integer assessId) {
		int res = db().update(ASSESS_ACTIVITY)
				.set(ASSESS_ACTIVITY.IS_BLOCK,(byte)0)
				.where(ASSESS_ACTIVITY.ID.eq(assessId))
				.execute();
		return res;
	}
	
	/**
	 * 发布活动
	 * @param assessId
	 * @return
	 */
	public int publishAssess(Integer assessId) {
		int res = db().update(ASSESS_ACTIVITY)
				.set(ASSESS_ACTIVITY.PUB_FLAG,(byte)1)
				.where(ASSESS_ACTIVITY.ID.eq(assessId))
				.execute();
		return res;
	}
	
	/**
	 * 添加测评题目
	 * @param param
	 * @return
	 */
	public int addAssessTopic(AssessTopicOneParam param) {
		AssessTopicRecord record = new AssessTopicRecord();
		assign(param, record);
		return db().executeInsert(record);
	}
	
	/**
	 * 获取单条测评题目信息
	 * @param id
	 * @return
	 */
	public AssessTopicOneParam getAssessTopicOne(Integer id) {
		AssessTopicOneParam res = db().select().from(ASSESS_TOPIC)
				.where(ASSESS_TOPIC.ID.eq(id))
				.fetchOne()
				.into(AssessTopicOneParam.class);
		return res;
	}
	
	/**
	 * 测评题目编辑保存
	 * @param param
	 * @return
	 */
	public int saveAssessTopic(AssessTopicOneParam param) {
		AssessTopicRecord record = new AssessTopicRecord();
		assign(param, record);
		return db().executeUpdate(record);
	}
	
	/**
	 * 删除测评题目
	 * @param id
	 * @return
	 */
	public int delAssessTopic(Integer id) {
		int res = db().update(ASSESS_TOPIC)
				.set(ASSESS_TOPIC.DEL_FLAG,(byte)1)
				.where(ASSESS_TOPIC.ID.eq(id))
				.execute();
		return res;
	}
	
	/**
	 *添加测评结果
	 * @param param
	 * @return
	 */
	public int addAssessResult(AssessResultOneParam param) {
		AssessResultRecord record = new AssessResultRecord();
		assign(param, record);
		return db().executeInsert(record);
	}
	
	
}
