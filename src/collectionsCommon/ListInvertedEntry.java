package collectionsCommon;

/**
 * Created by ilya on 24.05.2017.
 */
public class ListInvertedEntry
{

  public static void main(String[] args)
  {
    add( new Node(1));
    add( new Node(2));
    add( new Node(3));
    add( new Node(4));
    add( new Node(5));
    print();
    inverse();
    print();
  }
  static class Node{
    private int value;
    public Node next=null;
    public Node(int value){
      this.value = value;
    }
    @Override
    public String toString()
    {
      return "[" +
              + value +
              ']';
    }
  }
  static Node head=null;
  static void add(Node newNode){
//    Node lHead = head;
    newNode.next=head;
    head=newNode;
  }
  static void print(){
//    Node lHead = head;
    Node lHead=head;
    while (lHead!=null){
      System.out.print(lHead+" ");
      lHead=lHead.next;
    }
    System.out.println();
  }


  static void inverse(){
    Node lHead=head;
    Node current=lHead.next;
    if (current==null)         // 1 element in the list only
      return;
    Node next = current.next;
    Node prev = lHead;
    if (next!=null){
      //3 or more elements
      while (next!=null){
        current.next=prev;      // reverse next to previous
        if (prev==head)
          prev.next=null;
        prev=current;
        current=next;
        next=next.next;
      }
      current.next=prev;
      head=current;
    }else{
      //2 elements only
      prev.next=null;
      current.next=prev;
      head=current;
    }
  }
}
