package Producer2Consumer;

/**
 * Created by ilya on 16.10.2016.
 */
public class QueueBean
{
  private int value;
  public boolean redByFirst=false;
  public boolean redBySecond=false;
  public QueueBean(int value)
  {
    this.value = value;
  }
  public int getValue()
  {
    return value;
  }
}
