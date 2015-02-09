package cellsociety_team01.modelview;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareGridView implements GridView {
	int sceneWidth, sceneHeight, rows, cols;
	double cellWidth, cellHeight;
	
	public SquareGridView(int sceneWidthIn, int sceneHeightIn, int rowsIn, int colsIn) {
		sceneWidth = sceneWidthIn;
		sceneHeight = sceneHeightIn;
		rows = rowsIn;
		cols = colsIn;
	}

	@Override
	public Canvas makeGrid (Canvas grid) {
		grid = new Canvas (grid.getWidth(), grid.getHeight());
		GraphicsContext gContext = grid.getGraphicsContext2D();
		gContext.setFill(Color.LIGHTGRAY);
		gContext.fillRect(0, 0, grid.getWidth(), grid.getHeight());
		
		cellWidth = grid.getWidth()/cols;
		cellHeight = grid.getHeight()/rows;	
		
		gContext.setFill(Color.BLACK);
		for (int i = 0; i <= cols; i ++) {
			gContext.fillRect(cellWidth*i, 0, 1, grid.getHeight());
		}
		
		for (int i = 0; i <= rows; i ++) {
			gContext.fillRect(0, cellHeight*i, grid.getWidth(), 1);
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
