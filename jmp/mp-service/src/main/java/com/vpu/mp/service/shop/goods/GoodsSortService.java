package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.*;
import com.vpu.mp.service.pojo.wxapp.goods.sort.GoodsSortMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.sort.GoodsSortParentMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.sort.SortGroupByParentParam;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.Sort.SORT;

/**
 * @author 李晓冰
 * @date 2019年06月27日
 */
@Service
public class GoodsSortService extends ShopBaseService {

    @Autowired
    protected ImageService imageService;

    /**
     * 分类列表
     * @param param
     * @return
     */
    public List<GoodsSortListVo> getSortList(GoodsSortListParam param) {
        Condition condition = buildCondition(param);
        List<GoodsSortListVo> goodsSortListVos = db().select().from(SORT).where(condition).orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc())
            .fetchInto(GoodsSortListVo.class);
        goodsSortListVos.forEach(vo->vo.setSortImg(getImgFullUrlUtil(vo.getSortImg())));

        return goodsSortListVos;
    }

    private Condition buildCondition(GoodsSortListParam param){
        Condition condition = DSL.noCondition();
        if (param.getType() != null) {
            condition = condition.and(SORT.TYPE.eq(param.getType()));
        }

        if (param.getParentId() != null) {
            condition = condition.and(SORT.PARENT_ID.eq(param.getParentId()));
        }
        return condition;
    }

    /**
     * 普通分类下拉列表式
     * @return
     */
    public List<GoodsSortSelectListVo> getSelectList() {
       return db().select().from(SORT).where(SORT.TYPE.eq(GoodsConstant.NORMAL_SORT)).and(SORT.PARENT_ID.eq(0))
           .orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc())
           .fetchInto(GoodsSortSelectListVo.class);
    }

    /**
     * 普通商家分类新增
     * @param param 普通商家分类
     */
    public void insertNormal(GoodsNormalSortAddParam param) {
        transaction(() -> {
            DSLContext db= db();
            //是二级分类
            if (param.getParentId() != null && param.getParentId() != 0) {
                db.update(SORT).set(SORT.HAS_CHILD, GoodsConstant.HAS_CHILD).where(SORT.SORT_ID.eq(param.getParentId()))
                    .execute();
                param.setLevel(GoodsConstant.SECOND_LEVEL);
            } else {
                param.setLevel(GoodsConstant.ROOT_LEVEL);
            }

            SortRecord sortRecord = db.newRecord(SORT, param);
            sortRecord.insert();
        });

    }

    /**
     * 普通商家分类修改
     * @param param 普通商家分类
     */
    public void updateNormal(GoodsNormalSortUpdateParam param) {
        transaction(()->{
            SortRecord sortRecord =new SortRecord();
            assign(param,sortRecord);
            db().executeUpdate(sortRecord);
            // 二级节点修改了父节点，判断原父节点是否还有孩子，同时修改新的父节点为有孩子
            if (!Objects.equals(param.getParentId(),GoodsConstant.ROOT_PARENT_ID)&&!Objects.equals(param.getParentId(), param.getOldParentId())) {
                int i = db().fetchCount(SORT, SORT.PARENT_ID.eq(param.getOldParentId()));
                // 更新原父亲
                if (i == 0) {
                    db().update(SORT).set(SORT.HAS_CHILD,GoodsConstant.HAS_NO_CHILD).where(SORT.SORT_ID.eq(param.getOldParentId())).execute();
                }
                db().update(SORT).set(SORT.HAS_CHILD,GoodsConstant.HAS_CHILD).where(SORT.SORT_ID.eq(param.getParentId())).execute();
            }
        });
    }

    /**
     * 删除商家分类
     */
    public void delete(Integer sortId) {
        transaction(() -> {
            DSLContext db = db();
            SortRecord sortRecord = db.selectFrom(SORT).where(SORT.SORT_ID.eq(sortId)).fetchAny();
            if (sortRecord == null) {
                return;
            }

            db.delete(SORT).where(SORT.SORT_ID.eq(sortId)).execute();
            //是一级节点，有子分类
            if (GoodsConstant.ROOT_PARENT_ID.equals(sortRecord.getParentId())
                &&GoodsConstant.HAS_CHILD.equals(sortRecord.getHasChild())){
                db.delete(SORT).where(SORT.PARENT_ID.eq(sortId)).execute();
            }
            //是子分类，查看是否需要修改父分类hasChild属性
            if (!GoodsConstant.ROOT_PARENT_ID.equals(sortRecord.getParentId())) {
                int i = db.fetchCount(SORT, SORT.PARENT_ID.eq(sortRecord.getParentId()));
                if (i == 0) {
                    db.update(SORT).set(SORT.HAS_CHILD,GoodsConstant.HAS_NO_CHILD)
                        .where(SORT.SORT_ID.eq(sortRecord.getParentId())).execute();
                }
            }
        });
    }

    /**
     * 批量插入推荐分类
     * 默认列表中第一个分类为一级分类
     */
    public void insertRecommendSort(GoodsRecommendSortParam param) {
        SortRecord parentRecord = param.convertParentToRecord();
        List<SortRecord> childrenRecords = param.convertChildrenToRecord();
        transaction(() -> {
            DSLContext db = db();
            SortRecord executeRecord = db.newRecord(SORT, parentRecord);
            executeRecord.insert();
            childrenRecords.forEach(record-> record.setParentId(executeRecord.getSortId()));
            db.batchInsert(childrenRecords).execute();
        });
    }

    public void updateRecommendSort(GoodsRecommendSortParam param) {
        final String toUpdate = "k1";
        final String toInsert = "k2";

        SortRecord parentRecord = param.convertParentToRecord();
        List<SortRecord> childrenRecords = param.convertChildrenToRecord();
        Map<String, List<SortRecord>> collect = childrenRecords.stream().collect(Collectors.groupingBy(x -> x.getSortId() == null ? toInsert : toUpdate));
        List<Integer> childrenIds= childrenRecords.stream().map(SortRecord::getSortId).filter(Objects::nonNull).collect(Collectors.toList());

        transaction(()->{
            DSLContext db = db();
            SortRecord executeRecord = db.newRecord(SORT, parentRecord);
            executeRecord.update();
            db.delete(SORT).where(SORT.PARENT_ID.eq(parentRecord.getSortId())).and(SORT.SORT_ID.notIn(childrenIds)).execute();
            // 更新操作
            List<SortRecord> recordsUpdate = collect.get(toUpdate);
            if (recordsUpdate != null) {
                db.batchUpdate(recordsUpdate).execute();
            }
            // 新增操作
            List<SortRecord> recordsInsert = collect.get(toInsert);
            if (recordsInsert != null) {
                db.batchInsert(recordsInsert).execute();
            }
        });
    }




    /**
     * 根据条件查询符合条件的分类信息
     * @param param
     * @return
     */
    public List<Sort> getList(GoodsSortListParam param) {
        SelectWhereStep<Record> selectFrom = db().select().from(SORT);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc());

        List<Sort> sorts = select.fetchInto(Sort.class);
        /* 处理图片路径 */
        sorts.forEach(sort -> sort.setSortImgUrl(getImgFullUrlUtil(sort.getSortImg())));

        return sorts;
    }

    /**
     * 根据分类id集合查询说所有分类
     * @param sortIds 分类id集合
     * @return 分类集合
     */
    public List<Sort> getList(List<Integer> sortIds) {
        List<Sort> sorts = db().selectFrom(SORT).where(SORT.SORT_ID.in(sortIds)).fetchInto(Sort.class);

        /* 处理图片路径 */
        sorts.forEach(sort -> sort.setSortImgUrl(getImgFullUrlUtil(sort.getSortImg())));

        return sorts;
    }

    /**
     *  查询分类详细信息
     * @param sortId
     * @return
     */
    public Sort getSort(Integer sortId) {
        Record record = db().selectFrom(SORT).where(SORT.SORT_ID.eq(sortId)).fetchAny();
        if (record == null) {
            return null;
        }
        Sort sort= record.into(Sort.class);
        /* 处理图片路径 */
        sort.setSortImgUrl(getImgFullUrlUtil(sort.getSortImg()));
        return sort;
    }
    /**
     *  查询分类详细信息
     * @param ids
     * @return
     */
    public Map<Integer,Sort> getSort(List<Integer> ids) {
        List<Sort> sorts = db().selectFrom(SORT).where(SORT.SORT_ID.in(ids)).fetchInto(Sort.class);

        /* 处理图片路径 */
        sorts.forEach(sort -> sort.setSortImgUrl(getImgFullUrlUtil(sort.getSortImg())));

        return sorts.stream().collect(Collectors.toMap(Sort::getSortId,x->x));
    }

    public Map<Integer,Sort> getParentSortsByChildId(List<Integer> ids){
        com.vpu.mp.db.shop.tables.Sort s1 = SORT.as("s1");
        com.vpu.mp.db.shop.tables.Sort s2 = SORT.as("s2");
        com.vpu.mp.db.shop.tables.Sort s3 = SORT.as("s3");
        Result<Record3<Integer,Integer,Integer>> idResult = db().select(s1.SORT_ID,s2.SORT_ID,s3.SORT_ID)
            .from(s1)
            .leftJoin(s2).on(s1.SORT_ID.eq(s2.PARENT_ID))
            .leftJoin(s3).on(s2.SORT_ID.eq(s3.PARENT_ID))
            .where(s1.SORT_ID.in(ids))
            .fetch();
        if( idResult.isNotEmpty() ){
            return getSort(getIdListByRecord3(idResult));
        }
        return new HashMap<>(0);
    }
    private List<Integer> getIdListByRecord3(Result<Record3<Integer,Integer,Integer>> idResult){
        List<Integer> result = new ArrayList<>(idResult.size()*3);
        idResult.forEach(x->{
            if( null != x.value1() ){
                result.add(x.value1());
            }
            if( null != x.value2() ){
                result.add(x.value2());
            }
            if( null != x.value3() ){
                result.add(x.value3());
            }
        });
        return result;
    }

    /**
     *  获取推荐分类详细信息
     * @param sortId
     * @return
     */
    public Sort getRecommendSort(Integer sortId) {
       Sort sort = getSort(sortId);
        GoodsSortListParam param = new GoodsSortListParam();
        param.setParentId(sort.getSortId());
        param.setType(sort.getType());
        List<Sort> children = getList(param);
        sort.setChildren(children);
        return sort;
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
     * 查询出绑定了商品的的商家分类(如果是子分类则包含该分类的祖先级分类)
     * @return {@link com.vpu.mp.service.pojo.shop.goods.sort.Sort}
     */
    public List<Sort> getListBindedGoods(Condition condition,Integer selectType) {

        // 查询sortId 对应的所有sort集合，集合内都是相同的数据，仅仅是为了统计数量
        Map<Integer,List<Sort>> sortsMap = null;
        if (GoodsPageListParam.GOODS_LIST.equals(selectType)) {
            sortsMap = db().select(SORT.asterisk()).from(SORT).innerJoin(GOODS).on(SORT.SORT_ID.eq(GOODS.SORT_ID)).where(condition).fetch().intoGroups(SORT.SORT_ID, Sort.class);
        } else {
            sortsMap = db().select(SORT.asterisk()).from(SORT).innerJoin(GOODS).on(SORT.SORT_ID.eq(GOODS.SORT_ID))
                .innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID)).where(condition).fetch().intoGroups(SORT.SORT_ID,Sort.class);
        }

        // 临时存储当前查出来的sort数据
        List<Sort> tempData = new ArrayList<>(sortsMap.size());
        // 设置数据的商品数量并放置到sortIdMap中，为后期计算商品数量准备
        Map<Integer,Sort> sortIdMap=new HashMap<>(tempData.size());
        // 设置最初分类的商品或规格数量，并放入sortIdMap和tempData中
        for (Map.Entry<Integer, List<Sort>> entry : sortsMap.entrySet()) {
            Sort sort = entry.getValue().get(0);
            sort.setGoodsNumber(entry.getValue().size());
            sortIdMap.put(entry.getKey(),sort);
            tempData.add(sort);
        }
        // 返回的结果对象
        List<Sort> resultSort = new ArrayList<>(tempData.size());
        resultSort.addAll(tempData);

        List<Integer> tempIds = new ArrayList<>(tempData.size());
        // 迭代查询所有的祖先数据，并将祖先数据的商品或规格数全部设置为0
        while (tempData.size() > 0) {
            List<Integer> tempParentIds = new ArrayList<>(tempData.size());

            tempData.forEach(sort -> {
                tempParentIds.add(sort.getParentId());
                tempIds.add(sort.getSortId());

                if (sort.getGoodsNumber() == null) {
                    sort.setGoodsNumber(0);
                }
                sort.setGoodsNumberSum(sort.getGoodsNumber());
                sortIdMap.put(sort.getSortId(),sort);
            });
            tempData = db().select().from(SORT).where(SORT.SORT_ID.in(tempParentIds)).and(SORT.SORT_ID.notIn(tempIds)).fetchInto(Sort.class);

            resultSort.addAll(tempData);
        }
        // 迭代计算平台分类对应的所有商品或规格数量
        resultSort.forEach(sort -> {
            Integer baseNum = sort.getGoodsNumber();
            Sort parent = sortIdMap.get(sort.getParentId());
            while (parent != null) {
                parent.setGoodsNumberSum(parent.getGoodsNumberSum()+baseNum);
                parent = sortIdMap.get(parent.getParentId());
            }
        });
        // 根据first和creteTime 排序
        resultSort.sort((s1,s2)->{
            if (s1.getFirst().equals(s2.getFirst())){
                return s2.getCreateTime().compareTo(s1.getCreateTime());
            }
            return  s2.getFirst() - s1.getFirst();
        });

        return resultSort;
    }

    /**
     * 普通商家分类新增
     * @param sort
     */
    public void insert(Sort sort) {
        transaction(() -> {
            DSLContext db= db();
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
     * 商家分类名称是否存在，用来新增检查
     * @param sortId 分类id
     * @param sortName 商家分类名称
     * @return true 存在 false 不存在
     */
    public boolean isSortNameExist(Integer sortId,String sortName) {
        Condition condition = SORT.SORT_NAME.eq(sortName);
        if (sortId != null) {
            condition = condition.and(SORT.SORT_ID.ne(sortId));
        }
        int count = db().fetchCount(SORT, condition);

        return count>0;
    }


    /**
     * 商家分类名称是否存在，推荐分类新增使用
     * @param  sortNames 商家分类名称
     * @return true 存在 false 不存在
     */
    public boolean isSortNameExist(List<String> sortNames) {
        int count = db().fetchCount(SORT, SORT.SORT_NAME.in(sortNames));
        return count>0;
    }

    /**
     * 商家分类名称是否存在，推荐分类修改使用
     * @param parentId 推荐分类父分类id
     * @param sortNames 父分类名称和其所有子分类名称
     * @return true 存在 false 不存在
     */
    public boolean isSortNameExist(Integer parentId, List<String> sortNames) {
        Condition condition = SORT.SORT_NAME.in(sortNames).and(SORT.SORT_ID.ne(parentId)).and(SORT.PARENT_ID.ne(parentId));
        int count = db().fetchCount(SORT, condition);
        return count>0;
    }

    /**
     *  推荐分类修改时名称重复判断
     * @param sorts
     * @param parentId
     * @return
     */
    public boolean isOtherSortNameExist(List<Sort> sorts,Integer parentId) {
        List<String> sortNames = new ArrayList<>(sorts.size());
        List<Integer> sortIds = new ArrayList<>(sorts.size());
        sorts.forEach(item -> {
            sortNames.add(item.getSortName());
            sortIds.add(item.getSortId());
        });

        List<Integer> readyToDeleteIds = db().selectFrom(SORT).where(SORT.SORT_ID.notIn(sortIds)).and(SORT.PARENT_ID.eq(parentId)).fetch(SORT.SORT_ID);

        sortIds.addAll(readyToDeleteIds);

        Record1<Integer> countRecord = db().selectCount().from(SORT)
            .where(SORT.SORT_NAME.in(sortNames)).and(SORT.SORT_ID.notIn(sortIds))
            .fetchOne();

        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 商家分类修改
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
     * 修改推荐分类及其子分类
     * @param sort
     */
    public void updateRecommendSort(Sort sort) {
        List<? extends Sort> children = sort.getChildren();
        List<Integer> childrenId = new ArrayList<>(children.size());
        List<SortRecord> childrenSortRecordForUpdate =new ArrayList<>(children.size());
        List<SortRecord> childrenSortRecordForInsert =new ArrayList<>(children.size());

        children.forEach((Sort item)->{
            SortRecord sortRecord=new SortRecord();
            sortRecord.setType(Sort.RECOMMENT_TYPE_CODE);
            sortRecord.setHasChild(Sort.HAS_NO_CHILD_CODE);
            sortRecord.setParentId(sort.getSortId());
            sortRecord.setSortName(item.getSortName());
            sortRecord.setSortImg(item.getSortImg());
            sortRecord.setImgLink(item.getImgLink());

            if (item.getSortId() != null) {
                sortRecord.setSortId(item.getSortId());
                childrenId.add(item.getSortId());
                childrenSortRecordForUpdate.add(sortRecord);
            } else {
                childrenSortRecordForInsert.add(sortRecord);
            }
        });
        SortRecord parentSort =new SortRecord();
        parentSort.setSortId(sort.getSortId());
        parentSort.setSortName(sort.getSortName());
        parentSort.setFirst(sort.getFirst());
        if (childrenSortRecordForInsert.size() == 0 && childrenSortRecordForUpdate.size() == 0) {
            parentSort.setHasChild(Sort.HAS_NO_CHILD_CODE);
        }

        transaction(()->{
            db().deleteFrom(SORT).where(SORT.PARENT_ID.eq(sort.getSortId())).and(SORT.SORT_ID.notIn(childrenId)).execute();
            db().batchUpdate(childrenSortRecordForUpdate).execute();
            db().batchInsert(childrenSortRecordForInsert).execute();
            db().executeUpdate(parentSort);
        });
    }

    /**
     * 根据父节点查询所有子孙节点，包含传入节点
     * @param parentId
     * @return
     */
    public List<Integer> findChildrenByParentId(Integer parentId) {
        Integer[] children = new Integer[]{parentId};
        List<Integer> list = new ArrayList<>(children.length);
        do {
            for (Integer id : children) {
                list.add(id);
            }

            children = db().select(SORT.SORT_ID).from(SORT).where(SORT.PARENT_ID.in(children)).fetchArray(SORT.SORT_ID);

        } while (children.length > 0);

        return list;
    }

    /**
     * @param parentIds
     * @return
     */
    public List<Integer> findChildrenByParentId(List<Integer> parentIds) {
        Integer[] children = new Integer[parentIds.size()];
        for (int i = 0; i < parentIds.size(); i++) {
            children[i] = parentIds.get(i);
        }
        List<Integer> list = new ArrayList<>(children.length);
        do {
            for (Integer id : children) {
                list.add(id);
            }
            children = db().select(SORT.SORT_ID).from(SORT).where(SORT.PARENT_ID.in(children)).fetchArray(SORT.SORT_ID);

        } while (children.length > 0);

        Set set = new HashSet(list);

        return new ArrayList<>(set);
    }


    /**
     * 获取所有有效分类作为父分类，并查询这些有效分类的子分类，将子分类按照父分类进行组织
     * @param param 查询父分类需要的条件
     * @return 按照父分类进行组织的结果集合
     */
    public List<GoodsSortParentMpVo> getSortGroupByParentMp(SortGroupByParentParam param){

        Condition sortParentCondition = buildSortGroupByParentCondition(param);

        List<GoodsSortParentMpVo> sortParent = db().selectFrom(SORT).where(sortParentCondition)
            .orderBy(SORT.FIRST.desc(),SORT.CREATE_TIME.desc())
            .fetchInto(GoodsSortParentMpVo.class);

        List<Integer> parentIds = sortParent.stream().mapToInt(GoodsSortParentMpVo::getSortId).boxed().collect(Collectors.toList());

        Map<Integer, List<GoodsSortMpVo>> sortMap = db().selectFrom(SORT).where(SORT.PARENT_ID.in(parentIds))
            .orderBy(SORT.FIRST.desc(),SORT.CREATE_TIME.desc())
            .fetchGroups(SORT.PARENT_ID, GoodsSortMpVo.class);

        for (GoodsSortParentMpVo goodsRecommendSortMpVo : sortParent) {
            goodsRecommendSortMpVo.setImgLink(getImgFullUrlUtil(goodsRecommendSortMpVo.getImgLink()));
            List<GoodsSortMpVo> goodsSortMpVos = sortMap.get(goodsRecommendSortMpVo.getSortId());
            if (goodsSortMpVos == null) {
                goodsRecommendSortMpVo.setGoodsSorts(new ArrayList<>());
            } else {
                goodsRecommendSortMpVo.setGoodsSorts(goodsSortMpVos);
                goodsSortMpVos.forEach(sort-> sort.setSortImg(getImgFullUrlUtil(sort.getSortImg())));
            }
        }
        return sortParent;
    }

    public Condition buildSortGroupByParentCondition(SortGroupByParentParam param) {
        Condition condition = DSL.noCondition();
        if (param.getIsRecommend() != null) {
            condition = condition.and(SORT.TYPE.eq(param.getIsRecommend()));
        }
        if (param.getParentIds() != null) {
            condition = condition.and(SORT.PARENT_ID.in(param.getParentIds()));
        }
        if (param.getSortIds() != null) {
            condition = condition.and(SORT.SORT_ID.in(param.getSortIds()));
        }
        return condition;
    }

    /**
     * 将相对路劲修改为全路径
     *
     * @param relativePath 相对路径
     * @return null或全路径
     */
    private String getImgFullUrlUtil(String relativePath) {
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return imageService.imageUrl(relativePath);
        }
    }

}
