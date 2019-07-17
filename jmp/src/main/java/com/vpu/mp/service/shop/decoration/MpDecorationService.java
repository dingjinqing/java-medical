package com.vpu.mp.service.shop.decoration;

import static com.vpu.mp.db.main.tables.DecorationTemplate.DECORATION_TEMPLATE;
import static com.vpu.mp.db.shop.tables.PageClassification.PAGE_CLASSIFICATION;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.Record5;
import org.jooq.SelectWhereStep;

import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.shop.decoration.PageClassificationVo;
import com.vpu.mp.service.pojo.shop.decoration.PageStoreParam;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;

/**
 * 
 * @author lixinguo
 *
 */
public class MpDecorationService extends BaseService {
	/**
	 * 装修页面列表
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<XcxCustomerPageVo> getPageList(XcxCustomerPageVo param) {
		if (getPageCount() == 0) {
			this.addDefaultPage();
		}
		 SelectWhereStep<Record5<Integer, String, Timestamp, Byte, String>> select = db().select(XCX_CUSTOMER_PAGE.PAGE_ID,XCX_CUSTOMER_PAGE.PAGE_NAME,XCX_CUSTOMER_PAGE.CREATE_TIME,XCX_CUSTOMER_PAGE.PAGE_TYPE,PAGE_CLASSIFICATION.NAME)
				.from(XCX_CUSTOMER_PAGE 
				.leftJoin(PAGE_CLASSIFICATION)
				.on(XCX_CUSTOMER_PAGE.CAT_ID . eq(PAGE_CLASSIFICATION.ID)));
		select = buildOptions(select, param);
		select.orderBy(XCX_CUSTOMER_PAGE.PAGE_TYPE.desc(), XCX_CUSTOMER_PAGE.CREATE_TIME.desc());
		return this.getPageResult(select, XcxCustomerPageVo.page,XcxCustomerPageVo.class);
	}

	/**
	 * 查询条件
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<Record5<Integer, String, Timestamp, Byte, String>> buildOptions(SelectWhereStep<Record5<Integer, String, Timestamp, Byte, String>> select, XcxCustomerPageVo param) {
		Byte enabled = 1;
		select.where(XCX_CUSTOMER_PAGE.PAGE_ENABLED.eq(enabled));
		//页面内容
		if (param.getPageName() != null) {
			select.where(XCX_CUSTOMER_PAGE.PAGE_NAME.eq(param.getPageName()));
		}

		//页面分类
		if (param.getCatId() != null && param.getCatId() > 0) {
			select.where(XCX_CUSTOMER_PAGE.CAT_ID.eq(param.getCatId()));
		}

		return select;
	}

	/**
	 * 添加默认装修页
	 * 
	 * @return
	 */
	public int addDefaultPage() {
		return db()
				.insertInto(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_NAME, XCX_CUSTOMER_PAGE.PAGE_TYPE,
						XCX_CUSTOMER_PAGE.PAGE_TPL_TYPE)
				.values("首页", (byte) 1, (byte) 3)
				.execute();
	}

	/**
	 * 添加页面
	 * 
	 * @param page
	 * @return
	 */
	public XcxCustomerPageRecord addPage(XcxCustomerPageVo page) {
		XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE, page);
		record.insert();
		return record;
	}

	public int setPageCatId(Integer pageId, Integer catId) {
		return db().update(XCX_CUSTOMER_PAGE)
				.set(XCX_CUSTOMER_PAGE.CAT_ID, catId)
				.where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)))
				.execute();
	}

	public int removeRow(Integer pageId) {
		return db().deleteFrom(XCX_CUSTOMER_PAGE)
				.where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)))
				.execute();
	}

	/**
	 * 获取装修页面
	 * 
	 * @param pageId
	 * @return
	 */
	public XcxCustomerPageRecord getPageById(Integer pageId) {
		return db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)));
	}

	/**
	 * 得到一个空页面
	 * 
	 * @return
	 */
	public XcxCustomerPageRecord getEmptyPage() {
		XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);
		record.setPageContent("{}");
		return record;
	}

	/**
	 * 过滤页面内容
	 * 
	 * @param pageContent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Object>> filterPageContent(String pageContent) {
		if (pageContent == null) {
			return null;
		}
		Map<String, Map<String, Object>> result = Util.parseJson(pageContent, Map.class);
		for (Map.Entry<String, Map<String, Object>> entry : result.entrySet()) {
			Map<String, Object> v = entry.getValue();
			filterActivityModules(v);
			filterImageModules(v);
		}
		return result;
	}

	protected void filterActivityModules(Map<String, Object> v) {

	}

	protected void filterImageModules(Map<String, Object> v) {

	}

	/**
	 * 得到店铺版本模块
	 * 
	 * @return
	 */
	public Map<String, Integer> getVersionModules() {
		VersionConfig config = saas().shop.version.mergeVersion(this.shopId);
		List<String> sub2 = config.mainConfig.sub2;
		Map<String, Integer> moduleMap = new HashMap<String, Integer>(8);
		String[] modules = { "m_member_card", "m_voucher", "m_bargain", "m_video",
				"m_integral_goods", "m_seckill_goods", "m_group_draw", "m_pin_integration" };
		for (String module : modules) {
			moduleMap.put(module, sub2.contains(module) ? 1 : 0);
		}
		return moduleMap;
	}

	/**
	 * 获取装修页面数量
	 * 
	 * @return
	 */
	public int getPageCount() {
		Byte enabled = 1;
		return db().fetchCount(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_ENABLED.eq(enabled));
	}

	/**
	 * 取得全部装修页面
	 * 
	 * @param param
	 * @return
	 */
//	public Result<Record> getMpList(PageListQueryParam param) {
//		if (getPageCount() == 0) {
//			this.addDefaultPage();
//		}
//		SelectWhereStep<Record> select = db().select().from(XCX_CUSTOMER_PAGE);
//		select = this.buildOptions(select, param);
//		select.orderBy(XCX_CUSTOMER_PAGE.PAGE_TYPE.desc(), XCX_CUSTOMER_PAGE.CREATE_TIME.desc());
//		return select.fetch();
//	}

	/**
	 * 设置首页(事务处理)
	 * 
	 * @param pageId
	 * @return 
	 */
	public boolean setIndex(Integer pageId) {
		this.transaction(()->{
			db().update(XCX_CUSTOMER_PAGE)
					.set(XCX_CUSTOMER_PAGE.PAGE_TYPE, (byte) 0)
					.where(XCX_CUSTOMER_PAGE.PAGE_TYPE.eq((byte) 1))
					.execute();
		    db().update(XCX_CUSTOMER_PAGE)
					.set(XCX_CUSTOMER_PAGE.PAGE_TYPE, (byte) 1)
					.where(XCX_CUSTOMER_PAGE.PAGE_ID.eq((pageId)))
					.execute();
		});
		return true;
	}
	
	/**
	 * 获取页面分类信息
	 * @return
	 */
	public List<PageClassificationVo> getPageCate() {
		List<PageClassificationVo>list = db().select(PAGE_CLASSIFICATION.ID,PAGE_CLASSIFICATION.NAME)
				.from(PAGE_CLASSIFICATION)
				.fetch().into(PageClassificationVo.class);
		return list;
	}
	
	/**
	 * 删除装修页面
	 * @param param
	 * @return
	 */
	public int delXcxPage(PageClassificationVo param) {
		int result = db()
				.delete(XCX_CUSTOMER_PAGE)
				.where(XCX_CUSTOMER_PAGE.PAGE_ID.eq(param.getPageId()))
				.execute();
		return result;
	}
	
	/**
	 * 保存页面分类数据
	 * @param param 
	 * @param param
	 * @return
	 */
	public int setPageCate(PageClassificationVo param) {
		
		int result = db().update(XCX_CUSTOMER_PAGE)
				.set(XCX_CUSTOMER_PAGE.CAT_ID,param.getId())
				.where(XCX_CUSTOMER_PAGE.PAGE_ID.eq(param.getPageId()))
				.execute();
		return result;
	}
	
	/**
	 * 编辑保存
	 * @param info
	 * @return
	 */
	public Boolean saveDecoration(XcxCustomerPageVo info) {
		XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE, info);
		int res = db().executeUpdate(record);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 获取首页
	 * 
	 * @return
	 */
	public XcxCustomerPageRecord getIndex() {
		return db().fetchAny(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.PAGE_TYPE.eq((byte) 1));
	}

	/**
	 * 克隆系统模板
	 * 
	 * @param templateId
	 * @return
	 */
	public XcxCustomerPageRecord cloneTemplate(Integer templateId) {
		DecorationTemplateRecord record = mainDb().fetchAny(DECORATION_TEMPLATE,
				DECORATION_TEMPLATE.PAGE_ID.eq((templateId)));
		XcxCustomerPageRecord page = db().newRecord(XCX_CUSTOMER_PAGE);
		page.setPageContent(record.getPageContent());
		page.insert();
		return page;
	}

	/**
	 * 复制已有页面
	 * 
	 * @param copyId
	 * @return
	 */
	public Boolean copyDecoration(Integer pageId) {
		XcxCustomerPageRecord source = this.getPageById(pageId);
		XcxCustomerPageRecord page = db().newRecord(XCX_CUSTOMER_PAGE);
		page.setPageName(source.getPageName() + "+copy");
		page.setPageContent(source.getPageContent());
		page.setShopId(source.getShopId());
		page.setPageType(source.getPageType());
		page.setPageEnabled(source.getPageEnabled());
		page.setPageTplType(source.getPageTplType());
		page.setPageContent(source.getPageContent());
		page.setPagePublishContent(source.getPagePublishContent());
		page.setPageState(source.getPageState());
		page.setCatId(source.getCatId());
		page.insert();
		return true;
	}

	/**
	 * 获取分类下页面个数
	 * 
	 * @param catId
	 * @return
	 */
	public int getPageCount(Integer catId) {
		return db().fetchCount(XCX_CUSTOMER_PAGE, XCX_CUSTOMER_PAGE.CAT_ID.eq(catId));
	}

	

	/**
	 * 保存页面，包含添加和更新
	 * 
	 * @param page
	 * @return
	 */
	public XcxCustomerPageRecord storePage(PageStoreParam page) {
		page.setPageContent(processMapModule(page.getPageContent()));
		recordPageChange(page);
		XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE);

		record.setPageContent(page.getPageContent());
		record.setPageName(page.getPageName());
		record.setCatId(page.getCatId() == null ? 0 : page.getCatId());

		Byte toPublishState = 1;
		if (page.pageState.equals(toPublishState)) {
			record.setPagePublishContent(page.getPageContent());
		}
		if (page.pageId != null) {
			XcxCustomerPageRecord oldRecord = this.getPageById(page.pageId);
			record.setPageId((page.getPageId()));
			Byte toDraftState = 3;
			if (page.pageState.equals(toDraftState)) {
				record.setPageContent(oldRecord.getPagePublishContent());
			}
			record.update();
			return record;
		}

		record.insert();
		return record;
	}

	/**
	 * 处理Map模块
	 * 
	 * @param pageContent
	 * @return
	 */
	protected String processMapModule(String pageContent) {
		return pageContent;
	}

	/**
	 * 记录页面变化部分
	 * 
	 * @param page
	 */
	protected void recordPageChange(PageStoreParam page) {

	}


}
