import java.util.*;

Class Cell {

	private int myX;
	private int myY;
	private State myCurState;
	private State myNextState;

	public Cell(int x, int y, Color c) {
		myX = x;
		myY = y;
		myCurState = new State(c);
		myNextState = null;
	}

	public void update() {
		myCurState = myNextState;
	}	

	public State findNextState(ArrayList<Cell> cellArray, Simulation sim) {
		State s = sim.applyRules(this, cellArray);
		myNextState = s;
		return s;
	}	

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

	public void setCurState(State s) {
		myCurState = s;
	}

	public void setNextState(State s) {
		myNextState = s;
	}

}