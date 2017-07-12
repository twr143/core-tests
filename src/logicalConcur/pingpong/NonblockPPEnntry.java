package logicalConcur.pingpong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ilya on 12.07.2017.
 */
public class NonblockPPEnntry {
  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    UnblockPP pp = new UnblockPP();
    es.submit(new Runnable() {
      @Override
      public void run() {
        try {
          TimeUnit.MICROSECONDS.sleep(50L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        pp.ping();
        pp.pong();
        pp.ping();
        pp.ping();
        pp.ping();
        pp.pong();
        pp.ping();
        pp.pong();
        pp.pong();
        pp.pong();
        pp.ping();
        pp.pong();
        pp.pong();
        pp.ping();
        pp.pong();
      }
    });
    es.submit(new Runnable() {
      @Override
      public void run() {
        pp.pong();
        pp.ping();
        pp.pong();
        pp.pong();
        pp.ping();
        pp.pong();
        pp.ping();
        pp.ping();
      }
    });
     es.shutdown();
  }

  static class UnblockPP{
    int cPing,cPong=0;
    boolean nextPing=true ;
    Lock lock = new ReentrantLock();

    void ping(){
      try{
        lock.lockInterruptibly();
        if (nextPing){
          print(true);
          nextPing=!nextPing;
          if (cPong>0){
            cPong--;
            print(false);
            nextPing=!nextPing;
          }
        } else
          cPing++;

      } catch (InterruptedException ie){

      }
      finally {
        lock.unlock();
      }

    }
    void pong(){
      try{
        lock.lockInterruptibly();
        if (!nextPing){
            print(false);
          nextPing=!nextPing;
          if (cPing>0){
            cPing--;
            print(true);
            nextPing=!nextPing;
          }
        } else
          cPong++;

      } catch (InterruptedException ie){

      }
      finally {
        lock.unlock();
      }

    }
    static void print (boolean isPing){
      String result = isPing?"ping":"pong";
      System.out.println(result+", th \t:"+Thread.currentThread().getId());

    }
  }
}
