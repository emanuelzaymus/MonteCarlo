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
        restart();
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void restart() {
        playground.restart(startPosition);
        robot.restart();
        robotPosition = startPosition;
        movesCount = 0;
    }

    public void run() {
        //noinspection UnusedAssignment
        boolean wasThere = false;

        do {
            var possibleMoves = playground.getPossibleMoves(robotPosition);
            var nextMove = robot.move(possibleMoves);

            robotPosition = playground.getNewPosition(robotPosition, nextMove);
            wasThere = playground.wasHere(robotPosition);

            if (!wasThere) {
                playground.markFieldVisited(robotPosition);
                movesCount++;
            }
        } while (!wasThere);
    }

}
