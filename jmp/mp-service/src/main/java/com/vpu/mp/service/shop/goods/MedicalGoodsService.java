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
import com.vpu.mp.service.pojo.shop.goods.entity.Goods;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if (goods.getSpecs() != null && goods.getSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goods.getSpecs(), goods.getGoodsId());
        }
        goods.calculateSkuPrdSpecsBySpecs();
        goodsSpecProductRepository.batchSkuInsert(goods.getGoodsSpecProducts(), goods.getGoodsId());

        // 处理标签、图片额外信息
        List<Integer> labelIds = goods.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCouple> goodsLabelCouples = GoodsLabelCouple.generateCouples(labelIds, goods.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
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

    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void update(@RedisLockKeys Integer shopId, Goods goods) {
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
        goodsRepository.update(goods);

        // 删除旧的spec组
        goodsSpecProductRepository.deleteSpecByGoodsId(goods.getGoodsId());
        // 处理sku
        if (goods.getSpecs() != null && goods.getSpecs().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goods.getSpecs(), goods.getGoodsId());
        }
        goods.calculateSkuPrdSpecsBySpecs();
        // 处理需要修改的sku
        List<GoodsSpecProduct> goodsSpecProducts = GoodsSpecProduct.filterSkuForUpdateOrInsert(goods.getGoodsSpecProducts(), true);
        goodsSpecProductRepository.batchSkuUpdate(goodsSpecProducts);
        // 处理需要新增的sku
        goodsSpecProducts = GoodsSpecProduct.filterSkuForUpdateOrInsert(goods.getGoodsSpecProducts(), false);
        goodsSpecProductRepository.batchSkuInsert(goodsSpecProducts,goods.getGoodsId());

        // 处理标签
        goodsLabelRepository.deleteCouple(Collections.singletonList(goods.getGoodsId()),MedicalLabelConstant.GTA_GOODS);
        List<Integer> labelIds = goods.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCouple> goodsLabelCouples = GoodsLabelCouple.generateCouples(labelIds, goods.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
            goodsLabelRepository.batchInsertCouple(goodsLabelCouples);
        }

        // 处理图片
        goodsImgDao.deleteByGoodsId(goods.getGoodsId());
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
