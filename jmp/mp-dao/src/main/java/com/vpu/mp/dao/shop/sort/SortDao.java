package com.vpu.mp.dao.shop.sort;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.sort.MedicalGoodsSortConstant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.SORT;

/**
 * 分类dao
 * @author 李晓冰
 * @date 2020年07月07日
 */
@Repository
public class SortDao extends ShopBaseDao {

    /**
     * 获取分类的祖先点id集合
     * @param sortId
     * @return 祖先节点结合
     */
    public List<Integer> getParentsSortIds(Integer sortId) {
        List<Integer> parentIds = new ArrayList<>(2);
        Integer parentId = db().select(SORT.PARENT_ID).from(SORT).where(SORT.SORT_ID.eq(sortId)).fetchAny(SORT.PARENT_ID);
        while (parentId != null && !MedicalGoodsSortConstant.ROOT_PARENT.equals(parentId)) {
            parentIds.add(parentId);
            parentId = db().select(SORT.PARENT_ID).from(SORT).where(SORT.SORT_ID.eq(parentId)).fetchAny(SORT.PARENT_ID);
        }
        return parentIds;
    }

}
