package prodCons;

import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
public class Consumer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;

    public Consumer(Queue<Integer> queue, int maxSize, String name) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty,"
                            + "Consumer thread is waiting"
                            + " for producer thread to put something in queue");
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                System.out.println("Consuming value (1) : " + (queue.remove()));
                queue.notifyAll();
            }
        }
    }

}
