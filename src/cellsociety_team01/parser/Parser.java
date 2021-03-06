package cellsociety_team01.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

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
import cellsociety_team01.simulations.AntForaging;
import cellsociety_team01.simulations.GameOfLife;
import cellsociety_team01.simulations.PredatorPrey;
import cellsociety_team01.simulations.Segregation;
import cellsociety_team01.simulations.Simulation;
import cellsociety_team01.simulations.SlimeMolds;
import cellsociety_team01.simulations.SpreadingOfFire;
import cellsociety_team01.simulations.Sugarscape;


public class Parser {

	private static final String DEFVALS_RESOURCE_PACKAGE = "resources/DefVals/DefVals";
	private ResourceBundle myDefVals;

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
	private int myNullCells;

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

		//parse data from the three sections of the XML file
		parseInfo((Element)myRoot.getElementsByTagName("info").item(0));
		parseConfig((Element)myRoot.getElementsByTagName("config").item(0));
		parseGrid((Element)myRoot.getElementsByTagName("grid").item(0));

	}

	private void parseInfo(Element info) {		
		Map<String, String> infoMap = getChildValues(info);
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
			case "PredatorPrey" :
				return new PredatorPrey();
			case "SpreadingOfFire" :
				return new SpreadingOfFire();
			case "GameOfLife" :
				return new GameOfLife();
			case "SlimeMolds" :
				return new SlimeMolds();
			case "AntForaging" :
				return new AntForaging();
			case "Sugarscape" :
				return new Sugarscape();
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
	}

	private Map<String, String> getChildValues(Element element) {
		NodeList nl = element.getChildNodes();
		Map<String, String> values = new HashMap<>();
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
		myNullCells = myWidth*myHeight;

		myGrid.setBounds(new Pair(myWidth, myHeight));

		myCells = new HashMap<Pair, Cell>();
		NodeList gridList = grid.getChildNodes();
		int randomStateCount = mySim.getNumPossibleStates();
		for (int i = 0 ; i < gridList.getLength() ; i++) {
			if (gridList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element team = (Element)gridList.item(i);
				if (myCellPlacement.equals("Location")) {
					placeGivenCells(team);
				} else if (myCellPlacement.equals("Percent")) {
					placeDistributedCells(team);
				} else if (myCellPlacement.equals("Random")) {
					randomStateCount--;
					placeRandomCells(team, (randomStateCount == 0));
				}
			}
		}
		try {
			if (myNullCells != 0) {
				throw new CellLocationException();
			}
		} catch (CellLocationException e) {
			e.handleException();
		}

		myGrid.setNeighbors(myCells);

		myGrid.updateGrid(myCells.values());
	}

	private void placeGivenCells(Element team) {
		String[] xVals = null;
		String[] yVals = null;
		try {
			xVals = (getTextValue(team, "xVals").split(" "));
			yVals = (getTextValue(team, "yVals").split(" "));
		} catch (ElementValueException e) {
			e.handleException();
		}
		for (int i = 0 ; i < xVals.length ; i++ ) {
			Pair location = new Pair(Integer.parseInt(xVals[i]), Integer.parseInt(yVals[i]));
			try {
				checkLocation(location);
			} catch (CellLocationException e) {
				e.handleException();
				continue;
			}
			addCell(location, getState(team.getNodeName()));
		}
		myNullCells -= xVals.length;
	}

	private void placeDistributedCells(Element team) {
		Double popPercent = null;
		try {
			popPercent = Double.parseDouble(getTextValue(team, "population_percent"));
		} catch (ElementValueException e) {
			e.handleException();
		}
		Long L = Math.round((popPercent * myWidth * myHeight));
		int population = Integer.valueOf(L.intValue());
		fillMap(team.getNodeName(), population);
		myNullCells -= population;
	}

	private void placeRandomCells(Element team, boolean last) {
		int population;
		if (last) {
			population = myNullCells;
		} else {
			population = myRandom.nextInt(myNullCells);
		}
		fillMap(team.getNodeName(), population);
		myNullCells -= population;
	}

	private void checkLocation(Pair location) throws CellLocationException {
		if ((myCells.get(location) != null) ||
				(location.getX() < 0) || (location.getX() >= myWidth) ||
				(location.getY() < 0) || (location.getY() >= myHeight)) {
			throw new CellLocationException();
		}
	}

	private void fillMap(String teamName, int population) {
		wLoop : while (population != 0) {
			Pair newXY = new Pair(myRandom.nextInt(myWidth), myRandom.nextInt(myHeight));
			for (Pair checkXY : myCells.keySet()) {
				if ((newXY.getX() == checkXY.getX()) && (newXY.getY() == checkXY.getY())) {
					continue wLoop;
				}
			}
			addCell(newXY, getState(teamName));
			population--;
		}
	}

	private void addCell(Pair location, State state) {
		Cell newCell = new Cell(location.getX(), location.getY(), state);
		myCells.put(location, newCell);

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
		State s = mySim.findState(color);
		return s;
	}
}