package com.vpu.mp.service.saas.region;

import com.vpu.mp.service.foundation.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class RegionService extends BaseService {
	public ProvinceService province;
	public DistrictService district;
	public CityService city;
}
