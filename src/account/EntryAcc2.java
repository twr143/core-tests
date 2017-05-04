package account;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ilya on 17.12.2016.
 */
public class EntryAcc2
{
  public static void main(String[] args) throws Exception
  {
    double[] amts ={10,20,30,40,50};
    String[] currencies ={"USD","EUR","JPY"};

//    List<Account> acc = getStream(0,30,6,currencies).filter(a->a.getAmount()==60).collect(Collectors.toList());
    List<Account> acc = getStream(0,30,6,currencies).filter(a->a.getAmount()==60).collect(Collectors.toList());

    ConcurrentMap<String, List<Account>> currs= getStream(0,30,6,currencies).collect(Collectors.groupingByConcurrent(Account::getCurrency));
//    System.out.println("size="+acc.size());
    currs.forEach((k,v)-> {
      Collections.sort(v, new Comparator<Account>(){
            @Override
            public int compare(Account o1, Account o2)
            {
              return o1.getId()-o2.getId();
            }
          });
      System.out.println(k+":"+v);
    }
    );
//    acc.stream().map(a->a.getCurrency()).distinct().forEach(System.out::println);
    System.out.println();
  }
   static Stream<Account> getStream(final int sartId,final int endId, int amtsLimit, String[] currencies) throws Exception{
      return IntStream.range(sartId,endId).parallel().boxed().flatMap(id-> DoubleStream.iterate(10,n->n+10).limit(amtsLimit).boxed().
                  flatMap(initCur-> Stream.of(currencies).map(cur-> new Account(id,initCur,cur))));

  }

}
