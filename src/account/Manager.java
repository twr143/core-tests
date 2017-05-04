package account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * Created by ilya on 15.12.2016.
 */
public class Manager
{
  public static boolean transfer(Account from, Account to, float amount){
    boolean success = false;
    long wlockFrom,wlockTo;
    int fromOrTo=from.compareTo(to);
    if (fromOrTo>0){
      wlockFrom = from.stampedLock.writeLock();
      wlockTo = to.stampedLock.writeLock();
    }else{
      wlockTo = to.stampedLock.writeLock();
      wlockFrom = from.stampedLock.writeLock();
    }
    try{
      if(from.withdraw(amount)){
        to.deposit(amount);
        success=true;
      }
    } finally
    {
      if (fromOrTo>0){
        to.stampedLock.unlockWrite(wlockTo);
        from.stampedLock.unlockWrite(wlockFrom);
      } else {
        from.stampedLock.unlockWrite(wlockFrom);
        to.stampedLock.unlockWrite(wlockTo);
      }
    }
    return success;
  }
  public static double getTotal(Account[] accounts){
    long[] stamps = new long [accounts.length];
//    IntStream.range(0,accounts.length).forEach(i->{
//      stamps[i]=accounts[i].stampedLock.readLock();
//    });
    double sum = Arrays.stream(accounts).mapToDouble(a->a.getAmount()).sum();
//    IntStream.range(0,accounts.length).forEach(i->{
//      accounts[accounts.length-i-1].stampedLock.unlockRead(stamps[accounts.length-i-1]);
//    });
    return sum;
  }
}
