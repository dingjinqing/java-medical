package com.vpu.mp.service.shop.image;

import static com.vpu.mp.db.shop.tables.Code.CODE;
import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.CodeRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.qrcode.QrCodeGenerator;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 小程序码
 *
 * @author 郑保乐
 */
@Service
@Slf4j
public class QrCodeService extends ShopBaseService {

    private final ImageService imageService;

    public QrCodeService(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     *  获取小程序分享码，无url参数
     * @param typeEnum 活动类型
     * @return null 无法获取，否则对应二维码地址
     */
    public String getMpQrCode(QrCodeTypeEnum typeEnum) {
        return getMpQrCode(typeEnum, "");
    }

    /**
     *  获取小程序分享码，无url参数
     * @param typeEnum 活动类型
     * @param  paramStr url参数
     * @return null 无法获取，否则对应二维码地址
     */
    public String getMpQrCode(QrCodeTypeEnum typeEnum, String paramStr) {
        String typeUrl = StringUtils.isBlank(paramStr) ? typeEnum.getUrl() : typeEnum.getUrl() + "?" + paramStr;
        String paramId = Util.md5(typeUrl);

        return getMpQrCode(typeEnum.getUrl(),typeEnum.getType(),paramStr,paramId);
    }

    /**
     * 获取小程序码
     * @param typeUrl 带参数的小程序页面url
     * @param type  类型id
     * @param paramId 记录的唯一值，由typeUrl加密后产生
     * @return 小程序码图片url，null表示无法获取相应二维码
     */
    private String getMpQrCode(String typeUrl, Short type, String paramStr,String paramId) {
        String relativePath = db().select(CODE.QRCODE_IMG).from(CODE)
            .where(CODE.PARAM_ID.eq(paramId)).and(CODE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetchAny(CODE.QRCODE_IMG);
        log.debug("get img url from db:{}", relativePath);

        //数据库存在该图片路劲
        if (!StringUtils.isBlank(relativePath)) {
            String fullPath=this.imageService.imageUrl(relativePath);

            try {
                //判断upYun上是否有该图片
                Map<String, String> fileInfo = this.imageService.getUpYunClient().getFileInfo(relativePath);
                if (fileInfo != null) {
                    //有图片则直接返回图片全路径
                    return fullPath;
                }
            } catch (IOException | UpException e) {
                //如果失败则认为图片不存在
                logger().warn("upYun 获取图片信息失败："+e.getMessage());
            }
            //upYun不存在则将该记录设置为删除状态
            db().update(CODE).set(CODE.DEL_FLAG,DelFlag.DISABLE.getCode())
                .where(CODE.PARAM_ID.eq(paramId)).execute();
        }

        //获取小程序分享码
        Integer shopId = getShopId();
        MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(shopId);
        String appId =  mp.getAppId();

        //二维码图片大小
        int qrcodWidth = 430;

        try {
            log.debug("调取微信接口，尝试请求二维码图片");
            byte[] qrcodeBytes = open().getWxOpenComponentService().getWxMaServiceByAppid(appId)
                .getQrcodeService().createWxaCodeUnlimitBytes(paramStr,typeUrl, qrcodWidth,true,null,true);
            log.debug("调取微信二维码接口，图片字节长度：{}",qrcodeBytes==null? 0 : qrcodeBytes.length);

            relativePath = format("upload/%s/qrcode/%s/T%sP%s_%s.jpg", type, getShopId(), type, paramId,
                new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));

            this.imageService.getUpYunClient().writeFile(relativePath, qrcodeBytes, true);
            log.debug("通过UpYun将二进制写入磁盘，磁盘路径{}",relativePath);
        } catch (WxErrorException e) {
            logger().warn("获取小程序分享码错误：" + e.getMessage());
            return null;
        } catch (Exception e) {
            logger().warn("upYun上传文件错误：" + e.getMessage());
            return null;
        }

        CodeRecord codeRecord = db().newRecord(CODE);
        codeRecord.setType(type);
        codeRecord.setParamId(paramId);
        codeRecord.setTypeUrl(typeUrl);
        codeRecord.setQrcodeImg(relativePath);
        codeRecord.insert();

        return this.imageService.imageUrl(relativePath);
    }
    
    
    /**
             * 生成用户会员卡二维码图
     * @return 二维码地址
     */
    public String getUserCardQrCode(String cardNo,MemberCardRecord card) {
		
		// 	获取底图背景
		BufferedImage bgImg = getBgImg(600, 800);
    	if(isNull(bgImg)) {
    		return null;
    	}
    	//	获取会员卡头像
    	BufferedImage cardAvatar = getCardVatar();
    	if(isNull(cardAvatar)) {
    		return null;
    	}
    	// 	获取会员卡二维码
    	BufferedImage cardQrCode = getCardNoQrCode(cardNo);
    	if(isNull(cardQrCode)) {
    		return null;
    	}
    	
    	// 	获取会员卡条形码
    	BufferedImage cardBarCode = getCardNoBarCode(cardNo);
    	if(isNull(cardBarCode)) {
    		return null;
    	}
    	
    	//	设置构图
    	setCardQrCode(bgImg, cardQrCode);
    	setCardBgType(bgImg,card);
    	setCardName(bgImg,card.getCardName());
    	setCardDisCount(bgImg,card.getDiscount());
    	setCardNo(cardNo, bgImg);
    	setCardAvatar(bgImg, cardAvatar);
    	setCardBarCode(bgImg, cardBarCode);
    	
    	String filePath = createCardQrCodeFileName(cardNo);
    	
    	//	转换图片成流
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	
    	try {
			ImageIO.write(bgImg, "png", outputStream);
		} catch (IOException e) {
			logger().info("图片转换成字节流失败");
			e.printStackTrace();
			return null;
		}
    	
    	// 上传到又拍云
		byte[] byteArray = outputStream.toByteArray();
    	boolean upload = false;
    	
    	try {
			upload = saas.sysImage.uploadToUpYunByByte(filePath, byteArray);
		} catch (Exception e) {
			logger().info("图片上传服务器失败");
			e.printStackTrace();
			return null;
		}
	
    	if(upload) {
    		logger().info("图片上传服务器成功");
    	}else {
    		logger().info("图片上传服务器失败");
    	}
    	return imageService.imageUrl(filePath);
    }

    /**
     *	会员卡积分
     */
	private void setCardDisCount(BufferedImage bgImg, BigDecimal discount) {
    	String dis = discount.toString()+" 折";
    	ImageUtil.addFont(bgImg, dis, new Font(null, Font.BOLD, 22), 500, 180,
				Color.WHITE);
	}

	private String createCardQrCodeFileName(String cardNo) {
		String filePath = "";
		//	名称
    	String saveFileName = String.format("pictorial_%s_%s.png", cardNo, DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE));
    	logger().info(saveFileName);
    	//	文件地址
    	String imgDir = "upload/"+getShopId()+"/pictorial/ucardqrcode/";
    	filePath = imgDir+saveFileName;
    	logger().info(filePath);
		return filePath;
	}

	private void setCardBarCode(BufferedImage bgImg, BufferedImage cardBarCode) {
		ImageUtil.addTwoImage(bgImg, cardBarCode,15, 620);
	}

	private void setCardAvatar(BufferedImage bgImg, BufferedImage cardAvatar) {
		ImageUtil.addTwoImage(bgImg, cardAvatar, 20, 35);
	}

	private void setCardNo(String cardNo, BufferedImage bgImg) {
		ImageUtil.addFont(bgImg, cardNo, new Font(null, Font.ITALIC, 22), 190, 750, Color.GRAY);
	}

    /**
     * 	设置会员卡名称
     */
	private void setCardName(BufferedImage bgImg,String cardName) {
		Integer posx=150,posy=70;
    	ImageUtil.addFont(bgImg, cardName, new Font(null, Font.BOLD, 30), posx, posy,
				Color.BLACK);
	}
    
    /**
     *	 设置会员卡号二维码
     */
	private void setCardQrCode(BufferedImage bgImg, BufferedImage cardQrCode) {
		Integer posx = 65,posy=200;
    	ImageUtil.addTwoImage(bgImg, cardQrCode, posx, posy);
	}

	/**
	 * 	设置会员卡背景
	 */
	private void setCardBgType(BufferedImage bgImg,MemberCardRecord card) {
		// add color
    	Byte bgType = card.getBgType();
    	if(isNull(bgType)) {
    		return;
    	}
    	Integer bgWidth = 600,bgHeight = 208;
    	if(CardUtil.isBgColorType(bgType)) {
    		logger().info("设置会员卡颜色");
	    	Color color = Color.decode(card.getBgColor());
	    	Graphics2D graph = bgImg.createGraphics();
	    	graph.setPaint(color);
	    	graph.fillRect(0, 0, bgWidth, bgHeight);
    	}else if(CardUtil.isBgImgType(bgType)){
    		// 背景图片
    		logger().info("设置会员卡背景图片");
        	String bgImgUrl = imageService.imageUrl(card.getBgImg());
    		BufferedImage bgImgTwo = null;
        	try {
        		bgImgTwo = ImageIO.read(new URL(bgImgUrl));
    		} catch (Exception e) {
    			logger().info("背景图片解析失败: "+bgImgUrl);
    			e.printStackTrace();
    			return;
    		}
        	bgImgTwo  = ImageUtil.resizeImage(bgWidth, bgHeight, bgImgTwo);
        	ImageUtil.addTwoImage(bgImg, bgImgTwo, 0, 0);
    	}
	}
   
    /**
             * 生成会员卡号条形码
     */
	private BufferedImage getCardNoBarCode(String cardNo) {
		BufferedImage cardBarCode = null;
		Integer width = 560;
		Integer height = 100;
    	try {
			byte[] barCodeByte = QrCodeGenerator.generateBarCodeImg(cardNo, width, height);
			ByteArrayInputStream bais = new ByteArrayInputStream(barCodeByte);
			cardBarCode = ImageIO.read(bais);
		} catch (Exception e) {
			logger().info("会员卡号生成条形码失败");
			e.printStackTrace();
			return null;
		}
		return cardBarCode;
	}
	
    /**
             * 获取会员卡号二维码
     */
	private BufferedImage getCardNoQrCode(String cardNo) {
		BufferedImage cardQrCode = null;
		Integer size = 450;
    	try {
			byte[] qrCodeByte = QrCodeGenerator.generateQRCodeImg(cardNo, size, size);
			ByteArrayInputStream bais = new ByteArrayInputStream(qrCodeByte);
			cardQrCode = ImageIO.read(bais);
		} catch (Exception e) {
			logger().info("用户会员卡卡二维码生成失败");
			e.printStackTrace();
			return null;
		}
		return cardQrCode;
	}
	
    /**
            * 获取 会员卡头像
     */
	private BufferedImage getCardVatar() {
		String cardAvatarAddress = imageService.imageUrl(saas().shop.getShopAvatarById(this.getShopId()));
		
    	if(StringUtils.isBlank(cardAvatarAddress)) {
    		return null;
    	}
    	
    	BufferedImage cardAvatar = null;
    	try {
    		cardAvatar = ImageIO.read(new URL(cardAvatarAddress));
		} catch (Exception e) {
			logger().info("获取会员卡头像失败: "+ cardAvatarAddress);
			return null;
		}
    	
    	cardAvatar = ImageUtil.makeRound(cardAvatar, 110);
		return cardAvatar;
	}

    /**
             * 获取底层图片
     */
	private BufferedImage getBgImg(Integer width,Integer height) {
		// 背景图片
    	BufferedImage bgImg = null;
    	InputStream loadFile = null;
    	String userBgPath = "image/wxapp/user_background.png";
    	try {
    		loadFile = Util.loadFile(userBgPath);
			bgImg = ImageIO.read(loadFile);
		} catch (IOException e) {
			logger().info("获取背景图片失败");
			e.printStackTrace();
			return null;
		}finally {
			if(loadFile != null) {
				try {
					loadFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	bgImg = ImageUtil.resizeImage(width, height, bgImg);
		return bgImg;
	}
    
    
	private boolean isNull(Object obj) {
		return obj == null;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
