package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.COMMENT_GOODS;
import static com.vpu.mp.db.shop.Tables.COMMENT_GOODS_ANSWER;
import static com.vpu.mp.db.shop.Tables.LOTTERY_RECORD;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.Tables.USER;
import static org.jooq.impl.DSL.field;

import java.sql.Timestamp;

import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsComment;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentAnswer;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheck;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentCheckPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentConfig;

/**
 * 商品评价
 *
 * @author liangchen
 * @date 2019年7月7日
 */
public class GoodsCommentService extends BaseService {

    /**
     * 分页获取评价信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsComment> getPageList(GoodsCommentPageListParam param) {
        SelectConditionStep<Record12<Integer,String,Byte, String, String,Timestamp, String,  String, String,String, Byte,String>> selectFrom = db()
        		.select(COMMENT_GOODS.ID,
        		COMMENT_GOODS.ORDER_SN,COMMENT_GOODS.COMMSTAR,
        		COMMENT_GOODS.COMM_NOTE,COMMENT_GOODS_ANSWER.CONTENT,COMMENT_GOODS.CREATE_TIME,
        		ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_IMG,USER.USERNAME,USER.MOBILE,COMMENT_GOODS.ANONYMOUSFLAG,LOTTERY_RECORD.LOTTERY_AWARD)
        		.from(COMMENT_GOODS,ORDER_GOODS,USER,LOTTERY_RECORD,COMMENT_GOODS_ANSWER)
        		.where(COMMENT_GOODS.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
        		.and(COMMENT_GOODS.USER_ID.eq(USER.USER_ID))
        		.and(COMMENT_GOODS.ORDER_SN.eq(LOTTERY_RECORD.ORDER_SN))
        		.and(COMMENT_GOODS.ID.eq(COMMENT_GOODS_ANSWER.COMMENT_ID));

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy( COMMENT_GOODS.CREATE_TIME.desc());

        PageResult<GoodsComment> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsComment.class);

        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param selectFrom
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectConditionStep<Record12<Integer,String, Byte,String, String,Timestamp,  String, String, String,String,Byte, String>> selectFrom, GoodsCommentPageListParam param) {
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
            scs = scs.and(field("commstar").eq(param.getCommstar()));
        }

        return scs;
    }

    /**
     * 分页获取评价审核信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsCommentCheck> getCheckPageList(GoodsCommentCheckPageListParam param) {
    	SelectConditionStep<Record13<Integer,String, String,  String,  String ,String,Byte, String,String, String,Timestamp,Byte,Byte>> selectFrom = db()
        		.select(COMMENT_GOODS.ID,
        		COMMENT_GOODS.ORDER_SN,ORDER_GOODS.GOODS_IMG,ORDER_GOODS.GOODS_NAME,USER.USERNAME,USER.MOBILE,
        		COMMENT_GOODS.COMMSTAR,COMMENT_GOODS.COMM_NOTE,LOTTERY_RECORD.LOTTERY_AWARD,COMMENT_GOODS_ANSWER.CONTENT,COMMENT_GOODS.CREATE_TIME,
        		COMMENT_GOODS.ANONYMOUSFLAG,COMMENT_GOODS.FLAG)        		
        		.from(COMMENT_GOODS,ORDER_GOODS,USER,LOTTERY_RECORD,COMMENT_GOODS_ANSWER)
        		.where(COMMENT_GOODS.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
        		.and(COMMENT_GOODS.USER_ID.eq(USER.USER_ID))
        		.and(COMMENT_GOODS.ORDER_SN.eq(LOTTERY_RECORD.ORDER_SN))
        		.and(COMMENT_GOODS.ID.eq(COMMENT_GOODS_ANSWER.COMMENT_ID));

        SelectConditionStep<?> select = this.buildCheckOptions(selectFrom, param);

        select.orderBy( COMMENT_GOODS.CREATE_TIME.desc());

        PageResult<GoodsCommentCheck> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsCommentCheck.class);

        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param selectFrom
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildCheckOptions(SelectConditionStep<Record13<Integer,String, String,  String,  String ,String,Byte, String,String, String,Timestamp,Byte,Byte>> selectFrom, GoodsCommentCheckPageListParam param) {
        SelectConditionStep<?> scs = selectFrom
                .and(COMMENT_GOODS.DEL_FLAG.eq((byte) GoodsCommentCheckPageListParam.IS_DELETE_DEFAULT_VALUE));

        if (!StringUtils.isBlank(param.getOrderSn())) {
            scs = scs.and(COMMENT_GOODS.ORDER_SN.like(this.likeValue(param.getOrderSn())));
        }
        
        if (!StringUtils.isBlank(param.getGoodsName())) {
            scs = scs.and(ORDER_GOODS.GOODS_NAME.like(this.likeValue(param.getGoodsName())));
        }
        
        if (!StringUtils.isBlank(param.getMobile())) {
            scs = scs.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
        }

        if (param.getCommstar() != GoodsCommentCheckPageListParam.COMMSTAR_DEFAULT_VALUE) {
            scs = scs.and(field("commstar").eq(param.getCommstar()));
        }
        
        if (param.getFlag() != GoodsCommentCheckPageListParam.FLAG_DEFAULT_VALUE) {
            scs = scs.and(field("flag").eq(param.getFlag()));
        }

        return scs;
    }
    
    /**
     * 假删除指定评价
     *
     * @param goodsComment
     * @return 数据库受影响行数
     */
    public int delete(GoodsComment goodsComment) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.DEL_FLAG, (byte) 1).where(COMMENT_GOODS.ID.eq(goodsComment.getId()))
                .execute();
    }
    
    /**
     * 评价回复
     *
     * @param goodsComment
     * @return 数据库受影响行数
     */
	public int insertAnswer(GoodsCommentAnswer goodsCommentAnswer) {
		 int result = db()
	                .insertInto(COMMENT_GOODS_ANSWER, COMMENT_GOODS_ANSWER.COMMENT_ID, COMMENT_GOODS_ANSWER.CONTENT)
	                .values(goodsCommentAnswer.getCommentId(),goodsCommentAnswer.getContent())
	                .execute();
	        return result;
	}


  
    /**
     * 修改评价审核状态
     *
     * @param goodsComment
     * @return
     */
    public int passflag(GoodsComment goodsComment) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.FLAG,(byte)GoodsCommentCheckPageListParam.FLAG_PASS_VALUE)
                .where(COMMENT_GOODS.ID.eq(goodsComment.getId()))
                .execute();
    }
    
    public int refuseflag(GoodsComment goodsComment) {
        return db().update(COMMENT_GOODS).set(COMMENT_GOODS.FLAG,(byte)GoodsCommentCheckPageListParam.FLAG_REFUSE_VALUE)
                .where(COMMENT_GOODS.ID.eq(goodsComment.getId()))
                .execute();
    }

}
