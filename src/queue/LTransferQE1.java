package queue;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ilya on 11.01.2017.
 */
public class LTransferQE1
{
  public static void main(String[] args)
  {
    int nCores=Runtime.getRuntime().availableProcessors();

    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(nCores);
    final Phaser ph = new Phaser(4){
      @Override
      protected boolean onAdvance(int phase, int registeredParties)
      {
        return false;
      }
    };
    final LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<Integer>();
    executorService.submit(new Runnable()
    {
      @Override
      public void run()
      {
        final Thread currentThread = Thread.currentThread();
        currentThread.setName("LTQueue writer");
/*        try
        {
          Thread.sleep(1050);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }*/
        for (int i=0;i<10;i++){
          try
          {
            Thread.sleep(350);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
          try
          {
            System.out.println("transferring:"+i);
            queue.transfer(i);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
          System.out.println("transferred:"+i);
          System.out.println(queue);


        }
        ph.arriveAndDeregister();

      }
    });
    executorService.submit(new Runnable()
    {
      @Override
      public void run()
      {
        final Thread currentThread = Thread.currentThread();
        currentThread.setName("LTQueue reader 1");
        for (int i=0;i<5;i++){
          try
          {
            Thread.sleep(1000);
            System.out.println("took 1:"+queue.take());
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
        ph.arriveAndDeregister();

      }
    });
    executorService.submit(new Runnable()
    {
      @Override
      public void run()
      {
        final Thread currentThread = Thread.currentThread();
        currentThread.setName("LTQueue reader 2");
        for (int i=0;i<5;i++){
          try
          {
            Thread.sleep(1000);
            System.out.println("took 2:"+queue.take());
//            Thread.sleep(300);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
        ph.arriveAndDeregister();

      }
    });


    ph.arriveAndAwaitAdvance();
    executorService.shutdown();
    System.out.println("the end");

  }
}
