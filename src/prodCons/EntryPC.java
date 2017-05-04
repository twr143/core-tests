package prodCons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
public class EntryPC
{
  public static void main(String args[]) {
          System.out.println("How to use wait and notify method in Java");
          System.out.println("Solving Producer Consumper Problem");

          Queue<Integer> buffer = new LinkedList<Integer>();
          int maxSize = 10;
          int range = 20;

          Thread producer = new Producer(buffer, maxSize, "PRODUCER", range);
          Thread consumer = new Consumer(buffer, maxSize, "CONSUMER1");

          producer.start();
          consumer.start();

      }

}
