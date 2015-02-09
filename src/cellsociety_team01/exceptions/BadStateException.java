package cellsociety_team01.exceptions;

public class BadStateException extends Exception {
	private static final String myErrorMessage = "Incorect State Given";

	public BadStateException() {
		super();
	}
	
	public void handleException() {
		System.out.println(myErrorMessage);
		this.printStackTrace();
	}
}