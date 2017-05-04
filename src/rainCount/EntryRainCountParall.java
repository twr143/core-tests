package rainCount;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ilya on 04.12.2016.
 */
public class EntryRainCountParall
{
  static final int nCores=Runtime.getRuntime().availableProcessors();
  static long trap(final long[] height) {
      long startTime = System.currentTimeMillis();
      final AtomicLong result = new AtomicLong(0);

      if(height==null || height.length<=2)
          return result.get();

      final long left[] = new long[height.length];
      final long right[]= new long[height.length];



    final Phaser phaserMaxAndVolume = new Phaser(3/*for left and right scan and main*/) {
      protected boolean onAdvance(int phase, int registeredParties) {
        System.out.println("phase="+phase);
        System.out.println("registeredParties="+registeredParties);
        return phase >= 1 || registeredParties == 0;
      }
    };
//    phaserMaxAndVolume.register();//for left scan
//    phaserMaxAndVolume.register();//for right scan
      new Thread() {
        public void run() {
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
          phaserMaxAndVolume.arriveAndAwaitAdvance();
        }
      }.start();

    new Thread() {
      public void run() {
        long max = height[height.length-1];
        right[height.length-1]=height[height.length-1];
        for(int i=height.length-2; i>=0; i--){
            if(height[i]<max){
                right[i]=max;
            }else{
                right[i]=height[i];
                max = height[i];
            }
        }
        phaserMaxAndVolume.arriveAndAwaitAdvance();
      }
    }.start();

    phaserMaxAndVolume.arriveAndAwaitAdvance();

    new Thread() {
      public void run() {
        // first half
        for(int i=0; i<height.length/2; i++){
            result.addAndGet(Math.min(left[i],right[i])-height[i]);
        }
        phaserMaxAndVolume.arriveAndAwaitAdvance();
      }
    }.start();

    new Thread() {
      public void run() {
        // second half
        for(int i=height.length/2; i<height.length; i++){
            result.addAndGet(Math.min(left[i],right[i])-height[i]);
        }
        phaserMaxAndVolume.arriveAndAwaitAdvance();
      }
    }.start();

    phaserMaxAndVolume.arriveAndAwaitAdvance();

    System.out.println("phaser terminated:"+phaserMaxAndVolume.isTerminated());
      long endTime = System.currentTimeMillis();
      System.out.println("lin time ms:"+(endTime - startTime));
      return result.get();
  }

  static long[] concurrArrayFill(final int size, final int nWaves, final int nThreads){
    long startTime = System.currentTimeMillis();
    final double size_d=size;
    final long[] source = new long[size];
    final Phaser ph =new Phaser(nThreads+1){
      protected boolean onAdvance(int phase, int registeredParties) {
        return phase >= 0;
      }
    };
    for (int j=0;j<nThreads;j++){
      final int j1=j;
      Thread t = new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          for (int i=j1*size/nThreads;i<(j1+1)*size/nThreads;i++){
            double i_d=i;
            source[i]=(long)Math.round(Math.abs(i_d*Math.sin(Math.PI*(i_d*nWaves/size_d))));
          }
          ph.arrive();
        }
      });
      t.start();
    }
    ph.arriveAndAwaitAdvance();
    long endTime = System.currentTimeMillis();
    System.out.println("fill array ms:"+(endTime - startTime));
    return source;
  }

  static long[] concurrArrayFillFixedFP(final int size, final int nWaves, final int nThreads){
    long startTime = System.currentTimeMillis();
    final double size_d=size;
    final long[] source = new long[size];
    final Phaser ph =new Phaser(nThreads+1){
      protected boolean onAdvance(int phase, int registeredParties) {
        return phase >= 0;
      }
    };
    ExecutorService executor = Executors.newFixedThreadPool(nCores);
    for (int j=0;j<nThreads;j++){
      final int j1=j;
      executor.submit(new Runnable()
      {
        @Override
        public void run()
        {
          for (int i=j1*size/nThreads;i<(j1+1)*size/nThreads;i++){
            double i_d=i;
            source[i]=(long)Math.round(Math.abs(i_d*Math.sin(Math.PI*(i_d*nWaves/size_d))));
          }
          ph.arrive();
        }
      });
    }
    ph.arriveAndAwaitAdvance();
    executor.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println("fill array FFP ms:"+(endTime - startTime));
    return source;
  }

  public static void main(String[] args)
  {
//    Integer[] source = {0,1,0,2,1,0,1,3,2,1,2,1};
/*    int size= 100000;
    long n_waves= 100;
    double size_d=size;
    long[] source = new long[size];
    for (int i=0;i<size;i++){
      double i_d=i;
      source[i]=(long)Math.round(Math.abs(i_d*Math.sin(Math.PI*(i_d*n_waves/size_d))));
    }*/
    System.out.println("# cores:"+nCores);
    System.out.println("vol lin parall:"+trap(concurrArrayFillFixedFP(100000000,100,16)));
  }


}
