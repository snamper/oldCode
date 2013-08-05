package com.jinrl.powermodule.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Tdepartment entity. @author MyEclipse Persistence Tools
 */

public class Tdepartment implements java.io.Serializable {
	private static final long serialVersionUID = 3391015309191793275L;
	private String fid;
	private String fdename;
	private String fisused;
	private Set tdepartpositions = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tdepartment() {
	}

	/** minimal constructor */
	public Tdepartment(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public Tdepartment(String fid, String fdename, String fisused,
			Set tdepartpositions) {
		this.fid = fid;
		this.fdename = fdename;
		this.fisused = fisused;
		this.tdepartpositions = tdepartpositions;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFdename() {
		return this.fdename;
	}

	public void setFdename(String fdename) {
		this.fdename = fdename;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

	public Set getTdepartpositions() {
		return this.tdepartpositions;
	}

	public void setTdepartpositions(Set tdepartpositions) {
		this.tdepartpositions = tdepartpositions;
	}

}