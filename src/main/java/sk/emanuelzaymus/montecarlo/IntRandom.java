package sk.emanuelzaymus.montecarlo;

import java.util.Random;

/**
 * Pseudo-random integer generator.
 */
public class IntRandom {

    private final Random random = new Random(SeedGenerator.nextSeed());
    private final int bound;

    /**
     * Pseudo-random integer generator which generates numbers from 0 to bound.
     *
     * @param bound Upper bound of the interval
     */
    public IntRandom(final int bound) {
        this.bound = bound;
    }

    /**
     * @return Next random sample from this random generator.
     */
    public int next() {
        return random.nextInt(bound);
    }

}
