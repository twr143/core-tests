package concurr;

/**
 * Created by ilya on 14.11.2016.
 */
public class CountDownLatchApp
{
  public static void main(String[] args)
      throws InterruptedException, java.io.IOException
  {
      System.out.println("Prepping...");

      Race r = new Race(
          "Beverly Takes a Bath",
          "RockerHorse",
          "Phineas"
          );

      r.run();
  }
}
