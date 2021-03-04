package sk.emanuelzaymus.robot;

import java.util.LinkedList;
import java.util.List;

public class Playground {

    private final int width;
    private final int height;
    private final boolean[][] visitedFields;

    public Playground(int width, int height) {
        this.width = width;
        this.height = height;
        visitedFields = new boolean[height][width];
    }

    /**
     * Playground needs to be restarted start and start position set by this method.
     *
     * @param startPosition Start position of robot which is visited from the beginning.
     */
    public void restart(Position startPosition) {
        for (var line : visitedFields) {
            for (int i = 0, lineLength = line.length; i < lineLength; i++)
                line[i] = false;
        }
        markFieldVisited(startPosition);
    }

    public List<MoveOption> getPossibleMoves(Position robotPosition) {
        var ret = new LinkedList<MoveOption>();

        if (robotPosition.getY() > 0) ret.add(MoveOption.UP);
        if (robotPosition.getX() < width - 1) ret.add(MoveOption.RIGHT);
        if (robotPosition.getY() < height - 1) ret.add(MoveOption.DOWN);
        if (robotPosition.getX() > 0) ret.add(MoveOption.LEFT);

        return ret;
    }

    public Position getNewPosition(Position robotPosition, MoveOption nextMove) {
        int x = robotPosition.getX();
        int y = robotPosition.getY();

        if (nextMove == MoveOption.UP) y--;
        else if (nextMove == MoveOption.DOWN) y++;
        else if (nextMove == MoveOption.RIGHT) x++;
        else if (nextMove == MoveOption.LEFT) x--;
        else throw new IllegalStateException("Next move was probably null.");

        return new Position(x, y);
    }

    public boolean wasHere(Position robotPosition) {
        final int x = robotPosition.getX();
        final int y = robotPosition.getY();

        if (x >= 0 && x < width && y >= 0 && y < height) {
            return visitedFields[y][x];
        }
        throw new IllegalStateException("Robot is out of the playground");
    }

    public void markFieldVisited(Position robotPosition) {
        final int x = robotPosition.getX();
        final int y = robotPosition.getY();

        if (!visitedFields[y][x]) {
            visitedFields[y][x] = true;
        } else
            throw new IllegalStateException("Robot was already here.");
    }

//    public int visitedFieldsCount() {
//        int ret = 0;
//
//        for (var line : visitedFields)
//            for (boolean visited : line)
//                if (visited) ret++;
//
//        return ret;
//    }

}
