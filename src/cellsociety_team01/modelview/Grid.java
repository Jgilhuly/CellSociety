package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.simulations.Simulation;

public class Grid {
	Simulation simulation;
	private ArrayList<Cell> cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private Timeline myLoop;
	private String author;
	int cols, rows;

	private enum gridShapeTypes {
		SQUARE, TRIANGULAR
	};

	private gridShapeTypes myCellShape;

	private enum gridEdgeTypes {
		FINITE, INFINITE, TOROIDAL
	};

	private gridEdgeTypes myEdgeType;
	private boolean gridOutline;

	public Grid() {
		simRunning = false;
	}

	public void setView(GUI viewIn) {
		myView = viewIn;
	}

	public void setBounds(Pair bounds) {
		cols = bounds.getX();
		rows = bounds.getY();
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}

	public boolean isSimRunning() {
		return simRunning;
	}

	public void setAnimationLoop(Timeline anIn) {
		myLoop = anIn;
	}

	public void play() {
		simRunning = true;
	}

	public void pause() {
		simRunning = false;
	}

	public void step() {
		updateCells();
		myView.update(true);

	}

	public void updateCurStates() {
		for (Cell c : cells)
			c.updateCurState();
	}

	public void changeUpdateRate(double newRate) {
		myLoop.stop();
		KeyFrame frame = start(newRate);
		myLoop.setCycleCount(Animation.INDEFINITE);
		myLoop.getKeyFrames().add(frame);
		myLoop.play();
	}

	public void setConfigs(Map<String, String> configs) {
		String grid_shape = configs.get("grid_shape");
		if (grid_shape.equals("Square"))
			myCellShape = gridShapeTypes.SQUARE;
		else if (grid_shape.equals("Triangular"))
			myCellShape = gridShapeTypes.TRIANGULAR;
		else {
			Exception e = new Exception();
			e.printStackTrace();
		}

		String grid_edge = configs.get("grid_edge");
		if (grid_edge.equals("Finite"))
			myEdgeType = gridEdgeTypes.FINITE;
		else if (grid_edge.equals("Infinite"))
			myEdgeType = gridEdgeTypes.INFINITE;
		else if (grid_edge.equals("Toroidal"))
			myEdgeType = gridEdgeTypes.TOROIDAL;
		else {
			Exception e = new Exception();
			e.printStackTrace();
		}

		String grid_outline = configs.get("grid_outline");
		if (grid_outline.equals("Yes"))
			gridOutline = true;
		else
			gridOutline = false;
	}

	public void setAuthor(String authorIn) {
		author = authorIn;
	}
	public String getAuthor() {
		return author;
	}
	
	public void setSimulation(Simulation simulationIn) {

		simulation = simulationIn;
	}

	public void updateGrid(Collection<Cell> cellsIn) {
		cells = new ArrayList<Cell>();
		cells.addAll(cellsIn);
	}

	public void setNeighbors(Map<Pair, Cell> cellMap) {
		if (myEdgeType == gridEdgeTypes.FINITE)
			finiteSetNeighbors(cellMap);
		else if (myEdgeType == gridEdgeTypes.INFINITE)
			infiniteSetNeighbors(cellMap);
		else
			toroidalSetNeighbors(cellMap);
	}

	private void finiteSetNeighbors(Map<Pair, Cell> cellMap) {
		int neighborsType = simulation.getNeighborType();
		// 0 is cardinal 4 neighbors, 1 is 8 neighbors, else
		// number of outward steps in cardinal directions

		if (neighborsType == 0) {
			for (Pair pair : cellMap.keySet()) {
				ArrayList<Pair> possibleNeighbors = getPossible4Neighbors(cellMap
						.get(pair));
				ArrayList<Cell> legalNeighbors = new ArrayList<Cell>();

				for (Pair possibleNeighbor : possibleNeighbors)
					if (possibleNeighbor.getX() > 0
							&& possibleNeighbor.getX() < cols
							&& possibleNeighbor.getY() > 0
							&& possibleNeighbor.getY() < rows) {
						legalNeighbors.add(cellMap.get(possibleNeighbor));
					}
				
				cellMap.get(pair).getNeighbors().addAll(legalNeighbors);
			}
		} else if (neighborsType == 1) {
			for (Pair pair : cellMap.keySet()) {
				ArrayList<Pair> possibleNeighbors = getPossible8Neighbors(cellMap
						.get(pair), 1);
				ArrayList<Cell> legalNeighbors = new ArrayList<Cell>();

				for (Pair possibleNeighbor : possibleNeighbors)
					if (possibleNeighbor.getX() > 0
							&& possibleNeighbor.getX() < cols
							&& possibleNeighbor.getY() > 0
							&& possibleNeighbor.getY() < rows) {
						legalNeighbors.add(cellMap.get(possibleNeighbor));
					}
			}
		} else {
			for (Pair pair : cellMap.keySet()) {
				ArrayList<Pair> possibleNeighbors = getPossible8Neighbors(cellMap
						.get(pair), neighborsType);
				ArrayList<Cell> legalNeighbors = new ArrayList<Cell>();

				for (Pair possibleNeighbor : possibleNeighbors)
					if (possibleNeighbor.getX() > 0
							&& possibleNeighbor.getX() < cols
							&& possibleNeighbor.getY() > 0
							&& possibleNeighbor.getY() < rows) {
						legalNeighbors.add(cellMap.get(possibleNeighbor));
					}
			}
		}
	}

	private ArrayList<Pair> getPossible8Neighbors(Cell cell, int levels) {
		ArrayList<Pair> ret = new ArrayList<Pair>();
		int x = cell.getX();
		int y = cell.getY();		
		
		for (int i = -1 * levels; i <= levels; i++) {
			for (int j = -1 * levels; j <= levels; j++) {
				ret.add(new Pair(x + i, y + j));
			}
		}
		
		return ret;
	}

	private ArrayList<Pair> getPossible4Neighbors(Cell cell) {
		ArrayList<Pair> ret = new ArrayList<Pair>();
		int x = cell.getX();
		int y = cell.getY();

		ret.add(new Pair(x + 1, y));
		ret.add(new Pair(x - 1, y));
		ret.add(new Pair(x, y + 1));
		ret.add(new Pair(x, y - 1));

		return ret;
	}

	private void infiniteSetNeighbors(Map<Pair, Cell> cellMap) {

	}

	private void toroidalSetNeighbors(Map<Pair, Cell> cellMap) {

	}

	public void setTitle(String titleIn) {
		myView.getStage().setTitle(titleIn);
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(double frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(1000 / updateRate * 1000),
				e -> update());
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void update() {
		if (simRunning) {
			updateCells();
			updateCurStates();
		}
		myView.update(false); // by this call, NEXT is null, and CUR is
		// up-to-date
	}

	private void updateCells() {
		for (State s : simulation.getStates()) {
			for (Cell cur : cells) { // cur = each cell in the grid
				if ((cur.getCurState().equals(s))) { // THIS SHOULD take both
					// types of RACE at one
					// time
					simulation.update(cur, cur.getNeighbors());
				}
			}
		}
		setNotUpdated();
	}
	
	private void setNotUpdated() {
		for (Cell c : cells) {
			c.setUpdated(false);
		}
	}

}
