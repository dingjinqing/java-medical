package com.vpu.mp.service.shop.image;

import com.vpu.mp.db.shop.tables.pojos.UploadedImageCategory;
import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;
import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;

import org.apache.commons.dbcp2.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.types.UInteger;;

/**
 * 
 * @author 新国
 *
 */
public class ImageService extends BaseService {

	public ImageCategoryService category;

	@Data
	public static class UploadPath {
		public String relativeFilePath;
		public String relativeDirectory;
		public String fullPath;
		public String fullDirectory;
		public String type;
		public String filname;
		public String extension;
	};

	public int removeRow(UInteger imageId) {
		Byte delFlag = 1;
		return db().update(UPLOADED_IMAGE)
				.set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
				.where(UPLOADED_IMAGE.IMG_ID.eq(imageId))
				.execute();
	}

	public int removeRow(List<UInteger> imageIds) {
		Byte delFlag = 1;
		return db().update(UPLOADED_IMAGE)
				.set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
				.where(UPLOADED_IMAGE.IMG_ID.in(imageIds))
				.execute();
	}

	@Data
	@NoArgsConstructor
	public static class ImageListQueryParam {
		public Integer page;
		public Integer imgCatId;
		public Timestamp startRq;
		public Timestamp endRq;
		public Integer imgWidth;
		public Integer imgHeight;
		public String keywords;
		public Integer searchNeed;
		public Integer needImgWidth;
		public Integer needImgHeight;
		public Integer uploadSortId;
		public String act;
		public String showType;
		public Integer[] cbxImg;
		public Integer[] cbxImg2;
		public Integer setCatId;
		public String opCatName;
		public Integer opCatId;
		public Integer opCatPid;
	};

	public String processPostRequest(ImageListQueryParam param) {
		if (param.act == null) {
			return "";
		}
		String actSetCatId = "set_cat_id";
		String actDel = "del";
		String actRenameCatName = "op_cat_rename";
		String actDelCat = "op_cat_del";
		String actAddCat = "op_cat_add";

		if (actSetCatId.equals(param.act)) {
			Integer[] imageIds = "list".equals(param.showType) ? param.cbxImg : param.cbxImg2;
			db().update(UPLOADED_IMAGE)
					.set(UPLOADED_IMAGE.UPLOAD_TIME, Timestamp.valueOf(LocalDateTime.now()))
					.set(UPLOADED_IMAGE.IMG_CAT_ID, param.setCatId)
					.where(UPLOADED_IMAGE.IMG_ID.in(Util.convertToUIntegers((imageIds))))
					.execute();
			return "分类设置成功";
		}

		if (actDel.equals(param.act)) {
			Integer[] imageIds = "list".equals(param.showType) ? param.cbxImg : param.cbxImg2;
			db().delete(UPLOADED_IMAGE)
					.where(UPLOADED_IMAGE.IMG_ID.in(Util.convertToUIntegers((imageIds))))
					.execute();
			return "删除成功";
		}

		if (actRenameCatName.equals(param.act)) {
			db().update(UPLOADED_IMAGE_CATEGORY)
					.set(UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME, param.opCatName)
					.where(UPLOADED_IMAGE.IMG_CAT_ID.eq(param.opCatId))
					.execute();
			return "修改名称成功";
		}

		if (actDelCat.equals(param.act)) {
			this.category.removeCategory(param.opCatId);
			return "删除分类成功";
		}

		if (actAddCat.equals(param.act)) {
			UploadedImageCategory cat = new UploadedImageCategory();
			cat.setImgCatName(param.opCatName);
			cat.setImgCatParentId(param.opCatPid);
			cat.setShopId(this.shopId);
			this.category.addCategory(cat);
			return "添加分类成功";
		}

		return "";
	}

	public PageResult getPageList(ImageListQueryParam param) {
		SelectWhereStep<Record> select = db()
				.select(UPLOADED_IMAGE.asterisk(),
						UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME)
				.from(UPLOADED_IMAGE)
				.leftJoin(UPLOADED_IMAGE_CATEGORY)
				.on(UPLOADED_IMAGE.IMG_CAT_ID.eq(DSL.cast(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID, Integer.class)));
		select = this.buildOptions(select, param);
		select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());
		return this.getPageResult(select, param.page);
	}

	protected List<Integer> convertIntegerArray(List<UInteger> array) {
		List<Integer> result = new ArrayList<Integer>();
		for (UInteger i : array) {
			result.add(i.intValue());
		}
		return result;
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ImageListQueryParam param) {
		if (param == null) {
			return select;
		}
		Byte noDel = 0;
		select.where(UPLOADED_IMAGE.DEL_FLAG.eq(noDel))
				.and(UPLOADED_IMAGE.IMG_WIDTH.gt(0))
				.and(UPLOADED_IMAGE.IMG_HEIGHT.gt(0));

		if (param.imgCatId != null && param.imgCatId > 0) {
			List<Integer> imgCatIds = convertIntegerArray(category.getChildCategoryIds(param.imgCatId, true, true));
			select.where(UPLOADED_IMAGE.IMG_CAT_ID.in(imgCatIds.toArray(new Integer[0])));
		}

		if (param.startRq != null) {
			select.where(UPLOADED_IMAGE.UPLOAD_TIME.ge(param.startRq));
		}

		if (param.endRq != null) {
			select.where(UPLOADED_IMAGE.UPLOAD_TIME.lt(param.endRq));
		}
		if (param.imgWidth != null && param.imgWidth > 0) {
			select.where(UPLOADED_IMAGE.IMG_WIDTH.eq(param.imgWidth));
		}

		if (param.imgHeight != null && param.imgHeight > 0) {
			select.where(UPLOADED_IMAGE.IMG_HEIGHT.eq(param.imgHeight));
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
		SortField<?>[] sortFields = {
				UPLOADED_IMAGE.UPLOAD_TIME.desc(),
				UPLOADED_IMAGE.UPLOAD_TIME.asc(),
				UPLOADED_IMAGE.IMG_SIZE.desc(),
				UPLOADED_IMAGE.IMG_SIZE.asc(),
				UPLOADED_IMAGE.IMG_NAME.desc(),
				UPLOADED_IMAGE.IMG_NAME.asc()
		};
		if (param.uploadSortId != null && param.uploadSortId >= 0 && param.uploadSortId < sortFields.length) {
			select.orderBy(sortFields[param.uploadSortId]);
		} else {
			select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());
		}

		return select;
	}

	public String[] getUploadSortList() {
		String[] sortList = {
				"按上传时间从晚到早",
				"按上传时间从早到晚",
				"按图片从大到小",
				"按图片从小到大",
				"按图片名降序",
				"按图片名升序",
		};
		return sortList;
	}

	/**
	 * 通过原始URL得到图片信息
	 * 
	 * @param imageUrl
	 * @return
	 */
	public UploadedImageRecord getImageFromOriginName(String imagePathOrUrl) {
		return db().selectFrom(UPLOADED_IMAGE).where(UPLOADED_IMAGE.IMG_ORIG_FNAME.eq(imagePathOrUrl)).limit(1)
				.fetchOne();
	}

	/**
	 * 通过图片相对路径获取图片信息
	 * 
	 * @param imagePath
	 * @return
	 */
	public UploadedImageRecord getImageFromImagePath(String imagePath) {
		return db().selectFrom(UPLOADED_IMAGE).where(UPLOADED_IMAGE.IMG_PATH.eq(imagePath)).limit(1).fetchOne();
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
			image.setImgSize(UInteger.valueOf(file.length()));
			image.setImgUrl(this.imageUrl(relativePath));
			image.setImgWidth(imageInfo.getWidth());
			image.setImgHeight(imageInfo.getHeight());
			image.setImgCatId(catId == null ? 0 : catId);
			image.setShopId(this.getShopId());
			image.setUploadTime(Timestamp.valueOf(LocalDateTime.now()));
			image.insert();
			return image;
		}
		return null;
	}
	
	/**
	 * 得到图片信息
	 * @param imageId
	 * @return
	 */
	public UploadedImageRecord getImageById(Integer imageId) {
		return db().fetchOne(UPLOADED_IMAGE, UPLOADED_IMAGE.IMG_ID.eq(UInteger.valueOf(imageId)));
	}

	/**
	 * 获取总大小（不包括删除）
	 * 
	 * @return
	 */
	public Integer getAllSize() {
		Byte noDel = 0;
		Object imageSize = db().select(DSL.sum(UPLOADED_IMAGE.IMG_SIZE)).from(UPLOADED_IMAGE)
				.where(UPLOADED_IMAGE.SHOP_ID.eq(this.getShopId()))
				.and(UPLOADED_IMAGE.IMG_WIDTH.gt(0))
				.and(UPLOADED_IMAGE.IMG_HEIGHT.gt(0))
				.and(UPLOADED_IMAGE.DEL_FLAG.eq(noDel))
				.fetchOne(0);
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
			UploadPath uploadPath = this.getWritableUploadPath("image", filename, extension);
			try {
				FileUtils.copyURLToFile(new URL(imageUrl), new File(uploadPath.fullPath), 30, 30);
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
	 * 图片URL
	 * 
	 * @param relativePath
	 * @return
	 */
	public String imageUrl(String relativePath) {
		return String.format("http://%s/%s", Util.getProperty("domain.image"), relativePath);
	}

	/**
	 * 本地全路径
	 * 
	 * @param relativePath
	 * @return
	 */
	public String fullPath(String relativePath) {
		return String.format("%s/%s", Util.getProperty("web.static-root-path"), relativePath);
	}

	/**
	 * 得到相对路径
	 * 
	 * @param type
	 * @return
	 */
	public String getRelativePathDirectory(String type) {
		Calendar cal = Calendar.getInstance();
		return String.format("upload/%d/%s/%04d%02d%02d/", this.shopId, type,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
	}

	/**
	 * 得到可写上传路径
	 * 
	 * @param type
	 * @param filename
	 * @param extension
	 * @return
	 */
	public UploadPath getWritableUploadPath(String type, String filename, String extension) {
		UploadPath uploadPath = new UploadPath();
		uploadPath.filname = filename;
		uploadPath.extension = extension;
		uploadPath.relativeDirectory = getRelativePathDirectory(type);
		uploadPath.relativeFilePath = uploadPath.relativeDirectory + filename + "." + extension;
		uploadPath.type = type;
		uploadPath.fullPath = fullPath(uploadPath.relativeFilePath);
		uploadPath.fullDirectory = fullPath(uploadPath.relativeDirectory);
		mkdir(uploadPath.fullDirectory);
		return uploadPath;
	}

	/**
	 * 强制创建目录，支持多目录
	 * 
	 * @param fullPath
	 * @return
	 */
	public boolean mkdir(String fullPath) {
		File dir = new File(fullPath);
		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return true;
	}

	/**
	 * 随机文件名
	 * 
	 * @return
	 */
	public String randomFilename() {
		return RandomStringUtils.randomAlphanumeric(20);
	}

	/**
	 * 得到图片扩展名
	 * 
	 * @param pathOrUrl
	 * @return
	 */
	public String getImageExension(String pathOrUrl) {
		String suffix = null;
		try {
			String contentType = URLConnection.getFileNameMap().getContentTypeFor(pathOrUrl);
			Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType(contentType);
			while (suffix == null && readers.hasNext()) {
				ImageReaderSpi provider = readers.next().getOriginatingProvider();
				if (provider != null) {
					String[] suffixes = provider.getFileSuffixes();
					if (suffixes != null) {
						suffix = suffixes[0];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtils.defaultIfNull(suffix, "");
	}

	@Data
	@AllArgsConstructor
	public static class ImageDim {
		public Integer width;
		public Integer height;
	};

	public ImageDim getImageDim(String path) throws FileNotFoundException, IOException {
		BufferedImage img = ImageIO.read(new FileInputStream(path));
		return new ImageDim(img.getWidth(), img.getHeight());
	}

	public BufferedImage getImageInfo(String path) {
		try {
			return ImageIO.read(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage getRemoteImageInfo(String imageUrl) {
		try {
			return ImageIO.read(new URL(imageUrl).openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean validImageType(String contentType) {
		String[] types = { "image/jpeg", "image/gif", "image/png", "image/bmp" };
		return Arrays.asList(types).contains(contentType);
	}

	public String getImageExtension(String contentType) {
		String[] types = { "image/jpeg", "image/gif", "image/png", "image/bmp" };
		return Arrays.asList(types).contains(contentType) ? contentType.split("/")[1] : "unkown";
	}

	public UploadPath getImageWritableUploadPath(String contentType) {
		return this.getWritableUploadPath("image", randomFilename(), getImageExtension(contentType));
	}

	public String baseFilename(String filename) {
		int p = filename.lastIndexOf('.');
		return p == -1 ? filename : filename.substring(0, p);
	}

	@Data
	public static class CropImageParam {
		public String remoteImgPath;
		public Integer cropWidth;
		public Integer cropHeight;
		public Integer x;
		public Integer y;
		public Integer w;
		public Integer h;
		public Double imgScaleW;
		public Integer imgCatId;
		public Integer remoteImgId;
	};

	public UploadPath makeCrop(CropImageParam param) throws Exception {
		String fullPath = fullPath(param.remoteImgPath);
		String extension = this.getImageExension(fullPath);
		ImageDim dim = this.getImageDim(fullPath);
		if (param.imgScaleW != null) {
			double ratio = dim.width / param.imgScaleW;
			param.x = (int) (ratio * param.x);
			param.y = (int) (ratio * param.y);
			param.w = (int) (ratio * param.w);
			param.h = (int) (ratio * param.h);
		}

		if (Util.getInteger(param.cropWidth) > 0 && Util.getInteger(param.cropHeight) <= 0) {
			param.cropHeight = param.h / param.w * param.cropWidth;
		} else if (Util.getInteger(param.cropHeight) > 0 && Util.getInteger(param.cropWidth) <= 0) {
			param.cropWidth = param.w / param.h * param.cropHeight;
		} else if (Util.getInteger(param.cropHeight) <= 0 && Util.getInteger(param.cropWidth) <= 0) {
			param.cropWidth = param.w;
			param.cropHeight = param.h;
		}

		UploadPath uploadPath = getWritableUploadPath("image", randomFilename(), extension);
		Thumbnails.of(fullPath)
				.sourceRegion(param.x, param.y, param.w, param.h)
				.size(param.cropWidth, param.cropHeight)
				.toFile(uploadPath.fullPath);
		return uploadPath;
	}
}
