package cellsociety_team01.modelview;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.simulations.Simulation;

public class Grid {
	Simulation simulation;
	private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private String author;
	private Timeline myLoop;
	private ArrayList<Point2D> myNeighborCoords;
	int i = 0; //FOR TESTING

	public Grid() {
		initialize4Neighbors();
	}
	
	public void initialize4Neighbors(){
		myNeighborCoords = new ArrayList<Point2D>();
		
		myNeighborCoords.add(new Point2D(-1, 0));
		myNeighborCoords.add(new Point2D(1, 0));
		myNeighborCoords.add(new Point2D(0, -1));
		myNeighborCoords.add(new Point2D(0, 1));
	}
	
	public void initialize8Neighbors(){
		myNeighborCoords = new ArrayList<Point2D>();
		
		myNeighborCoords.add(new Point2D(-1, 0));
		myNeighborCoords.add(new Point2D(1, 0));
		myNeighborCoords.add(new Point2D(0, -1));
		myNeighborCoords.add(new Point2D(0, 1));

		myNeighborCoords.add(new Point2D(-1, -1));
		myNeighborCoords.add(new Point2D(1, -1));
		myNeighborCoords.add(new Point2D(1, 1));
		myNeighborCoords.add(new Point2D(-1, 1));
	}

	public void setView(GUI viewIn) {
		myView = viewIn;
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
		update();
		
		myView.singleUpdate();
	}
	
	public void updateCurStates(){
		for(ArrayList<Cell> list : cells)
			for(Cell c : list)
				c.updateCurState();
	}

	public void changeUpdateRate(double newRate) {	
		myLoop.stop();
		KeyFrame frame = start(newRate);
		myLoop.setCycleCount(Animation.INDEFINITE);
		myLoop.getKeyFrames().add(frame);
		myLoop.play();
	}

	public void setSimulation(Simulation simulationIn) {

		simulation = simulationIn;
	}

	public void updateGrid(ArrayList<ArrayList<Cell>> cellsIn) {
		System.out.println("UPDATE CYCLE   "+i);
		i++;
		cells = cellsIn;
	}
	
	public ArrayList<Cell> getNeighbors(Cell c){
		int x = 0;
		int y = 0;
		for (ArrayList<Cell> list: cells)
			for (Cell temp : list)
				if(temp.equals(c)){
					x = cells.indexOf(list);
					y = list.indexOf(temp);
				}
		ArrayList<Cell> ret = new ArrayList<Cell>();
		
		//COME BACK TO THIS -- MIGHT also WORK FOR HEXAGONAL / TRIANGLES
		
		for(Point2D p: myNeighborCoords)
			if(     (x+(int)p.getX() <= (cells.size()-1))&&
					(x+(int)p.getX() >= 0)&&
					(y+(int)p.getY() <= (cells.get(0).size()-1))&&
					(y+(int)p.getY() >= 0)){
				ret.add(cells.get(x+(int)p.getX()).get(y+(int)p.getY()));
			}
		return ret;
	}

	public void setTitle(String titleIn) {
		myView.getStage().setTitle(titleIn);
	}
	
	public void setAuthor(String authorIn) {
		author = authorIn;
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(double frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(1000 / updateRate * 1000), e -> update());
	}
	
	public ArrayList<ArrayList<Cell>> getCells() {
		return cells;
	}

	public void update() {
		if (simRunning) {			
			updateGrid(simulation.updateGrid(this));
			updateCurStates();
		}
		myView.update();
		setNotUpdated();	
	}

	public void setNotUpdated(){	
		for(ArrayList<Cell> list: cells)
			for(Cell c: list)
				c.setUpdated(false);
	}
}
