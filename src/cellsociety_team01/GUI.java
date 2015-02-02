package cellsociety_team01;

import java.awt.Dimension;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
	public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private Scene myScene;
	private ResourceBundle myResources;
	private Grid myModel;
//	private Stage myStage;

	private Button playButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private GridPane grid;

	public GUI(Grid gridIn, String language, Stage stageIn) {
		myModel = gridIn;
//		myStage = stageIn;

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ language);
		BorderPane root = new BorderPane();
		root.setBottom(makeButtons());
		root.setTop(makeMenuBar());
		 root.setCenter(makeGrid());

		enableButtons();

		myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}

	private Node makeButtons() {
		HBox result = new HBox();
		playButton = makeButton("PlayCommand", event -> myModel.play());
		result.getChildren().add(playButton);
		pauseButton = makeButton("PauseCommand", event -> myModel.pause());
		result.getChildren().add(pauseButton);
		stepButton = makeButton("StepCommand", event -> myModel.step());
		result.getChildren().add(stepButton);
		resetButton = makeButton("ResetCommand", event -> myModel.reset());
		result.getChildren().add(resetButton);
		return result;
	}

	private Button makeButton(String property, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		String label = myResources.getString(property);
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}

	private Node makeMenuBar() {
		Menu menu1 = new Menu("File");
		Menu menu2 = new Menu("Help");

		MenuItem loadXML = new MenuItem("Load XML");
		loadXML.setOnAction(e -> loadXML());
		menu1.getItems().add(loadXML);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2);
		return menuBar;
	}

	private void loadXML() {
		System.out.println("CHECK");
	}

	private Node makeGrid() {
		grid = new GridPane();
		setGridCellStates();
		grid.setGridLinesVisible(true);
		return grid;
	}

	private void setGridCellStates() {		
		Text t1 = new Text("Hello");
		t1.setOnMouseClicked(e -> cellClicked(t1));
		grid.add(t1, 2, 3);
		
		grid.add(new Text("Patrick"), 1, 1);
		grid.add(new Text("Jangsoon"), 1, 2);
		grid.add(new Text("John"), 5, 4);
		grid.add(new Text("Peter"), 3, 4);
	}
	
	private void cellClicked (Text t) {
		t.setText("Checked");
	}

	private void enableButtons() {
		playButton.setDisable(myModel.isSimRunning());
		pauseButton.setDisable(!myModel.isSimRunning());
		stepButton.setDisable(myModel.isSimRunning());
	}

	// update just the view to display given URL
	public void update() {
		enableButtons();
	}

	/**
	 * Returns scene for this view so it can be added to stage.
	 */
	public Scene getScene() {
		return myScene;
	}
}