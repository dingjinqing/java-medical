package com.vpu.jmd.service.image;

import com.UpYun;
import com.upyun.UpException;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.config.StorageConfig;
import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.HttpsUtils;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.RegexUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionNumConfig;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.image.CropImageParam;
import com.vpu.mp.service.pojo.shop.image.DownloadImageBo;
import com.vpu.mp.service.pojo.shop.image.ImageListQueryParam;
import com.vpu.mp.service.pojo.shop.image.UploadImageCatNameVo;
import com.vpu.mp.service.pojo.shop.image.UploadImageListVo;
import com.vpu.mp.service.pojo.shop.image.UploadImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.shop.version.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;
import static com.vpu.mp.db.shop.tables.UploadedImageCategory.UPLOADED_IMAGE_CATEGORY;
import static com.vpu.mp.service.pojo.shop.image.ImageConstant.IMG_CAT_ID_MY_IMAGE;
import static com.vpu.mp.service.pojo.shop.image.ImageConstant.IMG_CAT_ID_USER_IMAGE;

/**
 * @author 新国
 */
@Service
@Slf4j
public class ImageService extends ShopBaseService implements ImageDefault {


    @Autowired
    public ImageCategoryService category;

    @Autowired
    protected DomainConfig domainConfig;

    @Autowired
    protected UpYunConfig upYunConfig;

    @Autowired
    protected StorageConfig storageConfig;
    @Autowired
    private VersionService versionService;

    @Override
    public String imageUrl(String relativePath) {
        if (RegexUtil.checkUrl(relativePath)) {
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
    public UploadImageListVo getPageList(ImageListQueryParam param) {
        SelectWhereStep<Record> select =
            db().select(UPLOADED_IMAGE.asterisk(), UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME)
                .from(UPLOADED_IMAGE)
                .leftJoin(UPLOADED_IMAGE_CATEGORY)
                .on(
                    UPLOADED_IMAGE.IMG_CAT_ID.eq(
                        DSL.cast(UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID, Integer.class)));
        select = this.buildOptions(select, param);
        select.orderBy(UPLOADED_IMAGE.IMG_ID.desc());

        PageResult<UploadImageCatNameVo> pageResult = this.getPageResult(select, param.getPage(), param.getPageRows(), UploadImageCatNameVo.class);
        Integer limitSize = versionService.getLimitNum(VersionNumConfig.PICTURENUM);
        BigDecimal totalMaxSize = BigDecimal.valueOf(limitSize).multiply(BigDecimal.valueOf(1024*1024));
        BigDecimal validImageTotalSize = getValidImageTotalSize();
        BigDecimal divide = totalMaxSize.subtract(validImageTotalSize).divide(BigDecimal.valueOf(1024 * 1024));
        UploadImageListVo vo =new UploadImageListVo();
        vo.setDataList(pageResult.getDataList());
        vo.setPage(pageResult.getPage());
        vo.setFreeMemory(divide.setScale(2,BigDecimal.ROUND_HALF_UP));
        return vo;
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
    public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ImageListQueryParam param) {
        if (param == null) {
            return select;
        }
        select.where(UPLOADED_IMAGE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        if (param.getImgCatId().equals(IMG_CAT_ID_MY_IMAGE)) {
            //我的图
            select.where(UPLOADED_IMAGE.IMG_CAT_ID.notEqual(IMG_CAT_ID_USER_IMAGE));
        } else if (param.getImgCatId().equals(IMG_CAT_ID_USER_IMAGE)) {
            //用户上传
            select.where(UPLOADED_IMAGE.IMG_CAT_ID.eq(IMG_CAT_ID_USER_IMAGE));
        } else {
            List<Integer> imgCatIds = convertIntegerArray(category.getChildCategoryIds(param.getImgCatId()));
            select.where(UPLOADED_IMAGE.IMG_CAT_ID.in(imgCatIds.toArray(new Integer[0])));
        }
        if (!StringUtils.isBlank(param.getKeywords())) {
            select.where(UPLOADED_IMAGE.IMG_NAME.like(this.likeValue(param.getKeywords())));
        }
        if (param.getSearchNeed() != null && param.getSearchNeed() == 1) {
            if (param.getNeedImgWidth() != null && param.getNeedImgWidth() > 0) {
                select.where(UPLOADED_IMAGE.IMG_WIDTH.eq(param.getNeedImgWidth()));
            }
            if (param.getNeedImgHeight() != null && param.getNeedImgHeight() > 0) {
                select.where(UPLOADED_IMAGE.IMG_HEIGHT.eq(param.getNeedImgHeight()));
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
        if (param.getUploadSortId() != null && param.getUploadSortId() >= 0 && param.getUploadSortId() < sortFields.length) {
            select.orderBy(sortFields[param.getUploadSortId()]);
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
        return addImageToDb(param, file.getSubmittedFileName(), file.getContentType(), (int) file.getSize(), uploadPath);
    }

    /**
     * 保存图片到数据库
     *
     * @return
     */
    public UploadedImageRecord addImageToDb(UploadImageParam param, String submitName, String contentType, Integer fileSize, UploadPath uploadPath) {
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
     * 批量添加下载的外链图片信息
     * @param downloadImageBos
     * @return
     */
    public List<String> addImageToDbBatch(List<DownloadImageBo> downloadImageBos) {
        List<String> imgRelativePath = new ArrayList<>(downloadImageBos.size());

        List<UploadedImageRecord> records = new ArrayList<>();
        for (DownloadImageBo bo : downloadImageBos) {
            if (Boolean.TRUE.equals(bo.isAlreadyHas())) {
                imgRelativePath.add(bo.getRelativeFilePath());
                continue;
            }

            UploadedImageRecord record = new UploadedImageRecord();
            record.setImgType(bo.getImageType());
            record.setImgSize(bo.getSize());
            record.setImgOrigFname(bo.getImageName());
            // 去掉.jpg
            if (bo.getImageName().lastIndexOf(".") != -1) {
                record.setImgName(bo.getImageName().substring(0,bo.getImageName().lastIndexOf(".")));
            }else{
                record.setImgName(bo.getImageName());
            }

            record.setImgPath(bo.getRelativeFilePath());
            record.setImgUrl(bo.getImgUrl());
            record.setImgCatId(0);
            record.setImgWidth(bo.getWidth());
            record.setImgHeight(bo.getHeight());
            records.add(record);
            imgRelativePath.add(bo.getRelativeFilePath());
        }

        db().batchInsert(records).execute();
        return imgRelativePath;
    }

    /**
     * 根据图片orgName查询图片信息
     * @param imgOrgNames 原名集合
     * @return 图片信息集合
     */
    public List<UploadedImageRecord> getImgsByImgOrgNames(List<String> imgOrgNames) {
        return db().select(UPLOADED_IMAGE.IMG_ORIG_FNAME,UPLOADED_IMAGE.IMG_PATH).from(UPLOADED_IMAGE)
            .where(UPLOADED_IMAGE.IMG_ORIG_FNAME.in(imgOrgNames).and(UPLOADED_IMAGE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UploadedImageRecord.class);
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
        return validImageParam(param, file.getSize(), file.getContentType(), file.getInputStream());
    }

    /**
     * 校验添加图片参数
     *
     * @param param    入参
     * @param fileSize
     * @return jsonResultCode, object
     */
    public ResultMessage validImageParam(UploadImageParam param, long fileSize, String fileType, InputStream fileStream) {
        //大小校验 5*1024*1024
        Integer maxSize = 5 <<20;
        if (fileSize > maxSize) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_UPLOAD_GT_5M).build();
        }
        //图片空间容量校验 只校验admin上传
        if (param.getImgCatId()!=-1){
            //限制大小(单位Mb)
            Integer limitSize = versionService.getLimitNum(VersionNumConfig.PICTURENUM);
            //-1 为不校验空间大小
            if(limitSize!=-1){
                BigDecimal totalMaxSize =BigDecimal.valueOf(limitSize).multiply(BigDecimal.valueOf(1024*1024));
                BigDecimal validImageTotalSize = getValidImageTotalSize();
                if (totalMaxSize.compareTo(validImageTotalSize)<0){
                    log.info("图片总空间{},已使用{}",totalMaxSize,validImageTotalSize);
                    return ResultMessage.builder().jsonResultCode(JsonResultCode.MSG_IMGAE_UPLOAD_ET_TOTAL_SIZE).build();
                }
            }
        }
        //后缀名校验
        if (!validImageType(fileType)) {
            logger().info("图片类型为：" + fileType);
            logger().info("是否为jpg类型图片：" + "image/jpg".equals(fileType));
            return ResultMessage.builder()
                .jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID)
                .build();
        }
        //图片文件校验
        BufferedImage bufferImage = null;
        try {
            bufferImage = ImageIO.read(fileStream);
        } catch (Exception e) {
            logger().info("文件读取失败");
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID).build();
        }
        int type = bufferImage.getType();
        if (bufferImage == null || bufferImage.getWidth(null) <= 0 || bufferImage.getHeight(null) <= 0) {
            logger().info("文件读取为空，" + "宽：" + bufferImage.getWidth(null) + "，高：" + bufferImage.getHeight(null));
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_IMGAE_FORMAT_INVALID).build();
        }
        // 图片的尺寸大小校验
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

    /**
     * 获取有效(未删除)图片的大小
     * @return 0 no null
     */
    private BigDecimal getValidImageTotalSize() {
        Record1<BigDecimal> fetchAny = db().select(DSL.sum(UPLOADED_IMAGE.IMG_SIZE)).from(UPLOADED_IMAGE)
            .where(UPLOADED_IMAGE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchAny();
        if (fetchAny!=null){
         return  fetchAny.component1();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 根据传入的图片相对路径拼接称绝对路径
     *
     * @param imgPath 图片相对路径
     * @return 图片绝对路径
     */
    public String getImgFullUrl(String imgPath) {
        if (StringUtils.isBlank(imgPath)) {
            return null;
        } else {
            return imageUrl(imgPath);
        }
    }

    public String getImageHost() {
        return domainConfig.getImageHost();
    }


    public DownloadImageBo downloadImgAndUpload(String url) throws IOException {
        return downloadImgAndUpload(url,null,null);
    }
    /**
     * 下载外链图片并上传至upYun
     * @param url
     * @throws IOException
     */
    public DownloadImageBo downloadImgAndUpload(String url,Integer targetWidth,Integer targetHeight) throws IOException {
        Integer maxSize = 5 * 1024 * 1024;

        byte[] byteArray = HttpsUtils.getByteArray(url, null);
        int size = byteArray.length;
        if (size > maxSize) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        // 读取的不是合法图片
        if (bufferedImage == null) {
            return null;
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (width == 0 || height == 0) {
            log.debug("下载图片，图片尺寸错误");
            return null;
        }
        inputStream.reset();

        String imgType = getImgType(inputStream);
        if (imgType == null) {
            log.debug("下载图片，无法获取图片类型");
            return null;
        }

        if (targetHeight != null && targetWidth != null) {
            width = targetWidth;
            height = targetHeight;
            bufferedImage = ImageUtil.resizeImage(targetWidth,targetHeight,bufferedImage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,imgType.split("/")[1],bos);
            byteArray = bos.toByteArray();
            inputStream = new ByteArrayInputStream(byteArray);
        }
        inputStream.reset();

        UploadPath uploadPath = getImageWritableUploadPath(imgType);

        // 上传又拍云
        boolean ret = false;
        try {
            ret = uploadToUpYunBySteam(uploadPath.relativeFilePath,inputStream);
        } catch (IOException | UpException e) {
            log.debug("下载图片，上传upYun错误：" + e.getMessage());
            return null;
        }
        String fileName = url.substring(url.lastIndexOf("/")+1);
        DownloadImageBo bo = new DownloadImageBo();
        bo.setImageName(fileName);
        bo.setWidth(width);
        bo.setHeight(height);
        bo.setSize(size);
        bo.setRelativeFilePath(uploadPath.getRelativeFilePath());
        bo.setImgUrl(uploadPath.getImageUrl());
        bo.setImageType(imgType);
        return bo;
    }

    /**
     * 获取图片类型
     * @param inputStream 图片输入流
     * @return 图片类型 image/jpeg等
     */
    private String getImgType(InputStream inputStream) {
        ImageInputStream imageInputStream = null;
        try {
            imageInputStream = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                log.debug("根据输入流获取图片类型错误");
                return null;
            }
            ImageReader next = readers.next();
            String type = next.getFormatName();
            type = type.toLowerCase();
            return "image/" + type;
        } catch (IOException e) {
            log.debug("根据输入流获取图片类型-无法读取输入流");
            e.printStackTrace();
            return null;
        }
    }
}
