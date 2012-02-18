package mk.tm.android.tiled.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 18.2.12
 * Time: 01:35
 * To change this template use File | Settings | File Templates.
 */
public interface ICell {
    // ===========================================================
    // Constants
    // ===========================================================

    public abstract int getCellX();
    public abstract int getCellY();
    public abstract CellState getState();

    public abstract void setCell(final ICell pCell);
    public abstract void setCell(final int pCellX, final int pCellY);
    public abstract void setState(final CellState state);

    public abstract boolean isInSameCell(final ICell pCellEntity);
}
