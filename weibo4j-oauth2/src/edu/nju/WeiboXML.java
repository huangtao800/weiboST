package edu.nju;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeiboXML {

	public static ArrayList<WeiboStatus> getWeiboStatusList(String xml) {
		File file = new File("weiboXML.xml");
		FileWriter fWriter;
		try {
			fWriter = new FileWriter(file);
			fWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			fWriter.write(xml);
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ArrayList<WeiboStatus> resultList = new ArrayList<WeiboStatus>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(new File("weiboXML.xml"));
			NodeList infolist = document.getElementsByTagName("info");
			System.out.println(infolist.getLength());
			for (int i = 0; i < infolist.getLength(); i++) {
				Element currentElement = (Element) infolist.item(i);
				Element parentElement = (Element) currentElement
						.getParentNode();
				if (!parentElement.getNodeName().equals("data")) {
					continue;
				}

				Node textNode = currentElement.getElementsByTagName("origtext")
						.item(0);
				String text = "";
				if (textNode != null) {
					text = textNode.getTextContent();
				}
				
				String id = currentElement.getElementsByTagName("id").item(0)
						.getTextContent();
				
				String timestamp=currentElement.getElementsByTagName("timestamp").item(0).getTextContent();
				WeiboStatus weiboStatus = new WeiboStatus(id, text,"t");
				weiboStatus.setTimestamp(timestamp);
				resultList.add(weiboStatus);
				file.delete();
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultList;
	}

}
