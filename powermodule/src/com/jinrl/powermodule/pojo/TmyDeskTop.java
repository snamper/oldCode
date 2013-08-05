package com.jinrl.powermodule.pojo;

/**
 * TmyDeskTop entity. @author MyEclipse Persistence Tools
 */

public class TmyDeskTop implements java.io.Serializable {

	// Fields

	private String fid;
	private String ftitle;
	private String ffunctionid;
	private String furl;
	private Integer forderbynum;
	private String fisused;

	// Constructors

	/** default constructor */
	public TmyDeskTop() {
	}

	public TmyDeskTop(String fid, String ftitle, String ffunctionid,
			String furl, Integer forderbynum, String fisused) {
		super();
		this.fid = fid;
		this.ftitle = ftitle;
		this.ffunctionid = ffunctionid;
		this.furl = furl;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
	}

	public String getFfunctionid() {
		return ffunctionid;
	}

	public void setFfunctionid(String ffunctionid) {
		this.ffunctionid = ffunctionid;
	}

	/** minimal constructor */
	public TmyDeskTop(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public TmyDeskTop(String fid, String ftitle, String furl,
			Integer forderbynum, String fisused) {
		this.fid = fid;
		this.ftitle = ftitle;
		this.furl = furl;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getFurl() {
		return this.furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public Integer getForderbynum() {
		return this.forderbynum;
	}

	public void setForderbynum(Integer forderbynum) {
		this.forderbynum = forderbynum;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}