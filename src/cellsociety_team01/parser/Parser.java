package cellsociety_team01.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.paint.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.exceptions.SimulationTypeException;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.simulations.GameOfLife;
import cellsociety_team01.simulations.PredatorPrey;
import cellsociety_team01.simulations.Segregation;
import cellsociety_team01.simulations.Simulation;
import cellsociety_team01.simulations.SpreadingOfFire;


public class Parser {

	private File myFile;
	private Grid myGrid;
	private String myFilename;
	private Element myRoot;
	private int myWidth;
	private int myHeight;
	private Simulation mySim;

	public Parser (File file) {
		myFile = file;
		myFilename = myFile.getPath();
	}

	public void parseXmlFile(){
		try {		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(myFilename);
			
			//get the root element
			myRoot = dom.getDocumentElement();

		}catch(ParserConfigurationException pce) {
			
			
			pce.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		parseInfo((Element)myRoot.getElementsByTagName("info").item(0));
		parseConfig((Element)myRoot.getElementsByTagName("config").item(0));
		parseGrid((Element)myRoot.getElementsByTagName("grid").item(0));

	}

	private void parseInfo(Element info) {
		myGrid.setTitle(getTextValue(info,"name"));
		myGrid.setAuthor(getTextValue(info,"author"));
		mySim = makeSim(getTextValue(info,"type"));
		myGrid.setSimulation(mySim);
	}

	private Simulation makeSim(String textValue) {
		try {
			switch (textValue) {
			case "Segregation" :
				return new Segregation();
			case "PredatoryPrey" :
				return new PredatorPrey();
			case "SpreadingOfFire" :
				return new SpreadingOfFire();
			case "GameOfLife" :
				return new GameOfLife();
			case "SlimeMolds" :
//				return new SlimeMolds();
			case "ForagingAnts" :
//				return new ForagingAnts();
			case "Sugarscape" :
//				return new Sugarscape();
			default :
				throw new SimulationTypeException();
			}
		} catch (SimulationTypeException e) {
			System.out.println(e.errorMessage());
			e.printStackTrace();
			return null;
		}
	}

	private void parseConfig(Element config) {
		NodeList configList = config.getChildNodes();
		ArrayList<String> configVars = new ArrayList<String>();
		for (int i = 0 ; i < configList.getLength() ; i++){
			if (configList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				configVars.add(getTextValue(config, configList.item(i).getNodeName()));
			}
		}
		mySim.setConfigs(configVars);
	}

	private void parseGrid(Element grid) {
		myWidth = Integer.parseInt(grid.getAttribute("width"));
		myHeight = Integer.parseInt(grid.getAttribute("height"));

		Cell[][] cells = new Cell[myWidth][myHeight];

		NodeList rowList = grid.getChildNodes();
		int xcnt = 0;
		int ycnt = 0;
		for(int i = 0 ; i < rowList.getLength() ; i++) {
			if (rowList.item(i).getNodeType() == Node.ELEMENT_NODE)	{
				Element row = (Element)rowList.item(i);
				NodeList cellList = row.getChildNodes();
				for (int j = 0 ; j < cellList.getLength() ; j++) {
					if (cellList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Element cellEl = (Element)cellList.item(j);
						String color = getTextValue(cellEl,"state");
						Cell newCell = new Cell(j, i, getState(color));
						cells[xcnt][ycnt] = newCell;
						xcnt++;
					}
				}
				xcnt = 0;
				ycnt++;
			}
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
