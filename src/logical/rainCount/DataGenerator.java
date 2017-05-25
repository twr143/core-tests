package logical.rainCount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Created by ilya on 09.12.2016.
 */
public class DataGenerator {
    public static final int nCores = Runtime.getRuntime().availableProcessors();
    static int j2;

    public static long[] concurrArrayFillFixedFP(final int size, final int nWaves, final int nThreads) {
        long startTime = System.currentTimeMillis();
        final double size_d = size;
        final long[] source = new long[size];
        final Phaser ph = new Phaser(nThreads + 1) {
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase >= 0;
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(nCores);
        int j2 = 0;
        for (int j = 0; j < nThreads; j++) {
            final int j1 = j;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = j1 * size / nThreads; i < (j1 + 1) * size / nThreads; i++) {
                        double i_d = i;
                        source[i] = (long) Math.round(Math.abs(i_d * Math.sin(Math.PI * (i_d * nWaves / size_d))));
                    }
                    ph.arrive();
                }
            });
        }
        ph.arriveAndAwaitAdvance();
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("fill array FFP ms:" + (endTime - startTime));
        return source;
    }


}
