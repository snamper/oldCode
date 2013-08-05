package com.jinrl.powermodule.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Tfunction entity. @author MyEclipse Persistence Tools
 */

public class Tfunction implements java.io.Serializable,Comparable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3680718353916364624L;
	private String fid;
	private String fsystemid;
	private String fmoduleid;
	private String ffunctionname;
	private String ffunctionrul;
	private String fbusiinfoid;
	private Integer forderbynum;
	private String fisused;
	private String fcommon;
	private Set tpositionfunctions = new HashSet(0);
	private String fkey;
	private String fversion;

	public Tfunction(String fid, String fsystemid, String fmoduleid,
			String ffunctionname, String ffunctionrul, String fbusiinfoid,
			Integer forderbynum, String fisused, String fcommon,
			Set tpositionfunctions, String fkey, String fversion) {
		super();
		this.fid = fid;
		this.fsystemid = fsystemid;
		this.fmoduleid = fmoduleid;
		this.ffunctionname = ffunctionname;
		this.ffunctionrul = ffunctionrul;
		this.fbusiinfoid = fbusiinfoid;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
		this.fcommon = fcommon;
		this.tpositionfunctions = tpositionfunctions;
		this.fkey = fkey;
		this.fversion = fversion;
	}

	// Constructors

	public String getFversion() {
		return fversion;
	}

	public void setFversion(String fversion) {
		this.fversion = fversion;
	}

	/** default constructor */
	public Tfunction() {
	}

	public Tfunction(String fid, String fsystemid, String fmoduleid,
			String ffunctionname, String ffunctionrul, String fbusiinfoid,
			Integer forderbynum, String fisused, String fcommon,
			Set tpositionfunctions, String fkey) {
		super();
		this.fid = fid;
		this.fsystemid = fsystemid;
		this.fmoduleid = fmoduleid;
		this.ffunctionname = ffunctionname;
		this.ffunctionrul = ffunctionrul;
		this.fbusiinfoid = fbusiinfoid;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
		this.fcommon = fcommon;
		this.tpositionfunctions = tpositionfunctions;
		this.fkey = fkey;
	}

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public Tfunction(String fid, String fsystemid, String fmoduleid,
			String ffunctionname, String ffunctionrul, String fbusiinfoid,
			Integer forderbynum, String fisused, String fcommon,
			Set tpositionfunctions) {
		super();
		this.fid = fid;
		this.fsystemid = fsystemid;
		this.fmoduleid = fmoduleid;
		this.ffunctionname = ffunctionname;
		this.ffunctionrul = ffunctionrul;
		this.fbusiinfoid = fbusiinfoid;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
		this.fcommon = fcommon;
		this.tpositionfunctions = tpositionfunctions;
	}

	public String getFcommon() {
		return fcommon;
	}

	public void setFcommon(String fcommon) {
		this.fcommon = fcommon;
	}

	public Tfunction(String fid, String fsystemid, String fmoduleid,
			String ffunctionname, String ffunctionrul, String fbusiinfoid,
			Integer forderbynum, String fisused, Set tpositionfunctions) {
		super();
		this.fid = fid;
		this.fsystemid = fsystemid;
		this.fmoduleid = fmoduleid;
		this.ffunctionname = ffunctionname;
		this.ffunctionrul = ffunctionrul;
		this.fbusiinfoid = fbusiinfoid;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
		this.tpositionfunctions = tpositionfunctions;
	}

	public String getFbusiinfoid() {
		return fbusiinfoid;
	}

	public void setFbusiinfoid(String fbusiinfoid) {
		this.fbusiinfoid = fbusiinfoid;
	}

	/** minimal constructor */
	public Tfunction(String fid, Integer forderbynum) {
		this.fid = fid;
		this.forderbynum = forderbynum;
	}

	/** full constructor */
	public Tfunction(String fid, String fsystemid, String fmoduleid,
			String ffunctionname, String ffunctionrul, Integer forderbynum,
			String fisused, Set tpositionfunctions) {
		this.fid = fid;
		this.fsystemid = fsystemid;
		this.fmoduleid = fmoduleid;
		this.ffunctionname = ffunctionname;
		this.ffunctionrul = ffunctionrul;
		this.forderbynum = forderbynum;
		this.fisused = fisused;
		this.tpositionfunctions = tpositionfunctions;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFsystemid() {
		return this.fsystemid;
	}

	public void setFsystemid(String fsystemid) {
		this.fsystemid = fsystemid;
	}

	public String getFmoduleid() {
		return this.fmoduleid;
	}

	public void setFmoduleid(String fmoduleid) {
		this.fmoduleid = fmoduleid;
	}

	public String getFfunctionname() {
		return this.ffunctionname;
	}

	public void setFfunctionname(String ffunctionname) {
		this.ffunctionname = ffunctionname;
	}

	public String getFfunctionrul() {
		return this.ffunctionrul;
	}

	public void setFfunctionrul(String ffunctionrul) {
		this.ffunctionrul = ffunctionrul;
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

	public Set getTpositionfunctions() {
		return this.tpositionfunctions;
	}

	public void setTpositionfunctions(Set tpositionfunctions) {
		this.tpositionfunctions = tpositionfunctions;
	}

	public int compareTo(Object o) {
////		try {
////			//由小到大排序
////			String ofid = ((Tfunction) o).getFid();
////			return Integer.parseInt(fid) - Integer.parseInt(ofid);
////		} catch (Exception e) {
////			return fid.compareTo(((Tfunction) o).getFid());
////		}
////		int orderbm = ((Tfunction) o).getForderbynum();
////		return this.forderbynum-orderbm;
		Tfunction t = (Tfunction) o;
//		return this.forderbynum - t.getForderbynum();
////		return this.forderbynum != t.getForderbynum() ? this.forderbynum - t.getForderbynum() : 1;
////		if(this.forderbynum != t.getForderbynum())
////			return this.forderbynum - t.getForderbynum();
////		else
////			return 1;
////		return 1;
		return this.fid.compareTo(t.getFid());
	}

}