package com.vpu.mp.service.shop.prescription;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionListVo;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionParam;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处方
 * @author 孔德成
 * @date 2020/7/2 14:25
 */
@Service
public class PrescriptionService extends ShopBaseService {

    @Autowired
    protected PrescriptionDao prescriptionDao;
    @Autowired
    protected PrescriptionItemDao prescriptionItemDao;
    @Autowired
    protected GoodsMedicalInfoDao goodsMedicalInfoDao;
    @Autowired
    protected GoodsDao goodsDao;


    /**
     * 保存处方
     */
    public void addPrescription(PrescriptionParam param){
        param.setId(null);
        int save = prescriptionDao.save(param);
        if (save>0) {
            param.getList().forEach(item->item.setId(null));
            prescriptionItemDao.batchSave(param.getList());
        }
    }

    /**
     * 查询处方
     * @return
     */
    public PrescriptionVo getById(Integer id){
        return prescriptionDao.getById(id,PrescriptionVo.class);
    }

    /**
     * 分页
     * @return
     */
    public PageResult<PrescriptionListVo> listPageResult(PrescriptionListParam param){
        return prescriptionDao.listPageResult(param);
    }

    /**
     *  获取处方关联商品
     *  1商品id 2通用名+系数 3系数
     * @param goodsId 商品id
     * @param goodsCommonName 商品通用名
     * @param goodsQualityRatio 商品规格系数
     * @return 处方明细
     */
    public PrescriptionVo getByGoodsInfo(Integer goodsId, String goodsCommonName, String goodsQualityRatio,String productionEnterprise) {
        PrescriptionVo prescriptionItem = prescriptionDao.getValidByGoodsId(goodsId);
        if (prescriptionItem==null){
            prescriptionItem=  prescriptionDao.getValidByCommonNameAndQualityRatio(goodsCommonName,goodsQualityRatio,productionEnterprise);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonNameAndQualityRatio(goodsCommonName, goodsQualityRatio);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonName(goodsCommonName);
        }
        return prescriptionItem;
    }

    /**
     * 获取处方简要信息
     * @param prescriptionNoList
     */
    public void ListSimpleByprescriptionNo(List<String> prescriptionNoList) {
        prescriptionDao.ListSimpleByprescriptionNo(prescriptionNoList);
    }
}
