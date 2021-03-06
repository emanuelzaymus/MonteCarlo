package sk.emanuelzaymus.robot;

import java.util.List;

public class StrategyRobot extends Robot {

    @Override
    public MoveOption move(List<MoveOption> possibleMoves) {
        if (lastMove == null) {
            return possibleMoves.get(randomNextMove(possibleMoves.size()));
        }

        // TODO

        return null;
    }

}
