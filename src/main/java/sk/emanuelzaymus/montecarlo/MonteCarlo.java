package sk.emanuelzaymus.montecarlo;

public abstract class MonteCarlo {

    private final int replicationsCount;
    protected int currentReplicNumber;

    public MonteCarlo(int replicationsCount) {
        this.replicationsCount = replicationsCount;
    }

    public final void simulate() {
        beforeSimulation();

        for (int i = 0; i < replicationsCount; i++) {
            currentReplicNumber = i;

            beforeReplication();
            doReplication();
            afterReplication();
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
