package com.vpu.mp.service.foundation.util;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 图片的处理
 *
 * @author zhaojianqiang
 *
 *         2019年10月18日 上午9:42:04
 */
@Slf4j
public final class ImageUtil {
	/**
	 * 重新设置图片大小
	 *
	 * @param width
	 * @param height
	 * @param bufferedImages
	 * @return
	 */
	public static BufferedImage resizeImage(int width, int height, BufferedImage bufferedImage) {
		BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		newBufferedImage.getGraphics().drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
				0, null);
		return newBufferedImage;
	}


	/**
	 * 给图片添加文字
	 * @param backgroundImage  背景图片
	 * @param message  文字
	 * @param font 字体
	 * @param x x轴数值
	 * @param y y轴数值
	 * @param color 颜色,默认黑色
	 * @returns
	 */
	public static BufferedImage addFont(BufferedImage backgroundImage,String message,Font font,int x, int y,Color color) {
		Graphics2D graphics = backgroundImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 在背景图片上添加文字
		graphics.setColor(color==null?Color.BLACK:color);
		graphics.setFont(font);
		graphics.drawString(message, x, y);
		graphics.dispose();
		return backgroundImage;
	}

	/**
	 * 背景图上添加前景图
	 * @param backgroundImage 背景图
	 * @param foregroundImage 前景图
	 * @param x x轴数值
	 * @param y y轴数值
	 * @return
	 */
	public static BufferedImage addTwoImage(BufferedImage backgroundImage,BufferedImage foregroundImage,int x, int y) {
		Graphics2D graphics = backgroundImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.drawImage(foregroundImage, x, y, foregroundImage.getWidth(), foregroundImage.getHeight(), null);
		graphics.dispose();
		return backgroundImage;
		
	}


	/**
	 * 图片裁成圆形
	 * @param image  图片
	 * @param diameter 直径
	 * @return
	 */
	public static BufferedImage makeRound(BufferedImage image,Integer diameter) {
		 // 透明底的图片
		BufferedImage formatAvatarImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D createGraphics = formatAvatarImage.createGraphics();
		createGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
		int border = 1;
		// 图片是一个圆型
		Ellipse2D.Double shape = new Ellipse2D.Double(border, border, diameter - border * 2, diameter - border * 2);
		createGraphics.setClip(shape);
		createGraphics.drawImage(image, border, border, diameter - border * 2, diameter - border * 2, null);
		createGraphics.dispose();
		return formatAvatarImage;
	}

	/**
	 * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。
	 * @param imageFile
	 * @return
	 */
	public static boolean isImage(File imageFile) {
		if (!imageFile.exists()) {
			return false;
		}
		Image img = null;
		try {
			img = ImageIO.read(imageFile);
			if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			img = null;
		}
	}
	
	/**
	 * image转base64
	 * @param image
	 * @return
	 */
	public static String toBase64(BufferedImage image) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					return null;
				}
			}
		}
		byte[] byteArray = os.toByteArray();
		Base64Encoder encoder = new Base64Encoder();
		String encode = encoder.encode(byteArray);
		return "data:image/png;base64," + encode;
	}
}
