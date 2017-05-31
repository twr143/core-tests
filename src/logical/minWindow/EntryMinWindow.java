package logical.minWindow;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ilya on 23.11.2016.
 */
public class EntryMinWindow
{
  public static String minWindow(String s, String t) {
      if(t.length()>s.length())
          return "";
      String result = "";

      //character counter for t
      HashMap<Character, Integer> target = new HashMap<Character, Integer>();
      for(int i=0; i<t.length(); i++){
          char c = t.charAt(i);
          if(target.containsKey(c)){
              target.put(c,target.get(c)+1);
          }else{
              target.put(c,1);
          }
      }

      // character counter for s
      HashMap<Character, Integer> srcMap = new HashMap<Character, Integer>();
      int left = 0;
      int minLen = s.length()+1;

      int count = 0; // the total of mapped characters

      for(int i=0; i<s.length(); i++){
          char c = s.charAt(i);
           Integer srcIndex = srcMap.get(c);
          Integer targetIndex = target.get(c);

          if(targetIndex!=null){
              if(srcIndex!=null){
                  if(srcIndex<targetIndex){
                      count++;
                  }
                  srcMap.put(c,srcIndex+1);
              }else{
                  srcMap.put(c,1);
                  count++;
              }
          }

          if(count == t.length()){
//            System.out.println("t.length=count="+count);
//            System.out.println("count:"+count+",c="+c);
              char sc = s.charAt(left);
//            System.out.println("sc="+sc);
//            System.out.println("left="+left);
              srcIndex = srcMap.get(sc);
              targetIndex = target.get(sc);

              while ((srcIndex==null) || srcIndex > targetIndex) {
                  if ((srcIndex!=null) && srcIndex > targetIndex)
                      srcMap.put(sc, srcIndex - 1);
                  left++;
                  sc = s.charAt(left);
                  srcIndex = srcMap.get(sc);
                  targetIndex = target.get(sc);
              }

              if (i - left + 1 < minLen) {
                  result = s.substring(left, i + 1);
//                System.out.println("result="+result);
                  minLen = i - left + 1;
              }
          }
      }

      return result;
  }
  public static String minW2(String word, String targ){

  		if(word==null || word.length() < 1 ) return "";

  		if(targ==null || targ.length() < 1) return "";



   		char[] arr = targ.toCharArray();

  		String shortest = word;

  		for(int i =0; i < word.length(); i++){

  			String temp = "";

  			ArrayList<Character> target = new ArrayList<Character>();

  			for(Character c: arr){

  				target.add((Character)c);

  			}

            for(int j = i; j < word.length(); j++){

              Character a = (Character) word.charAt(j);

              temp+=a;

              if(target.contains(a)){

              target.remove(a);

              }



              if(target.size()==0){

                if(shortest.length() > temp.length()) shortest = temp;

                break;

              }

            }

        }

  			return shortest;

  		}

  public static void main(String[] args)
  {
    String s = "a";
    String t="a";
    System.out.println("s="+s);
    System.out.println("t="+t);
    String result = minWindow(s,t);
    System.out.println("result="+result);
//    result = minW2(s,t);
//    System.out.println("result="+result);
  }
}
