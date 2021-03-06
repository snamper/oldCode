package com.jinrl.powermodule.pojo;

/**
 * Tpowermanage entity. @author MyEclipse Persistence Tools
 */

public class Tpowermanage implements java.io.Serializable {

	// Fields

	private String fid;
	private String fpositionid;
	private String ffunctionid;
	private String fpowertype;
	private String fdataid;
	private String fpower;

	// Constructors

	/** default constructor */
	public Tpowermanage() {
	}

	/** minimal constructor */
	public Tpowermanage(String fid, String fpositionid, String ffunctionid,
			String fpowertype, String fdataid) {
		this.fid = fid;
		this.fpositionid = fpositionid;
		this.ffunctionid = ffunctionid;
		this.fpowertype = fpowertype;
		this.fdataid = fdataid;
	}

	/** full constructor */
	public Tpowermanage(String fid, String fpositionid, String ffunctionid,
			String fpowertype, String fdataid, String fpower) {
		this.fid = fid;
		this.fpositionid = fpositionid;
		this.ffunctionid = ffunctionid;
		this.fpowertype = fpowertype;
		this.fdataid = fdataid;
		this.fpower = fpower;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFpositionid() {
		return this.fpositionid;
	}

	public void setFpositionid(String fpositionid) {
		this.fpositionid = fpositionid;
	}

	public String getFfunctionid() {
		return this.ffunctionid;
	}

	public void setFfunctionid(String ffunctionid) {
		this.ffunctionid = ffunctionid;
	}

	public String getFpowertype() {
		return this.fpowertype;
	}

	public void setFpowertype(String fpowertype) {
		this.fpowertype = fpowertype;
	}

	public String getFdataid() {
		return this.fdataid;
	}

	public void setFdataid(String fdataid) {
		this.fdataid = fdataid;
	}

	public String getFpower() {
		return this.fpower;
	}

	public void setFpower(String fpower) {
		this.fpower = fpower;
	}

}