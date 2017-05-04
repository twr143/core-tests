package rotatedArr;

/**
 * Created by ilya on 24.11.2016.
 */
public class EntrySearch1
{
  private static int findElReap3(int[] arr, int elem){
    int left=0, right=arr.length-1;
    if (arr[left]==arr[right])
      while (arr[left]==arr[right]&&right>left)
        right--;
    if (left==right)
      return arr[left]==elem?left:-1;
//    System.out.printf("l:%d,r:%d\n",left,right);
    while (right-left>1){
      int m = left+ (right-left)/2;
      System.out.printf("l:%d,r:%d\n",left,right);
//      System.out.println("m="+m);
      if (arr[left]<=arr[m])
        if(inBetween(arr[left],arr[m],elem))
          right=m;
        else
          left=m;
      else
        if (inBetween(arr[m],arr[right],elem)&&arr[m-1]!=elem/*we want leftmost element*/)
            left=m;
        else
          right=m;
    }
    System.out.printf("l:%d,r:%d\n",left,right);
    return arr[left]==elem?left:(arr[right]==elem?right:-1);

  }
  static boolean inBetween(int valueSmall, int valueLarge, int value){
    return value>=valueSmall&&value<=valueLarge;
  }
  public static void main(String[] args)
  {
//    int a[] = {1,2,3,4,5,6,7,8,0,0,0,0,0,0};
//    int a[] = {1,3,5,6,0,1};
    int a[] = {1,1,1,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    System.out.println("minIndexForFound="+findElReap3(a,-1));

  }

}
