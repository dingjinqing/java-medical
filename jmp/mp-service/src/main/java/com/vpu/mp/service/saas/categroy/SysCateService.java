package com.vpu.mp.service.saas.categroy;

import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.decoration.ChildCateVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import org.jooq.Record2;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * 根据传入的平台分类id集合查询出相应的所有平台分类
     * @param catIds 平台分类id
     * @param goodsNumberMap 平台分类id对应的商品数量
     * @return {@link com.vpu.mp.service.pojo.saas.category.SysCatevo}
     */
    public List<SysCatevo> getList(List<Integer> catIds,Map<Integer,Integer> goodsNumberMap) {
        List<SysCatevo> resultCat = new ArrayList<>(catIds.size());
        List<SysCatevo> tempData = db().select().from(CATEGORY).where(CATEGORY.CAT_ID.in(catIds)).fetchInto(SysCatevo.class);
        resultCat.addAll(tempData);
        // 设置数据的商品数量并放置到catIdMap中，为后期计算商品数量准备
        Map<Integer,SysCatevo> catIdMap=new HashMap<>(resultCat.size());

        while (tempData.size() > 0) {
            List<Integer> tempParentIds = new ArrayList<>(tempData.size());
            List<Integer> tempIds = new ArrayList<>(tempData.size());
            tempData.forEach(sysCatevo -> {
                tempParentIds.add(sysCatevo.getParentId());
                tempIds.add(sysCatevo.getCatId());

                Integer num = goodsNumberMap.get(sysCatevo.getCatId().intValue());
                sysCatevo.setGoodsNumber(num ==null ? 0:num);
                sysCatevo.setGoodsNumberSum(sysCatevo.getGoodsNumber());
                catIdMap.put(sysCatevo.getCatId(),sysCatevo);
            });
            tempData= db().select().from(CATEGORY).where(CATEGORY.CAT_ID.in(tempParentIds)).and(CATEGORY.CAT_ID.notIn(tempIds)).fetchInto(SysCatevo.class);
            resultCat.addAll(tempData);
        }

        resultCat.forEach(sysCatevo -> {
            Integer baseNum = sysCatevo.getGoodsNumber();
            SysCatevo parent = catIdMap.get(sysCatevo.getParentId());
            while (parent != null) {
                parent.setGoodsNumberSum(parent.getGoodsNumberSum()+baseNum);
                parent = catIdMap.get(parent.getParentId());
            }
        });

        resultCat.sort((c1,c2)->{
            if (c1.getFirst().equals(c2.getFirst())){
                return c2.getCreateTime().compareTo(c1.getCreateTime());
            }
            return  c2.getFirst() - c1.getFirst();
        });

        return resultCat;
    }
    /**
     * 根据父节点查询所有子节点,平台分类最多三层
     *
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
}
