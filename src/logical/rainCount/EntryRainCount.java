package logical.rainCount;

/**
 * Created by ilya on 02.12.2016.
 */
public class EntryRainCount
{
  static int firstIndexGEValue(int startIndex, long[] array, long value){
      for(int i=startIndex;i<array.length;i++){
          if (array[i]>=value)
              return i;
      }
      return -1;
  }
  static long addRainVolume(int startIndex, int endIndex, long[] array, long value){
      long volume = 0;
      for(int i=startIndex;i<endIndex;i++){
           volume+=value-array[i];
      }
      return volume;
  }
  static long rainCount(long[] source){
    int i=1;
    long rainVolume=0;
    long startTime = System.currentTimeMillis();
    while (i<source.length){
        if (source[i]<source[i-1]) {
            int fIndexGEValue=-1;
            long value = source[i-1];
            while (value>source[i]){
                fIndexGEValue=firstIndexGEValue(i+1,source,value);
                if (fIndexGEValue!=-1)
                    break;
                value--;
            }
            if (fIndexGEValue!=-1){
                rainVolume+=addRainVolume(i,fIndexGEValue,source,value);
                i=fIndexGEValue+1;
            }else i++;

        }
        else i++;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("my time ms:"+(endTime - startTime));

    return rainVolume;
  }
  static long trap(long[] height) {
      long startTime = System.currentTimeMillis();
      long result = 0;

      if(height==null || height.length<=2)
          return result;

      long left[] = new long[height.length];
      long right[]= new long[height.length];

      //scan from left to right
      long max = height[0];
      left[0] = height[0];
      for(int i=1; i<height.length; i++){
          if(height[i]<max){
              left[i]=max;
          }else{
              left[i]=height[i];
              max = height[i];
          }
      }

      //scan from right to left
      max = height[height.length-1];
      right[height.length-1]=height[height.length-1];
      for(int i=height.length-2; i>=0; i--){
          if(height[i]<max){
              right[i]=max;
          }else{
              right[i]=height[i];
              max = height[i];
          }
      }

      //calculate totoal
      for(int i=0; i<height.length; i++){
          result+= Math.min(left[i],right[i])-height[i];
      }

      long endTime = System.currentTimeMillis();
      System.out.println("lin time ms:"+(endTime - startTime));
      return result;
  }
  public static void main(String[] args)
  {
//    Integer[] source = {0,1,0,2,1,0,1,3,2,1,2,1};
    long startTime = System.currentTimeMillis();
    int size= 100000000;
    long n_waves= 100;
    double size_d=size;
    long[] source = new long[size];
    for (int i=0;i<size;i++){
      double i_d=i;
      source[i]=(long)Math.round(Math.abs(i_d*Math.sin(Math.PI*(i_d*n_waves/size_d))));
    }
//    System.out.println("vol my:"+logical.rainCount(source));
    long endTime = System.currentTimeMillis();
    System.out.println("fill array ms:"+(endTime - startTime));

    System.out.println("vol linear:"+trap(source));
  }
}
