package com.vpu.mp.service.saas.categroy;

import com.google.common.base.Functions;
import com.vpu.mp.db.main.tables.records.CategoryRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.category.SysCategorySelectTreeVo;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.decoration.ChildCateVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import org.jooq.Record2;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.Tables.CATEGORY;

/**
 * 平台分类
 *
 * @author 常乐 2019年7月15日
 */
@Service

public class SysCateService extends MainBaseService {

    /**
     * 选择平台分分类列表
     *
     * @return
     */
    public List<SysCatevo> getSysCate() {
        List<SysCatevo> parentList = db().select()
            .from(CATEGORY).orderBy(CATEGORY.FIRST.desc(),CATEGORY.CREATE_TIME.desc())
            .fetchInto(SysCatevo.class);
        return parentList;
    }


    /**
     * 分类列表获取树形基础数据，不需要商品数量
     * @return {@link com.vpu.mp.service.pojo.saas.category.SysCategorySelectTreeVo}
     */
    public List<SysCategorySelectTreeVo> getCatSelectTree(){
        List<CategoryRecord> categoryListVo = getCategoryListVo();
        return categoryListVo.stream().map(x->x.into(SysCategorySelectTreeVo.class)).collect(Collectors.toList());
    }
    /**
     * 分类列表获取树形基础数据，需要商品数量
     * @param catNumMap key: catId value: 对应的商品数量
     * @return {@link com.vpu.mp.service.pojo.saas.category.SysCategorySelectTreeVo}
     */
    public List<SysCategorySelectTreeVo> getCatSelectTree(Map<Integer,Long> catNumMap) {
        Set<Integer> catIds = catNumMap.keySet();
        List<CategoryRecord> categoryRecords = getCategoryListVo(catIds);

        List<SysCategorySelectTreeVo> retTree = categoryRecords.stream().map(x -> {
            SysCategorySelectTreeVo vo = x.into(SysCategorySelectTreeVo.class);
            vo.setGoodsNum(catNumMap.get(x.getCatId()).intValue());
            vo.setGoodsSumNum(catNumMap.get(x.getCatId()).intValue());
            return vo;
        }).collect(Collectors.toList());

        // 迭代查询所有父节点，tempIds为已查询处理的所有节点id集合，避免同一个节点被查询两次
        Set<Integer> tempIds = new HashSet<>(catIds);
        while (categoryRecords.size() > 0) {
            List<Integer> tempParentIds = categoryRecords.stream().map(CategoryRecord::getParentId).collect(Collectors.toList());
            List<CategoryRecord> tempList = getCategoryListVo(tempParentIds, tempIds);
            tempIds.addAll(tempList.stream().map(CategoryRecord::getCatId).collect(Collectors.toList()));
            List<SysCategorySelectTreeVo> tempTree = tempList.stream().map(x -> x.into(SysCategorySelectTreeVo.class)).collect(Collectors.toList());
            retTree.addAll(tempTree);
            categoryRecords = tempList;
        }
        // 没有在上上面的递归中一次性转换，为了逻辑清晰点（效率稍有损耗，但是不大）
        Map<Integer, SysCategorySelectTreeVo> catIdMap = retTree.stream().collect(Collectors.toMap(SysCategorySelectTreeVo::getCatId, Functions.identity()));
        retTree.forEach(vo->{
            SysCategorySelectTreeVo parent = catIdMap.get(vo.getParentId());
            while (parent != null) {
                parent.setGoodsSumNum(parent.getGoodsNum()+vo.getGoodsNum());
                parent = catIdMap.get(parent.getParentId());
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
     * 根据传入的平台分类id集合获取对应平台分类对象集合
     * @param catIds 平台分类id结合
     * @return 平台分类集合
     */
    public List<SysCatevo> getList(List<Integer> catIds) {
        return db().select().from(CATEGORY).where(CATEGORY.CAT_ID.in(catIds)).fetchInto(SysCatevo.class);
    }
    /**
     * 根据父节点查询所有子节点,平台分类最多三层
     * @param catId
     * @return
     */
    public List<Integer> findChildrenByParentId(Integer catId) {
        List<Integer> res = new ArrayList<>();
        Short level = db().select(CATEGORY.LEVEL).from(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchOne()
            .into(Short.class);
        res.add(catId);
        if (level == 2) {
            /** 第三级，子分类 */
        } else if (level == 1) {
            /** 第二级分类 */
            List<Integer> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId))
                .fetch(CATEGORY.CAT_ID);
            res.addAll(children);
        } else if (level == 0) {
            /** 第一级分类 */
            List<Integer> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId))
                .fetch(CATEGORY.CAT_ID);
            res.addAll(children);
            for (Integer id : children) {
                List<Integer> grandchildren = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(id))
                    .fetch(CATEGORY.CAT_ID);
                res.addAll(grandchildren);
            }
        }
        return res;
    }

    /**
     * 根据父节点查询所有子节点,包含传入节点,平台分类最多三层
     * @param parentIds
     * @return
     */
    public List<Integer> findChildrenByParentId(List<Integer> parentIds){
        List<Integer> tempIds = new ArrayList<>(parentIds.size());

        for (Integer id : parentIds) {
            tempIds.add(id);
        }

        List<Integer> list = new ArrayList<>(tempIds.size());
        do {
            for (Integer id : tempIds) {
                list.add(id);
            }
            tempIds=db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.in(tempIds)).fetchInto(Integer.class);
        }while (tempIds.size()>0);

        return list;
    }
    /**
     * 根据父id获取子分类
     *
     * @param parentId
     * @return
     */
    public List<ChildCateVo> getSysCateChild(Integer parentId) {
        List<ChildCateVo> child = db().select().from(CATEGORY).where(CATEGORY.PARENT_ID.eq(parentId)).fetch().into(ChildCateVo.class);
        return child;
    }

    public  LinkedList<Map<String,Object>> findParentByChildId(Integer catId) {
        LinkedList<Map<String,Object>> result=new LinkedList<>();

        Map<String, Object> parents = new HashMap<>(2);

        Record2<String, Integer> stringIntegerRecord2 = db().select(CATEGORY.CAT_NAME, CATEGORY.PARENT_ID)
            .from(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchAny();
        Integer parentId = stringIntegerRecord2.get(CATEGORY.PARENT_ID);

        parents.put(CATEGORY.CAT_ID.getName(),catId);
        parents.put(CATEGORY.CAT_NAME.getName(),stringIntegerRecord2.get(CATEGORY.CAT_NAME));

        result.addFirst(parents);

        while (parentId != 0) {
            Record3<Integer, String, Integer> record3 = db().select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME, CATEGORY.PARENT_ID).from(CATEGORY)
                .where(CATEGORY.CAT_ID.eq(parentId)).fetchAny();
            parents=new HashMap<>();
            parents.put(CATEGORY.CAT_ID.getName(),record3.get(CATEGORY.CAT_ID));
            parents.put(CATEGORY.CAT_NAME.getName(),record3.get(CATEGORY.CAT_NAME));
            result.addFirst(parents);
            parentId=record3.get(CATEGORY.PARENT_ID);
        }
        return result;
    }

    /**
     * 遍历查询结果设置对应的平台分类
     *
     * @param goodsPageListVos
     */
    public void disposeCategoryName(List<GoodsPageListVo> goodsPageListVos) {
        List<Integer> catIds = new ArrayList<Integer>(goodsPageListVos.size());

        for (GoodsPageListVo vo : goodsPageListVos) {
            catIds.add(vo.getCatId());
        }

        Map<Integer, String> catIdNameMap = db().select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME).from(CATEGORY)
            .where(CATEGORY.CAT_ID.in(catIds)).fetch().intoMap(CATEGORY.CAT_ID, CATEGORY.CAT_NAME);

        for (GoodsPageListVo goodsPageListVo : goodsPageListVos) {
            Integer catId = goodsPageListVo.getCatId();
            goodsPageListVo.setCatName(catIdNameMap.get(catId));
        }
    }
    
    /**
     * 根据分类id获取单条信息
     * @param catId
     * @return
     */
    public SysCatevo getOneCateInfo(Integer catId) {
    	SysCatevo cateInfo = db().select().from(CATEGORY)
    			.where(CATEGORY.CAT_ID.eq(catId)).fetchOne().into(SysCatevo.class);
    	return cateInfo;
    }
    
    /**
     * 得到商品层级类目
     * @param catId
     * @return
     */
    public List<String> getCategories(Integer catId) {
    	List<String> list=new LinkedList<String>();
    	while(catId!=0) {
    		CategoryRecord cat = db().selectFrom(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchAny();
    		if(cat==null) {
    			break;
    		}
    		list.add(cat.getCatName());
    		catId=cat.getParentId();
    	}
    	Collections.reverse(list);
    	return list;
    	
    }

    /**
     * 获取指定id集合的平台分类
     * @param catIds id集合
     * @return 平台分类集合
     */
    private List<CategoryRecord> getCategoryListVo(Collection<Integer> catIds) {
        return db().select().from(CATEGORY).where(CATEGORY.CAT_ID.in(catIds)).fetchInto(CategoryRecord.class);
    }

    private List<CategoryRecord> getCategoryListVo(){
        return db().select().from(CATEGORY).orderBy(CATEGORY.FIRST.desc(),CATEGORY.CREATE_TIME.desc()).fetchInto(CategoryRecord.class);
    }

    private List<CategoryRecord> getCategoryListVo(Collection<Integer> inIds, Collection<Integer> notInIds) {
        return db().select().from(CATEGORY).where(CATEGORY.CAT_ID.in(inIds)).and(CATEGORY.CAT_ID.notIn(notInIds)).fetchInto(CategoryRecord.class);
    }
}
