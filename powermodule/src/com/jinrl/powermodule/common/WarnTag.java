package com.jinrl.powermodule.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jinrl.powermodule.dao.TstatisticDAO;
import com.jinrl.powermodule.pojo.Tstatistic;

public class WarnTag extends TagSupport {

	String warns = "";
	public String getWarns() {
		return warns;
	}
	public void setWarns(String warns) {
		this.warns = warns;
	}
	@Override
	public int doEndTag() throws JspException {

		TstatisticDAO stDao = new TstatisticDAO();
		// 此部分是现实预警信息的
		List<Tstatistic> stlist = stDao.finShowStatistic();

		//存放最后结果
		List<String[]> stresult = new ArrayList<String[]>();


		for(Tstatistic s : stlist){
			//String[0]='显示字',String[1]='显示的值',String[2]='是否显示成红字'
			String[] warns = new String[3];
			warns[0]=s.getFdescription();
			warns[1]=s.getFbeforeValue();

			//
			String warnValue = s.getFwarnValue();
			String nowValue = s.getFbeforeValue();
			String warnType = s.getFwarnType();
			//对应比较方式，分开比较
			if("<".equals(warnType)){
				try{
					//放到try catch 里面，是为了当数据库中数据不正常时，发生异常，跳过此行记录，不影响后续的结果显示
					if(Float.valueOf(nowValue) < Float.valueOf(warnValue)){
						//标志成红色
						warns[2]="red";
						stresult.add(warns);
					}
					else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误<");
				}
			}else
			if(">".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) > Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误>");
				}
			}else
			if("=".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) == Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误=");
				}
			}else
			if("<>".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) != Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误<>");
				}
			}else
			if("@".equals(warnType)){
				try{
					if(nowValue.indexOf(warnValue) != -1){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误@");
				}
			}

		}


		//配接字符串，用于页面显示，调用此自定义标签
		StringBuffer sb = new StringBuffer();
		for(String[] s : stresult){
			if(!"red".equals(s[2])){
				sb.append("&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;");
				sb.append(s[0]+"("+s[1]+")");

			}else{
				sb.append("&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;");
				sb.append(s[0] + "(");
				sb.append("<span style='color: red'>");
				sb.append(s[1]);
				sb.append("</span>");
				sb.append(")");

			}
		}
		try {
			if(sb.length() > 18){
				sb = new StringBuffer(sb.substring(18));
			}
			//输出到页面
			pageContext.getOut().print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.EVAL_PAGE;
	}

}
