package logical.rotatedArray;

import java.util.TreeMap;

/**
 * Created by ilya on 17.10.2016.
 */
public class EntryRA
{
  public static void main(String[] args)
  {
//          int a[] = new int[]{5,6,8,10,-1,0,1,2,3,4};
//          int toSearch = 10;
//    int index_found = search(a,0,a.length-1,toSearch);
//    System.out.println("index found: "+index_found);


//    int b[] = new int[]{5,6,8,10,12,15,19,23,35,48, 51,52,53,54,56};
//    int toSearch = 53;
//    int b_index_found = ListySearch(b,1,0,toSearch);
//    System.out.println("index found: "+b_index_found);

    String b[] = new String []{"at","", "", "", "ball","","","", "car", "", "", "dad", ""};
    String toSearch = "dad";
    int b_index_found = binarySpaced(b,toSearch);
    System.out.println("index found: "+b_index_found);

  }
    static int search(int a[], int left, int right, int x)
    {
      System.out.println("l:"+left+" r:"+right);
      int mid= (left+ right)/ 2;
      if (x == a[mid]) {//Found element
        return mid;
      }
      if (right< left) {
        return -1;
      }

    /* Either the left or right half must be normally ordered. Find out which side
    * is normally ordered, and then use the normally ordered half to figure out
    * which side to search to find x. */
    if (a[left] < a[mid]) {//Left is normally ordered.
     if (x >= a[left] && x < a[mid]) {
       return search(a, left, mid - 1, x); // Search left
     }
     else {
       return search(a, mid+ 1, right, x); // Search right
     }
    }
    else if (a[mid] < a[left]) {//Right is normally ordered.
      if (x > a[mid] && x <= a[right]) {
        return search(a, mid+ 1, right, x); // Search right
      } else {
        return search(a, left, mid - 1, x); // Search left
      }
    }
    else if (a[left] == a[mid]) {//Left or right half is all repeats
     if (a[mid] != a[right]) {//If right is different, search it
       return search(a, mid+ 1, right, x); // search right
     }
     else {//Else, we have to search both halves
       int result= search(a, left, mid - 1, x); // Search left
       if (result == -1) {
          return search(a, mid+ 1, right, x); // Search right
       }
       else {
          return result;
       }
     }
    }
    return -1;
    }
  static int ListySearch(int a[], int left, int shift, int x)
  {
    System.out.println("l:"+left+" s:"+shift);
    if (x==a[0])  return 0;
    if (left+shift>=a.length) return -1;
    if (x==a[left+shift]) return left+shift;
    else if (2*left+shift<a.length&&x>a[2*left+shift])
      return ListySearch(a, 2*left, shift, x);
    else
      return ListySearch(a, 1, shift+left, x);
  }
  private static int binarySpaced(String[] a, String x) {
    int spaceCount=0;
    TreeMap<String,Integer> aData = new TreeMap<String, Integer>();
    int aSpaces[] = new int[a.length];
    int spaceIndex=0;
    for (int i=0;i<a.length;i++)
     {
        if (a[i].isEmpty())
          spaceCount++;
        else
        {
          aSpaces[spaceIndex] = spaceCount;
          aData.put(a[i],spaceIndex);
          spaceIndex++;
        }
     }
    Integer indexX = aData.get(x);
      return indexX!=null?indexX+aSpaces[indexX]:-1;
  }

}
