package com.vpu.mp.service.saas.region;

import org.jooq.Result;

import com.vpu.mp.db.main.tables.B2cDictCity;
import com.vpu.mp.db.main.tables.records.B2cDictCityRecord;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class City extends BaseComponent {
	protected B2cDictCity tableCity = B2cDictCity.B2C_DICT_CITY;

	/**
	 * 得到省的城市列表
	 * 
	 * @param provinceId
	 * @return
	 */
	public Result<B2cDictCityRecord> getCityList(Integer provinceId) {
		return db().selectFrom(tableCity).where(tableCity.PROVINCE_ID.eq(provinceId)).fetch();
	}

	public B2cDictCityRecord getCityName(Integer cityId) {
		return db().selectFrom(tableCity).where(tableCity.CITY_ID.eq(cityId)).fetchOne();
	}

	public B2cDictCityRecord getCityId(String cityName, Integer provinceId) {
		return db().selectFrom(tableCity)
				.where(tableCity.NAME.like(this.likeValue(cityName)).and(tableCity.PROVINCE_ID.eq(provinceId)))
				.fetchOne();
	}

	public int addNewCity(Integer provinceId, String cityName) {
		B2cDictCityRecord record = db().selectFrom(tableCity).where(tableCity.PROVINCE_ID.eq(provinceId))
				.orderBy(tableCity.CITY_ID.desc()).fetchOne();
		Integer cityId = record.getCityId() + 100;
		while (getCityName(cityId) != null) {
			cityId += 100;
		}
		return db().insertInto(tableCity, tableCity.PROVINCE_ID, tableCity.CITY_ID, tableCity.NAME)
				.values(provinceId, cityId, cityName).execute();
	}
}
