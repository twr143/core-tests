package streams;

import streams.MyMapper;

import static streams.MapReducer.mapReduce;

/**
 * Created by ilya on 30.12.2016.
 */
public class StreanMREntry1
{
  public static void main(String[] args)
  {
    Integer[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    Integer result=mapReduce(a, new MyMapper<Integer>(), new MyReducer<Integer>()
    {
      @Override
      Integer apply(Integer x, Integer y)
      {
        return x+y;
      }
    });
    System.out.println("r="+result);
  }
}
