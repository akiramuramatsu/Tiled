package mk.tm.android.tiled.board;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Board {
    private BoardCollection board;
    private Position current;
    private Position startedAt;
    private History history;

    public Board(int width, int height, Position startPosition, List<Position> unavailableFields) throws Exception {

        if (width < 1 || height < 1)
            throw new Exception("Width and height must be greater than 0.");

        mWidth = width;
        mHeight = height;
        mStep = 1;
        mAllowDiagonal = false;
        mAllowBack = true;

        history = new History();

        // check if all fields are within the board
        if (unavailableFields != null)
            for (Position item : unavailableFields)
                if (!isWithinBounds(item))
                    throw new Exception("unavailableFields");

        // initialise the board
        board = new BoardCollection();
        for (int i = 0; i < mWidth; i++)
            for (int j = 0; j < mHeight; j++) {
                Field field = new Field();
                field.state = FieldState.FREE;
                field.position = new Position(i, j);

                board.add(field);
            }

        // mark unavailable fields
        if (unavailableFields != null)
            for (Position p : unavailableFields)
                board.get(p).state = FieldState.UNAVAILABLE;

        // start position must be free
        if (!isAvailable(startPosition))
            throw new Exception("Must start on empty field.");

        current = startPosition.clone();
        startedAt = startPosition.clone();
        // occupy the start position
        board.get(current).state = FieldState.MARKED;

        // add the move to history
        history.add(current);
    }

    private int mWidth;

    public int getWidth() {
        return mWidth;
    }

    private int mHeight;

    public int getHeight() {
        return mHeight;
    }

    private int mStep;

    int getStep() {
        return mStep;
    }

    private boolean mAllowDiagonal;

    public boolean getAllowDiagonal() {
        return mAllowDiagonal;
    }

    private boolean mAllowBack;

    public boolean getAllowBack() {
        return mAllowBack;
    }

    public Position[] getHistory() {
        return history.toArray(new Position[0]);
    }

    public Field[] getFields(){
        return board.toArray(new Field[0]);
    }

    public void reset() {
        for (Field field : board) {
            if (field.state == FieldState.MARKED)
                field.state = FieldState.FREE;
        }
        current = startedAt.clone();
        board.get(current).state = FieldState.MARKED;

        history.clear();
        history.add(current.clone());
    }

    public boolean move(int x, int y) {
        return move(new Position(x, y));
    }

    public boolean move(Position p) {
        if (history.size() > 1 && history.get(history.size() - 2) == p && !isSame(p))
            return undo();

        if ((isDiagonalMove(p) && !mAllowDiagonal) || isLargeStep(p)
                || isSame(p) || !isAvailable(p) || !isWithinBounds(p))
            return false;

        history.add(p.clone());
        current = p.clone();
        board.get(p).state = FieldState.MARKED;

        return true;
    }

    public boolean undo() {
        if (history.size() > 1) {
            boolean result = history.remove(history.get(history.size() - 1));
            if (result) {
                board.get(current).state = FieldState.FREE;
                current = history.get(history.size() - 1).clone();
            }
            return result;
        }
        return false;
    }

    public boolean isSolved() {
        for (Field field : board)
            if (field.state == FieldState.FREE)
                return false;
        return true;
    }

    private boolean isWithinBounds(Position p) {
        return p.x >= 0 && p.y >= 0 && p.x < mWidth && p.y < mHeight;
    }

    private boolean isAvailable(Position p) {
        if (!isWithinBounds(p))
            return false;
        return board.get(p).state == FieldState.FREE;
    }

    private boolean isLargeStep(Position p) {
        return (Math.max(p.x, current.x) - Math.min(p.x, current.x))
                + (Math.max(p.y, current.y) - Math.min(p.y, current.y)) > mStep * 2
                || (Math.max(p.x, current.x) - Math.min(p.x, current.x)) > mStep
                || (Math.max(p.y, current.y) - Math.min(p.y, current.y)) > mStep;
    }

    private boolean isDiagonalMove(Position p) {
        return Math.max(p.x, current.x) - Math.min(p.x, current.x)
                == Math.max(p.y, current.y) - Math.min(p.y, current.y);
    }

    private boolean isSame(Position p) {
        return p.equals(current);
    }

    public abstract void onFieldChanged(Position position, FieldState newState);
}
