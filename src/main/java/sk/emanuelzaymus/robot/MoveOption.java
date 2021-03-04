package sk.emanuelzaymus.robot;

public enum MoveOption {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public MoveOption getOppositeMove() {
        return switch (this) {
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
        };
    }

}
