package com.jinrl.powermodule.pojo;

/**
 * Tdownload entity. @author MyEclipse Persistence Tools
 */

public class Tdownload implements java.io.Serializable {

	// Fields

	private String fid;
	private String fname;
	private String finfoid;
	private String ffieldid;

	// Constructors

	/** default constructor */
	public Tdownload() {
	}

	/** minimal constructor */
	public Tdownload(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public Tdownload(String fid, String fname, String finfoid, String ffieldid) {
		this.fid = fid;
		this.fname = fname;
		this.finfoid = finfoid;
		this.ffieldid = ffieldid;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFinfoid() {
		return this.finfoid;
	}

	public void setFinfoid(String finfoid) {
		this.finfoid = finfoid;
	}

	public String getFfieldid() {
		return this.ffieldid;
	}

	public void setFfieldid(String ffieldid) {
		this.ffieldid = ffieldid;
	}

}