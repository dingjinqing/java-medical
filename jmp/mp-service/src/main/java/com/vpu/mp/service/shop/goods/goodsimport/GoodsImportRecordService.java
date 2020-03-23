package com.vpu.mp.service.shop.goods.goodsimport;

import com.vpu.mp.db.shop.tables.records.GoodsImportDetailRecord;
import com.vpu.mp.db.shop.tables.records.GoodsImportRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsDataIIllegalEnum;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportBo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_IMPORT;

/**
 * 商品导入操作记录
 * @author 李晓冰
 * @date 2020年03月21日
 */
@Service
public class GoodsImportRecordService extends ShopBaseService {

    /**
     * 导入信息操作记录插入
     * @param totalNum 总数
     * @param filePath 文件路径
     * @param isUpdate 是否更新
     * @return 操作id
     */
    public Integer insertGoodsImportInfo(Integer totalNum, String filePath, boolean isUpdate) {
        GoodsImportRecord goodsImportRecord = db().newRecord(GOODS_IMPORT);
        goodsImportRecord.setTotalNum(totalNum);
        goodsImportRecord.setImportFilePath(filePath);
        goodsImportRecord.setIsUpdate((byte) (isUpdate?1:0));
        goodsImportRecord.insert();
        return goodsImportRecord.getId();
    }

    /**
     * 更新导入的成功数
     * @param successNum 成功数量
     * @param batchId 操作id
     */
    public void updateGoodsImportSuccessNum(Integer successNum,Integer batchId) {
        db().update(GOODS_IMPORT).set(GOODS_IMPORT.SUCCESS_NUM,successNum)
            .where(GOODS_IMPORT.ID.eq(batchId)).execute();
    }

    /**
     * 插入数据操作记录
     * @param list
     */
    public void insertGoodsImportDetailBatch(List<GoodsImportDetailRecord> list) {
        db().batchInsert(list).execute();
    }

    /**
     * 转换bo为GoodsImportDetail
     * @param bo GoodsVpuExcelImportBo 后期可以修改为一个通用的基类
     * @param e 错误类型枚举类
     * @param batchId 本次批量导入的batchId
     * @return
     */
    public GoodsImportDetailRecord convertVpuExcelImportBoToImportDetail(GoodsVpuExcelImportBo bo, GoodsDataIIllegalEnum e, Integer batchId) {
        GoodsImportDetailRecord record = new GoodsImportDetailRecord();
        record.setBatchId(batchId);
        record.setGoodsSn(bo.getGoodsSn());
        record.setPrdSn(bo.getPrdSn());
        record.setGoodsName(bo.getGoodsName());
        record.setPrdDesc(bo.getPrdDesc());
        record.setIsSuccess((byte) 0);
        record.setErrorCode(e.getErrorCode());
        return record;
    }

    /**
     * 转换bo集合为GoodsImportDetail
     * @param bos
     * @param e
     * @param batchId
     * @return
     */
    public List<GoodsImportDetailRecord> convertVpuExcelImportBosToImportDetails(List<GoodsVpuExcelImportBo> bos, GoodsDataIIllegalEnum e,Integer batchId) {
        return this.convertVpuExcelImportBosToImportDetails(bos,e,batchId,false);
    }

    /**
     * 转换bo集合为GoodsImportDetail
     * @param bos
     * @param e
     * @param batchId
     * @return
     */
    public List<GoodsImportDetailRecord> convertVpuExcelImportBosToImportDetails(List<GoodsVpuExcelImportBo> bos, GoodsDataIIllegalEnum e,int batchId,boolean isSuccess) {
        List<GoodsImportDetailRecord> records = new ArrayList<>(bos.size());
        for (GoodsVpuExcelImportBo bo : bos) {
            GoodsImportDetailRecord record = new GoodsImportDetailRecord();
            record.setBatchId(batchId);
            record.setGoodsSn(bo.getGoodsSn());
            record.setPrdSn(bo.getPrdSn());
            record.setGoodsName(bo.getGoodsName());
            record.setPrdDesc(bo.getPrdDesc());
            record.setIsSuccess((byte) (isSuccess?1:0));
            record.setErrorCode(e.getErrorCode());
            records.add(record);
        }
        return records;
    }
}
