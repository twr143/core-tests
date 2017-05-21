package collectionsCommon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by ilya on 24.11.2016.
 */
public class ACEntry1
{
  public static void main(String[] args)  {

//     List<Long> longList = new LinkedList<Long>();
     // remove comment from  below line and add comment on above line
//     List<Long> longList = new ArrayList<Long>();
    long maxValue = 200000;
    List<Long> collArray = LongStream.range(0,100).boxed().collect(Collectors.toCollection(ArrayList::new));//new ArrayList<Long>();
    List<Long> collList = LongStream.range(0,100).boxed().collect(Collectors.toCollection(LinkedList::new));
    List<Long> oriArray = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(ArrayList::new));//new ArrayList<Long>();
    List<Long> oriList = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(LinkedList::new));
    testList(oriArray,maxValue,"arrayList");
    testList(oriList,maxValue,"linkedList");

    oriArray = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(ArrayList::new));//new ArrayList<Long>();
    oriList = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(LinkedList::new));

    testListInserCollection(oriArray,collArray,maxValue,"arrayList:array");
    testListInserCollection(oriList,collArray,maxValue,"linkedList:array");

    oriArray = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(ArrayList::new));//new ArrayList<Long>();
    oriList = LongStream.range(0,maxValue).boxed().collect(Collectors.toCollection(LinkedList::new));

    testListInserCollection(oriArray,collList,maxValue,"arrayList:list");
    testListInserCollection(oriList,collList,maxValue,"linkedList:list");


   }
  static void testList(List<Long> longList, long maxValue,String description){
    long startTime = System.currentTimeMillis();


     int size = longList.size();
     long insertions=20000L;
//     for (long i=0;i<insertions;i++)
      LongStream.range(0,insertions).forEach(i->longList.add((int)(size/10+8*i),i));

     long endTime = System.currentTimeMillis();

     System.out.println(description+":"+(endTime - startTime));

  }
  static void testListInserCollection(List<Long> longList, List<Long> collection, long maxValue,String description){
    long startTime = System.currentTimeMillis();

     int size = longList.size();
     long insertions=20000L;
     LongStream.range(0,insertions).forEach(i->longList.addAll((int)(size/10+8*i),collection));

     long endTime = System.currentTimeMillis();

     System.out.println(description+":"+(endTime - startTime));

  }

}
