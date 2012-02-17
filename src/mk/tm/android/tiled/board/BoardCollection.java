package mk.tm.android.tiled.board;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tomislav.Markovski
 * Date: 2/10/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardCollection extends ArrayList<Field> {
    public Field get(Position position) {
        for (Field field : this) {
            if (field.position.equals(position))
                return field;
        }
        return null;
    }

    public Position last() {
        return this.get(this.size() - 1).getPosition();
    }

    public Position beforeLast() {
        return this.get(this.size() - 2).getPosition();
    }
}
