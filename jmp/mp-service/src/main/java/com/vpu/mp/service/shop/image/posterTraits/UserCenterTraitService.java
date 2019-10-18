package com.vpu.mp.service.shop.image.posterTraits;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.LoadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.UserCenterTraitVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.shop.user.user.UserService;

/**
 * 生成分享图片
 * @author zhaojianqiang
 *
 *         2019年10月17日 下午5:09:19
 */
@Service
public class UserCenterTraitService extends ShopBaseService {
	@Autowired
	private UserService user;
	@Autowired
	public PictorialService pService;

	private final static byte PSTATUS_ZERO = 0;
	private final static byte PSTATUS_ONE = 1;

	public UserCenterTraitVo getUserCenter(Integer userId) {
		UserInfo userInfo = user.getUserInfo(userId);
		PictorialRecord pictorialRecord = pService.getCanUserPictorial(userId, 0, (byte) 99);
		UserCenterTraitVo vo = new UserCenterTraitVo();
		if (pictorialRecord != null && StringUtils.isNotEmpty(pictorialRecord.getPath())) {
			vo.setImage(pictorialRecord.getPath());
			vo.setStatus(PSTATUS_ONE);
			return vo;
		}
		logger().info("读取背景图");
		// 读取背景图片
		BufferedImage backgroundImage = null;
		try {
			
			ClassPathResource resource = new ClassPathResource("image/wxapp/user_background.png");
			logger().info("地址："+resource.getURL());
			logger().info("读："+resource.getInputStream()==null?"空":"不空");
			logger().info(resource.getFile()==null?"空":"不空");
			
			ClassPathResource resource2 = new ClassPathResource("user_background.png");
			logger().info("地址2："+resource2.getURL());
			logger().info("读2："+resource2.getInputStream()==null?"空":"不空");
			logger().info(resource2.getFile()==null?"空":"不空");
			
			ClassPathResource resource3 = new ClassPathResource("admin.versionNew.json");
			logger().info("地址3："+resource3.getURL());
			logger().info("读3："+resource3.getInputStream()==null?"空":"不空");
			logger().info(resource3.getFile()==null?"空":"不空");
			
			
			File loadFile = Util.loadFile("image/wxapp/user_background.png");
			
			if(loadFile==null) {
				vo.setMsg("读取背景图失败");
				vo.setStatus(PSTATUS_ZERO);
				logger().info("读取背景图失败1");

				loadFile = Util.loadFile("user_background.png");
				if(loadFile==null) {
					logger().info("读取背景图失败2");
					return vo;
				}
			}
			
			backgroundImage = ImageIO.read(loadFile);
		} catch (IOException e) {
			vo.setMsg("获取背景图失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		}
		// 重新设置大小
		backgroundImage = ImageUtil.resizeImage(600, 800, backgroundImage);
		logger().info("重新设置大小完成");

		logger().info("获取微信二维码");
		// 获取微信二维码地址
		String mpQrCode = user.qrCode.getMpQrCode(QrCodeTypeEnum.INVITE, "invite_id=" + userId);
		if (StringUtils.isEmpty(mpQrCode)) {
			vo.setMsg("小程序获取二维码失败");
			vo.setStatus(PSTATUS_ZERO);
			return vo;
		}

		// 微信微信二维码
		BufferedImage qrCodeImage = null;
		try {
			qrCodeImage = ImageIO.read(new URL(mpQrCode));
		} catch (Exception e) {
			vo.setMsg("小程序读取二维码失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		}
		logger().info("获取微信头像");
		// 微信头像
		BufferedImage avatarImage = null;
		try {
			avatarImage = ImageIO.read(new URL(userInfo.getUserAvatar()));
		} catch (Exception e) {
			vo.setMsg("小程序获取头像失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		}

		// 把头像盘圆
		avatarImage = ImageUtil.makeRound(avatarImage, 110);

		// 添加字体
		ImageUtil.addFont(backgroundImage, userInfo.getUsername(), new Font(null, Font.BOLD, 30), 180, 100, Color.BLACK);

		String titel1 = "分享给你一个好店铺";
		ImageUtil.addFont(backgroundImage, titel1, new Font(null, Font.BOLD, 22), 180, 145, Color.GRAY);

		String titel2 = "扫一扫上面的二维码，进店选购商品";
		ImageUtil.addFont(backgroundImage, titel2, new Font(null, Font.BOLD, 22), 120, 750, Color.GRAY);

		// 合并头像图片
		ImageUtil.addTwoImage(backgroundImage, avatarImage, 50, 60);

		// 合并二维码图片
		ImageUtil.addTwoImage(backgroundImage, qrCodeImage, 100, 260);
		
		String saveFileName="pictorial_"+userId+"_"+DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE)+".png";
		logger().info("userId: "+userId+"  保存文件名"+saveFileName);
		//文件地址
		String imgDir="upload/"+getShopId()+"/pictorial/userqrcode/";
		String filePath=imgDir+saveFileName;
		logger().info("userId: "+userId+"  保存相对路径"+filePath);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(backgroundImage, "png", os);
		} catch (IOException e) {
			vo.setMsg("图片转换失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		}
		
		byte[] byteArray = os.toByteArray();
		boolean upload = false;
		try {
			upload = saas.sysImage.uploadToUpYunByByte(filePath, byteArray);
		} catch (Exception e) {
			vo.setMsg("图片上传服务器失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		}
		if(upload==false) {
			vo.setMsg("图片上传服务器失败");
			vo.setStatus(PSTATUS_ZERO);
			return vo;
		}
		vo.setImage(user.image.imageUrl(filePath));
		vo.setStatus(PSTATUS_ONE);
		return vo;

	}
}
