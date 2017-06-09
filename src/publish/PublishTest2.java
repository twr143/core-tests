package publish;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by MSI-Pc on 09.06.2017.
 */
public class PublishTest2 {

    public static void main(String[] args) throws InterruptedException {
        final int sampleSize = 1000;

        Deque<AlwaysSafePublished> deque = new ArrayDeque<>(sampleSize);
        Thread thread = new Thread(() -> {
            int amount = 0;
            while (true) {
                AlwaysSafePublished alwaysSafePublished = deque.pollLast();
                while (alwaysSafePublished != null) {
                    ++amount;
                    if (alwaysSafePublished.number() != 1) {
                        throw new RuntimeException("Not safely pusblished at pos " + amount);
                    }
                    alwaysSafePublished = deque.pollLast();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Total checked: " + amount);
                    break;
                }
            }
        });
        thread.start();
        IntStream.range(1, sampleSize).parallel().mapToObj(index -> new AlwaysSafePublished()).forEach(deque::push);

        thread.interrupt();
        thread.join();
        System.out.println("All save published");
    }
}
