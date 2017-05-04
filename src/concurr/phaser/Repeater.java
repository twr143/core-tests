package concurr.phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Created by ilya on 19.11.2016.
 */
public class Repeater
{
  static void startTasks(List<Runnable> tasks, final int iterations) {
     final Phaser phaser = new Phaser() {
       protected boolean onAdvance(int phase, int registeredParties) {
         System.out.println("phase="+phase);
         System.out.println("registeredParties="+registeredParties);
         return phase >= iterations || registeredParties == 0;
       }
     };
     phaser.register();
     for (final Runnable task : tasks) {
       phaser.register();
       new Thread() {
         public void run() {
           do {
             task.run();
             phaser.arriveAndAwaitAdvance();
           } while (!phaser.isTerminated());
         }
       }.start();
     }
//    while (!phaser.isTerminated())
//         phaser.arriveAndAwaitAdvance();
    System.out.println("done main");
    phaser.arriveAndDeregister(); // deregister self, don't wait

  }
  public static void main(String[] args)
  {
    List<Runnable> works = new ArrayList<Runnable>();
    int workNum=3;
    for (int i=0;i<workNum;i++){
      works.add(new TestWork(i));
    }
    startTasks(works,3);

  }
  static class TestWork implements Runnable{

    int workNum;
    public TestWork(int workNum)
    {
      this.workNum = workNum;
    }
    @Override
    public void run()
    {
      System.out.printf("work #%d\n",workNum);
    }
  }
}
