package sk.emanuelzaymus.robot;

public class DirectionalMove {

    public static MoveOption toTheRight(final MoveOption lastMove) {
        return switch (lastMove) {
            case UP -> MoveOption.RIGHT;
            case RIGHT -> MoveOption.DOWN;
            case DOWN -> MoveOption.LEFT;
            case LEFT -> MoveOption.UP;
        };
    }

    public static MoveOption toTheLeft(final MoveOption lastMove) {
        return switch (lastMove) {
            case UP -> MoveOption.LEFT;
            case RIGHT -> MoveOption.UP;
            case DOWN -> MoveOption.RIGHT;
            case LEFT -> MoveOption.DOWN;
        };
    }

}
