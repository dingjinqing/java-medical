package com.vpu.mp.service.saas.region;

import org.jooq.Record;
import org.jooq.Result;

import static com.vpu.mp.db.main.tables.DictCity.DICT_CITY;
import static com.vpu.mp.db.main.tables.DictDistrict.DICT_DISTRICT;
import static com.vpu.mp.db.main.tables.DictProvince.DICT_PROVINCE;
import com.vpu.mp.db.main.tables.records.DictDistrictRecord;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class CDistrict extends BaseComponent {
	

	/**
	 * 得到城市的区县列表
	 * 
	 * @param cityId
	 * @return
	 */
	public Result<DictDistrictRecord> getDistrictList(Integer cityId) {
		return db().selectFrom(DICT_DISTRICT).where(DICT_DISTRICT.CITY_ID.eq(cityId)).fetch();
	}

	public DictDistrictRecord getDistrictName(Integer districtId) {
		return db().selectFrom(DICT_DISTRICT).where(DICT_DISTRICT.DISTRICT_ID.eq(districtId)).fetchOne();
	}

	public Record getAreaName(Integer districtId) {
		return db()
				.select(DICT_PROVINCE.PROVINCE_ID, DICT_PROVINCE.NAME.as("province_name"), DICT_CITY.CITY_ID,
						DICT_CITY.NAME.as("city_name"), DICT_DISTRICT.DISTRICT_ID,
						DICT_DISTRICT.NAME.as("district_name"))
				.from(DICT_DISTRICT).join(DICT_CITY).on(DICT_DISTRICT.CITY_ID.eq(DICT_CITY.CITY_ID)).join(DICT_PROVINCE)
				.on(DICT_PROVINCE.PROVINCE_ID.eq(DICT_CITY.PROVINCE_ID)).where(DICT_DISTRICT.DISTRICT_ID.eq(districtId))
				.fetchOne();
	}

	public DictDistrictRecord getDistrictName(String districtName, Integer cityId) {
		return db().selectFrom(DICT_DISTRICT)
				.where(DICT_DISTRICT.CITY_ID.eq(cityId).and(DICT_DISTRICT.NAME.eq(districtName))).fetchOne();
	}

	/**
	 * 根据省市区名称得到详细信息
	 * 
	 * @param provinceName
	 * @param cityName
	 * @param districtName
	 * @return
	 */
	public Record getAreaDetailInfo(String provinceName, String cityName, String districtName) {
		return db()
				.select(DICT_PROVINCE.PROVINCE_ID, DICT_PROVINCE.NAME.as("province_name"), DICT_CITY.CITY_ID,
						DICT_CITY.NAME.as("city_name"), DICT_DISTRICT.DISTRICT_ID,
						DICT_DISTRICT.NAME.as("district_name"))
				.from(DICT_DISTRICT).join(DICT_CITY).on(DICT_DISTRICT.CITY_ID.eq(DICT_CITY.CITY_ID)).join(DICT_PROVINCE)
				.on(DICT_PROVINCE.PROVINCE_ID.eq(DICT_CITY.PROVINCE_ID)).where(DICT_DISTRICT.NAME.eq(districtName))
				.and(DICT_CITY.NAME.eq(cityName)).and(DICT_PROVINCE.NAME.eq(provinceName)).fetchOne();
	}

	/**
	 * 添加新区
	 * @param cityId
	 * @param districtName
	 * @return
	 */
	public int addNewDistrict(Integer cityId, String districtName) {
		DictDistrictRecord record = db().selectFrom(DICT_DISTRICT).where(DICT_DISTRICT.CITY_ID.eq(cityId))
				.orderBy(DICT_DISTRICT.DISTRICT_ID.desc()).fetchOne();
		
		Integer districtId = record.getDistrictId() + 1;
		while (this.getDistrictName(districtId) != null) {
			districtId += 1;
		}
		return db().insertInto(DICT_DISTRICT, DICT_DISTRICT.DISTRICT_ID, DICT_DISTRICT.CITY_ID, DICT_DISTRICT.NAME)
				.values(districtId, cityId, districtName).execute();
	}
}
