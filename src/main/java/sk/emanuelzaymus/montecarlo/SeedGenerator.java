package sk.emanuelzaymus.montecarlo;

import java.util.Random;

public class SeedGenerator {

    private static final Random seedGenerator = new Random();
    private static long lastSeed;

    public static long nextSeed() {
        while (true) {
            long nextSeed = seedGenerator.nextLong();

            if (nextSeed != lastSeed) {
                lastSeed = nextSeed;
                return nextSeed;
            }
        }
    }

    public static void setSeed(long seed) {
        seedGenerator.setSeed(seed);
    }

}
