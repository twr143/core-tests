package locks;

import java.util.concurrent.*;

/**
 * Created by ilya on 05.12.2016.
 */
public class EntryBlockingQ
{
  public static void main(String[] args)
  {
    int nCores=Runtime.getRuntime().availableProcessors();

    ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(nCores);
    final Phaser ph = new Phaser(4){
      @Override
      protected boolean onAdvance(int phase, int registeredParties)
      {
        return false;
      }
    };
    final BlockingQueue<Integer> queue = new BlockingQueue<Integer>();
    executorService.submit(new Runnable()
    {
      @Override
      public void run()
      {
        final Thread currentThread = Thread.currentThread();
        currentThread.setName("BQueue writer");
        for (int i=0;i<10;i++){
          queue.put(i);
          System.out.println("put:"+i);
          try
          {
            Thread.sleep(350);
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
        currentThread.setName("BQueue reader 1");
        for (int i=0;i<5;i++){
          System.out.println("took 1:"+queue.take());
          System.out.println(queue);
          try
          {
            Thread.sleep(300);
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
        currentThread.setName("BQueue reader 2");
        for (int i=0;i<5;i++){
          System.out.println("took 2:"+queue.take());
          System.out.println(queue);
          try
          {
            Thread.sleep(300);
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
