package mk.tm.android.tiled.entity;

import android.graphics.Point;
import mk.tm.android.tiled.config.BoardConfiguration;
import mk.tm.android.tiled.config.GameConstants;
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
public abstract class Board extends Scene implements GameConstants {
    private int mSizeX;
    private int mSizeY;

    protected List<Cell> mCells;
    protected List<Cell> history;

    private BoardConfiguration mConfiguration;

    protected Board(BoardConfiguration configuration) {
        super();

        mSizeX = configuration.getSizeX();
        mSizeY = configuration.getSizeY();

        mConfiguration = configuration;

        history = new ArrayList<Cell>();
        mCells = new ArrayList<Cell>();

        setPosition((CAMERA_WIDTH - (mSizeX * TILE_WIDTH + (mSizeX - 1) * TILE_SPACING)) / 2
                , (CAMERA_HEIGHT - (mSizeY * TILE_HEIGHT + (mSizeY - 1) * TILE_SPACING)) / 2);
    }

    public void initializeCells() {
        for (Point p : mConfiguration.getCells()) {
            Cell cell = onCreateCell(p.x, p.y, CellState.FREE);
            mCells.add(cell);
            cell.setCell(p.x, p.y);
            cell.setState(CellState.FREE);
        }
    }

    public void tryMove(Cell pCell) {
        boolean canMove = true;
        if (pCell.getState() != CellState.FREE)
            canMove = false;

        if (canMove) {
            onCurrentCellChanged(pCell, null);
            pCell.animate();
            history.add(pCell);
        }
    }

    private Cell getCurrentCell() {
        if (history.size() > 0)
            return history.get(history.size() - 1);
        return null;
    }

    public void tryMove(int pX, int pY) {
        tryMove(getCell(pX, pY));
    }

    public Cell getCell(int pX, int pY) {
        for (Cell cell : this.mCells) {
            if (cell.getCellX() == pX && cell.getCellY() == pY)
                return cell;
        }
        return null;
    }

    protected abstract Cell onCreateCell(int cellX, int cellY, CellState state);

    protected abstract void onCurrentCellChanged(Cell newCell, Cell oldCell);
}
