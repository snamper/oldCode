package com.dhtx.sign.servlet.app;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 类名称：WaterMark   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-6 下午03:45:12   
 * @version 1.0
 *  
 */
public class WaterMark {
	
	/**
	 * 
	 * 方法名称: createSignPic 
	 * 方法描述: 合并图片
	 * 创建人: renfy
	 * 创建时间: 2012-1-12 下午12:02:07
	 * @param signName 
	 * @param bgPicName 背景图片
	 * @param outPicName 结果图片
	 * @version 1.0
	 *
	 */
	public static void createSignPic(String signName,String bgPicName,String outPicName){
		try {
			InputStream imagein = new FileInputStream(bgPicName);
			InputStream imagein2 = new FileInputStream(signName);
			BufferedImage image = ImageIO.read(imagein);
			BufferedImage image2 = ImageIO.read(imagein2);
			Graphics g = image.getGraphics();
			g.drawImage(image2, (image.getWidth() - image2.getWidth()) / 2, (image.getHeight() - image2.getHeight()) / 2, null);
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outPicName));
			JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(out);
			enc.encode(image);
			imagein.close();
			imagein2.close();
			out.close();
			System.out.println("合体OK!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
