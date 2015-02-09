package cellsociety_team01.modelview;

/**
 * Interface used for drawing different cell shapes
 */

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public interface GridView {
	public Canvas makeGrid(Canvas grid);

	public void setGridCellStates(GraphicsContext gContext,
			ArrayList<Cell> cells);
}
