package mk.tm.android.tiled.entity;

import mk.tm.android.tiled.board.*;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.List;

public class BoardScene extends Scene implements IFieldMovementListener {

    private final Board mBoard;

    private final int mWidth;
    private final int mHeight;


    public BoardScene(int width, int height, Position startPosition, List<Position> unavailableFields) throws Exception {
        super();

        this.mWidth = width;
        this.mHeight = height;

        this.mBoard = new Board(width, height, startPosition, unavailableFields);
        this.mBoard.setFieldMovementListener(this);
    }

    private BoardField currentTouched;

    private Rectangle createField(Position field, VertexBufferObjectManager vertexBufferObjectManager) {
        float width = 80f;
        float height = 80f;

        float x = (field.x * width) + (field.x * 5);
        float y = (field.y * height) + (field.y * 5);

        final BoardField newField = new BoardField(x, y, width, height, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (currentTouched != this) {
                    BoardScene.this.mBoard.move(this.getPosition());
                    currentTouched = this;
                }
                return true;
            }
        };
        newField.setPosition(field);

        switch (field.getState()) {
            case FREE:
                newField.setColor(1, 1, 1);
                break;
            case MARKED:
                newField.setColor(1, 0, 0);
                break;
            case UNAVAILABLE:
                newField.setColor(0.4f, 0.4f, 0.4f);
                break;
        }
        return newField;
    }

    public void init(VertexBufferObjectManager vertexBufferObjectManager) {
        for (Position field : mBoard.getFields()) {
            Rectangle rect = createField(field, vertexBufferObjectManager);
            registerTouchArea(rect);
            attachChild(rect);
        }
    }

    @Override
    public void onFieldChanged(Position current, Position last) {
        BoardField curr = get(current);
        BoardField bef = get(last);

        switch (last.getState()) {
            case FREE:
                bef.setColor(1, 1, 1);
                break;
            case MARKED:
                bef.setColor(1, 0, 0);
                break;
            case UNAVAILABLE:
                bef.setColor(0.4f, 0.4f, 0.4f);
                break;
        }

        curr.setColor(0, 0, 1);
    }

    private BoardField get(Position position) {
        for (IEntity field : this.mChildren) {
            BoardField f = (BoardField) field;
            if (f.getPosition().equals(position))
                return f;
        }
        return null;
    }
}
