package multithread3methods;

/**
 * Created by ilya on 16.10.2016.
 */
public class ThreadFoo extends Thread
{

  private Foo foo;
  private int order;
  ThreadFoo(Foo foo, int order)
  {
    this.foo=foo;
    this.order=order;
  }
  public void run() {
    if (order==1)
      foo.first();
    else if (order==2)
      foo.second();
    else if (order==3)
      foo.third();
  }

}
