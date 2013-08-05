package com.jinrl.powermodule.pojo;

/**
 * Tpositionuser entity. @author MyEclipse Persistence Tools
 */

public class Tpositionuser implements java.io.Serializable {
	private static final long serialVersionUID = 1096773664695446420L;
	private String fid;
	private Tposition tposition;
	private Tuser tuser;
	private String fisused;

	// Constructors

	/** default constructor */
	public Tpositionuser() {
	}

	/** minimal constructor */
	public Tpositionuser(String fid, Tposition tposition, Tuser tuser) {
		this.fid = fid;
		this.tposition = tposition;
		this.tuser = tuser;
	}

	/** full constructor */
	public Tpositionuser(String fid, Tposition tposition, Tuser tuser,
			String fisused) {
		this.fid = fid;
		this.tposition = tposition;
		this.tuser = tuser;
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

	public Tuser getTuser() {
		return this.tuser;
	}

	public void setTuser(Tuser tuser) {
		this.tuser = tuser;
	}

	public String getFisused() {
		return this.fisused;
	}

	public void setFisused(String fisused) {
		this.fisused = fisused;
	}

}