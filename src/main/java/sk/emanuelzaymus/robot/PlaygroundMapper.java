package sk.emanuelzaymus.robot;

import java.util.LinkedList;
import java.util.List;

public class PlaygroundMapper {

    private final int initialMapWidth;
    private final int initialMapHeight;
    private final int expandSize;
    private int width;
    private int height;
    private Boolean[][] fieldMap;

    public PlaygroundMapper(final int initialMapWidth, final int initialMapHeight, final int expandSize) {
        this.initialMapWidth = initialMapWidth;
        this.initialMapHeight = initialMapHeight;
        this.expandSize = expandSize;
    }

    /**
     * This method needs to be called after creation of this object.
     *
     * @param startPosition Robot start position to be marked as visited
     */
    public void restart(final Position startPosition) {
        width = initialMapWidth;
        height = initialMapHeight;
        fieldMap = getEmptyFieldMap(initialMapWidth, initialMapHeight);
        setFieldVisited(startPosition);
    }

    /**
     * Analyses possible moves and sets not present moves as end of playground. Does fieldMap expansion if it is needed.
     *
     * @param possibleMoves Possible moves to be analysed
     * @param myPosition    Robot's current position
     * @return Returns updated robot position in case of fieldMap expansion
     */
    public Position analyse(final List<MoveOption> possibleMoves, final Position myPosition) {
        final var expandedPosition = expandIfNecessary(myPosition);

        if (!possibleMoves.contains(MoveOption.UP)) {
            setFieldEndOfPlayground(expandedPosition.getX(), expandedPosition.getY() - 1);
        }
        if (!possibleMoves.contains(MoveOption.DOWN)) {
            setFieldEndOfPlayground(expandedPosition.getX(), expandedPosition.getY() + 1);
        }
        if (!possibleMoves.contains(MoveOption.LEFT)) {
            setFieldEndOfPlayground(expandedPosition.getX() - 1, expandedPosition.getY());
        }
        if (!possibleMoves.contains(MoveOption.RIGHT)) {
            setFieldEndOfPlayground(expandedPosition.getX() + 1, expandedPosition.getY());
        }
        return expandedPosition;
    }

    public List<MoveOption> getPossibleMoves(final Position myPosition) {
        var ret = new LinkedList<MoveOption>();
        final int x = myPosition.getX();
        final int y = myPosition.getY();

        if (fieldMap[y - 1][x] != null && !fieldMap[y - 1][x]) ret.add(MoveOption.UP);
        if (fieldMap[y][x + 1] != null && !fieldMap[y][x + 1]) ret.add(MoveOption.RIGHT);
        if (fieldMap[y + 1][x] != null && !fieldMap[y + 1][x]) ret.add(MoveOption.DOWN);
        if (fieldMap[y][x - 1] != null && !fieldMap[y][x - 1]) ret.add(MoveOption.LEFT);

        return ret;
    }

    public void setFieldVisited(Position position) {
        setField(position.getX(), position.getY(), true);
    }

    private void setFieldEndOfPlayground(final int x, final int y) {
        setField(x, y, null);
    }

    private void setField(final int x, final int y, final Boolean value) {
        fieldMap[y][x] = value;
    }

    private Boolean[][] getEmptyFieldMap(final int width, final int height) {
        var map = new Boolean[height][width];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                map[y][x] = false;

        return map;
    }

    private Position expandIfNecessary(final Position position) {
        int x = position.getX();
        int y = position.getY();

        if (y - 1 < 0) {
            expandFieldMapUpward();
            y += expandSize;
        }
        if (y + 1 >= height) {
            expandFieldMapDownward();
        }
        if (x - 1 < 0) {
            expandFieldMapLeftward();
            x += expandSize;
        }
        if (x + 1 >= width) {
            expandFieldMapRightward();
        }
        return new Position(x, y);
    }

    private void expandFieldMapUpward() {
        expandFieldMapVertically(true);
    }

    private void expandFieldMapDownward() {
        expandFieldMapVertically(false);
    }

    private void expandFieldMapVertically(final boolean up) {
        final int newHeight = height + expandSize;
        var newFieldMap = getEmptyFieldMap(width, newHeight);

        System.arraycopy(fieldMap, 0, newFieldMap, up ? expandSize : 0, height);

        fieldMap = newFieldMap;
        height = newHeight;
    }

    private void expandFieldMapLeftward() {
        expandFieldMapHorizontally(true);
    }

    private void expandFieldMapRightward() {
        expandFieldMapHorizontally(false);
    }

    private void expandFieldMapHorizontally(final boolean left) {
        final int newWidth = width + expandSize;
        var newFieldMap = getEmptyFieldMap(newWidth, height);

        for (int i = 0; i < height; i++) {
            System.arraycopy(fieldMap[i], 0, newFieldMap[i], left ? expandSize : 0, width);
        }

        fieldMap = newFieldMap;
        width = newWidth;
    }

    public void print() {
        System.out.println("PlaygroundMapper:");
        for (var line : fieldMap) {
            for (var b : line) {
                System.out.print(" " + (b == null ? 'X' : b ? 't' : 'f'));
            }
            System.out.println();
        }
        System.out.println();
    }

}
