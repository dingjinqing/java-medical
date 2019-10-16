package com.vpu.mp.service.shop.store.service;

import com.vpu.mp.db.shop.tables.records.StoreServiceCategoryRecord;
import com.vpu.mp.db.shop.tables.records.StoreServiceRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.shop.store.service.*;
import com.vpu.mp.service.shop.image.QrCodeService;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static com.vpu.mp.db.shop.tables.StoreServiceCategory.STORE_SERVICE_CATEGORY;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 *
 * 门店服务
 */
@Service

public class StoreServiceService extends ShopBaseService{

	/**
	 * 门店服务分类列表分页查询
	 * @param
	 * @return StorePageListVo
	 */
	public PageResult<StoreServiceCategoryListQueryVo> getCatePageList(StoreServiceCategoryListQueryParam param) {
        SelectWhereStep<? extends Record> select =
            db().select(STORE_SERVICE_CATEGORY.CAT_ID, STORE_SERVICE_CATEGORY.CAT_NAME, STORE_SERVICE_CATEGORY.CREATE_TIME).from(STORE_SERVICE_CATEGORY);
		select = this.buildCateOptions(select, param);
		select.where(STORE_SERVICE_CATEGORY.STORE_ID.eq(param.getStoreId())).orderBy(STORE_SERVICE_CATEGORY.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreServiceCategoryListQueryVo.class);
	}


	/**
	 * 门店服务分类的条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildCateOptions(SelectWhereStep<? extends  Record> select, StoreServiceCategoryListQueryParam param) {
		if (param == null) {
			return select;
		}

		if (!StringUtils.isEmpty(param.getCatName())) {
			select.where(STORE_SERVICE_CATEGORY.CAT_NAME.contains(param.getCatName()));
		}
		return select;
	}

	/**
	 * 门店全部服务分类列表查询
	 * @param
	 * @return StorePageListVo
	 */
    public List<StoreServiceCategoryListQueryVo> getAllStoreServiceCategory(StoreServiceCategoryListQueryParam param) {
		return db().select(STORE_SERVICE_CATEGORY.CAT_ID,STORE_SERVICE_CATEGORY.CAT_NAME,STORE_SERVICE_CATEGORY.CREATE_TIME).from(STORE_SERVICE_CATEGORY).where(STORE_SERVICE_CATEGORY.STORE_ID.eq(param.getStoreId())).fetchInto(StoreServiceCategoryListQueryVo.class);
	}

    /**
	 * 门店服务列表分页查询
	 * @param
	 * @return StorePageListVo
	 */
	public PageResult<StoreServiceListQueryVo> getServicePageList(StoreServiceListQueryParam param) {
        SelectWhereStep<? extends Record> select =
		db().select(STORE_SERVICE.ID,STORE_SERVICE.SERVICE_NAME,STORE_SERVICE.SERVICE_IMG,STORE_SERVICE.SERVICE_PRICE,STORE_SERVICE_CATEGORY.CAT_NAME,STORE_SERVICE.SALE_NUM,STORE_SERVICE.SERVICE_TYPE,STORE_SERVICE.SERVICE_SHELF,STORE_SERVICE.CREATE_TIME).
		from(STORE_SERVICE).
		leftJoin(STORE_SERVICE_CATEGORY).on(STORE_SERVICE_CATEGORY.CAT_ID.eq(STORE_SERVICE.CAT_ID));
		select = this.buildServiceOptions(select, param);
		select.where(STORE_SERVICE.STORE_ID.eq(param.getStoreId())).orderBy(STORE_SERVICE.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreServiceListQueryVo.class);
	}


    /**
	 * 门店服务的条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildServiceOptions(SelectWhereStep<? extends  Record> select, StoreServiceListQueryParam param) {
		if (param == null) {
			return select;
		}

        if(param.getCatId() != null && param.getCatId() > 0) {
			select.where(STORE_SERVICE.CAT_ID.eq(param.getCatId()));
		}
		if (!StringUtils.isEmpty(param.getServiceName())) {
			select.where(STORE_SERVICE.SERVICE_NAME.contains(param.getServiceName()));
		}
		return select;
	}

    public List<StoreServiceListQueryVo> getAllStoreServiceByStoreId(Integer storeId){
		return db().select(STORE_SERVICE.ID,STORE_SERVICE.SERVICE_NAME).from(STORE_SERVICE).where(STORE_SERVICE.STORE_ID.eq(storeId)).and(STORE_SERVICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(StoreServiceListQueryVo.class);
	}


    /**
	 * 门店服务编码生成
	 * @return String
	 */
	public String createServiceSn(){
		String serviceSn;
		do {
			serviceSn = "";
			Record record = db().select(STORE_SERVICE.ID).from(STORE_SERVICE).orderBy(STORE_SERVICE.ID.desc()).limit(1).fetchOne();
            Integer id;
			if(record == null){
                id = 0;
            }else{
			    id = record.into(Integer.class);
            }
            Random random = new Random();
            int s = random.nextInt(999)%(900) + 100;
            serviceSn = String.valueOf(id+1) + String.valueOf(s);
            serviceSn = StringUtils.leftPad(serviceSn, 9, "10");
            serviceSn = "G".concat(serviceSn);
        } while (this.hasServiceSn(serviceSn));
		return serviceSn;
	}

    /**
	 * 判断该serviceSn是否已存在
	 * @param serviceSn
	 * @return
	 */
	public Boolean hasServiceSn(String serviceSn) {
		List<Integer> ids = db().select(STORE_SERVICE.ID).from(STORE_SERVICE).where(STORE_SERVICE.SERVICE_SN.eq(serviceSn)).fetch().into(Integer.class);
		if(ids.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

    /**
	 * 新增门店服务分类
	 * @param
	 */
	public Boolean addStoreServiceCategory(StoreServiceCategoryParam storeServiceCategory) {
		StoreServiceCategoryRecord record = new StoreServiceCategoryRecord();
		this.assign(storeServiceCategory,record);
		return db().executeInsert(record) > 0 ? true : false;
	}

    /**
	 * 更新门店服务分类
     * @param storeServiceCategory
	 */
	public Boolean updateStoreServiceCategory(StoreServiceCategoryParam storeServiceCategory) {
		StoreServiceCategoryRecord record = new StoreServiceCategoryRecord();
		this.assign(storeServiceCategory, record);
		return db().executeUpdate(record) > 0 ? true : false;
	}

    /**
	 * 删除门店服务分类
     * @param catId 门店服务分类ID
	 */
	public Boolean delStoreServiceCategory(Integer catId) {
		this.transaction(()->{
			db().deleteFrom(STORE_SERVICE_CATEGORY).where(STORE_SERVICE_CATEGORY.CAT_ID.eq(catId)).execute();
            db().update(STORE_SERVICE).set(STORE_SERVICE.CAT_ID, INTEGER_ZERO).where(STORE_SERVICE.CAT_ID.eq(catId)).execute();
		});
		return true;
	}

    /**
	 * 新增门店服务
	 */
	public Boolean addStoreService(StoreServiceParam storeService) {
		storeService.setServiceSn(this.createServiceSn());
		StoreServiceRecord record = new StoreServiceRecord();
		this.assign(storeService,record);
		return db().executeInsert(record) > 0 ? true : false;
	}

    /**
     * 根据id查询门店服务详情
     */
    public StoreServiceParam getStoreService(Integer serviceId) {
        return db().selectFrom(STORE_SERVICE).where(STORE_SERVICE.ID.eq(serviceId)).fetchOneInto(StoreServiceParam.class);
    }

    /**
	 * 更新门店服务
	 */
    public void updateStoreService(StoreServiceParam storeService) {
        if (db().fetchExists(STORE_SERVICE, STORE_SERVICE.ID.eq(storeService.getId()))) {
            StoreServiceRecord record = new StoreServiceRecord();
            this.assign(storeService, record);
            db().executeUpdate(record);

        } else {
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
        }
    }

	/**
	 * 删除门店服务
     * @param id 服务id
	 */
    public void delStoreService(Integer id) {
        if (db().fetchExists(STORE_SERVICE, STORE_SERVICE.ID.eq(id))) {
            db().update(STORE_SERVICE).set(STORE_SERVICE.DEL_FLAG, DelFlag.DISABLE.getCode()).where(STORE_SERVICE.ID.eq(id)).execute();
        } else {
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
        }
	}

    /**
     * 批量上/下架门店服务
     * @param serviceIds 服务id集合
     * @param serviceShelf 上下架状态值:1:上架，0:下架
     */
    public void batchOnOrOFFStoreService(Integer[] serviceIds, Byte serviceShelf) {
        db().update(STORE_SERVICE).set(STORE_SERVICE.SERVICE_SHELF, serviceShelf).where(STORE_SERVICE.ID.in(serviceIds)).execute();
    }

    @Autowired
    QrCodeService qrCodeService;
    private static final String PARAM = "service_id=";

    /**
     * Share store service share qr code vo.分享门店服务
     *
     * @param serviceId the service id
     * @return the share qr code vo
     */
    public ShareQrCodeVo shareStoreService(Integer serviceId) {
        String pathParam = PARAM + serviceId;
        String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.SERVICE_APPOINTMENT, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;
    }

}
