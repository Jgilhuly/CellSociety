package cellsociety_team01.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	private Grid myGrid;
	private String filename;
	private Document dom;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Element root;
	private NodeList mainNL;
	private int myWidth;
	private int myHeight;
	private ArrayList<String> myPossibleSimulationsTXT = (
			"Segregation",
			"PredatorPrey",
			"Fire",
			"GameOfLife");
	private Simulation[] myPossibleSimulations = {
			new Segregation(),
			new PredatorPrey(),
			new SpreadingOfFire(),
			new GameOfLife()};
	
	public Parser (File file, Grid grid) {
		myFile = file;
		myGrid = grid;
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
		myGrid.setTitle(getTextValue(info,"name"));
		myGrid.setAuthor(getTextValue(info,"author"));
		setRule(getTextValue(info,"rule"));
	}

	private void setRule(String textValue) {
		int x = myPossibleSimulationsTXT.indexOf(textValue);
		Simulation sim = myPossibleSimulations[x];
		myGrid.setRule(sim);
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
		
		Cell[][] cells = new Cell[myWidth][myHeight];
		
		NodeList rowList = grid.getChildNodes();
		for(int i = 0 ; i < myHeight ; i++) {
			Element row = (Element)rowList.item(i);
			NodeList cellList = row.getChildNodes();
			for (int j = 0 ; j < myWidth ; j++) {
				Element cellEl = (Element)cellList.item(j);
				String color = getTextValue(cellEl,"state");
				Cell newCell = new Cell(j, i, getState(color));
				cells[j][i] = newCell;
			}
		}
		myGrid.setCells(cells);
	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private State getState(String color) {
		int hexCode = Integer.parseInt(color);
		return new State(hexCode, false);
	}
}
