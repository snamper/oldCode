package com.agents.pojo;

import java.sql.Timestamp;

/**
 * FillCard entity. @author MyEclipse Persistence Tools
 */

public class FillCard implements java.io.Serializable {

	// Fields

	private String id;
	private String fclient;
	private String fstate;
	private byte[] fcardId;
	private String fcardType;
	private String fcardTypeId;
	//private byte[] fpassword;
	private Double fprice;
	private Timestamp fcreateTime;
	private String ffullOrderNo;
	private Double fbili;
	private Double ffactMoney;
	private String fremark;
	private Timestamp ffillTime;
	private String version;
	private String fuserId;
	private String forderId;
	private String fshowId;
	private String fcheckState;
	private Double fmoney;
	private String fupperClientId1;
	private Double fupperBili1;
	private String fupperClientId2;
	private Double fupperBili2;
	private String faccountId;
	private Integer ferrorCount;
	private String freturnSupState;
	private String fautoType;
	private Timestamp foverTime;
	private String fisSend;
	private String fuserA;
	private String fuserB;
	private String fuserC;
	private String fuserUrla;
	private String fuserUrlb;
	private String ffillMsg;
	private String cardid;
	private String password;
	// Constructors

	/** default constructor */
	public FillCard() {
	}

	/** full constructor */
	public FillCard(String fclient, String fstate, byte[] fcardId,
			String fcardType, String fcardTypeId, byte[] fpassword,
			Double fprice, Timestamp fcreateTime, String ffullOrderNo,
			Double fbili, Double ffactMoney, String fremark,
			Timestamp ffillTime, String version, String fuserId,
			String forderId, String fshowId, String fcheckState, Double fmoney,
			String fupperClientId1, Double fupperBili1, String fupperClientId2,
			Double fupperBili2, String faccountId, Integer ferrorCount,
			String freturnSupState, String fautoType, Timestamp foverTime,
			String fisSend, String fuserA, String fuserB, String fuserC,
			String fuserUrla, String fuserUrlb, String ffillMsg,String cardid,String password) {
		this.fclient = fclient;
		this.fstate = fstate;
		this.fcardId = fcardId;
		this.fcardType = fcardType;
		this.fcardTypeId = fcardTypeId;
		//this.fpassword = fpassword;
		this.fprice = fprice;
		this.fcreateTime = fcreateTime;
		this.ffullOrderNo = ffullOrderNo;
		this.fbili = fbili;
		this.ffactMoney = ffactMoney;
		this.fremark = fremark;
		this.ffillTime = ffillTime;
		this.version = version;
		this.fuserId = fuserId;
		this.forderId = forderId;
		this.fshowId = fshowId;
		this.fcheckState = fcheckState;
		this.fmoney = fmoney;
		this.fupperClientId1 = fupperClientId1;
		this.fupperBili1 = fupperBili1;
		this.fupperClientId2 = fupperClientId2;
		this.fupperBili2 = fupperBili2;
		this.faccountId = faccountId;
		this.ferrorCount = ferrorCount;
		this.freturnSupState = freturnSupState;
		this.fautoType = fautoType;
		this.foverTime = foverTime;
		this.fisSend = fisSend;
		this.fuserA = fuserA;
		this.fuserB = fuserB;
		this.fuserC = fuserC;
		this.fuserUrla = fuserUrla;
		this.fuserUrlb = fuserUrlb;
		this.ffillMsg = ffillMsg;
	}

	// Property accessors

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFclient() {
		return this.fclient;
	}

	public void setFclient(String fclient) {
		this.fclient = fclient;
	}

	public String getFstate() {
		return this.fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public byte[] getFcardId() {
		return this.fcardId;
	}

	public void setFcardId(byte[] fcardId) {
		this.fcardId = fcardId;
	}

	public String getFcardType() {
		return this.fcardType;
	}

	public void setFcardType(String fcardType) {
		this.fcardType = fcardType;
	}

	public String getFcardTypeId() {
		return this.fcardTypeId;
	}

	public void setFcardTypeId(String fcardTypeId) {
		this.fcardTypeId = fcardTypeId;
	}

	//public byte[] getFpassword() {
	//	return this.fpassword;
	//}

	//public void setFpassword(byte[] fpassword) {
	//	this.fpassword = fpassword;
	//}

	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	public String getFfullOrderNo() {
		return this.ffullOrderNo;
	}

	public void setFfullOrderNo(String ffullOrderNo) {
		this.ffullOrderNo = ffullOrderNo;
	}

	public Double getFbili() {
		return this.fbili;
	}

	public void setFbili(Double fbili) {
		this.fbili = fbili;
	}

	public Double getFfactMoney() {
		return this.ffactMoney;
	}

	public void setFfactMoney(Double ffactMoney) {
		this.ffactMoney = ffactMoney;
	}

	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public Timestamp getFfillTime() {
		return this.ffillTime;
	}

	public void setFfillTime(Timestamp ffillTime) {
		this.ffillTime = ffillTime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFuserId() {
		return this.fuserId;
	}

	public void setFuserId(String fuserId) {
		this.fuserId = fuserId;
	}

	public String getForderId() {
		return this.forderId;
	}

	public void setForderId(String forderId) {
		this.forderId = forderId;
	}

	public String getFshowId() {
		return this.fshowId;
	}

	public void setFshowId(String fshowId) {
		this.fshowId = fshowId;
	}

	public String getFcheckState() {
		return this.fcheckState;
	}

	public void setFcheckState(String fcheckState) {
		this.fcheckState = fcheckState;
	}

	public Double getFmoney() {
		return this.fmoney;
	}

	public void setFmoney(Double fmoney) {
		this.fmoney = fmoney;
	}

	public String getFupperClientId1() {
		return this.fupperClientId1;
	}

	public void setFupperClientId1(String fupperClientId1) {
		this.fupperClientId1 = fupperClientId1;
	}

	public Double getFupperBili1() {
		return this.fupperBili1;
	}

	public void setFupperBili1(Double fupperBili1) {
		this.fupperBili1 = fupperBili1;
	}

	public String getFupperClientId2() {
		return this.fupperClientId2;
	}

	public void setFupperClientId2(String fupperClientId2) {
		this.fupperClientId2 = fupperClientId2;
	}

	public Double getFupperBili2() {
		return this.fupperBili2;
	}

	public void setFupperBili2(Double fupperBili2) {
		this.fupperBili2 = fupperBili2;
	}

	public String getFaccountId() {
		return this.faccountId;
	}

	public void setFaccountId(String faccountId) {
		this.faccountId = faccountId;
	}

	public Integer getFerrorCount() {
		return this.ferrorCount;
	}

	public void setFerrorCount(Integer ferrorCount) {
		this.ferrorCount = ferrorCount;
	}

	public String getFreturnSupState() {
		return this.freturnSupState;
	}

	public void setFreturnSupState(String freturnSupState) {
		this.freturnSupState = freturnSupState;
	}

	public String getFautoType() {
		return this.fautoType;
	}

	public void setFautoType(String fautoType) {
		this.fautoType = fautoType;
	}

	public Timestamp getFoverTime() {
		return this.foverTime;
	}

	public void setFoverTime(Timestamp foverTime) {
		this.foverTime = foverTime;
	}

	public String getFisSend() {
		return this.fisSend;
	}

	public void setFisSend(String fisSend) {
		this.fisSend = fisSend;
	}

	public String getFuserA() {
		return this.fuserA;
	}

	public void setFuserA(String fuserA) {
		this.fuserA = fuserA;
	}

	public String getFuserB() {
		return this.fuserB;
	}

	public void setFuserB(String fuserB) {
		this.fuserB = fuserB;
	}

	public String getFuserC() {
		return this.fuserC;
	}

	public void setFuserC(String fuserC) {
		this.fuserC = fuserC;
	}

	public String getFuserUrla() {
		return this.fuserUrla;
	}

	public void setFuserUrla(String fuserUrla) {
		this.fuserUrla = fuserUrla;
	}

	public String getFuserUrlb() {
		return this.fuserUrlb;
	}

	public void setFuserUrlb(String fuserUrlb) {
		this.fuserUrlb = fuserUrlb;
	}

	public String getFfillMsg() {
		return this.ffillMsg;
	}

	public void setFfillMsg(String ffillMsg) {
		this.ffillMsg = ffillMsg;
	}

}