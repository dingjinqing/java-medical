package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.BackProcess.BACK_PROCESS;

import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.BackProcessRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.mp.MpOperateVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpUploadListParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpUploadListVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionVo;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年9月6日 下午4:16:36
 */
@Service
public class MpBackProcessService extends MainBaseService {

	public int updateProgress(Integer recId, Short progress, String progressInfo) {
		if (!StringUtils.isEmpty(progressInfo)) {
			return db().update(BACK_PROCESS).set(BACK_PROCESS.PROGRESS, progress)
					.set(BACK_PROCESS.PROGRESS_INFO, progressInfo).where(BACK_PROCESS.REC_ID.eq(recId)).execute();
		} else {
			return db().update(BACK_PROCESS).set(BACK_PROCESS.PROGRESS, progress).where(BACK_PROCESS.REC_ID.eq(recId))
					.execute();
		}
	}

	public int fail(Integer recId, String failReason) {
		return db().update(BACK_PROCESS).set(BACK_PROCESS.FAIL_REASON, failReason).set(BACK_PROCESS.STATE, (byte) 3)
				.where(BACK_PROCESS.REC_ID.eq(recId)).execute();
	}

	public int insertRow(BackProcessRecord record) {
		return db().executeInsert(record);
	}
	
	public int finish(Integer recId) {
		return db().update(BACK_PROCESS).set(BACK_PROCESS.STATE, (byte)2).where(BACK_PROCESS.REC_ID.eq(recId)).execute();
	}
	
	public int updateRow(Integer recId, Integer jobCode, String jobMessage,String jobResult) {
		return db().update(BACK_PROCESS).set(BACK_PROCESS.JOB_CODE, jobCode)
				.set(BACK_PROCESS.JOB_MESSAGE, jobMessage).set(BACK_PROCESS.JOB_RESULT, jobResult).where(BACK_PROCESS.REC_ID.eq(recId)).execute();
	}

	/**
	 * 返回主键
	 * 
	 * @param vo
	 * @param className
	 * @param processId
	 * @return
	 */
	public int insertByInfo(MpVersionVo vo, String className, Integer processId) {
		BackProcessRecord bProcessRecord = db().newRecord(BACK_PROCESS);
		bProcessRecord.setShopId(0);
		bProcessRecord.setProcessId(processId);
		bProcessRecord.setClassName(className);
		bProcessRecord.setParameters(Util.toJson(vo));
		bProcessRecord.setState((byte) 0);
		bProcessRecord.setOnlyRunOne((byte) 1);
		bProcessRecord.setProgress((short) 0);
		bProcessRecord.setProgressInfo("开始提交");
		bProcessRecord.setJobName("批量提交小程序审核");
		bProcessRecord.insert();
		return bProcessRecord.getRecId();
	}
	
	/**
	 * 查询任务列表
	 * 查询所有传 -1
	 * @param state
	 * @return
	 */
	public PageResult<MpUploadListVo> getPageList(MpUploadListParam param) {
		SelectWhereStep<BackProcessRecord> selectFrom = db().selectFrom(BACK_PROCESS);
		Byte state=param.getState();
		if (!StringUtils.isEmpty(state)) {
			if (state != -1) {
				selectFrom.where(BACK_PROCESS.STATE.eq(state));
			}
			return  this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), MpUploadListVo.class);
		}
		return null;
	}
}
