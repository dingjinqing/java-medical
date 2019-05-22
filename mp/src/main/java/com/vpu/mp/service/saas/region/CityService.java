package com.vpu.mp.service.saas.region;

import org.jooq.Result;

import static com.vpu.mp.db.main.tables.DictCity.DICT_CITY;
import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.service.foundation.BaseService;

/**
 * 
 * @author 新国
 *
 */
public class CityService extends BaseService {

	/**
	 * 得到省的城市列表
	 * 
	 * @param provinceId
	 * @return
	 */
	public Result<DictCityRecord> getCityList(Integer provinceId) {
		return db().selectFrom(DICT_CITY).where(DICT_CITY.PROVINCE_ID.eq(provinceId)).fetch();
	}

	public DictCityRecord getCityName(Integer cityId) {
		return db().selectFrom(DICT_CITY).where(DICT_CITY.CITY_ID.eq(cityId)).fetchOne();
	}

	public DictCityRecord getCityId(String cityName, Integer provinceId) {
		return db().selectFrom(DICT_CITY)
				.where(DICT_CITY.NAME.like(this.likeValue(cityName)).and(DICT_CITY.PROVINCE_ID.eq(provinceId)))
				.fetchOne();
	}

	public int addNewCity(Integer provinceId, String cityName) {
		DictCityRecord record = db().selectFrom(DICT_CITY).where(DICT_CITY.PROVINCE_ID.eq(provinceId))
				.orderBy(DICT_CITY.CITY_ID.desc()).fetchOne();
		Integer cityId = record.getCityId() + 100;
		while (getCityName(cityId) != null) {
			cityId += 100;
		}
		return db().insertInto(DICT_CITY, DICT_CITY.PROVINCE_ID, DICT_CITY.CITY_ID, DICT_CITY.NAME)
				.values(provinceId, cityId, cityName).execute();
	}
}
