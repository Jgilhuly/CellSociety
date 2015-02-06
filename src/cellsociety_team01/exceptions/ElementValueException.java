package cellsociety_team01.exceptions;

import java.util.ResourceBundle;

public class ElementValueException extends Exception {
	private static final String myErrorMessage = "Incorect Value Given: ";
	private String myTag;
	private ResourceBundle myDefVals;
	
	public ElementValueException(String tagName, ResourceBundle defVals) {
		super();
		setTag(tagName);
		myDefVals = defVals;
	}

	public String errorMessage() {
		return myErrorMessage;
	}

	public String getTag() {
		return myTag;
	}

	public void setTag(String myTag) {
		this.myTag = myTag;
	}

	public String handleException() {
		System.out.println(myErrorMessage+myTag);
		String ret = null;
		if (myDefVals.containsKey(this.getTag())) {
			ret = myDefVals.getString(this.getTag());
		} else {
			this.printStackTrace();
		}
		return ret;
	}
}
