package com.jinrl.powermodule.pojo;

/**
 * Tdepartposition entity. @author MyEclipse Persistence Tools
 */

public class Tdepartposition implements java.io.Serializable {
	private static final long serialVersionUID = 4158447994912940814L;
	private String fid;
	private Tposition tposition;
	private Tdepartment tdepartment;
	private String fisused;

	// Constructors

	/** default constructor */
	public Tdepartposition() {
	}

	/** minimal constructor */
	public Tdepartposition(String fid, Tposition tposition,
			Tdepartment tdepartment) {
		this.fid = fid;
		this.tposition = tposition;
		this.tdepartment = tdepartment;
	}

	/** full constructor */
	public Tdepartposition(String fid, Tposition tposition,
			Tdepartment tdepartment, String fisused) {
		this.fid = fid;
		this.tposition = tposition;
		this.tdepartment = tdepartment;
		this.fisused = fisused;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Tposition getTposition() {
		return this.tposition;
	}

	public void setTposition(Tposition tposition) {
		this.tposition = tposition;
	}

	public Tdepartment getTdepartment() {
		return this.tdepartment;
	}

	public void setTdepartment(Tdepartment tdepartment) {
		this.tdepartment = tdepartment;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}