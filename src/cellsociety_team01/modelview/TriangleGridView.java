package cellsociety_team01.modelview;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import cellsociety_team01.CellState.Cell;

public class TriangleGridView implements GridView{

	int sceneWidth, sceneHeight, rows, cols;
	double cellWidth, cellHeight;
	boolean outlined;

	public TriangleGridView(int sceneWidthIn, int sceneHeightIn, int rowsIn, int colsIn, boolean outlinedIn) {
		sceneWidth = sceneWidthIn;
		sceneHeight = sceneHeightIn;
		rows = rowsIn;
		cols = colsIn;
		outlined = outlinedIn;
	}


	@Override
	public Canvas makeGrid(Canvas grid) {
		grid = new Canvas (grid.getWidth(), grid.getHeight());
		GraphicsContext gContext = grid.getGraphicsContext2D();
		gContext.setFill(Color.LIGHTGRAY);
		gContext.fillRect(0, 0, grid.getWidth(), grid.getHeight());

		cellWidth = grid.getWidth()/cols;
		cellHeight = grid.getHeight()/rows;	

		return grid;
	}

	@Override
	public void setGridCellStates(GraphicsContext gContext,
			ArrayList<Cell> cells) {
		for (Cell c : cells) {
			gContext.setFill(c.getCurState().getColor());
			if (c.getX() % 2 == 0) {
				double[] xPoints = {(c.getX() * cellWidth)-(cellWidth/2), (c.getX() * cellWidth) + cellWidth/2, ((c.getX()+1) * cellWidth)+(cellWidth/2)}; // point up
				double[] yPoints = {(c.getY()+1) * cellHeight, c.getY()*cellHeight, (c.getY()+1) * cellHeight};
				
				if (outlined) {
					Paint color = gContext.getFill();
					gContext.setFill(Color.BLACK);
					gContext.fillPolygon(xPoints, yPoints, 3);

					for (int i = 0; i < xPoints.length; i ++) {
						xPoints[i] -= 1;
						yPoints[i] -= 1;
					}
					
					gContext.setFill(color);
					gContext.fillPolygon(xPoints, yPoints, 3);

				} else {
					gContext.fillPolygon(xPoints, yPoints, 3);
				}
			}
			else {
				double[] xPoints = {(c.getX() * cellWidth)-(cellWidth/2), (c.getX() * cellWidth) + cellWidth/2, ((c.getX()+1) * cellWidth)+(cellWidth/2)}; // point down
				double[] yPoints = {c.getY() * cellHeight, (c.getY()+1)*cellHeight, c.getY() * cellHeight};
				gContext.fillPolygon(xPoints, yPoints, 3);
				
				if (outlined) {
					Paint color = gContext.getFill();
					gContext.setFill(Color.BLACK);
					gContext.fillPolygon(xPoints, yPoints, 3);

					for (int i = 0; i < xPoints.length; i ++) {
						xPoints[i] -= 1;
						yPoints[i] -= 1;
					}
					
					gContext.setFill(color);
					gContext.fillPolygon(xPoints, yPoints, 3);

				} else {
					gContext.fillPolygon(xPoints, yPoints, 3);
				}
			}
		}
	}

}
