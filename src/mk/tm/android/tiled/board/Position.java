package mk.tm.android.tiled.board;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/7/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Position {

    public final int x;
    public final int y;

    private FieldState state;
    private IStateChangedListener mStateChangedListener;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public FieldState getState() {
        return this.state;
    }

    public void setState(FieldState state) {
        this.state = state;
        if (this.mStateChangedListener != null)
            this.mStateChangedListener.stateChanged(this);
    }

    public void setStateChangedListener(IStateChangedListener stateChangedListener) {
        this.mStateChangedListener = stateChangedListener;
    }
}
