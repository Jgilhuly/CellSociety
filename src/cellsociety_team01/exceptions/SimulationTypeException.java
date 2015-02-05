package cellsociety_team01.exceptions;

public class SimulationTypeException extends Exception {
	private static final String myErrorMessage = "Incorect Type of Simulation Used";
		  public SimulationTypeException() { super(); }
		  public SimulationTypeException(String message) { super(message); }
		  public SimulationTypeException(String message, Throwable cause) { super(message, cause); }
		  public SimulationTypeException(Throwable cause) { super(cause); }
		  public String errorMessage() {
			  return myErrorMessage;
		  }
}