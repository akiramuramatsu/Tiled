package mk.tm.android.tiled.board;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardCollection extends ArrayList<Position> {

    public Position get(Position position) {
        for (Position field : this) {
            if (field.equals(position))
                return field;
        }
        return null;
    }
}
