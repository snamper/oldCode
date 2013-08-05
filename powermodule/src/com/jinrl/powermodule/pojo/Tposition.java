package com.jinrl.powermodule.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Tposition entity. @author MyEclipse Persistence Tools
 */

public class Tposition implements java.io.Serializable,Comparable {
	private static final long serialVersionUID = 2820080516952201689L;
	private String fid;
	private String fpositionname;
	private String fisused;
	private String fshowstatistic;
	private Set tdepartpositions = new HashSet(0);
	private Set tpositionusers = new HashSet(0);
	private Set tpositionfunctions = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tposition() {
	}

	public Tposition(String fid, String fpositionname, String fisused,
			String fshowstatistic, Set tdepartpositions, Set tpositionusers,
			Set tpositionfunctions) {
		super();
		this.fid = fid;
		this.fpositionname = fpositionname;
		this.fisused = fisused;
		this.fshowstatistic = fshowstatistic;
		this.tdepartpositions = tdepartpositions;
		this.tpositionusers = tpositionusers;
		this.tpositionfunctions = tpositionfunctions;
	}

	public String getFshowstatistic() {
		return fshowstatistic;
	}

	public void setFshowstatistic(String fshowstatistic) {
		this.fshowstatistic = fshowstatistic;
	}

	/** minimal constructor */
	public Tposition(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public Tposition(String fid, String fpositionname, String fisused,
			Set tdepartpositions, Set tpositionusers, Set tpositionfunctions) {
		this.fid = fid;
		this.fpositionname = fpositionname;
		this.fisused = fisused;
		this.tdepartpositions = tdepartpositions;
		this.tpositionusers = tpositionusers;
		this.tpositionfunctions = tpositionfunctions;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFpositionname() {
		return this.fpositionname;
	}

	public void setFpositionname(String fpositionname) {
		this.fpositionname = fpositionname;
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

	public Set getTpositionusers() {
		return this.tpositionusers;
	}

	public void setTpositionusers(Set tpositionusers) {
		this.tpositionusers = tpositionusers;
	}

	public Set getTpositionfunctions() {
		return this.tpositionfunctions;
	}

	public void setTpositionfunctions(Set tpositionfunctions) {
		this.tpositionfunctions = tpositionfunctions;
	}

	public int compareTo(Object o) {
		return fid.compareTo(((Tposition) o).getFid());
	}

}