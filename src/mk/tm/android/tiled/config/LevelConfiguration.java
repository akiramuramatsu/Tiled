package mk.tm.android.tiled.config;

import android.content.Context;
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
public class LevelConfiguration {
    private int mSizeX;
    private int mSizeY;

    private List<Point> mCells;
    private List<Point> mNonexistentCells;
    private List<Point> mUnavailableCells;
    private Point mStartCell;

    public LevelConfiguration() {
        this.mCells = new ArrayList<Point>();
        this.mNonexistentCells = new ArrayList<Point>();
        this.mUnavailableCells = new ArrayList<Point>();
    }

    public int getSizeX() {
        return mSizeX;
    }

    public int getSizeY() {
        return mSizeY;
    }

    void setSize(final int pSizeX, final int pSizeY) {
        this.mSizeX = pSizeX;
        this.mSizeY = pSizeY;

        this.mCells.clear();
        this.mNonexistentCells.clear();
        this.mUnavailableCells.clear();

        for (int x = 0; x < getSizeX(); x++)
            for (int y = 0; y < getSizeY(); y++)
                mCells.add(new Point(x, y));
    }

    public List<Point> getCells() {
        return mCells;
    }

    public Point getStartCell() {
        return mStartCell;
    }

    Point setStartCell(Point pStartCell) {
        return mStartCell = pStartCell;
    }

    void addNonexistantCell(final Point pCell) {
        this.mNonexistentCells.add(pCell);
    }

    void addUnavailableCell(final Point pCell) {
        this.mUnavailableCells.add(pCell);
    }

    public static LevelConfiguration get(final int pWorld, final int pLevel, final Context pContext) {
        LevelParser parser = new LevelParser(pWorld, pLevel, pContext);
        return parser.getConfiguration();
    }

    public boolean containsUnavailable(Point point) {
        for (Point p : this.mUnavailableCells) {
            if (p.x == point.x && p.y == point.y)
                return true;
        }
        return false;
    }

    public boolean containsNonexistent(Point point) {
        for (Point p : this.mNonexistentCells) {
            if (p.x == point.x && p.y == point.y)
                return true;
        }
        return false;
    }
}
