package com.agents.pojo;


public class CaAgentProduct implements java.io.Serializable  {
	private String fID;
	private String fAgentID;
	private String fProductID;
	private Double fRate;
	private Double fClientRate;
	private String fDefState;
	public CaAgentProduct( String fID,
			 String fAgentID,
			 String fProductID,
			 Double fRate,
			 Double fClientRate,
			 String fDefState ){
		    this.fID=fID;
			this.fAgentID=fAgentID;
			this.fProductID=fProductID;
			this.fRate=fRate;
			this.fClientRate=fClientRate;
			this.fDefState=fDefState;
		
	}
	public String getfID() {
		return fID;
	}

	public void setfID(String fID) {
		this.fID = fID;
	}

	public String getfAgentID() {
		return fAgentID;
	}

	public void setfAgentID(String fAgentID) {
		this.fAgentID = fAgentID;
	}

	public String getfProductID() {
		return fProductID;
	}

	public void setfProductID(String fProductID) {
		this.fProductID = fProductID;
	}

	public Double getfRate() {
		return fRate;
	}

	public void setfRate(Double fRate) {
		this.fRate = fRate;
	}

	public Double getfClientRate() {
		return fClientRate;
	}

	public void setfClientRate(Double fClientRate) {
		this.fClientRate = fClientRate;
	}

	public String getfDefState() {
		return fDefState;
	}

	public void setfDefState(String fDefState) {
		this.fDefState = fDefState;
	}

	public String getfDefGiveID() {
		return fDefGiveID;
	}

	public void setfDefGiveID(String fDefGiveID) {
		this.fDefGiveID = fDefGiveID;
	}

	public String getfState() {
		return fState;
	}

	public void setfState(String fState) {
		this.fState = fState;
	}

	private String fDefGiveID;
	private String fState;
	
	public CaAgentProduct() {
	}

	/** minimal constructor */
	public CaAgentProduct(String fID) {
		this.fID = fID;
		
	}
	
}
