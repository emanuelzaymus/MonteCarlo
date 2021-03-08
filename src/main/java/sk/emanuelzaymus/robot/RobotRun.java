package sk.emanuelzaymus.robot;

public class RobotRun {

    private final Playground playground;
    private final Robot robot;
    private final Position startPosition;

    private Position robotPosition;
    private int movesCount;

    public RobotRun(final Playground playground, final Robot robot, final Position startPosition) {
        if (playground == null) throw new IllegalArgumentException("Playground is null.");
        if (robot == null) throw new IllegalArgumentException("Robot is null.");

        this.playground = playground;
        this.robot = robot;
        this.startPosition = startPosition;
    }

    public int getMovesCount() {
        return movesCount;
    }

    /**
     * This method needs to be called after creation of this object.
     */
    public void restart() {
        playground.restart(startPosition);
        robot.restart();
        robotPosition = startPosition;
        movesCount = 0;
    }

    public void run() {
        while (true) {
            final var possibleMoves = playground.getPossibleMoves(robotPosition);
            final var nextMove = robot.move(possibleMoves);

            robotPosition = nextMove.moveFromPosition(robotPosition);
            movesCount++;

            if (!playground.wasHere(robotPosition)) {
                playground.markFieldVisited(robotPosition);
            } else {
                break;
            }
        }
    }

    public void print() {
        playground.print();
        ((StrategyRobot) robot).print();
    }

}
