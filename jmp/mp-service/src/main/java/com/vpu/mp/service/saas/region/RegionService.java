package com.vpu.mp.service.saas.region;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;

/**
 * 
 * @author 新国
 *
 */
@Service

public class RegionService extends MainBaseService {
	@Autowired public ProvinceService province;
	@Autowired public DistrictService district;
	@Autowired public CityService city;
}
