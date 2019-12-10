package com.vpu.mp.service.shop.store.comment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.CommentServiceRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.comment.CommentFlagEnum;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentPageListParam;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author 黄荣刚
 * @date 2019年7月18日
 *服务评价相关方法
 */
@Service
@Slf4j
public class ServiceCommentService extends ShopBaseService {
    /**
     * The Domain config.
     */
    @Autowired
    public DomainConfig domainConfig;

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
		select.orderBy(COMMENT_SERVICE.CREATE_TIME);
        PageResult<ServiceCommentVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), ServiceCommentVo.class);
        result.dataList.forEach((e) -> {
            e.setServiceImg(imgDomain(e.getServiceImg()));
            e.setCommImg(imgDomain(e.getCommImg()));
        });
        return result;
    }

    public String imgDomain(String imgs) {
        List<String> imgList = Util.json2Object(imgs, new TypeReference<List<String>>() {
        }, false);
        if (CollectionUtils.isNotEmpty(imgList)) {
            return domainConfig.imageUrl(imgList.get(INTEGER_ONE));
        } else {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
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

    /**
     * Gets comment by service id.获取服务所有评论(只选择评审通过的)
     *
     * @param serviceId the service id
     * @return the comment by service id
     */
    public List<ServiceCommentVo> getCommentByServiceId(Integer serviceId) {
        return db().select(COMMENT_SERVICE.asterisk()).from(COMMENT_SERVICE)
            .where(COMMENT_SERVICE.SERVICE_ID.eq(serviceId))
            .and(COMMENT_SERVICE.FLAG.eq(BYTE_ONE))
            .and(COMMENT_SERVICE.DEL_FLAG.eq(BYTE_ZERO))
            .fetchInto(ServiceCommentVo.class);
    }

    /**
     * Gets newestcomment.获取服务的最新评论(只选择评审通过的)
     *
     * @param serviceId the service id
     * @return the newestcomment
     */
    public ServiceCommentVo getNewestcomment(Integer serviceId) {
        return db().select(COMMENT_SERVICE.asterisk()).from(COMMENT_SERVICE)
            .where(COMMENT_SERVICE.SERVICE_ID.eq(serviceId))
            .and(COMMENT_SERVICE.DEL_FLAG.eq(BYTE_ZERO))
            .and(COMMENT_SERVICE.FLAG.eq(BYTE_ONE))
            .orderBy(COMMENT_SERVICE.CREATE_TIME.desc())
            .fetchOneInto(ServiceCommentVo.class);
    }

    /**
     * Is comment boolean.订单是否已评价
     *
     * @param orderSn the order sn
     * @return the boolean
     */
    public boolean isComment(String orderSn) {
        return db().fetchExists(COMMENT_SERVICE, COMMENT_SERVICE.ORDER_SN.eq(orderSn));
    }

    /**
     * Gets comment by order id.获取订单评价(审核状态为已通过的)
     *
     * @param orderSn the order sn
     * @return the comment by order id
     */
    public ServiceCommentVo getCommentByOrderSn(String orderSn) {
        ServiceCommentVo vo = db().select(COMMENT_SERVICE.asterisk(), SERVICE_ORDER.SERVICE_DATE, SERVICE_ORDER.SERVICE_PERIOD).from(COMMENT_SERVICE)
            .leftJoin(SERVICE_ORDER).on(COMMENT_SERVICE.ORDER_SN.eq(SERVICE_ORDER.ORDER_SN))
            .where(COMMENT_SERVICE.ORDER_SN.eq(orderSn)).and(COMMENT_SERVICE.FLAG.eq(BYTE_ONE))
            .limit(INTEGER_ONE)
            .fetchOneInto(ServiceCommentVo.class);
        if (Objects.nonNull(vo)) {
            // 图片加域名处理
            String imgs = vo.getCommImg();
            List<String> stringList = Util.json2Object(imgs, new TypeReference<List<String>>() {
            }, false);
            if (CollectionUtils.isEmpty(stringList)) {
                return vo;
            }
            stringList = stringList.stream().map(domainConfig::imageUrl).collect(Collectors.toList());
            log.debug("评论图片有：{}", stringList.toString());
            vo.setCommImg(Util.toJson(stringList));
        }
        return vo;
    }


    /**
     * Create comment.添加评价
     *
     * @param record the record
     */
    public void createComment(CommentServiceRecord record) {
        db().executeInsert(record);
    }

}
