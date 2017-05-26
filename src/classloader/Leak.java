package classloader;

/**
 * Created by AIKuznetsov on 26.05.2017.
 */
public class Leak {

    public static void main(String[] args) throws InterruptedException {

        final Thread thread = new Thread(() -> {
            while (true) {
                new ThreadLocal<Object>() {
                    int[] moreBytesToLeak = new int[1024 * 1024 * 25];

                    {
                        set(this);
                    }
                };
            }
        });
        thread.start();
        thread.join();
    }
}
