package basic;

/**
 * Created by ilya on 14.06.2017.
 */
public class InnerEntry {

  public static void main(String[] args) {
     InnerStatic is = new InnerStatic();
     is.foo();
     InnerStatic.bar();
  }
  class Inner{}
  static class InnerStatic{
    void foo(){
      //Object o = InnerEntry.this;
    };
    static void bar(){
      Object o = InnerEntry.class;
    };
  }
}
