package leaks;

import java.io.IOException;

/**
 * Created by AIKuznetsov on 26.05.2017.
 */
public class LeakThreadLocal {

    // True for causing OOM, false to leaking 1 GB of HEAP without GC possibility
    static final boolean causeOOM = false;

    public static void main(String[] args) throws InterruptedException, IOException {
        // Here is the time to go to your profiler and hook up to the app to monitor heap / call GC manually
        System.out.println("Please hook up a profiler to this App JVM, then press any key...");
        System.in.read();

        final Thread thread = new Thread(() -> {
            long count = 0;

            while (true) {
                // If we want to cause OOM we just create relentlessly, otheerwise we eat up about 1 GB of HEAP to see in profiler and manually call GC
                if (causeOOM || count < 1000000) {
                    new ThreadLocal<Object>() {
                        int[] moreBytesToLeak = new int[256];

                        {
                            set(this);
                        }
                    };
                }

                ++count;

                // Print out current count
                if (count % 10000 == 0) {
                    System.out.println(causeOOM || count < 1000000 ? count : "No More");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("How did we get it?");
                    }
                }


            }
        });
        thread.start();
        thread.join();

    }
}
