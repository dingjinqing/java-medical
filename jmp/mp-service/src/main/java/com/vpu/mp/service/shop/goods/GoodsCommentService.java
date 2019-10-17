package com.vpu.mp.service.shop.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.data.DelFlag;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddCommParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAddListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswerParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckListVo;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentIdParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentVo;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 商品评价
 *
 * @author liangchen
 * @date 2019年7月7日
 */
@Service
public class GoodsCommentService extends ShopBaseService {

    /**
     * 分页获取评价信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsCommentVo> getPageList(GoodsCommentPageListParam param) {

        SelectConditionStep<? extends Record> select = (SelectConditionStep<? extends Record>) db().select(COMMENT_GOODS.ID,
                COMMENT_GOODS.ORDER_SN,
                COMMENT_GOODS.COMMSTAR,
                COMMENT_GOODS.COMM_NOTE,
                COMMENT_GOODS.CREATE_TIME,
                COMMENT_GOODS.ANONYMOUSFLAG,
                COMMENT_GOODS.BOGUS_USERNAME,
                GOODS.GOODS_NAME,
                GOODS.GOODS_IMG,
                USER.USERNAME,
                USER.MOBILE,
                COMMENT_AWARD.NAME,
                COMMENT_AWARD.AWARD_TYPE,
                COMMENT_AWARD.SCORE,
                COMMENT_AWARD.ACCOUNT)
                .from(COMMENT_GOODS)
                .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(COMMENT_GOODS.GOODS_ID))
                .leftJoin(USER).on(USER.USER_ID.eq(COMMENT_GOODS.USER_ID))
                .leftJoin(COMMENT_AWARD).on(COMMENT_GOODS.COMMENT_AWARD_ID.eq(COMMENT_AWARD.ID));
        this.buildOptions(select, param);
        select.orderBy( COMMENT_GOODS.CREATE_TIME.desc());

        PageResult<GoodsCommentVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsCommentVo.class);
        for (GoodsCommentVo vo: pageResult.dataList) {
            String content = db().select(COMMENT_GOODS_ANSWER.CONTENT).from(COMMENT_GOODS_ANSWER)
                .where(COMMENT_GOODS_ANSWER.COMMENT_ID.eq(vo.getId()))
                .and(COMMENT_GOODS_ANSWER.DEL_FLAG.eq((byte)GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE))
                .fetchOptionalInto(String.class).orElse(null);
            vo.setContent(content);
        }
        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param select
     * @param param
     */
    private SelectConditionStep<?> buildOptions(SelectConditionStep<? extends Record> select, GoodsCommentPageListParam param) {
        SelectConditionStep<?> scs = select.and(COMMENT_GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

        if (!StringUtils.isBlank(param.getOrderSn())) {
            scs = scs.and(COMMENT_GOODS.ORDER_SN.like(this.likeValue(param.getOrderSn())));
        }
        
        if (!StringUtils.isBlank(param.getGoodsName())) {
            scs = scs.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
        }
        
        if (!StringUtils.isBlank(param.getMobile())) {
            scs = scs.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
        }

        if (param.getCommstar() != GoodsCommentPageListParam.COMMSTAR_DEFAULT_VALUE) {
            scs = scs.and(COMMENT_GOODS.COMMSTAR.eq(param.getCommstar()));
        }
        if (param.getAwardActivityId()!=null){
            scs.and(COMMENT_GOODS.COMMENT_AWARD_ID.eq(param.getAwardActivityId()));
        }
        return scs;
    }

    /**
     * 分页获取评价审核信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsCommentCheckListVo> getCheckPageList(GoodsCommentPageListParam param) {
    	SelectConditionStep<Record13<Integer,String, String,  String,  String ,String,Byte, String,String, String,Timestamp,Byte,Byte>> selectFrom = db()
        		.select(COMMENT_GOODS.ID,
        		COMMENT_GOODS.ORDER_SN,ORDER_GOODS.GOODS_IMG,ORDER_GOODS.GOODS_NAME,USER.USERNAME,USER.MOBILE,
        		COMMENT_GOODS.COMMSTAR,COMMENT_GOODS.COMM_NOTE,LOTTERY_RECORD.LOTTERY_AWARD,COMMENT_GOODS_ANSWER.CONTENT,COMMENT_GOODS.CREATE_TIME,
        		COMMENT_GOODS.ANONYMOUSFLAG,COMMENT_GOODS.FLAG)        		
        		.from(COMMENT_GOODS,ORDER_GOODS,USER,LOTTERY_RECORD,COMMENT_GOODS_ANSWER)
        		.where(COMMENT_GOODS.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
        		.and(COMMENT_GOODS.USER_ID.eq(USER.USER_ID))
        		.and(COMMENT_GOODS.ORDER_SN.eq(LOTTERY_RECORD.ORDER_SN))
        		.and(COMMENT_GOODS.ID.eq(COMMENT_GOODS_ANSWER.COMMENT_ID)
        		.and(COMMENT_GOODS_ANSWER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)));

        SelectConditionStep<?> select = this.buildCheckOptions(selectFrom, param);

        select.orderBy( COMMENT_GOODS.CREATE_TIME.desc());

        PageResult<GoodsCommentCheckListVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsCommentCheckListVo.class);

        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param selectFrom
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildCheckOptions(SelectConditionStep<Record13<Integer,String, String,  String,  String ,String,Byte, String,String, String,Timestamp,Byte,Byte>> selectFrom, GoodsCommentPageListParam param) {
        SelectConditionStep<?> scs = selectFrom
                .and(COMMENT_GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

        if (!StringUtils.isBlank(param.getOrderSn())) {
            scs = scs.and(COMMENT_GOODS.ORDER_SN.like(this.likeValue(param.getOrderSn())));
        }
        
        if (!StringUtils.isBlank(param.getGoodsName())) {
            scs = scs.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
        }
        
        if (!StringUtils.isBlank(param.getMobile())) {
            scs = scs.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
        }

        if (param.getCommstar() != GoodsCommentPageListParam.COMMSTAR_DEFAULT_VALUE) {
            scs = scs.and(COMMENT_GOODS.COMMSTAR.eq((byte)param.getCommstar()));
        }
        
        if (param.getFlag() != GoodsCommentPageListParam.FLAG_DEFAULT_VALUE) {
            scs = scs.and(COMMENT_GOODS.FLAG.eq((byte)param.getFlag()));
        }

        return scs;
    }
    
    /**
     * 假删除指定评价
     *
     * @param goodsCommentId
     * @return 数据库受影响行数
     */
    public int delete(GoodsCommentIdParam goodsCommentId) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.DEL_FLAG, (byte) 1).where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
                .execute();
    }
    
    /**
     * 评价回复
     *
     * @param goodsCommentAnswer
     * @return 数据库受影响行数
     */
	public int insertAnswer(GoodsCommentAnswerParam goodsCommentAnswer) {
		 int result = db()
	                .insertInto(COMMENT_GOODS_ANSWER, COMMENT_GOODS_ANSWER.COMMENT_ID, COMMENT_GOODS_ANSWER.CONTENT)
	                .values(goodsCommentAnswer.getCommentId(),goodsCommentAnswer.getContent())
	                .execute();
	        return result;
	}

	 /**
     * 删除评价回复
     *
     * @param goodsCommentId
     * @return 
     */
	public void delAnswer(GoodsCommentIdParam goodsCommentId) {
		db().update(COMMENT_GOODS_ANSWER).set(COMMENT_GOODS_ANSWER.DEL_FLAG, (byte) 1)
		.where(COMMENT_GOODS_ANSWER.COMMENT_ID.eq(goodsCommentId.getId()))
		.and(COMMENT_GOODS_ANSWER.DEL_FLAG.eq((byte)0))
        .execute();
	}
  
    /**
     * 修改评价审核状态
     *
     * @param goodsCommentId
     * @return
     */
    public int passflag(GoodsCommentIdParam goodsCommentId) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.FLAG,(byte)GoodsCommentPageListParam.FLAG_PASS_VALUE)
                .where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
                .execute();
    }
    
    public int refuseflag(GoodsCommentIdParam goodsCommentId) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.FLAG,(byte)GoodsCommentPageListParam.FLAG_REFUSE_VALUE)
                .where(COMMENT_GOODS.ID.eq(goodsCommentId.getId()))
                .execute();
    }
    
    /**
     * 分页查询添加评论列表
     *
     * @param param
     * @return
     */
    public PageResult<GoodsCommentAddListVo> getAddList(GoodsCommentPageListParam param) {
		
    	SelectConditionStep<Record10<Integer, String, String, String,String,  BigDecimal, Integer, Integer, Integer, Integer>> selectFrom = db()
    			.select(GOODS.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, GOODS.GOODS_SN,SORT.SORT_NAME, GOODS.SHOP_PRICE, 
    					GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM, GOODS_SUMMARY.UV, GOODS_SUMMARY.PV)
    			.from(GOODS,SORT,GOODS_SUMMARY)
    			.where(GOODS.SORT_ID.eq(SORT.SORT_ID))
    			.and(GOODS.GOODS_ID.eq(GOODS_SUMMARY.GOODS_ID));

        SelectConditionStep<?> select = this.buildAddOptions(selectFrom, param);

        PageResult<GoodsCommentAddListVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsCommentAddListVo.class);

    	return pageResult;
	}
    
    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param selectFrom
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildAddOptions(SelectConditionStep<Record10<Integer, String,String, String, String,  BigDecimal, Integer, Integer, Integer, Integer>> selectFrom, GoodsCommentPageListParam param) {
        SelectConditionStep<?> scs = selectFrom
                .and(GOODS.DEL_FLAG.eq((byte) GoodsCommentPageListParam.IS_DELETE_DEFAULT_VALUE));

        if (!StringUtils.isBlank(param.getGoodsName())) {
            scs = scs.and(GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
        }
        
        if (param.getSortName() != GoodsCommentPageListParam.SORTNAME_DEFAULT_VALUE) {
            scs = scs.and(SORT.SORT_NAME.eq(param.getSortName()));
        }
        
        return scs;
    }
    
    /**
     * 商家手动添加评价
     *
     * @param goodsCommentAddComm
     * @return 数据库受影响行数
     */
	public int addComment(GoodsCommentAddCommParam goodsCommentAddComm) {
		
		 int result = db()
	                .insertInto(COMMENT_GOODS,COMMENT_GOODS.SHOP_ID,COMMENT_GOODS.GOODS_ID,COMMENT_GOODS.BOGUS_USERNAME,COMMENT_GOODS.BOGUS_USER_AVATAR,
	                		COMMENT_GOODS.CREATE_TIME, COMMENT_GOODS.COMMSTAR,COMMENT_GOODS.COMM_NOTE,COMMENT_GOODS.COMM_IMG,COMMENT_GOODS.ANONYMOUSFLAG)
	                .values(getShopId(),goodsCommentAddComm.getGoodsId(),goodsCommentAddComm.getBogusUsername(),goodsCommentAddComm.getBogusUserAvatar(),
	                		goodsCommentAddComm.getCreateTime(),goodsCommentAddComm.getCommstar(),goodsCommentAddComm.getCommNote(),goodsCommentAddComm.getCommImg(),goodsCommentAddComm.getAnonymousFlag())
	                .execute();
	    
		 return result;
	}

    /**
     * 统计商品评价数量
     * @param goodsIds 待统计商品id集合
     * @param config 评价配置： 0不审，1先发后审，2先审后发
     * @return
     */
    public Map<Integer, Integer> statisticGoodsComment(List<Integer> goodsIds, Byte config) {
        Map<Integer,Integer> results=null;
        if (config == 2) {
            results = db().select(COMMENT_GOODS.GOODS_ID, DSL.count(COMMENT_GOODS.ID).as("num")).from(COMMENT_GOODS)
                .where(COMMENT_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(COMMENT_GOODS.GOODS_ID.in(goodsIds))
                .groupBy(COMMENT_GOODS.GOODS_ID).fetchMap(COMMENT_GOODS.GOODS_ID, DSL.field("num",Integer.class));
        } else {
            results = db().select(COMMENT_GOODS.GOODS_ID, DSL.count(COMMENT_GOODS.ID).as("num")).from(COMMENT_GOODS)
                .where(COMMENT_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(COMMENT_GOODS.GOODS_ID.in(goodsIds)).and(COMMENT_GOODS.FLAG.eq((byte) 1))
                .groupBy(COMMENT_GOODS.GOODS_ID).fetchMap(COMMENT_GOODS.GOODS_ID, DSL.field("num",Integer.class));
        }
        return results;
    }
}
