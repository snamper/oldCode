package com.jinrl.powermodule.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinrl.powermodule.common.fc;
import com.jinrl.powermodule.dao.TstatisticDAO;
import com.jinrl.powermodule.pojo.Tstatistic;

public class SendWarnEmail extends TimerTask {
	private static int start = 0;
	TstatisticDAO sdao = null;
	public SendWarnEmail(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		sdao= (TstatisticDAO) context.getBean("statisticDAO");
	}

	@Override
	public void run() {
		if(start != 0){return;}
		start = 1;
		Date date = new Date();
//		String sFormat = "yyyy-MM-dd HH:mm:ss";
//		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
//		String time = sdf.format(date).substring(14);
//		String mm = time.substring(3);
//		if(mm.equals("00")){

		List<Tstatistic> list = sdao.findAll();
		for(Tstatistic s : list){
			if(null != s.getFwarnEmail() && !"".equals(s.getFwarnEmail()) && this.checkWarn(s) && this.chackAgainSendTime(s,date)){
				String[] emails = s.getFwarnEmail().split(",");
				for(int i = 0; i < emails.length; i++){
					if(emails[i].indexOf("@") != -1){
						String to_title = "";
						if(s.getFgroupbyField() == null || "".equals(s.getFgroupbyField())){
							to_title = "报警:"+s.getFdescription();
						}else{
							to_title = "报告:"+s.getFdescription();
						}
						String info = s.getFwarnInfo();
						if(info==null){info="";}
						String to_content = fc.replace(info, "[T]", s.getFbeforeValue());
						to_content = fc.replace(to_content, ";", "\n");
						fc.message(to_content);
						fc.sendEmail(emails[i], to_title, to_content);
					}
				}
				s.setFbeforeSendEmail(new Timestamp(this.getSaveTime(s, date)));
				fc.message("SendEmail:"+this.getSaveTime(s, date));
				sdao.update(s);
			}
		}
//		}
		start = 0;
	}

	//此方法用来检验是否达到报警条件
	private boolean checkWarn(Tstatistic s){
//		Tstatistic s  = sdao.findById(fid);
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
					return true;
				}
			}catch(Exception e){
				//System.out.println("表中数据错误");
			}
		}else
		if(">".equals(warnType)){
			try{
				if(Float.valueOf(nowValue) > Float.valueOf(warnValue)){
					return true;
				}
			}catch(Exception e){
				//System.out.println("表中数据错误");
			}
		}else
		if("=".equals(warnType)){
			try{
				if(Float.valueOf(nowValue) == Float.valueOf(warnValue)){
					return true;
				}
			}catch(Exception e){
				//System.out.println("表中数据错误");
			}
		}else
		if("<>".equals(warnType)){
			try{
				if(Float.valueOf(nowValue) != Float.valueOf(warnValue)){
					return true;
				}
			}catch(Exception e){
				//System.out.println("表中数据错误");
			}
		}else
		if("@".equals(warnType)){
			try{
				if(nowValue.indexOf(warnValue) != -1){
					return true;
				}
			}catch(Exception e){
				//System.out.println("表中数据错误");
			}
		}
		return false;


	}

	//检验是否达到了再次发送邮件的条件
	private boolean chackAgainSendTime(Tstatistic s, Date date){
//		Tstatistic s  = sdao.findById(fid);
		try{
			long before = s.getFbeforeSendEmail().getTime();
			long now  = date.getTime();
			if((now - before) >= s.getFsendEmailTime() * 60 * 1000){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}

	private Long getSaveTime(Tstatistic s,Date date){
		long before = s.getFbeforeSendEmail().getTime();
		long now  = date.getTime();
		long time = s.getFsendEmailTime() * 60 * 1000L;  //间隔的时间
		long n = (now - before) / time;    // l 表示间隔了n个时间段
		long result = n * time + before;
		return result;
	}

}
