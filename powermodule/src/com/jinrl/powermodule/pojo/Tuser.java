package com.jinrl.powermodule.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */

public class Tuser implements java.io.Serializable {
	private static final long serialVersionUID = 6223671449182842772L;
	private String fid;
	private String fusername;
	private String fpassword;
	private String fsex;
	private Timestamp fbirthday;
	private String fisused;
	private String allowip;
	private String email;
	private String fcustomUrl;
	private String fGoogleDayUrl;//Google日历
	private Set tpositionusers = new HashSet(0);

	// Constructors

	/** default constructor */

	public Tuser() {
	}

	public Tuser(String fid, String fusername, String fpassword, String fsex,
			Timestamp fbirthday, String fisused, String allowip, String email,
			String fcustomUrl, String fGoogleDayUrl, Set tpositionusers) {
		super();
		this.fid = fid;
		this.fusername = fusername;
		this.fpassword = fpassword;
		this.fsex = fsex;
		this.fbirthday = fbirthday;
		this.fisused = fisused;
		this.allowip = allowip;
		this.email = email;
		this.fcustomUrl = fcustomUrl;
		this.fGoogleDayUrl = fGoogleDayUrl;
		this.tpositionusers = tpositionusers;
	}

	public String getFcustomUrl() {
		return fcustomUrl;
	}

	public void setFcustomUrl(String fcustomUrl) {
		this.fcustomUrl = fcustomUrl;
	}

	public Tuser(String fid, String fusername, String fpassword, String fsex,
			Timestamp fbirthday, String fisused, String allowip, String email,
			Set tpositionusers) {
		super();
		this.fid = fid;
		this.fusername = fusername;
		this.fpassword = fpassword;
		this.fsex = fsex;
		this.fbirthday = fbirthday;
		this.fisused = fisused;
		this.allowip = allowip;
		this.email = email;
		this.tpositionusers = tpositionusers;
	}

	public Tuser(String fid, String fusername, String fpassword, String fsex,
			Timestamp fbirthday, String fisused, String allowip,
			Set tpositionusers) {
		super();
		this.fid = fid;
		this.fusername = fusername;
		this.fpassword = fpassword;
		this.fsex = fsex;
		this.fbirthday = fbirthday;
		this.fisused = fisused;
		this.allowip = allowip;
		this.tpositionusers = tpositionusers;
	}

	public Tuser(String fid, String fusername, String fpassword, String fsex,
			Timestamp fbirthday, String fisused, String allowip, String email,
			String fGoogleDayUrl, Set tpositionusers) {
		super();
		this.fid = fid;
		this.fusername = fusername;
		this.fpassword = fpassword;
		this.fsex = fsex;
		this.fbirthday = fbirthday;
		this.fisused = fisused;
		this.allowip = allowip;
		this.email = email;
		this.fGoogleDayUrl = fGoogleDayUrl;
		this.tpositionusers = tpositionusers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




	public String getfGoogleDayUrl() {
		return fGoogleDayUrl;
	}

	public void setfGoogleDayUrl(String fGoogleDayUrl) {
		this.fGoogleDayUrl = fGoogleDayUrl;
	}


	public String getAllowip() {
		return allowip;
	}

	public void setAllowip(String allowip) {
		this.allowip = allowip;
	}

	/** minimal constructor */
	public Tuser(String fid, String fpassword) {
		this.fid = fid;
		this.fpassword = fpassword;
	}

	/** full constructor */
	public Tuser(String fid, String fusername, String fpassword, String fsex,
			Timestamp fbirthday, String fisused, Set tpositionusers) {
		this.fid = fid;
		this.fusername = fusername;
		this.fpassword = fpassword;
		this.fsex = fsex;
		this.fbirthday = fbirthday;
		this.fisused = fisused;
		this.tpositionusers = tpositionusers;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFusername() {
		return this.fusername;
	}

	public void setFusername(String fusername) {
		this.fusername = fusername;
	}

	public String getFpassword() {
		return this.fpassword;
	}

	public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
	}

	public String getFsex() {
		return this.fsex;
	}

	public void setFsex(String fsex) {
		this.fsex = fsex;
	}

	public Timestamp getFbirthday() {
		return this.fbirthday;
	}

	public void setFbirthday(Timestamp fbirthday) {
		this.fbirthday = fbirthday;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

	public Set getTpositionusers() {
		return this.tpositionusers;
	}

	public void setTpositionusers(Set tpositionusers) {
		this.tpositionusers = tpositionusers;
	}

}