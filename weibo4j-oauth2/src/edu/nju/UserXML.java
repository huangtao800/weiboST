package edu.nju;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UserXML {

	public UserXML(String xml) {
		File file = new File("userXML.xml");

		FileWriter fWriter;
		try {
			fWriter = new FileWriter(file);
			fWriter.write("<?xml version=\"1.0\" encoding=\"gbk\"?>");
			fWriter.write(xml);
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public User getCount() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		User user = new User();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File("userXML.xml"));

			NodeList list = document.getElementsByTagName("tweetnum");
			NodeList list2 = document.getElementsByTagName("name");

			Element element = (Element) list.item(0);
			Element element2 = (Element) list2.item(0);

			String tweetnum = element.getTextContent();
			String name = element2.getTextContent();
			
			if (tweetnum.equals("")) {
				user.setTweetnum(0);
			} else {
				user.setTweetnum(Integer.parseInt(tweetnum));
			}
			user.setName("");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}

}
