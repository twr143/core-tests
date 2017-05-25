package logical.rotatedArr;

/**
 * Created by ilya on 24.11.2016.
 */
public class EntryMin2
{
  private static int findMin2(int[] arr){
    int left=0, right=arr.length-1;
    if (arr[left]<arr[right])
      return left;
    while (right-left>1){
      int m = left+ (right-left)/2;
      if(arr[left]>arr[m])
        right=m;
      else
        left=m;
    }
    return arr[left]<arr[right]?left:right;

  }
  public static void main(String[] args)
  {
    int a[] = {1,2};
    System.out.println("minIndex="+findMin2(a));

  }
}
