package mk.tm.android.tiled.entity;

import mk.tm.android.tiled.board.IPosition;
import mk.tm.android.tiled.board.Position;
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
public class BoardField extends Rectangle implements IPosition {
    private Position mPosition;

    public BoardField(final float pX, final float pY, final float pWidth, final float pHeight, final IRectangleVertexBufferObject pRectangleVertexBufferObject) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBufferObject);
    }

    /**
     * Uses a default {@link org.andengine.entity.primitive.Rectangle.HighPerformanceRectangleVertexBufferObject} in {@link org.andengine.opengl.vbo.VertexBufferObject.DrawType#STATIC} with the {@link org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute}s: {@link org.andengine.entity.primitive.Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public BoardField(final float pX, final float pY, final float pWidth, final float pHeight, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    /**
     * Uses a default {@link org.andengine.entity.primitive.Rectangle.HighPerformanceRectangleVertexBufferObject} with the {@link org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute}s: {@link org.andengine.entity.primitive.Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public BoardField(final float pX, final float pY, final float pWidth, final float pHeight, final VertexBufferObjectManager pVertexBufferObjectManager, final VertexBufferObject.DrawType pDrawType) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager, pDrawType);
    }

    @Override
    public Position getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Position position) {
        mPosition = position;
    }
}
