package sk.emanuelzaymus.montecarlo;

import java.util.Random;

/**
 * Pseudo-random seed generator.
 */
public class SeedGenerator {

    private static final Random seedGenerator = new Random();
    private static long lastSeed;

    /**
     * Next random seed for initialization of new random generator. Will not return two same seeds after each other.
     *
     * @return Next random seed
     */
    public static long nextSeed() {
        while (true) {
            long nextSeed = seedGenerator.nextLong();

            if (nextSeed != lastSeed) {
                lastSeed = nextSeed;
                return nextSeed;
            }
        }
    }

    /**
     * Sets seed for SeedGenerator.
     *
     * @param seed Seed to set
     */
    public static void setSeed(long seed) {
        seedGenerator.setSeed(seed);
    }

}
