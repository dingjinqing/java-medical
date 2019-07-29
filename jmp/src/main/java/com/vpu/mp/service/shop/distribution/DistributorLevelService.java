package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorLevelVo;

@Service
public class DistributorLevelService extends ShopBaseService{

	public void levelConfig() {
		Integer level_info = db().selectCount()
				.from(DISTRIBUTOR_LEVEL)
				.where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq((byte) 1))
				.fetchOne().into(Integer.class);
		
		//初始化没有默认等级，自动创建默认等级
		if(level_info == 0) {
			db().insertInto(DISTRIBUTOR_LEVEL,DISTRIBUTOR_LEVEL.LEVEL_ID,DISTRIBUTOR_LEVEL.LEVEL_NAME,DISTRIBUTOR_LEVEL.LEVEL_STATUS)
			.values((byte) 1,"普通分销员",(byte) 1).execute();
		}
		
		//获取全部等级信息
		List<DistributorLevelVo> all_level = db().select().from(DISTRIBUTOR_LEVEL).fetch().into(DistributorLevelVo.class);
		HashMap<Byte, DistributorLevelVo> levelData = new HashMap<Byte, DistributorLevelVo>();
		for(DistributorLevelVo level : all_level) {
			levelData.put(level.getLevelId(),level);
		}
		this.getUserDistributorLevel();
	
	}
	
	
	public void getUserDistributorLevel() {
//		Result<Record1<Byte>> sql = db().select(USER.DISTRIBUTOR_LEVEL).from(USER).groupBy(USER.DISTRIBUTOR_LEVEL);
		
		DistributionParam fanliCfg = saas().getShopApp(getShopId()).config.distributionCfg.getDistributionCfg();
		
		if(fanliCfg.getJudgeStatus() == 1) {
//			res = sql.where(USER.IS_DISTRIBUTOR.eq((byte) 1))
		}
	}

}
