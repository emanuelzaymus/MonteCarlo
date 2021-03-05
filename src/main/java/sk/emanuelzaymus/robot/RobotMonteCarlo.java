package sk.emanuelzaymus.robot;

import javafx.util.Pair;
import sk.emanuelzaymus.montecarlo.MonteCarlo;

import java.util.LinkedList;
import java.util.List;

public class RobotMonteCarlo extends MonteCarlo {

    private final RobotRun robotRun;
    private final int kMoves;
    private final int saveFromReplic;
    private final int saveEveryReplic;

    private final List<Pair<Integer, Double>> savedEstimations;
    private double averageMovesCount;
    private final List<Pair<Integer, Double>> moreThanKMovesProbabilities;
    private double moreThanKMovesProbability;

    public RobotMonteCarlo(RobotRun robotRun, final int replicationsCount, final double skipPercent,
                           final int kMoves, final int estimationsCount) {
        super(replicationsCount);
        if (robotRun == null) throw new IllegalArgumentException("RobotRun is null.");

        this.robotRun = robotRun;
        this.kMoves = kMoves;
        saveFromReplic = (int) Math.ceil(replicationsCount * skipPercent);
        saveEveryReplic = replicationsCount - saveFromReplic > estimationsCount
                ? (replicationsCount - saveFromReplic) / estimationsCount
                : 1;
        savedEstimations = new LinkedList<>();
        moreThanKMovesProbabilities = new LinkedList<>();
    }

    public List<Pair<Integer, Double>> getSavedEstimations() {
        return savedEstimations;
    }

    public double getAverageMovesCount() {
        return averageMovesCount;
    }

    public double getMoreThanKMovesProbability() {
        return moreThanKMovesProbability;
    }

    public List<Pair<Integer, Double>> getMoreThanKMovesProbabilities() {
        return moreThanKMovesProbabilities;
    }

    @Override
    protected void beforeSimulation() {
        savedEstimations.clear();
        averageMovesCount = 0;
        moreThanKMovesProbabilities.clear();
        moreThanKMovesProbability = 0;
    }

    @Override
    protected void beforeReplication() {
        robotRun.restart();
    }

    @Override
    protected void doReplication() {
        robotRun.run();
    }

    @Override
    protected void afterReplication() {
        int movesCount = robotRun.getMovesCount();
        averageMovesCount = countAverageMovesCount(movesCount);
        moreThanKMovesProbability = countMoreThanKMovesProbability(movesCount);

        if (saveEstimation()) {
            savedEstimations.add(new Pair<>(currentReplicNumber, averageMovesCount));
//            System.out.printf("%d. repl = %d  -> avg: %f\n", currentReplicNumber, movesCount, averageMovesCount);
            moreThanKMovesProbabilities.add(new Pair<>(currentReplicNumber, moreThanKMovesProbability));
        }
    }

    private boolean saveEstimation() {
        if (saveFromReplic > currentReplicNumber) {
            return false;
        }
        return currentReplicNumber % saveEveryReplic == 0;
    }

    private double countAverageMovesCount(final int newMovesCount) {
        return (averageMovesCount * currentReplicNumber + newMovesCount) / (currentReplicNumber + 1);
    }

    private double countMoreThanKMovesProbability(final int newMovesCount) {
        final int i = newMovesCount > kMoves ? 1 : 0;
        return (moreThanKMovesProbability * currentReplicNumber + i) / (currentReplicNumber + 1);
    }

}
