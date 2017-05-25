package logical.maxRectangle;

import java.util.Stack;

/**
 * Created by ilya on 25.11.2016.
 */
public class EntryMaxRect
{
  static int min(int[] arr, int left, int right){
    int min = arr[left];
    for (int i=left;i<=right;i++)
      if(arr[i]<min)
        min=arr[i];
    return min;
  }
  static int squareInterval(int[] arr, int left, int right)
  {
    return (right-left+1)*min(arr,left,right);
  }
  static int maxRectangleInterval(int[] arr, String description)
  {
    int result=0;
    long startTime = System.currentTimeMillis();
    for (int i=0;i<arr.length;i++)
      for (int j=0;j<=i;j++)
      {
        int local=squareInterval(arr,j,i);
        result=Math.max(result,local);
      }
    long endTime = System.currentTimeMillis();
    System.out.println(description+":"+(endTime - startTime));
    return result;
  }
  static  MaxRectInfo largestRectangleArea(int[] height, String description) {
  	if (height == null || height.length == 0) {
  		return null;
  	}
    long startTime = System.currentTimeMillis();


  	Stack<Integer> stack = new Stack<Integer>();

  	int max = 0;
  	int i = 0;
    MaxRectInfo mrinfo=null;

  	while (i < height.length) {
  		//push index to stack when the current height is larger than the previous one
  		if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
  			stack.push(i);
  			i++;
  		} else {
  		//calculate max value when the current height is less than the previous one
  			int p = stack.pop();
  			int h = height[p];
  			int w = stack.isEmpty() ? i : i - stack.peek() - 1;
  			if (Math.max(h * w, max)>max){
          max = Math.max(h * w, max);
          mrinfo = new MaxRectInfo(max,i-1,w ,h);
        }
//        System.out.printf("h:%d,w:%d,stackPeak:%d,i:%d,max temp:%d\n",h,w,stack.isEmpty() ? i :stack.peek(),i,max);
      }

  	}

  	while (!stack.isEmpty()) {
      int p = stack.pop();
      int h = height[p];
  		int w = stack.isEmpty() ? i : i - stack.peek() - 1;
      if (Math.max(h * w, max)>max){
           max = Math.max(h * w, max);
           mrinfo = new MaxRectInfo(max,i-1,w, h);
         }
//      System.out.printf("h:%d,w:%d,max temp:%d\n",h,w,max);
  	}
    long endTime = System.currentTimeMillis();
    System.out.println(description+" time ms:"+(endTime - startTime));

  	return mrinfo;
  }
  static MaxRectInfo largestAreaLev(int[] heights)
  {
      int N = heights.length;

    int[] L = new int[N + 2];
    int[] R = new int[N + 2];
    int[] hs = new int[N + 2];

    for(int i = 0; i < N; i++)
    {
        L[i] = 0;
        R[i] = 0;
        hs[i + 1] = heights[i];
    }

    long startTime = System.currentTimeMillis();
    L[N] = 0;
    L[N + 1] = 0;
    R[N] = 0;
    R[N + 1] = 0;
    hs[0] = hs[1];
    hs[N + 1] = 0;

    int iL = 0;
    int iR = 0;

    int i1, i2;
    int h, s;

    int mal = 0;    //  здесь левая граница максимального прямоугольника
    int mar = 0;    //  здесь правая граница максимального прямоугольника
    int mah = heights[0];    //  здесь высота максимального прямоугольника


    int max_area = heights[0];


    while( true )
    {
        if (iR >= N + 2)
        {
            if (mal == -1)
                mal = 0;
            break;
        }

        while( (hs[iR + 1] >= hs[iR]) && (iR < N ) )
            iR++;

        while( (hs[iR + 1] <= hs[iR]) && (iR < N ) )
            iR++;

        if( iR < N )
        {

            if( hs[iL] <= hs[iR] )
                while( (iL < iR) && (hs[iL + 1] <= hs[iR]) )
                    iL = iL + 1 + R[iL];
            else
                while( (iL > 0) && (hs[iL] > hs[iR]) )
                    iL = iL - 1 - L[iL];

        }
        else
        {
            iL = 0;

        }

        i1 = iL;   // исправить

        if( iR == N )
            i2 = iR;
        else
            i2 = iR - 1;

        while( i1 < i2 )
        {
            if( hs[i1] > hs[i2] )
                h = hs[i2];
            else
                h = hs[i1];

            if( i1 == 0 )
                s = h * (i2 - i1);
            else
                s = h * (1 + i2 - i1);

            if (s > max_area)
            {
                mal = i1 - 1;
                mar = i2 - 1;
                max_area = s;
                mah = h;
            }

            if( hs[i1] > hs[i2] )
                i2 = i2 - 1 - L[i2];
            else
                i1 = i1 + 1 + R[i1];

        }

        if( hs[i1] > max_area )
        {
            max_area = hs[i1];
            mal = i1 - 1;
            mar = i1 - 1;
            mah = hs[i1];
        }

        if (iR >= N)
        {
            if (mal == -1)
                mal = 0;
            break;
        }
        R[iL] = iR - iL - 1;
        L[iR] = R[iL];

        if (iL == 0)
            hs[0] = hs[1 + R[0]];

    }



    long endTime = System.currentTimeMillis();
    System.out.println("lev time ms:"+(endTime - startTime));


    return new MaxRectInfo(max_area,mar,mar-mal+1,mah);
  }
  public static void main(String[] args)
  {

    int size= 10000000;
    int n_waves= 100000;
    double size_d=size;
    int[] arr = new int[size];
    for (int i=0;i<size;i++){
      double i_d=i;
      arr[i]=(int)Math.round(Math.abs(i_d*Math.sin(Math.PI*(i_d*n_waves/size_d))));
    }
//    int[] arr = {2,3,5,4,2};
//    int[] arr = {2,1,2};
//      for (int i = 0; i < arr.length; i++)
//      {
//        System.out.printf(arr[i]+"("+i+")"+" "+(i==arr.length-1?"\n":""));
//      }
//    System.out.println("max sq mine:"+maxRectangleInterval(arr,"n^2"));
    System.out.println("max sq stack:"+largestRectangleArea(arr,"stack"));
    System.out.println("max sq lev:"+largestAreaLev(arr));
  }
   static class MaxRectInfo{
     public int sq;
     public int right;
     public int width;
     public int heigth;
     public MaxRectInfo(int sq, int right, int width, int heigth)
     {
       this.sq = sq;
       this.right = right;
       this.width = width;
       this.heigth = heigth;
     }
     @Override
     public String toString()
     {
       return  "[sq:" + sq +
               ", r:" + right +
               ", w:" + width +
               ", h:" + heigth +
               ']';
     }
   }

}
