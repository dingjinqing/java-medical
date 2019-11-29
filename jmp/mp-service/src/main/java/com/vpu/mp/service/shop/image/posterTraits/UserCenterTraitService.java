package com.vpu.mp.service.shop.image.posterTraits;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 
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
		InputStream loadFile = null;
		try {
			loadFile = Util.loadFile("image/wxapp/user_background.png");
			backgroundImage = ImageIO.read(loadFile);
		} catch (IOException e) {
			vo.setMsg("获取背景图失败");
			vo.setStatus(PSTATUS_ZERO);
			logger().error(e.getMessage(), e);
			return vo;
		} finally {
			if (loadFile != null) {
				try {
					loadFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		ImageUtil.addFont(backgroundImage, userInfo.getUsername(), new Font(null, Font.BOLD, 30), 180, 100,
				Color.BLACK);

		String titel1 = "分享给你一个好店铺";
		ImageUtil.addFont(backgroundImage, titel1, new Font(null, Font.BOLD, 22), 180, 145, Color.GRAY);

		String titel2 = "扫一扫上面的二维码，进店选购商品";
		ImageUtil.addFont(backgroundImage, titel2, new Font(null, Font.BOLD, 22), 120, 750, Color.GRAY);

		// 合并头像图片
		ImageUtil.addTwoImage(backgroundImage, avatarImage, 50, 60);

		// 合并二维码图片
		ImageUtil.addTwoImage(backgroundImage, qrCodeImage, 100, 260);

		String saveFileName = "pictorial_" + userId + "_" + DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE)
				+ ".png";
		logger().info("userId: " + userId + "  保存文件名" + saveFileName);
		// 文件地址
		String imgDir = "upload/" + getShopId() + "/pictorial/userqrcode/";
		String filePath = imgDir + saveFileName;
		logger().info("userId: " + userId + "  保存相对路径" + filePath);
		String base64 = ImageUtil.toBase64(backgroundImage);
		vo.setImage(base64);
		vo.setStatus(PSTATUS_ONE);
		return vo;

	}
}
