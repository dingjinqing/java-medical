package com.vpu.jmd.service.shop;



import cn.hutool.core.date.DateUtil;
import com.vpu.jmd.table.main.tables.records.ShopTagRecord;
import com.vpu.jmd.table.main.tables.records.TagRecord;
import com.vpu.jmd.service.base.MainBaseService;
import com.vpu.jmd.service.base.bo.DelFlag;
import com.vpu.jmd.service.base.bo.PageResult;
import com.vpu.jmd.service.shop.bo.tag.ShopTagPageListParam;
import com.vpu.jmd.service.shop.bo.tag.ShopTagPageListVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.jmd.table.main.Tables.SHOP_TAG;
import static com.vpu.jmd.table.main.Tables.TAG;


/**
 * system店铺管理-店铺标签管理
 * @author liangchen
 * @date 2020.05.27
 */
@Service
public class ShopTagService extends MainBaseService {
    /**
     * 获取店铺标签列表
     * @param param 标签名称 分页信息
     * @return 标签列表
     */
    public PageResult<ShopTagPageListVo> getShopTagPageList(ShopTagPageListParam param){
        //构造查询语句
        SelectConditionStep selectPageListSql = db().select()
            .from(TAG)
            .where(TAG.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        //按条件查询
        if (StringUtils.isNotBlank(param.getName())){
            selectPageListSql.and(TAG.NAME.like(this.likeValue(param.getName())));
        }
        selectPageListSql.orderBy(TAG.ID.desc());
        //整合sql和分页信息
        PageResult<ShopTagPageListVo> pageResult =
            getPageResult(selectPageListSql,param.getCurrentPage(),param.getPageRows(),ShopTagPageListVo.class);
        //处理出参-添加店铺数量信息
        pageResult.getDataList().forEach(v-> v.setShopNum(getTagHasShopNum(v.getId())));
        return pageResult;
    }

    /**
     * 获得当前标签下店铺的数量
     * @param id 标签id
     * @return 店铺数量
     */
    private Integer getTagHasShopNum(Integer id){
        Integer shopNum;
        shopNum = db().select(DSL.count(SHOP_TAG.ID))
            .from(SHOP_TAG)
            .leftJoin(TAG).on(SHOP_TAG.TAG_ID.eq(TAG.ID))
            .where(SHOP_TAG.TAG_ID.eq(id))
            .and(TAG.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchOptionalInto(Integer.class)
            .orElse(ShopTagConstant.DEFAULT_SHOP_NUM);
        return shopNum;
    }

    /**
     * 添加新标签
     * @param param 标签名称
     * @return 是否添加成功
     */
    public Boolean addNewShopTag(ShopTagOperationParam param){
        if (StringUtils.isNotBlank(param.getName())){
            param.setDelFlag(DelFlag.NORMAL_VALUE);
            TagRecord tagRecord = db().newRecord(TAG,param);
            int affectRows = db().executeInsert(tagRecord);
            return affectRows > 0;
        }else {
            return false;
        }
    }

    /**
     * 编辑标签
     * @param param 标签id 新的标签名称
     * @return 是否编辑成功
     */
    public Boolean updateShopTag(ShopTagOperationParam param){
        if (StringUtils.isNotBlank(param.getName())&&null!=param.getId()){
            param.setDelFlag(DelFlag.NORMAL_VALUE);
            TagRecord tagRecord = db().newRecord(TAG,param);
            int affectRows = db().executeUpdate(tagRecord,TAG.ID.eq(param.getId()));
            return affectRows > 0;
        }else {
            return false;
        }
    }

    /**
     * 删除标签
     * @param param 标签id
     * @return 是否删除成功
     */
    public Boolean deleteShopTag(ShopTagOperationParam param){
        if (null!=param.getId()){
            param.setDelFlag(DelFlag.DISABLE_VALUE);
            param.setDelTime(DateUtil.date().toTimestamp());
            TagRecord tagRecord = db().newRecord(TAG,param);
            int affectRows = db().executeUpdate(tagRecord,TAG.ID.eq(param.getId()));
            return affectRows > 0;
        }else {
            return false;
        }
    }

    /**
     * 根据shopId获取已经设置的id和name
     * @param shopId
     * @return
     */
	public List<SystemShopTagVo> getTagInfoByShopId(Integer shopId) {
		return db().select(SHOP_TAG.TAG_ID, TAG.NAME.as("tagName")).from(SHOP_TAG, TAG)
				.where(SHOP_TAG.TAG_ID.eq(TAG.ID).and(SHOP_TAG.SHOP_ID.eq(shopId))).fetchInto(SystemShopTagVo.class);
	}

	/**
	 * tagId获取shopId
	 * @param tagIds
	 * @return
	 */
	public List<Integer> getShopIdByTagId(List<Integer> tagIds) {
		return db().select(SHOP_TAG.SHOP_ID).from(SHOP_TAG).where(SHOP_TAG.TAG_ID.in(tagIds)).fetchInto(Integer.class);

	}
	public Boolean setShopTag(ShopTagSetParam param){
        if (null!=param.getShopId()){
            delShopTag(param.getShopId());
            if (null!=param.getTagIds()&&param.getTagIds().size()>0){
                param.getTagIds().forEach(t->{
                    ShopTagRecord shopTagRecord = db().newRecord(SHOP_TAG);
                    shopTagRecord.setShopId(param.getShopId());
                    shopTagRecord.setTagId(t);
                    db().executeInsert(shopTagRecord);
                });
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除店铺标签
     * @param shopId 店铺id
     */
    private void delShopTag(Integer shopId){
        db().deleteFrom(SHOP_TAG).where(SHOP_TAG.SHOP_ID.eq(shopId)).execute();
    }

    /**
     * 获取所有店铺标签
     * @return 标签
     */
    public List<ShopTagAllTagsVo> getAllShopTags(){
        List<ShopTagAllTagsVo> vo= db().select()
            .from(TAG)
            .where(TAG.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .orderBy(TAG.ID.desc())
            .fetchInto(ShopTagAllTagsVo.class);

        return vo;
    }
}
