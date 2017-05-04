package singleton;

/**
 * Created by ilya on 26.12.2016.
 */
public class Data
{
  static volatile int attemptNum=0;
  String question;
      int answer;
      int maxAllowedValue;

      public Data() {
          attemptNum++;
          this.answer = 42;
          this.maxAllowedValue = 9000;
      }

  @Override
  public String toString()
  {
    return "D{" +
            "que='" + question + '\'' +
            ", a=" + answer +
            ", attmpt=" + attemptNum+
            '}';
  }
}
