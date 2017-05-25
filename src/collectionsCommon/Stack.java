package collectionsCommon;

import java.util.Arrays;

/**
 * Created by AIKuznetsov on 25.05.2017.
 */
public interface Stack {
    void push(int value);

    default void pushAll(int... value) {
        Arrays.stream(value).forEach(this::push);
    }

    int pop();

    int size();

    void printAll();

    int KthFromLast(int k);
}
