package sk.emanuelzaymus.robot;

import java.util.List;

/**
 * Robot that every time picks his move randomly.
 */
public class RandomRobot extends Robot {

    /**
     * Robot chooses his next move randomly.
     *
     * @param possibleMoves List of possible moves which the robot could potentially make
     * @return Random move
     */
    @Override
    public MoveOption move(List<MoveOption> possibleMoves) {
        if (lastMove != null)
            possibleMoves.remove(lastMove.getOppositeMove());

        final int possibleMovesCount = possibleMoves.size();
        final int nextMove = possibleMovesCount > 1 ? randomNextMove(possibleMovesCount) : 0;

        var retMoveOption = possibleMoves.get(nextMove);
        lastMove = retMoveOption;

        return retMoveOption;
    }

}
