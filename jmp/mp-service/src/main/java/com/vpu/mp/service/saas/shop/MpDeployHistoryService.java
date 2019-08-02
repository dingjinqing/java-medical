package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpDeployHistory.MP_DEPLOY_HISTORY;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpDeployHistoryRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;

/**
 * 
 * @author lixinguo
 *
 */
@Service
public class MpDeployHistoryService extends MainBaseService {

	/**
	 * 得到小程序模板发布信息
	 * 
	 * @param appId
	 * @param templateId
	 * @return
	 */
	public MpDeployHistoryRecord getDeployInfo(String appId, Integer templateId) {
		return db().fetchAny(MP_DEPLOY_HISTORY,
				MP_DEPLOY_HISTORY.BIND_TEMPLATE_ID.eq(templateId).and(MP_DEPLOY_HISTORY.APP_ID.eq(appId)));
	}

	/**
	 * 初始化发布
	 * 
	 * @param appId
	 * @param templateId
	 */
	public void initDeployHistory(String appId, Integer templateId) {
		if (templateId == null || templateId == 0) {
			templateId = saas.shop.mpVersion.getCurrentUseTemplateId(appId);
		}
		MpDeployHistoryRecord deployInfo = getDeployInfo(appId, templateId);
		if (deployInfo != null) {
			return;
		}
		this.addRow(templateId, appId);
	}

	/**
	 * 添加行
	 * 
	 * @param templateId
	 * @param appId
	 */
	public void addRow(Integer templateId, String appId) {
		MpDeployHistoryRecord record = db().newRecord(MP_DEPLOY_HISTORY);
		record.setAppId(appId);
		record.setBindTemplateId(templateId);
		record.setDeployTime(Timestamp.valueOf(LocalDateTime.now()));
		record.insert();
	}

	/**
	 * 更新日志
	 * @param message
	 * @param appId
	 * @param templateId
	 */
	public void updateDeployLog(String message, String appId, Integer templateId)
    {
		if(templateId == null || templateId == 0) {
			templateId = saas.shop.mpVersion.getCurrentUseTemplateId(appId);
		}
       initDeployHistory(appId, templateId);
       MpDeployHistoryRecord deployInfo = getDeployInfo(appId, templateId);
        if (deployInfo != null) {
        	deployInfo.setDeployLog(deployInfo.getDeployLog()+"\n"+message);
        	deployInfo.update();
        }
    }

}
