package sk.emanuelzaymus.robot;

import javafx.util.Pair;
import sk.emanuelzaymus.montecarlo.MonteCarlo;

import java.util.LinkedList;
import java.util.List;

public class RobotMonteCarlo extends MonteCarlo {

    private final RobotRun robotRun;
    private final int saveFromReplic;
    private final int saveEveryReplic;

    private final List<Pair<Integer, Double>> savedEstimations;
    private double averageMovesCount;

    public RobotMonteCarlo(RobotRun robotRun, final int replicationsCount, final double skipPercent, final int estimationsCount) {
        super(replicationsCount);
        if (robotRun == null) throw new IllegalArgumentException("RobotRun is null.");

        this.robotRun = robotRun;
        saveFromReplic = (int) Math.ceil(replicationsCount * skipPercent);
        saveEveryReplic = replicationsCount - saveFromReplic > estimationsCount
                ? (replicationsCount - saveFromReplic) / estimationsCount
                : 1;
        savedEstimations = new LinkedList<>();
    }

    public List<Pair<Integer, Double>> getSavedEstimations() {
        return savedEstimations;
    }

    public double getAverageMovesCount() {
        return averageMovesCount;
    }

    @Override
    protected void beforeSimulation() {
        savedEstimations.clear();
        averageMovesCount = 0;
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

        if (saveEstimation()) {
            savedEstimations.add(new Pair<>(currentReplicNumber, averageMovesCount));
//            System.out.printf("%d. repl = %d  -> avg: %f\n", currentReplicNumber, movesCount, averageMovesCount);
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

}
