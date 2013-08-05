package com.jinrl.powermodule.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinrl.powermodule.common.DataConnect;
import com.jinrl.powermodule.common.fc;
import com.jinrl.powermodule.dao.TstatisticDAO;
import com.jinrl.powermodule.pojo.Tstatistic;

public class ExcuteStatistic extends TimerTask {
	 private static int start = 0;
	TstatisticDAO sdao = null;
	public ExcuteStatistic(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		sdao= (TstatisticDAO) context.getBean("statisticDAO");
	}

	public void run() {
		if(start != 0) return;
		start = 1;
		//
		Date dat = new Date();
		String sFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		String time = sdf.format(dat).substring(14);
		int mm = Integer.parseInt(time.substring(3));
		if(time.equals("00:00") || mm%3==0){
		//
		List<Tstatistic> list = sdao.findAll();
		for(Tstatistic s : list){
			try {
				String currIP = InetAddress.getLocalHost().getHostAddress();
				if((!"*".equals(s.getFip()) && !currIP.equals(s.getFip())) || "".equals(s.getFip()) || s.getFip() == null){
					continue;
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			fc.message(" 统计: " + s.getFdescription());

			Timestamp tt = s.getFbeforeTime();
			//上次的时间
			long before = tt.getTime();
			//当前时间
			long now = dat.getTime();
			long refreshTime = s.getFrefreshTime() * 1000L;
			if((now-before) >= refreshTime){
				// 连接数据源中所指定的数据库
				DataConnect dc = new DataConnect(s.getFconnDatabase(), false);
				ResultSet rs = dc.query(s.getFsql());

				if(s.getFgroupbyField()!=null && !"".equals(s.getFgroupbyField())){

					//使用groupby 返回多条记录
					try{
//						List<String> listField = new ArrayList<String>();
//						ResultSetMetaData rsmd = rs.getMetaData();
//						   int count = rsmd.getColumnCount();
//						   for (int j = 1; j <= count; j++) {
//						     String cellName= rsmd.getColumnName(j);
//						     listField.add(cellName);
//						   }

						//
						StringBuffer sbr = new StringBuffer();
						while(rs.next()){
							if (rs.getRow() != 1) sbr.append(";");
							String sss = "";
							//值
							String t1 = s.getFwarnField();
							String[] taa = t1.split(",");
							for(int j = 0; j<taa.length; j++){
								//分类
								String ss = "";
								String s1 = s.getFgroupbyField();
								String[] saa = s1.split(",");
								for(int i = 0; i<saa.length; i++){
									String sv = fc.getrv(rs, saa[i], "");
									ss = ss + "[" + sv + "]";
								}
								ss = ss + "[" + taa[j] + "]=" + fc.getrv(rs, taa[j], "");
								sss = sss + " , " + ss.substring(0);
							}
//
//							values.append(";");
//							for(String sf : listField){
//								values.append(rs.getString(sf) + "_");
//							}
							//值
							sbr.append(sss.substring(2));
						}


						Timestamp t = new Timestamp(this.getBeforeTime(s, dat));
						s.setFbeforeValue(sbr.substring(1).toString());
						s.setFbeforeTime(t);
						sdao.update(s);
						rs.close();
						dc.CloseConnect();
					}catch(Exception e){
						String newValue = "统计失败";
						Timestamp t = new Timestamp(this.getBeforeTime(s, dat));
						s.setFbeforeValue(newValue);
						s.setFbeforeTime(t);
						sdao.update(s);
					}

				}else{
					//普通情况，单条返回值
					try{
						if(rs.next()){
							String newValue = rs.getString(s.getFwarnField());
							Timestamp t = new Timestamp(this.getBeforeTime(s, dat));
							//System.out.println("beforeTime:"+this.getBeforeTime(s, dat));
							s.setFbeforeValue(newValue);
							s.setFbeforeTime(t);
							sdao.update(s);
						}
						rs.close();
						dc.CloseConnect();
					}catch(Exception e){
						String newValue = "统计失败";
						Timestamp t = new Timestamp(this.getBeforeTime(s, dat));
						s.setFbeforeValue(newValue);
						s.setFbeforeTime(t);
						sdao.update(s);
					}
				}

			}

		}
		}
		start = 0;
	}


	private Long getBeforeTime(Tstatistic s,Date date){
		long before = s.getFbeforeTime().getTime();
		long now  = date.getTime();
		long time = s.getFrefreshTime() * 1000L;  //刷新的时间
		long n = (now - before) / time;    // l 表示间隔了n个时间段
		long result = n * time + before;
		return result;
	}
}
