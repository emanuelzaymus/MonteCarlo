package sk.emanuelzaymus.robot;

import sk.emanuelzaymus.montecarlo.IntRandom;

import java.util.List;

public class Robot {

    private final IntRandom twoWayRandom = new IntRandom(2);
    private final IntRandom threeWayRandom = new IntRandom(3);
    private final IntRandom fourWayRandom = new IntRandom(4);

    private MoveOption lastMove;

    public Robot(boolean useCustomStrategy) {

    }

    public void restart() {
        lastMove = null;
    }

    public MoveOption move(List<MoveOption> possibleMoves) {
        if (lastMove != null)
            possibleMoves.remove(lastMove.getOppositeMove());

        final int possibleMovesCount = possibleMoves.size();
        final int nextMove = possibleMovesCount > 1 ? randomNextMove(possibleMovesCount) : 0;

        var retMoveOption = possibleMoves.get(nextMove);
        lastMove = retMoveOption;

        return retMoveOption;
    }

    private int randomNextMove(final int possibleMovesCount) {
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
