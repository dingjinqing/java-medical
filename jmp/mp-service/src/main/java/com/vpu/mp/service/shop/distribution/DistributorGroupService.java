package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_GROUP;
import static com.vpu.mp.db.shop.Tables.USER;

import java.sql.Timestamp;

import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.DistributorGroupRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.shop.distribution.AddDistributorToGroupParam;

@Service
public class DistributorGroupService extends ShopBaseService{
	/**
	 * 分销员分组列表
	 * @param param
	 * @return 
	 */
	public PageResult<DistributorGroupListVo> getDistributorGroupList(DistributorGroupListParam param) {
		SelectJoinStep<? extends Record> select = db()
				.select(DISTRIBUTOR_GROUP.ID,DISTRIBUTOR_GROUP.GROUP_NAME,DISTRIBUTOR_GROUP.IS_DEFAULT,DISTRIBUTOR_GROUP.DEL_FLAG,DISTRIBUTOR_GROUP.CREATE_TIME)
				.from(DISTRIBUTOR_GROUP);
		SelectConditionStep<? extends Record> sql = buildOptions(select,param);
		PageResult<DistributorGroupListVo> groupList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), DistributorGroupListVo.class);
		
		//每个分组下的分销员数量
		for(DistributorGroupListVo group : groupList.dataList) {
			int groupAmount = db().selectCount().from(USER).where(USER.INVITE_GROUP.eq(group.getId())).fetchOne().into(Integer.class);
			group.setDistributorAmount(groupAmount);
		}
		return groupList;
	}
	
	/**
	 * 分组列表条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select,DistributorGroupListParam param) {
		SelectConditionStep<? extends Record> sql = select.where(DISTRIBUTOR_GROUP.DEL_FLAG.eq((byte) 0));
		if(param.getGroupName() != null) {
			sql = sql.and(DISTRIBUTOR_GROUP.GROUP_NAME.eq(param.getGroupName()));
		}
		sql.orderBy(DISTRIBUTOR_GROUP.ID.desc());
		return sql;
	}
	
	/**
	 * 添加分组
	 * @param param
	 * @return
	 */
	public boolean adddistributorGroup(DistributorGroupListParam param) {
		DistributorGroupRecord record = new DistributorGroupRecord();
		assign(param,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 判断分组分组是否存在
	 * @param param
	 * @return
	 */
	public boolean isExistGroup(DistributorGroupListParam param) {
		Integer res = db().selectCount()
				.from(DISTRIBUTOR_GROUP)
				.where(DISTRIBUTOR_GROUP.GROUP_NAME.eq(param.getGroupName()))
				.fetchOne().into(Integer.class);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
	}
	
	/**
	 * 设置默认分组-事务
	 * @param id
	 * @return
	 */
	public boolean setDefault(Integer id) {
		this.transaction(()->{
			db().update(DISTRIBUTOR_GROUP)
				.set(DISTRIBUTOR_GROUP.IS_DEFAULT,(byte) 0)
				.where(DISTRIBUTOR_GROUP.IS_DEFAULT.eq((byte) 1))
				.execute();
			
			db().update(DISTRIBUTOR_GROUP)
				.set(DISTRIBUTOR_GROUP.IS_DEFAULT,(byte)1)
				.where(DISTRIBUTOR_GROUP.ID.eq(id))
				.execute();
		});
		return true;
	}
	
	/**
	 * 取消默认分组
	 * @param id
	 * @return
	 */
	public boolean cancleDefault(Integer id) {
		int res = db().update(DISTRIBUTOR_GROUP)
				.set(DISTRIBUTOR_GROUP.IS_DEFAULT,(byte) 0)
				.where(DISTRIBUTOR_GROUP.IS_DEFAULT.eq((byte) 1))
				.execute();
		return res > 0 ? true : false;
	}
	
	/**
	 * 获取单条分组信息
	 * @param id
	 * @return
	 */
	public DistributorGroupListVo getOneInfo(Integer id) {
		DistributorGroupListVo result = db().select().from(DISTRIBUTOR_GROUP)
				.where(DISTRIBUTOR_GROUP.ID.eq(id))
				.fetchOneInto(DistributorGroupListVo.class);
		return result;	
	}
	
	/**
	 * 编辑保存分销分组
	 * @param param
	 * @return
	 */
	public int groupSave(DistributorGroupListParam param) {
		DistributorGroupRecord record = new DistributorGroupRecord();
		assign(param,record);
		return db().executeUpdate(record);
	}
	
	/**
	 * 删除分组，假删除
	 * @param id
	 * @return
	 */
	public boolean delGroup(Integer id) {
		int result = db().update(DISTRIBUTOR_GROUP)
				.set(DISTRIBUTOR_GROUP.DEL_FLAG,(byte)1)
				.set(DISTRIBUTOR_GROUP.DEL_TIME,new Timestamp(System.currentTimeMillis()))
				.where(DISTRIBUTOR_GROUP.ID.eq(id))
				.execute();
		return result > 0 ? true : false;
	}
	
	/**
	 * 分组添加分销员
	 * @param param
	 * @return
	 */
	public boolean addDistributorGroup(AddDistributorToGroupParam param) {
		 int result = db().update(USER)
				.set(USER.INVITE_GROUP,(param.getGroupId()))
				.where(USER.USER_ID.in(param.getUserIds()))
				.execute();
		 return result > 0 ? true : false;
	}

}
