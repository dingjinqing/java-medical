package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandPageListParam;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsComment;
import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentPageListParam;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrand;
import org.jooq.*;
import org.jooq.tools.StringUtils;

import java.sql.Timestamp;

import javax.print.DocFlavor.STRING;

import static com.vpu.mp.db.shop.Tables.COMMENT_GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.LOTTERY_RECORD;
import static org.jooq.impl.DSL.field;

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
        SelectConditionStep<Record10<String,Byte, String, Timestamp, String,  String, String,String, Byte,String>> selectFrom = db()
        		.select(
        		COMMENT_GOODS.ORDER_SN,COMMENT_GOODS.COMMSTAR,
        		COMMENT_GOODS.COMM_NOTE,COMMENT_GOODS.CREATE_TIME,
        		ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_IMG,USER.USERNAME,USER.MOBILE,COMMENT_GOODS.ANONYMOUSFLAG,LOTTERY_RECORD.LOTTERY_AWARD)
        		.from(COMMENT_GOODS,ORDER_GOODS,USER,LOTTERY_RECORD)
        		.where(COMMENT_GOODS.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
        		.and(COMMENT_GOODS.USER_ID.eq(USER.USER_ID))
        		.and(COMMENT_GOODS.ORDER_SN.eq(LOTTERY_RECORD.ORDER_SN));

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
    private SelectConditionStep<?> buildOptions(SelectConditionStep<Record10<String, Byte,String, Timestamp,  String, String, String,String,Byte, String>> selectFrom, GoodsCommentPageListParam param) {
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

        
      /*  

        if (param.getEndAddTime() != null) {
            scs = scs.and(GOODS_BRAND.CREATE_TIME.le(param.getEndAddTime()));
        }

        if (param.getClassifyId() != GoodsBrandPageListParam.CLASSIFY_ID_DEFAULT_VALUE) {
            scs = scs.and(field("classify_id").eq(param.getClassifyId()));
        }
*/
        if (param.getCommstar() != GoodsCommentPageListParam.COMMSTAR_DEFAULT_VALUE) {
            scs = scs.and(field("commstar").eq(param.getCommstar()));
        }

        return scs;
    }

    /**
     * 添加品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     *//*
    public int insert(GoodsBrand goodsBrand) {
        int result = db()
                .insertInto(GOODS_BRAND, GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME,
                        GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.DESC,
                        GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
                .values(goodsBrand.getBrandName(), goodsBrand.getEName(), goodsBrand.getLogo(), goodsBrand.getFirst(), goodsBrand.getDesc(),
                        goodsBrand.getIsRecommend(), goodsBrand.getClassifyId())
                .execute();
        return result;
    }

    *//**
     * 假删除指定品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     *//*
    public int delete(GoodsBrand goodsBrand) {
        return db().update(GOODS_BRAND).set(GOODS_BRAND.DEL_FLAG, (byte) 1).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .execute();
    }

    *//**
     * 更新指定商品
     *
     * @param goodsBrand
     * @return
     *//*
    public int update(GoodsBrand goodsBrand) {
        return db().update(GOODS_BRAND).set(GOODS_BRAND.BRAND_NAME, goodsBrand.getBrandName())
                .set(GOODS_BRAND.E_NAME, goodsBrand.getEName()).set(GOODS_BRAND.LOGO, goodsBrand.getLogo())
                .set(GOODS_BRAND.FIRST, goodsBrand.getFirst()).set(GOODS_BRAND.DESC, goodsBrand.getDesc())
                .set(GOODS_BRAND.IS_RECOMMEND, goodsBrand.getIsRecommend()).set(GOODS_BRAND.CLASSIFY_ID, goodsBrand.getClassifyId())
                .where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .execute();
    }

    *//**
     * 查询单个
     *
     * @param goodsBrand
     * @return
     *//*
    public GoodsBrand select(GoodsBrand goodsBrand) {
        return db().select(GOODS_BRAND.ID,
                GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.CREATE_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
                .from(GOODS_BRAND).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .fetchOne().into(GoodsBrand.class);

    }

    *//**
     *  判断商品名称是否存在，新增使用
     * @param goodsBrand
     * @return
     *//*
    public boolean isBrandNameExist(GoodsBrand goodsBrand) {
        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
                .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
                .and(GOODS_BRAND.DEL_FLAG.eq((byte) 0))
                .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    *//**
     *  判断其他商品名称是否存在同名，修改使用
     * @param goodsBrand
     * @return
     *//*
    public boolean isOtherBrandNameExist(GoodsBrand goodsBrand) {
        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
                .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
                .and(GOODS_BRAND.ID.ne(goodsBrand.getId()))
                .and(GOODS_BRAND.DEL_FLAG.eq((byte) 0))
                .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
*/
}
