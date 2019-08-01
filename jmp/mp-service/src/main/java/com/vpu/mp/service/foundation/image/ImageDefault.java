package com.vpu.mp.service.foundation.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jooq.tools.StringUtils;

import com.UpYun;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.CropImageParam;
import com.vpu.mp.service.pojo.shop.image.ImageDim;
import com.vpu.mp.service.pojo.shop.image.UploadPath;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片公用接口，实现了默认方法
 * 
 * @author lixinguo
 *
 */
public interface ImageDefault {

	/**
	 * 删除文件
	 *
	 * @param path
	 */
	public default boolean deleteFile(String path) {
		return FileUtils.deleteQuietly(new File(path));
	}

	/**
	 * 图片URL
	 *
	 * @param relativePath
	 * @return
	 */
	public  String imageUrl(String relativePath);

	/**
	 * 本地全路径
	 *
	 * @param relativePath
	 * @return
	 */
	public  String fullPath(String relativePath);

	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public default Integer currentShopId() {
		return 0;
	}

	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public default Integer getShopId() {
		return 0;
	}

	/**
	 * 得到相对路径
	 *
	 * @param type
	 * @return
	 */
	public default String getRelativePathDirectory(String type, Integer sysId) {
		Calendar cal = Calendar.getInstance();
		if (sysId != null && !sysId.equals(0)) {
			return String.format("upload/%d/%s/%04d%02d%02d/", sysId, type, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
		}
		return String.format("upload/%d/%s/%04d%02d%02d/", this.getShopId(), type, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
	}

	/**
	 * 得到可写上传路径
	 *
	 * @param type
	 * @param filename
	 * @param extension
	 * @return
	 */
	public default UploadPath getWritableUploadPath(String type, String filename, String extension, Integer sysId) {
		UploadPath uploadPath = new UploadPath();
		uploadPath.filname = filename;
		uploadPath.extension = extension;
		uploadPath.relativeDirectory = getRelativePathDirectory(type, sysId);
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
	public default boolean mkdir(String fullPath) {
		File dir = new File(fullPath);
		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return true;
	}

	/**
	 * 删除文件
	 *
	 * @param fullPath
	 * @return
	 */
	public default boolean rmFile(String fullPath) {
		File file = new File(fullPath);
		if (!file.exists()) {
			return file.delete();
		}
		return true;
	}

	/**
	 * 得到又拍云客户端
	 *
	 * @return
	 */
	public  UpYun getUpYunClient();

	/**
	 * 上传到又拍云
	 *
	 * @param upYunPath 又拍云路径
	 * @param localFile 本地文件
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public default boolean uploadToUpYun(String upYunPath, File localFile) throws IOException, Exception {
		return this.getUpYunClient().writeFile(upYunPath, localFile);
	}

	/**
	 * 随机文件名
	 *
	 * @return
	 */
	public default String randomFilename() {
		return RandomStringUtils.randomAlphanumeric(20);
	}

	/**
	 * 得到图片扩展名
	 *
	 * @param pathOrUrl
	 * @return
	 */
	public default String getImageExension(String pathOrUrl) {
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

	public default ImageDim getImageDim(String path) throws FileNotFoundException, IOException {
		BufferedImage img = ImageIO.read(new FileInputStream(path));
		return new ImageDim(img.getWidth(), img.getHeight());
	}

	public default BufferedImage getImageInfo(String path) {
		try {
			return ImageIO.read(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public default BufferedImage getRemoteImageInfo(String imageUrl) {
		try {
			return ImageIO.read(new URL(imageUrl).openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public default boolean validImageType(String contentType) {
		String[] types = { "image/jpeg", "image/gif", "image/png", "image/bmp" };
		return Arrays.asList(types).contains(contentType);
	}

	public default String getImageExtension(String contentType) {
		String[] types = { "image/jpeg", "image/gif", "image/png", "image/bmp" };
		return Arrays.asList(types).contains(contentType) ? contentType.split("/")[1] : "unkown";
	}

	public default UploadPath getImageWritableUploadPath(String contentType) {
		return this.getWritableUploadPath("image", randomFilename(), getImageExtension(contentType), null);
	}

	public default String baseFilename(String filename) {
		int p = filename.lastIndexOf('.');
		return p == -1 ? filename : filename.substring(0, p);
	}

	public default UploadPath makeCrop(CropImageParam param) throws Exception {
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
		UploadPath uploadPath = getWritableUploadPath("image", randomFilename(), extension, null);
		Thumbnails.of(fullPath).sourceRegion(param.x, param.y, param.w, param.h).size(param.cropWidth, param.cropHeight)
				.toFile(uploadPath.fullPath);
		return uploadPath;
	}
	
	public default boolean uploadToUpYunBySteam(String upYunPath, InputStream inStream) throws IOException, Exception {
		return this.getUpYunClient().writeFile(upYunPath, inStream, true, null);
	}
}
