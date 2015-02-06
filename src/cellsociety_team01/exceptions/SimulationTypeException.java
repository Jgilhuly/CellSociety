package cellsociety_team01.exceptions;

public class SimulationTypeException extends Exception {
	private static final String myErrorMessage = "Incorect Type of Simulation Given";

	public SimulationTypeException() {
		super();
	}
	
	public void handleException() {
		System.out.println(myErrorMessage);
		this.printStackTrace();			
	}
}