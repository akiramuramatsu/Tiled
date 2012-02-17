package mk.tm.android.tiled.board;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 17.2.12
 * Time: 01:30
 * To change this template use File | Settings | File Templates.
 */
public interface IFieldMovementListener {
    public void onFieldChanged(Position current, Position last);
}
