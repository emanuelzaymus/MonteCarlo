package sk.emanuelzaymus.robot;

/**
 * Immutable X,Y position.
 */
public class Position {

    private final int x;
    private final int y;

    /**
     * Creates immutable X,Y position.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

}
