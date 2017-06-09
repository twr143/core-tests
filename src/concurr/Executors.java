package concurr;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Created by AIKuznetsov on 09.06.2017.
 */
public class Executors {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int suiteSize = 64;

        for (int threadCount = 1; threadCount < 12; ++threadCount) {
            final ExecutorService service = newFixedThreadPool(threadCount);

            final long start = System.currentTimeMillis();
            System.out.println("Potential total duration sequential with " + threadCount + " threads: " +
                            service.invokeAll(LongRunningOperation.giveSuite(suiteSize))
                                    .stream()
                                    .parallel()
                                    .map(stringFuture -> {
                                        try {
                                            final LongRunningOperation longRunningOperation = stringFuture.get();
//                                            System.out.println(longRunningOperation);
                                            return longRunningOperation.getDuration();
                                        } catch (InterruptedException | ExecutionException e) {
                                            System.out.println(e);
                                        }
                                        return 0L;
                                    }).mapToInt(Long::intValue)
                                    .sum()
            );

            service.shutdown();
            service.awaitTermination(10, TimeUnit.HOURS);

            System.out.println("Actual total duration parallel: " + (System.currentTimeMillis() - start));
        }

        final long start = System.currentTimeMillis();
        LongRunningOperation.giveSuite(suiteSize).forEach(LongRunningOperation::call);
        System.out.println("Actual total duration sequential: " + (System.currentTimeMillis() - start));
    }
}

class LongRunningOperation implements Callable<LongRunningOperation> {

    private static final int SERIOUS_NUMBER = 1000000;

    private long duration;

    private double result;

    long getDuration() {
        return duration;
    }

    static List<LongRunningOperation> giveSuite(int size) {
        return IntStream.range(0, size).mapToObj(index -> new LongRunningOperation()).collect(Collectors.toList());
    }

    @Override
    public LongRunningOperation call()  {
        final long start = System.currentTimeMillis();

        result = new Random().ints(SERIOUS_NUMBER).sorted().average().getAsDouble();
        duration = System.currentTimeMillis() - start;

        return this;
    }

    @Override
    public String toString() {
        return "LongRunningOperation{" +
                "duration=" + duration +
                ", result=" + result +
                '}';
    }
}
