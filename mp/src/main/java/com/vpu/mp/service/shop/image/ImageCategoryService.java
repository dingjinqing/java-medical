package com.vpu.mp.service.shop.image;

import com.vpu.mp.service.foundation.BaseService;
import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;
import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import com.vpu.mp.db.shop.tables.pojos.UploadedImageCategory;
import com.vpu.mp.db.shop.tables.records.UploadedImageCategoryRecord;

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

//	public function moveCategory($id, $newParentId)
//    {
//        $category = $this->getRow($id);
//        if (!$category) return 0;
//        $parentCategory = $newParentId ? $this->getRow($newParentId) : null;
//        $levelDiff = $parentCategory ? $parentCategory->level + 1 - $category->level : 0 - $category->level;
//        $oldCatIdsPrefix = $category->cat_ids;
//        $newCatIdsPrefix = $parentCategory ? $parentCategory->cat_ids . "," . $category->img_cat_id : $category->img_cat_id;
//        $this->getTableBuilder()->where("img_cat_id", $id)->update(["img_cat_parent_id" => $newParentId]);
//        return $this->db->update(
//            "update " . tbl($this->tableName) . " set cat_ids=concat(?,substring(cat_ids,?)),level = level+? where cat_ids like ?",
//            [$newCatIdsPrefix, strlen($oldCatIdsPrefix) + 1, $levelDiff, $oldCatIdsPrefix . "%"]
//        );
//    }

	public UInteger[] getUpCatIds(Integer catId) {
		List<UInteger> catIds = new ArrayList<UInteger>();
		if (catId > 0) {
			UploadedImageCategoryRecord cat = this.getCategoryById(catId);
			if (cat == null) {
				return catIds.toArray(new UInteger[0]);
			}
			Integer parentCatId = cat.getImgCatParentId();
			while (parentCatId > 0) {
				cat = this.getParentCategory(parentCatId);
				catIds.add(cat.getImgCatId());
				parentCatId = cat.getImgCatParentId();
			}
			Object[] ids = catIds.toArray();
			ArrayUtils.reverse(ids);
			return (UInteger[]) ids;
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
	 * 得到父节点分类
	 * 
	 * @param catId
	 * @return
	 */
	public UploadedImageCategoryRecord getParentCategory(Integer catId) {
		return db().selectFrom(UPLOADED_IMAGE_CATEGORY).where(UPLOADED_IMAGE_CATEGORY.IMG_CAT_PARENT_ID.eq(catId))
				.fetchOne();
	}

//	public function addRow(array $data)
//    {
//        $catId = parent::addRow($data);
//        $catIds = $this->getUpCatIds($catId);
//        $data = [];
//        $data['cat_ids'] = implode(",", $catIds);
//        $data['level'] = count($catIds) - 1;
//        return $this->updateRow($catId, $data);
//    }
}
