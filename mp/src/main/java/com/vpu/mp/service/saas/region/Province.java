package com.vpu.mp.service.saas.region;

import org.jooq.Result;

import com.vpu.mp.db.main.tables.B2cDictProvince;
import com.vpu.mp.db.main.tables.records.B2cDictProvinceRecord;
import com.vpu.mp.service.foundation.BaseComponent;

public class Province extends BaseComponent {
	protected B2cDictProvince tableProvince = B2cDictProvince.B2C_DICT_PROVINCE;

	public Result<B2cDictProvinceRecord> getAll() {
		return db().fetch(tableProvince);
	}
	 	
	public B2cDictProvinceRecord getProvinceName(Integer provinceId) {
		return db().selectFrom(tableProvince).where(tableProvince.PROVINCE_ID.eq(provinceId)).fetchOne();
	}

	public B2cDictProvinceRecord getProvinceName(String provinceName) {
		return db().selectFrom(tableProvince).where(tableProvince.NAME.like(this.likeValue(provinceName))).fetchOne();
	}

	public  int updateProvinceName(String provinceName)
    {
    	return db().update(tableProvince) 
    	.set(tableProvince.NAME,provinceName).where(tableProvince.NAME.like(this.likeValue(provinceName))).execute();
    }
}
