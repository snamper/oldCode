package accept;

/*
 * 卡密长度及类型检则
 */
public class AllotCardInfoTest {
	
	//检查卡密是否合法
	public static String Test(String sCardType, String sCardNo, String sPassword){
		try{
			
			String card[][] = {
	//				{"01", "17", "18", "全国移动"},
	//				{"01", "16", "17", "江苏移动"},
	//				{"01", "10", "8", "浙江移动"},
	//				{"01", "16", "21", "辽宁移动"},
	//				{"01", "16", "17", "福建移动"},
	//				{"01", "18", "16", "地方移动"},
					{"02", "15", "8", "盛大"},
					{"03", "16", "16", "骏网"},
					{"04", "15", "19", "联通"},
					{"05", "9", "12", "QQ"},
					{"06", "16", "8", "征途"},
					{"07", "13", "10", "久游"},
					{"08", "10", "15", "完美"},
					{"09", "13", "9", "网易"},
					{"11", "20", "12", "搜狐"},
					{"12", "19", "18", "电信"},
				};
			
			//根据卡密长度,匹配商品代码		
			String isOk = "false";
			int nCardLen = sCardNo.length();
			int nPasswordLen = sPassword.length();
			int i = 0, j = 0, k = -1;
			for (; i < card.length; i++){
				if (card[i][0].equals(sCardType)){
					k = 0;
					j = i;
				}
				if (card[i][1].equals(nCardLen + "") && (card[i][2].equals(nPasswordLen + ""))){
					isOk = "true";
					break;
				}
			}
			
			//
	//		if (j == 0) return "OK,未知卡类型";
			if (k == -1) return "OK,未知卡类型";
			//
			if (i == card.length) i = j;
			String sAlias = card[i][3] + "(" + card[i][1] + "," + card[i][2] + ")";
			//
			if (isOk.equals("true")){		//如果卡密长度符合规则
				if(card[i][0].equals(sCardType)){	
					return "OK," + sAlias;					//如果类型相同,返回ok
				}else{
					return "ERROR,商品代码与卡密类型不符";		//如果类型不符,返回type_error
				}
			}else
				return "ERROR,卡密位数有误," + sAlias;		//卡密有错
		}catch(Exception e){
			e.printStackTrace();
			return "OK,检查卡时异常";
		}
	}

}
