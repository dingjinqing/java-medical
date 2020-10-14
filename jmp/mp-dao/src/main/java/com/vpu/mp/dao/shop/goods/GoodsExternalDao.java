package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsExternalDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsFromHisRecord;
import com.vpu.mp.db.shop.tables.records.GoodsFromStoreRecord;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.medical.goods.param.GoodsExternalPageParam;
import org.jooq.Condition;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.GoodsFromHis.GOODS_FROM_HIS;
import static com.vpu.mp.db.shop.tables.GoodsFromStore.GOODS_FROM_STORE;

/**
 * @author 李晓冰
 * @date 2020年10月13日
 */
@Repository
public class GoodsExternalDao extends ShopBaseDao {

    public void insertExternalFromHis(GoodsExternalDo externalDo){
        GoodsFromHisRecord goodsFromHisRecord = db().newRecord(GOODS_FROM_HIS);
        FieldsUtil.assignNotNull(externalDo,goodsFromHisRecord);
        goodsFromHisRecord.insert();
    }

    public void updateExternalFromHis(GoodsExternalDo externalDo){
        GoodsFromHisRecord goodsFromHisRecord = db().newRecord(GOODS_FROM_HIS);
        FieldsUtil.assignNotNull(externalDo,goodsFromHisRecord);
        goodsFromHisRecord.update();
    }

    public void insertExternalFromStore(GoodsExternalDo externalDo){
        GoodsFromStoreRecord goodsFromStoreRecord = db().newRecord(GOODS_FROM_STORE);
        FieldsUtil.assignNotNull(externalDo,goodsFromStoreRecord);
        goodsFromStoreRecord.insert();
    }

    public void updateExternalFromStore(GoodsExternalDo externalDo){
        GoodsFromStoreRecord goodsFromStoreRecord = db().newRecord(GOODS_FROM_STORE);
        FieldsUtil.assignNotNull(externalDo,goodsFromStoreRecord);
        goodsFromStoreRecord.update();
    }

    public GoodsExternalDo getExternalFromHis(String medicalKey) {
        Condition condition = GOODS_FROM_HIS.GOODS_COMMON_NAME.concat(GOODS_FROM_HIS.GOODS_QUALITY_RATIO.concat(GOODS_FROM_HIS.GOODS_PRODUCTION_ENTERPRISE)).eq(medicalKey);
        GoodsExternalDo goodsExternalDo = db().select(GOODS_FROM_HIS.ID).from(GOODS_FROM_HIS).where(GOODS_FROM_HIS.IS_DELETE.eq(DelFlag.NORMAL_VALUE).and(condition))
            .fetchAnyInto(GoodsExternalDo.class);

        return goodsExternalDo;
    }

    public GoodsExternalDo getExternalFromStore(String medicalKey) {
        Condition condition = GOODS_FROM_STORE.GOODS_COMMON_NAME.concat(GOODS_FROM_STORE.GOODS_QUALITY_RATIO.concat(GOODS_FROM_STORE.GOODS_PRODUCTION_ENTERPRISE)).eq(medicalKey);
        GoodsExternalDo goodsExternalDo = db().select(GOODS_FROM_STORE.ID).from(GOODS_FROM_STORE).where(GOODS_FROM_STORE.IS_DELETE.eq(DelFlag.NORMAL_VALUE).and(condition))
            .fetchAnyInto(GoodsExternalDo.class);

        return goodsExternalDo;
    }


    public PageResult<GoodsExternalDo> getExternalPageList(GoodsExternalPageParam param){
        if (MedicalGoodsConstant.PAGE_LIST_FROM_HIS.equals(param.getPageListFrom())) {
            return getExternalPageListFromHis(param);
        } else {
            return getExternalPageListFromHis(param);
        }
    }

    private PageResult<GoodsExternalDo> getExternalPageListFromHis(GoodsExternalPageParam param) {
        Condition baseCondition = GOODS_FROM_HIS.IS_DELETE.eq(DelFlag.NORMAL_VALUE);
        if (param.getGoodsCommonName() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_HIS.GOODS_COMMON_NAME.like(likeValue(param.getGoodsCommonName())));
        }
        if (param.getGoodsAliasName() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_HIS.GOODS_ALIAS_NAME.like(likeValue(param.getGoodsAliasName())));
        }
        if (param.getGoodsQualityRatio() != null) {
            baseCondition =baseCondition.and(GOODS_FROM_HIS.GOODS_QUALITY_RATIO.like(likeValue(param.getGoodsQualityRatio())));
        }
        if (param.getGoodsApprovalNumber() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_HIS.GOODS_APPROVAL_NUMBER.like(likeValue(param.getGoodsApprovalNumber())));
        }
        if (param.getGoodsProductionEnterprise() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_HIS.GOODS_PRODUCTION_ENTERPRISE.like(likeValue(param.getGoodsProductionEnterprise())));
        }

        SelectConditionStep<GoodsFromHisRecord> select = db().selectFrom(GOODS_FROM_HIS).where(baseCondition);
        PageResult<GoodsExternalDo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsExternalDo.class);
        return pageResult;
    }

    private PageResult<GoodsExternalDo> getExternalPageListFromStore(GoodsExternalPageParam param) {
        Condition baseCondition = GOODS_FROM_STORE.IS_DELETE.eq(DelFlag.NORMAL_VALUE);
        if (param.getGoodsCommonName() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_STORE.GOODS_COMMON_NAME.like(likeValue(param.getGoodsCommonName())));
        }
        if (param.getGoodsAliasName() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_STORE.GOODS_ALIAS_NAME.like(likeValue(param.getGoodsAliasName())));
        }
        if (param.getGoodsQualityRatio() != null) {
            baseCondition =baseCondition.and(GOODS_FROM_STORE.GOODS_QUALITY_RATIO.like(likeValue(param.getGoodsQualityRatio())));
        }
        if (param.getGoodsApprovalNumber() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_STORE.GOODS_APPROVAL_NUMBER.like(likeValue(param.getGoodsApprovalNumber())));
        }
        if (param.getGoodsProductionEnterprise() != null) {
            baseCondition = baseCondition.and(GOODS_FROM_STORE.GOODS_PRODUCTION_ENTERPRISE.like(likeValue(param.getGoodsProductionEnterprise())));
        }

        SelectConditionStep<GoodsFromStoreRecord> select = db().selectFrom(GOODS_FROM_STORE).where(baseCondition);
        PageResult<GoodsExternalDo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsExternalDo.class);
        return pageResult;
    }

}
