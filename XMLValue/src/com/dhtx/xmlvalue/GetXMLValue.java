package com.dhtx.xmlvalue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GetXMLValue {

	public static void main(String[] args) throws Exception {
		
		// create a new file
		FileOutputStream fileOut = new FileOutputStream("d:/workbook.xls");
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet sheet = wb.createSheet();

		
		
		File inputXml = new File("d:/19payprodid.xml");
		SAXReader saxReader = new SAXReader();  
		Document document = saxReader.read(inputXml);
		Element rootEle = document.getRootElement();
		Iterator iter = rootEle.elementIterator();
		
		int rowNum = 0;
		while(iter.hasNext()){
			Row row = sheet.createRow(rowNum);
			
			StringBuffer littleRS = new StringBuffer();
			Element products = (Element) iter.next();
			Iterator iter2 = products.elementIterator();
			int cellNum = 0;
			while(iter2.hasNext()){
				Cell cell = row.createCell(cellNum);
//			    cell.setCellValue(new Date());
				Element product = (Element) iter2.next();
				String name = product.attributeValue("name");
				String value = product.attributeValue("value");
				cell.setCellValue(value);
				littleRS.append(name + "=" + value + ",");
			cellNum++;
			}
			System.out.println(littleRS);
			
			
			
			rowNum++;
		}
		
		
		 wb.write(fileOut);
		    fileOut.close();
System.out.println(rowNum);
	}
}
