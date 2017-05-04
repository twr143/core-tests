package prodCons;

import Producer2Consumer.QueueBean;

import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
public class Producer extends Thread
{
  private Queue<Integer> queue;

  private int maxSize;
  private int range;


  public Producer(Queue<Integer> queue, int maxSize, String name, int range){
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

              queue.add(i);
              queue.notifyAll();
              i++;
          }

      }
  }

}
