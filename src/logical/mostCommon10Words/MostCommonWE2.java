package logical.mostCommon10Words;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilya on 14.04.2017.
 */
public class MostCommonWE2
{

  public static void main(String[] args)
  {
    String source = " abc abc xyz def ghi ghi def abc xyz uvw uvw";
    mostCommon10Words(source);
//    mostCommon10WordsStream2(source);
  }
  public static void mostCommon10Words(String text){
     String[] words = text.split("\\s+");
     Map<String, Integer> counts = new HashMap<String,Integer>();
     for (String w:words){
      counts.put(w, counts.getOrDefault(w,0)+1);
     }
     Set<Map.Entry<String,Integer>> countsSorted = new TreeSet<Map.Entry<String,Integer>>( new Comparator<Map.Entry<String,Integer>>()
     {
          public int compare(Map.Entry<String,Integer> e1,Map.Entry<String,Integer> e2){
            int counts = -  (e1.getValue() - e2.getValue());
            return counts==0?e1.getKey().compareTo(e2.getKey()):counts;
          }
     });

     for (String key: counts.keySet()){
       countsSorted.add(new AbstractMap.SimpleEntry<String,Integer>(key,counts.get(key)));
     }

     int i=0;
    Iterator<Map.Entry<String,Integer>> it= countsSorted.iterator();
     while ( it.hasNext() && i++<3){
       Map.Entry<String,Integer> e=it.next();
       System.out.println("word: "+ e.getKey()+" counts:"+e.getValue());

     }
  }

  public static void mostCommon10WordsStream(String text){
    String[] words = text.split("\\s+");
    Map<String, Integer> counts= Arrays.stream(words).map(String::toLowerCase).collect(Collectors.toMap(k->k,v->1,(u,v)->u+v));
    counts.entrySet().stream().sorted((u,v)->{
             int cnts = -  (u.getValue() - v.getValue());
             return cnts==0?u.getKey().compareTo(u.getKey()):cnts;

    }).limit(4).forEach(e-> System.out.print(" "+e));
  }
  public static void mostCommon10WordsStream2(String text){

    Map<String, Integer> counts= Stream.of(text).flatMap(s->Arrays.stream(s.split("\\s+"))).map(String::toLowerCase).collect(Collectors.toMap(k->k,v->1,(u,v)->u+v));
    counts.entrySet().stream().sorted((u,v)->{
             int cnts = -  (u.getValue() - v.getValue());
             return cnts==0?u.getKey().compareTo(u.getKey()):cnts;

    }).limit(4).forEach(e-> System.out.print(" "+e));
  }



}
