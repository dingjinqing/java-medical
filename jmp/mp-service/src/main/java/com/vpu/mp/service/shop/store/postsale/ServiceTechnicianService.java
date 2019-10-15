package com.vpu.mp.service.shop.store.postsale;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.ServiceTechnicianRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.technician.*;
import com.vpu.mp.service.shop.store.schedule.TechnicianScheduleService;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN;
import static com.vpu.mp.db.shop.Tables.STORE_SERVICE;

/**
 * @author 黄荣刚
 * @date 2019年7月15日
 *
 */
@Service

public class ServiceTechnicianService extends ShopBaseService {

	public final static Byte SERVICE_TYPE_ALL=0;
	public final static Byte SERVICE_TYPE_PART=1;

	@Autowired public ServiceTechnicianGroupService groupService;
	@Autowired public TechnicianScheduleService scheduleService;

	/**
	 * 根据ID查数据库一条记录
	 * @param id
	 * @return
	 */
	public ServiceTechnicianRecord selectRecord(Integer id) {
		if(id == null) {
			return null;
		}
		ServiceTechnicianRecord record = db()
						.selectFrom(SERVICE_TECHNICIAN)
						.where(SERVICE_TECHNICIAN.ID.eq(id))
						.and(SERVICE_TECHNICIAN.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
						.fetchOne();
		return record;
	}
	/**
	 * 根据ID查一条售后的信息
	 * @param id
	 * @return
	 */
	public ServiceTechnicianPojo select(Integer id) {
		ServiceTechnicianRecord technicianRecord = selectRecord(id);
		ServiceTechnicianPojo serviceTechnician = convert2ServiceTechnicianPojo(technicianRecord);
		return serviceTechnician;
	}
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	public PageResult<ServiceTechnicianPojo> getPageList(ServiceTechnicianPageListParam param){
		SelectWhereStep<?> selectFrom = db().selectFrom(SERVICE_TECHNICIAN);
		SelectConditionStep<?> select = buildOptions(selectFrom,param);
		PageResult<ServiceTechnicianRecord> recordList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), ServiceTechnicianRecord.class);
		List<ServiceTechnicianRecord> dataList = recordList.getDataList();
		List<ServiceTechnicianPojo> pojoList = new ArrayList<ServiceTechnicianPojo>();
		if(dataList != null) {
			for (ServiceTechnicianRecord record : dataList) {
				ServiceTechnicianPojo pojo = convert2ServiceTechnicianPojo(record);
				pojoList.add(pojo);
			}
		}
		PageResult<ServiceTechnicianPojo> result = new PageResult<ServiceTechnicianPojo>();
		result.dataList = pojoList;
		result.page = recordList.page;
		return result;
	}
	/**
	 * 添加售后
	 * @param technicianParam
	 * @return
	 */
	public int insert(ServiceTechnicianParam technicianParam) {
		if(technicianParam == null) {
			return 0;
		}
		String serviceList = null;
		if(SERVICE_TYPE_PART.equals(technicianParam.getServiceType())) {
			serviceList = Util.toJson(technicianParam.getServiceList());
		}
        int result = db().insertInto(SERVICE_TECHNICIAN,
				SERVICE_TECHNICIAN.STORE_ID, SERVICE_TECHNICIAN.TECHNICIAN_NAME,
            SERVICE_TECHNICIAN.TECHNICIAN_MOBILE, SERVICE_TECHNICIAN.BG_IMG_PATH,
            SERVICE_TECHNICIAN.TECHNICIAN_INTRODUCE, SERVICE_TECHNICIAN.GROUP_ID,
            SERVICE_TECHNICIAN.SERVICE_TYPE, SERVICE_TECHNICIAN.SERVICE_LIST,
				SERVICE_TECHNICIAN.REMARKS).values(technicianParam.getStoreId(), technicianParam.getTechnicianName(),
						technicianParam.getTechnicianMobile(), technicianParam.getBgImgPath(),
            technicianParam.getTechnicianIntroduce(), technicianParam.getGroupId(),
						technicianParam.getServiceType(), serviceList,
						technicianParam.getRemarks()).execute();
		return result;
	}
	/**
	 * 编辑售后
	 * @param param
	 * @return
	 */
	public int update(ServiceTechnicianParam param) {
		if(param == null) {
			return 0;
		}
		String serviceList = null;
		if(SERVICE_TYPE_PART.equals(param.getServiceType())) {
			serviceList = Util.toJson(param.getServiceList());
		}
		int result = db().update(SERVICE_TECHNICIAN)
			.set(SERVICE_TECHNICIAN.TECHNICIAN_NAME, param.getTechnicianName())
			.set(SERVICE_TECHNICIAN.TECHNICIAN_MOBILE, param.getTechnicianMobile())
			.set(SERVICE_TECHNICIAN.BG_IMG_PATH,param.getBgImgPath())
			.set(SERVICE_TECHNICIAN.TECHNICIAN_INTRODUCE,param.getTechnicianIntroduce())
			.set(SERVICE_TECHNICIAN.GROUP_ID,param.getGroupId())
			.set(SERVICE_TECHNICIAN.SERVICE_TYPE,param.getServiceType())
			.set(SERVICE_TECHNICIAN.SERVICE_LIST,serviceList)
			.set(SERVICE_TECHNICIAN.REMARKS,param.getRemarks())
			.where(SERVICE_TECHNICIAN.ID.eq(param.getId()))
			.execute();
		return result;
	}
	/**
	 * 删除售后
	 * @param id
	 * @return
	 */
	public int delete(Integer id) {
		if(id == null) {
			return 0;
		}
		int result = db().update(SERVICE_TECHNICIAN)
				.set(SERVICE_TECHNICIAN.DEL_FLAG, DelFlag.DISABLE_VALUE)
				.where(SERVICE_TECHNICIAN.ID.eq(id))
				.execute();
		return result;
	}

    /**
	 * @param selectFrom
	 * @param param
     * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectWhereStep<?> selectFrom, ServiceTechnicianPageListParam param) {
		SelectConditionStep<?> where = selectFrom.where(SERVICE_TECHNICIAN.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
											.and(SERVICE_TECHNICIAN.STORE_ID.eq(param.getStoreId()));
		if(!StringUtils.isBlank(param.getTechnicianName())) {
			where = where.and(SERVICE_TECHNICIAN.TECHNICIAN_NAME.like(this.likeValue(param.getTechnicianName())));
		}
		if(!StringUtils.isBlank(param.getTechnicianMobile())) {
			where = where.and(SERVICE_TECHNICIAN.TECHNICIAN_MOBILE.like(this.likeValue(param.getTechnicianMobile())));
		}
		if(param.getGroupId() != null) {
			where = where.and(SERVICE_TECHNICIAN.GROUP_ID.eq(param.getGroupId()));
		}
		return where;
	}

	/**
     * @param record
	 * @return
	 */
	private ServiceTechnicianPojo convert2ServiceTechnicianPojo(ServiceTechnicianRecord record) {
		if(record == null) {
			return null;
		}
		ServiceTechnicianPojo pojo = new ServiceTechnicianPojo(record.getId(),record.getStoreId(),
				record.getTechnicianName(),record.getTechnicianMobile(),
				record.getBgImgPath(),record.getTechnicianIntroduce(),
				null,
				record.getServiceType(),null,
				record.getRemarks(),record.getCreateTime(),
				record.getUpdateTime());
		ServiceTechnicianGroup group = groupService.select(record.getGroupId());
		pojo.setSeviceGroup(group);

        if(SERVICE_TYPE_PART.equals(record.getServiceType())) {
			List<Integer> serviceId = Util.parseJson(record.getServiceList(), new TypeReference<List<Integer>>() {});
			pojo.setServiceList(selectServiceListByIdList(serviceId));
		}else {
			pojo.setServiceList(selectAllServiceByStoreId(record.getStoreId()));
		}

        return pojo;
	}

	/**
	 * @param serviceId
	 * @return
	 */
	private List<TechnicianService> selectServiceListByIdList(List<Integer> serviceId) {
		List<TechnicianService> list = db().selectFrom(STORE_SERVICE)
							.where(STORE_SERVICE.ID.in(serviceId))
							.and(STORE_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
							.fetchInto(TechnicianService.class);
		return list;
	}

	/**
	 * @return
	 */
	private List<TechnicianService> selectAllServiceByStoreId(Integer storeId) {
		if(storeId == null) {
			return null;
		}
		List<TechnicianService> list = db().selectFrom(STORE_SERVICE)
				.where(STORE_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(STORE_SERVICE.STORE_ID.eq(storeId))
				.fetchInto(TechnicianService.class);
		return list;
	}

}
