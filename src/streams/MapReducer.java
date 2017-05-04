package streams;

import java.util.concurrent.CountedCompleter;

/**
 * Created by ilya on 30.12.2016.
 */
class MyMapper<E> { E apply(E v) {  return v;  } }
abstract class MyReducer<E> { abstract E apply(E x, E y); }
public class MapReducer<E> extends CountedCompleter<E>
{
  final E[] array;
  final MyMapper<E> mapper;
  final MyReducer<E> reducer;
  final int lo, hi;
  MapReducer<E> sibling;
  E result;

  public int getLo()
  {
    return lo;
  }
  public int getHi()
  {
    return hi;
  }
  MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
             MyReducer<E> reducer, int lo, int hi)
  {
    super(p);
    this.array = array;
    this.mapper = mapper;
    this.reducer = reducer;
    this.lo = lo;
    this.hi = hi;
  }
  public void compute()
  {
    if (hi - lo >= 2)
    {
      int mid = (lo + hi) >>> 1;
      MapReducer<E> left = new MapReducer(this, array, mapper, reducer, lo, mid);
      MapReducer<E> right = new MapReducer(this, array, mapper, reducer, mid, hi);
      left.sibling = right;
      right.sibling = left;
      setPendingCount(1); // only right is pending
      right.fork();
      left.compute();     // directly execute left
    }
    else
    {

      if (hi > lo)
        //works for a leaf node
        result = mapper.apply(array[lo]);
      tryComplete();
    }
  }
  public void onCompletion(CountedCompleter<?> caller)
  {
    if (caller != this)
    {
      MapReducer mr = (MapReducer) caller;
      //works for a branch node
      System.out.println("caller lo: "+ mr.getLo()+" caller hi:"+mr.getHi());
      System.out.println(" this lo: "+ this.getLo()+" this hi:"+this.getHi());
      System.out.println();

      MapReducer<E> child = (MapReducer<E>) caller;
      MapReducer<E> sib = child.sibling;
      if (sib == null || sib.result == null)
        result = child.result;
      else
        result = reducer.apply(child.result, sib.result);
    }
  }
  public E getRawResult()
  {
    return result;
  }


  public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer)
  {
    return new MapReducer<E>(null, array, mapper, reducer,
            0, array.length).invoke();
  }
}
