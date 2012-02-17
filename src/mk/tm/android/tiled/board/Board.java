package mk.tm.android.tiled.board;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Board {
    private int mWidth;
    private int mHeight;
    private int mStep;
    private boolean mAllowDiagonal;
    private boolean mAllowBack;

    private BoardCollection board;
    private PositionCollection history;
    private IFieldMovementListener movementListener;

    public Board(int width, int height, Position startPosition, List<Position> unavailableFields) throws Exception {

        if (width < 1 || height < 1)
            throw new Exception("Width and height must be greater than 0.");

        mWidth = width;
        mHeight = height;
        mStep = 1;
        mAllowDiagonal = false;
        mAllowBack = true;

        history = new PositionCollection();

        // check if all fields are within the board
        if (unavailableFields != null)
            for (Position item : unavailableFields)
                if (!isWithinBounds(item))
                    throw new Exception("unavailableFields");

        // initialise the board
        board = new BoardCollection();
        for (int i = 0; i < mWidth; i++)
            for (int j = 0; j < mHeight; j++) {
                Position field = new Position(i, j);
                field.setState(FieldState.FREE);

                board.add(field);
            }

        // mark unavailable fields
        if (unavailableFields != null)
            for (Position p : unavailableFields)
                board.get(p).setState(FieldState.UNAVAILABLE);

        // start position must be free
        if (!isAvailable(startPosition))
            throw new Exception("Must start on empty field.");

        //current = startPosition.clone();
        //startedAt = startPosition.clone();
        // occupy the start position
        board.get(startPosition).setState(FieldState.MARKED);

        // add the move to history
        history.add(startPosition);
    }



    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    int getStep() {
        return mStep;
    }



    public boolean getAllowDiagonal() {
        return mAllowDiagonal;
    }



    public boolean getAllowBack() {
        return mAllowBack;
    }

    public Position[] getHistory() {
        return history.toArray(new Position[0]);
    }

    public Position[] getFields() {
        return board.toArray(new Position[0]);
    }

    public void reset() {
        for (Position field : board) {
            if (field.getState() == FieldState.MARKED)
                field.setState(FieldState.FREE);
        }

        Position p = history.last();
        history.clear();
        history.add(p);
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

        history.add(p);
        board.get(p).setState(FieldState.MARKED);

        onFieldChanged(board.get(p), board.get(history.beforeLast()));

        return true;
    }

    private boolean undo() {
        board.get(history.last()).setState(FieldState.FREE);
        onFieldChanged(board.get(history.beforeLast()), board.get(history.last()));
        history.remove(history.last());
        return true;
    }

    private void onFieldChanged(Position current, Position last) {
        if (movementListener != null)
            movementListener.onFieldChanged(current, last);
    }

    public boolean isSolved() {
        for (Position field : board)
            if (field.getState() == FieldState.FREE)
                return false;
        return true;
    }

    private boolean isWithinBounds(Position p) {
        return p.x >= 0 && p.y >= 0 && p.x < mWidth && p.y < mHeight;
    }

    private boolean isAvailable(Position p) {
        if (!isWithinBounds(p))
            return false;
        return board.get(p).getState() == FieldState.FREE;
    }

    private boolean isLargeStep(Position p) {
        Position current = history.last();
        return (Math.max(p.x, current.x) - Math.min(p.x, current.x))
                + (Math.max(p.y, current.y) - Math.min(p.y, current.y)) > mStep * 2
                || (Math.max(p.x, current.x) - Math.min(p.x, current.x)) > mStep
                || (Math.max(p.y, current.y) - Math.min(p.y, current.y)) > mStep;
    }

    private boolean isDiagonalMove(Position p) {
        Position current = history.last();
        return Math.max(p.x, current.x) - Math.min(p.x, current.x)
                == Math.max(p.y, current.y) - Math.min(p.y, current.y);
    }

    private boolean isSame(Position p) {
        Position current = history.last();
        return p.equals(current);
    }

    public void setFieldMovementListener(IFieldMovementListener listener) {
        this.movementListener = listener;
    }

    public Position getCurrent() {
        return history.last();
    }
}
