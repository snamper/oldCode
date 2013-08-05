package com.agents.service;

import java.util.List;

import com.agents.dao.FillCardDAO;
import com.agents.dao.SelectCardInfoDao;
import com.agents.pojo.FillCard;

public class FillCardService {

	private FillCardDAO fillCardDao = new FillCardDAO();
	private static final int pageSize = 15;

	public void setFillCardDao(FillCardDAO fillCardDao) {
		this.fillCardDao = fillCardDao;
	}

	public List<String[]> findFillCards(String datatime,String selectItemValue,String selectItem,String fcardid,String nowPage,String agentID){
		if("".equals(selectItemValue) && "请输入卡号！".equals(fcardid)){
			return null;
		}
		int np;
		try{
			np = Integer.parseInt(nowPage);
		}catch(Exception  e){
			np = 1;
		}
		np = (np - 1) * pageSize;

		if("请输入卡号！".equals(fcardid)){
			fcardid = "";
		}
		return fillCardDao.findFillCards(datatime,selectItemValue,selectItem,fcardid,np,agentID);
	}

	public FillCard findById(String id){
		return new SelectCardInfoDao().findById(id);
	}
}
