package cellsociety_team01.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.simulations.GameOfLife;
import cellsociety_team01.simulations.PredatorPrey;
import cellsociety_team01.simulations.Segregation;
import cellsociety_team01.simulations.Simulation;
import cellsociety_team01.simulations.SpreadingOfFire;
import jdk.internal.org.xml.sax.SAXException;


public class Parser {

	private File myFile;
	private Grid myGrid;
	private String filename;
	private Document dom;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private NodeList mainNL;
	private int myWidth;
	private int myHeight;
	private ArrayList<String> myPossibleSimulationsTXT = new ArrayList<>(Arrays.asList(
			"Segregation",
			"PredatorPrey",
			"Fire",
			"GameOfLife"));
	private Simulation[] myPossibleSimulations = {
			new Segregation(),
			new PredatorPrey(),
			new SpreadingOfFire(),
			new GameOfLife()};
	private Simulation mySim;

	public Parser (File file, Grid grid) {
		myFile = file;
		myGrid = grid;
		filename = myFile.getPath();
		dbf = DocumentBuilderFactory.newInstance();
	}

	public void parseXmlFile(){
		try {

			//Using factory get an instance of document builder
			db = dbf.newDocumentBuilder();

			System.out.println(filename);
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
		Element root = dom.getDocumentElement();

		NodeList infoNL = root.getElementsByTagName("info");
		NodeList configNL = root.getElementsByTagName("config");
		NodeList gridNL = root.getElementsByTagName("grid");

		//Pass 3 Types of mainNL elements to different handling functions
		if ((infoNL != null && infoNL.getLength() > 0) &&
				(configNL != null && configNL.getLength() > 0) &&
				(gridNL != null && gridNL.getLength() > 0)){
			parseInfo((Element)infoNL.item(0));
			parseConfig((Element)configNL.item(0));
			parseGrid((Element)gridNL.item(0));
		}
	}

	private void parseInfo(Element info) {
		myGrid.setTitle(getTextValue(info,"name"));
		myGrid.setAuthor(getTextValue(info,"author"));
		setRule(getTextValue(info,"rule"));
	}

	private void setRule(String textValue) {
		int x = myPossibleSimulationsTXT.indexOf(textValue);
		mySim = myPossibleSimulations[x];
		myGrid.setSimulation(mySim);
	}

	private void parseConfig(Element config) {
		NodeList configList = config.getChildNodes();
		ArrayList<String> configVars = new ArrayList<String>();
		for (int i = 0 ; i < configList.getLength() ; i++){
			if (configList.item(i) instanceof Element == false)
				continue;
			
			configVars.add(getTextValue(config, configList.item(i).getNodeName()));
		}
		mySim.setConfigs(configVars);
	}

	private void parseGrid(Element grid) {
		//		NamedNodeMap dimensions = grid.getAttributes();
		//		Element widthEl = (Element)dimensions.getNamedItem("width");
		myWidth = Integer.parseInt(grid.getAttribute("width"));
		//		Element heightEl = (Element)dimensions.getNamedItem("height");
		myHeight = Integer.parseInt(grid.getAttribute("height"));

		Cell[][] cells = new Cell[myWidth][myHeight];

		NodeList rowList = grid.getChildNodes();
		int xcnt = 0;
		int ycnt = 0;
		for(int i = 0 ; i < rowList.getLength() ; i++) {
			if (rowList.item(i) instanceof Element == false)
				continue;
			Element row = (Element)rowList.item(i);
			NodeList cellList = row.getChildNodes();
			for (int j = 0 ; j < cellList.getLength() ; j++) {
				if (cellList.item(j) instanceof Element == false)
					continue;

				Element cellEl = (Element)cellList.item(j);
				String color = getTextValue(cellEl,"state");
				Cell newCell = new Cell(j, i, getState(color));
				cells[xcnt][ycnt] = newCell;
				xcnt++;
			}
			xcnt = 0;
			ycnt++;
		}
		myGrid.updateGrid(cells);
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
		Color c = Color.web(color);
		State s = mySim.findState(c);
		return s;
	}
}
