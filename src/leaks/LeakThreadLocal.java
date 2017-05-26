package leaks;

/**
 * Created by AIKuznetsov on 26.05.2017.
 */
public class LeakThreadLocal
{

    static int count;
    public static void main(String[] args) throws InterruptedException {

        final Thread thread = new Thread(() -> {
            while (true) {
                new ThreadLocal<Object>() {
                    int[] moreBytesToLeak = new int[1024 * 1024 * 25];

                    {
                        set(this);
                      System.out.print((count++)+" ");
                    }
                };
                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
    }
}
