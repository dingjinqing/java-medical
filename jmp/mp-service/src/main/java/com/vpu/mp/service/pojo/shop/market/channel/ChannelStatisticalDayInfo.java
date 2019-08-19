package com.vpu.mp.service.pojo.shop.market.channel;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月16日
 */
@Data
@NoArgsConstructor
public class ChannelStatisticalDayInfo {
	/**	key为渠道名，value 为渠道访问量 */
	private Map<String,PerChannelDayInfo>  dayInfoMap = new HashMap<>();
    /** 全部访问量：包括各个渠道和无渠道的 */
	private PerChannelDayInfo allInfo;
	/** 无渠道的访问量 */
	private PerChannelDayInfo noChannelInfo;
	/** 各个渠道访问量汇总 */
	private PerChannelDayInfo totalChannelInfo;
	
	/** 将各个渠道的访问量累加 */
	public void setTotalChannelInfo() {
		int pvAll=0,uvAll=0,pvNew=0,uvNew=0,pvOld=0,uvOld=0;
		for (PerChannelDayInfo item : dayInfoMap.values()) {
			pvAll+=item.getPvAll();
			uvAll+=item.getUvAll();
			pvNew+=item.getPvNew();
			uvNew+=item.getUvNew();
			pvOld+=item.getPvOld();
			uvOld+=item.getUvOld();
		}
		totalChannelInfo = new PerChannelDayInfo(pvAll, uvAll, pvNew, uvNew, pvOld, uvOld);
	}
	/** 必须先设置全部访问量 */
	public void setNoChannelInfo() {
		if(allInfo == null) {
			throw new IllegalStateException();
		}
		if(totalChannelInfo == null) {
			setTotalChannelInfo();
		}
		int pvAll=0,uvAll=0,pvNew=0,uvNew=0,pvOld=0,uvOld=0;
		pvAll = allInfo.getPvAll() - totalChannelInfo.getPvAll();
		uvAll = allInfo.getUvAll() - totalChannelInfo.getUvAll();
		pvNew = allInfo.getPvNew() - totalChannelInfo.getPvNew();
		uvNew = allInfo.getUvNew() - totalChannelInfo.getUvNew();
		pvOld = allInfo.getPvOld() - totalChannelInfo.getPvOld();
		uvOld = allInfo.getUvOld() - totalChannelInfo.getUvOld();
		
		pvAll = pvAll<0?0:pvAll;
		uvAll = uvAll<0?0:uvAll;
		pvNew = pvNew<0?0:pvNew;
		uvNew = uvNew<0?0:uvNew;
		pvOld = pvOld<0?0:pvOld;
		uvOld = uvOld<0?0:uvOld;
		
		noChannelInfo = new PerChannelDayInfo(pvAll, uvAll, pvNew, uvNew, pvOld, uvOld);
		return ;
	}
	
	
}

