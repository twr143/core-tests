package equals;

/**
 * Created by ilya on 19.05.2017.
 */
public class EqualsEntry
{
  public static void main(String[] args)
  {
    A a1 = new A(1,1);
    A a2 = new A(1,1);

    System.out.println(a1.equals(a2));
  }
}
