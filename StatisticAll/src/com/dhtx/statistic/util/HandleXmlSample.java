package com.dhtx.statistic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class HandleXmlSample {
	/**
	 * 建立一个XML文档,文档名由输入属性决定,文档内容在内部由initialDocument（Document document）进行初始化
	 *
	 * @param filename
	 *            需建立的文件名,如"test.xml",可以加路径
	 * @return返回操作结果, 0表失败, 1表成功
	 */
	public int createXMLFile(String filename) {
		// 返回操作结果, 0表失败, 1表成功
		int returnValue = 0;
		// 建立document对象
		Document document = DocumentHelper.createDocument();
		// 对document初始化，加入xml所需要的内容节点
		document = initialDocument(document);
		try {
			// 对document格式化输出到指定名称文件, 格式化后，使xml符合节点缩进样式
			formatXMLOutput(document, filename);
			// 输出正确，修改返回值为1
			returnValue = 1;
		} catch (Exception e) {
			System.out.println("格式化输出文件出错！");
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * <b>方法描述</b>： 创建xml字符串,文档内容在内部由initialDocument（Document document）进行初始化
	 * <p>
	 * <b>方法流程</b>：
	 * <p>
	 *
	 * @return
	 */
	public String createXMLStr() {
		// 建立document对象
		Document document = DocumentHelper.createDocument();
		// 对document初始化，加入xml所需要的内容节点
		document = initialDocument(document);
		return document.asXML();
	}

	/**
	 * <b>方法描述</b>： 初始化document对象
	 * <p>
	 * <b>方法流程</b>：
	 * <p>
	 *
	 * @return 返回初始化完成对象
	 */

	private Document initialDocument(Document document) {

		/** 建立XML文档的根books */
		Element booksElement = document.addElement("versions");
		/** 加入一行注释 */
//		booksElement.addComment(" 存放各功能的版本信息 ");
//		/** 加入第一个book节点 */
//		Element bookElement = booksElement.addElement("book");
//		/** 加入show属性内容 */
//		bookElement.addAttribute("show", "yes");
//		/** 加入title节点 */
//		Element titleElement = bookElement.addElement("title");
//		/** 为title设置内容 */
//		titleElement.setText("java程序设计");
//
//		/** 类似的完成后两个book */
//		bookElement = booksElement.addElement("book");
//		bookElement.addAttribute("show", "yes");
//		titleElement = bookElement.addElement("title");
//		titleElement.setText("java编程思想");
//		bookElement = booksElement.addElement("book");
//		bookElement.addAttribute("show", "no");
//		titleElement = bookElement.addElement("title");
//		titleElement.setText("Head First 设计模式");

//		/** 加入owner节点 */
//		Element ownerElement = booksElement.addElement("owner");
//		ownerElement.setText("vcom11");
		return document;
	}

	/**
	 * <b>方法描述</b>： 格式化xml输出格式，指定输出文件名、采用编码格式
	 * <p>
	 * <b>方法流程</b>：
	 * <p>
	 *
	 * @param document
	 * @param filename
	 *            文件名
	 * @param encoding
	 *            编码格式
	 * @throws Exception
	 */
	private void formatXMLOutput(Document document, String filename)
			throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 默认为Utf-8 编码，可以根据需要改变编码格式
		// format.setEncoding("GBK");
		/** 将document中的内容写入文件中 */
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(filename)), format);
		writer.write(document);
		writer.close();
	}

	/**
	 * 修改XML文件中内容,并另存为一个新文件 重点掌握dom4j中如何添加节点,修改节点,删除节点
	 *
	 * @param filename
	 *            修改对象文件
	 * @param newfilename
	 *            修改后另存为该文件
	 * @return 返回操作结果, 0表失败, 1表成功
	 */
	public int modiXMLFile(String filename, String newFilename) {
		int returnValue = 0;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new java.io.File(filename));
			/** 修改内容之一: 如果book节点中show属性的内容为yes,则修改成no */
			/** 先用xpath查找对象 */
			List list = document.selectNodes("/versions");
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getValue().equals("yes")) {
					attribute.setValue("no");
				}
			}

			/**
			 * 修改内容之二: 把owner项内容改为"zzvcom"
			 * 并在owner节点中加入date节点,date节点的内容为2009-10-22,还为date节点添加一个属性type
			 */
			list = document.selectNodes("/versions");
			iter = list.iterator();
			if (iter.hasNext()) {
				Element ownerElement = (Element) iter.next();
				ownerElement.setText("测试修改");
				Element dateElement = ownerElement.addElement("date");
				dateElement.setText("2009-10-22");
				dateElement.addAttribute("type", "日期");
			}

			/** 修改内容之三: 若title内容为"Head First 设计模式",则删除该节点 */
			list = document.selectNodes("/books/book");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element bookElement = (Element) iter.next();
				Iterator iterator = bookElement.elementIterator("title");
				while (iterator.hasNext()) {
					Element titleElement = (Element) iterator.next();
					if (titleElement.getText().equals("Head First 设计模式")) {
						bookElement.remove(titleElement);
					}
				}
			}

			formatXMLOutput(document, newFilename);
			/** 执行成功,需返回1 */
			returnValue = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	public void addElement(String filename,String name,String value){
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new java.io.File(filename));
			Element rootElement = document.getRootElement();
			Element e = rootElement.element(name);
			//检验是否存在此节点
			if(e != null){
				rootElement.element(name).setText(value);
			}else{
				Element dateElement = rootElement.addElement(name);
				dateElement.setText(value);
//				dateElement.addAttribute("type", "日期");
			}
			formatXMLOutput(document, filename);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateElement(String filename,String name,String value){
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new java.io.File(filename));
			Element rootElement = document.getRootElement();
			rootElement.element(name).setText(value);
			formatXMLOutput(document, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getElement(String filename,String name){
		String value = null;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new java.io.File(filename));
			Element rootElement = document.getRootElement();
			Element e = rootElement.element(name);
			if(e != null){
				value = e.getText();
//				formatXMLOutput(document, filename);
			}
			return value;
		} catch (Exception e) {
			System.out.println("  查找节点异常,返回此节点不存在  ");
			return null;
		}
	}

	/**
	 * <b>方法描述</b>： 得到xml字符串中owner节点的值
	 * <p>
	 * <b>方法流程</b>：
	 * <p>
	 *
	 * @param xmlStr
	 * @return
	 */
	public String getOwnerValue(String xmlStr) {
		Document document;
		try {
			document = DocumentHelper.parseText(xmlStr);
			Element rootElement = document.getRootElement();
			return rootElement.element("owner").getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <b>方法描述</b>： 得到xml字符串中book节点的值的集合
	 * <p>
	 * <b>方法流程</b>：
	 * <p>
	 *
	 * @param xmlStr
	 * @return
	 */
	public List getBookListValue(String xmlStr) {
		Document document;
		List list = new ArrayList();
		try {
			document = DocumentHelper.parseText(xmlStr);
			Element rootElement = document.getRootElement();
			List<Element> elementList = rootElement.elements("book");
			for (Element element : elementList) {
				list.add(element.elementText("title"));
			}
			return list;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

}
