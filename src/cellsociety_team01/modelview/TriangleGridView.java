package cellsociety_team01.modelview;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import cellsociety_team01.CellState.Cell;

public class TriangleGridView implements GridView {

	int sceneWidth, sceneHeight, rows, cols;
	double cellWidth, cellHeight;
	boolean outlined; // are cells outlined?

	public TriangleGridView(int sceneWidthIn, int sceneHeightIn, int rowsIn,
			int colsIn, boolean outlinedIn) {
		sceneWidth = sceneWidthIn;
		sceneHeight = sceneHeightIn;
		rows = rowsIn;
		cols = colsIn;
		outlined = outlinedIn;
	}

	/**
	 * Sets up the grid canvas
	 */
	@Override
	public Canvas makeGrid(Canvas grid) {
		grid = new Canvas(grid.getWidth(), grid.getHeight());
		GraphicsContext gContext = grid.getGraphicsContext2D();
		gContext.setFill(Color.LIGHTGRAY);
		gContext.fillRect(0, 0, grid.getWidth(), grid.getHeight());

		cellWidth = grid.getWidth() / cols;
		cellHeight = grid.getHeight() / rows;

		return grid;
	}

	/**
	 * draws the grid cells
	 */
	@Override
	public void setGridCellStates(GraphicsContext gContext,
			ArrayList<Cell> cells) {
		for (Cell c : cells) {
			gContext.setFill(c.getCurState().getColor());
			double[] xPoints = { (c.getX() * cellWidth) - (cellWidth / 2),
					(c.getX() * cellWidth) + cellWidth / 2,
					((c.getX() + 1) * cellWidth) + (cellWidth / 2) }; // point
																		// up

			if (c.getX() % 2 == 0) {
				double[] yPoints = { (c.getY() + 1) * cellHeight,
						c.getY() * cellHeight, (c.getY() + 1) * cellHeight };
				drawTriangle(gContext, xPoints, yPoints);
			} else {
				double[] yPoints = { c.getY() * cellHeight,
						(c.getY() + 1) * cellHeight, c.getY() * cellHeight };
				drawTriangle(gContext, xPoints, yPoints);
			}
		}
	}

	/**
	 * Draws a triangle, outlined or otherwise, with the given points
	 * 
	 * @param gContext
	 * @param xPoints
	 * @param yPoints
	 */
	private void drawTriangle(GraphicsContext gContext, double[] xPoints,
			double[] yPoints) {
		if (outlined) {
			Paint color = gContext.getFill();
			gContext.setFill(Color.BLACK);
			gContext.fillPolygon(xPoints, yPoints, 3);

			for (int i = 0; i < xPoints.length; i++) {
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
