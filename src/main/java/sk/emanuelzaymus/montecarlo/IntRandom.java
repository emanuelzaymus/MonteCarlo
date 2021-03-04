package sk.emanuelzaymus.montecarlo;

import java.util.Random;

public class IntRandom {

    private final Random random = new Random(SeedGenerator.nextSeed());
    private final int bound;

    public IntRandom(final int bound) {
        this.bound = bound;
    }

    public int next() {
        return random.nextInt(bound);
    }

}
