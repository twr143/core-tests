package streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by ilya on 19.12.2016.
 */
public class StreamE3
{
  public static void main(String[] args)
  {
//    Stream<String> stream = Stream.iterate("", (s) -> s + "1");
//    stream.limit(2).map(x -> x + "2").forEach(x-> System.out.print(x+" "));

    Predicate<? super String> predicate = s -> s.startsWith("g");
    Stream<String> stream1 = Stream.generate(() -> "growl! ").limit(3);
    Stream<String> stream2 = Stream.generate(() -> "growl! ").limit(3);
    boolean b1 = stream1.anyMatch(predicate);
    boolean b2 = stream2.allMatch(predicate);
    System.out.println(b1 + " " + b2);
    Stream.generate(() -> "growl! ").limit(3).forEach(s-> System.out.print(s+" "));

  }
}
