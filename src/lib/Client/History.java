/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Client;

import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lib.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Arden
 */
public class History {
    public String filePath;
    
    public History (String filePath) {
	this.filePath = filePath;
    }
    
    public void addMessage (Message msg, String Time) {
	try {
	    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.parse(filePath);
	    
	    Node data = doc.getFirstChild();
	    
	    Element message = doc.createElement("message");
	    Element sender = doc.createElement("sender");
	    sender.setTextContent(msg.sender);
	    Element content = doc.createElement("content");
	    content.setTextContent(msg.content);
	    Element recipient = doc.createElement("recipient");
	    recipient.setTextContent(msg.recipient);
	    Element time = doc.createElement("time");
	    time.setTextContent(Time);
	    
	    message.appendChild(sender);
	    message.appendChild(content);
	    message.appendChild(recipient);
	    message.appendChild(time);
	    data.appendChild(message);
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File(filePath));
	    transformer.transform(source, result);
	} 
	catch (ParserConfigurationException | SAXException | IOException | TransformerConfigurationException ex) {
	    System.out.println("Exceptionmodify xml");
	}
	catch (TransformerException ex) {
	    System.out.println("Exceptionmodify xml");
	}
    }
    
    public void fillTable (HistoryFrame frame) {
	DefaultTableModel Model = (DefaultTableModel)frame.jtblHistory.getModel();
	
	try {
	    File fXmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    NodeList nList = doc.getElementsByTagName("message");
	    
	    for (int i = 0; i < nList.getLength(); i++) {
		Node nNode = nList.item(i);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    Element e = (Element)nNode;
		    Model.addRow(new Object[] {
			getTagValue("sender", e), getTagValue("content", e), getTagValue("recipient", e), getTagValue("time", e)
		    });
		}
	    }
	}
	catch (ParserConfigurationException | SAXException | IOException ex) {
	    System.out.println("Filling Exception");
	}
    }
    
    public static String getTagValue (String sTag, Element e) {
	NodeList nodeList = e.getElementsByTagName(sTag).item(0).getChildNodes();
	Node node = (Node)nodeList.item(0);
	return node.getNodeValue();
    }
}
