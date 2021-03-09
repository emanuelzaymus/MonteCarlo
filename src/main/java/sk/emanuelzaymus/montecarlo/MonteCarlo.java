package sk.emanuelzaymus.montecarlo;

/**
 * Monte Carlo simulation base class.
 */
public abstract class MonteCarlo implements Runnable {

    private final int replicationsCount;
    protected int currentReplicNumber;
    private boolean stopped = false;

    /**
     * Monte Carlo simulation constructor.
     *
     * @param replicationsCount Number of replication to execute the simulation
     */
    public MonteCarlo(int replicationsCount) {
        this.replicationsCount = replicationsCount;
    }

    /**
     * @return Whether the simulation is stopped
     */
    public final synchronized boolean isStopped() {
        return stopped;
    }

    /**
     * Stops execution of the simulation.
     */
    public final synchronized void stop() {
        stopped = true;
    }

    /**
     * Run the simulation as Runnable.
     */
    @Override
    public void run() {
        simulate();
    }

    /**
     * Starts execution of the simulation.
     */
    public final void simulate() {
        beforeSimulation();

        for (int i = 0; i < replicationsCount; i++) {
            currentReplicNumber = i;

            beforeReplication();
            doReplication();
            afterReplication();

            if (isStopped()) break;
        }

        afterSimulation();
    }

    /**
     * Is called only once before execution of the simulation.
     */
    protected void beforeSimulation() {
    }

    /**
     * Is Called before every single replication - before doReplication() method is called. Is called replicationsCount times.
     */
    protected void beforeReplication() {
    }

    /**
     * Atomic replication of the simulation. Is called replicationsCount times.
     */
    protected abstract void doReplication();

    /**
     * Is Called after every replication - after doReplication() method is called. Is called replicationsCount times.
     */
    protected void afterReplication() {
    }

    /**
     * Is called only once after execution of the simulation.
     */
    protected void afterSimulation() {
    }

}
