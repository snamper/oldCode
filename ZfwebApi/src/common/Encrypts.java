package common;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Encrypts { 
	private static String[] pass = new String[]{
	"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"};

	private static int getn2(String s2){
	  int n = 0;
	  for (int i = 0; i < s2.length(); i++) 
	    n = n + (int)s2.substring(i, i + 1).charAt(0) 
	    	* ((int)s2.substring(i, i + 1).charAt(0) + i + 1);
	  return n;
	}
	
	private static int getn3(int n1, int n2, String s2){
	  int n = ((n1 + n2 + 1) * (n2 + s2.length() + 2) * (n1 + n2 + 3)
	      * Integer.valueOf((getTime("yyMMdd", 0))).intValue()
	      + (s2.length()+1) + (s2.length()+2)) % 62 ;
	  if (n < 0)  n = -n;
	  return n;
	}

	/*
	 * 功能: 加密
	 * 参数: data 加密内容, key 密钥
	 * 返回: 密文
	 */
	public static String encrypt(String data, String key){
	  if (data.equals("")) return "";
	  int n1 = 0;
	  String s1 = data;
	  String s2 = key;
	  //由明文生成n1
	  for (int i = 0; i < s1.length();i++)
	    n1 = n1 +  (int)s1.substring(i, i + 1).charAt(0) 
	    		* (int)s1.substring(i, i + 1).charAt(0) 
	    		* ((int)s1.substring(i, i + 1).charAt(0) + (i + 1));
	  n1 = n1 % 60;
	  
	  //生成n2,n3
	  int n2 = getn2(s2);
	  int n3 = getn3(n1, n2, s2);
	  //生成密文s3
	  String s3 = s1;
	  for (int i = 0; i<s3.length(); i++){
	    int j = pass[0].indexOf(s3.substring(i, i+1));
	    if (j > -1)	
	    	s3 = s3.substring(0, i) 
	    		+ pass[(n1 + n3 + i + 1) % 62].substring(j, j + 1)
	    		+ s3.substring(i + 1);
	  };
	  s3 = pass[(n2 % 62)].substring(n1, n1 + 1) + s3;
	  //返回密文
	  return s3;
	  //
}

	/*
	 * 功能: 解密
	 * 参数: data 解密内容, key 密钥
	 * 返回: 明文
	 */
	public static String uncrypt(String data, String key){
	  //
	  if (data.equals("")) return "";
	  String s2 = key;
	  String s3 = data;
	  String s1 = s3.substring(1);
	  //生成n2
	  int n2 = getn2(s2);
	  //由密文还原n1
	  int n1 = pass[n2 % 62].indexOf((s3.substring(0, 1)));
	  //生成n3
	  int n3 = getn3(n1, n2, s2);
	  //还原明文s1
	  for (int i = 0; i < s1.length(); i++){
	    int j = pass[(n1 + n3 + i + 1) % 62].indexOf(s1.substring(i, i + 1));
	    if (j > -1)	
	    	s1 = s1.substring(0, i) + pass[0].substring(j, j + 1) + s1.substring(i + 1);
	  };
	  return s1;

}
	
	private static String getTime(String sFormat, int mm){
		if (sFormat.equals("")) sFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		String s = sdf.format(grc.getTime());
		//
		if (mm != 0){
			Date dt=sdf.parse(s,new ParsePosition(0));
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MINUTE, mm);//要加一分钟 
			dt = rightNow.getTime();
			s = sdf.format(dt);
		}
		//
		return s;
	}

}
