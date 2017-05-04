package Producer2Consumer;

import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
class Producer extends Thread {
    private Queue<QueueBean> queue;

    private int maxSize;
    private int range;


    public Producer(Queue<QueueBean> queue, int maxSize, String name, int range){
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
        this.range=range;
    }

    @Override
    public void run() {
        int i=1;
        while (i<=range) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out .println("Queue is full, "
                                + "Producer thread waiting for "
                                + "consumer to take something from queue");
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                queue.add(new QueueBean(i));
                queue.notifyAll();
                i++;
            }

        }
    }
}