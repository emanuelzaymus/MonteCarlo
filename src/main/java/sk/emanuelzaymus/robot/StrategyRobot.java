package sk.emanuelzaymus.robot;

import java.util.List;

/**
 * Robot which analyses its position on the playground and makes the best decision of the next move.
 */
public class StrategyRobot extends Robot {

    private final int mapperSize = 10;
    private final int mapperExpandSize = 10;
    private final Position startPosition = new Position(mapperSize / 2, mapperSize / 2);

    private final PlaygroundMapper mapper = new PlaygroundMapper(mapperSize, mapperSize, mapperExpandSize);
    private Position myPosition;
    private boolean firstEndOfPlaygroundFound;

    /**
     * Restarts the robot to the original state.
     * This method needs to be called after creation of this object.
     */
    @Override
    public void restart() {
        super.restart();
        myPosition = startPosition;
        mapper.restart(startPosition);
        firstEndOfPlaygroundFound = false;
    }

    /**
     * Robots selects the best possible decision he can make. Robot prefers to go in the shape of spiral clockwise if it's possible.
     *
     * @param possibleMoves List of possible moves which the robot could potentially make
     * @return Best possible move
     */
    @Override
    public MoveOption move(List<MoveOption> possibleMoves) {
        MoveOption retMove;
        // Save not present move options as end of playground. Returns updated position in case of expansion.
        myPosition = mapper.analyse(possibleMoves, myPosition);

        List<MoveOption> unvisited = mapper.getPossibleMoves(myPosition);

        if (lastMove == null || unvisited.isEmpty()) {
            // If this is the fist move OR I have no choice - choose randomly.
            retMove = possibleMoves.get(randomNextMove(possibleMoves.size()));

        } else if (unvisited.size() == 1) {
            // If I have only one option - choose the option.
            retMove = unvisited.get(0);

        } else {
            // Possible directions
            final var goRight = DirectionalMove.toTheRight(lastMove);
            final var goLeft = DirectionalMove.toTheLeft(lastMove);
            final var goStraight = lastMove;

            if (!unvisited.contains(goStraight)) {
                if (!firstEndOfPlaygroundFound && unvisited.contains(goRight)) {
                    // If I cannot go straight AND I haven't find end of playground yet AND I can go right => GO RIGHT
                    retMove = goRight;
                    firstEndOfPlaygroundFound = true;
                } else if (unvisited.contains(goLeft)) {
                    // If I cannot go straight AND I cannot go right AND I con go left => GO LEFT
                    retMove = goLeft;

                } else throw new IllegalStateException("You should not get here.");

            } else if (unvisited.contains(goRight)) {
                retMove = goRight; // If I can go straight AND I can go right => GO RIGHT

            } else if (unvisited.contains(goStraight)) {
                retMove = goStraight; // If I cannot go right AND I can go straight => GO STRAIGHT

            } else throw new IllegalStateException("You should not get here.");
        }

        myPosition = retMove.moveFromPosition(myPosition);
        mapper.setFieldVisited(myPosition);
        lastMove = retMove;
        return retMove;
    }

    /**
     * Prints its playground mapper state. Debug purpose.
     */
    public void print() {
        mapper.print();
    }

}
