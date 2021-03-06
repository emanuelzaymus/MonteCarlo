package sk.emanuelzaymus.robot;

import java.util.LinkedList;
import java.util.List;

public class PlaygroundMapper {

    private final int expandSize;
    private int width; // xMin xMax
    private int height; // yMin yMax TODO
    private Boolean[][] fieldMap;

    public PlaygroundMapper(final int size, final int expandSize) {
        this.expandSize = expandSize;
        width = size;
        height = size;
    }

    public void restart(final Position startPosition) {
        fieldMap = getEmptyFieldMap(height, width);
        setFieldVisited(startPosition);
    }

    // Returns updated position in case of expansion
    // public Position analyse(final List<MoveOption> possibleMoves, final Position myPosition)
    public void analyse(final List<MoveOption> possibleMoves, final Position myPosition) {
        if (!possibleMoves.contains(MoveOption.UP))
            setFieldEndOfPlayground(myPosition.getX(), myPosition.getY() - 1);

        if (!possibleMoves.contains(MoveOption.DOWN))
            setFieldEndOfPlayground(myPosition.getX(), myPosition.getY() + 1);

        if (!possibleMoves.contains(MoveOption.LEFT))
            setFieldEndOfPlayground(myPosition.getX() - 1, myPosition.getY());

        if (!possibleMoves.contains(MoveOption.RIGHT))
            setFieldEndOfPlayground(myPosition.getX() + 1, myPosition.getY());
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

    private Boolean[][] getEmptyFieldMap(final int height, final int width) {
        var map = new Boolean[height][width];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                map[y][x] = false;

        return map;
    }

    public void setFieldVisited(Position position) {
        setField(position.getX(), position.getY(), true);
    }

    private void setFieldEndOfPlayground(final int x, final int y) {
        setField(x, y, null);
    }

    private void setField(final int x, final int y, final Boolean value) {
        checkFiledMapArea(x, y);
        fieldMap[y][x] = value;
    }

    private void checkFiledMapArea(final int x, final int y) {
        // TODO expanziou sa to cele pokazi, prestane platit myPosition - mozno zmenit myPosition
        if (y <= 0) // 0 ???
            expandFieldMapUpward();
        if (y >= height)
            expandFieldMapDownward();
        if (x <= 0) // 0 ???
            expandFieldMapLeftward();
        if (x >= width)
            expandFieldMapRightward();
    }

    private void expandFieldMapUpward() {
        expandFieldMapVertically(true);
    }

    private void expandFieldMapDownward() {
        expandFieldMapVertically(false);
    }

    private void expandFieldMapVertically(final boolean up) {
        final int newHeight = height + expandSize;
        var newFieldMap = getEmptyFieldMap(newHeight, width);

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
        var newFieldMap = getEmptyFieldMap(height, newWidth);

        for (int i = 0; i < height; i++) {
            System.arraycopy(fieldMap[i], 0, newFieldMap[i], left ? expandSize : 0, width);
        }

        fieldMap = newFieldMap;
        width = newWidth;
    }

}
