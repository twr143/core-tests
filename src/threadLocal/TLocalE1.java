package threadLocal;

/**
 * Created by ilya on 19.04.2017.
 */
public class TLocalE1
{
  static final ThreadLocal userName = new ThreadLocal();
  static final ThreadLocal userNameInherited = new InheritableThreadLocal();
  public static void main(String[] args)
  {
    tlprint(userName);
    tlprint(userNameInherited);
  }

  static void tlprint(ThreadLocal userName)
  {
    userName.set("Jane");
    Runnable run = new Runnable()
    {
      @Override
      public void run()
      {
        String threadName = Thread.currentThread().getName();
        String name = (String) userName.get();
        System.out.println(threadName + ": Welcome " + name);
      }
    };
    Thread t1 = new Thread(run);
    t1.start();
  }
}
