package collectionsCommon;

/**
 * Created by ilya on 25.05.2017.
 */
public class StackByLList
{
  public static void main(String[] args) throws Exception
  {
    Stack s = new Stack();
    s.push(new Node(1));
    s.push(new Node(2));
    s.push(new Node(3));
    s.printAll();
    s.pop();
    s.printAll();
    s.push(new Node(3));
    s.push(new Node(4));
    s.push(new Node(5));
    s.printAll();


  }
  static class Node {
      private int value;
      public StackByLList.Node next = null;

      public Node(int value) {
          this.value = value;
      }

      @Override
      public String toString() {
          return "[" +
                  +value +
                  ']';
      }
  }
  static class Stack{
    Node head;
    public void push(Node n){
      n.next=head;
      head=n;
    }
    public Node pop() throws Exception{
      if (head==null)
        throw new Exception("stack is empty");
      Node result=head;
      head=head.next;
      result.next=null;
      return result;
    }
    public Node peek(){
      return head;
    }
    public void printAll(){
      Node Lhead=head;
      while(Lhead!=null){
        System.out.print(Lhead+" ");
        Lhead=Lhead.next;
      }
      System.out.println();
    }
  }
}
