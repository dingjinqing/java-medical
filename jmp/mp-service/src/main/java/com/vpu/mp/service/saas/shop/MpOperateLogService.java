package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpOperateLog.MP_OPERATE_LOG;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;

/**
 * 
 * @author lixinguo
 *
 */
@Service
public class MpOperateLogService extends MainBaseService {

	/**
	 * 设置服务器域名
	 */
	public static final Byte OP_TYPE_MODIFY_DOMAIN = 1;

	/**
	 * 上传代码
	 */
	public static final Byte OP_TYPE_UPLOAD_CODE = 2;

	/**
	 * 添加体验者
	 */
	public static final Byte OP_TYPE_ADD_TESTER = 3;

	/**
	 * 删除体验者
	 */
	public static final Byte OP_TYPE_DEL_TESTER = 4;

	/**
	 * 获取体验者二维码
	 */
	public static final Byte OP_TYPE_GET_TESTER_QR = 5;

	/**
	 * 获取可选类目
	 */
	public static final Byte OP_TYPE_GET_CATEGORY = 6;

	/**
	 * 获取页面配置
	 */
	public static final Byte OP_TYPE_GET_PAGE_CFG = 7;

	/**
	 * 提交审核
	 */
	public static final Byte OP_TYPE_SUBMIT_AUDIT = 8;

	/**
	 * 发布代码
	 */
	public static final Byte OP_TYPE_PUBLISH_CODE = 9;

	/**
	 * 审核成功通知
	 */
	public static final Byte OP_TYPE_AUDIT_SUCCESS = 10;

	/**
	 * 审核失败通知
	 */
	public static final Byte OP_TYPE_AUDIT_FAILED = 11;

	/**
	 * 授权通知
	 */
	public static final Byte OP_TYPE_AUTH_OK = 12;

	/**
	 * 取消授权通知
	 */
	public static final Byte OP_TYPE_CANCEL_AUTH = 13;

	/**
	 * 记录小程序相关Log
	 * 
	 * @param appId       小程序appId
	 * @param templateId  模板Id
	 * @param operateType 操作类型
	 * @param memo        备注
	 * @return
	 */
	public int log(String appId, Long templateId, Byte operateType, String memo) {
		return db()
				.insertInto(MP_OPERATE_LOG, MP_OPERATE_LOG.APP_ID, MP_OPERATE_LOG.TEMPLATE_ID,
						MP_OPERATE_LOG.OPERATE_TYPE, MP_OPERATE_LOG.MEMO)
				.values(appId, templateId.intValue(), operateType, memo).execute();
	}
}
