package logical.rotatedArr;

/**
 * Created by ilya on 24.11.2016.
 */
public class EntryMin1
{
  private static int findMin(int[] arr, int left, int right){
    int j=(left+right)/2;
    if (right-left>1){
      if (arr[left]>arr[j])
       return findMin(arr,left,j);
      else
       return findMin(arr,j,right);
    }else if (right-left==1)
    {
       return arr[left]<arr[right]?left:right;
    }
    return left;
  }
  private static int findMin(int[] arr){
    return findMin(arr,0,arr.length-1);
  }
  public static void main(String[] args)
  {
    int[] a= {31,1,16,17,18,20,30};
    System.out.println("minIndex="+findMin(a));
  }
}
