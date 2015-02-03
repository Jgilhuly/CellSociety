package cellsociety_team01.modelview;

import java.awt.Dimension;
import java.io.File;
import java.util.ResourceBundle;

import cellsociety_team01.Cell;
import cellsociety_team01.parser.Parser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI {
	public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private Scene myScene;
	private ResourceBundle myResources; // for language support
	private Grid myModel; // the logical aspect of the grid
	private Stage myStage;

	private Button playButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private Slider slider;
	private GridPane grid; // the visual aspect of the grid
	private Parser parser;

	public GUI(Grid gridIn, String language, Stage stageIn) {
		myModel = gridIn;
		myStage = stageIn;

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ language);
		BorderPane root = new BorderPane();
		root.setBottom(makeButtonsAndSlider());
		root.setTop(makeMenuBar());
		root.setCenter(makeGrid());

		enableButtons();

		myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}

	/**
	 * Makes the Buttons and Slider
	 * 
	 * @return
	 */
	private Node makeButtonsAndSlider() {
		HBox result = new HBox();
		playButton = makeButton("PlayCommand", event -> myModel.play());
		result.getChildren().add(playButton);
		pauseButton = makeButton("PauseCommand", event -> myModel.pause());
		result.getChildren().add(pauseButton);
		stepButton = makeButton("StepCommand", event -> myModel.step());
		result.getChildren().add(stepButton);
		resetButton = makeButton("ResetCommand", event -> myModel.reset());
		result.getChildren().add(resetButton);

		slider = new Slider();
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(50);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);

		result.getChildren().add(slider);

		result.setSpacing(10);

		return result;
	}

	public Stage getStage() {
		return myStage;
	}

	/**
	 * Helper method to create buttons with labels and handlers
	 * 
	 * @param property
	 * @param handler
	 * @return
	 */
	private Button makeButton(String property, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		String label = myResources.getString(property);
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}

	/**
	 * Configures the top menu bar
	 * 
	 * @return
	 */
	private Node makeMenuBar() {
		Menu menu1 = new Menu(myResources.getString("File"));
		Menu menu2 = new Menu(myResources.getString("About"));

		MenuItem loadXML = new MenuItem(myResources.getString("LoadXML"));
		loadXML.setOnAction(e -> loadXML());
		menu1.getItems().add(loadXML);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2);
		return menuBar;
	}

	/**
	 * Configures the grid view
	 * 
	 * @return
	 */
	private Node makeGrid() {
		grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(10, 50, 10, 50));
		grid.setHgap(5);
		grid.setVgap(5);
		return grid;
	}

	/**
	 * Brings up a file chooser to select an xml file
	 */
	private void loadXML() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(myResources.getString("OpenResourceFile"));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("XML File", "*.xml"));

		File file = fileChooser.showOpenDialog(myStage);
		if (file != null) {
			parser = new Parser(file, myModel);
			parser.parseXmlFile();
		}
		else {
			System.err.println("Error Loading XML File");
		}
	}

	/**
	 * Sets grid states based on the cell array in myModel
	 */
	private void setGridCellStates() {
		//		Text t1 = new Text("Hello");
		//		t1.setOnMouseClicked(e -> cellClicked(t1));
		//		grid.add(t1, 2, 3);
		//
		//		grid.add(new Text("Patrick"), 1, 1);
		//		grid.add(new Text("Jangsoon"), 1, 2);
		//		grid.add(new Text("John"), 5, 4);
		//		grid.add(new Text("Peter"), 3, 4);

		Cell[][] cells = myModel.getCells();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; i++) {
				Cell c = cells[i][j];
				Rectangle newCell = new Rectangle ();
				newCell.setFill(c.getCurState().getColor());
				newCell.setOnMouseClicked(e -> cellClicked(c));
				grid.add(newCell, i, j);
			}
		}
	}

	/**
	 * Helper method that handles the user clicking a cell
	 * 
	 * @param t
	 */
	private void cellClicked(Cell cell) {
		// ADD CYCLE CELL STATES

		//		System.out.println(grid.getColumnIndex(t).intValue());
		//		System.out.println(grid.getRowIndex(t).intValue());
	}

	/**
	 * enables / disables buttons on update
	 */
	private void enableButtons() {
		playButton.setDisable(myModel.isSimRunning());
		pauseButton.setDisable(!myModel.isSimRunning());
		stepButton.setDisable(myModel.isSimRunning());
		resetButton.setDisable(myModel.isSimRunning());
	}

	/**
	 * updates the GUI, called by grid on update tick
	 */
	public void update() {
		if (myModel.isSimRunning()) {
			 setGridCellStates();
		}
		//		System.out.println(slider.getValue());
		myModel.changeUpdateRate(slider.getValue()); //  Change This
		enableButtons();
	}

	/**
	 * Returns scene for this view so it can be added to stage.
	 */
	public Scene getScene() {
		return myScene;
	}
}