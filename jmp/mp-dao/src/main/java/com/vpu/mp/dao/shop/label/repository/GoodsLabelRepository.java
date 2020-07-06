package com.vpu.mp.dao.shop.label.repository;

import com.vpu.mp.common.pojo.shop.table.GoodsLabelCoupleDo;
import com.vpu.mp.dao.shop.label.GoodsLabelCoupleDao;
import com.vpu.mp.dao.shop.label.GoodsLabelDao;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCouple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Component
public class GoodsLabelRepository {
    @Autowired
    GoodsLabelDao goodsLabelDao;
    @Autowired
    GoodsLabelCoupleDao goodsLabelCoupleDao;

    public void batchInsertCouple(List<GoodsLabelCouple> goodsLabelCouples) {
        List<GoodsLabelCoupleDo> goodsLabelCoupleDos = new ArrayList<>(goodsLabelCouples.size());

        for (GoodsLabelCouple goodsLabelCouple : goodsLabelCouples) {
            GoodsLabelCoupleDo goodsLabelCoupleDo = new GoodsLabelCoupleDo();
            goodsLabelCoupleDo.setLabelId(goodsLabelCouple.getLabelId());
            goodsLabelCoupleDo.setGtaId(goodsLabelCouple.getGtaId());
            goodsLabelCoupleDo.setType(goodsLabelCouple.getType());
            goodsLabelCoupleDos.add(goodsLabelCoupleDo);
        }
        goodsLabelCoupleDao.batchInsert(goodsLabelCoupleDos);

    }
}
