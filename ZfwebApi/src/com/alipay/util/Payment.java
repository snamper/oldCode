package com.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
/**
 * ���ƣ�֧������
 * ���ܣ�֧�����ⲿ����ӿڿ���
 * �ӿ����ƣ���׼��ʱ���˽ӿ�
 * �汾��2.0
 * ���ڣ�2008-12-25
 * ���ߣ�֧������˾���۲�����֧���Ŷ�
 * ��ϵ��0571-26888888
 * ��Ȩ��֧������˾
 * */
public class Payment {
	/**
	 * ����url����
	 * ����
	 * @param paygateway
	 * �������
	 * @param service
	 * ǩ������
	 * @param sign_type
	 * �ⲿ������
	 * @param out_trade_no
	 * �������
	 * @param input_charset
	 * ������ID
	 * @param partner
	 * ��ȫУ����
	 * @param key
	 * ��Ʒչʾ��ַ
	 * @param show_url
	 * ��Ʒ����
	 * @param body
	 * ��Ʒ�۸�
	 * @param total_fee
	 * ֧������
	 * @param payment_type
	 * �����˻�
	 * @param seller_email
	 * ��Ʒ����
	 * @param subject
	 * �첽���ص�ַ
	 * @param notify_url
	 * ͬ�����ص�ַ
	 * @param return_url
	 * ֧����ʽ
	 * @param paymethod
	 * Ĭ������
	 * @param defaultbank
	 * @return
	 */
    public static String CreateUrl(String paygateway, String service, String sign_type,
    		String out_trade_no,String input_charset,
    		String partner,String key,String show_url, 
    		String body, String total_fee, String payment_type,
    		String seller_email,String subject ,String notify_url, 
    		String return_url,String paymethod,String defaultbank) {

    	System.out.print(notify_url);
        Map params = new HashMap();
        params.put("service", service);
        params.put("partner", partner);
        params.put("subject", subject);
        params.put("body", body);
        params.put("out_trade_no", out_trade_no);
        params.put("total_fee", total_fee);
        params.put("show_url", show_url);
        params.put("payment_type",payment_type);
        params.put("seller_email", seller_email);
        params.put("return_url", return_url);
        params.put("notify_url", notify_url);
        params.put("_input_charset", input_charset);
        params.put("paymethod", paymethod);
        params.put("defaultbank", defaultbank);

        String prestr = "";

        prestr = prestr + key;
        //System.out.println("prestr=" + prestr);

        String sign = com.alipay.util.Md5Encrypt.md5(getContent(params, key));

        String parameter = "";
        parameter = parameter + paygateway;
        //System.out.println("prestr="  + parameter);
        List keys = new ArrayList(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
          	String value =(String) params.get(keys.get(i));
            if(value == null || value.trim().length() ==0){
            	continue;
            }
            try {
                parameter = parameter + keys.get(i) + "="
                    + URLEncoder.encode(value, input_charset) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

        return sign;

    }
    /**
     * ���ܣ�����ȫУ����Ͳ�������
     * ��������
     * @param params
     * ��ȫУ����
     * @param privateKey
     * */
    private static String getContent(Map params, String privateKey) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}

        return prestr + privateKey;
    }
}
