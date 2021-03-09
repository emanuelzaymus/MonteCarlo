package sk.emanuelzaymus.robot;

/**
 * Represent one run of the robot.
 */
public class RobotRun {

    private final Playground playground;
    private final Robot robot;
    private final Position startPosition;

    private Position robotPosition;
    private int movesCount;

    /**
     * Creates one run of the robot. For more runs it needs to be restarted to the original state.
     *
     * @param playground    Playground on which the robot will be moving
     * @param robot         The robot
     * @param startPosition Start position of the robot on the playground - needs to be valid position on the playground
     */
    public RobotRun(final Playground playground, final Robot robot, final Position startPosition) {
        if (playground == null) throw new IllegalArgumentException("Playground is null.");
        if (robot == null) throw new IllegalArgumentException("Robot is null.");

        this.playground = playground;
        this.robot = robot;
        this.startPosition = startPosition;
    }

    /**
     * @return Number of moves the robot has done
     */
    public int getMovesCount() {
        return movesCount;
    }

    /**
     * Restarts the Robot Run to the original state.
     * This method needs to be called after creation of this object.
     */
    public void restart() {
        playground.restart(startPosition);
        robot.restart();
        robotPosition = startPosition;
        movesCount = 0;
    }

    /**
     * Run robot. Robot moves until he comes to already visited field.
     */
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

    /**
     * Print state. Debug purpose.
     */
    public void print() {
        playground.print();
        ((StrategyRobot) robot).print();
    }

}
