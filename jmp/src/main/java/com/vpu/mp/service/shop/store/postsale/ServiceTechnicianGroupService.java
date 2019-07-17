package com.vpu.mp.service.shop.store.postsale;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianGroup;
import static com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN_GROUP;


/**
 * @author 黄荣刚
 * @date 2019年7月15日
 *
 */
public class ServiceTechnicianGroupService extends BaseService {
	
	public ServiceTechnicianGroup select(Integer id) {
		return db().select(SERVICE_TECHNICIAN_GROUP.GROUP_ID, SERVICE_TECHNICIAN_GROUP.GROUP_NAME, SERVICE_TECHNICIAN_GROUP.STORE_ID, SERVICE_TECHNICIAN_GROUP.CREATE_TIME)
		.from(SERVICE_TECHNICIAN_GROUP).where(SERVICE_TECHNICIAN_GROUP.GROUP_ID.eq(id))
		.fetchOneInto(ServiceTechnicianGroup.class);
	}
	
}
