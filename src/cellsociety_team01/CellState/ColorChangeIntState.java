package cellsociety_team01.CellState;

import java.util.Random;

import javafx.scene.paint.Color;

public class ColorChangeIntState extends IntState{
	
	private int myColorStatusIndex;
	private int myColorStatusBaseline;
	private Random myRandom = new Random();
	private Color myBaselineColor;
	
	public ColorChangeIntState(Color myColor, String s, int i,  Color baselineColor,int colorStatusIndex, int colorStatusBaseline){
		super(myColor, s, i);
		myBaselineColor = baselineColor;
		myColorStatusIndex = colorStatusIndex;
		myColorStatusBaseline = colorStatusBaseline;
	}
	
	public void updateColor(){
		int k = stateNums[myColorStatusIndex] - myColorStatusBaseline; // difference 
		myColor = myBaselineColor;
		if(k > 0){
			for(int i = 0; i < k; i++)
				myColor = myColor.brighter();
		}
		else{
			for(int i = 0; i < k*-1; i++)
				myColor = myColor.darker();
		}
	}
	public State newInstanceOf(){
		ColorChangeIntState a =  new ColorChangeIntState(myColor, myName, stateNums.length, myColor, myColorStatusIndex, myColorStatusBaseline);
		a.setInt(myColorStatusIndex, (int) (myColorStatusBaseline*myRandom.nextDouble()));
		return a;
	}
}
