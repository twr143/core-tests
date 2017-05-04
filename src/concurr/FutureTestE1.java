package concurr;

import java.util.concurrent.*;

/**
 * Created by ilya on 18.01.2017.
 */
public class FutureTestE1
{
  public static void main(String[] args) throws Exception
  {
    ExecutorService es = Executors.newFixedThreadPool(3);
     Phaser phaser = new Phaser(2);
    ExecutorCompletionService ecs = new ExecutorCompletionService(es);
    ecs.submit(()->{

      try
      {
        Thread.sleep(1000);
      } catch (InterruptedException e){
        System.out.println("task 1 interrupted");
      }
      System.out.println("task1 arraived");
      return 0;
    });
    Future f2= ecs.submit(()->{
      try
      {
        Thread.sleep(1000);
      } catch (InterruptedException e){
        System.out.println("task 2 interrupted");
      }
      System.out.println("task2 arraived");
      return 0;
    });
    Thread.sleep(1000);


//    FutureTask<Integer> ft = new FutureTask<Integer>(new Callable<Integer>()
    Callable<Integer> ft = (new Callable<Integer>()
        {
          @Override
          public Integer call() throws Exception
          {
                    try
                    {
                      while (true)
                      {
                        System.out.println("task 3 works");
                        Thread.sleep(300);
                      }
                    }
                    catch (InterruptedException e){
                      System.out.println("task 3 interrupted");
                    }
                  System.out.println("task3 finished: "+Thread.currentThread().isInterrupted());
                  return 0;
          }}
        );
    Future f3= ecs.submit(ft);

//    f2.cancel(false);
//    f3.cancel(true);

    System.out.println("arrived at the end");
    System.out.println("f3 is cancelled:"+f3.isCancelled());
    Thread.sleep(2000);
    f3.cancel(true);

    es.shutdown();

  }
}
