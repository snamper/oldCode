package ty_Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class yanzheng {
	/**
	 * @param str
	 * @return
	 */
	public static boolean hasElse(String str) {// 这是验证商户字符串中是否存在非法字符的方�?
		if (str != null && str != "") {
			char c[] = str.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (!((c[i] >= 48 && c[i] <= 57) || (c[i] >= 65 && c[i] <= 97) || (c[i] >= 90 && c[i] <= 122))) {
					int jc = 0;
					for (int j = 0; j <= i; j++) {
						byte[] bytes = ("" + c[i]).getBytes();
						boolean isgb2312 = false;
						if (bytes.length == 2) {
							int[] ints = new int[2];
							ints[0] = bytes[0] & 0xff;
							ints[1] = bytes[1] & 0xff;
								if (ints[0] >= 0x81 && ints[0] <= 0xfe
										&& ints[1] >= 0x40 && ints[1] <= 0xfe) {
									isgb2312 = true;
									jc = jc + 1;
								}
						} else {
							return false;
						}
					}
					//return false;
				}
			}
			return true;
		} else {
			return true;

		}
	}

	public static boolean isgb2312(String str)// 这是验证商户字符串中是否是中�?
	{
		char[] chars = str.toCharArray();
		boolean isgb2312 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xfe && ints[1] >= 0x40
						&& ints[1] <= 0xfe) {
					isgb2312 = true;
					// break;
				}
			} else {
				isgb2312 = false;
				break;
			}
		}
		return isgb2312;
	}

}
