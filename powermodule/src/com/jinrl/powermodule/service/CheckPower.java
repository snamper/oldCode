package com.jinrl.powermodule.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jinrl.powermodule.common.Tools;
import com.jinrl.powermodule.dao.TfunctionDAO;
import com.jinrl.powermodule.dao.TmoduletypeDAO;
import com.jinrl.powermodule.dao.TpowermanageDAO;
import com.jinrl.powermodule.dao.TuserDAO;
import com.jinrl.powermodule.pojo.Tfunction;
import com.jinrl.powermodule.pojo.Tmoduletype;
import com.jinrl.powermodule.pojo.Tposition;
import com.jinrl.powermodule.pojo.Tpositionfunction;
import com.jinrl.powermodule.pojo.Tpositionuser;
import com.jinrl.powermodule.pojo.Tpowermanage;
import com.jinrl.powermodule.pojo.Tuser;

public class CheckPower {
	TuserDAO userDao;
	TfunctionDAO fDao;
	TmoduletypeDAO mDao;
	TpowermanageDAO pmDao;

	public TpowermanageDAO getPmDao() {
		return pmDao;
	}

	public void setPmDao(TpowermanageDAO pmDao) {
		this.pmDao = pmDao;
	}

	public TmoduletypeDAO getmDao() {
		return mDao;
	}

	public void setmDao(TmoduletypeDAO mDao) {
		this.mDao = mDao;
	}

	public TuserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(TuserDAO userDao) {
		this.userDao = userDao;
	}

	public TfunctionDAO getfDao() {
		return fDao;
	}

	public void setfDao(TfunctionDAO fDao) {
		this.fDao = fDao;
	}

	public Tuser getUserById(String uesrid){
		Tuser user = userDao.findById(uesrid);
		return Tools.GetisusedValue(user)?user:null;
	}
	/**
	 *	检验是否登录成功
	 * @param userid
	 * @param password
	 * @param positionids
	 * @param request
	 * @return
	 */
	public  int isLogined(String userid, String password,HttpServletRequest request) {
		//System.out.println("IP:"+request.getRemoteAddr());
		int a=0;//正常情况，可以登录
		Tuser user =userDao.isLogin(userid, password);
		if(user != null){
			String allowips = user.getAllowip();
			if(allowips == null || allowips.indexOf("#") != -1){
				a=2;//用户名密码正确，但需要USB-KEY验证
			}else{
				if(allowips.indexOf("*") == -1){
					String[] as=allowips.split(",");
					int b=0;
					for(String ip : as){
						if(ip.equals(request.getRemoteAddr())){
							b=1;
							break;
						}
					}
					if(b==0){
						a=3;//用户名密码正确,但IP地址错误
					}
				}
			}
		}else{
			a=1;//用户名密码错误
		}
		if(a==0 || a==2 || a==3){
			request.getSession().setAttribute("currentUserid", userid);
		}
		return a;
	}

//专用于email检验
	public  boolean isLogined2(String userid, String password,HttpServletRequest request) {
		//System.out.println("IP:"+request.getRemoteAddr());
		Tuser user =userDao.isLogin(userid, password);
		if(user !=null)
		request.getSession().setAttribute("currentUserid", userid);
		 return (user==null) ?  false :   true;
	}
	/**
	 * 检验当前用户是否有权限查看
	 * @param currentUserid
	 * @param functionid
	 * @param defaulturl
	 * @param request
	 * @return
	 */
	public  String isallowed(String currentUserid, String functionid,
			String defaulturl, HttpServletRequest request) {
		if (null == defaulturl) {
			defaulturl = "";
		}
		if (null == functionid)  {
			functionid = "";
		}
		if (null == currentUserid) {
			currentUserid = "";
		}
		if(null == request.getSession().getAttribute("currentUserid")){
			return defaulturl;
		}
		String userid = (String) request.getSession().getAttribute("currentUserid");
		if (!currentUserid.equals(userid)) {
			return defaulturl;
		}

		Tfunction f = fDao.findById(functionid);
		if (null == f) {
			// 添加一条记录

			//增加功能表(此功能没有使用)
//			Tfunction func = new Tfunction();
//			func.setFid(functionid);
//			func.setFisused("1");
//			TfunctionDAO funDao = new TfunctionDAO();
//			funDao.save(func);
//			//
//			Tuser user = userDao.findById(userid);
//			Set<Tpositionuser> setpu = user.getTpositionusers();
//			List<Tpositionuser> listpu = new ArrayList<Tpositionuser>(setpu);
//			for(int r=0;r<listpu.size();r++){
//				listpu.get(r).getTposition();
//			}

			return defaulturl;
		}

		//
		if (!Tools.GetisusedValue(f)) {
			return defaulturl;
		}
		List<Tfunction> listurl = geturl2(currentUserid);

		if("0".equals(f.getFcommon())){
			if(f.getFbusiinfoid() == null || "".equals(f.getFbusiinfoid())){
				return f.getFfunctionrul();
			}else{
				return "/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID="+f.getFbusiinfoid();
			}

		}
		for(Tfunction func : listurl){
			if(func.getFid().equals(f.getFid())){
				if(f.getFbusiinfoid() == null || "".equals(f.getFbusiinfoid())){
					return f.getFfunctionrul();
				}else{
					return "/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID="+f.getFbusiinfoid();
				}
			}
		}
		return defaulturl;

	}

	/**
	 * 根据ID获得系统模块ID
	 * @param fid
	 * @return
	 */
	public String getSystemId(String fid){
		Tfunction f = fDao.findById(fid);
		String sysid = f.getFsystemid();
		if(null==sysid){sysid="0";}
		return sysid;
	}

	public Tfunction findByFunctionID(String functionID){
		return fDao.findById(functionID);
	}

	public Tfunction findByBusiInfoID(String busiINfoID){
		return fDao.findByBusiInfoID(busiINfoID);
	}


	/**
	 * 获得当前用户所有可用的功能集合
	 * @param currentUserid 当前用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<Tfunction> geturl2(String currentUserid) {
		Tuser user = userDao.findById(currentUserid);
		if(!Tools.GetisusedValue(user)){return null;}
		//存放岗位集合
		List<Tposition> listp = new ArrayList<Tposition>();
		//遍历岗位人员表
		for(Iterator<Tpositionuser> it = user.getTpositionusers().iterator();it.hasNext();){
			Tpositionuser pu = it.next();
			if(Tools.GetisusedValue(pu)){
				Tposition p = pu.getTposition();
				if(Tools.GetisusedValue(p)){
					listp.add(p);
				}
			}
		}
		//现在得到了一个可用的岗位集合对象

		//存放最终结果
		List<Tfunction> listf = new ArrayList<Tfunction>();
		//开始遍历岗位集合
		for(Tposition po :listp){
			for(Iterator<Tpositionfunction> ite = po.getTpositionfunctions().iterator();ite.hasNext();){
				Tpositionfunction pf = ite.next();
				if(Tools.GetisusedValue(pf)){
					Tfunction f = pf.getTfunction();
					if(Tools.GetisusedValue(f)){
						listf.add(f);
					}
				}
			}
		}
//		//删除重复的
//		List l =Tools.removeDuplicateFunction(listf);
		return listf;
	}

	/**
	 * 获得当前用户的所有可用岗位
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Tposition> getPositionsByuser2(Tuser user){
		if(!Tools.GetisusedValue(user)){return null;}
		//存放岗位集合
		List<Tposition> listp = new ArrayList<Tposition>();
		//遍历岗位人员表
		for(Iterator<Tpositionuser> it = user.getTpositionusers().iterator();it.hasNext();){
			Tpositionuser pu = it.next();
			if(Tools.GetisusedValue(pu)){
				Tposition p = pu.getTposition();
				if(Tools.GetisusedValue(p)){
					listp.add(p);
				}
			}
		}
		//现在得到了一个可用的岗位集合对象
		List<Tposition> l = Tools.removeDuplicatePosition(listp);
		return l;
	}

	/*
	 * 得到所有可用的模块
	 */
	public List<Tmoduletype> findAllmt(Set systemset){
		return mDao.findAllisused(systemset);
	}

	public List<Tpowermanage> findpowerManages(String positionid,String functionid){
		return pmDao.findpowerManages(positionid, functionid);
	}

	public List<Tpowermanage> findFieldpower(String busiInfoid,List position){
		return pmDao.findFieldpower(busiInfoid,position);
	}

	public String getEmail(String userid){
		return userDao.getEmail(userid);
	}

	public List<Tfunction> findAllCommonFuntions(){
		return fDao.findAllCommonFuntions();
	}

	public List<Tfunction> findAllFunByUserId(String userid){
		return fDao.findAllFunByUserId(userid);
	}

	public Tmoduletype findMtById(Integer fid){
		return mDao.findById(fid);
	}

}
