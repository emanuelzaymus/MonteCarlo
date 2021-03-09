package sk.emanuelzaymus.robot;

import java.util.LinkedList;
import java.util.List;

/**
 * Playground on which the robot will be moving.
 */
public class Playground {

    private final int width;
    private final int height;
    private final boolean[][] visitedFields;

    /**
     * Playground on which the robot will be moving.
     *
     * @param width  Width of the playground
     * @param height Height of the playground
     */
    public Playground(int width, int height) {
        this.width = width;
        this.height = height;
        visitedFields = new boolean[height][width];
    }

    /**
     * Playground needs to be restarted start and start position set by this method.
     *
     * @param startPosition Start position of robot which is visited from the beginning.
     */
    public void restart(Position startPosition) {
        for (var line : visitedFields) {
            for (int i = 0, lineLength = line.length; i < lineLength; i++)
                line[i] = false;
        }
        markFieldVisited(startPosition);
    }

    /**
     * Get all possible move options where robot can go from his current position robotPosition.
     * Options that collide with end of playground will not be returned.
     *
     * @param robotPosition Robots current position
     * @return All possible move options where the robot may go
     */
    public List<MoveOption> getPossibleMoves(Position robotPosition) {
        var ret = new LinkedList<MoveOption>();

        if (robotPosition.getY() > 0) ret.add(MoveOption.UP);
        if (robotPosition.getX() < width - 1) ret.add(MoveOption.RIGHT);
        if (robotPosition.getY() < height - 1) ret.add(MoveOption.DOWN);
        if (robotPosition.getX() > 0) ret.add(MoveOption.LEFT);

        return ret;
    }

    /**
     * @param robotPosition Robots current position
     * @return Whether robot has already visited his current position robotPosition
     */
    public boolean wasHere(Position robotPosition) {
        final int x = robotPosition.getX();
        final int y = robotPosition.getY();

        if (x >= 0 && x < width && y >= 0 && y < height) {
            return visitedFields[y][x];
        }
        throw new IllegalStateException("Robot is out of the playground.");
    }

    /**
     * Set current robotPosition to be visited.
     *
     * @param robotPosition Position to be set to visited
     */
    public void markFieldVisited(Position robotPosition) {
        final int x = robotPosition.getX();
        final int y = robotPosition.getY();

        if (!visitedFields[y][x]) {
            visitedFields[y][x] = true;
        } else
            throw new IllegalStateException("Robot was already here.");
    }

    /**
     * Print current playground state. Debug Purpose.
     */
    public void print() {
        System.out.println("Playground:");
        for (var line : visitedFields) {
            for (var b : line) {
                System.out.print(" " + (b ? 'T' : 'F'));
            }
            System.out.println();
        }
        System.out.println();
    }

}
