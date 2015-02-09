package cellsociety_team01.modelview;

import java.awt.Dimension;
import java.io.File;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cellsociety_team01.parser.Parser;

public class GUI {
	public static final Dimension DEFAULT_SIZE = new Dimension(1450, 800);
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private ResourceBundle myResources; // for language support
	private Scene myScene;
	private Grid myModel; // the logical aspect of the grid
	private Stage myStage;

	// gui nodes
	private Button playButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private Slider slider;
	private TextField textField1;
	private TextField textField2;
	private TextField textField3;

	// grid nodes
	private Canvas gridCanvas;
	private GridView gridView;

	private Parser parser;
	private File file;

	private int myWidth;
	private int myHeight;
	private int numRows;
	private int numCols;

	public GUI(Grid gridIn, String language, Stage stageIn) {
		myModel = gridIn;
		myStage = stageIn;
		myWidth = DEFAULT_SIZE.width;
		myHeight = DEFAULT_SIZE.height;

		numRows = 10; // CHANGE THIS
		numCols = 10;

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ language);
		BorderPane root = new BorderPane();
		root.setBottom(makeButtonsAndSlider());
		root.setTop(makeMenuBar());
		root.setCenter(makeGrid(myWidth, myHeight, numRows, numCols));
		root.setRight(makeGraph());
		root.setLeft(makePrefPanel());

		enableButtons();

		myScene = new Scene(root, myWidth, myHeight);
	}
	
	/**
	 * Makes the Buttons and Slider
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
		resetButton = makeButton("ResetCommand", event -> reset());
		result.getChildren().add(resetButton);
		
		result.getChildren().add(makeSlider());
		result.setSpacing(10);
		
		return result;
	}
	
	/**
	 * Helper method to create buttons with labels and handlers (Taken from the example_browser)
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
	 * Makes the Slider which controls the speed of the simulation
	 * @return
	 */
	private Node makeSlider() {
		slider = new Slider();
		slider.setMin(0);
		slider.setMax(1000);
		slider.setValue(500);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(500);
		slider.setMinorTickCount(10);
		return slider;
	}
	
	/**
	 * Configures the top menu bar
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
	 * @return
	 */
	private Node makeGrid(int sceneWidth, int sceneHeight, int rows, int cols) {
		gridView = new SquareGridView(sceneWidth, sceneHeight, rows, cols);
		gridCanvas = gridView.makeGrid(new Canvas(sceneWidth / 2,
				sceneHeight / 2));
		gridCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
				e -> cellClicked(e));
		return gridCanvas;
	}
	
	/**
	 * Sets up the graph
	 * @return
	 */
	private Node makeGraph() {
		VBox result = new VBox();
		
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        
        LineChart<Number,Number> lineGraph = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        XYChart.Series series = new XYChart.Series();
        series.setName("Name");
        series.getData().add(new XYChart.Data(1, 23));
        
        lineGraph.setTitle("Title");
        lineGraph.getData().add(series);
        
        result.getChildren().add(lineGraph);
        
		return result;
	}

	/**
	 * Sets up the preferences panel for changing parameters
	 * @return
	 */
	private Node makePrefPanel() {
		GridPane inputGrid = new GridPane();
		inputGrid.setHgap(5);
		inputGrid.setVgap(5);

		textField1 = new TextField();
		textField1.setPromptText(myResources.getString("TextField1"));
		inputGrid.add(textField1, 0, 0);
		Button b1 = makeButton("EnterCommand", event -> myModel.play());
		inputGrid.add(b1, 1, 0);

		textField2 = new TextField();
		textField2.setPromptText(myResources.getString("TextField1"));
		inputGrid.add(textField2, 0, 1);
		Button b2 = makeButton("EnterCommand", event -> myModel.play());
		inputGrid.add(b2, 1, 1);

		textField3 = new TextField();
		textField3.setPromptText(myResources.getString("TextField1"));
		inputGrid.add(textField3, 0, 2);
		Button b3 = makeButton("EnterCommand", event -> myModel.play());
		inputGrid.add(b3, 1, 2);

		return inputGrid;
	}

	/**
	 * Brings up a file chooser to select an XML file
	 */
	private void loadXML() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(myResources.getString("OpenResourceFile"));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("XML File", "*.xml"));

		file = fileChooser.showOpenDialog(myStage);
		if (file != null) {
			parser = new Parser(file, myModel);
			parser.parseXmlFile();
		} else {
			System.err.println("Error Loading XML File");
		}

		setGridCellStates();
	}
	
	/**
	 * updates the GUI, called by grid on update tick
	 */
	public void update(boolean step) {
		if (myModel.isSimRunning() || step) {
			setGridCellStates();
		}
		myModel.changeUpdateRate(slider.getValue()); // Change This
		enableButtons();
	}

	/**
	 * Sets grid states based on the cell map in myModel
	 */
	private void setGridCellStates() {
		gridView.setGridCellStates(gridCanvas.getGraphicsContext2D(),
				myModel.getCells());
	}

	/**
	 * Helper method that handles the user clicking a cell
	 * @param e
	 */
	private void cellClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		System.out.println(x + ", " + y);
		int cellX = (int) (x / (gridCanvas.getWidth() / numCols));
		int cellY = (int) (y / (gridCanvas.getHeight() / numRows));
		System.out.println(cellX + ", " + cellY);
	}
	
	/**
	 * Resets the simulation to the last xml file
	 */
	public void reset() {
		parser = new Parser(file, myModel);
		parser.parseXmlFile();
		setGridCellStates();
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
	 * Returns scene for this view so it can be added to stage.
	 */
	public Scene getScene() {
		return myScene;
	}
	
	/**
	 * Gets stage
	 * @return
	 */
	public Stage getStage() {
		return myStage;
	}
}