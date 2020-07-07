package com.vpu.mp.dao.shop.label.repository;

import com.vpu.mp.common.pojo.shop.table.GoodsLabelCoupleDo;
import com.vpu.mp.common.pojo.shop.table.GoodsLabelDo;
import com.vpu.mp.dao.shop.label.GoodsLabelCoupleDao;
import com.vpu.mp.dao.shop.label.GoodsLabelDao;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCoupleVal;
import com.vpu.mp.service.pojo.shop.label.vo.GoodsLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * 批量插入标签关联数据
     * @param goodsLabelCoupleVals
     */
    public void batchInsertCouple(List<GoodsLabelCoupleVal> goodsLabelCoupleVals) {
        List<GoodsLabelCoupleDo> goodsLabelCoupleDos = new ArrayList<>(goodsLabelCoupleVals.size());

        for (GoodsLabelCoupleVal goodsLabelCoupleVal : goodsLabelCoupleVals) {
            GoodsLabelCoupleDo goodsLabelCoupleDo = new GoodsLabelCoupleDo();
            goodsLabelCoupleDo.setLabelId(goodsLabelCoupleVal.getLabelId());
            goodsLabelCoupleDo.setGtaId(goodsLabelCoupleVal.getGtaId());
            goodsLabelCoupleDo.setType(goodsLabelCoupleVal.getType());
            goodsLabelCoupleDos.add(goodsLabelCoupleDo);
        }
        goodsLabelCoupleDao.batchInsert(goodsLabelCoupleDos);
    }

    /**
     * 获取商品指定的标签
     * @param goodsId 商品id
     * @return 标签信息
     */
    public List<GoodsLabelVo> getGoodsPointLabels(Integer goodsId){
        List<GoodsLabelDo> goodsLabelDos = goodsLabelDao.getLabelByGtaInfo(Collections.singletonList(goodsId), MedicalLabelConstant.GTA_GOODS);
        List<GoodsLabelVo> retList = new ArrayList<>(goodsLabelDos.size());

        for (GoodsLabelDo goodsLabelDo : goodsLabelDos) {
            GoodsLabelVo vo = new GoodsLabelVo(goodsLabelDo.getId(),goodsLabelDo.getName());
            retList.add(vo);
        }
        return retList;
    }

    /**
     * 获取商品关联的所有标签
     * @param sortIds 商家分类集合
     * @return
     */
    public List<GoodsLabelVo> getGoodsNormalLabels(List<Integer> sortIds) {
        List<GoodsLabelDo> sortLabels = goodsLabelDao.getLabelByGtaInfo(sortIds, MedicalLabelConstant.GTA_SORT);
        List<GoodsLabelDo> allLabels = goodsLabelDao.getLabelByGtaInfo(null, MedicalLabelConstant.GTA_ALL);
        sortLabels.addAll(allLabels);

        List<GoodsLabelVo> retList = new ArrayList<>(sortLabels.size());
        for (GoodsLabelDo goodsLabelDo : sortLabels) {
            GoodsLabelVo vo = new GoodsLabelVo(goodsLabelDo.getId(),goodsLabelDo.getName());
            retList.add(vo);
        }
        return retList;
    }


    /**
     * 删除标签关联信息
     * @param gtaIds 待删除id集合
     * @param gtaType 要删除的类型
     */
    public void deleteCouple(List<Integer> gtaIds, Byte gtaType){
        goodsLabelCoupleDao.deleteCouple(gtaIds,gtaType);
    }
}
