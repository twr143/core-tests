package arrayCommon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ilya on 05.02.2017.
 */
public class ACEntry2
{
  // merging collection while iteration through it
  public static void main(String[] args)
  {
    List<Integer> la = IntStream.range(0,2).boxed().collect(Collectors.toList());
    List<Integer> lb = IntStream.range(2,4).boxed().collect(Collectors.toList());


    for (int i=0;;i++){
      if (i<la.size()){
        Integer val = la.get(i);
        if (i == 1)
          la.addAll(lb);
      }
      else
        break;
    }
    System.out.println(la);

  }
}
