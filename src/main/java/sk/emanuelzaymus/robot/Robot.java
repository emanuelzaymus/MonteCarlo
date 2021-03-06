package sk.emanuelzaymus.robot;

import sk.emanuelzaymus.montecarlo.IntRandom;

import java.util.List;

public abstract class Robot {

    private final IntRandom twoWayRandom = new IntRandom(2);
    private final IntRandom threeWayRandom = new IntRandom(3);
    private final IntRandom fourWayRandom = new IntRandom(4);

    protected MoveOption lastMove;

    public void restart() {
        lastMove = null;
    }

    public abstract MoveOption move(List<MoveOption> possibleMoves);

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
