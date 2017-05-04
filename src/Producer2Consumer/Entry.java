package Producer2Consumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ilya on 16.10.2016.
 */
public class Entry {
    public static void main(String args[]) {
        System.out.println("How to use wait and notify method in Java");
        System.out.println("Solving Producer Consumer Problem");

        Queue<QueueBean> buffer = new LinkedList<QueueBean>();
        int maxSize = 10;
        int range = 20;

        Thread producer = new Producer(buffer, maxSize, "PRODUCER", range);
        Thread consumer = new Consumer1(buffer, maxSize, "CONSUMER1");
        Thread consumer2 = new Consumer2(buffer, maxSize, "CONSUMER2");

        producer.start();
        consumer.start();
        consumer2.start();


    }
}
