package cellsociety_team01.CellState;
import java.util.*;

import cellsociety_team01.simulations.Simulation;

public class Cell {

	private int myID;
	private State myCurState;
	private State myNextState;
	private ArrayList<Cell> myNeighbors;

	public Cell(int i, State s) {
		myID = i;
		myCurState = s;
		myNextState = null;
		myNeighbors = new ArrayList<Cell>();
	}

	public void update() {
		myCurState = myNextState;
	}	

	public void findNextState(Simulation sim) {
		State s = sim.applyRules(this, myNeighbors);
		if (s != null) {
			myNextState = s;
		}
	}	

	public void addNeighbor(Cell c) {
		myNeighbors.add(c);
	}

	public ArrayList<Cell> getNeighbors() {
		return myNeighbors;
	}

	public void setID(int i) {
		myID = i;
	}

	public int getID() {
		return myID;
	}
	
	public State getCurState() {
		return myCurState;
	}

	public State getNextState() {
		return myNextState;
	}

	public void setCurState(State s) {
		myCurState = s;
	}

	public void setNextState(State s) {
		myNextState = s;
	}

}