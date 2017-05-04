package concurr;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by ilya on 14.11.2016.
 */
public class SemaphoreRunnable implements Runnable
{
  final Random rand = new Random();
  final Semaphore available;
  int num;
  public SemaphoreRunnable(int num, Semaphore available)
  {
    this.num = num;
    this.available=available;
  }
  public void run()
  {
      int time = rand.nextInt(5);

      try
      {

//          System.out.println("before aquire:" + num);
          available.acquire();

          System.out.println("Executing " +
              "long-running action for " +
              time + " seconds... #" + num);

          Thread.sleep(time * 1000);

          System.out.println("Done with #" +
              num + "!");

          available.release();
      }
      catch (InterruptedException intEx)
      {
          intEx.printStackTrace();
      }
  }

}
