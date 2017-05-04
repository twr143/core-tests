package file;


import org.openjdk.jmh.annotations.Setup;

import java.util.*;
import java.util.concurrent.CountedCompleter;
import java.util.stream.Collectors;

import static file.ReaderE1.WORD_DELIMITERS_REGEX;
import static file.ReaderE1.pattern;

/**
 * Created by ilya on 13.01.2017.
 */
abstract class ReduceReader<E> { abstract E apply(E x, E y); }

public class MapReduceReader extends CountedCompleter<Map<String,Integer>>
{
  final List<String> lines;
  final ReduceReader<Map<String,Integer>> reducer;
  final int lo, hi;
  final long threshold;
  MapReduceReader sibling;
  Map<String,Integer> result;


  public int getLo()
  {
    return lo;
  }
  public int getHi()
  {
    return hi;
  }
  MapReduceReader(CountedCompleter<?> p, List<String> lines,
                  ReduceReader<Map<String,Integer>> reducer, int lo, int hi, long threshold)
  {
    super(p);
    this.lines = lines;
    this.reducer = reducer;
    this.lo = lo;
    this.hi = hi;
    this.threshold=threshold;
  }
  public void compute()
  {
    if (hi - lo > threshold)
    {

      int mid = (lo + hi) >>> 1;
      MapReduceReader left = new MapReduceReader(this, lines, reducer, lo, mid, threshold);
      MapReduceReader right = new MapReduceReader(this, lines, reducer, mid, hi,threshold);
      left.sibling = right;
      right.sibling = left;
      setPendingCount(1); // only right is pending
      right.fork();
      left.compute();     // directly execute left
    }
    else
    {

      result =lines.stream().skip((lo)).limit(hi-lo).filter(s -> !s.isEmpty()).
              flatMap((String s) -> Arrays.stream(pattern.split(s))).filter(s -> !s.isEmpty()).
                      map(String::toLowerCase).collect(Collectors.toMap(k -> (String) k, k -> 1, (Integer v1, Integer v2) -> v1 + v2));

      tryComplete();
    }
  }
  public void onCompletion(CountedCompleter<?> caller)
  {
    if (caller != this)
    {
      MapReduceReader mr = (MapReduceReader) caller;
      //works for a branch node

      MapReduceReader child = (MapReduceReader) caller;
      MapReduceReader sib = child.sibling;
      if (sib == null || sib.result == null)
        result = child.result;
      else
        result = reducer.apply(child.result, sib.result);
    }
  }
  public Map<String,Integer> getRawResult()
  {
    return result;
  }


  public static Map<String, Integer> parseAndCountEntriesJ(List<String> lines, long threshold)
  {

    MapReduceReader mr = new MapReduceReader(null, lines, new ReduceReader<Map<String, Integer>>()
    {
      @Override
      Map<String, Integer> apply(Map<String, Integer> x, Map<String, Integer> y)
      {
//        Map<String, Integer> merge = new HashMap<>();
//        Set<String> mergeKeys = new HashSet<>();
//        mergeKeys.addAll(x.keySet());
//        mergeKeys.addAll(y.keySet());
//        mergeKeys.forEach(k->merge.put(k,x.getOrDefault(k,0)+y.getOrDefault(k,0)));
//       return merge;
          y.entrySet().forEach(e->x.merge(e.getKey(),e.getValue(),(v,w)->v+w));
          return x;

      }
    },
            0, lines.size(), threshold);

    return mr.invoke();
  }
  public static void main(String[] args) throws Exception
  {
    ReaderE1.setUp();
    Map<String,Integer> dictionary= parseAndCountEntriesJ(ReaderE1.lines,1000);
    List<Map.Entry<String,Integer>> sorted =dictionary.entrySet().stream().sorted((u, v)->v.getValue().compareTo(u.getValue())).collect(Collectors.toList());
    System.out.println(sorted);
    ReaderE1.tearDown();
  }


}
