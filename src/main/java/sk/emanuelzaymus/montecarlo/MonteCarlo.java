package sk.emanuelzaymus.montecarlo;

public abstract class MonteCarlo implements Runnable {

    private final int replicationsCount;
    protected int currentReplicNumber;
    private boolean stopped = false;

    public MonteCarlo(int replicationsCount) {
        this.replicationsCount = replicationsCount;
    }

    public final synchronized boolean isStopped() {
        return stopped;
    }

    public final synchronized void stop() {
        stopped = true;
    }

    @Override
    public void run() {
        simulate();
    }

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

    protected void beforeSimulation() {
    }

    protected void beforeReplication() {
    }

    protected abstract void doReplication();

    protected void afterReplication() {
    }

    protected void afterSimulation() {
    }

}
