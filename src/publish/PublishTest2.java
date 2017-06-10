package publish;

/**
 * Created by MSI-Pc on 09.06.2017.
 */
public class PublishTest2 {

    public static void main(String[] args) throws InterruptedException {
        new PublishTest2().check();

        System.out.println("All save published");
    }

    final int sampleSize = 10000;

    volatile AlwaysSafePublished alwaysSafePublished;

    void check() throws InterruptedException {
        Thread checker = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (alwaysSafePublished != null) {
                        alwaysSafePublished.checkCorrect();
                        alwaysSafePublished = null;
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

        };

        Thread publisher = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < sampleSize; ++i) {
                    alwaysSafePublished = new AlwaysSafePublished();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }

        };
        checker.start();
        publisher.start();
        publisher.join();
        checker.interrupt();
        checker.join();
    }
}
