package mk.tm.android.tiled.entity;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 18.2.12
 * Time: 01:48
 * To change this template use File | Settings | File Templates.
 */
public class BoardConfiguration {
    private int mSizeX;
    private int mSizeY;

    private List<Point> mCells;
    private Point mStartCell;

    public BoardConfiguration() {
        mCells = new ArrayList<Point>();

        setSizeX(5);
        setSizeY(5);
        mStartCell = new Point(0, 0);

        for (int x = 0; x < getSizeX(); x++)
            for (int y = 0; y < getSizeY(); y++)
                mCells.add(new Point(x, y));
    }

    public int getSizeX() {
        return mSizeX;
    }

    public void setSizeX(int mSizeX) {
        this.mSizeX = mSizeX;
    }

    public int getSizeY() {
        return mSizeY;
    }

    public void setSizeY(int mSizeY) {
        this.mSizeY = mSizeY;
    }

    public List<Point> getCells() {
        return mCells;
    }

    public Point getStartCell() {
        return mStartCell;
    }
}
