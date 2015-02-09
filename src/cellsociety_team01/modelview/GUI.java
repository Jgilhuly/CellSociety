package cellsociety_team01.modelview;

import java.awt.Dimension;
import java.io.File;
import java.util.Queue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cellsociety_team01.parser.Parser;

public class GUI {
	public static final Dimension DEFAULT_SIZE = new Dimension(1400, 800);
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
//		root.setLeft(makePrefPanel());

		enableButtons();

		myScene = new Scene(root, myWidth, myHeight);
	}

	private Node makePrefPanel() {
		return null;
	}

	private Node makeGraph() {
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
        
		return lineGraph;
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
		resetButton = makeButton("ResetCommand", event -> reset());
		result.getChildren().add(resetButton);

		slider = new Slider();
		slider.setMin(0);
		slider.setMax(1000);
		slider.setValue(500);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(500);
		slider.setMinorTickCount(10);

		result.getChildren().add(slider);

		result.setSpacing(10);

		return result;
	}

	public void reset() {
		parser = new Parser(file, myModel);
		parser.parseXmlFile();
		setGridCellStates();
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
	private Node makeGrid(int sceneWidth, int sceneHeight, int rows, int cols) {
		gridView = new SquareGridView(sceneWidth, sceneHeight, rows, cols);
		gridCanvas = gridView.makeGrid(new Canvas(sceneWidth/2, sceneHeight/2));
		gridCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cellClicked(e));
		return gridCanvas;
	}

	/**
	 * Brings up a file chooser to select an xml file
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
		}
		else {
			System.err.println("Error Loading XML File");
		}

		setGridCellStates();
	}

	/**
	 * Sets grid states based on the cell array in myModel
	 */
	private void setGridCellStates() {
//		Cell[][] cells = myModel.getCells();
//		for (int i = 0; i < cells.length; i++) {
//			for (int j = 0; j < cells[i].length; j++) {
//				Cell c = cells[i][j];
//				Polygon newCell = new Polygon ();
////				newCell.setHeight(myStage.getHeight()/(cells[0].length+50));
////				newCell.setWidth(myStage.getWidth()/(cells.length+50));
//				newCell.getPoints().addAll(new Double[] {
//						300.0, 200.0,
//						250.0, 250.0,
//						250.0, 300.0,
//						300.0, 350.0,
//						350.0, 300.0,
//						350.0, 250.0
//				});
//				newCell.setFill(c.getCurState().getColor());
//				newCell.setOnMouseClicked(e -> cellClicked(c));
//				grid.add(newCell, i, j);
//			}
//		}

		gridView.setGridCellStates(gridCanvas.getGraphicsContext2D(), myModel.getCells());
	}

	/**
	 * Helper method that handles the user clicking a cell
	 * 
	 * @param t
	 */
	private void cellClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		System.out.println(x + ", " + y);
		int cellX = (int) (x / (gridCanvas.getWidth()/numCols));
		int cellY = (int) (y / (gridCanvas.getHeight()/numRows));
		System.out.println(cellX + ", " + cellY);
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
		myModel.changeUpdateRate(slider.getValue()); //  Change This
		enableButtons();
	}

	public void singleUpdate() {
		setGridCellStates();
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