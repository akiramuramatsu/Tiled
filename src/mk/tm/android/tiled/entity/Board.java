package mk.tm.android.tiled.entity;

import android.graphics.Point;
import org.andengine.entity.scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Board extends Scene {
    private int mSizeX;
    private int mSizeY;

    protected List<ICell> mCells;
    protected List<ICell> history;

    private BoardConfiguration mConfiguration;

    protected Board(BoardConfiguration configuration) {
        super();

        mSizeX = configuration.getSizeX();
        mSizeY = configuration.getSizeY();

        mConfiguration = configuration;

        history = new ArrayList<ICell>();
        mCells = new ArrayList<ICell>();
    }

    public void initializeCells() {
        for (Point p : mConfiguration.getCells()) {
            Cell cell = onCreateCell(p.x, p.y, CellState.FREE);
            mCells.add(cell);
        }
    }

    public void tryMove(Cell boardField) {
        onCurrentCellChanged(boardField, null);
    }

    protected abstract Cell onCreateCell(int cellX, int cellY, CellState state);

    protected abstract void onCurrentCellChanged(Cell newCell, Cell oldCell);
}
