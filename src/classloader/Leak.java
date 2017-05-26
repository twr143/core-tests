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
                        Thread.sleep(100);

                        ThreadLocal<Object> threadLocal = new ThreadLocal<Object>() {
                            int[] moreBytesToLeak = new int[1024 * 1024 * 25];
                        };
                        threadLocal.set(threadLocal);
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
