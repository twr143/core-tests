package logical.twoStacksSort;

import java.util.Stack;

/**
 * Created by ilya on 26.05.2017.
 */
/*
Sorts stack 1 (s1) into Stack 2 (s2)
 */
public class TwoStacksSortEntry
{
  public static void main(String[] args)
  {
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();
    s1.push(10);
    s1.push(5);
    s1.push(7);
    s1.push(3);
    s1.push(1);
    s1.push(9);

    sortV1(s1,s2);

    System.out.println(s2);
  }
  /*
      s1 - stack to sort
      s2 - empty helper stack
      result - s2 sorted stack with elements from s1
   */
  static void sortV1(Stack<Integer> s1, Stack <Integer> s2){
    while (!s1.isEmpty()){
      if (s2.isEmpty()){
        s2.push(s1.pop());
      }else{
        Integer k = s1.pop();
        if (s2.peek()>k)
          s2.push(k);
        else{
          int count=0,l;
          while(!s2.isEmpty()){
            l=s2.peek();
            if (l>=k)
              break;
            s1.push(l);
            count++;
            s2.pop();
          }
          s2.push(k);
          while (count-->0)
            s2.push(s1.pop());
        }
      }
    }
  }
}
