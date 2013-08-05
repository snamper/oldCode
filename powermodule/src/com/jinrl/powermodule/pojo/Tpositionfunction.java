package com.jinrl.powermodule.pojo;

/**
 * Tpositionfunction entity. @author MyEclipse Persistence Tools
 */

public class Tpositionfunction implements java.io.Serializable {
	private static final long serialVersionUID = -984375707889409838L;
	private String fid;
	private Tposition tposition;
	private Tfunction tfunction;
	private String fisused;

	// Constructors

	/** default constructor */
	public Tpositionfunction() {
	}

	/** minimal constructor */
	public Tpositionfunction(String fid, Tposition tposition,
			Tfunction tfunction) {
		this.fid = fid;
		this.tposition = tposition;
		this.tfunction = tfunction;
	}

	/** full constructor */
	public Tpositionfunction(String fid, Tposition tposition,
			Tfunction tfunction, String fisused) {
		this.fid = fid;
		this.tposition = tposition;
		this.tfunction = tfunction;
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

	public Tfunction getTfunction() {
		return this.tfunction;
	}

	public void setTfunction(Tfunction tfunction) {
		this.tfunction = tfunction;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}