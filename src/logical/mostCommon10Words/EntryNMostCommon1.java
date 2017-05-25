package logical.mostCommon10Words;

import java.util.*;

/**
 * Created by ilya on 13.11.2016.
 */
public class EntryNMostCommon1
{
  // searches for n most common words in text
  public static void printNMostCommonWords(String text,final int n)
  {
    String[] words = text.split("\\s*,*\\s+");

     HashMap<String,Integer> occurences = new HashMap<String, Integer>();
      Arrays.stream(words).forEach( w->{
        if (occurences.containsKey(w))
          occurences.put(w, occurences.get(w) + 1);
        else
          occurences.put(w, 1);
      });
    TreeSet<Map.Entry<String, Integer>> occurSorted = new TreeSet(new Comparator<Map.Entry<String, Integer>>(){
       @Override
       public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
         int major = - o1.getValue().compareTo(o2.getValue());
         return major==0? o1.getKey().compareTo(o2.getKey()):major;
       }
     });


//     for (Map.Entry<String,Integer> entry: occurences.entrySet())
    occurences.entrySet().forEach(e->{
       occurSorted.add(new AbstractMap.SimpleEntry<String,Integer>(e.getKey(),e.getValue()));
    });

      occurSorted.stream().limit(n).forEach(e->System.out.println(e.getKey()+":"+e.getValue()));
  }
  public static void main(String[] args)
  {
    String text = "abc def abc abc, 65, def, 56 12 7 7, 12, 34 7 7 65 def 65 abc 65 def 65 65";
    printNMostCommonWords(text,5);
  }
}
