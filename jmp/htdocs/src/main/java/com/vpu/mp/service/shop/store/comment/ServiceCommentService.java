package com.vpu.mp.service.shop.store.comment;

import static com.vpu.mp.db.shop.Tables.COMMENT_SERVICE;
import static com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;

import java.util.List;
import java.util.Optional;

import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.comment.CommentFlagEnum;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentPageListParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;
/**
 * @author 黄荣刚
 * @date 2019年7月18日
 *服务评价相关方法
 */
public class ServiceCommentService extends BaseService {
	
	/**
	 * 分页查询服务评价列表
	 * @param param
	 * @return
	 */
	public PageResult<ServiceCommentVo> getPageList(ServiceCommentPageListParam param){
		SelectOnConditionStep<?> selectFrom = db().select(COMMENT_SERVICE.ID, COMMENT_SERVICE.STORE_ID,COMMENT_SERVICE.ORDER_SN,COMMENT_SERVICE.SERVICE_ID,STORE_SERVICE.SERVICE_IMG, STORE_SERVICE.SERVICE_NAME,USER.USER_ID,USER.USERNAME, USER.MOBILE,COMMENT_SERVICE.COMMSTAR, COMMENT_SERVICE.COMM_NOTE, COMMENT_SERVICE.COMM_IMG, COMMENT_SERVICE.TECHNICIAN_ID, SERVICE_TECHNICIAN.TECHNICIAN_NAME, COMMENT_SERVICE.CREATE_TIME, COMMENT_SERVICE.ANONYMOUSFLAG, COMMENT_SERVICE.FLAG)
			.from(COMMENT_SERVICE)
			.leftJoin(STORE_SERVICE)
			.on(COMMENT_SERVICE.SERVICE_ID.eq(STORE_SERVICE.ID))
			.leftJoin(USER)
			.on(COMMENT_SERVICE.USER_ID.eq(USER.USER_ID))
			.leftJoin(SERVICE_TECHNICIAN)
			.on(COMMENT_SERVICE.TECHNICIAN_ID.eq(SERVICE_TECHNICIAN.ID));
		SelectConditionStep<?> select = buildOptions(selectFrom,param);
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), ServiceCommentVo.class);
	}

	/**
	 * @param selectFrom
	 * @param param 
	 * @return 
	 */
	private SelectConditionStep<?> buildOptions(SelectOnConditionStep<?> selectFrom, ServiceCommentPageListParam param) {
		SelectConditionStep<?> select = selectFrom.where(COMMENT_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		if(!StringUtils.isBlank(param.getOrderSn()))	{
			select = select.and(COMMENT_SERVICE.ORDER_SN.like(this.likeValue(param.getOrderSn())));
		}
		if(!StringUtils.isBlank(param.getServiceName())) {
			select = select.and(STORE_SERVICE.SERVICE_NAME.like(this.likeValue(param.getServiceName())));
		}
		if(param.getStoreId() != null) {
			select = select.and(COMMENT_SERVICE.STORE_ID.eq(param.getStoreId()));
		}
		if(!StringUtils.isBlank(param.getMobile())) {
			select = select.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if(!StringUtils.isBlank(param.getTechnicianName())) {
			select = select.and(SERVICE_TECHNICIAN.TECHNICIAN_NAME.like(this.likeValue(param.getTechnicianName())));
		}
		if(param.getCommstar() != null) {
			select = select.and(COMMENT_SERVICE.COMMSTAR.eq(param.getCommstar()));
		}
		if(param.getFlag() != null) {
			select = select.and(COMMENT_SERVICE.FLAG.eq(param.getFlag()));
		}
		return select;
	}

	/**
	 * 批量将传入ID的评论置为删除状态
	 * @param commentIdList
	 * @return
	 */
	public int batchDelete(List<Integer> commentIdList) {
		if(commentIdList == null) {
			return 0;
		}
		this.transaction(()->{
			db().update(COMMENT_SERVICE)
				.set(COMMENT_SERVICE.DEL_FLAG, DelFlag.DISABLE_VALUE)
				.where(COMMENT_SERVICE.ID.in(commentIdList))
				.and(COMMENT_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).execute();
		});
		return 0;
	}

	/**
	 * 批量将传入ID的评论置为通过状态
	 * @param commentIdList
	 * @return
	 */
	public int batchPass(List<Integer> commentIdList) {
		if(commentIdList == null) {
			return 0;
		}
		this.transaction(()->{
			db().update(COMMENT_SERVICE)
				.set(COMMENT_SERVICE.FLAG,CommentFlagEnum.PASS.getValue())
				.where(COMMENT_SERVICE.ID.in(commentIdList))
				.and(COMMENT_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).execute();
		});
		return 0;
	}
	
	/**
	 * 批量将传入ID的评论置为拒绝通过状态
	 * @param commentIdList
	 * @return
	 */
	public int batchRefuse(List<Integer> commentIdList) {
		if(commentIdList == null) {
			return 0;
		}
		this.transaction(()->{
			db().update(COMMENT_SERVICE)
				.set(COMMENT_SERVICE.FLAG,CommentFlagEnum.REFUSE.getValue())
				.where(COMMENT_SERVICE.ID.in(commentIdList))
				.and(COMMENT_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).execute();
		});
		return 0;
	}
}
