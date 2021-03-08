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

    /**
     * Move from the position in the direction of this object.
     *
     * @param position Position from witch you want to move
     * @return New position after moving position to the direction of this MoveOption
     */
    public Position moveFromPosition(final Position position) {
        int x = position.getX();
        int y = position.getY();

        if (this == MoveOption.UP) y--;
        else if (this == MoveOption.DOWN) y++;
        else if (this == MoveOption.RIGHT) x++;
        else if (this == MoveOption.LEFT) x--;
        else throw new IllegalStateException("Next move was probably null.");

        return new Position(x, y);
    }

}
