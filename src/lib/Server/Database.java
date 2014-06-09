/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Server;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Arden
 */
public class Database {
    public String filePath;
    
    public Database (String filePath) {
	this.filePath = filePath;
    }
    
    public boolean userExists (String username) {
	try {
	    File fXmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    NodeList nodeList = doc.getElementsByTagName("user");
	    
	    for (int i=0; i<nodeList.getLength(); i++) {
		Node nNode = nodeList.item(i);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    Element e = (Element) nNode;
		    //if (e.getElementsByTagName("username").item(0).getTextContent().equals(username))
		    if (getTagValue("username", e).equals(username))
			return true;
		}
	    }
	    return false;
	}
	catch (ParserConfigurationException | SAXException | IOException ex) {
	    System.out.println("Database exception : userExists()");
	    return false;
	}
    }

    private Object getTagValue(String sTag, Element e) {
	NodeList nodeList = e.getElementsByTagName(sTag).item(0).getChildNodes();
	Node nValue = (Node)nodeList.item(0);
	return nValue.getNodeValue();
    }
    
    public boolean checkLogin (String username, String password) {
	if (!userExists(username))
	    return false;
	try {
	    File fXmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    NodeList nodeList = doc.getElementsByTagName("user");
	    
	    for (int i=0; i<nodeList.getLength(); i++) {
		Node nNode = nodeList.item(i);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    Element e = (Element)nNode;
		    if (getTagValue("username", e).equals(username) || getTagValue(("password"), e).equals(password))
			return true;
		}
	    }
	    return false;
	} catch (ParserConfigurationException | SAXException | IOException ex) {
	    System.out.println("Database exception : userExist()");
	    return false;
	}
    }
    /*
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
 
            Node data = doc.getFirstChild();
            
            Element newuser = doc.createElement("user");
            Element newusername = doc.createElement("username"); newusername.setTextContent(username);
            Element newpassword = doc.createElement("password"); newpassword.setTextContent(password);
            
            newuser.appendChild(newusername); newuser.appendChild(newpassword); data.appendChild(newuser);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
    */
    public void addUser (String username, String password) {
	try {
	    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.parse(filePath);
	    
	    Node data = doc.getFirstChild();
	    
	    Element newuser = doc.createElement("user");
	    Element newusername = doc.createElement("username");    newusername.setTextContent(username);
	    Element newpassword = doc.createElement("password");    newpassword.setTextContent(password);
	    
	    newuser.appendChild(newusername);
	    newuser.appendChild(newpassword);
	    data.appendChild(newuser);
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File(filePath));
	    transformer.transform(source, result);
	}
	catch (Exception ex) {
	    System.out.println("Exceptionmodify xml");
	}
    }
}