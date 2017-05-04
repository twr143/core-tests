package hashtree.longestSubstr;

import java.util.*;

/**
 * Created by ilya on 13.11.2016.
 */
public class EntryLongestSubstrNoRepeating
{
  private static void printLongestSubstr(String text)
  {
    int i,j=0;
    // map contains the longest substring's length: key = start index of the substring, i.e. 0,1,2,3... length-2, value = length of the substring
    TreeSet<AbstractMap.Entry<Integer,Integer>> maxlengthNonRepeat = new TreeSet<AbstractMap.Entry<Integer, Integer>>(new Comparator<AbstractMap.Entry<Integer, Integer>>()
    {
      @Override
      public int compare(AbstractMap.Entry<Integer, Integer> o1, AbstractMap.Entry<Integer, Integer> o2)
      {
        return -o1.getValue().compareTo(o2.getValue());
      }
    });
    for (i=0;i<text.length()-1;i++)
    {
      Set<Character> charsDiff = new HashSet<Character>();
      charsDiff.add(new Character(text.charAt(i)));
      for (j = i + 1; j <= text.length(); j++)
      {
            if (j == text.length()||!charsDiff.add(new Character(text.charAt(j)))
              /*addition unsuccessful, first copy of the char, breaking loop and recording start index i and length j-i*/){
              maxlengthNonRepeat.add(new AbstractMap.SimpleEntry<Integer,Integer>(i,j-i));
              break;
            }
      }
    }
    StringBuilder resultStr = new StringBuilder();
    resultStr.append(text).
            append(": is of length ").
            append(maxlengthNonRepeat.first().getValue()).
            append(" at index ").
            append(maxlengthNonRepeat.first().getKey()).
            append(":").
            append(text.substring(maxlengthNonRepeat.first().getKey(),maxlengthNonRepeat.first().getKey()+maxlengthNonRepeat.first().getValue()));
    System.out.println(resultStr.toString());
  }

  public static void main(String[] args)
  {
    printLongestSubstr("abbae");
    printLongestSubstr("abbcdrhec");
    printLongestSubstr("abcadbhec");
    printLongestSubstr("abcdefgh");
    printLongestSubstr("aaaaa");
//    printLongestSubstr("abbae");
  }

}
