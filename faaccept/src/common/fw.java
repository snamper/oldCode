package common;

/*
 * 与request有关的函数
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class fw {
	static String m_LastPageButton = "";
	
	
	public static void smg(String s){
		System.out.print(s);
	}
	/*
	 * 检测验证码
	 */
	public static boolean checkCode(HttpServletRequest request, String checkCode)
			throws ServletException, IOException {
		try {
			String s = "";
			HttpSession session = request.getSession(true);
			Error.outs("session=" + session.toString());
			if (session.getAttribute("checkcode") != null){
				s = session.getAttribute("checkcode").toString();
				Error.outs("比较验证码:s=" + s + ", checkCode=" + checkCode);
				return (s.equals(checkCode));
			}else
				return false;
		} catch (Exception e) {
			Error.oute(e);
			return false;
		}
	}

	/*
	 * 清除验证码
	 */
	public static boolean clearCheckCode(HttpServletRequest request)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("checkcode", "");
			return true;
		} catch (Exception e) {
			Error.outs(e.toString());
			return false;
		}
	}

	/*
	 * 注册权限
	 */
	public static void logonPopedom(HttpServletRequest request,
			HttpServletResponse response, String name) throws ServletException,
			IOException {
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute(session.getId(), name);
			session.setMaxInactiveInterval(-1);
			Error.outs("[logon popedom]" + session.getId() + "," + name);
		} catch (Exception e) {
			Error.outs(e.toString());
		}
	}

	/*
	 * 注销权限
	 */
	public static void logoutPopedom(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			Object name = session.getAttribute(session.getId());
			session.removeAttribute(session.getId());
			Error.outs("[logout popedom]" + session.getId() + "," + name);
		} catch (Exception e) {
			Error.outs(e.toString());
		}
	}

	/*
	 * 返回当前用户
	 */
	public static String getLogonName(HttpServletRequest request)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			Object name = session.getAttribute(session.getId());
			if (name == null) 
				return "";
			else
				return name.toString();
		} catch (Exception e) {
			Error.outs(e.toString());
			return "";
		}
	}


	/*
	 * 返回验证码 在Servlet中调用 funcejb.getCheckCode(request, response); 
	 * 在网页中调用Server：<img src="/cardweb/CheckCode" width="100" height="30" align="middle">
	 */

	public static void getCheckCode(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		getCheckCode(request, response, 60, 20, 16, 12, "1234567890");
	}

 
	public static void getCheckCode(HttpServletRequest request,
			HttpServletResponse response, int nWidth, int nHeight, int nSize, int nInterval, String sCodeStr) throws ServletException,
			java.io.IOException {
		Error.outs("[PayAdmin.webserver.getCheckCode]session=" + request.getSession().toString());
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 设置图片的长宽
		int width = nWidth; //70;
		int height = nHeight; //26;
		// 设定被随机选取的中文字
		String base = sCodeStr; //"23456789qwertyupkjhgfdsazxcvbnm";
		// 备选随机汉字的个数
		int length = base.length();
		// 创建缓存图像 
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图像
		Graphics g = image.getGraphics();
		// 创建随机函数的实例
		Random random = new Random();// 此处 设定图像背景色
		g.setColor(getRandColor(random, 188, 235));
		g.fillRect(0, 0, width, height);
		// 设置随机 备选的字体类型
//		String[] fontTypes = { "隶书", "华文新魏", "黑体", "幼圆", "楷体_GB2312", "新宋体" };
		String[] fontTypes = { "Times New Roman" };
		int fontTypesLength = fontTypes.length;
		//画边框。
		g.setColor(Color.BLACK); 
		g.drawRect(0, 0, width - 1, height - 1);
		// 在图片背景上增加噪点，增加图片分析难度
		g.setColor(getRandColor(random, 180, 199));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
//		for (int i = 0; i < 4; i++) {
//			g.drawString("@$@&@#@%@$@@$@&@#@%@$@", 0, 5 * (i + 2));
//		}
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.GRAY);
		for (int i = 0; i < 16; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(32);
			int yl = random.nextInt(32);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的验证码 (4 个汉字 )
		// 保存生成的汉字字符串
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			int start = random.nextInt(length);
			String rand = base.substring(start, start + 1);
			sRand += rand;
			// 设置图片上字体的颜色
			g.setColor(getRandColor(random, 10, 150));
			// 设置字体格式
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
					Font.BOLD, nSize + random.nextInt(1)));	//字符的大小 20
			// 将此汉字画到验证图片上面
			g.drawString(rand, nInterval * i + 5 + random.nextInt(1), 16);   //字符的间隔 14
		}// 将验证码存入S ession中
		HttpSession session = request.getSession();
		session.setAttribute("checkcode", sRand);
		g.dispose();
		// 将 图象输出到JSP页面中
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
		}
		;
		// 关闭流
	}

	/*
	 * 返回随机颜色
	 */
	private static Color getRandColor(Random random, int i, int j) {
		if (i > 255)
			i = 255;
		if (j > 255)
			j = 255;
		int r = i + random.nextInt(j - i);
		int g = i + random.nextInt(j - i);
		int b = i + random.nextInt(j - i);
		return new Color(r, g, b);

	}

}
