package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpVersion.MP_VERSION;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpVersionRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;

@Service
public class MpVersionService extends MainBaseService {

	public static final Byte PACK_VERSION_NORMAL = 1;
	public static final Byte PACK_VERSION_PLUGIN = 2;
	public static final Byte NOT_IN_USE = 0;
	public static final Byte IN_USE = 1;

	/**
	 * 同步版本列表
	 * 
	 * @throws WxErrorException
	 */
	public void synMpVersionList() throws WxErrorException {
		Integer lastTemplateId = 0;
		List<WxOpenMaCodeTemplate> list = open.getWxOpenComponentService().getTemplateList();
		for (WxOpenMaCodeTemplate template : list) {
			MpVersionRecord record = db().newRecord(MP_VERSION);
			record.setCreateTime(new Timestamp(template.getCreateTime()));
			record.setUserVersion(template.getUserVersion());
			record.setUserDesc(template.getUserDesc());
			record.setTemplateId(template.getTemplateId().intValue());
			record.store();
			lastTemplateId = template.getTemplateId().intValue();
		}
		Integer useTemplateId = this.getCurrentUseTemplateId(null, PACK_VERSION_NORMAL);
		if (useTemplateId == 0 && lastTemplateId > 0) {
			// 如果没有当前的模板ID，最后的模板ID设置为当前使用模板ID
			this.setCurrentUseTemplateId(lastTemplateId);
		}
	}

	/**
	 * 得到最新版本信息
	 * 
	 * @return
	 */
	public MpVersionRecord getLastVersion() {
		return db().selectFrom(MP_VERSION).orderBy(MP_VERSION.TEMPLATE_ID.desc()).limit(1).fetchAny();
	}

	/**
	 * 得到最新版本信息，分为：1 正常版本 2 好物推荐版本
	 * 
	 * @param appId          小程序appId，不为空时，查询小程序的使用版本
	 * @param packageVersion 插件版本标识：1 正常版本 2 好物推荐版本
	 * @return
	 */
	public MpVersionRecord getCurrentUseVersion(String appId, Byte packageVersion) {
		if (appId != null) {
			// TODO: packageVersion = saas.shop.mp.getMpPackageVersion(appId);
		}
		MpVersionRecord record = db().fetchAny(MP_VERSION,
				MP_VERSION.CURRENT_IN_USE.eq((byte) 1).and(MP_VERSION.PACKAGE_VERSION.eq(packageVersion)));
		if (record == null && packageVersion != (byte) 1) {
			record = db().fetchAny(MP_VERSION,
					MP_VERSION.CURRENT_IN_USE.eq((byte) 1).and(MP_VERSION.PACKAGE_VERSION.eq((byte) 1)));
		}
		return record;
	}

	/**
	 * 得到当前使用的模板Id
	 * 
	 * @param appId
	 * @param packageVersion
	 * @return
	 */
	public Integer getCurrentUseTemplateId(String appId, Byte packageVersion) {
		MpVersionRecord record = getCurrentUseVersion(appId, packageVersion);
		return record != null ? record.getTemplateId() : 0;
	}
	
	/**
	 * 得到当前使用的模板Id
	 * @param appId
	 * @return
	 */
	public Integer getCurrentUseTemplateId(String appId) {
		return  getCurrentUseTemplateId(appId, PACK_VERSION_NORMAL);
	}
	
	/**
	 * 得到当前使用的模板Id
	 * @return
	 */
	public Integer getCurrentUseTemplateId() {
		return  getCurrentUseTemplateId(null, PACK_VERSION_NORMAL);
	}

	/**
	 * 设置当前可用版本
	 * 
	 * @param templateId
	 * @return
	 */
	public void setCurrentUseTemplateId(Integer templateId) {
		MpVersionRecord version = getRow(templateId);
		if (version != null) {
			db().update(MP_VERSION).set(MP_VERSION.CURRENT_IN_USE, (byte) 0).where(MP_VERSION.CURRENT_IN_USE
					.eq((byte) 1).and(MP_VERSION.PACKAGE_VERSION.eq(version.getPackageVersion()))).execute();

			db().update(MP_VERSION).set(MP_VERSION.CURRENT_IN_USE, (byte) 1)
					.where(MP_VERSION.TEMPLATE_ID.eq(templateId)).execute();
		}
	}

	/**
	 * 得到小程序模板版本列表
	 * @return
	 */
	public Result<MpVersionRecord> getAll() {
		return db().selectFrom(MP_VERSION).orderBy(MP_VERSION.TEMPLATE_ID.desc()).fetch();
	}

	/**
	 * 得到模板ID的版本记录
	 * 
	 * @param templateId
	 * @return
	 */
	public MpVersionRecord getRow(Integer templateId) {
		return db().fetchAny(MP_VERSION, MP_VERSION.TEMPLATE_ID.eq(templateId));
	}
}
