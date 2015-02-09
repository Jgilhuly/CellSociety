package cellsociety_team01.exceptions;

public class CellLocationException extends Exception {
	private static final String myErrorMessage = "Incorect Cell Locations Given";

	public CellLocationException() {
		super();
	}
	
	public void handleException() {
		System.out.println(myErrorMessage);
		this.printStackTrace();
	}
}