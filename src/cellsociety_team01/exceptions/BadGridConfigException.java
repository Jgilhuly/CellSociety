package cellsociety_team01.exceptions;

public class BadGridConfigException extends Exception {
	private static final String myErrorMessage = "Incorect Config Value Given to Grid: ";

	public BadGridConfigException () {
		super();
	}
	
	public void handleException() {
		System.out.println(myErrorMessage);
		this.printStackTrace();
	}
}
