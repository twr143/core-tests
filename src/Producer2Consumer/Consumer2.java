package Producer2Consumer;

import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
class Consumer2 extends Thread {
    private Queue<QueueBean> queue;
    private int maxSize;

    public Consumer2(Queue<QueueBean> queue, int maxSize, String name){
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int i=0;
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

                QueueBean bean = (QueueBean)queue.peek();
                if (!bean.redByFirst)
                {
                  bean.redBySecond = true;
                  System.out.println("read by second :" +bean.getValue());
//                  queue.notifyAll();

                  try {
                      queue.wait();
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }

                }
                else
                {
                  System.out.println("Consuming value (2) : " + ((QueueBean)queue.remove()).getValue());
                  queue.notifyAll();
                }
            }
          i++;

        }
    }
}