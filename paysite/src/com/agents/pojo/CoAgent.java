package com.agents.pojo;

/**
 * Agents entity. @author MyEclipse Persistence Tools
 */

public class CoAgent implements java.io.Serializable {

	// Fields
	private String  fid;
	private String  fname  ;                 
	private String  fpassword;
	
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

	
	private String  fstate;
	private Integer  freInterval;
	private Integer   freMaxTime;
	private String  factualMoney;
	private String  fservicePhone;
	private String  fserviceQq;
	private String  fserviceQq1;
	private String  fserviceQq2;
	private String  fserviceQq3;
	public CoAgent() {
	}

	/** minimal constructor */
	public CoAgent(String fid) {
		this.fid = fid;
	}
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
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

	public Integer  getFreInterval() {
		return freInterval;
	}

	public void setFreInterval(Integer freInterval) {
		this.freInterval = freInterval;
	}

	public Integer getFreMaxTime() {
		return freMaxTime;
	}
  
	public void setFreMaxTime(Integer freMaxTime) {
		this.freMaxTime = freMaxTime;
	}

	public String getFactualMoney() {
		return factualMoney;
	}

	public void setFactualMoney(String factualMoney) {
		this.factualMoney = factualMoney;
	}

	public String getFservicePhone() {
		return fservicePhone;
	}

	public void setFservicePhone(String fservicePhone) {
		this.fservicePhone = fservicePhone;
	}

	public String getFserviceQq() {
		return fserviceQq;
	}

	public void setFserviceQq(String fserviceQq) {
		this.fserviceQq = fserviceQq;
	}

	public String getFserviceQq1() {
		return fserviceQq1;
	}

	public void setFserviceQq1(String fserviceQq1) {
		this.fserviceQq1 = fserviceQq1;
	}

	public String getFserviceQq2() {
		return fserviceQq2;
	}

	public void setFserviceQq2(String fserviceQq2) {
		this.fserviceQq2 = fserviceQq2;
	}

	public String getFserviceQq3() {
		return fserviceQq3;
	}

	public void setFserviceQq3(String fserviceQq3) {
		this.fserviceQq3 = fserviceQq3;
	}

	public String getFserviceQq4() {
		return fserviceQq4;
	}

	public void setFserviceQq4(String fserviceQq4) {
		this.fserviceQq4 = fserviceQq4;
	}

	public String getFdomainName() {
		return fdomainName;
	}

	public void setFdomainName(String fdomainName) {
		this.fdomainName = fdomainName;
	}

	public String getFdomainIP() {
		return fdomainIP;
	}

	public void setFdomainIP(String fdomainIP) {
		this.fdomainIP = fdomainIP;
	}

	public String getFgiveIP() {
		return fgiveIP;
	}

	public void setFgiveIP(String fgiveIP) {
		this.fgiveIP = fgiveIP;
	}

	public String getFcaFaceID() {
		return fcaFaceID;
	}

	public void setFcaFaceID(String fcaFaceID) {
		this.fcaFaceID = fcaFaceID;
	}

	public String getFcaQueryID() {
		return fcaQueryID;
	}

	public void setFcaQueryID(String fcaQueryID) {
		this.fcaQueryID = fcaQueryID;
	}

	public String getFacFaceID() {
		return facFaceID;
	}

	public void setFacFaceID(String facFaceID) {
		this.facFaceID = facFaceID;
	}

	public String getFacQueryID() {
		return facQueryID;
	}

	public void setFacQueryID(String facQueryID) {
		this.facQueryID = facQueryID;
	}

	public String getFfaceIP() {
		return ffaceIP;
	}

	public void setFfaceIP(String ffaceIP) {
		this.ffaceIP = ffaceIP;
	}

	public String getFfaceDomain() {
		return ffaceDomain;
	}

	public void setFfaceDomain(String ffaceDomain) {
		this.ffaceDomain = ffaceDomain;
	}

	public String getFstartColor() {
		return fstartColor;
	}

	public void setFstartColor(String fstartColor) {
		this.fstartColor = fstartColor;
	}

	public String getFendColor() {
		return fendColor;
	}

	public void setFendColor(String fendColor) {
		this.fendColor = fendColor;
	}

	public String getFbackgroundColor() {
		return fbackgroundColor;
	}

	public void setFbackgroundColor(String fbackgroundColor) {
		this.fbackgroundColor = fbackgroundColor;
	}

	public String getFicp() {
		return ficp;
	}

	public void setFicp(String fiCP) {
		this.ficp = fiCP;
	}  

	public String getFindexPage() {
		return findexPage;
	}

	public void setFindexPage(String findexPage) {
		this.findexPage = findexPage;
	}

	public String getFwelcomePage() {
		return fwelcomePage;
	}

	public void setFwelcomePage(String fwelcomePage) {
		this.fwelcomePage = fwelcomePage;
	}

	public String getFmasterCSS() {
		return fmasterCSS;
	}

	public void setFmasterCSS(String fmasterCSS) {
		this.fmasterCSS = fmasterCSS;
	}

	public String getFleftCSS() {
		return fleftCSS;
	}

	public void setFleftCSS(String fleftCSS) {
		this.fleftCSS = fleftCSS;
	}

	public String getFtopCSS() {
		return ftopCSS;
	}

	public void setFtopCSS(String ftopCSS) {
		this.ftopCSS = ftopCSS;
	}

	public String getFphotoName() {
		return fphotoName;
	}

	public void setFphotoName(String fphotoName) {
		this.fphotoName = fphotoName;
	}

	public String getFjsName() {
		return fjsName;
	}

	public void setFjsName(String fjsName) {
		this.fjsName = fjsName;
	}

	public String getFcontentCSS() {
		return fcontentCSS;
	}

	public void setFcontentCSS(String fcontentCSS) {
		this.fcontentCSS = fcontentCSS;
	}

	public String getFlogin() {
		return flogin;
	}

	public void setFlogin(String flogin) {
		this.flogin = flogin;
	}

	private String  fserviceQq4;
	private String  fdomainName;
	private String  fdomainAgent;
	private String  fdomainClient;
	public String getFdomainAgent() {
		return fdomainAgent;
	}

	public void setFdomainAgent(String fdomainAgent) {
		this.fdomainAgent = fdomainAgent;
	}

	public String getFdomainClient() {
		return fdomainClient;
	}

	public void setFdomainClient(String fdomainClient) {
		this.fdomainClient = fdomainClient;
	}

	private String  fdomainIP;
	private String  fgiveIP;
	private String  fcaFaceID;
	private String  fcaQueryID;
	private String  facFaceID;
	private String  facQueryID;
	private String  ffaceIP;
	private String  ffaceDomain;
	private String  fstartColor;
	private String  fendColor;
	private String  fbackgroundColor;
	private String  ficp;
	private String  findexPage;
	private String  fwelcomePage;
	private String  fmasterCSS;
	private String  fleftCSS;
	private String  ftopCSS;
	private String  fphotoName;
	private String  fjsName;
	private String  fcontentCSS;
	private String  flogin;
	
	private String  fStyleConfig;
	private String  fMenuConfig;

	
	public String getfStyleConfig() {
		return fStyleConfig;
	}

	public void setfStyleConfig(String fStyleConfig) {
		this.fStyleConfig = fStyleConfig;
	}

	public String getfMenuConfig() {
		return fMenuConfig;
	}

	public void setfMenuConfig(String fMenuConfig) {
		this.fMenuConfig = fMenuConfig;
	}

	public CoAgent( String  fserviceQq4,String  fdomainName,String  fdomainIP,String  fgiveIP,String  fcaFaceID,
			 String  fcaQueryID, String  facFaceID, String  facQueryID, String  ffaceIP, String  ffaceDomain,
			 String  fstartColor, String  fendColor, String  fbackgroundColor, String  ficp,String  findexPage,
			 String  fwelcomePage, String  fmasterCSS,String  fleftCSS,String  ftopCSS,String  fphotoName,
			 String  fjsName, String  fcontentCSS,String  flogin,String fStyleConfig,String fMenuConfig,String fdomainAgent,String fdomainClient) {
		super();
		this.fStyleConfig=fStyleConfig;
		this.fMenuConfig=fMenuConfig;
		this.fserviceQq4=fserviceQq4;
		this.fdomainName=fdomainName;
		this.fdomainAgent=fdomainAgent;
		this.fdomainClient=fdomainClient;  
		this.fdomainIP=fdomainIP;
		this.fgiveIP=fgiveIP;
		this.fcaFaceID=fcaFaceID;
		this.fcaQueryID=fcaQueryID;
		this.facFaceID=facFaceID;
		this.facQueryID=facQueryID;
		this.ffaceIP=ffaceIP;
		this.ffaceDomain=ffaceDomain;
		this.fstartColor=fstartColor;
		this.fendColor=fendColor;
		this.fbackgroundColor=fbackgroundColor;
		this.ficp=ficp;
		this.findexPage=findexPage;
		this.fwelcomePage=fwelcomePage;
		this.fmasterCSS=fmasterCSS;
		this.fleftCSS=fleftCSS;
		this.ftopCSS=ftopCSS;
		this.fphotoName=fphotoName;
		this.fjsName=fjsName;
		this.fcontentCSS=fcontentCSS;
		this.flogin=flogin;
	}
}