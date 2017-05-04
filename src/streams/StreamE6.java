package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ilya on 05.03.2017.
 */
public class StreamE6 {
    public static void main(String[] args) {
        List<Integer> excList = new ArrayList<Integer>();
        String excListId = "41 62 18 75";
        String[] excListIdArray = excListId.split("\\s");
        excList = Arrays.stream(excListIdArray).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(excList);
//    excList.forEach(n-> System.out.print(n+" "));
        List<String> names = Arrays.asList("Tom", "Jerry", "Ilya");
        String strNames = names.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
        System.out.println(strNames);


    }
}
