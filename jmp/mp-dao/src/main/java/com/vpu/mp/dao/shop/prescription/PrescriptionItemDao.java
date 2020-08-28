package com.vpu.mp.dao.shop.prescription;

import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionItemRecord;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionItemVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionItemInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionItemParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION_ITEM;

/**
 * 处方明细
 *
 * @author 孔德成
 * @date 2020/7/2 14:21
 */
@Repository
public class PrescriptionItemDao extends ShopBaseDao {


    /**
     * 保存
     * @param param
     * @return
     */
    public int save(PrescriptionItemDo param) {
        PrescriptionItemRecord record = db().newRecord(PRESCRIPTION_ITEM, param);
        return record.insert();
    }

    /**
     * 批量保存
     * @param list
     * @return
     */
    public int[] batchSave(List<? extends PrescriptionItemDo> list){
        List<PrescriptionItemRecord> doList=new ArrayList<>();
        list.forEach(item->{
            PrescriptionItemRecord itemRecord = db().newRecord(PRESCRIPTION_ITEM, item);
            doList.add(itemRecord);
        });
        return db().batchInsert(doList).execute();
    }

    /**
     * *****
     * 处方明细
     * @param prescriptionNo
     * @return
     */
    public List<PrescriptionItemInfoVo> listByPrescriptionNo(String prescriptionNo) {
        return db().select().from(PRESCRIPTION_ITEM)
                .where(PRESCRIPTION_ITEM.PRESCRIPTION_CODE.eq(prescriptionNo))
                .fetchInto(PrescriptionItemInfoVo.class);
    }

    /**
     * 处方明细
     * @param codeList
     * @return
     */
    public Map<String, List<PrescriptionItemDo>>  mapByPrescriptionCodeList(Collection<String> codeList){
        return db().select().from(PRESCRIPTION_ITEM)
                .where(PRESCRIPTION_ITEM.PRESCRIPTION_CODE.in(codeList))
                .fetchGroups(PRESCRIPTION_ITEM.PRESCRIPTION_CODE,PrescriptionItemDo.class);
    }

    /**
     * *****
     * 获取对应处方集合下的商品id
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByPrescriptionNos(List<String> prescriptionNos) {
        return db().selectDistinct(GOODS_MEDICAL_INFO.GOODS_ID).from(PRESCRIPTION_ITEM)
            .leftJoin(GOODS_MEDICAL_INFO).on(GOODS_MEDICAL_INFO.GOODS_COMMON_NAME.eq(PRESCRIPTION_ITEM.GOODS_COMMON_NAME))
            .where(PRESCRIPTION_ITEM.PRESCRIPTION_CODE.in(prescriptionNos).and(GOODS_MEDICAL_INFO.GOODS_ID.gt(0))).fetchInto(Integer.class);
    }

    /**
     * 处方下单的商品信息
     * @param prescriptionCode 处方号
     * @return
     */
    public List<PrescriptionItemDo>  listOrderGoodsByPrescriptionCode(String prescriptionCode){
        return db().select(PRESCRIPTION_ITEM.GOODS_ID,PRESCRIPTION_ITEM.PRD_ID,
                PRESCRIPTION_ITEM.MEDICINE_PRICE,
                PRESCRIPTION_ITEM.DRAG_SUM_NUM,PRESCRIPTION_ITEM.TOTAL_REBATE_MONEY,PRESCRIPTION_ITEM.REBATE_PROPORTION,PRESCRIPTION_ITEM.GOODS_SHARING_PROPORTION,PRESCRIPTION_ITEM.CREATE_TIME,
            PRESCRIPTION_ITEM.GOODS_COMMON_NAME)
                .from(PRESCRIPTION_ITEM)
                .where(PRESCRIPTION_ITEM.PRESCRIPTION_CODE.eq(prescriptionCode))
                .fetchInto(PrescriptionItemDo.class);
    }

    /**
     * @description 从his系统拉取处方详情
     * @create 2020-7-16 15:06
     * @Author zhaoxiaodong
     */

    /**
     * 根据处方明细id查询处方
     * @param id
     * @return
     */
    public PrescriptionItemDo getPrescriptionById(int id) {
        return db().select().from(PRESCRIPTION_ITEM)
            .where(PRESCRIPTION_ITEM.ID.eq(id)).fetchOneInto(PrescriptionItemDo.class);
    }

    /**
     * 更新处方详情
     * @param fetchPrescriptionItemVo 处方详情入参
     */
    public void updateHisPrescriptionItem(FetchPrescriptionItemVo fetchPrescriptionItemVo){
        PrescriptionItemRecord prescriptionItemRecord = db().select().from(PRESCRIPTION_ITEM)
            .where(PRESCRIPTION_ITEM.ID.eq(fetchPrescriptionItemVo.getId()))
            .fetchOneInto(PrescriptionItemRecord.class);
        prescriptionItemRecord.update();
        fetchPrescriptionItemVo.setId(prescriptionItemRecord.getId());

    }
}
