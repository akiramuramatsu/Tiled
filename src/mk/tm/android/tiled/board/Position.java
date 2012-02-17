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

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null
                && ((Position) obj).x == this.x
                && ((Position) obj).y == this.y;
    }

    public Position clone() {
        return new Position(this.x, this.y);
    }
}
