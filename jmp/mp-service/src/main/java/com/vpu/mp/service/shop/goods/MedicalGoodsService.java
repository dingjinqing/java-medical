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
import com.vpu.mp.service.pojo.shop.goods.entity.GoodsEntity;
import com.vpu.mp.service.pojo.shop.label.MedicalLabelConstant;
import com.vpu.mp.service.pojo.shop.label.entity.GoodsLabelCoupleVal;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProductEntity;
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

    /**
     * 新增
     * @param shopId
     * @param goodsEntity
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void insert(@RedisLockKeys Integer shopId, GoodsEntity goodsEntity) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goodsEntity.getGoodsSn())) {
            goodsEntity.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsRepository.isGoodsSnExist(goodsEntity.getGoodsSn())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsRepository.insert(goodsEntity);
        // 处理sku
        if (goodsEntity.getSpecEntities() != null && goodsEntity.getSpecEntities().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goodsEntity.getSpecEntities(), goodsEntity.getGoodsId());
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        goodsSpecProductRepository.batchSkuInsert(goodsEntity.getGoodsSpecProducts(), goodsEntity.getGoodsId());

        // 处理标签、图片额外信息
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCoupleVal> goodsLabelCoupleVals = GoodsLabelCoupleVal.generateCouples(labelIds, goodsEntity.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
            goodsLabelRepository.batchInsertCouple(goodsLabelCoupleVals);
        }

        // 处理图片
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            List<GoodsImgDo> goodsImgDos = new ArrayList<>(goodsEntity.getImgPaths().size());
            for (String imgPath : goodsEntity.getImgPaths()) {
                goodsImgDos.add(new GoodsImgDo(goodsEntity.getGoodsId(), imgPath));
            }
            goodsImgDao.batchInsert(goodsImgDos);
        }
    }

    /**
     * 修改
     * @param shopId
     * @param goodsEntity
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    public void update(@RedisLockKeys Integer shopId, GoodsEntity goodsEntity) {
        // 验证goodsSn是否重复
        if (StrUtil.isBlank(goodsEntity.getGoodsSn())) {
            goodsEntity.setGoodsSn(generateGoodsSn());
        } else {
            if (goodsRepository.isGoodsSnExist(goodsEntity.getGoodsSn())) {
                throw new IllegalArgumentException("商品goodsSn重复");
            }
        }
        // 处理自己的价格
        goodsEntity.calculateGoodsPriceWeight();
        goodsRepository.update(goodsEntity);

        // 删除旧的spec组
        goodsSpecProductRepository.deleteSpecByGoodsId(goodsEntity.getGoodsId());
        // 处理sku
        if (goodsEntity.getSpecEntities() != null && goodsEntity.getSpecEntities().size() > 0) {
            // 先插入规格组，协助sku生成其规格描述id字符串
            goodsSpecProductRepository.batchSpecInsert(goodsEntity.getSpecEntities(), goodsEntity.getGoodsId());
        }
        goodsEntity.calculateSkuPrdSpecsBySpecs();
        // 处理需要修改的sku
        List<GoodsSpecProductEntity> goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), true);
        goodsSpecProductRepository.batchSkuUpdate(goodsSpecProductEntities);
        // 处理需要新增的sku
        goodsSpecProductEntities = GoodsSpecProductEntity.filterSkuForUpdateOrInsert(goodsEntity.getGoodsSpecProducts(), false);
        goodsSpecProductRepository.batchSkuInsert(goodsSpecProductEntities, goodsEntity.getGoodsId());

        // 处理标签
        goodsLabelRepository.deleteCouple(Collections.singletonList(goodsEntity.getGoodsId()),MedicalLabelConstant.GTA_GOODS);
        List<Integer> labelIds = goodsEntity.getLabelIds();
        if (labelIds != null && labelIds.size() > 0) {
            List<GoodsLabelCoupleVal> goodsLabelCoupleVals = GoodsLabelCoupleVal.generateCouples(labelIds, goodsEntity.getGoodsId(), MedicalLabelConstant.GTA_GOODS);
            goodsLabelRepository.batchInsertCouple(goodsLabelCoupleVals);
        }

        // 处理图片
        goodsImgDao.deleteByGoodsId(goodsEntity.getGoodsId());
        if (goodsEntity.getImgPaths() != null && goodsEntity.getImgPaths().size() > 0) {
            List<GoodsImgDo> goodsImgDos = new ArrayList<>(goodsEntity.getImgPaths().size());
            for (String imgPath : goodsEntity.getImgPaths()) {
                goodsImgDos.add(new GoodsImgDo(goodsEntity.getGoodsId(), imgPath));
            }
            goodsImgDao.batchInsert(goodsImgDos);
        }
    }

    /**
     * 删除药品
     * @param goodsId
     */
    public void deleteByGoodsIds(Integer goodsId){
        goodsRepository.deleteGoodsById(goodsId);
        goodsSpecProductRepository.deleteSkuByGoodsId(goodsId);
        goodsSpecProductRepository.deleteSpecByGoodsId(goodsId);
        goodsLabelRepository.deleteCouple(Collections.singletonList(goodsId),MedicalLabelConstant.GTA_GOODS);
        goodsImgDao.deleteByGoodsId(goodsId);
    }

    /**
     * 根据商品id搜索商品信息
     * @param goodsId
     */
    public void selectByGoodsId(Integer goodsId){

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
