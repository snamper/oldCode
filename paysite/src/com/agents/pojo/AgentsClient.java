package com.agents.pojo;



/**
 * AgentsClient entity. @author MyEclipse Persistence Tools
 */

public class AgentsClient implements java.io.Serializable {

	// Fields

	private String fid;
	private String fagentid;
	private String fclientid;

	// Constructors

	/** default constructor */
	public AgentsClient() {
	}

	/** minimal constructor */
	public AgentsClient(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public AgentsClient(String fid, String fagentid, String fclientid) {
		this.fid = fid;
		this.fagentid = fagentid;
		this.fclientid = fclientid;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFagentid() {
		return this.fagentid;
	}

	public void setFagentid(String fagentid) {
		this.fagentid = fagentid;
	}

	public String getFclientid() {
		return this.fclientid;
	}

	public void setFclientid(String fclientid) {
		this.fclientid = fclientid;
	}

}