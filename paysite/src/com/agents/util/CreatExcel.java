package com.agents.util;
import java.io.*; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.*; 
import jxl.format.UnderlineStyle;
import jxl.write.*; 
public class CreatExcel 
{ 
	public String createExcel(String path,String fieldName,List <String[]> data){
		String basePath=Config.getWebAppPath();
		try   
		{ 
		String filePath=basePath+"/数据.xls";
		   /**
		   * 定义单元格样式
		   */
		   WritableFont wf = new WritableFont(WritableFont.ARIAL, 8,
		     WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
		     jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		   WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		   wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
  
		WritableWorkbook book= 
		Workbook.createWorkbook(new File(filePath));       
		//生成名为“第一页”的工作表，参数0表示这是第一页 
		WritableSheet sheet=book.createSheet("第一页",0); 
		//在Label对象的构造子中指名单元格位置是第一列第一行(0,0) 
		//以及单元格内容为test   
		//输出表头   
		
		String fieldNameArr[]=fieldName.split(",");
		for (int i = 0; i < fieldNameArr.length; i++) {
			Label label=new Label(i,0,fieldNameArr[i],wcf);
			sheet.setColumnView(i, 20);
			sheet.addCell(label);     
		}    
		sheet.setRowView(0, 550);       
		for (int j = 0; j < data.size(); j++) {
			String dataArr[]=data.get(j);
			for (int k = 0; k < dataArr.length; k++) {  
				Label label=new Label(k,j+1,dataArr[k]);
				sheet.addCell(label);   
			}
			sheet.setRowView(j+1, 400);   //设置行高   
		}
		//jxl.write.Number number = new jxl.write.Number(1,0,789.123); 
		//sheet.addCell(number); 
		//写入数据并关闭文件 
		book.write(); 
		book.close(); 
		}catch(Exception e) 
		{ 
		System.out.println(e); 
		} 
		return "true";
	}
public static void main(String args[]) 
{ 
    CreatExcel ce=new CreatExcel();
    String a="1,2,3,4";
    String b="a,b,c,d";
    String c="e,f,g,h";
    List <String[]> list=new ArrayList<String[]>();
    list.add(b.split(","));
    list.add(c.split(","));
  // String aa= ce.createExcel(a, list);
} 
} 
