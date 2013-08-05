package ty_Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment {

	public static  String CreateURL(String paygateway,String MerchantID,String MerOrderNo,String CardType,String CradNo,String CardPassword,String NoticeURL,String key){

		String params=MerchantID+MerOrderNo+CardType+CradNo+CardPassword+NoticeURL+key;

		String sign =ty_Util.Md5Encrypt.md5(params);

		return sign;
	}
}
