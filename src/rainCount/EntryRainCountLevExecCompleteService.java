package rainCount;

import java.util.concurrent.*;

import static rainCount.DataGenerator.concurrArrayFillFixedFP;
import static rainCount.DataGenerator.nCores;

/**
 * Created by ilya on 09.12.2016.
 */
public class EntryRainCountLevExecCompleteService
{
  static int nCores=Runtime.getRuntime().availableProcessors();

  static int glMaxIndex(long[] h){
    int N = h.length;
        long glMax = 0;
        int glMaxIndex = 0;
        for( int i = 0; i < N; i++ )
            if (h[i] > glMax)
            {
                glMaxIndex = i;
                glMax = h[i];
            }
            return glMaxIndex;
  }
  static long leftToMaxVolume(long[] h,int glMaxIndex){
    long startTime = System.currentTimeMillis();


    int j = 0;
    long curMax = h[0];
    long vol = 0;

    while( j < glMaxIndex ){
        while( (h[j + 1] >= h[j]) && (j < glMaxIndex) )
            j++;

        curMax = h[j];

        while( (j < glMaxIndex) && (h[j] <= curMax) )
        {
            vol += curMax - h[j];
            j++;
        }

    }
    long endTime = System.currentTimeMillis();
    System.out.println("left part:"+(endTime - startTime));

    return vol;
  }
  static long rightToMaxVolume(long[] h,int glMaxIndex){
    long startTime = System.currentTimeMillis();
    int j = 0;
    long curMax = h[0];
    long vol = 0;
    j = h.length - 1;
    curMax = h[j];

    while (j > glMaxIndex){
        while ( (h[j - 1] >= h[j]) && (j > glMaxIndex) )
            j--;

        curMax = h[j];

        while ( (j > glMaxIndex) && (h[j] <= curMax) )
        {
            vol += curMax - h[j];
            j--;
        }

    }

    long endTime = System.currentTimeMillis();
    System.out.println("right part:"+(endTime - startTime));
    return vol;
  }
  static long findVolume(final long[] h){
    final int glMaxIndex= glMaxIndex(h);
    System.out.println("glMaxIndex="+glMaxIndex);
    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(nCores);
    CompletionService<Long> completionService = new ExecutorCompletionService<Long>(executorService);
    completionService.submit(new Callable<Long>()
    {
      @Override
      public Long call() throws Exception
      {
        return leftToMaxVolume(h,glMaxIndex);
      }
    });
    completionService.submit(new Callable<Long>()
    {
      @Override
      public Long call() throws Exception
      {
        return rightToMaxVolume(h,glMaxIndex);
      }
    });
    try
    {
      Long leftOrRight1 = completionService.take().get();
      Long leftOrRight2 = completionService.take().get();
      executorService.shutdown();
      return leftOrRight1+leftOrRight2;
    }catch (Exception ie){
      ie.printStackTrace();
    }
    return 0;
  }
  public static void main(String[] args)
  {
      System.out.println("# cores:"+nCores);
      System.out.println("vol lin parall:"+findVolume(concurrArrayFillFixedFP(100000000,100,16)));
  }

}
