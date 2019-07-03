package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandPageListParam;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrand;
import org.jooq.*;
import org.jooq.tools.StringUtils;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.GOODS_BRAND;
import static org.jooq.impl.DSL.field;

/**
 * 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
public class GoodsBrandService extends BaseService {

    /**
     * 分页获取品牌信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsBrand> getPageList(GoodsBrandPageListParam param) {
        SelectJoinStep<Record9<Integer, String, String, String, Byte, Timestamp, String, Byte, Integer>> selectFrom = db().select(GOODS_BRAND.ID,
                GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.ADD_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID).from(GOODS_BRAND);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.ADD_TIME.desc());

        PageResult<GoodsBrand> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsBrand.class);

        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param select
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectWhereStep<?> select, GoodsBrandPageListParam param) {
        SelectConditionStep<?> scs = select
                .where(field("is_delete").eq(GoodsBrandPageListParam.IS_DELETE_DEFAULT_VALUE));

        if (!StringUtils.isBlank(param.getBrandName())) {
            scs = scs.and(GOODS_BRAND.BRAND_NAME.like(this.likeValue(param.getBrandName())));
        }

        if (param.getStartAddTime() != null) {
            scs = scs.and(GOODS_BRAND.ADD_TIME.ge(param.getStartAddTime()));
        }

        if (param.getEndAddTime() != null) {
            scs = scs.and(GOODS_BRAND.ADD_TIME.le(param.getEndAddTime()));
        }

        if (param.getClassifyId() != GoodsBrandPageListParam.CLASSIFY_ID_DEFAULT_VALUE) {
            scs = scs.and(field("classify_id").eq(param.getClassifyId()));
        }

        if (param.getIsRecommend() != GoodsBrandPageListParam.IS_RECOMMEND_DEFAULT_VALUE) {
            scs = scs.and(field("is_recommend").eq(param.getIsRecommend()));
        }

        return scs;
    }

    /**
     * 添加品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
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

    /**
     * 假删除指定品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
    public int delete(GoodsBrand goodsBrand) {
        return db().update(GOODS_BRAND).set(GOODS_BRAND.IS_DELETE, (byte) 1).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .execute();
    }

    /**
     * 更新指定商品
     *
     * @param goodsBrand
     * @return
     */
    public int update(GoodsBrand goodsBrand) {
        return db().update(GOODS_BRAND).set(GOODS_BRAND.BRAND_NAME, goodsBrand.getBrandName())
                .set(GOODS_BRAND.E_NAME, goodsBrand.getEName()).set(GOODS_BRAND.LOGO, goodsBrand.getLogo())
                .set(GOODS_BRAND.FIRST, goodsBrand.getFirst()).set(GOODS_BRAND.DESC, goodsBrand.getDesc())
                .set(GOODS_BRAND.IS_RECOMMEND, goodsBrand.getIsRecommend()).set(GOODS_BRAND.CLASSIFY_ID, goodsBrand.getClassifyId())
                .where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .execute();
    }

    /**
     * 查询单个
     *
     * @param goodsBrand
     * @return
     */
    public GoodsBrand select(GoodsBrand goodsBrand) {
        return db().select(GOODS_BRAND.ID,
                GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.ADD_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
                .from(GOODS_BRAND).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .fetchOne().into(GoodsBrand.class);

    }

    public boolean isBrandNameExist(GoodsBrand goodsBrand) {
        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
                .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
                .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

}
