package cellsociety_team01.exceptions;

public class BadStateException extends Exception {
	private static final String myErrorMessage = "Incorect State Given";
	private String id;

	public BadStateException(String identifier) {
		super();
		id = identifier;
	}
	
	public void handleException() {
		System.out.println(myErrorMessage+id);
		this.printStackTrace();
	}
}