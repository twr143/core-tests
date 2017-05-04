package rotatedArr;

/**
 * Created by ilya on 24.11.2016.
 */
public class EntryMinRepeat1
{
  private static int findMinReap3(int[] arr){
    int left=0, right=arr.length-1;
    if (arr[left]==arr[right])
      while (arr[left]==arr[right]&&right>left)
        right--;
    if (left==right)
      return left;
    if (arr[left]<arr[right])
      return left;
//    System.out.printf("l:%d,r:%d\n",left,right);
    while (right-left>1){
      int m = left+ (right-left)/2;
    System.out.printf("l:%d,r:%d\n",left,right);
//      System.out.println("m="+m);
      if(arr[left]>arr[m])
        right=m;
      else
        left=m;
    }
    return arr[left]<=arr[right]?left:right;

  }
  public static void main(String[] args)
  {
//    int a[] = {1,2,3,4,5,6,7,8,0,0,0,0,0,0};
//    int a[] = {1,1,1,1,0,1,1};
    int a[] = {1,1,1,2,-1,0,0,0,1,1,1,1,1,1};

    System.out.println("minIndex="+findMinReap3(a));

  }

}
