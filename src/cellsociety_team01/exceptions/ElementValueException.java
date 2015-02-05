package cellsociety_team01.exceptions;

import java.util.ResourceBundle;

public class ElementValueException extends Exception {
	private static final String myErrorMessage = "Incorect Value Given: ";
	private String myTag;
	private ResourceBundle myDefaults;
	
	public ElementValueException(String tagName, String Def) {
		super();
		setTag(tagName);
		myDefaults = ResourceBundle.getBundle(Def+"General");
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
		if (this.getTag().matches("author|cell_shape|grid_edge|grid_outline|cell_placement|color_scheme|cell_neighbors")){
			ret = getDefault();
		} else {
			this.printStackTrace();
		}
		return ret;
	}

	private String getDefault() {
		return myDefaults.getString(this.getTag());
	}
}
