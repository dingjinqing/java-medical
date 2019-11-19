package com.vpu.mp.service.saas.area;

import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.shop.area.AreaCityVo;
import com.vpu.mp.service.pojo.shop.area.AreaDistrictVo;
import com.vpu.mp.service.pojo.shop.area.AreaProvinceVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.main.Tables.*;

;
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
	 * @return List<AreaSelectVo>
	 */
	public List<AreaProvinceVo> getAllArea() {
		// 先查省
		List<AreaProvinceVo> provinceVo = db()
				.select(DICT_PROVINCE.PROVINCE_ID,DICT_PROVINCE.NAME.as("provinceName"))
				.from(DICT_PROVINCE)
				.fetch().into(AreaProvinceVo.class);
		// 遍历省份中的市
		for (int i = 0; i < provinceVo.size(); i++) {
			// 查出省里所有的市
			List<AreaCityVo> cityVo = db()
					.select(DICT_CITY.CITY_ID,DICT_CITY.NAME.as("cityName"))
					.from(DICT_CITY)
					.where(DICT_CITY.PROVINCE_ID.eq(provinceVo.get(i).getProvinceId()))
					.fetch().into(AreaCityVo.class);
			// 所有市添加到对应的省
			provinceVo.get(i).setAreaCity(cityVo);
			// 遍历市中的区县
			for (int j = 0; j < cityVo.size(); j++) {
				// 查出市里所有的区县
				List<AreaDistrictVo> districtVo = db()
						.select(DICT_DISTRICT.DISTRICT_ID,DICT_DISTRICT.NAME.as("districtName"))
						.from(DICT_DISTRICT)
						.where(DICT_DISTRICT.CITY_ID.eq(cityVo.get(j).getCityId()))
						.fetch().into(AreaDistrictVo.class);
				// 所有区县添加到对应的市
				cityVo.get(j).setAreaDistrict(districtVo);
			}
		}
		// 返回省-市-区县三级结构
		return provinceVo;
		
	}
}
