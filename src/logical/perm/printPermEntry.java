package logical.perm;

import defaultmethods.A;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by ilya on 29.05.2017.
 *
 * find all the permutations for S_n group
 */
public class printPermEntry
{

  public static void main(String[] args)
  {
    int n=3;
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    printPerms(new TreeMap<Integer,Integer>(),0,n,result);
    IntStream.range(0,result.size()).forEach(i->{
      List<Integer> a = result.get(i);
      System.out.println(a +"\t" + (i+1));
    });
  }
  /**
   * finds al perms for S_n group
   * @param perms
   * indexes of each member relatively vacant cells (marked by 0's)
   * @param cur
   * current member index
   * @param total
   * n
   * @param results
   * result list of permutations
   */
  static void printPerms(Map<Integer,Integer> perms, int cur, int total, List<List<Integer>> results){
    if (cur<total){
      for (int i=0;i<total-cur;i++){
        perms.put(cur,i);
        printPerms(perms,cur+1,total,results);
      }
    }else{
      Integer[] arr = new Integer[total];
      Arrays.fill(arr,0);
      for (Integer key:perms.keySet()){
          int index = 0; //index to calculate
          int rest = perms.get(key);  // how many zeros before resulting index
          while (rest>=0)
            if (arr[index++]==0)
              rest--;
          arr[index-1]=key+1;
      }
      results.add(Arrays.asList(arr));
    }
  }
}
