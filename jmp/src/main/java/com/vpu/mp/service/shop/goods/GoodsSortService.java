package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.pojos.Sort;

import static com.vpu.mp.db.shop.tables.Sort.*;

import com.vpu.mp.service.foundation.BaseService;
import lombok.Data;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年06月27日
 */
public class GoodsSortService extends BaseService {

    @Data
    public static class GoodsSortListParam {
        public static final byte TYPE_DEFAULT_VALUE = 0;//商家分类类型默认值 0普通分类，1推荐分类
        public static final int PARENT_ID_DEFAULT_VALUE = 0;//分类父id默认值 0一级分类，否则为二级分类

        public byte type = TYPE_DEFAULT_VALUE;
        public int parentId = PARENT_ID_DEFAULT_VALUE;
        public String sortName;
        public Timestamp startCreateTime;
        public Timestamp endCreateTime;
    }

    /**
     * 根据父分类和分类类型查询
     *
     * @param param
     * @return
     */
    public List<Sort> getList(GoodsSortListParam param) {
        SelectWhereStep<Record> selectFrom = db().select().from(SORT);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc());

        List<Sort> sorts = select.fetchInto(Sort.class);

        return sorts;
    }

    private SelectConditionStep<?> buildOptions(SelectWhereStep<?> select, GoodsSortListParam param) {
        SelectConditionStep<?> scs = select.where(SORT.PARENT_ID.eq(param.getParentId()))
                .and(SORT.TYPE.eq(param.getType()));

        if (!StringUtils.isBlank(param.sortName)) {
            scs = scs.and(SORT.SORT_NAME.like(this.likeValue(param.getSortName())));
        }

        if (param.startCreateTime != null) {
            scs = scs.and(SORT.CREATE_TIME.ge(param.startCreateTime));
        }

        if (param.endCreateTime != null) {
            scs = scs.and(SORT.CREATE_TIME.le(param.endCreateTime));
        }

        return scs;
    }

    /**
     * 普通商家分类新增
     *
     * @param sort
     * @return 受影响行数
     */
    public void insert(Sort sort) {

        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);

            if (sort.getParentId() != 0) {
                db.update(SORT).set(SORT.HAS_CHILD, (byte) 1).where(SORT.SORT_ID.eq(Integer.valueOf(sort.getParentId())))
                        .execute();
            }


            db.insertInto(SORT, SORT.SORT_NAME, SORT.PARENT_ID, SORT.LEVEL, SORT.SORT_IMG, SORT.IMG_LINK, SORT.FIRST,
                    SORT.TYPE, SORT.SORT_DESC)
                    .values(sort.getSortName(), sort.getParentId(), sort.getLevel(), sort.getSortImg(), sort.getImgLink(),
                            sort.getFirst(), sort.getType(), sort.getSortDesc())
                    .execute();
        });

    }

    /**
     * 商家分类名称是否存在
     *
     * @param sort
     * @return
     */
    public boolean isSortNameExist(Sort sort) {
        Record1<Integer> countRecord = db().selectCount().from(SORT).where(SORT.SORT_NAME.eq(sort.getSortName()))
                .fetchOne();

        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除商家分类
     *
     * @param sort
     * @return 受影响行数
     */
    public void delete(Sort sort) {

        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);

            db.delete(SORT).where(SORT.SORT_ID.eq(sort.getSortId())).execute();

            if (sort.getHasChild() != 0) {//有子分类
                db.delete(SORT).where(SORT.PARENT_ID.eq(sort.getSortId())).execute();
            }

            if (sort.getParentId() != 0) {//是子分类，查看是否需要修改父分类hasChild属性
                Record1<Integer> countRecord = db().selectCount().from(SORT).where(SORT.PARENT_ID.eq(sort.getParentId())).fetchOne();
                if (countRecord.getValue(0, Integer.class) == 0) {
                    db.update(SORT).set(SORT.HAS_CHILD, (byte) 0).where(SORT.SORT_ID.eq(sort.getParentId().intValue())).execute();
                }
            }

        });
    }

    /**
     * 商家分类修改
     *
     * @param sort
     * @return 受影响行数
     */
    public void update(Sort sort) {
        db().update(SORT).set(SORT.SORT_NAME, sort.getSortName())
                .set(SORT.SORT_IMG, sort.getSortImg()).set(SORT.IMG_LINK, sort.getImgLink())
                .set(SORT.FIRST, sort.getFirst()).set(SORT.SORT_DESC, sort.getSortDesc())
                .where(SORT.SORT_ID.eq(sort.getSortId()))
                .execute();
    }
}
