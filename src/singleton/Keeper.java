package singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ilya on 26.12.2016.
 */
public class Keeper
{
  static volatile Data dataLock = null;
  static Data getDataByLock(){
    if (dataLock ==null)
      synchronized (Keeper.class)   {
        if (dataLock ==null)
          dataLock = createData();
    }
    return dataLock;
  }

  static Data createData(){
    Data d = new Data();
    d.answer=32;
    return d;
  }

  static class DataHolder{
    public static /*final*/ Data instance = new Data();
  }

  Data getDataBySH(){
    return DataHolder.instance;
  }
//  AtomicReference<Data> dataAtomic = new AtomicReference<>(new Data());


//  Data getDataByAR(){
//    dataAtomic.compareAndSet(null,new Data());
//    return dataAtomic.get();
//  }

}
