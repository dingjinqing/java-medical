package com.vpu.mp.service.saas.official;

import org.apache.commons.dbcp2.Utils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;

import com.vpu.mp.db.main.tables.pojos.ShopFreeExperience;
import com.vpu.mp.db.main.tables.records.ShopFreeExperienceRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.offical.FreeExperienceInfo;
import com.vpu.mp.service.saas.official.FreeExperienceService.FreeExperiencePageListParam;

import lombok.Data;

import static com.vpu.mp.db.main.tables.ShopFreeExperience.SHOP_FREE_EXPERIENCE;

import java.sql.Timestamp;
/**
 * 
 * System中官方申请试用服务
 * @author 黄壮壮
 * 2019-06-27 15:45
 */
public class FreeExperienceService extends BaseService{
	
	/**
	 * 
	 * 接收客户端参数类
	 */
	@Data
	final public static class FreeExperiencePageListParam{
		public String company;
		public String contact;
		public String startTime;
		public String endTime;
		public Integer provinceId;
		public Integer searchShopId;
		public Page page;
		public Byte isDeal;   // 已处理： 1 ； 未处理： 0
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @return PageResult
	 */
	public PageResult getPageList(FreeExperiencePageListParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_FREE_EXPERIENCE);
		//多条件选择
		select = this.buildOptions(select,param);
		//升序
		select.orderBy(SHOP_FREE_EXPERIENCE.FE_ID.asc());
		
		if(param.page != null && param.page.pageRows != null && param.page.pageRows>0) {
			return this.getPageResult(select,param.page.currentPage,param.page.pageRows);
		}else {
			return this.getPageResult(select, param.page.currentPage);
		}
	}
	
	/**
	 * 多条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	private SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select,FreeExperiencePageListParam param) {
		if(param == null) {
			return select;
		}
		
		//公司
		if(!StringUtils.isEmpty(param.company)) {
			select.where(SHOP_FREE_EXPERIENCE.COMPANY.eq(param.getCompany()));
		}
		
		
		//省份编号为int
		if(param.provinceId != null) {
			select.where(SHOP_FREE_EXPERIENCE.PROVINCE_ID.eq(param.getProvinceId()));
		}
		
		//开始时间
		if(param.getStartTime() != null) {
			select.where(SHOP_FREE_EXPERIENCE.ASK_TIME.ge(Util.convertToTimestamp(param.getStartTime())));
		}
		//最终时间
		if(param.getEndTime() != null) {
			select.where(SHOP_FREE_EXPERIENCE.ASK_TIME.le(Util.convertToTimestamp(param.getEndTime())));
		}
		//店铺ID
		if(param.getSearchShopId() != null) {
			select.where(SHOP_FREE_EXPERIENCE.SHOP_ID.eq(param.getSearchShopId()));
		}
		
		//是否处理
		if(param.isDeal != null) {
			select.where(SHOP_FREE_EXPERIENCE.IS_DEAL.eq(param.getIsDeal()));
		}
		return select;
	}
	
	
	/**
	 * 查询手机号是否注册成功
	 * @param mobile
	 * @return boolean
	 */
	public boolean verifyIsExist(String mobile) {
		return db().fetchCount(SHOP_FREE_EXPERIENCE, SHOP_FREE_EXPERIENCE.MOBILE.eq(mobile))>0;		 
	}
	
	/**
	 * 将用户信息持久化
	 * @param usernamedbzz
	 * @param mobile
	 */
	public int insertUserInfo(ShopFreeExperience shopFreeExperience) {
		ShopFreeExperience shop = shopFreeExperience;
		int i = this.db()
			.insertInto(SHOP_FREE_EXPERIENCE,SHOP_FREE_EXPERIENCE.CONTACT, SHOP_FREE_EXPERIENCE.MOBILE,SHOP_FREE_EXPERIENCE.SOURCE)
			.values(shop.getContact(), shop.getMobile(),shop.getSource())
			.execute();
		return i;
	}
	
	
	/**
	 * 更新用户产品信息
	 * @param info
	 * @return int 0 表示更新失败，>0表示更新的记录数
	 */
	public int updateFreeExperience(FreeExperienceInfo info) {
		System.out.println("inside updateFreeExperience method ");
		UpdateSetFirstStep<ShopFreeExperienceRecord> update =  this.db().update(SHOP_FREE_EXPERIENCE);
		UpdateSetMoreStep<ShopFreeExperienceRecord> step=null;
	
		switch(info.getKey()) {
			case "desc":
				System.out.println("desc");
				step = update.set(SHOP_FREE_EXPERIENCE.DESC, info.getDesc());
				
				break;
			case "provinceId":
				System.out.println("proviceId");
				step = update.set(SHOP_FREE_EXPERIENCE.PROVINCE_ID, info.getProvinceId());
				break;
			default:
				System.out.println("default switch");
				step =  null;
		}
		if(step != null) {
			return step.where(SHOP_FREE_EXPERIENCE.FE_ID.eq(info.getFeId())).execute();
		}
		else
			return 0;
	}
}
