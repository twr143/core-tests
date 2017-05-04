package streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ilya on 19.12.2016.
 */
public class StreamE4 {
    public static void main(String[] args) {
//    Stream<String> s = Stream.generate(() -> "meow");
//    boolean match = s.allMatch(String::isEmpty);
//    System.out.println(match);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("BB");
        list.add("c");
        list.add("CC");
        list = sortedList2(list);
        System.out.println(list);
    }

    private static List<String> sortedList(List<String> list) {
        return list.stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList());
    }

    private static List<String> sortedList2(List<String> list) {
        return list.stream()
                .sorted(Comparator.comparing(String::length).thenComparing(String::compareToIgnoreCase))
                .collect(Collectors.toList());
    }

}
