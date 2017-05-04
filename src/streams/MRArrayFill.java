package streams;

import java.util.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CountedCompleter;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by ilya on 01.01.2017.
 */
public class MRArrayFill extends CountedCompleter<Long>
{
  final long[] array;
  final MyReducer<Long> reducer;
  final int lo, hi, nWaves;
  final long threshold;
  long startTime;
  MRArrayFill sibling;
  BiFunction<Long /*index*/, /*nWaves*/Integer,Long/*result*/> f;
  Long result;

  public int getLo()
  {
    return lo;
  }
  public int getHi()
  {
    return hi;
  }
  MRArrayFill(CountedCompleter<?> p, long[] array,
             MyReducer<Long> reducer, int lo, int hi,long threshold, int nWaves,BiFunction<Long /*index*/, /*nWaves*/Integer,Long/*result*/> f, long startTime)
  {
    super(p);
    this.array = array;
    this.reducer = reducer;
    this.lo = lo;
    this.hi = hi;
    this.threshold=threshold;
    this.nWaves=nWaves;
    this.f=f;
    this.startTime=startTime;
  }
  public void compute()
  {
    if (hi - lo > threshold)
    {
//      long endTime=System.currentTimeMillis();
//      System.out.println("branch comp:" +(endTime-startTime));
      int mid = (lo + hi) >>> 1;
      MRArrayFill left = new MRArrayFill(this, array, reducer, lo, mid, threshold,nWaves,f,startTime);
      MRArrayFill right = new MRArrayFill(this, array, reducer, mid, hi,threshold,nWaves,f,startTime);
      left.sibling = right;
      right.sibling = left;
      setPendingCount(1); // only right is pending
      right.fork();
      left.compute();     // directly execute left
    }
    else
    {
      long endTime=System.currentTimeMillis();
//      System.out.println("leaf comp starts:" +(endTime-startTime)+" "+"("+lo+","+hi+")");
      for (int i=lo;i<hi;i++)
        array[(int)i]=f.apply( (long)i,nWaves);
//      LongStream.range(lo,hi).forEach(i->array[(int)i]=f.apply( i,nWaves));
      endTime=System.currentTimeMillis();
//      System.out.println("leaf comp ends:" +(endTime-startTime)+" "+"("+lo+","+hi+")");
      tryComplete();
    }
  }
  public void onCompletion(CountedCompleter<?> caller)
  {
    if (caller != this)
    {
      MRArrayFill mr = (MRArrayFill) caller;
      //works for a branch node
//      System.out.println("caller lo: "+ mr.getLo()+" caller hi:"+mr.getHi());
//      System.out.println(" this lo: "+ this.getLo()+" this hi:"+this.getHi());
//      System.out.println();

      MRArrayFill child = (MRArrayFill) caller;
      MRArrayFill sib = child.sibling;
      if (sib == null || sib.result == null)
        result = child.result;
      else
        result = reducer.apply(child.result, sib.result);
    }
  }
  public Long getRawResult()
  {
    return result;
  }


  public static Long fillArray(long[] array,  MyReducer<Long> reducer,long threshold, int nWaves,BiFunction<Long /*index*/, /*nWaves*/Integer,Long/*result*/> f)
  {

    long startTime = System.currentTimeMillis();
    MRArrayFill mr = new MRArrayFill(null, array, reducer,
                0, array.length,threshold, nWaves,f, startTime);
    mr.invoke();
    long endTime = System.currentTimeMillis();
//    System.out.println("fill array FJ ms:"+(endTime - startTime));

    return endTime-startTime;
  }
  public static void main(String[] args)
  {
   int n=32;
   BiFunction<Long,Integer,Long> f =  (i,nWaves)->(long)Math.round(Math.abs(i*Math.sin(Math.PI*((double) (i)*nWaves/n))));
    BiFunction<Long,Integer,Long> f1 =  (i,nWaves)->i;
   long[] array = new long[n];
    fillArray(array, new MyReducer<Long>()
    {
      @Override
      Long apply(Long x, Long y)
      {
        return 0L;
      }
    },4,2,f1);
    Arrays.stream(array).forEach(a-> System.out.print(a+" "));
  }

}
