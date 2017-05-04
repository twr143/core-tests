package concurr;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by ilya on 14.11.2016.
 */
public class SemaphoreTest
{
  public static void main(String[] args)
  {

    Semaphore s = new Semaphore(3);
    for (int i=0; i<10; i++)
        new Thread(new SemaphoreRunnable(i,s)).start();
  }
}
