package classloader;

/**
 * Created by AIKuznetsov on 26.05.2017.
 */
public class Leak {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                        new ThreadLocal<Object>() {
                            int[] moreBytesToLeak = new int[1024 * 1024 * 25];
                            {
                                set(this);
                            }
                        };
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted");
                    }
                }
            }
        };
        thread.start();
        thread.join();
    }
}
