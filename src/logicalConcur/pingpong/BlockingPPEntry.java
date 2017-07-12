package logicalConcur.pingpong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ilya on 12.07.2017.
 */
public class BlockingPPEntry {
  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(3);
    BlockingPP bpp = new BlockingPP();
    es.submit(new Runnable() {
      @Override
      public void run() {
        try {
          TimeUnit.MICROSECONDS.sleep(50L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        bpp.ping();
        bpp.pong();
        bpp.ping();
      }
    });
    es.submit(new Runnable() {
      @Override
      public void run() {
        bpp.pong();
        bpp.ping();
        bpp.pong();
      }
    });
    /*es.submit(new Runnable() {
      @Override
      public void run() {
        bpp.pong();
      }
    });*/

     es.shutdown();

  }

 static class BlockingPP{
   final Lock lock = new ReentrantLock();
   final Condition ping = lock.newCondition();
   final Condition pong = lock.newCondition();
   boolean nextPing=false;

   void ping(){
      try {
       lock.lockInterruptibly();
       while (!nextPing)
         ping.await();
        NonblockPPEnntry.UnblockPP.print(true);
        nextPing=!nextPing;
        pong.signal();
      } catch (InterruptedException ie){
        ie.printStackTrace();
      }
      finally {
        lock.unlock();
      }
   }
   void pong(){
     try {
      lock.lockInterruptibly();
       while (nextPing)
         pong.await();
        NonblockPPEnntry.UnblockPP.print(false);
        nextPing=!nextPing;
        ping.signal();
     } catch (InterruptedException ie){
       ie.printStackTrace();
     }
     finally {
       lock.unlock();
     }

   }

 }

}
