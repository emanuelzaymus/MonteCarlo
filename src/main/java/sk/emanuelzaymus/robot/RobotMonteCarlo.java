package sk.emanuelzaymus.robot;

import javafx.application.Platform;
import javafx.util.Pair;
import sk.emanuelzaymus.montecarlo.MonteCarlo;

import java.util.LinkedList;
import java.util.List;

/**
 * Monte Carlo simulation for the robot on the playground problem.
 */
public class RobotMonteCarlo extends MonteCarlo {

    private final RobotRun robotRun;
    private final int kMoves;
    private final int saveFromReplic;
    private final int saveEveryReplic;
    private final Runnable afterSimulationCallback;

    private final List<Pair<Integer, Double>> savedEstimations;
    private double averageMovesCount;
    private final List<Pair<Integer, Double>> moreThanKMovesProbabilities;
    private double moreThanKMovesProbability;

    /**
     * Creates new Robot Monte Carlo simulation.
     *
     * @param robotRun                RobotRun to execute every replication
     * @param replicationsCount       Number of replications to execute
     * @param skipPercent             How many percent of the estimations to skip before saving
     * @param kMoves                  Count probability of the robot passing more than K moves before failing
     * @param estimationsCount        How many estimations to save
     * @param afterSimulationCallback After simulation callback method
     */
    public RobotMonteCarlo(final RobotRun robotRun, final int replicationsCount, final double skipPercent,
                           final int kMoves, final int estimationsCount, final Runnable afterSimulationCallback) {
        super(replicationsCount);
        if (robotRun == null) throw new IllegalArgumentException("RobotRun is null.");

        this.robotRun = robotRun;
        this.kMoves = kMoves;
        saveFromReplic = (int) Math.ceil(replicationsCount * skipPercent);
        saveEveryReplic = replicationsCount - saveFromReplic > estimationsCount
                ? (replicationsCount - saveFromReplic) / estimationsCount
                : 1;
        this.afterSimulationCallback = afterSimulationCallback;
        savedEstimations = new LinkedList<>();
        moreThanKMovesProbabilities = new LinkedList<>();
    }

    /**
     * @return Saved estimations of the average number of moves the robot has done
     */
    public List<Pair<Integer, Double>> getSavedEstimations() {
        return savedEstimations;
    }

    /**
     * @return Average number of moves the robot has done
     */
    public double getAverageMovesCount() {
        return averageMovesCount;
    }

    /**
     * @return Probability of the robot making more than K moves
     */
    public double getMoreThanKMovesProbability() {
        return moreThanKMovesProbability;
    }

    /**
     * @return Saved estimations of probabilities that the robot makes more than K moves
     */
    public List<Pair<Integer, Double>> getMoreThanKMovesProbabilities() {
        return moreThanKMovesProbabilities;
    }

    /**
     * Clears all results.
     */
    @Override
    protected void beforeSimulation() {
        savedEstimations.clear();
        averageMovesCount = 0;
        moreThanKMovesProbabilities.clear();
        moreThanKMovesProbability = 0;
    }

    /**
     * Restarts robot run.
     */
    @Override
    protected void beforeReplication() {
        robotRun.restart();
    }

    /**
     * Runs robot run.
     */
    @Override
    protected void doReplication() {
        robotRun.run();
    }

    /**
     * Counts average number of count and probability of passing more than K moves
     * from current replication run of the robot. Saves estimations.
     */
    @Override
    protected void afterReplication() {
        int movesCount = robotRun.getMovesCount();
        averageMovesCount = countAverageMovesCount(movesCount);
        moreThanKMovesProbability = countMoreThanKMovesProbability(movesCount);

        if (saveEstimation()) {
            savedEstimations.add(new Pair<>(currentReplicNumber, averageMovesCount));
            moreThanKMovesProbabilities.add(new Pair<>(currentReplicNumber, moreThanKMovesProbability));
        }
    }

    /**
     * Displays the results.
     */
    @Override
    protected void afterSimulation() {
        Platform.runLater(afterSimulationCallback);
    }

    /**
     * @return Whether to save current replication result or not
     */
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
