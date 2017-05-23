package collectionsCommon;

import java.util.*;

/**
 * Created by ilya on 20.05.2017.
 */
public class EntryIterators
{
  public static void main(String[] args)
  {
    List<Integer> coll1 = new ArrayList<>();
    coll1.add(1);
    coll1.add(2);
    coll1.add(3);

    /*Iterator it = coll1.iterator();
    while (it.hasNext()) {
      Integer i= (Integer) it.next();
      coll1.remove(i);
    }*/
    for (Integer i:coll1){
      coll1.add(i+10);
    }

  }
}
