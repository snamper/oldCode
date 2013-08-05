package com.dhtx.sign.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhtx.sign.pojo.BGPicture;
import com.dhtx.sign.pojo.CharType;
import com.dhtx.sign.pojo.Product;
import com.dhtx.sign.util.global.DataConnect;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：ProductDAO   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 上午11:46:47   
 * @version 1.0
 *  
 */
public class ProductDAO {
	
	private static DataConnect dc = new DataConnect("cftsign", false);
	
	/**
	 * 
	 * 方法名称: getProductById 
	 * 方法描述: 获取指定ID的商品
	 * 创建人: renfy
	 * 创建时间: 2012-1-4 下午12:09:00
	 * @param fid
	 * @return
	 * @version 1.0
	 *
	 */
	public Product getProductById(String fid){
		Product result = null;
		String sql = "select * from tProduct where fID='"+fid+"' and fstate='0'";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				result = new Product();
				result.setFid(fc.getrv(rs, "fID", ""));
				result.setFname(fc.getrv(rs, "fName", ""));
				result.setFnum(rs.getInt("fNum"));
				result.setFstate(fc.getrv(rs, "fState", ""));
				result.setFpayMoney(fc.getrv(rs, "fPayMoney", ""));
				result.setFadMoney(fc.getrv(rs, "fAdMoney", ""));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
	public Product getProductByName(String name){
		Product result = null;
		String sql = "select * from tProduct where fName='"+name+"' and fstate='0'";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				result = new Product();
				result.setFid(fc.getrv(rs, "fID", ""));
				result.setFname(fc.getrv(rs, "fName", ""));
				result.setFnum(rs.getInt("fNum"));
				result.setFstate(fc.getrv(rs, "fState", ""));
				result.setFpayMoney(fc.getrv(rs, "fPayMoney", ""));
				result.setFadMoney(fc.getrv(rs, "fAdMoney", ""));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
	/**
	 * 
	 * 方法名称: getAllProduct 
	 * 方法描述: 获取所有可用商品
	 * 创建人: renfy
	 * 创建时间: 2012-1-4 下午12:12:25
	 * @return
	 * @version 1.0
	 *
	 */
	public List<Product> getAllProduct(){
		List<Product> result = new ArrayList<Product>();
		String sql = "select top 2 * from tProduct where fstate='0' order by fNum asc";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			Product pro = null;
			while(rs != null && rs.next()){
				pro = new Product();
				pro.setFid(fc.getrv(rs, "fID", ""));
				pro.setFname(fc.getrv(rs, "fName", ""));
				pro.setFnum(rs.getInt("fNum"));
				pro.setFstate(fc.getrv(rs, "fState", ""));
				pro.setFpayMoney(fc.getrv(rs, "fPayMoney", ""));
				pro.setFadMoney(fc.getrv(rs, "fAdMoney", ""));
				result.add(pro);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
	/**
	 * 
	 * 方法名称: getAllProCharType 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2012-1-4 下午12:58:44
	 * @param fProductID
	 * @return
	 * @version 1.0
	 *
	 */
	public List<CharType> getAllProCharType(String fProductID){
		List<CharType> result = new ArrayList<CharType>();
		String sql = "select * from tCharType where fState='0' and fID in (select fCharTypeID from tProductCharType where fProductID='"+fProductID+"' and fState='0')";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			CharType ct = null;
			while(rs != null && rs.next()){
				ct = new CharType();
				ct.setFid(fc.getrv(rs, "fID", ""));
				ct.setFname(fc.getrv(rs, "fName", ""));
				ct.setFstate(fc.getrv(rs, "fState", ""));
				result.add(ct);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}

	
	public List<BGPicture> getAllProBGPicture(String fProductID){
		List<BGPicture> result = new ArrayList<BGPicture>();
		String sql = "select * from tBGPicture where fState='0' and fID in (select fBGPicture from tProductBGPicture where fProductID='"+fProductID+"' and  fState='0')";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			BGPicture bgp = null;
			while(rs != null && rs.next()){
				bgp = new BGPicture();
				bgp.setFid(fc.getrv(rs, "fID", ""));
				bgp.setFname(fc.getrv(rs, "fName", ""));
				bgp.setFstate(fc.getrv(rs, "fState", ""));
				result.add(bgp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
}
