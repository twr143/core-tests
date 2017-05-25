package logical.candies;

import java.util.Stack;

/**
 * Created by ilya on 29.11.2016.
 */
public class EntryCandies
{

  static int minCandies(int[] r){
    Stack<Integer> rates = new Stack<Integer>();
    int i=0;
    int counter =1;
    int total=0;
    while (i<r.length){
      if (rates.isEmpty()||r[i]<r[rates.peek()]){
        rates.push(i);
        i++;
      }else if(r[i]>r[rates.peek()]){
        total+=i-rates.pop()+1;
      }else{
        //r[i]=r[rates.peek()]
        total+=counter;
        i++;
      }

    }
    if (!rates.isEmpty()){
      i= rates.pop();
      total++;
    }

    while (!rates.isEmpty()){
      total+=i-rates.pop()+1;
    }
    return total;
  }
  static int minCandies2(int[] r){
    int[] candies = new int[r.length];
    int count=1;
    for (int i = 0; i < candies.length; i++)
    {
       candies[i]=1;
    }
    for(int i=0;i<r.length-1;i++){
      if (r[i+1]>r[i]){
        count++;
        candies[i+1]=count;
      } else if (r[i+1]==r[i]){
        candies[i+1]=candies[i];
//        count=1;
      } else if (i>0&&r[i+1]<r[i]&&r[i-1]<=r[i]){
        candies[i]=count;
        count=1;
      }
    }
    count=1;
    for(int i=r.length-1;i>0;i--){
      if (r[i-1]>r[i]){
        count++;
        candies[i-1]=count;
      } else if (r[i-1]==r[i]){
        candies[i-1]=candies[i];
//        count=1;
      } else if ((i<r.length-1)&&r[i-1]<r[i]&&r[i+1]<=r[i]){
        candies[i]=Math.max(candies[i],count);
        count=1;
      }
    }
    int total=0;
    for (int i = 0; i < r.length; i++)
    {
      System.out.print(candies[i]+" ");
      total+=candies[i];
    }
    return total;
  }
  public static void main(String[] args)
  {
//    int[] r = {1,6,17,16,15,14,13,12,1};
//    int[] r = {14,13,12,1,5,4,5,6,3,4};
//    int[] r = {4,3,2,1,1,5,6,7};
//    int[] r = {4,3,2,1,5,5,6,7};
    int[] r = {1,3,5,7,7,7,6,5,4};
    System.out.println("res="+minCandies2(r));
  }
}
