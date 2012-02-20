package mk.tm.android.tiled.entity;

import mk.tm.android.tiled.config.GameConstants;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 17.2.12
 * Time: 00:03
 * To change this template use File | Settings | File Templates.
 */
public class Cell extends AnimatedSprite implements GameConstants {

    protected int mCellX;
    protected int mCellY;
    protected CellState mState;

    public Cell(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    public Cell(final float pX, final float pY, final float pWidth, final float pHeight, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
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

    public void setCell(final int pCellX, final int pCellY) {
        this.mCellX = pCellX;
        this.mCellY = pCellY;
        //this.setPosition(this.mCellX * TILE_WIDTH, this.mCellY * TILE_HEIGHT);
    }

    public void setState(final CellState state) {
        this.mState = state;
    }

    public void animate() {
        this.animate(150, false);
    }
}
