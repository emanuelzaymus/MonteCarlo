package sk.emanuelzaymus.robot;

public class DirectionalMove {

    /**
     * @param lastMove Last move direction from which you want to go to the right
     * @return Relative MoveOption to the right based on lastMove MoveOption
     */
    public static MoveOption toTheRight(final MoveOption lastMove) {
        return switch (lastMove) {
            case UP -> MoveOption.RIGHT;
            case RIGHT -> MoveOption.DOWN;
            case DOWN -> MoveOption.LEFT;
            case LEFT -> MoveOption.UP;
        };
    }

    /**
     * @param lastMove Last move direction from which you want to go to the left
     * @return Relative MoveOption to the left based on lastMove MoveOption
     */
    public static MoveOption toTheLeft(final MoveOption lastMove) {
        return switch (lastMove) {
            case UP -> MoveOption.LEFT;
            case RIGHT -> MoveOption.UP;
            case DOWN -> MoveOption.RIGHT;
            case LEFT -> MoveOption.DOWN;
        };
    }

}
