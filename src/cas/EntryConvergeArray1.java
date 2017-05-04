package cas;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.LongBinaryOperator;

import static rainCount.DataGenerator.nCores;

/**
 * Created by ilya on 09.12.2016.
 */
public class EntryConvergeArray1
{
  public static void main(final String[] args)
  {
    int size=10000;
    final AtomicLongArray arr = new AtomicLongArray(size);
    for (int i = 0; i < size; i++)
    {
      arr.set(i,0);
    }

    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(nCores);
    CompletionService<Long> completionService = new ExecutorCompletionService<Long>(executorService);
    completionService.submit(new Callable<Long>()
    {
      @Override
      public Long call() throws Exception
      {
        int i=0;
        Thread.sleep(2);
//         while (i<arr.length()&&arr.compareAndSet(i,0,1));
        while (i<arr.length()&&arr.accumulateAndGet(i++, 1, new LongBinaryOperator()
        {
          @Override
          public long applyAsLong(long left, long right)
          {
            return left==0?right:left;//enforce cas
          }
        })==1);
         return 0L;
      }
    });
    completionService.submit(new Callable<Long>()
    {
      @Override
      public Long call() throws Exception
      {
        int i=arr.length()-1;
        while (i>=0&&arr.accumulateAndGet(i--, 2, new LongBinaryOperator()
        {
          @Override
          public long applyAsLong(long left, long right)
          {
            return left==0?right:left;//enforce cas
          }
        })==2);
        return 0L;

      }
    });

    try
    {
      Long leftOrRight1 = completionService.take().get();
      Long leftOrRight2 = completionService.take().get();
      executorService.shutdown();
    }
    catch (Exception ie){
      ie.printStackTrace();
    }

        boolean has3=false;
        int i = 0;
        System.out.println("[0]:"+arr.get(0)+" [10]:"+arr.get(10)+" [9999]:"+arr.get(9999));
        for (i = 0; i < arr.length(); i++)
        {
          if (arr.get(i)==3)
          {
            has3 = true;
            break;
          }
        }
        if (has3)
           System.out.printf("contains 3 at index "+i);
        else
          System.out.printf("no 3 ");

  }
}
