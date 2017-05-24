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
    newNode.next=head;
    head=newNode;
  }
  static void print(){
    Node lHead=head;
    while (lHead!=null){
      System.out.print(lHead+" ");
      lHead=lHead.next;
    }
    System.out.println();
  }


  static void inverse(){
    Node lHead=head;
    Node currentNode=lHead.next;
    if (currentNode==null)         // 1 element in the list only
      return;
    Node nextNode = currentNode.next;
    Node prevNode = lHead;
    if (nextNode!=null){
      //3 or more elements
      while (nextNode!=null){
        currentNode.next=prevNode;      // reverse next to previous
        if (prevNode==head)
          prevNode.next=null;
        prevNode=currentNode;
        currentNode=nextNode;
        nextNode=nextNode.next;
      }
      currentNode.next=prevNode;
      head=currentNode;
    }else{
      //2 elements only
      prevNode.next=null;
      currentNode.next=prevNode;
      head=currentNode;
    }
  }
}
