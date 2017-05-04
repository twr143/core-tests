package reftypes;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by ilya on 12.01.2017.
 */
public class weakedHashMapE1
{
  static Data someDataObject=null;
  public static void main(String[] args) throws Exception {
         // -- Fill a weak hash map with one entry
         WeakHashMap<Data, String> map = new WeakHashMap<Data, String>();
        someDataObject = new Data("foo");
         map.put(someDataObject, someDataObject.value);
         System.out.println("map contains someDataObject ? " + map.containsKey(someDataObject));

         // -- now make someDataObject elligible for garbage collection...
         someDataObject = null;

         for (int i = 0; i < 500000; i++) {
             if (map.size() != 0) {
               if (i % 10000 ==0)
                 System.out.println("At iteration " + i + " the map still holds the reference on someDataObject");
             } else {
                 System.out.println("somDataObject has finally been garbage collected at iteration " + i + ", hence the map is now empty");
                 break;
             }
            if (i % 10 ==0){
             Thread.sleep(1);
             System.gc();
             }

         }
     }

     static class Data {
         String value;
         Data(String value) {
             this.value = value;
         }
     }
}
