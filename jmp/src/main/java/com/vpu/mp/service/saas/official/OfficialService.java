package com.vpu.mp.service.saas.official;

import com.vpu.mp.service.foundation.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 黄壮壮
 * 2019-07-01 09:09
 */
@Service
@Scope("prototype")
public class OfficialService extends BaseService{
	public FreeExperienceService freeExperienceService;
}
