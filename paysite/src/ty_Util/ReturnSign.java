package ty_Util;

public class ReturnSign {

	public static String sign(String content, String privateKey) {
		if (privateKey == null) {
			return null;
		}
		String signBefore = content + privateKey;
		//System.out.print("signBefore=" + signBefore);
		return Md5Encrypt.md5(signBefore);

	}

	 public static String shift(String str) {
		  int size = str.length();
		  char[] chs = str.toCharArray();
		  for (int i = 0; i < size; i++) {
		   if (chs[i] <= 'Z' && chs[i] >= 'A') {
		    chs[i] = (char) (chs[i] + 32);
		   } else if (chs[i] <= 'z' && chs[i] >= 'a') {
		    chs[i] = (char) (chs[i] - 32);
		   }
		  }
		  return new String(chs);
		 }
}
