package com.vpu.mp.service.shop.image;

import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;
import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UpYun;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.image.ImageDefault;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ImageListQueryParam;
import com.vpu.mp.service.pojo.shop.image.UploadImageCatNameVo;
import com.vpu.mp.service.pojo.shop.image.UploadPath;

/**
 * @author 新国
 */
@Service

public class ImageService extends ShopBaseService implements ImageDefault {

    @Autowired public ImageCategoryService category;
    
    @Autowired
    protected DomainConfig domainConfig;
    
    @Autowired
    protected UpYunConfig upYunConfig;


    /**
     * 删除单张图片
     *
     * @param imageId
     * @return
     */
    public int removeRow(Integer imageId) {
        Byte delFlag = 1;
        return db().update(UPLOADED_IMAGE)
                .set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
                .where(UPLOADED_IMAGE.IMG_ID.eq(imageId))
                .execute();
    }

    /**
     * 删除多张图片
     *
     * @param imageIds
     * @return
     */
    public int removeRows(List<Integer> imageIds) {
        Byte delFlag = 1;
        return db().update(UPLOADED_IMAGE)
                .set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
                .where(UPLOADED_IMAGE.IMG_ID.in(imageIds))
                .execute();
    }

    /**
     * 设置图片的分类
     *
     * @param imageIds
     * @param catId
     * @return
     */
    public int setCatId(Integer[] imageIds, Integer catId) {
        return db().update(UPLOADED_IMAGE)
                .set(UPLOADED_IMAGE.IMG_CAT_ID, catId)
                .where(UPLOADED_IMAGE.IMG_ID.in((imageIds)))
                .execute();
    }

    /**
     * 图片列表分页
     *
     * @param param
     * @return
     */
    public PageResult<UploadImageCatNameVo> getPageList(ImageListQueryParam param) {
        SelectWhereStep<Record> select = db().select(UPLOADED_IMAGE.asterisk(), UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME)
                .from(UPLOADED_IMAGE)
                .leftJoin(UPLOADED_IMAGE_CATEGORY)
                .on(UPLOADED_IMAGE.IMG_CAT_ID.eq(DSL.cast(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID, Integer.class)));
        select = this.buildOptions(select, param);
        select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());
        return this.getPageResult(select, param.page,param.pageRows, UploadImageCatNameVo.class);
    }


    public List<Integer> convertIntegerArray(List<Integer> array) {
        List<Integer> result = new ArrayList<Integer>();
        for (Integer i : array) {
            result.add(i.intValue());
        }
        return result;
    }

    /**
     * 拼接查询条件
     * @param select
     * @param param
     * @return
     */
    public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ImageListQueryParam param) {
        if (param == null) {
            return select;
        }
        Byte noDel = 0;
        select.where(UPLOADED_IMAGE.DEL_FLAG.eq(noDel))
                .and(UPLOADED_IMAGE.IMG_WIDTH.gt(0))
                .and(UPLOADED_IMAGE.IMG_HEIGHT.gt(0));

        if (param.imgCatId != null && param.imgCatId > 0) {
            List<Integer> imgCatIds = convertIntegerArray(category.getChildCategoryIds(param.imgCatId));
            select.where(UPLOADED_IMAGE.IMG_CAT_ID.in(imgCatIds.toArray(new Integer[0])));
        }
        if (!StringUtils.isBlank(param.keywords)) {
            select.where(UPLOADED_IMAGE.IMG_NAME.like(this.likeValue(param.keywords)));
        }
        if (param.searchNeed != null && param.searchNeed == 1) {
            if (param.needImgWidth != null && param.needImgWidth > 0) {
                select.where(UPLOADED_IMAGE.IMG_WIDTH.eq(param.needImgWidth));
            }
            if (param.needImgHeight != null && param.needImgHeight > 0) {
                select.where(UPLOADED_IMAGE.IMG_HEIGHT.eq(param.needImgHeight));
            }
        }
        SortField<?>[] sortFields = {UPLOADED_IMAGE.CREATE_TIME.desc(), UPLOADED_IMAGE.CREATE_TIME.asc(),
                UPLOADED_IMAGE.IMG_SIZE.desc(), UPLOADED_IMAGE.IMG_SIZE.asc(), UPLOADED_IMAGE.IMG_NAME.desc(),
                UPLOADED_IMAGE.IMG_NAME.asc()};
        if (param.uploadSortId != null && param.uploadSortId >= 0 && param.uploadSortId < sortFields.length) {
            select.orderBy(sortFields[param.uploadSortId]);
        } else {
            select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());
        }
        return select;
    }


    /**
     * 通过原始URL得到图片信息
     *
     * @param imagePathOrUrl
     * @return
     */
    public UploadedImageRecord getImageFromOriginName(String imagePathOrUrl) {
        return db().selectFrom(UPLOADED_IMAGE).where(UPLOADED_IMAGE.IMG_ORIG_FNAME.eq(imagePathOrUrl)).fetchAny();
    }

    /**
     * 通过图片相对路径获取图片信息
     *
     * @param imagePath
     * @return
     */
    public UploadedImageRecord getImageFromImagePath(String imagePath) {
        return db().selectFrom(UPLOADED_IMAGE).where(UPLOADED_IMAGE.IMG_PATH.eq(imagePath)).fetchAny();
    }

    /**
     * 保存图片到数据库
     *
     * @param relativePath
     * @param imageName
     * @param originFileName
     * @param catId
     * @return
     */
    public UploadedImageRecord addImageToDb(String relativePath, String imageName, String originFileName,
                                            Integer catId) {
        String fullPath = fullPath(relativePath);
        File file = new File(fullPath);
        if (file.exists()) {
            BufferedImage imageInfo = getImageInfo(fullPath);
            if (imageInfo == null) {
                return null;
            }
            UploadedImageRecord image = db().newRecord(UPLOADED_IMAGE);
            image.setImgName(imageName == null ? file.getName() : imageName);
            image.setImgPath(relativePath);
            image.setImgType(this.getImageExension(fullPath));
            image.setImgOrigFname(originFileName == null ? file.getName() : originFileName);
            image.setImgSize((int) (file.length()));
            image.setImgUrl(this.imageUrl(relativePath));
            image.setImgWidth(imageInfo.getWidth());
            image.setImgHeight(imageInfo.getHeight());
            image.setImgCatId(catId == null ? 0 : catId);
            image.setShopId(this.getShopId());
            image.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
            image.insert();
            return image;
        }
        return null;
    }

    /**
     * 得到图片信息
     *
     * @param imageId
     * @return
     */
    public UploadedImageRecord getImageById(Integer imageId) {
        return db().fetchAny(UPLOADED_IMAGE, UPLOADED_IMAGE.IMG_ID.eq((imageId)));
    }

    /**
     * 获取总大小（不包括删除）
     *
     * @return
     */
    public Integer getAllSize() {
        Byte noDel = 0;
        Object imageSize = db().select(DSL.sum(UPLOADED_IMAGE.IMG_SIZE))
                .from(UPLOADED_IMAGE)
                .where(UPLOADED_IMAGE.SHOP_ID.eq(this.getShopId()))
                .and(UPLOADED_IMAGE.IMG_WIDTH.gt(0))
                .and(UPLOADED_IMAGE.IMG_HEIGHT.gt(0))
                .and(UPLOADED_IMAGE.DEL_FLAG.eq(noDel))
                .fetchAny(0);
        return Util.getInteger(imageSize);
    }

    /**
     * 添加外链图片到数据库
     *
     * @param imageUrl
     * @return
     */
    public UploadedImageRecord addLocalGoodsImage(String imageUrl) {
        if (getImageFromOriginName(imageUrl) == null) {
            String extension = this.getImageExension(imageUrl);
            String filename = randomFilename();
            UploadPath uploadPath = this.getWritableUploadPath("image", filename, extension,null);
            try {
                FileUtils.copyURLToFile(new URL(imageUrl), new File(uploadPath.fullPath), 30, 30);
                deleteFile(uploadPath.getFullPath());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            UploadedImageRecord record = this.getImageFromImagePath(uploadPath.relativeFilePath);
            if (record == null) {
                record = this.addImageToDb(uploadPath.relativeFilePath, null, imageUrl, 0);
            }
            return record;
        }
        return null;
    }

    /**
     * 当前店铺Id 
     * @return
     */
    public Integer currentShopId() {
    	return this.getShopId();
    }
    
    @Override
	public String imageUrl(String relativePath) {
		return domainConfig.imageUrl(relativePath);
	}

	@Override
	public String fullPath(String relativePath) {
		return domainConfig.mainUrl(relativePath);
	}

	@Override
	public UpYun getUpYunClient() {
		return new UpYun(upYunConfig.getServer(), upYunConfig.getName(), upYunConfig.getPassword());
	}
}
