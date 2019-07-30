package com.vpu.mp.service.saas.region;

import org.jooq.Result;

import static com.vpu.mp.db.main.tables.DictProvince.DICT_PROVINCE;
import com.vpu.mp.db.main.tables.records.DictProvinceRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;


import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service

public class ProvinceService extends MainBaseService {

	public Result<DictProvinceRecord> getAll() {
		return db().fetch(DICT_PROVINCE);
	}
	 	
	public DictProvinceRecord getProvinceName(Integer provinceId) {
		return db().selectFrom(DICT_PROVINCE).where(DICT_PROVINCE.PROVINCE_ID.eq(provinceId)).fetchAny();
	}

	public DictProvinceRecord getProvinceName(String provinceName) {
		return db().selectFrom(DICT_PROVINCE).where(DICT_PROVINCE.NAME.like(this.likeValue(provinceName))).fetchAny();
	}

	public  int updateProvinceName(String provinceName)
    {
    	return db().update(DICT_PROVINCE) 
    	.set(DICT_PROVINCE.NAME,provinceName).where(DICT_PROVINCE.NAME.like(this.likeValue(provinceName))).execute();
    }
}
