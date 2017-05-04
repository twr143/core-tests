package concurr;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ilya on 14.11.2016.
 */
public class Race
{
  private Random rand = new Random();

   private int distance = 100;
   private CountDownLatch start;
   private CountDownLatch finish;

   private List<String> horses = new ArrayList<String>();

   public Race(String... names)
   {
       this.horses.addAll(Arrays.asList(names));
   }

   public void run()
       throws InterruptedException
   {
       System.out.println("And the horses are stepping up to the gate...");
       final CountDownLatch start = new CountDownLatch(1);
       final CountDownLatch finish = new CountDownLatch(horses.size());
       final List<String> places =
           Collections.synchronizedList(new ArrayList<String>());

       for (final String h : horses)
       {
           new Thread(new Runnable() {
               public void run() {
                   try
                   {
                       System.out.println(h +
                           " stepping up to the gate...");
                       start.await();

                       int traveled = 0;
                       while (traveled < distance)
                       {
                           // через 0-2 секунды....
                           Thread.sleep(rand.nextInt(3) * 300);

                           // ... лошадь проходит дистанцию 0-14 пунктов
                           traveled += rand.nextInt(45);
                           System.out.println(h +
                               " advanced to " + traveled + "!");
                       }
                     System.out.println(h +
                         " crossed the finish!");
                     places.add(h);
                     Thread.sleep(1000);
                       finish.countDown();
                   }
                   catch (InterruptedException intEx)
                   {
                       System.out.println("ABORTING RACE!!!");
                       intEx.printStackTrace();
                   }
               }
           }).start();
       }

       System.out.println("And... they're off!");
       start.countDown();

       finish.await();
       System.out.println("And we have our winners!");
       System.out.println(places.get(0) + " took the gold...");
       System.out.println(places.get(1) + " got the silver...");
       System.out.println("and " + places.get(2) + " took home the bronze.");
   }
}
