package com.dhtx.sign.servlet.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.Tools;

/**
 * 类名称：CreateSign   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-5 下午05:31:12   
 * @version 1.0
 *  
 */
public class CreateSign {


	public boolean doSign(Order order) {
	try {
			
		
		if(order == null){
			return false;
		}
		
		//
		String orderID = order.getFid();
		String name = order.getfSignName();
		String charTypeName = order.getfCharTypeName();
		String picName = order.getFpictureName();
		String picURL = order.getFpictureURL();
		
		
		if(name == null ||  "".equals(name)){
			System.out.println("生成个性签名，名字不能为空");
			return false;
		}
		if(charTypeName == null ||  "".equals(charTypeName)){
			System.out.println("生成个性签名，字体不能为空");
			return false;
		}
		
		int width =300;
		int height =135;
	
		// 创建BufferedImage对象
		BufferedImage image =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// 获取Graphics2D
		Graphics2D g2d = image.createGraphics();

		// ----------   增加下面的代码使得背景透明   -----------------
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		// ----------   背景透明代码结束   -----------------

		// 画图
		g2d.setFont(new Font(charTypeName,Font.BOLD, 80));
		g2d.setColor(new Color(0,0,0));
		g2d.setStroke(new BasicStroke(1f));
		g2d.drawString(name, 5, 90);
		

		
		//释放对象
		g2d.dispose();
		
		try {
			Tools.checkFileExists(picURL);
			String fileName = picURL + "/" + picName + ".png";
			// 保存文件    
			ImageIO.write(image, "png", new File(fileName)); 
			//修改订单状态
			new OrderDAO().appUpdateState(orderID);
			System.out.println("~~~~CREATE SIGN OK!~~~~~"+fileName);
		} catch (Exception e) {
			System.out.println("生成png时错误(" + e.getMessage() + ")");
		}
		
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
