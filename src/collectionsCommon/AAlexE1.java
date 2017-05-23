package collectionsCommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ilya on 03.04.2017.
 */
public class AAlexE1
{
  public static void main(String[] args)
  {
    List<Integer> testSubject = new ArrayList<>();

            long time = System.currentTimeMillis();
            for (int i = 0; i < 100000; ++i) {
                testSubject.add(i);
            }
            System.out.println(System.currentTimeMillis() - time);

            testSubject.retainAll(Collections.emptyList());
            time = System.currentTimeMillis();
            for (int i = 0; i < 1000000; ++i) {
                testSubject.add(i);
            }
            System.out.println(System.currentTimeMillis() - time);

            testSubject.retainAll(Collections.emptyList());
            time = System.currentTimeMillis();
            for (int i = 0; i < 10000000; ++i) {
                testSubject.add(i);
            }
            System.out.println(System.currentTimeMillis() - time);

  }
}
