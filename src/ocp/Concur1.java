package ocp;

import java.util.Arrays;

/**
 * Created by ilya on 12.12.2016.
 */
public class Concur1
{
  public static void main(String[] args){
//    Arrays.asList("jackal","kangaroo","lemur")
//    .parallelStream()
//    .map(s -> s.toUpperCase())
//    .forEach(System.out::println);

    int[] arr = {1,2,3,4,5};
    Arrays.asList(1,2,3,4,5).stream().map(a->a).forEach(n->{
      System.out.printf("%d ",n);
//      System.out.print(n);
    });



  }
}
