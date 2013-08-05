package com.alipay.config;

import java.util.*;
/**
 * 名称：支付宝关键值配置类
 * 功能：将支付时需要的合作者ID和安全校验码文件中，在其他文设置成常变量。也可以保存在容器或者数据库中，提取传给支付宝，这样操作性也更强
 * 类属性：接口公共类
 * 版本：2.0
 * 日期：2008-12-31
 * 作者：支付宝公司销售部技术支持团队
 * 联系：0571-26888888
 * 版权：支付宝公司
 * */
public class AlipayConfig { 
	//partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
	public static String sign_type = "MD5"; 
	public static String CharSet = "GBK"; 

	//天源互联
	public static String partnerID2 = "2088501949241555";       
	public static String key2 = "szwmmq8qsnsr8dpfsncnnm1ravto196j"; 
	public static String sellerEmail2 = "tianyuan95588@163.com"; 
	
	
	//华丹商贸
	public static String partnerID1 = "2088001531023533";        
	public static String key1 = "g6l8oqztpvdqlx9dzeny8zciim5knjo1"; 
	public static String sellerEmail1 = "tjhdsm@yahoo.cn"; 
	
}
