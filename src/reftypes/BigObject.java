package reftypes;

/**
 * Created by ilya on 12.01.2017.
 */
public class BigObject
{
  private int value;
   public BigObject(Integer pValue) {
     value = pValue;
   }
   @Override
   protected void finalize() throws Throwable {
     System.out.println("BigObject ("+value+") finalize()");
   }
   @Override
   public String toString() { return value+"";  }
}
