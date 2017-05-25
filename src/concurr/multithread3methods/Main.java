package concurr.multithread3methods;

public class Main {

    public static void main(String[] args) throws InterruptedException {
           Foo foo = new Foo();
          Thread t1 = new ThreadFoo(foo,1);
          t1.start();
            t1.join();
        Thread t2 = new ThreadFoo(foo,2);
        t2.start();
          t2.join();
        Thread t3 = new ThreadFoo(foo,3);
        t3.start();
          t3.join();

    }
}
