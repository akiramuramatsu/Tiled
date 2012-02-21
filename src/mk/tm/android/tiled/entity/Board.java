package mk.tm.android.tiled.entity;

import android.graphics.Point;
import mk.tm.android.tiled.config.LevelConfiguration;
import mk.tm.android.tiled.config.GameConstants;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

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

    private Cell currentTouched;

    private LevelConfiguration mConfiguration;

    protected Board(LevelConfiguration configuration) {
        super();

        mSizeX = configuration.getSizeX();
        mSizeY = configuration.getSizeY();

        mConfiguration = configuration;

        history = new ArrayList<Cell>();
        mCells = new ArrayList<Cell>();

        this.setPosition((CAMERA_WIDTH - (mSizeX * TILE_WIDTH + (mSizeX - 1) * TILE_SPACING)) / 2
                , (CAMERA_HEIGHT - (mSizeY * TILE_HEIGHT + (mSizeY - 1) * TILE_SPACING)) / 2);
    }

    public void initializeCells(ITiledTextureRegion pCellTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        for (Point point : mConfiguration.getCells()) {

            if (mConfiguration.containsNonexistent(point))
                continue;

            final float x = point.x * (TILE_WIDTH + TILE_SPACING);
            final float y = point.y * (TILE_HEIGHT + TILE_SPACING);

            final Cell cell = new Cell(x, y, pCellTextureRegion, pVertexBufferObjectManager) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                    if (currentTouched != this) {
                        tryMove(this);
                        currentTouched = this;
                    }
                    return true;
                }
            };

            cell.setCell(point.x, point.y);
            cell.setState(CellState.FREE);
            mCells.add(cell);

            this.attachChild(cell);
            this.registerTouchArea(cell);
        }
    }

    public void tryMove(Cell pCell) {
        boolean canMove = true;
        if (pCell.getState() != CellState.FREE)
            canMove = false;

        if (canMove) {
            pCell.setState(CellState.MARKED);
            pCell.animate();

            this.onCurrentCellChanged(pCell, getCurrentCell());

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

    protected abstract void onCurrentCellChanged(Cell newCell, Cell oldCell);

    public int getMarkedCellCount() {
        return history.size();
    }
}
