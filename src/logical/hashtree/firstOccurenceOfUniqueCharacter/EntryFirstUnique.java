package logical.hashtree.firstOccurenceOfUniqueCharacter;

import java.util.*;

/**
 * Created by ilya on 15.11.2016.
 */
public class EntryFirstUnique
{
  static void occurence(String s){

    final Map<Character,Set<Integer>> chars= new HashMap<Character,Set<Integer>>();

    for (int i=0;i<s.length();i++){
        final Character c = s.charAt(i);
        final Set<Integer> indexes;
        indexes = chars.containsKey(c)?chars.get(c):new TreeSet<Integer>();
        indexes.add(i);
        chars.put(c,indexes);
    }

    TreeSet<Map.Entry<Character,TreeSet<Integer>>> sortedChars  = new TreeSet<Map.Entry<Character,TreeSet<Integer>>>(new Comparator<Map.Entry<Character, TreeSet<Integer>>>()
    {
      @Override
      public int compare(Map.Entry<Character, TreeSet<Integer>> o1, Map.Entry<Character, TreeSet<Integer>> o2)
      {
        if (o1.getValue().size()==o2.getValue().size()&&o1.getValue().size()==0)//both are 0
          return 1;
        int major = o1.getValue().size()-o2.getValue().size();
        return major==0? o1.getValue().first()-o2.getValue().first():major;
      }
    });
    for (Map.Entry<Character,Set<Integer>> e:chars.entrySet()) {
         sortedChars.add(new AbstractMap.SimpleEntry<Character, TreeSet<Integer>>(e.getKey(),(TreeSet<Integer>) e.getValue()));
    }
    System.out.printf("1st nr for %s:" +sortedChars.first().getKey()+", index:"+sortedChars.first().getValue().first()+"\n",s);
  }


  public static void main(String[] args)
  {
          occurence("abcdehabcedkajhsladjlkadjlaksjjsjhlkjslkjkgjhlkjhakjhflkjadlkjhklda");
          occurence("aaabbc");
          occurence("aaawad");

//          occurence("abcdehabced");
//          occurence("abcdehabced");
  }
  abstract class Digest {
  private Map<byte[], byte[]> cache = new HashMap<byte[], byte[]>();

  public byte[] digest(byte[] input) {
            byte[] result = cache.get(input);
            if (result == null) {
                synchronized (cache) {
                    result = cache.get(input);
                    if (result == null) {
                        result = doDigest(input);
                        cache.put(input, result);
                    }
                }
            }
            return result;
        }
        protected abstract byte[] doDigest(byte[] input);
    }
}
