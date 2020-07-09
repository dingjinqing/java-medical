package com.vpu.mp.service.shop.goods;

import com.vpu.mp.common.pojo.shop.repository.GoodsLabelAo;
import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
import com.vpu.mp.dao.shop.sort.SortDao;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.bo.LabelRelationInfoBo;
import com.vpu.mp.service.pojo.shop.label.vo.GoodsLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2020年07月09日
 */
@Service
public class MedicalGoodsLabelService {
    @Autowired
    private GoodsLabelRepository goodsLabelRepository;
    /**
     * 需要移动至sortService
     */
    @Autowired
    private SortDao sortDao;

    /**
     * 获取标签关联的所有关联信息
     * @param labelId 标签id
     * @return
     */
    public LabelRelationInfoBo getLabelRelationInfo(Integer labelId) {
        LabelRelationInfoBo labelRelationInfoBo = new LabelRelationInfoBo();

        List<Integer> gtaIds = goodsLabelRepository.listGtaIdsByLabelIdAndType(labelId, MedicalLabelConstant.GTA_ALL);
        labelRelationInfoBo.setIsAll(gtaIds != null);

        gtaIds = goodsLabelRepository.listGtaIdsByLabelIdAndType(labelId, MedicalLabelConstant.GTA_SORT);
        if (gtaIds.size() > 0) {
            labelRelationInfoBo.setSortIds(gtaIds);
        }

        gtaIds = goodsLabelRepository.listGtaIdsByLabelIdAndType(labelId, MedicalLabelConstant.GTA_GOODS);
        if (gtaIds.size() > 0) {
            labelRelationInfoBo.setGoodsIds(gtaIds);
        }
        return labelRelationInfoBo;
    }

    /**
     * 获取关联所有商品的标签
     * @return
     */
    public List<GoodsLabelVo> getRelateAllGoodsLabels(){
        List<GoodsLabelAo> labelAos = goodsLabelRepository.listLabelAoByType(MedicalLabelConstant.GTA_ALL);

        return labelAos.stream().map(ao->{
            GoodsLabelVo vo = new GoodsLabelVo();
            vo.setId(ao.getId());
            vo.setLabelName(ao.getLabelName());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据gtaId和type 获取gtaId到其所关联的所有标签的映射
     * @param gtaIds
     * @param type
     * @return
     */
    public Map<Integer, List<GoodsLabelVo>> getLabelGtaLabelMap(List<Integer> gtaIds, Byte type) {
        List<GoodsLabelAo> labelAos = goodsLabelRepository.listLabelAoByGtaIdsAndType(gtaIds, type);
        Map<Integer, List<GoodsLabelAo>> goodsLabelAoMap = labelAos.stream().collect(Collectors.groupingBy(GoodsLabelAo::getGtaId));

        Map<Integer, List<GoodsLabelVo>> retMap = new HashMap<>(goodsLabelAoMap.size());
        for (Map.Entry<Integer, List<GoodsLabelAo>> entry : goodsLabelAoMap.entrySet()) {
            List<GoodsLabelVo> vos = entry.getValue().stream().map(ao -> {
                GoodsLabelVo vo = new GoodsLabelVo();
                vo.setId(ao.getId());
                vo.setLabelName(ao.getLabelName());
                return vo;
            }).collect(Collectors.toList());
            retMap.put(entry.getKey(),vos);
        }
        return retMap;
    }
}
