package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
