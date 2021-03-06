package com.jinrl.powermodule.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetConfigValue {

	private String settingfilepath;
	private static GetConfigValue dbsetting;

	public static GetConfigValue newInstance() {
		if (dbsetting == null) {
			dbsetting = new GetConfigValue();
		} else {
			return dbsetting;
		}
		return dbsetting;
	}

	// 得到属性文件的物理路径
	private String getSettingFilePath() {

		if (settingfilepath != null) {
			return settingfilepath.replace("%20", " ");
		}
		settingfilepath = this.getClass().getResource("/").getPath()
				+ "config.properties";
		settingfilepath = settingfilepath.substring(1);
		// %20代表空格,需要将其替换
		return settingfilepath.replace("%20", " ");

	}

	//GetConfigValue.newInstance().filePosition();

	/**
	 * @author rfy
	 */
	public String serviceIPPort() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(this.getSettingFilePath()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("属性文件不存在：" + this.getSettingFilePath());
		} catch (IOException e) {
			throw new RuntimeException("读取属性文件时出错:" + this.getSettingFilePath());
		}
		return p.getProperty("ServerIpAndPort");
	}

}
