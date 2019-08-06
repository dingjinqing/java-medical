package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpOperateLog.MP_OPERATE_LOG;
import static com.vpu.mp.db.main.tables.MpVersion.MP_VERSION;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionListParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionVo;

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
	 * 操作成功
	 */
	public static final Byte OP_STATE_SUCCESS = 1;
	
	/**
	 * 操作失败
	 */
	public static final Byte OP_STATE_FAILED = 2;

	/**
	 * 记录小程序相关Log
	 * 
	 * @param appId       小程序appId
	 * @param templateId  模板Id
	 * @param operateType 操作类型
	 * @param operateState 操作状态 1 成功 2 失败
	 * @param memo        记录失败原因
	 * @return
	 */
	public int log(String appId, Integer templateId, Byte operateType, Byte operateState, String memo) {
		return db()
				.insertInto(MP_OPERATE_LOG, MP_OPERATE_LOG.APP_ID, MP_OPERATE_LOG.TEMPLATE_ID,
						MP_OPERATE_LOG.OPERATE_TYPE, MP_OPERATE_LOG.OPERATE_STATE,MP_OPERATE_LOG.MEMO)
				.values(appId, templateId, operateType, operateState,memo).execute();
	}
	
//	/**
//	 * 查询版本分页
//	 * 
//	 * @param param
//	 * @return
//	 */
//	public PageResult<MpVersionVo> getPageList(MpVersionListParam param) {
//		SelectWhereStep<Record> select = db().select().from(MP_VERSION);
//		select.orderBy(MP_VERSION.TEMPLATE_ID.desc());
//		return this.getPageResult(select, param.page, MpVersionVo.class);
//	}
}
