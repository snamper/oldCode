package ty_Util;

import java.util.Random;

/**

* ��������漴�ַ�������

* User: leizhimin

* Date: 2008-11-19 9:43:09

*/

public class RandomUtils {

public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

public static final String numberChar = "0123456789";

/**

* ����һ������������ַ���(ֻ������Сд��ĸ������)

*

* @param length ����ַ�������

* @return ����ַ���

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
