package logical.rainCount;

import static logical.rainCount.DataGenerator.concurrArrayFillFixedFP;
import static logical.rainCount.DataGenerator.nCores;

/**
 * Created by ilya on 04.12.2016.
 */
public class EntryRainCountLev
{
  static long puddleVolume(long[] h)
   {
       long startTime = System.currentTimeMillis();
       int N = h.length;
       long glMax = 0;
       int glMaxIndex = 0;
       for( int i = 0; i < N; i++ )
           if (h[i] > glMax)
           {
               glMaxIndex = i;
               glMax = h[i];
           }

       int j = 0;
       long curMax = h[0];
       long vol = 0;

       while( j < glMaxIndex )
       {
           while( (h[j + 1] >= h[j]) && (j < glMaxIndex) )
               j++;

           curMax = h[j];

           while( (j < glMaxIndex) && (h[j] <= curMax) )
           {
               vol += curMax - h[j];
               j++;
           }

       }

       j = N - 1;
       curMax = h[j];

       while (j > glMaxIndex)
       {
           while ( (h[j - 1] >= h[j]) && (j > glMaxIndex) )
               j--;

           curMax = h[j];

           while ( (j > glMaxIndex) && (h[j] <= curMax) )
           {
               vol += curMax - h[j];
               j--;
           }

       }
       long endTime = System.currentTimeMillis();
       System.out.println("rain count Lev ms:"+(endTime - startTime));

       return vol;
   }
    public static void main(String[] args)
    {
        System.out.println("# cores:"+nCores);
        System.out.println("vol lin parall:"+puddleVolume(concurrArrayFillFixedFP(100000000,100,16)));

    }


}
