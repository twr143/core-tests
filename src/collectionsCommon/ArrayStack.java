package collectionsCommon;

/**
 * Created by AIKuznetsov on 25.05.2017.
 */
public class ArrayStack implements Stack {

    private int[] values = new int[10];

    private int pos = -1;

    @Override
    public void push(int value) {
        if (++pos >= values.length) {
            int[] biggerValues = new int[values.length << 1];
            System.arraycopy(values, 0, biggerValues, 0, values.length);
        }
        values[pos] = value;
    }

    @Override
    public int pop() {
        return values[pos--];
    }

    @Override
    public int size() {
        return pos + 1;
    }

    @Override
    public void printAll() {
        for (int i = pos; i > -1; --i) {
            System.out.print(values[i] + " ");
        }
        System.out.println();
    }

    @Override
    public int KthFromLast(int k) {
        return values[pos - k + 1];
    }


}
