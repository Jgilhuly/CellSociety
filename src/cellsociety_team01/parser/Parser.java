package cellsociety_team01.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.exceptions.CellLocationException;
import cellsociety_team01.exceptions.ElementValueException;
import cellsociety_team01.exceptions.SimulationTypeException;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.simulations.GameOfLife;
import cellsociety_team01.simulations.PredatorPrey;
import cellsociety_team01.simulations.Segregation;
import cellsociety_team01.simulations.Simulation;
import cellsociety_team01.simulations.SpreadingOfFire;


public class Parser {
	
	private static final String DEFVALS_RESOURCE_PACKAGE = "resources/DefVals/DefVals";
	private static final String COLORSCHEME_RESOURCE_PACKAGE = "resources/ColorScheme/ColorScheme";
	private ResourceBundle myDefVals;
	private ResourceBundle myColorScheme;

	private File myFile;
	private Grid myGrid;
	private String myFilename;
	private Element myRoot;
	private Simulation mySim;
	private String myCellPlacement;
	private int myWidth;
	private int myHeight;
	private Map<Pair, Cell> myCells;
	private Random myRandom;

	public Parser (File file, Grid grid) {
		myFile = file;
		myGrid = grid;
		myFilename = myFile.getPath();
		myRandom = new Random();
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
		HashMap<String, String> infoMap = getChildValues(info);
		myGrid.setTitle(infoMap.get("name"));
		myGrid.setAuthor(infoMap.get("author"));
		mySim = makeSim(infoMap.get("type"));
		myDefVals = ResourceBundle.getBundle(DEFVALS_RESOURCE_PACKAGE, new Locale(mySim.getClass().getName()));
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
			e.handleException();
			return null;
		}
	}

	private void parseConfig(Element config) {
		Map<String, String> configMap = getChildValues(config);
//		configMap = checkFilled(configMap);
		Map<String, String> gridConfigMap = new HashMap<>();
		Map<String, String> simConfigMap = new HashMap<>();
		for (String s : configMap.keySet()) {
			if (s.startsWith("grid")) {
				gridConfigMap.put(s, configMap.get(s));
			} else if (s.startsWith("sim")) {
				simConfigMap.put(s, configMap.get(s));
			}
		}
		myGrid.setConfigs(gridConfigMap);
		mySim.setConfigs(simConfigMap);

		myCellPlacement = configMap.get("cell_placement");
		myColorScheme = ResourceBundle.getBundle(COLORSCHEME_RESOURCE_PACKAGE, new Locale(configMap.get("color_scheme")));
	}

//	private HashMap<String, String> checkFilled(Map<String, String> configMap) {
//		//possibly checking for whether element tag is there at all (not just empty)
//
//		return configMap;
//	}

	private HashMap<String, String> getChildValues(Element element) {
		NodeList nl = element.getChildNodes();
		HashMap<String, String> values = new HashMap<>();
		for (int i = 0 ; i < nl.getLength() ; i++){
			if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String tagName = nl.item(i).getNodeName();
				String tagValue = null;
				try {
					tagValue = getTextValue(element, tagName);
				} catch (ElementValueException e) {
					tagValue = e.handleException();
				}
				values.put(tagName, tagValue);
			}
		}
		return values;
	}

	private void parseGrid(Element grid) {
		myWidth = Integer.parseInt(grid.getAttribute("width"));
		myHeight = Integer.parseInt(grid.getAttribute("height"));

		NodeList gridList = grid.getChildNodes();
		if (myCellPlacement.equals("Location")) {
			placeGivenCells(gridList);
		} else if (myCellPlacement.equals("Percent")) {
			placeDistributedCells(gridList);
		} else if (myCellPlacement.equals("Random")) {
			placeRandomCells(gridList);
		}
		try {
			checkCells();
		} catch (CellLocationException e) {
			e.handleException();
			fillMap("empty", 1.0);
		}

		myGrid.updateGrid((ArrayList<Cell>) myCells.values());
	}

	private void checkCells() throws CellLocationException {
		for (Pair xy : myCells.keySet()) {
			if ((myCells.get(xy) == null) ||
					(xy.getX() < 0) || (xy.getX() >= myWidth) ||
					(xy.getY() < 0) || (xy.getY() >= myHeight)) {
				throw new CellLocationException();
			}
		}
	}

	private void placeRandomCells(NodeList gridList) {
		for (int i = 0 ; i < gridList.getLength() ; i++) {
			if (gridList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element team = (Element)gridList.item(i);
				fillMap(team.getNodeName(), myRandom.nextDouble());
			}
		}
	}

	private void placeDistributedCells(NodeList gridList) {
		for (int i = 0 ; i < gridList.getLength() ; i++) {
			if (gridList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element team = (Element)gridList.item(i);
				Double popPercent = null;
				try {
					popPercent = Double.parseDouble(getTextValue(team, "population_percentage"));
				} catch (ElementValueException e) {
					e.handleException();
				}
				fillMap(team.getNodeName(), popPercent);
			}
		}
	}

	private void placeGivenCells(NodeList gridList) {
		for (int i = 0 ; i < gridList.getLength() ; i++) {
			if (gridList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element team = (Element)gridList.item(i);
				String[] xvals = null;
				String[] yvals = null;
				try {
					xvals = (getTextValue(team, "xvals").split(" "));
					yvals = (getTextValue(team, "yvals").split(" "));
				} catch (ElementValueException e) {
					e.handleException();
				}
				for (int j = 0 ; j < xvals.length ; j++ ) {
					Cell newCell = new Cell(Integer.parseInt(xvals[j]), Integer.parseInt(yvals[j]),
							getState(myColorScheme.getString(team.getNodeName())));
					myCells.put(new Pair(Integer.parseInt(xvals[j]), Integer.parseInt(yvals[j])), newCell);
				}
			}
		}
	}

	private void fillMap(String teamName, Double popPercent) {
		myCells = new HashMap<Pair, Cell>();
		for (int i = 0 ; i < myWidth ; i++) {
			for (int j = 0 ; j < myHeight ; j++) {
				Pair curXY = new Pair(i, j);
				if (myCells.get(curXY) == null) {
					Random rand = new Random();
					if (rand.nextDouble() <= popPercent) {
						Cell newCell = new Cell(i, j, getState(myColorScheme.getString(teamName)));
						myCells.put(new Pair(i,j), newCell);
					}
				}
			}
		}
	}

	private String getTextValue(Element element, String tagName) throws ElementValueException {
		String textVal = null;
		NodeList nl = element.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		} else {
			throw new ElementValueException(tagName, myDefVals);
		}
		return textVal;
	}

	private State getState(String color) {
		Color c = Color.web(color);
		State s = mySim.findState(c);
		//System.out.println(s.getName());
		return s;
	}
}
