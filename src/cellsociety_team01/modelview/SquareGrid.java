package cellsociety_team01.modelview;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareGrid implements GridView {
	int sceneWidth, sceneHeight, rows, cols;
	double gridCanvasWidth, gridCanvasHeight, cellWidth, cellHeight;
	
	public SquareGrid(int sceneWidthIn, int sceneHeightIn, int rowsIn, int colsIn) {
		sceneWidth = sceneWidthIn;
		sceneHeight = sceneHeightIn;
		rows = rowsIn;
		cols = colsIn;
	}

	@Override
	public Canvas makeGrid (Canvas grid) {
		gridCanvasWidth = sceneWidth/1.5;
		gridCanvasHeight = sceneHeight/1.5;
		
		grid = new Canvas (gridCanvasWidth, gridCanvasHeight);
		GraphicsContext gContext = grid.getGraphicsContext2D();
		gContext.setFill(Color.LIGHTGRAY);
		gContext.fillRect(0, 0, gridCanvasWidth, gridCanvasHeight);
		
		cellWidth = gridCanvasWidth/cols;
		cellHeight = gridCanvasHeight/rows;	
		
		gContext.setFill(Color.BLACK);
		for (int i = 0; i < cols; i ++) {
			gContext.fillRect(cellWidth*i, 0, 1, gridCanvasHeight);
		}
		
		for (int i = 0; i < rows; i ++) {
			gContext.fillRect(0, cellHeight*i, gridCanvasWidth, 1);
		}
		
//		double[] xPoints = {300, 250, 250, 300, 350, 350};
//		double[] yPoints = {200, 250, 300, 350, 300, 250};
//		gContext.fillPolygon(xPoints, yPoints, 6);
		
		return grid;
	}

	@Override
	public void setGridCellStates(GraphicsContext gContext, ArrayList<Cell> cells) {
		for (Cell c : cells) {
			gContext.setFill(c.getState().getColor());
			gContext.fillRect(c.getX()*cellWidth, c.getY()*cellHeight, cellWidth, cellHeight);
		}
	}

}
