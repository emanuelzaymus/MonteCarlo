package sk.emanuelzaymus.robot;

import java.util.List;

public class RandomRobot extends Robot {

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
