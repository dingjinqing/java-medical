package com.vpu.mp.service.saas.official;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;

/**
 * 
 * @author 黄壮壮
 * 2019-07-01 09:09
 */
@Service

public class OfficialService extends MainBaseService{
	@Autowired public FreeExperienceService freeExperienceService;
}
