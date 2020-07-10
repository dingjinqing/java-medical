package com.vpu.mp.dao.shop.label;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.GoodsLabelDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import org.jooq.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsLabelDao extends ShopBaseDao {

    /**
     * 根据标签id集合查询有效标签信息
     * @param labelIds
     * @return
     */
    public List<GoodsLabelDo> getLabelByLabelIds(List<Integer> labelIds) {
        return db().selectDistinct(GOODS_LABEL.asterisk())
            .from(GOODS_LABEL)
            .where(GOODS_LABEL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS_LABEL.ID.in(labelIds)))
            .fetchInto(GoodsLabelDo.class);
    }

    /**
     * 获取不同类型的商品标签
     * @param gatIds  待限制类型id
     * @param gtaType 限制的类型
     * @return
     */
    public List<GoodsLabelDo> getLabelByGtaInfo(List<Integer> gatIds, Byte gtaType) {
        Condition baseCondition = GOODS_LABEL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS_LABEL_COUPLE.TYPE.eq(gtaType));

        if (!MedicalLabelConstant.GTA_ALL.equals(gtaType)) {
            baseCondition = baseCondition.and(GOODS_LABEL_COUPLE.LABEL_ID.in(gatIds));
        }

        List<GoodsLabelDo> goodsLabelDos = db().select(GOODS_LABEL.asterisk())
            .from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
            .where(baseCondition).fetchInto(GoodsLabelDo.class);

        return goodsLabelDos;
    }

}
