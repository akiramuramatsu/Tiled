package mk.tm.android.tiled.entity;

import mk.tm.android.tiled.GameConstants;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 17.2.12
 * Time: 00:03
 * To change this template use File | Settings | File Templates.
 */
public class Cell extends Rectangle implements ICell, GameConstants {

    protected int mCellX;
    protected int mCellY;
    protected CellState mState;

    public Cell(final float pX, final float pY, final float pWidth, final float pHeight, final IRectangleVertexBufferObject pRectangleVertexBufferObject) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBufferObject);
    }

    /**
     * Uses a default {@link org.andengine.entity.primitive.Rectangle.HighPerformanceRectangleVertexBufferObject} in {@link org.andengine.opengl.vbo.VertexBufferObject.DrawType#STATIC} with the {@link org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute}s: {@link org.andengine.entity.primitive.Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public Cell(final float pX, final float pY, final float pWidth, final float pHeight, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    /**
     * Uses a default {@link org.andengine.entity.primitive.Rectangle.HighPerformanceRectangleVertexBufferObject} with the {@link org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute}s: {@link org.andengine.entity.primitive.Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public Cell(final float pX, final float pY, final float pWidth, final float pHeight, final VertexBufferObjectManager pVertexBufferObjectManager, final VertexBufferObject.DrawType pDrawType) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager, pDrawType);
    }


    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getCellX() {
        return this.mCellX;
    }

    public int getCellY() {
        return this.mCellY;
    }

    public CellState getState() {
        return this.mState;
    }

    public void setCell(final ICell pCellEntity) {
        this.setCell(pCellEntity.getCellX(), pCellEntity.getCellY());
    }

    public void setCell(final int pCellX, final int pCellY) {
        this.mCellX = pCellX;
        this.mCellY = pCellY;
        //this.setPosition(this.mCellX * TILE_WIDTH, this.mCellY * TILE_HEIGHT);
    }

    public void setState(final CellState state) {
        this.mState = state;
    }

    public boolean isInSameCell(final ICell pCellEntity) {
        return this.mCellX == pCellEntity.getCellX() && this.mCellY == pCellEntity.getCellY();
    }
}
