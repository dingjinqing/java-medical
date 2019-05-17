package com.vpu.mp.service.saas.region;

import org.jooq.Record;
import org.jooq.Result;

import com.vpu.mp.db.main.tables.B2cDictCity;
import com.vpu.mp.db.main.tables.B2cDictDistrict;
import com.vpu.mp.db.main.tables.B2cDictProvince;
import com.vpu.mp.db.main.tables.records.B2cDictDistrictRecord;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class District extends BaseComponent {
	protected B2cDictProvince tableProvince = B2cDictProvince.B2C_DICT_PROVINCE;
	protected B2cDictCity tableCity = B2cDictCity.B2C_DICT_CITY;
	protected B2cDictDistrict tableDistrict = B2cDictDistrict.B2C_DICT_DISTRICT;

	/**
	 * 得到城市的区县列表
	 * 
	 * @param cityId
	 * @return
	 */
	public Result<B2cDictDistrictRecord> getDistrictList(Integer cityId) {
		return db().selectFrom(tableDistrict).where(tableDistrict.CITY_ID.eq(cityId)).fetch();
	}

	public B2cDictDistrictRecord getDistrictName(Integer districtId) {
		return db().selectFrom(tableDistrict).where(tableDistrict.DISTRICT_ID.eq(districtId)).fetchOne();
	}

	public Record getAreaName(Integer districtId) {
		return db()
				.select(tableProvince.PROVINCE_ID, tableProvince.NAME.as("province_name"), tableCity.CITY_ID,
						tableCity.NAME.as("city_name"), tableDistrict.DISTRICT_ID,
						tableDistrict.NAME.as("district_name"))
				.from(tableDistrict).join(tableCity).on(tableDistrict.CITY_ID.eq(tableCity.CITY_ID)).join(tableProvince)
				.on(tableProvince.PROVINCE_ID.eq(tableCity.PROVINCE_ID)).where(tableDistrict.DISTRICT_ID.eq(districtId))
				.fetchOne();
	}

	public B2cDictDistrictRecord getDistrictName(String districtName, Integer cityId) {
		return db().selectFrom(tableDistrict)
				.where(tableDistrict.CITY_ID.eq(cityId).and(tableDistrict.NAME.eq(districtName))).fetchOne();
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
				.select(tableProvince.PROVINCE_ID, tableProvince.NAME.as("province_name"), tableCity.CITY_ID,
						tableCity.NAME.as("city_name"), tableDistrict.DISTRICT_ID,
						tableDistrict.NAME.as("district_name"))
				.from(tableDistrict).join(tableCity).on(tableDistrict.CITY_ID.eq(tableCity.CITY_ID)).join(tableProvince)
				.on(tableProvince.PROVINCE_ID.eq(tableCity.PROVINCE_ID)).where(tableDistrict.NAME.eq(districtName))
				.and(tableCity.NAME.eq(cityName)).and(tableProvince.NAME.eq(provinceName)).fetchOne();
	}

	/**
	 * 添加新区
	 * @param cityId
	 * @param districtName
	 * @return
	 */
	public int addNewDistrict(Integer cityId, String districtName) {
		B2cDictDistrictRecord record = db().selectFrom(tableDistrict).where(tableDistrict.CITY_ID.eq(cityId))
				.orderBy(tableDistrict.DISTRICT_ID.desc()).fetchOne();
		
		Integer districtId = record.getDistrictId() + 1;
		while (this.getDistrictName(districtId) != null) {
			districtId += 1;
		}
		return db().insertInto(tableDistrict, tableDistrict.DISTRICT_ID, tableDistrict.CITY_ID, tableDistrict.NAME)
				.values(districtId, cityId, districtName).execute();
	}
}
