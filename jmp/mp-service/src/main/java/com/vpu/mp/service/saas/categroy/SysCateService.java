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
            .from(CATEGORY)
            .fetchInto(SysCatevo.class);
        return parentList;
    }

    /**
     * 根据父节点查询所有子节点,平台分类最多三层
     *
     * @param catId
     * @return
     */
    public List<Short> findChildrenByParentId(Short catId) {
        List<Short> res = new ArrayList<Short>();
        Short level = db().select(CATEGORY.LEVEL).from(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchOne()
            .into(Short.class);
        res.add(catId);
        if (level == 2) {
            /** 第三级，子分类 */
        } else if (level == 1) {
            /** 第二级分类 */
            List<Short> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId))
                .fetch(CATEGORY.CAT_ID);
            res.addAll(children);
        } else if (level == 0) {
            /** 第一级分类 */
            List<Short> children = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(catId))
                .fetch(CATEGORY.CAT_ID);
            res.addAll(children);
            for (Short id : children) {
                List<Short> grandchildren = db().select(CATEGORY.CAT_ID).from(CATEGORY).where(CATEGORY.PARENT_ID.eq(id))
                    .fetch(CATEGORY.CAT_ID);
                res.addAll(grandchildren);
            }
        }
        return res;
    }

    /**
     * 根据父id获取子分类
     *
     * @param parentId
     * @return
     */
    public List<ChildCateVo> getSysCateChild(Short parentId) {
        List<ChildCateVo> child = db().select().from(CATEGORY).where(CATEGORY.PARENT_ID.eq(parentId)).fetch().into(ChildCateVo.class);
        return child;
    }

    public  LinkedList<Map<String,Object>> findParentByChildId(Short catId) {
        LinkedList<Map<String,Object>> result=new LinkedList<>();

        Map<String, Object> parents = new HashMap<>(2);

        Record2<String, Short> stringShortRecord2 = db().select(CATEGORY.CAT_NAME, CATEGORY.PARENT_ID)
            .from(CATEGORY).where(CATEGORY.CAT_ID.eq(catId)).fetchAny();
        Short parentId = stringShortRecord2.get(CATEGORY.PARENT_ID);

        parents.put(CATEGORY.CAT_ID.getName(),catId);
        parents.put(CATEGORY.CAT_NAME.getName(),stringShortRecord2.get(CATEGORY.CAT_NAME));

        result.addFirst(parents);

        while (parentId != 0) {
            Record3<Short, String, Short> record3 = db().select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME, CATEGORY.PARENT_ID).from(CATEGORY)
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
        List<Short> catIds = new ArrayList<Short>(goodsPageListVos.size());

        for (GoodsPageListVo vo : goodsPageListVos) {
            catIds.add(vo.getCatId());
        }

        Map<Short, String> catIdNameMap = db().select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME).from(CATEGORY)
            .where(CATEGORY.CAT_ID.in(catIds)).fetch().intoMap(CATEGORY.CAT_ID, CATEGORY.CAT_NAME);

        for (GoodsPageListVo goodsPageListVo : goodsPageListVos) {
            Short catId = goodsPageListVo.getCatId();
            goodsPageListVo.setCatName(catIdNameMap.get(catId));
        }
    }
}
