package com.agents.pojo;




/**
 * Staffs entity. @author MyEclipse Persistence Tools
 */

public class Staff  implements java.io.Serializable {

     private String fId;
     private String fHigherId;
     private String fType;
     private String  fName;
     private String fPassword;
     private String fPopedom;
     private String fState;
     public Staff() {
 	}
     private String  fbindingstate;
 	private String  fbindingpara;
 	public String getFbindingstate() {
 		return fbindingstate;
 	}

 	public void setFbindingstate(String fbindingstate) {
 		this.fbindingstate = fbindingstate;
 	}
 	
 	public String getFbindingpara() {
 		return fbindingpara;
 	}

 	public void setFbindingpara(String fbindingpara) {
 		this.fbindingpara = fbindingpara;
 	}
    public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getfHigherId() {
		return fHigherId;
	}
	public void setfHigherId(String fHigherId) {
		this.fHigherId = fHigherId;
	}
	public String getfType() {
		return fType;
	}
	public void setfType(String fType) {
		this.fType = fType;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getfPassword() {
		return fPassword;
	}


	public void setfPassword(String fPassword) {
		this.fPassword = fPassword;
	}


	public String getfPopedom() {
		return fPopedom;
	}


	public void setfPopedom(String fPopedom) {
		this.fPopedom = fPopedom;
	}


	public String getfState() {
		return fState;
	}


	public void setfState(String fState) {
		this.fState = fState;
	}
    

	/** full constructor */
    public Staff(String fid,String fHigherId,String fType,String  fName,String fPassword,String fPopedom,String fState) {
    	super();
    	this.fId = fId;
        this.fHigherId=fHigherId;
        this.fType=fType;
        this.fName=fName;
        this.fPassword = fPassword;
        this.fPopedom = fPopedom;
        this.fState = fState;
    }
     
    

}