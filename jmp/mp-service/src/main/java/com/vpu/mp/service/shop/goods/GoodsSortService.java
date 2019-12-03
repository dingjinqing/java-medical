package com.vpu.mp.service.shop.goods;

import com.google.common.base.Functions;
import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
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
     * @param param {@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam}
     * @return {@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListVo}集合
     */
    public List<GoodsSortListVo> getSortList(GoodsSortListParam param) {
        List<SortRecord> sortRecords = getSortListDao(param);
        List<GoodsSortListVo> retList = new ArrayList<>(sortRecords.size());

        sortRecords.forEach(record->{
            GoodsSortListVo vo = record.into(GoodsSortListVo.class);
            vo.setSortImg(getImgFullUrlUtil(vo.getSortImg()));
            retList.add(vo);
        });

        return retList;
    }

    /**
     * 分类列表获取树形基础数据，不需要商品数量
     * @return {@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortSelectTreeVo} 集合
     */
    public List<GoodsSortSelectTreeVo> getSortSelectTree() {
        GoodsSortListParam param = new GoodsSortListParam();
        param.setType(GoodsConstant.NORMAL_SORT);
        List<SortRecord> sortRecords = getSortListDao(param);
        return sortRecords.stream().map(x -> x.into(GoodsSortSelectTreeVo.class)).collect(Collectors.toList());
    }
    /**
     * 分类列表获取树形基础数据，需要商品数量
     * @param sortNumMap key: sortId value: 对应的商品数量
     * @return {@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortSelectTreeVo} 集合
     */
    public List<GoodsSortSelectTreeVo> getSortSelectTree(Map<Integer,Long> sortNumMap) {
        Set<Integer> sortIds = sortNumMap.keySet();
        List<SortRecord> sortRecords = getSortListDao(sortIds);

        // 存储所有需要返回的数据，并设置其初始商品数量
        List<GoodsSortSelectTreeVo> retTree = sortRecords.stream().map(x -> {
            GoodsSortSelectTreeVo vo = x.into(GoodsSortSelectTreeVo.class);
            vo.setGoodsNum(sortNumMap.get(x.getSortId()).intValue());
            vo.setGoodsSumNum(sortNumMap.get(x.getSortId()).intValue());
            return vo;
        }).collect(Collectors.toList());

        // 迭代查询所有父节点，tempIds为已查询处理的所有节点id集合，避免同一个节点被查询两次
        Set<Integer> tempIds = new HashSet<>(sortIds);
        while (sortRecords.size() > 0) {
            List<Integer> tempParentIds = sortRecords.stream().map(SortRecord::getParentId).collect(Collectors.toList());
            List<SortRecord> tempList = getSortListDao(tempParentIds, tempIds);
            tempIds.addAll(tempList.stream().map(SortRecord::getSortId).collect(Collectors.toList()));
            List<GoodsSortSelectTreeVo> tempTree = tempList.stream().map(x -> x.into(GoodsSortSelectTreeVo.class)).collect(Collectors.toList());
            retTree.addAll(tempTree);
            sortRecords = tempList;
        }
        // 没有在上上面的递归中一次性转换，为了逻辑清晰点（效率稍有损耗，但是不大）
        Map<Integer, GoodsSortSelectTreeVo> sortIdMap = retTree.stream().collect(Collectors.toMap(GoodsSortSelectTreeVo::getSortId, Functions.identity()));
        retTree.forEach(vo->{
            GoodsSortSelectTreeVo parent = sortIdMap.get(vo.getParentId());
            while (parent != null) {
                parent.setGoodsSumNum(parent.getGoodsSumNum()+vo.getGoodsNum());
                parent = sortIdMap.get(parent.getParentId());
            }
        });

        retTree.sort((s1,s2)->{
            if (s1.getFirst().equals(s2.getFirst())){
                return s2.getCreateTime().compareTo(s1.getCreateTime());
            }
            return  s2.getFirst() - s1.getFirst();
        });

        return retTree;
    }
    /**
     * 普通分类下拉列表式
     * @return
     */
    public List<GoodsSortSelectListVo> getSelectList() {
        GoodsSortListParam param = new GoodsSortListParam();
        param.setType(GoodsConstant.NORMAL_SORT);
        param.setParentId(GoodsConstant.ROOT_PARENT_ID);
        List<SortRecord> sortRecords = getSortListDao(param);
        return sortRecords.stream().map(x -> x.into(GoodsSortSelectListVo.class)).collect(Collectors.toList());
    }

    /**
     * 普通商家分类新增
     * @param param 普通商家分类
     */
    public void insertNormal(GoodsNormalSortParam param) {
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
    public void updateNormal(GoodsNormalSortParam param) {
        transaction(()->{
            SortRecord sortRecord = param.convertToSortRecord();
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
     * 批量插入推荐分类
     * @param param{@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortParam}
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

    /**
     * 批量更新推荐分类，会删除无效旧的二级分类
     * @param param{@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortParam}
     */
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
     * 删除商家分类，会同时删除所有子分类，同时会判断是否需要修改父节是否有孩子节点状态
     * @param  sortId 待删除二级分类
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

    public GoodsNormalSortDetailVo getNormalSort(Integer sortId){
        SortRecord sortRecord = getSortDao(sortId);

        if (sortRecord == null) {
            return null;
        }
        GoodsNormalSortDetailVo vo = sortRecord.into(GoodsNormalSortDetailVo.class);
        vo.setSortImgUrl(getImgFullUrlUtil(vo.getSortImg()));
        return vo;
    }

    /**
     * 根据分类id获取推荐分类信息
     * @param sortId 分类id
     * @return {@link GoodsRecommendSortDetailVo} 推荐分类详情
     */
    public GoodsRecommendSortDetailVo getRecommendSort(Integer sortId){
        Record3<Integer, String, Short> record = db().select(SORT.SORT_ID, SORT.SORT_NAME,SORT.FIRST).from(SORT).where(SORT.SORT_ID.eq(sortId)).fetchAny();
        if (record == null) {
            return null;
        }
        GoodsRecommendSortDetailVo parent = record.into(GoodsRecommendSortDetailVo.class);
        List<GoodsRecommendSortDetailVo.GoodsRecommendSortChild> children = db().select().from(SORT).where(SORT.PARENT_ID.eq(sortId)).fetchInto(GoodsRecommendSortDetailVo.GoodsRecommendSortChild.class);
        children.forEach(x->x.setSortImgUrl(getImgFullUrlUtil(x.getSortImg())));
        parent.setChildren(children);
        return parent;
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
     * 根据分类id集合查询说所有分类
     * @param sortIds 分类id集合
     * @return 分类集合
     */
    @Deprecated
    public List<Sort> getList(List<Integer> sortIds) {
        List<Sort> sorts = db().selectFrom(SORT).where(SORT.SORT_ID.in(sortIds)).fetchInto(Sort.class);

        /* 处理图片路径 */
        sorts.forEach(sort -> sort.setSortImgUrl(getImgFullUrlUtil(sort.getSortImg())));

        return sorts;
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
     * 获取所有有效分类作为父分类，并查询这些有效分类的子分类，将子分类按照父分类进行组织
     * @param param 查询父分类需要的条件
     * @return 按照父分类进行组织的结果集合
     */
    public List<GoodsSortParentMpVo> getSortGroupByParentMp(SortGroupByParentParam param){

        Condition sortParentCondition = buildSortGroupByParentCondition(param);

        List<GoodsSortParentMpVo> sortParent = db().selectFrom(SORT).where(sortParentCondition)
            .orderBy(SORT.FIRST.desc(),SORT.CREATE_TIME.desc())
            .fetchInto(GoodsSortParentMpVo.class);

        List<Integer> parentIds =sortParent.stream().map(GoodsSortParentMpVo::getSortId).collect(Collectors.toList());

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

    private Condition buildSortGroupByParentCondition(SortGroupByParentParam param) {
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


    /**
     * 根据筛选条件查询商家分类集合
     * @param param {@link com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam}
     * @return SortRecord集合
     */
    public List<SortRecord> getSortListDao(GoodsSortListParam param) {
        Condition condition = buildCondition(param);
        return db().select().from(SORT).where(condition).orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc())
            .fetchInto(SortRecord.class);
    }

    /**
     * 根据指定sortId集合查询商家分类集合
     * @return SortRecord集合
     */
    private List<SortRecord> getSortListDao(Collection<Integer> sortIds){
        return db().select().from(SORT).where(SORT.SORT_ID.in(sortIds)).orderBy(SORT.FIRST.desc(), SORT.CREATE_TIME.desc())
            .fetchInto(SortRecord.class);
    }

    /**
     * 根据传入的父id集合迭代查询所有子孙分类id集合（包含传入的id集合）
     * @param parentIds 父id集合
     * @return 子孙id集合
     */
    public List<Integer> getChildrenIdByParentIdsDao(Collection<Integer> parentIds) {
        List<Integer> retIds = new ArrayList<>(parentIds);
        while (parentIds.size() > 0) {
            List<Integer> childrenIds = db().select(SORT.SORT_ID).from(SORT).where(SORT.PARENT_ID.in(parentIds)).fetch(SORT.SORT_ID);
            retIds.addAll(childrenIds);
            parentIds = childrenIds;
        }
        return retIds;
    }

    /**
     * 查询分类id在inIds集合中，但是不在notInids集合内的所有分类
     * @param inIds  在该集合内
     * @param notInIds 不在该集合内
     * @return   分类集合
     */
    private List<SortRecord> getSortListDao(Collection<Integer> inIds,Collection<Integer> notInIds) {
        return db().select().from(SORT).where(SORT.SORT_ID.in(inIds).and(SORT.SORT_ID.notIn(notInIds))).fetchInto(SortRecord.class);
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
     * 根据id获取商家分类
     * @param sortId 商家分类id
     * @return null 或 SortRecord
     */
    public SortRecord getSortDao(Integer sortId){
        Record record = db().select().from(SORT).where(SORT.SORT_ID.eq(sortId)).fetchAny();
        if (record == null) {
            return null;
        }
        return record.into(SortRecord.class);
    }
}
