package sk.emanuelzaymus.robot;

import sk.emanuelzaymus.montecarlo.IntRandom;

import java.util.List;

/**
 * Base class of the robot.
 */
public abstract class Robot {

    private final IntRandom twoWayRandom = new IntRandom(2);
    private final IntRandom threeWayRandom = new IntRandom(3);
    private final IntRandom fourWayRandom = new IntRandom(4);

    protected MoveOption lastMove;

    /**
     * Restarts the robot to the original state.
     */
    public void restart() {
        lastMove = null;
    }

    /**
     * One more of the robot.
     *
     * @param possibleMoves List of possible moves which the robot could potentially make
     * @return Decision of the robot which direction he wants to go
     */
    public abstract MoveOption move(List<MoveOption> possibleMoves);

    /**
     * Returns next pseudo-random integer from one of three possible random generators.
     *
     * @param possibleMovesCount Upper bound of the interval - Has to be between 2 and 4
     * @return Random integer between 0 and possibleMovesCount parameter
     */
    protected int randomNextMove(final int possibleMovesCount) {
        return getNWayRandom(possibleMovesCount).next();
    }

    private IntRandom getNWayRandom(final int nWays) {
        return switch (nWays) {
            case 2 -> twoWayRandom;
            case 3 -> threeWayRandom;
            case 4 -> fourWayRandom;
            default -> throw new RuntimeException("More ways than it should be.");
        };
    }

}
