package logical.removeDuplicates;

import java.util.Arrays;

/**
 * Created by ilya on 01.06.2017.
 */
public class RemDupsEntry
{
  public static void main(String[] args)
  {
     int[] src = {1,1,1,2,3,3,4,4,4,5};
    System.out.println(removeDuplicates2(src));
    System.out.println(Arrays.toString(src));
  }
  //my ugly attempt
  static int removeDuplicates(int[] nums) {
      if (nums==null||nums.length==0)
          return 0;
      int l=1,equal=0,totRemoved=0;
      while (l+totRemoved+equal<nums.length){
        if (nums[l+equal]>nums[l+equal-1]){
          if (equal>0){
            System.arraycopy(nums,l+equal,nums,l,nums.length-totRemoved-l-equal);
            totRemoved+=equal;
            equal=0;
          }
          l++;
        }
        else
          equal++;

      }
    return l;
  }

  //standard
  static int removeDuplicates2(int[] A) {
      if (A.length==0) return 0;
      int j=0;
      for (int i=0; i<A.length; i++)
          if (A[i]!=A[j]) A[++j]=A[i];
      return ++j;
  }

}
