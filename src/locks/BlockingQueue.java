package locks;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ilya on 05.12.2016.
 */
public class BlockingQueue<T>
{
  final Lock lock = new ReentrantLock();
  final Condition empty =lock.newCondition();
  final Condition full = lock.newCondition();
  final Queue<T> queue = new LinkedList<T>();
  static final int maxSize=5;
//  volatile int last_index=0;
  public void put(T obj){

    try
    {
      lock.lockInterruptibly();
      while (queue.size()>=maxSize){
        System.out.println("queue is full, waiting...");
        full.await();
      }
      queue.add(obj);
        empty.signalAll();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }finally
    {
      lock.unlock();
    }
  }
  public T take(){
    T result =null;
    try
    {
      lock.lockInterruptibly();
      while (queue.isEmpty()){
        System.out.println("queue is empty, waiting...");
        empty.await();

      }
      result=(T)queue.remove();
      full.signalAll();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }finally
    {
      lock.unlock();
    }
    return result;
  }
  @Override
  public String toString()
  {
    String sb = "";
    for (T t:queue)
      sb+=" "+t;
    sb+="\n";

    return "BQueue{" + sb +
            '}';
  }
}
