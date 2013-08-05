package com.agents.pojo;

import java.sql.Timestamp;

/**
 * Client entity. @author MyEclipse Persistence Tools
 */

public class Client implements java.io.Serializable {

	// Fields

	private String fid;
	private String fagentId;
	private String fname;
	private String fpassword;   
	
	private Timestamp fcreateTime;
	private String fstate;
	
	private String ffaceID;
	private String fkey;
	private String femail;
	private String fqq;
	private String fbankId;
	private String fbankName;
	private String fbankNum;
	private String fquestion;
	private String fanswer;
	private String fWebName;
	private String fWebURL;
	private String fPhone;
	private String fBankSeat;
	private String  fbindingstate;
	private String  fbindingpara;
	private String fAddMoneyType;
	
	
	public String getfAddMoneyType() {
		return fAddMoneyType;
	}

	public void setfAddMoneyType(String fAddMoneyType) {
		this.fAddMoneyType = fAddMoneyType;
	}

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
	
	// Constructors

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFagentId() {
		return fagentId;
	}

	public void setFagentId(String fagentId) {
		this.fagentId = fagentId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFpassword() {
		return fpassword;
	}

	public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
	}

	public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public Timestamp getFcreateTime() {
		return fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	public String getFfaceID() {
		return ffaceID;
	}

	public void setFfaceID(String ffaceID) {
		this.ffaceID = ffaceID;
	}

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getFemail() {
		return femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	public String getFqq() {
		return fqq;
	}

	public void setFqq(String fqq) {
		this.fqq = fqq;
	}

	public String getFbankId() {
		return fbankId;
	}

	public void setFbankId(String fbankId) {
		this.fbankId = fbankId;
	}

	public String getFbankName() {
		return fbankName;
	}

	public void setFbankName(String fbankName) {
		this.fbankName = fbankName;
	}

	public String getFbankNum() {
		return fbankNum;
	}

	public void setFbankNum(String fbankNum) {
		this.fbankNum = fbankNum;
	}

	public String getFquestion() {
		return fquestion;
	}

	public void setFquestion(String fquestion) {
		this.fquestion = fquestion;
	}

	public String getFanswer() {
		return fanswer;
	}

	public void setFanswer(String fanswer) {
		this.fanswer = fanswer;
	}

	public String getfWebName() {
		return fWebName;
	}

	public void setfWebName(String fWebName) {
		this.fWebName = fWebName;
	}

	public String getfWebURL() {
		return fWebURL;
	}

	public void setfWebURL(String fWebURL) {
		this.fWebURL = fWebURL;
	}

	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	public String getfBankSeat() {
		return fBankSeat;
	}

	public void setfBankSeat(String fBankSeat) {
		this.fBankSeat = fBankSeat;
	}

	/** default constructor */
	public Client() {
	}

	/** minimal constructor */
	public Client(String fid) {
		this.fid = fid;
	}

	

	/** full constructor */
	public Client(String fid,String fagentId,String fname,String fpassword,String fstate,Timestamp fcreateTime,String ffaceID,String fkey,String femail,
			 String fqq,String fbankId,String fbankName,String fbankNum,String fquestion,String fanswer,String fWebName,String fWebURL,String fPhone,String fBankSeat
				) {
		this.fid=fid;
		this.fagentId=fagentId;
		this.fname=fname;
		this.fpassword=fpassword;
		this.fstate=fstate;
		this.fcreateTime=fcreateTime;   
		this.ffaceID=ffaceID;
		this.fkey=fkey;
		this.femail=femail;
		this.fqq=fqq;
		this.fbankId=fbankId;
		this.fbankName=fbankName;
		this.fbankNum=fbankNum;
		this.fquestion=fquestion;
		this.fanswer=fanswer;
		this.fWebName=fWebName;
		this.fWebURL=fWebURL;
		this.fPhone=fPhone;
		this.fBankSeat=fBankSeat;
		
	}

	
	

	

}