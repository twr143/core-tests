package account;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * Created by ilya on 15.12.2016.
 */
public class EntryAcc1
{
  private static int NUM_WRITERS;
  private static int NUM_READERS;
  private static int ARR_SIZE;
  private static long TEST_DURATION_MS;

  private static final long TEST_COOL_OFF_MS = 10;
  private static ThreadPoolExecutor EXECUTOR =null;

  public static void main(String[] args) throws Exception
  {
    NUM_READERS = Integer.parseInt(args[0]);
    NUM_WRITERS = Integer.parseInt(args[1]);
    ARR_SIZE = Integer.parseInt(args[2]);
    TEST_DURATION_MS = Long.parseLong(args[3]);
    EXECUTOR = (ThreadPoolExecutor)Executors.newFixedThreadPool(8);
    for (int i = 0; i < 3; i++)
    {
        System.out.println("*** Run - " + i);
            System.gc();
            Thread.sleep(TEST_COOL_OFF_MS);

            perfRun();
        }





    EXECUTOR.shutdown();

  }
  protected static Random random = new Random();
  public static void perfRun() throws Exception{
    Account[] accs = new Account[ARR_SIZE];
    for (int i=0;i<ARR_SIZE;i++) {
      Account a = new Account(i,100.0,"USD");
      accs[i] = a;
    }
    final AtomicBoolean isDone = new AtomicBoolean(false);
    final Phaser phStart = new Phaser(NUM_READERS+NUM_WRITERS +1);
    final Phaser phFinish = new Phaser(NUM_READERS+NUM_WRITERS +1);
    ConcurrentSkipListSet<Double> sums = new ConcurrentSkipListSet<>();
    LongAdder totalReads = new LongAdder();
    for( int i=0;i<NUM_WRITERS;i++){
      EXECUTOR.submit(()->
        {
          phStart.arrive();
          while (!isDone.get()){
            IntStream.range(0,accs.length).forEach(j->{
               float amount = 10*random.nextFloat();
               float delta = new Double(Math.round(amount * 100.0) / 100.0).floatValue();
               Manager.transfer(accs[j],accs[ARR_SIZE-j-1],delta);
            });

          }
          phFinish.arrive();
      });
    }

    for( int j1=0;j1<NUM_READERS;j1++){
      EXECUTOR.submit(()->
      {
          phStart.arrive();
//          while (!isDone.get()){
//            double sum=Manager.getTotal(accs);
//            sum = new Double(Math.round(sum * 100.0) / 100.0).floatValue();
//            sums.add(sum);
//            totalReads.increment();
//          }
          phFinish.arrive();
      });
    }
    phStart.arriveAndAwaitAdvance();
    Thread.sleep(TEST_DURATION_MS);
    isDone.set(true);
    phFinish.arriveAndAwaitAdvance();
    double sum=Manager.getTotal(accs);
    sum= new Double(Math.round(sum * 100.0) / 100.0).doubleValue();
    System.out.println(Arrays.toString(accs));
    System.out.println("sums are: "+sum);
//    System.out.println("total reads are: "+totalReads.longValue());

  }

}
