package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Sort.SORT;

/**
 * @author 李晓冰
 * @date 2019年06月27日
 */
@Service
@Scope("prototype")
public class GoodsSortService extends BaseService {

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
        List<Condition> list = new ArrayList<>(10);

        if (!StringUtils.isBlank(param.getSortName())) {
            list.add(SORT.SORT_NAME.like(likeValue(param.getSortName())));
        }

        if (param.getType() != null) {
            list.add(SORT.TYPE.eq(param.getType()));
        }

        if (param.getParentId() != null) {
            list.add(SORT.PARENT_ID.eq(param.getParentId()));
        }

        if (param.getStartCreateTime() != null) {
            list.add(SORT.CREATE_TIME.ge(param.getStartCreateTime()));
        }

        if (param.getEndCreateTime() != null) {
            list.add(SORT.CREATE_TIME.le(param.getEndCreateTime()));
        }

        return select.where(list);
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
            //防止故意传递错误的类型
            sort.setType(Sort.NORMAL_TYPE_CODE);
            sort.setHasChild(Sort.HAS_NO_CHILD_CODE);

            //是二级分类
            if (sort.getParentId() != null && sort.getParentId() != 0) {
                db.update(SORT).set(SORT.HAS_CHILD, Sort.HAS_CHILD_CODE).where(SORT.SORT_ID.eq(sort.getParentId()))
                        .execute();
            }

            SortRecord sortRecord = db.newRecord(SORT, sort);

            sortRecord.insert();
        });

    }

    /**
     * 批量插入推荐分类
     * 默认列表中第一个分类为一级分类
     */
    public void insertRecommendSort(List<Sort> sorts) {
        transaction(() -> {
            DSLContext db = db();
            Sort parentSort = sorts.remove(0);

            //存在子分类
            if (sorts.size() > 0) {
                parentSort.setHasChild(Sort.HAS_CHILD_CODE);
            }
            parentSort.setType(Sort.RECOMMENT_TYPE_CODE);

            SortRecord parentRecord = db.newRecord(SORT, parentSort);
            parentRecord.insert();
            parentSort.setSortId(parentRecord.getSortId());

            List<SortRecord> sortRecords = new ArrayList<>(sorts.size());

            for (Sort sort : sorts) {
                sort.setType(Sort.RECOMMENT_TYPE_CODE);
                sort.setHasChild(Sort.HAS_NO_CHILD_CODE);

                sort.setParentId(parentSort.getSortId());
                SortRecord record = new SortRecord();
                assign(sort, record);
                sortRecords.add(record);
            }

            db.batchInsert(sortRecords).execute();
        });
    }

    /**
     * 商家分类名称是否存在，用来新增检查
     *
     * @param sort
     * @return
     */
    public boolean isSortNameExist(Sort sort) {
        Record1<Integer> countRecord = db().selectCount().from(SORT)
                .where(SORT.SORT_NAME.eq(sort.getSortName()))
                .fetchOne();

        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 商家分类名称是否存在，修改使用
     *
     * @param sort
     * @return
     */
    public boolean isOtherSortNameExist(Sort sort) {
        Record1<Integer> countRecord = db().selectCount().from(SORT)
                .where(SORT.SORT_NAME.eq(sort.getSortName())).and(SORT.SORT_ID.ne(sort.getSortId()))
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

        Integer sortId = sort.getSortId();

        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);

            Sort s = db.selectFrom(SORT).where(SORT.SORT_ID.eq(sortId)).fetchOneInto(Sort.class);

            if (s == null) {
                return;
            }

            db.delete(SORT).where(SORT.SORT_ID.eq(sortId)).execute();

            //是一级节点，有子分类
            if (s.getParentId() == 0 && s.getHasChild() != 0) {
                db.delete(SORT).where(SORT.PARENT_ID.eq(sortId)).execute();
            }

            //是子分类，查看是否需要修改父分类hasChild属性
            if (s.getParentId() != 0) {
                Record1<Integer> countRecord = db().selectCount().from(SORT).where(SORT.PARENT_ID.eq(s.getParentId())).fetchOne();
                if (countRecord.getValue(0, Integer.class) == 0) {
                    db.update(SORT).set(SORT.HAS_CHILD, Sort.HAS_NO_CHILD_CODE).where(SORT.SORT_ID.eq(s.getParentId())).execute();
                }
            }

        });
    }

    /**
     * 商家分类修改
     *
     * @param sort
     */
    public void update(Sort sort) {

        //不允许修改父节点
        sort.setParentId(null);
        //不允许修改类型
        sort.setType(null);
        //不允许修改子节点情况
        sort.setHasChild(null);

        SortRecord sortRecord = new SortRecord();
        assign(sort, sortRecord);
        db().executeUpdate(sortRecord);
    }

    /**
     * 根据父节点查询所有子节点
     *
     * @param parentId
     * @return
     */
    protected List<Integer> findChildrenByParentId(Integer parentId) {
        Integer[] integers = db().select(SORT.SORT_ID)
                .from(SORT)
                .where(SORT.PARENT_ID.eq(parentId))
                .fetchArray(SORT.SORT_ID);
        List<Integer> list = new ArrayList<>(integers.length);
        for (Integer id : integers) {
            list.add(id);
        }
        return list;
    }
}
