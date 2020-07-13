package com.vpu.mp.service.shop.goods;

import com.vpu.mp.common.pojo.shop.table.GoodsBrandDo;
import com.vpu.mp.dao.shop.brand.GoodsBrandDao;
import com.vpu.mp.service.pojo.shop.medical.brand.vo.GoodsBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2020年07月11日
 */
@Service
public class MedicalGoodsBrandService {
    @Autowired
    GoodsBrandDao goodsBrandDao;

    /**
     * 根据品牌id获取其简单信息
     * @param brandId 品牌id
     * @return
     */
    public GoodsBrandVo getGoodsBrandById(Integer brandId){
        GoodsBrandDo goodsBrandDo = goodsBrandDao.getByBrandId(brandId);
        if (goodsBrandDo == null) {
            return null;
        }
        GoodsBrandVo vo =new GoodsBrandVo();
        vo.setBrandId(goodsBrandDo.getId());
        vo.setBrandName(goodsBrandDo.getBrandName());
        return vo;
    }
}
