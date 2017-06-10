package publish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilya on 21.05.2017.
 */
public class AlwaysSafePublished
{
  private /*final or not no matter*/ Map map = new HashMap();

   public AlwaysSafePublished()
   {

     try
     {
       Thread.sleep(1);
     }
     catch (InterruptedException e)
     {
       e.printStackTrace();
     }
     map.put("1", 1);
   }

   public int number(){
    return (Integer) map.get("1");
   }
}
