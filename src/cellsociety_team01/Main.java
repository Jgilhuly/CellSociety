package cellsociety_team01;

import cellsociety_team01.modelview.GUI;
import cellsociety_team01.modelview.Grid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String TITLE = "Cell Society";
    public static final int STARTING_SPEED = 1000;
	
	public static void main (String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Grid grid = new Grid();
		GUI gui = new GUI("English", stage);
		grid.setView(gui);
		
		stage.setTitle(TITLE);
		stage.setScene(gui.getScene());
		stage.show();
		
		// setup the game's loop
		KeyFrame frame = grid.start(STARTING_SPEED);
		Timeline animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		grid.setAnimationLoop(animation);
		animation.play();
		
	}
}
