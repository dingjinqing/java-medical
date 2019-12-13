package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.label.GoodsLabelMpVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 */
@Service
public class GoodsLabelMpService extends ShopBaseService {

    /**
     * 根据标签id 和 对应类型获取相关链的目标id集合
     * @param labelIds 标签id集合
     * @param type 标签关联的类型
     * @return 相关联的目标id集合
     */
    public List<Integer> getGoodsLabelCouple(List<Integer> labelIds,Byte type) {
        List<Integer> gtaIds = db().select(GOODS_LABEL_COUPLE.GTA_ID).from(GOODS_LABEL_COUPLE)
            .where(GOODS_LABEL_COUPLE.LABEL_ID.in(labelIds)).and(GOODS_LABEL_COUPLE.TYPE.eq(type)).fetchInto(Integer.class);
        return gtaIds;
    }

    /**
     * 获取小程序商品搜索页-搜索条件-标签信息
     * @return 标签信息{@link GoodsLabelMpVo}
     */
    public List<GoodsLabelMpVo> getGoodsSearchFilterCondition(){
        return db().selectDistinct(GOODS_LABEL.ID, GOODS_LABEL.NAME)
            .from(GOODS_LABEL).join(GOODS_LABEL_COUPLE).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
            .where(GOODS_LABEL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()).or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode())))
            .orderBy(GOODS_LABEL.LEVEL.desc(),GOODS_LABEL.CREATE_TIME.desc())
            .fetchInto(GoodsLabelMpVo.class);
    }
}
