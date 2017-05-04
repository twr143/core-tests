package account;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by ilya on 15.12.2016.
 */
public class Account implements Comparable<Account>
{
  final int id;
  float amount;
  final String currency;
  int wDrawAttempts =0;
  volatile int wDrawAttemptsVolatile =0;
  StampedLock stampedLock = new StampedLock();

  public Account(Integer id, Double amount, String currency)
  {
    this.id = id;
    this.amount = amount.floatValue();
    this.currency = currency;
  }
  public void deposit(float amount){
    this.amount+=amount;
  }
  public boolean withdraw(float amount){
    wDrawAttempts++;
    wDrawAttemptsVolatile++;
    float diff = this.amount-amount;
    if (diff>=0){
      this.amount -= amount;
    }
    return diff>=0;
  }
  public float getAmount(){
    return amount;
  }
  public int getId()
  {
    return id;
  }
  @Override
  public int compareTo(Account o)
  {
    return id-o.getId();
  }
  public String getCurrency()
  {
    return currency;
  }
  @Override
  public String toString()
  {
    return "Acc[" +
            "#=" + id +
            ", amt=" + new Double(Math.round(amount * 100.0) / 100.0).floatValue() +
            ", cur='" + currency + '\'' +
            ", w Att=" + wDrawAttempts +
            ", w AttVol=" + wDrawAttemptsVolatile +
            ']';
  }
}
