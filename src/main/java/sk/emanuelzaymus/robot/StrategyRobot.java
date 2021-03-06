package sk.emanuelzaymus.robot;

import java.util.List;

public class StrategyRobot extends Robot {

    private final int mapperSize = 10;
    private final int mapperExpandSize = 10;
    private final Position startPosition = new Position(mapperSize / 2, mapperSize / 2);

    private final PlaygroundMapper mapper = new PlaygroundMapper(mapperSize, mapperExpandSize);
    private Position myPosition;
    private boolean firstEndOfPlaygroundFound = false;

    public StrategyRobot() {
        restart();
    }

    @Override
    public void restart() {
        super.restart();
        myPosition = startPosition;
        mapper.restart(startPosition);
    }

    @Override
    public MoveOption move(List<MoveOption> possibleMoves) {
        MoveOption ret;
        // Save not present move options as end of playground.
        mapper.analyse(possibleMoves, myPosition);
        // myPosition = mapper.analyse(possibleMoves, myPosition); // TODO

        if (lastMove == null) {
            // If this is the fist move - choose randomly.
            ret = possibleMoves.get(randomNextMove(possibleMoves.size()));
        } else {
            List<MoveOption> unvisited = mapper.getPossibleMoves(myPosition);

            // If I cannot go straight AND I haven't find end of playground yet => GO RIGHT
            if (!unvisited.contains(lastMove) && !firstEndOfPlaygroundFound) {
                ret = DirectionalMoves.toTheRight(lastMove);
                firstEndOfPlaygroundFound = true;

            } else if (!unvisited.contains(lastMove)) {
                ret = DirectionalMoves.toTheLeft(lastMove); // If I cannot go straight => GO LEFT

            } else if (unvisited.contains(DirectionalMoves.toTheRight(lastMove))) {
                ret = DirectionalMoves.toTheRight(lastMove); // If I can go right => GO RIGHT

            } else {
                ret = DirectionalMoves.toTheLeft(lastMove); // Else => GO LEFT
            }
        }

        myPosition = getNewPosition(ret);
        mapper.setFieldVisited(myPosition);
        lastMove = ret;
        return ret;
    }

    private Position getNewPosition(final MoveOption move) {
        int x = myPosition.getX();
        int y = myPosition.getY();

        if (move == MoveOption.UP) y--;
        else if (move == MoveOption.DOWN) y++;
        else if (move == MoveOption.RIGHT) x++;
        else if (move == MoveOption.LEFT) x--;
        else throw new IllegalStateException("Next move was probably null.");

        return new Position(x, y);
    }

}
