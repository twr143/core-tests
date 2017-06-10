package publish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ilya on 21.05.2017.
 *
 * this code shows that shared object is always safely published in HotSpot if shared.
 */
public class SafePublishEntry
{

  private static final int ARRAY_SIZE=10000;   // this runs for 10 seconds if delay in constructor is 1 ms
  public static void main(String[] args)
  {


    AlwaysSafePublished[] array = new AlwaysSafePublished[ARRAY_SIZE];
    ExecutorService executor = Executors.newFixedThreadPool(2);

              executor.submit(new Publisher(array));
              executor.submit(new Validator(array));

               executor.shutdown();

  }
  static class Publisher implements Runnable{
    AlwaysSafePublished[] array;
    Publisher( AlwaysSafePublished[] array){
            this.array=array;
        }
        @Override
        public void run() {

          try
          {
            Thread.sleep(1);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
          for(int i = 0; i < ARRAY_SIZE; i++){
              array[i] = new AlwaysSafePublished();
            }
        }
    }
  static class Validator implements Runnable{
    AlwaysSafePublished[] array;
    boolean initialNull =false;
    Validator( AlwaysSafePublished[] array){
            this.array=array;
        }
        @Override
        public void run() {
           //delay to let publisher create some array references
          try
          {
            Thread.sleep(1);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }

            for(int i = ARRAY_SIZE -1; i >=0 ; i--){
              if (array[i]==null){
                if (i == 0){
                  System.out.println("empty");
                }
                if (i==ARRAY_SIZE-1)
                  initialNull =true;
              }else{
                System.out.println((array[i].number()+" #"));
                break;
              }
            }
            System.out.println(("initialNull:"+ initialNull));
        }
    }

}
