package mk.tm.android.tiled.board;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class History extends ArrayList<Position> {
    public boolean contains(Position position) {
        for (Position p : this)
            if (p.equals(position))
                return true;
        return false;
    }
}
