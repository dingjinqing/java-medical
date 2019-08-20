package com.vpu.mp.service.shop.market.commentaward;

import com.vpu.mp.db.shop.tables.records.CommentAwardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.commentaward.CommentAwardListParam;
import com.vpu.mp.service.pojo.shop.market.commentaward.CommentAwardListVo;
import com.vpu.mp.service.pojo.shop.market.commentaward.CommentAwardParam;
import com.vpu.mp.service.pojo.shop.market.commentaward.CommentAwardVo;
import lombok.Data;
import org.jooq.Record;
import org.jooq.SelectConditionStep;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.COMMENT_AWARD;

/**
 * @author 孔德成
 * @date 2019/8/20 13:57
 */
@Data
public class CommentAwardService extends ShopBaseService {

    /**
     * 启用状态  0 停用 1 启用
     */
    private static final byte USE_STATUS = 1;
    private static final byte STOP_STATUS = 0;
    /**
     * 有效期 0 固定期限 1永久有效
     */
    private static final byte FIXED_EXPIRE = 0;
    private static final byte NEVER_EXPIRE  = 1;
    /**
     * 添加
     * @param param commentAward
     * @return  1成功
     */
    public int addCommentAwardActivity(CommentAwardParam param) {
        param.setId(null);
        CommentAwardRecord record  =db().newRecord(COMMENT_AWARD,param);
        return  record.insert();
    }

    /**
     * 跟新
     * @param param commentAward
     * @return 1 成功
     */
    public int updateCommentAwardActivity(CommentAwardParam param) {
        if (param.getId()!=null){
            CommentAwardRecord record  =db().newRecord(COMMENT_AWARD,param);
            return  record.update();
        }
        return 0;
    }

    /**
     * 跟新妆台1
     * @param id id
     * @return 1 成功
     */
    public int changeCommentAwardActivity(Integer id) {
        Byte status = db().select(COMMENT_AWARD.STATUS).from(COMMENT_AWARD)
                .where(COMMENT_AWARD.ID.eq(id)).fetchOne().component1();
        if (status!=null&&status==STOP_STATUS){
            return db().update(COMMENT_AWARD).set(COMMENT_AWARD.STATUS, USE_STATUS)
                    .where(COMMENT_AWARD.ID.eq(id)).execute();
        }else {
           return  db().update(COMMENT_AWARD).set(COMMENT_AWARD.STATUS,STOP_STATUS)
                    .where(COMMENT_AWARD.ID.eq(id)).execute();
        }
    }


    /**
     *  查询单个活动
     * @param id id
     * @return commentAwardVo
     */
    public CommentAwardVo getCommentAwardActivity(Integer id) {
       return  db().select().from(COMMENT_AWARD)
                .where(COMMENT_AWARD.ID.eq(id)).fetchOneInto(CommentAwardVo.class);
    }


    /**
     * 查询列表
     * @param param
     * @return
     */
    public PageResult<CommentAwardListVo> getCommentAwardActivityList(CommentAwardListParam param) {
        SelectConditionStep<? extends Record> select = db()
                .select(COMMENT_AWARD.NAME,
                        COMMENT_AWARD.GOODS_TYPE,
                        COMMENT_AWARD.AWARD_TYPE,
                        COMMENT_AWARD.IS_FOREVER,
                        COMMENT_AWARD.START_TIME,
                        COMMENT_AWARD.END_TIME,
                        COMMENT_AWARD.COMMENT_TYPE,
                        COMMENT_AWARD.LEVE,
                        COMMENT_AWARD.STATUS)
                .from(COMMENT_AWARD)
                .where(COMMENT_AWARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        buildParam(select,param);
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), CommentAwardListVo.class);
    }

    private void buildParam(SelectConditionStep<? extends Record> select, CommentAwardListParam param) {
        Timestamp nowTime =new Timestamp(System.currentTimeMillis());
        switch (param.getNavType()){
            case 1:
                select.and(COMMENT_AWARD.IS_FOREVER.eq(NEVER_EXPIRE))
                        .or(COMMENT_AWARD.START_TIME.lt(nowTime)
                        .and(COMMENT_AWARD.END_TIME.gt(nowTime)));
                break;
            case 2:
                select.and(COMMENT_AWARD.IS_FOREVER.eq(NEVER_EXPIRE))
                        .and(COMMENT_AWARD.START_TIME.gt(nowTime))
                        .and(COMMENT_AWARD.STATUS.eq(USE_STATUS));
                break;
            case 3:
                select.and(COMMENT_AWARD.IS_FOREVER.eq(FIXED_EXPIRE))
                        .and(COMMENT_AWARD.END_TIME.lt(nowTime))
                        .and(COMMENT_AWARD.STATUS.eq(USE_STATUS));
                break;
            case 4:
                select.and(COMMENT_AWARD.STATUS.eq(STOP_STATUS));
                break;
            default:
        }
    }

}
