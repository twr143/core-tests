package locks;

/**
 * Created by ilya on 20.06.2017.
 */
public class DeadLockEntry1 {

  public static void main(String[] args) throws InterruptedException{
    Object obj1 = new Object();
            Object obj2 = new Object();
            Object obj3 = new Object();

            Thread t1 = new Thread(new SynchronizedThread(obj1, obj2), "t1");
            Thread t2 = new Thread(new SynchronizedThread(obj2, obj3), "t2");
            Thread t3 = new Thread(new SynchronizedThread(obj3, obj1), "t3");

            t1.start();
            Thread.sleep(500);
            t2.start();
            Thread.sleep(500);
            t3.start();
  }
  static class SynchronizedThread implements Runnable {
      private Object obj1;
      private Object obj2;

      public SynchronizedThread(Object obj1, Object obj2) {
          this.obj1 = obj1;
          this.obj2 = obj2;
      }

      @Override
      public void run() {
          String name = Thread.currentThread().getName();
          synchronized (obj1) {
              System.out.println(name + " acquired lock on Object1: " + obj1);
              try {
                  Thread.sleep(10000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

              synchronized (obj2) {
                  System.out.println(name + " acquired lock on Object2: " + obj2);
              }
              System.out.println(name + " released lock on Object2: " + obj2);
          }
          System.out.println(name + " released lock on Object1: " + obj1);
          System.out.println(name + " Finished Deadlock Test.");
      }
  }
}
