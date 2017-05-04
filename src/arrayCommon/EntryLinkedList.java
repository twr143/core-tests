package arrayCommon;

import java.awt.print.Printable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ilya on 25.03.2017.
 */
public class EntryLinkedList
{
  public static void main(String[] args)
  {
    List<Integer> l = new LinkedList<Integer>();
    l.add(1);
    l.add(2);
    l.add(3);

    l.forEach(e->{
      System.out.print(e+" ");
    });


  }
}
