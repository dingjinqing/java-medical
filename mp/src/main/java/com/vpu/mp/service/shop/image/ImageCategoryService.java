package com.vpu.mp.service.shop.image;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.shop.image.ImageCategoryService.CategoryTreeItem;

import lombok.Data;

import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import com.vpu.mp.db.shop.tables.pojos.UploadedImageCategory;
import com.vpu.mp.db.shop.tables.records.UploadedImageCategoryRecord;
/**
 * 
 * @author 新国
 *
 */
public class ImageCategoryService extends BaseService {

	/**
	 * 添加分类
	 * 
	 * @param cat
	 * @return
	 */
	public UploadedImageCategoryRecord addCategory(UploadedImageCategory cat) {
		UploadedImageCategoryRecord record = db().newRecord(UPLOADED_IMAGE_CATEGORY, cat);
		record.insert();
		UInteger[] catIds = getUpCatIds(record.getImgCatId().intValue());
		record.setCatIds(StringUtils.join(catIds));
		record.setLevel((byte) catIds.length);
		record.update();
		return record;
	}

	/**
	 * 删除分类及其子分类
	 * 
	 * @param catId
	 * @return
	 */
	public int removeCategory(Integer catId) {
		if (catId == 0) {
			return 0;
		}
		List<UInteger> ids = this.getChildCategoryIds(catId, true, true);
		return db()
				.delete(UPLOADED_IMAGE_CATEGORY)
				.where(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID.in(ids.toArray(new UInteger[0])))
				.execute();
	}

	/**
	 * 得到子分类ID列表
	 * 
	 * @param parentId
	 * @param includeParentId
	 * @param includeDecent
	 * @return
	 */
	public List<UInteger> getChildCategoryIds(Integer parentId, boolean includeParentId, boolean includeDecent) {
		Result<UploadedImageCategoryRecord> records = getChildCategory(parentId, includeParentId, includeDecent);
		return records.getValues(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID);
	}

	/**
	 * 得到子分类列表
	 * 
	 * @param parentId
	 * @param includeParentId
	 * @param includeDecent
	 * @return
	 */
	public Result<UploadedImageCategoryRecord> getChildCategory(Integer parentId, boolean includeParentId,
			boolean includeDecent) {
		if (parentId == 0) {
			if (includeDecent) {
				return this.getAll();
			} else {
				return db().selectFrom(UPLOADED_IMAGE_CATEGORY).where(DSL.val(1).eq(2)).fetch();
			}
		}
		UploadedImageCategoryRecord record = this.getCategoryById(parentId);
		if (includeDecent) {
			return db()
					.selectFrom(UPLOADED_IMAGE_CATEGORY)
					.where(UPLOADED_IMAGE_CATEGORY.CAT_IDS.like(this.prefixLikeValue(record.getCatIds())))
					.fetch();
		} else {
			return db()
					.selectFrom(UPLOADED_IMAGE_CATEGORY)
					.where(UPLOADED_IMAGE_CATEGORY.CAT_IDS.eq(record.getCatIds()))
					.fetch();
		}
	}

	/**
	 * 得到所有分类
	 * 
	 * @return
	 */
	public Result<UploadedImageCategoryRecord> getAll() {
		return db().fetch(UPLOADED_IMAGE_CATEGORY);
	}

	/**
	 * 移动分类
	 * @param catId
	 * @param newParentId
	 * @return
	 */
	public int moveCategory(Integer catId, Integer newParentId) {
		UploadedImageCategoryRecord record = this.getCategoryById(catId);
		if (record == null) {
			return 0;
		}
		UploadedImageCategoryRecord parentRecord = newParentId == 0 ? null : this.getCategoryById(newParentId);
		Integer levelDiff = parentRecord != null ? parentRecord.getLevel() + 1 - record.getLevel()
				: 0 - record.getLevel();
		String oldCatIdsPrefix = record.getCatIds();
		String newCatIdPreifx = parentRecord != null ? parentRecord.getCatIds() + "," + record.getImgCatId()
				: record.getImgCatId().toString();
		return db().update(UPLOADED_IMAGE_CATEGORY)
				.set(UPLOADED_IMAGE_CATEGORY.CAT_IDS,
						DSL.concat(newCatIdPreifx,
								DSL.substring(UPLOADED_IMAGE_CATEGORY.CAT_IDS, oldCatIdsPrefix.length() + 1)))
				.set(UPLOADED_IMAGE_CATEGORY.LEVEL, UPLOADED_IMAGE_CATEGORY.LEVEL.add(levelDiff))
				.where(UPLOADED_IMAGE_CATEGORY.CAT_IDS.like(this.prefixLikeValue(oldCatIdsPrefix)))
				.execute();
	}

	public UInteger[] getUpCatIds(Integer catId) {
		List<UInteger> catIds = new ArrayList<UInteger>();
		if (catId > 0) {
			UploadedImageCategoryRecord cat = this.getCategoryById(catId);
			if (cat == null) {
				return catIds.toArray(new UInteger[0]);
			}
			Integer parentCatId = cat.getImgCatParentId();
			while (parentCatId > 0) {
				cat = this.getCategoryById(parentCatId);
				catIds.add(cat.getImgCatId());
				parentCatId = cat.getImgCatParentId();
			}
			UInteger[] ids = catIds.toArray(new UInteger[0]);
			ArrayUtils.reverse(ids);
			return ids;
		}
		return catIds.toArray(new UInteger[0]);
	}

	/**
	 * 得到分类
	 * 
	 * @param catId
	 * @return
	 */
	public UploadedImageCategoryRecord getCategoryById(Integer catId) {
		return db().selectFrom(UPLOADED_IMAGE_CATEGORY)
				.where(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID.eq(UInteger.valueOf(catId))).fetchOne();
	}


	/**
	 * 判断是否有子分类
	 * 
	 * @param parentCatId
	 * @return
	 */
	public boolean hasChildCategory(Integer parentCatId) {
		return db().fetchCount(UPLOADED_IMAGE_CATEGORY, UPLOADED_IMAGE_CATEGORY.IMG_CAT_PARENT_ID.eq(parentCatId)) > 0;
	}
	
	final public static class CategoryMap {
		public Map<Integer, UploadedImageCategoryRecord> map = new HashMap<Integer, UploadedImageCategoryRecord>();
		public Map<Integer, List<UploadedImageCategoryRecord>> parent = new HashMap<Integer, List<UploadedImageCategoryRecord>>();
	}

	/**
	 * 得到指定等级的分类树
	 * @param maxLevel
	 * @return
	 */
	public Result<UploadedImageCategoryRecord> getCategoryTree(Byte maxLevel) {
		Result<UploadedImageCategoryRecord> result = db().fetch(UPLOADED_IMAGE_CATEGORY, DSL.falseCondition());
		CategoryMap catMap = this.getCategoryMap(maxLevel);
		getChildCategoryTree(catMap.parent.get(0), catMap, result);
		return result;
	}

	/**
	 * 递归获取分类树
	 * @param list
	 * @param catMap
	 * @param result
	 */
	protected void getChildCategoryTree(List<UploadedImageCategoryRecord> list, CategoryMap catMap,
			Result<UploadedImageCategoryRecord> result) {
		if (list == null || list.size() == 0) {
			return;
		}
		for (UploadedImageCategoryRecord record : list) {
			result.add(record);
			List<UploadedImageCategoryRecord> childCats = catMap.parent.get(record.getImgCatId().intValue());
			getChildCategoryTree(childCats, catMap, result);
		}
	}

	/**
	 * 得到最大等级
	 * @return
	 */
	public Integer getCategoryMaxLevel() {
		return (Integer) db().select(DSL.max(UPLOADED_IMAGE_CATEGORY.LEVEL)).from(UPLOADED_IMAGE_CATEGORY).fetchOne(0);
	}

	
	/**
	 * 得到分类Map
	 * @param maxLevel
	 * @return
	 */
	protected CategoryMap getCategoryMap(Byte maxLevel) {
		CategoryMap result = new CategoryMap();
		SelectWhereStep<UploadedImageCategoryRecord> select = db().selectFrom(UPLOADED_IMAGE_CATEGORY);
		if (maxLevel != -1) {
			select.where(UPLOADED_IMAGE_CATEGORY.LEVEL.le(maxLevel));
		}
		select.orderBy(UPLOADED_IMAGE_CATEGORY.IMG_CAT_PARENT_ID.asc(),
				UPLOADED_IMAGE_CATEGORY.SORT.desc(),
				UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID.asc());
		Result<UploadedImageCategoryRecord> records = select.fetch();
		for (UploadedImageCategoryRecord record : records) {
			result.map.put(record.getImgCatId().intValue(), record);
			List<UploadedImageCategoryRecord> list = result.parent.get(record.getImgCatParentId());
			if (list == null) {
				list = new ArrayList<UploadedImageCategoryRecord>();
				result.parent.put(record.getImgCatParentId(), list);
			}
			list.add(record);
		}
		return result;
	}
 
	@Data
	public static class CategoryTreeItem {
		public Integer id = 0;
		public String name = "";
		public Boolean open = true;
		public Integer pId = 0;
	}

	/**
	 * 得到ZTree图片目录列表
	 * 
	 * @param openId
	 * @return
	 */
	public List<CategoryTreeItem> getImageCategoryForZTree(Integer openId) {
		List<CategoryTreeItem> result = new ArrayList<CategoryTreeItem>();
		CategoryTreeItem root = new CategoryTreeItem();
		root.name = "我的图片";
		result.add(root);
		Result<UploadedImageCategoryRecord> records = this.getAll();
		for (UploadedImageCategoryRecord record : records) {
			CategoryTreeItem cat = new CategoryTreeItem();
			cat.id = record.getImgCatId().intValue();
			cat.name = record.getImgCatName();
			cat.pId = record.getImgCatParentId();
			cat.open = cat.pId == 0 || openId != null && openId.equals(cat.id);
			result.add(cat);
		}
		return result;
	}


}
