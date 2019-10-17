package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsT;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import org.jooq.Condition;
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
     *  获取商品最紧密的标签
     * @param goodsT {@link GoodsT}
     * @return 标签对象
     */
    public GoodsLabelMpVo getGoodsClosestLabel(GoodsT goodsT) {

        Condition goodsCondition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()).and(GOODS_LABEL_COUPLE.GTA_ID.eq(goodsT.getGoodsId()));
        Condition catCondition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.CATTYPE.getCode()).and(GOODS_LABEL_COUPLE.GTA_ID.eq(goodsT.getCatId()));
        Condition sortCondition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode()).and(GOODS_LABEL_COUPLE.GTA_ID.eq(goodsT.getSortId()));
        Condition allCondition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());

        GoodsLabelMpVo labelMpVo = db().select(GOODS_LABEL.ID, GOODS_LABEL.NAME, GOODS_LABEL.LIST_PATTERN, GOODS_LABEL_COUPLE.GTA_ID)
            .from(GOODS_LABEL_COUPLE).innerJoin(GOODS_LABEL).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
            .where(GOODS_LABEL.GOODS_LIST.eq(GoodsConstant.GOODS_LIST)).and(GOODS_LABEL.DEL_FLAG.eq(1))
            .and(goodsCondition.or(catCondition).or(sortCondition).or(allCondition))
            .orderBy(GOODS_LABEL_COUPLE.TYPE.asc(), GOODS_LABEL.LEVEL.desc(), GOODS_LABEL.CREATE_TIME.desc()).fetchAny().into(GoodsLabelMpVo.class);

        if (labelMpVo != null) {
            labelMpVo.setLabelLen(labelMpVo.getName().length());
        }
        return labelMpVo;
    }

}
