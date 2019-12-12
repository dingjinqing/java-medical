package com.vpu.mp.service.shop.image;

import com.UpYun;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.config.StorageConfig;
import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.image.ImageDefault;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.RegexUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.image.CropImageParam;
import com.vpu.mp.service.pojo.shop.image.ImageListQueryParam;
import com.vpu.mp.service.pojo.shop.image.UploadImageCatNameVo;
import com.vpu.mp.service.pojo.shop.image.UploadImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;
import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;

/** @author 新国 */
@Service
@Slf4j
public class ImageService extends ShopBaseService implements ImageDefault {




  @Autowired public ImageCategoryService category;

  @Autowired protected DomainConfig domainConfig;

  @Autowired protected UpYunConfig upYunConfig;

  @Autowired protected StorageConfig storageConfig;

  @Override
  public String imageUrl(String relativePath) {
      if(RegexUtil.checkUrl(relativePath)){
          return relativePath;
      }
      return domainConfig.imageUrl(relativePath);
  }

  @Override
  public String fullPath(String relativePath) {
    return storageConfig.storagePath(relativePath);
  }

  @Override
  public UpYun getUpYunClient() {
    return new UpYun(upYunConfig.getServer(), upYunConfig.getName(), upYunConfig.getPassword());
  }

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
    SelectWhereStep<Record> select =
        db().select(UPLOADED_IMAGE.asterisk(), UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME)
            .from(UPLOADED_IMAGE)
            .leftJoin(UPLOADED_IMAGE_CATEGORY)
            .on(
                UPLOADED_IMAGE.IMG_CAT_ID.eq(
                    DSL.cast(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID, Integer.class)));
    select = this.buildOptions(select, param);
    select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());

    return this.getPageResult(select, param.page, param.pageRows, UploadImageCatNameVo.class);
  }

  public List<Integer> convertIntegerArray(List<Integer> array) {
    return new ArrayList<Integer>(array);
  }

  /**
   * 拼接查询条件
   *
   * @param select
   * @param param
   * @return
   */
  public SelectWhereStep<Record> buildOptions(
      SelectWhereStep<Record> select, ImageListQueryParam param) {
    if (param == null) {
      return select;
    }
    Byte noDel = 0;
    select
        .where(UPLOADED_IMAGE.DEL_FLAG.eq(noDel))
        .and(UPLOADED_IMAGE.IMG_WIDTH.gt(0))
        .and(UPLOADED_IMAGE.IMG_HEIGHT.gt(0));

    if (param.imgCatId != null) {
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
    SortField<?>[] sortFields = {
      UPLOADED_IMAGE.CREATE_TIME.desc(),
      UPLOADED_IMAGE.CREATE_TIME.asc(),
      UPLOADED_IMAGE.IMG_SIZE.desc(),
      UPLOADED_IMAGE.IMG_SIZE.asc(),
      UPLOADED_IMAGE.IMG_NAME.desc(),
      UPLOADED_IMAGE.IMG_NAME.asc()
    };
    if (param.uploadSortId != null
        && param.uploadSortId >= 0
        && param.uploadSortId < sortFields.length) {
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
    return db().selectFrom(UPLOADED_IMAGE)
        .where(UPLOADED_IMAGE.IMG_ORIG_FNAME.eq(imagePathOrUrl))
        .fetchAny();
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
   * @return
   */
  public UploadedImageRecord addImageToDb(UploadImageParam param, Part file, UploadPath uploadPath) {
    return addImageToDb(param,file.getSubmittedFileName(),file.getContentType(), (int) file.getSize(),uploadPath);
  }
  /**
   * 保存图片到数据库
   *
   * @return
   */
  public UploadedImageRecord addImageToDb(UploadImageParam param, String submitName,String  contentType,Integer fileSize, UploadPath uploadPath) {
    UploadedImageRecord image = db().newRecord(UPLOADED_IMAGE);
    image.setImgName(baseFilename(submitName));
    image.setImgOrigFname(submitName);
    image.setImgType(contentType);
    image.setImgSize(new Long(fileSize).intValue());
    image.setImgPath(uploadPath.relativeFilePath);
    image.setImgUrl(uploadPath.getImageUrl());
    image.setImgWidth(param.getNeedImgWidth());
    image.setImgHeight(param.getNeedImgHeight());

    image.setImgCatId(param.getImgCatId());
    image.setUserId(param.getUserId());
    image.insert();
    return image;
  }

  public UploadedImageRecord addImageToDb(CropImageParam param, UploadPath uploadPath) {
    UploadedImageRecord image = db().newRecord(UPLOADED_IMAGE);
    image.setImgName(baseFilename(uploadPath.getFilname()));
    image.setImgPath(uploadPath.relativeFilePath);
    image.setImgType(uploadPath.extension);
    image.setImgOrigFname(param.remoteImgPath);
    image.setImgSize(param.getSize());
    image.setImgUrl(uploadPath.getImageUrl());
    image.setImgWidth(param.getCropWidth());
    image.setImgHeight(param.getCropHeight());
    image.setImgCatId(param.getImgCatId());
    image.insert();
    return image;
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
    Object imageSize =
        db().select(DSL.sum(UPLOADED_IMAGE.IMG_SIZE))
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
      UploadPath uploadPath = this.getWritableUploadPath("image", filename, extension, null);
      File file = new File(uploadPath.fullPath);
      try {
        FileUtils.copyURLToFile(new URL(imageUrl), file, 30, 30);
        deleteFile(uploadPath.getFullPath());
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      UploadedImageRecord record = this.getImageFromImagePath(uploadPath.relativeFilePath);
      if (record == null) {
        //                record = this.addImageToDb(uploadPath.relativeFilePath, null, imageUrl,
        // 0);
      }
      return record;
    }
    return null;
  }

  /**
   * 当前店铺Id
   *
   * @return
   */
  @Override
  public Integer currentShopId() {
    return this.getShopId();
  }

  /**
   * 校验添加图片参数
   *
   * @param param 入参
   * @param file
   * @return jsonResultCode, object
   */
  public ResultMessage validImageParam(UploadImageParam param, Part file) throws IOException {
    return validImageParam(param,file.getSize(),file.getContentType(),file.getInputStream());
  }

  /**
   * 校验添加图片参数
   *
   * @param param 入参
   * @param fileSize
   * @return jsonResultCode, object
   */
  public ResultMessage validImageParam(UploadImageParam param, long fileSize, String fileType, InputStream fileStream) {
    Integer maxSize = 5 * 1024 * 1024;
    if (fileSize > maxSize) {
      return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_UPLOAD_GT_5M).build();
    }
    if (!validImageType(fileType)) {
      return ResultMessage.builder()
          .jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID)
          .build();
    }
    BufferedImage bufferImage = null;
    try {
      bufferImage = ImageIO.read(fileStream);
    } catch (Exception e) {
      return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID).build();
    }
    int type = bufferImage.getType();
    if (bufferImage == null || bufferImage.getWidth(null) <= 0 || bufferImage.getHeight(null) <= 0) {
      return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID).build();
    }
    if (param.needImgWidth != null || param.needImgHeight != null) {
      if (param.needImgWidth != null && param.needImgWidth != bufferImage.getWidth()) {
        return ResultMessage.builder()
            .jsonResultCode(JsonResultCode.CODE_IMGAE_UPLOAD_EQ_WIDTH)
            .message(param.needImgWidth)
            .build();
      }
      if (param.needImgHeight != null && param.needImgHeight != bufferImage.getHeight()) {
        return ResultMessage.builder()
            .jsonResultCode(JsonResultCode.CODE_IMGAE_UPLOAD_EQ_HEIGHT)
            .message(param.needImgHeight)
            .build();
      }
    }
    return ResultMessage.builder().flag(true).build();
  }
}
