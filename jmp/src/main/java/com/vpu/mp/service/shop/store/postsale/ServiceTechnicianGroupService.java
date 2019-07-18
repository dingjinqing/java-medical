package com.vpu.mp.service.shop.store.postsale;

import static com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN_GROUP;

import org.jooq.SelectConditionStep;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianGroup;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianGroupParam;
import com.vpu.mp.service.pojo.shop.store.postsale.TechnicianGroupPageListParam;


/**
 * @author 黄荣刚
 * @date 2019年7月15日
 *
 */
public class ServiceTechnicianGroupService extends BaseService {
	/** 没有被删除的 */
	public final static short NORMAL =0 ;
	/** 已经被删除的 */
	public final static short DISABLE = 1;
	
	public ServiceTechnicianGroup select(Integer id) {
		return db().select(SERVICE_TECHNICIAN_GROUP.GROUP_ID, SERVICE_TECHNICIAN_GROUP.GROUP_NAME, SERVICE_TECHNICIAN_GROUP.STORE_ID, SERVICE_TECHNICIAN_GROUP.CREATE_TIME)
		.from(SERVICE_TECHNICIAN_GROUP).where(SERVICE_TECHNICIAN_GROUP.GROUP_ID.eq(id))
		.fetchOneInto(ServiceTechnicianGroup.class);
	}
	
	/**
	   * 分页查询售后分组信息
	 * @param param
	 * @return
	 */
	public PageResult<ServiceTechnicianGroup> getPageList(TechnicianGroupPageListParam param){
		SelectConditionStep<?> selectFrom = db().select(SERVICE_TECHNICIAN_GROUP.GROUP_ID, SERVICE_TECHNICIAN_GROUP.GROUP_NAME, SERVICE_TECHNICIAN_GROUP.STORE_ID, SERVICE_TECHNICIAN_GROUP.CREATE_TIME)
							.from(SERVICE_TECHNICIAN_GROUP).where(SERVICE_TECHNICIAN_GROUP.STORE_ID.eq(param.getStoreId()));
		PageResult<ServiceTechnicianGroup> pageResult = getPageResult(selectFrom, ServiceTechnicianGroup.class);
		return pageResult;
	}
	
	public int insert(ServiceTechnicianGroupParam param) {
		int result = db().insertInto(SERVICE_TECHNICIAN_GROUP, SERVICE_TECHNICIAN_GROUP.STORE_ID, SERVICE_TECHNICIAN_GROUP.GROUP_NAME)
			.values(param.getStoreId(),param.getGroupName())
			.execute();
		return result;
	}
	
	public int update(ServiceTechnicianGroupParam param) {
		int result = db().update(SERVICE_TECHNICIAN_GROUP)
				.set(SERVICE_TECHNICIAN_GROUP.GROUP_NAME, param.getGroupName())
				.where(SERVICE_TECHNICIAN_GROUP.GROUP_ID.eq(param.getGroupId()))
				.and(SERVICE_TECHNICIAN_GROUP.STORE_ID.eq(param.getStoreId()))
				.and(SERVICE_TECHNICIAN_GROUP.DEL_FLAG.eq(NORMAL))
				.execute();
		return result;
	}
	public int delete(Integer groupId) {
		int result = db().update(SERVICE_TECHNICIAN_GROUP)
				.set(SERVICE_TECHNICIAN_GROUP.DEL_FLAG, DISABLE)
				.where(SERVICE_TECHNICIAN_GROUP.GROUP_ID.eq(groupId))
				.and(SERVICE_TECHNICIAN_GROUP.DEL_FLAG.eq(NORMAL))
				.execute();
		return result;
	}

	
}
