package concurr;

/**
 * Created by ilya on 25.03.2017.
 */
public class EntryIntrinsic
{
  public static void main(String[] args) throws Exception
  {
         test1();
  }
  static synchronized void test1() throws Exception
  {
    EntryIntrinsic.class.getClass().wait();
  }
}
