package streams;

import kotlin.reflect.jvm.internal.pcollections.HashPMap;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by ilya on 19.12.2016.
 */
public class StreamEntry1
{
  public static void main(String[] args)
  {
    Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8 };
    List<Integer> listOfIntegers =
        new ArrayList<>(Arrays.asList(intArray));

    /*System.out.println("listOfIntegers:");
    listOfIntegers
        .stream().peek(i-> {System.out.println(i++);})
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");
    */
    double av=IntStream.rangeClosed(1,8).boxed().map(i->i+1).filter(i->i<5).mapToLong(i->i).average().getAsDouble();
    System.out.println("av="+av);

    /*System.out.println("listOfIntegers sorted in reverse order:");
    Comparator<Integer> normal = Integer::compare;
    Comparator<Integer> reversed = normal.reversed();
    Collections.sort(listOfIntegers, reversed);
    listOfIntegers
        .stream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("Parallel stream");
    listOfIntegers
        .parallelStream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("Another parallel stream:");
    listOfIntegers
        .parallelStream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("With forEachOrdered:");
    listOfIntegers
        .parallelStream().peek(i->i++)
        .forEachOrdered(e -> System.out.print(e + " "));
    System.out.println("");
  */
  }
}
