package com.dhtx.sign.pojo;

/**
 * 类名称：Product 
 * 类描述： 
 * 创建人: renfy 
 * 创建时间：2012-1-4 上午11:49:32
 * @version 1.0
 * 
 */
public class Product {
	private String fid;
	private String fname;
	private int fnum;
	private String fstate;
	private String fpayMoney;
	private String fadMoney;

	public String getFpayMoney() {
		return fpayMoney;
	}

	public void setFpayMoney(String fpayMoney) {
		this.fpayMoney = fpayMoney;
	}

	public String getFadMoney() {
		return fadMoney;
	}

	public void setFadMoney(String fadMoney) {
		this.fadMoney = fadMoney;
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

	public int getFnum() {
		return fnum;
	}

	public void setFnum(int fnum) {
		this.fnum = fnum;
	}

	public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

}
