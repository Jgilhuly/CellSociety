package cellsociety_team01.CellState;
import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import cellsociety_team01.simulations.Simulation;

public class Cell {

	private int myX;
	private int myY;
	
	private State myCurState;
	private State myNextState;
	//private State myState;
	private boolean updated;

	public Cell(int x, int y, State s){
		myX = x;
		myY = y;
		
		myCurState = s;
		myNextState = null;
		//myState = s;
	}
	
	public void setUpdated(boolean target){
		updated = target;
	}
	
	
	
	public boolean isUpdated(){
		return updated;
	}

	/*public void update() {
		myCurState = myNextState;
	}	*/

	/*public void findNextState(ArrayList<Cell> cellArray, Simulation sim) {
		State s = sim.applyRules(this, cellArray);
		if (s != null) {
			myCurState = s;
		}
	}*/	

	public void setX(int x) {
		myX = x;
	}

	public void setY(int y) {
		myY = y;
	}

	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	public State getCurState() {
		return myCurState;
	}

	public State getNextState() {
		return myNextState;
	}
	public void updateCurState(){
		//if (myNextState == null) return;
		myCurState = myNextState;
		myNextState = new State(Color.PINK, "future - shouldn't show");

		
		
	}

	public void setCurState(State s) {
		myCurState = s;
	}

	public void setNextState(State s) {
		
		myNextState = s;
	}
	
	/*public State getState(){
		return myState;
	}
	
	public void setState(State s){
		myState = s;
	}*/

}