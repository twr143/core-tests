package logical.mergeKSortedLists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by ilya on 31.05.2017.
 */
public class MergeKSLsEntry
{
  public static void main(String[] args)
  {
     ListNode l1=addToFront(new ListNode(1),addToFront(new ListNode(3),addToFront(new ListNode(5),addToFront(new ListNode(7),addToFront(new ListNode(9),null)))));
     ListNode l2=addToFront(new ListNode(1),addToFront(new ListNode(4),addToFront(new ListNode(6),addToFront(new ListNode(8),addToFront(new ListNode(10),null)))));
     ListNode l3=addToFront(new ListNode(2),addToFront(new ListNode(7),addToFront(new ListNode(12),null)));
     ListNode[] arr = new ListNode[3];
     arr[0]=l1;
     arr[1]=l2;
     arr[2]=l3;
     printList(mergeKLists(arr));

    //     printList(l1);
//     printList(inverse2(l1));
  }
  static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        @Override
        public String toString() {
            return "[" +
                    +val +
                    ']';
        }
  }
  static void printList(ListNode head)
  {
    ListNode lHead = head;
    while (lHead != null)
    {
      System.out.print(lHead + " ");
      lHead = lHead.next;
    }
    System.out.println();
  }
  static ListNode addToFront(ListNode node,ListNode head){
       node.next=head;
    return node;
  }
  //returns
  static ListNode addToBack(ListNode node, ListNode tail){
    if (tail!=null)
      tail.next=node;
    tail=node;
    node.next=null;
    return node;
  }

  static ListNode mergeKLists(ListNode[] lists) {
    TreeSet<ListNode> heads = new TreeSet<>(new Comparator<ListNode>()
    {
      @Override
      public int compare(ListNode o1, ListNode o2)
      {
        int diff = o1.val-o2.val;
        return diff==0?System.identityHashCode(o1)-System.identityHashCode(o2):diff;
      }
    });
    for (ListNode n:lists)
      if (n!=null)
        heads.add(n);

    ListNode target=null,targetTail=null;

    while (!heads.isEmpty()){
      ListNode smallestH = heads.first();
      heads.remove(smallestH);
      if (smallestH.next!=null)
        heads.add(smallestH.next);
      targetTail=addToBack(smallestH,targetTail);
      if (target==null)
        target=targetTail;
    }
    return target;
  }

}
