package singleton;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadFactory;

/**
 * Created by ilya on 26.12.2016.
 */
public class SingletinE1
{
  public static void main(String[] args)
  {
    TestSingletonCAS(10, new Keeper(),true);
  }

  public static void TestSingletonCAS(final int nThreads, Keeper keeper, boolean sh){
    long startTime = System.currentTimeMillis();
    final Phaser ph =new Phaser(nThreads+1){
      protected boolean onAdvance(int phase, int registeredParties) {
        return phase >= 0;
      }
    };
    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);
    for (int j=0;j<nThreads;j++){
      executor.submit(()->
      {
          StringBuilder out = new StringBuilder();
          out.append("thread: "+Thread.currentThread().getName()+" ").append(sh?keeper.getDataBySH():Keeper.getDataByLock());
          System.out.println(out);
          ph.arrive();
      });
    }
    ph.arriveAndAwaitAdvance();
    executor.shutdown();
    long endTime = System.currentTimeMillis();
//    System.out.println("fill array FTP ms:"+(endTime - startTime));
  }

}
