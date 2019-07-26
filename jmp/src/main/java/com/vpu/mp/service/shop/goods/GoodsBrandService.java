package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsBrandRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrand;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandPageListParam;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandVo;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_BRAND;

/**
 * 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@Service

public class GoodsBrandService extends ShopBaseService {

    /**
     * 分页获取品牌信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsBrand> getPageList(GoodsBrandPageListParam param) {
        SelectJoinStep<Record9<Integer, String, String, String, Byte, Timestamp, String, Byte, Integer>> selectFrom = db().select(GOODS_BRAND.ID,
                GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.CREATE_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID).from(GOODS_BRAND);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.CREATE_TIME.desc());

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
        SelectConditionStep<?> scs = select.where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

        if (!StringUtils.isBlank(param.getBrandName())) {
            scs = scs.and(GOODS_BRAND.BRAND_NAME.like(this.likeValue(param.getBrandName())));
        }

        if (param.getStartCreateTime() != null) {
            scs = scs.and(GOODS_BRAND.CREATE_TIME.ge(param.getStartCreateTime()));
        }

        if (param.getEndCreateTime() != null) {
            scs = scs.and(GOODS_BRAND.CREATE_TIME.le(param.getEndCreateTime()));
        }

        if (param.getClassifyId() != null) {
            scs = scs.and(GOODS_BRAND.CLASSIFY_ID.eq(param.getClassifyId()));
        }

        if (param.getIsRecommend() != null) {
            scs = scs.and(GOODS_BRAND.IS_RECOMMEND.eq(param.getIsRecommend()));
        }

        return scs;
    }

    /**
     * 添加品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
    public void insert(GoodsBrand goodsBrand) {
        GoodsBrandRecord goodsBrandRecord = new GoodsBrandRecord();
        assign(goodsBrand, goodsBrandRecord);
        db().executeInsert(goodsBrandRecord);
    }

    /**
     * 假删除指定品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
    public int delete(GoodsBrand goodsBrand) {
        return db().update(GOODS_BRAND)
                .set(GOODS_BRAND.DEL_FLAG, DelFlag.DISABLE.getCode())
                .set(GOODS_BRAND.BRAND_NAME, DSL.concat(getDelPrefix(goodsBrand.getId()))
                        .concat(GOODS_BRAND.BRAND_NAME))
                .where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .execute();
    }

    /**
     * 更新指定商品
     *
     * @param goodsBrand
     * @return
     */
    public void update(GoodsBrand goodsBrand) {
        GoodsBrandRecord goodsBrandRecord=new GoodsBrandRecord();
        assign(goodsBrand,goodsBrandRecord);
        db().executeUpdate(goodsBrandRecord);
    }

    /**
     * 查询单个
     *
     * @param goodsBrand
     * @return
     */
    public GoodsBrand select(GoodsBrand goodsBrand) {
        return db().select(GOODS_BRAND.ID,
                GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.CREATE_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
                .from(GOODS_BRAND).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
                .fetchOne().into(GoodsBrand.class);

    }

    /**
     * 判断商品名称是否存在，新增使用
     *
     * @param goodsBrand
     * @return
     */
    public boolean isBrandNameExist(GoodsBrand goodsBrand) {

        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
                .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
                .and(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断其他商品名称是否存在同名，修改使用
     *
     * @param goodsBrand
     * @return
     */
    public boolean isOtherBrandNameExist(GoodsBrand goodsBrand) {
        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
                .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
                .and(GOODS_BRAND.ID.ne(goodsBrand.getId()))
                .and(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有品牌
     * @return
     */
    public List<GoodsBrandVo> listGoodsBrandName(){
        List<GoodsBrandVo> goodsBrandNames = db().select(GOODS_BRAND.ID,GOODS_BRAND.BRAND_NAME)
                .from(GOODS_BRAND)
                .where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .fetch().into(GoodsBrandVo.class);

        return goodsBrandNames;
    }



}
