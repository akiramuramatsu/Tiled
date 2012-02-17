package mk.tm.android.tiled.ui;

import mk.tm.android.tiled.board.Board;
import mk.tm.android.tiled.board.Field;
import mk.tm.android.tiled.board.FieldState;
import mk.tm.android.tiled.board.Position;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.List;

public class BoardScene extends Scene {

    private final Board mBoard;

    private final int mWidth;
    private final int mHeight;


    public BoardScene(int width, int height, Position startPosition, List<Position> unavailableFields) throws Exception {
        super();

        this.mWidth = width;
        this.mHeight = height;

        this.mBoard = new Board(width, height, startPosition, unavailableFields) {
            @Override
            public void onFieldChanged(Position position, FieldState newState) {

            }
        };
    }

    private Rectangle createField(Field field, VertexBufferObjectManager vertexBufferObjectManager) {
        float width = 75f;
        float height = 75f;

        float x = (field.getPosition().x * width) + (field.getPosition().x * 5);
        float y = (field.getPosition().y * height) + (field.getPosition().y * 5);

        final BoardField coloredRect = new BoardField(x, y, width, height, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (BoardScene.this.mBoard.move(this.getPosition()))
                    this.setColor(1, 0, 0);
                return true;
            }
        };
        coloredRect.setPosition(field.getPosition());

        switch (field.getState()) {
            case FREE:
                coloredRect.setColor(1, 1, 1);
                break;
            case MARKED:
                coloredRect.setColor(1, 0, 0);
                break;
            case UNAVAILABLE:
                coloredRect.setColor(0.4f, 0.4f, 0.4f);
                break;
        }
        return coloredRect;
    }

    public void init(VertexBufferObjectManager vertexBufferObjectManager) {
        for (Field field : mBoard.getFields()) {
            Rectangle rect = createField(field, vertexBufferObjectManager);
            registerTouchArea(rect);
            attachChild(rect);
        }
    }
}
