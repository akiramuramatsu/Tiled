package mk.tm.android.tiled.board;

public class Field implements IPosition {
    FieldState state;
    Position position;

    @Override
    public boolean equals(Object obj) {

        return obj != null
                && ((Field) obj).position.equals(this.position);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public FieldState getState(){
        return this.state;
    }
}
