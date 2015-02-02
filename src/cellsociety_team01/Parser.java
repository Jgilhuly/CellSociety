package cellsociety_team01;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import jdk.internal.org.xml.sax.SAXException;


public class Parser {
	
	private File myFile;
	private GUI myGui;
	private String filename;
	private Document dom;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Element root;
	NodeList mainNL;
	int myWidth;
	int myHeight;
	
	public Parser (File file, GUI gui) {
		myFile = file;
		myGui = gui;
		filename = myFile.getName();
		dbf = DocumentBuilderFactory.newInstance();
	}

	public void parseXmlFile(){
		try {

			//Using factory get an instance of document builder
			db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(filename);

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		parseDocument();
		
	}
	
	private void parseDocument(){
		//get the root element
		root = dom.getDocumentElement();

		mainNL = root.getChildNodes();
		
		//Pass 3 Types of mainNL elements to different handling functions
		if(mainNL != null && mainNL.getLength() > 0) {
			parseInfo((Element)mainNL.item(0));
			parseConfig((Element)mainNL.item(1));
			parseGrid((Element)mainNL.item(2));
		}
	}

	private void parseInfo(Element info) {
		NodeList infoList = info.getChildNodes();
		myGui.setName(infoList.item(0).getNodeValue());
		myGui.setAuthor(infoList.item(1).getNodeValue());
		myGui.setRule(infoList.item(2).getNodeValue());
	}

	private void parseConfig(Element config) {
		NodeList configList = config.getChildNodes();
	}

	private void parseGrid(Element grid) {
		NamedNodeMap dimensions = grid.getAttributes();
		Element widthEl = (Element)dimensions.getNamedItem("width");
		myWidth = Integer.parseInt(widthEl.getNodeValue());
		Element heightEl = (Element)dimensions.getNamedItem("height");
		myHeight = Integer.parseInt(heightEl.getNodeValue());
		
		NodeList rowList = grid.getChildNodes();
		for(int i = 0 ; i < myHeight ; i++) {

			Element row = (Element)rowList.item(i);

			//get the Employee object
//			Employee e = getEmployee(element);

			//add it to list
//			myEmpls.add(e);
		}
	}
}
