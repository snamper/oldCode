package com.agents.pojo;

import java.sql.Timestamp;

/**
 * AgentNews entity. @author MyEclipse Persistence Tools
 */

public class AgentNews implements java.io.Serializable {

	// Fields  

	private Integer id;
	private String fagentId;
	private String ftitle;
	private String fauthor;
	private Timestamp ftime;
	private String fcontent;
	private String ftype;
	private String fstate;
	private String fstateRate;

	// Constructors

	/** default constructor */
	public AgentNews() {
	}

	/** minimal constructor */
	public AgentNews(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AgentNews(Integer id, String fagentId, String ftitle,
			String fauthor, Timestamp ftime, String fcontent, String ftype,
			String fstate, String fstateRate) {
		this.id = id;
		this.fagentId = fagentId;
		this.ftitle = ftitle;
		this.fauthor = fauthor;
		this.ftime = ftime;
		this.fcontent = fcontent;
		this.ftype = ftype;
		this.fstate = fstate;
		this.fstateRate = fstateRate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFagentId() {
		return this.fagentId;
	}

	public void setFagentId(String fagentId) {
		this.fagentId = fagentId;
	}

	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getFauthor() {
		return this.fauthor;
	}

	public void setFauthor(String fauthor) {
		this.fauthor = fauthor;
	}

	public Timestamp getFtime() {
		return this.ftime;
	}

	public void setFtime(Timestamp ftime) {
		this.ftime = ftime;
	}

	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	public String getFtype() {
		return this.ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getFstate() {
		return this.fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public String getFstateRate() {
		return this.fstateRate;
	}

	public void setFstateRate(String fstateRate) {
		this.fstateRate = fstateRate;
	}

}