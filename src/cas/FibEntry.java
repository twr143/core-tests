package cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ilya on 04.05.2017.
 */
public class FibEntry {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(3);
        FibLongCAS fb = new FibLongCAS();
        int iterationNumber = 17;

        es.submit(new FibLongCASTask(iterationNumber, fb));
        es.submit(new FibLongCASTask(iterationNumber, fb));
        es.submit(new FibLongCASTask(iterationNumber, fb));
        es.shutdown();

        Thread.sleep(300);

        FibLongSingleThreaded fbST = new FibLongSingleThreaded();
        long fbSTResult = 0;
        for (int i = 0; i < iterationNumber * 3; i++) {
            fbSTResult = fbST.nextVal();
        }

        System.out.println("singlethreaded:\t" + fbSTResult);
        System.out.println("multithreaded:\t" + fb.get());

        assert fb.get() == fbST.get();
    }

    static class FibLongCAS2 {
        private AtomicLong oneAtomic = new AtomicLong(0);
        private AtomicLong anotherAtomic = new AtomicLong(1);

        public long nextVal() {
            boolean anotherIsBigger;
            long one, another, next;
            do {
                one = oneAtomic.get();
                another = anotherAtomic.get();

                next = one + another;

                anotherIsBigger = one < another;
            } while (!(anotherIsBigger ? oneAtomic.compareAndSet(one, next) : anotherAtomic.compareAndSet(another, next)));
            return next;
        }

    }

    static class FibLongCAS {
        private AtomicLong val1 = new AtomicLong(1);
        private AtomicLong val2 = new AtomicLong(1);

        public long nextVal() {
            AtomicLong prevToUpdate;
            long next, locval1, locval2, locprev;
            do {
                locval1 = val1.get();
                locval2 = val2.get();
                if (locval2 > locval1) {
                    prevToUpdate = val1;
                    locprev = locval1;
                } else {
                    prevToUpdate = val2;
                    locprev = locval2;
                }
                next = locval1 + locval2;
            } while (!prevToUpdate.compareAndSet(locprev, next));
            return next;
        }

        public long get() {
            return (val2.get() > val1.get() ? val2.get() : val1.get());
        }
    }

    static class FibLongCASTask implements Runnable {
        int iters;
        FibLongCAS fb;

        public FibLongCASTask(int iters, FibLongCAS fb) {
            this.iters = iters;
            this.fb = fb;
        }

        @Override
        public void run() {
            int i = 0;
            while (i++ < iters)
                System.out.println("thread: " + Thread.currentThread().getName() + " next:\t" + fb.nextVal());
        }
    }

    static class FibLongSingleThreaded {
        private long val1 = 1L;
        private long val2 = 1L;

        public long nextVal() {

            long next = val1 + val2;
            if (val2 > val1)
                val1 = next;
            else
                val2 = next;
            return next;
        }

        public long get() {
            return (val2 > val1 ? val2 : val1);
        }
    }

}
