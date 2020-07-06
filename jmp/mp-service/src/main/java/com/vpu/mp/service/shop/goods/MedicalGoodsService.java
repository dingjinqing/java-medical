package com.vpu.mp.service.shop.goods;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.util.medical.DateFormatStr;
import com.vpu.mp.common.pojo.shop.table.GoodsImgDo;
import com.vpu.mp.dao.shop.goods.repository.GoodsRepository;
import com.vpu.mp.dao.shop.goods.repository.GoodsSpecProductRepository;
import com.vpu.mp.dao.shop.img.GoodsImgDao;
import com.vpu.mp.dao.shop.label.repository.GoodsLabelRepository;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.entity.Goods;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.sku.entity.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Service
public class MedicalGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsSpecProductRepository goodsSpecProductRepository;
    @Autowired
    private GoodsLabelRepository goodsLabelRepository;
    @Autowired
    private GoodsImgDao goodsImgDao;

    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void insert(@RedisLockKeys Integer shopId, Goods goods) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goods.getGoodsSn())) {
            goods.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsRepository.isGoodsSnExist(goods.getGoodsSn())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goods.calculateGoodsPriceWeight();
        goodsRepository.insert(goods);
        // 处理sku
        if (!MedicalGoodsConstant.DEFAULT_SKU.equals(goods.getIsDefaultProduct())) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goods.getSpecs(), goods.getGoodsId());
            Map<String, Spec> specNameMap = Spec.mapNameToSpec(goods.getSpecs());
            for (GoodsSpecProduct goodsSpecProduct : goods.getGoodsSpecProducts()) {
                goodsSpecProduct.calculatePrdSpecs(specNameMap);
            }
        }
        goodsSpecProductRepository.batchSkuInsert(goods.getGoodsSpecProducts(), goods.getGoodsId());

        // 处理标签、图片额外信息
        List<Integer> labelIds = goods.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCouple> goodsLabelCouples = new ArrayList<>();
            for (Integer labelId : labelIds) {
                goodsLabelCouples.add(new GoodsLabelCouple(labelId, goods.getGoodsId(), MedicalLabelConstant.GTA_GOODS));
            }
            goodsLabelRepository.batchInsertCouple(goodsLabelCouples);
        }
        // 处理图片
        if (goods.getImgPaths() != null && goods.getImgPaths().size() > 0) {
            List<GoodsImgDo> goodsImgDos = new ArrayList<>(goods.getImgPaths().size());
            for (String imgPath : goods.getImgPaths()) {
                goodsImgDos.add(new GoodsImgDo(goods.getGoodsId(), imgPath));
            }
            goodsImgDao.batchInsert(goodsImgDos);
        }
    }

    /**
     * 当前时间
     * 生成商品GoodsSn
     * @return
     */
    private String generateGoodsSn() {
        int count = goodsRepository.countAllGoods();
        String nowStr = DateUtil.format(new DateTime(), DateFormatStr.DATE_FORMAT_FULL_COMPACT);
        return String.format("G%s-%08d", nowStr, count);
    }
}
