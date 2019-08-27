package com.vpu.mp.service.saas.area;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.area.AreaSelectVo;
import static com.vpu.mp.db.main.Tables.DICT_PROVINCE;
import static com.vpu.mp.db.main.Tables.DICT_CITY;
import static com.vpu.mp.db.main.Tables.DICT_DISTRICT;;
/**
 *	选择区域弹窗
 * @author liangchen
 * @date 2019年8月27日
 */
@Service
public class AreaSelectService extends MainBaseService{
	
	/**
	 *	查询所有省、市、区、县
	 *
	 * @param 
	 * @return List<AreaSelectVo>
	 */
	public List<AreaSelectVo> getAllArea() {
		/** 返回代码及名称 */
		List<AreaSelectVo> areaVo = db()
				.select(DICT_PROVINCE.PROVINCE_ID,DICT_PROVINCE.NAME.as("provinceName"),
						DICT_CITY.CITY_ID,DICT_CITY.NAME.as("cityName"),
						DICT_DISTRICT.DISTRICT_ID,DICT_DISTRICT.NAME.as("districtName"))
				.from(DICT_PROVINCE)
				.leftJoin(DICT_CITY).on(DICT_PROVINCE.PROVINCE_ID.eq(DICT_CITY.PROVINCE_ID))
				.leftJoin(DICT_DISTRICT).on(DICT_CITY.CITY_ID.eq(DICT_DISTRICT.CITY_ID))
				.fetch().into(AreaSelectVo.class);
		return areaVo;
		
	}
}
