package queue;

import java.nio.Buffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ilya on 20.05.2017.
 */
public class ConcurrentLQEntry
{
  public static void main(String[] args) throws InterruptedException
  {
            Buffer buffer = new Buffer();
            ExecutorService executor = Executors.newFixedThreadPool(6);

            executor.execute(new ProdTask(buffer,50));

            System.out.println();
            executor.execute(new ConTask(buffer,10));
            executor.execute(new ConTask(buffer,10));
            executor.execute(new ConTask(buffer,10));
            executor.execute(new ConTask(buffer,10));
            executor.execute(new ConTask(buffer,10));
            executor.shutdown();

  }

  static class ProdTask implements Runnable{
      Buffer buffer;
      int iterationsN;
      ProdTask(Buffer buffer,int iterationsN){
          this.buffer = buffer;
          this.iterationsN=iterationsN;
      }
      @Override
      public void run() {
          for(int i = 0; i < iterationsN; i++){
              buffer.put(i);
              try{
              Thread.sleep(30);
              }catch (InterruptedException e){
                e.printStackTrace();
              }
          }
      }
  }
  static class ConTask implements Runnable{
      Buffer buffer;
      int iterationsN;
      ConTask(Buffer buffer,int iterationsN){
          this.buffer = buffer;
          this.iterationsN=iterationsN;
      }
      @Override
      public void run() {
          for (int i=0;i<iterationsN;i++)
            buffer.get();
      }
  }

  static class Buffer{
      int i;
      Queue<Integer> clQueue = new ConcurrentLinkedQueue<Integer>();

      //Retrieving from the queue
      public void get(){
          System.out.println("Consumer recd - \t" + clQueue.poll()+ " \t"+Thread.currentThread().getName());
      }
      // putting in the queue
      public void put(int i){
          this.i = i;
          clQueue.add(i);
          System.out.print("" + i +" ");
      }

  }
}
