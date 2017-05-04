package queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by ilya on 11.01.2017.
 */
public class PriorityE1
{
  public static void main(String[] args)
  {
    Integer elem=0;
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>()
    {
      @Override
      public int compare(Integer o1, Integer o2)
      {
        return -(o1-o2);
      }
    });
    priorityQueue.add(1);
    priorityQueue.add(2);
    priorityQueue.add(3);
    priorityQueue.add(4);
    priorityQueue.add(5);

    System.out.println(priorityQueue);
    elem=priorityQueue.remove();
    System.out.println(priorityQueue);
    priorityQueue.remove(2);
    System.out.println(priorityQueue);
/*    priorityQueue.remove();
    System.out.println(priorityQueue);
    priorityQueue.remove();
    System.out.println(priorityQueue);
    priorityQueue.add(0);
    System.out.println(priorityQueue);
  */
  }
}
