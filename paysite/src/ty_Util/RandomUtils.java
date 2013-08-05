package ty_Util;

import java.util.Random;

/**

* 随机数、随即字符串工具

* User: leizhimin

* Date: 2008-11-19 9:43:09

*/

public class RandomUtils {

public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

public static final String numberChar = "0123456789";

/**

* 返回一个定长的随机字符串(只包含大小写字母、数字)

*

* @param length 随机字符串长度

* @return 随机字符串

*/

public static String generateString(int length) {

StringBuffer sb = new StringBuffer();

Random random = new Random();

for (int i = 0; i < length; i++) {

sb.append(allChar.charAt(random.nextInt(allChar.length())));

}

return sb.toString();

	}
}
