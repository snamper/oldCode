package com.agents.util;

public class Tools {
	//根据标签ID，获取div标签及内容
	public static String divString(String source, String divid) {
//		source = source.toLowerCase();
		int d = source.indexOf(divid);
		String s1 = source.substring(0,d);
		String s2 = source.substring(d);
		int i = reverse(s1).indexOf("vid<") + 3;
		int start = s1.length() - 1 - i;
		int end = s2.indexOf("</div>") + s1.length() + 6;
		return source.substring(start, end);
	}

	//翻转字符
	public static String reverse(String arg0) {
		char[] reverse_c = new char[arg0.length()];
		for (int i = 0; i < reverse_c.length; i++)
			reverse_c[i] = arg0.charAt(reverse_c.length - i - 1);

		return (new String(reverse_c));
	}

	//根据标签ID，获取div标签及内容,内容 内的属性值
	public static String getAttributeValue(String source, String divid,String attributeName){
		String ds = divString(source,divid);
		int i = ds.indexOf(attributeName);
		String resultVaule = "";
		if(i != -1){
			String afters = ds.substring(i + attributeName.length() + 2);
			resultVaule = afters.substring(0, afters.indexOf("\""));
		}
		return resultVaule;
	}

	public static String getTagAttriValue(String source, String tagName,String attributeName){
		int t = source.indexOf(tagName);
		String temp = source.substring(0,t + tagName.length());
		int a = source.substring(t + tagName.length()).indexOf(attributeName);
		//t大于0是因为标签名字前面还有个“<”
		if(t > 0 && a > -1 && (t + tagName.length()) != source.length()){
			if("<".equals(source.substring(t-1, t)) && " ".equals(source.substring(t + tagName.length(), t + tagName.length()+1))){
				if(" ".equals(source.substring(temp.length() + a - 1, temp.length() + a)) && "=\"".equals(source.substring(temp.length() + a + attributeName.length(), temp.length() + a + attributeName.length()+2))){
					String tm = source.substring(temp.length() + a + attributeName.length()+2);
					return tm.substring(0, tm.indexOf("\""));
				}
			}
		}
		return "";
	}

	public static String getDivHtml(String sHtml, String sId){
		boolean IsBegin = false;
		int nDiv = 0, nBegin = 0, nEnd = 0;
		//找到开头
		String sTemp = fc.replace(sHtml, "id=\"", "id=^");
		int i = 0;
		while (i < sTemp.length()){
			if (sTemp.substring(i, i + 4).equals("<div")){
				String sDiv = fc.getString(sTemp.substring(i), "<div", ">");			//找到一个div
				String sDivId = fc.getString(fc.replace(sDiv, " ", ""), "id=^", "\"");	//得到一个id
				if (sDivId.equals(sId)){
//					System.out.print("begin=" + i);
					IsBegin = true;		//开始计数标志
					nBegin = i;			//开始位置
				}
				if (IsBegin) nDiv++;	//计数加1
			}
			if (sTemp.substring(i, i + 5).equals("</div")){
				if (IsBegin) nDiv--;	//计数减1
			}
			if (IsBegin && nDiv==0) {
				nEnd = i + 6;
//				System.out.print("end=" + nEnd);
				break;
			}
			i++;
		}
		return sHtml.substring(nBegin, nEnd);
		//
	}
	
	public static String correctDate(String date){
		//2011-7-1 修改为2011-07-01
		String result = date;
		if(date != null && !"".equals(date)){
			String[] da = date.split("-");
			if(da.length == 3){
				if(da[1].length() == 1){
					da[1] = "0" + da[1];
				}
				
				if(da[2].length() == 1){
					da[2] = "0" + da[2];
				}
				
				result = da[0] + "-" + da[1] +"-" + da[2];
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		String s = "<img src=\"images/2.jpg\" width=\"338\" height=\"99\" />";
		String a = Tools.getTagAttriValue(s, "imgadf","srcdsf");
		System.out.println(a);
	}

}
