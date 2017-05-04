package cas;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.LongBinaryOperator;

import static rainCount.DataGenerator.nCores;

/**
 * Created by ilya on 12.12.2016.
 */
public class EntryConv2
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
    Future<Long> left=completionService.submit(new Callable<Long>()
    {
      @Override
      public Long call() throws Exception
      {
        int i=0;
        Thread.sleep(1);
//         while (i<arr.length()&&arr.compareAndSet(i,0,1));
        while (i<arr.length()&&arr.accumulateAndGet(i++, 1, new LongBinaryOperator()
        {
          @Override
          public long applyAsLong(long left, long right)
          {
            return left==0?Long.max(left,left+right):left;//Long.max(left,left+right)
          }
        })==1);
         return (long)i;
      }
    });
    Future<Long> right=completionService.submit(new Callable<Long>()
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
            return left==0?Long.max(left,left+right):left; //Long.max(left,left+right)
          }
        })==2);
        return (long)i;

      }
    });

    long lResult=0,rResult=0;
    try
    {
      lResult=left.get();
      rResult=right.get();
      executorService.shutdown();
    }
    catch (Exception ie){
      ie.printStackTrace();
    }

        boolean has3=false;
        int i = 0;
        System.out.println("[0]:"+arr.get(0)+" [10]:"+arr.get(10)+" [9999]:"+arr.get(9999)+" l:"+lResult+" r:"+rResult);
        for (i = 0; i < arr.length(); i++)
        {
          if (arr.get(i)==3)
          {
            System.out.println("contains 3 at index "+i);
            has3 = true;
          }
        }
        if (!has3)
          System.out.printf("no 3 ");

  }

}
