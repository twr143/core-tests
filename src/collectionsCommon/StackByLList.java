package collectionsCommon;

/**
 * Created by ilya on 25.05.2017.
 * <p>
 * stack based on linked list
 * standard operations : push, pop, peek, printing elements and get k-th element from tail.
 */
public class StackByLList implements Stack {
    public static void main(String[] args) {
        Stack s = new StackByLList();
        s.pushAll(1, 2, 3);
        s.printAll();
        s.pop();
        s.printAll();
        s.pushAll(3, 4, 5, 6, 7);
        s.printAll();
        System.out.println(s.KthFromLast(3));

        System.out.println("ArrayStack");
        s = new ArrayStack();
        s.pushAll(1, 2, 3);
        s.printAll();
        s.pop();
        s.printAll();
        s.pushAll(3, 4, 5, 6, 7);
        s.printAll();
        System.out.println(s.KthFromLast(3));
    }

    private Node head;

    private int size;

    public void push(int value) {
        head = new Node(value, head);
        ++size;
    }

    public int pop() {
        if (head == null)
            throw new RuntimeException("stack is empty");

        int result = head.getValue();
        head = head.getNext();
        --size;
        return result;
    }

    public void printAll() {
        Node traversing = head;
        while (traversing != null) {
            System.out.print(traversing + " ");
            traversing = traversing.getNext();
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    //k-th from last = (n-k)-th from head
    public int KthFromLast(int k) {
        // first find size of the list
        Node Lhead = head;//local head
        for (int index = 0; index < size - k; ++index) {
            Lhead = Lhead.getNext();
        }
        return Lhead.getValue();
    }

}

class Node {

    private int value;

    private Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }
}

