package streams;

import com.sun.deploy.util.ArrayUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.*;

import static streams.MRArrayFill.fillArray;


/**
 * Created by ilya on 19.12.2016.
 */
//@State(Scope.Thread)
  @Warmup(iterations = 15)
  @Measurement(iterations = 15)
public class StreamE5
{
  static int nCores=Runtime.getRuntime().availableProcessors();
  static int n=16*1024*1024,nWaves=130;

//    int n=32,nWaves=2;
  static int threshold=2*256*1024;
  public static void main(String[] args)   throws Exception
  {
    Options opt = new OptionsBuilder()
                    .include(StreamE5.class.getSimpleName())
                    .forks(1)
                    .build();

            new Runner(opt).run();



  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public static void testFTP(){
    concurrArrayListFillFixedTP(n, nWaves, nCores>4?nCores-1:nCores);
  }
  @Benchmark
  @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public static void testFJ(){
    concurrArrayListFillFJ(n, nWaves,threshold);
  }

  static Long concurrArrayListFillStream(int n, int nWaves){
    long startTime = System.currentTimeMillis();
    List<Long> result=LongStream.range(0,n).parallel().map(i->f(i,n,nWaves)).boxed().collect(Collectors.toList());
    long endTime = System.currentTimeMillis();
    System.out.println("fill array stream ms:" + (endTime - startTime));
    return endTime - startTime;
  }
  public static Long concurrArrayListFillFixedTP(final int size, final int nWaves, final int nThreads){
    long startTime = System.currentTimeMillis();
    final double size_d=size;
    final long[] source = new long[size];
    final Phaser ph =new Phaser(nThreads+1){
      protected boolean onAdvance(int phase, int registeredParties) {
        return phase >= 0;
      }
    };
    ExecutorService executor = Executors.newFixedThreadPool(nCores-1);
    for (int j=0;j<nThreads;j++){
      int j1=j;
      executor.submit(()->
      {
          for (int i=j1*size/nThreads;i<(j1+1)*size/nThreads;i++){
            source[i]=f(i,size,nWaves);
          }
          ph.arrive();
      });
    }
    ph.arriveAndAwaitAdvance();
    executor.shutdown();
    long endTime = System.currentTimeMillis();
//    System.out.println("fill array FTP ms:"+(endTime - startTime));
    return endTime-startTime;
  }

  private static long f(long i,int n,int nWaves){
    return (long)Math.round(Math.abs(i*Math.sin(Math.PI*((double) i*nWaves/n))));
  }

  public static long  concurrArrayListFillFJ(final int n, final int nWavesAct, final int threshold){

    BiFunction<Long,Integer,Long> f =  (i, nWaves)->(long)Math.round(Math.abs(i*Math.sin(Math.PI*((double) (i)*nWaves/n))));
     BiFunction<Long,Integer,Long> f1 =  (i,nWaves)->i;
    long[] array = new long[n];
    long time=fillArray(array, new MyReducer<Long>()
     {
       @Override
       Long apply(Long x, Long y)
       {
         return 0L;
       }
     },threshold,nWavesAct,f);
    return time;

  }

  class A{
    int k=0;
    void foo(){
      UnaryOperator<Integer> un1 = i->{k++;i++;return i;};
//      BiFunction<E,T,R> f =
    }
  }

}
