package com.jinrl.powermodule.pojo;

import java.sql.Timestamp;

/**
 * Tstatistic entity. @author MyEclipse Persistence Tools
 */

public class Tstatistic implements java.io.Serializable {
	/**
	 *
	 */
	private String fid;
	private String fdescription;
	private String fwarnInfo;
	private String fconnDatabase;
	private String fsql;
	private String fisshow;
	private String fwarnField;
	private String fgroupbyField;
	private String fwarnValue;
	private String fwarnType;
	private String fwarnEmail;
	private Timestamp fbeforeSendEmail;
	private Integer fsendEmailTime;
	private Integer frefreshTime;
	private String fbeforeValue;
	private Timestamp fbeforeTime;
	private String fisused;
	private String fip;
	private String furl;

	// Constructors

	/** default constructor */
	public Tstatistic() {
	}

	public Tstatistic(String fid, String fdescription, String fwarnInfo,
			String fconnDatabase, String fsql, String fisshow,
			String fwarnField, String fgroupbyField, String fwarnValue,
			String fwarnType, String fwarnEmail, Timestamp fbeforeSendEmail,
			Integer fsendEmailTime, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused, String fip, String furl) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fwarnInfo = fwarnInfo;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fgroupbyField = fgroupbyField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.fbeforeSendEmail = fbeforeSendEmail;
		this.fsendEmailTime = fsendEmailTime;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
		this.fip = fip;
		this.furl = furl;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public Tstatistic(String fid, String fdescription, String fwarnInfo,
			String fconnDatabase, String fsql, String fisshow,
			String fwarnField, String fgroupbyField, String fwarnValue,
			String fwarnType, String fwarnEmail, Timestamp fbeforeSendEmail,
			Integer fsendEmailTime, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused, String fip) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fwarnInfo = fwarnInfo;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fgroupbyField = fgroupbyField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.fbeforeSendEmail = fbeforeSendEmail;
		this.fsendEmailTime = fsendEmailTime;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
		this.fip = fip;
	}

	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	public Tstatistic(String fid, String fdescription, String fwarnInfo,
			String fconnDatabase, String fsql, String fisshow,
			String fwarnField, String fgroupbyField, String fwarnValue,
			String fwarnType, String fwarnEmail, Timestamp fbeforeSendEmail,
			Integer fsendEmailTime, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fwarnInfo = fwarnInfo;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fgroupbyField = fgroupbyField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.fbeforeSendEmail = fbeforeSendEmail;
		this.fsendEmailTime = fsendEmailTime;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
	}

	public Tstatistic(String fid, String fdescription, String fconnDatabase,
			String fsql, String fisshow, String fwarnField, String fwarnValue,
			String fwarnType, String fwarnEmail, Timestamp fbeforeSendEmail,
			Integer fsendEmailTime, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.fbeforeSendEmail = fbeforeSendEmail;
		this.fsendEmailTime = fsendEmailTime;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
	}

	public Tstatistic(String fid, String fdescription, String fwarnInfo,
			String fconnDatabase, String fsql, String fisshow,
			String fwarnField, String fwarnValue, String fwarnType,
			String fwarnEmail, Timestamp fbeforeSendEmail,
			Integer fsendEmailTime, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fwarnInfo = fwarnInfo;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.fbeforeSendEmail = fbeforeSendEmail;
		this.fsendEmailTime = fsendEmailTime;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
	}

	public String getFwarnInfo() {
		return fwarnInfo;
	}

	public void setFwarnInfo(String fwarnInfo) {
		this.fwarnInfo = fwarnInfo;
	}

	public String getFgroupbyField() {
		return fgroupbyField;
	}

	public void setFgroupbyField(String fgroupbyField) {
		this.fgroupbyField = fgroupbyField;
	}

	public Timestamp getFbeforeSendEmail() {
		return fbeforeSendEmail;
	}

	public void setFbeforeSendEmail(Timestamp fbeforeSendEmail) {
		this.fbeforeSendEmail = fbeforeSendEmail;
	}

	/** minimal constructor */
	public Tstatistic(String fid, String fdescription, String fconnDatabase,
			String fsql, String fisshow, String fisused) {
		this.fid = fid;
		this.fdescription = fdescription;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fisused = fisused;
	}


	public Tstatistic(String fid, String fdescription, String fconnDatabase,
			String fsql, String fisshow, String fwarnField, String fwarnValue,
			String fwarnType, String fwarnEmail, Integer frefreshTime,
			String fbeforeValue, Timestamp fbeforeTime, String fisused) {
		super();
		this.fid = fid;
		this.fdescription = fdescription;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fwarnValue = fwarnValue;
		this.fwarnType = fwarnType;
		this.fwarnEmail = fwarnEmail;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
	}

	/** full constructor */
	public Tstatistic(String fid, String fdescription, String fconnDatabase,
			String fsql, String fisshow, String fwarnField, String fwarnValue,
			String fwarnEmail, Integer frefreshTime, String fbeforeValue,
			Timestamp fbeforeTime, String fisused) {
		this.fid = fid;
		this.fdescription = fdescription;
		this.fconnDatabase = fconnDatabase;
		this.fsql = fsql;
		this.fisshow = fisshow;
		this.fwarnField = fwarnField;
		this.fwarnValue = fwarnValue;
		this.fwarnEmail = fwarnEmail;
		this.frefreshTime = frefreshTime;
		this.fbeforeValue = fbeforeValue;
		this.fbeforeTime = fbeforeTime;
		this.fisused = fisused;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public String getFwarnType() {
		return fwarnType;
	}

	public void setFwarnType(String fwarnType) {
		this.fwarnType = fwarnType;
	}


	public Integer getFsendEmailTime() {
		return fsendEmailTime;
	}

	public void setFsendEmailTime(Integer fsendEmailTime) {
		this.fsendEmailTime = fsendEmailTime;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public String getFconnDatabase() {
		return this.fconnDatabase;
	}

	public void setFconnDatabase(String fconnDatabase) {
		this.fconnDatabase = fconnDatabase;
	}

	public String getFsql() {
		return this.fsql;
	}

	public void setFsql(String fsql) {
		this.fsql = fsql;
	}

	public String getFisshow() {
		return this.fisshow;
	}

	public void setFisshow(String fisshow) {
		this.fisshow = fisshow;
	}

	public String getFwarnField() {
		return this.fwarnField;
	}

	public void setFwarnField(String fwarnField) {
		this.fwarnField = fwarnField;
	}

	public String getFwarnValue() {
		return this.fwarnValue;
	}

	public void setFwarnValue(String fwarnValue) {
		this.fwarnValue = fwarnValue;
	}

	public String getFwarnEmail() {
		return this.fwarnEmail;
	}

	public void setFwarnEmail(String fwarnEmail) {
		this.fwarnEmail = fwarnEmail;
	}

	public Integer getFrefreshTime() {
		return this.frefreshTime;
	}

	public void setFrefreshTime(Integer frefreshTime) {
		this.frefreshTime = frefreshTime;
	}

	public String getFbeforeValue() {
		return this.fbeforeValue;
	}

	public void setFbeforeValue(String fbeforeValue) {
		this.fbeforeValue = fbeforeValue;
	}

	public Timestamp getFbeforeTime() {
		return this.fbeforeTime;
	}

	public void setFbeforeTime(Timestamp fbeforeTime) {
		this.fbeforeTime = fbeforeTime;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}