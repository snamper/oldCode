package com.jinrl.powermodule.pojo;

/**
 * Tmoduletype entity. @author MyEclipse Persistence Tools
 */

public class Tmoduletype implements java.io.Serializable {
	private static final long serialVersionUID = 49285531703304863L;
	private Integer fid;
	private String fmodulename;
	private String fisused;
	private String fip;

	// Constructors

	/** default constructor */
	public Tmoduletype() {
	}

	public Tmoduletype(Integer fid, String fmodulename, String fisused,
			String fip) {
		super();
		this.fid = fid;
		this.fmodulename = fmodulename;
		this.fisused = fisused;
		this.fip = fip;
	}

	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	/** minimal constructor */
	public Tmoduletype(Integer fid) {
		this.fid = fid;
	}

	/** full constructor */
	public Tmoduletype(Integer fid, String fmodulename, String fisused) {
		this.fid = fid;
		this.fmodulename = fmodulename;
		this.fisused = fisused;
	}

	// Property accessors

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFmodulename() {
		return this.fmodulename;
	}

	public void setFmodulename(String fmodulename) {
		this.fmodulename = fmodulename;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}