package com.vpu.mp.service.shop.goods;

import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
import com.vpu.mp.dao.shop.sort.SortDao;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.bo.LabelRelationInfoBo;
import com.vpu.mp.service.pojo.shop.label.vo.GoodsLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2020年07月09日
 */
@Service
public class MedicalGoodsLabelService {
    @Autowired
    private GoodsLabelRepository goodsLabelRepository;
    /**需要移动至sortService*/
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
        labelRelationInfoBo.setIsAll(gtaIds!=null);

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

    public Map<Integer, List<GoodsLabelVo>> getLabelGtaLabelMap(List<Integer> gtaIds, Byte type) {
        return null;
    }
}
