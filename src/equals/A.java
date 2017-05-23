package equals;

/**
 * Created by ilya on 19.05.2017.
 */
public class A
{
  private int p1;
  private int p2;
  public A(int p1, int p2)
  {
    this.p1 = p1;
    this.p2 = p2;
  }
  public int getP1()
  {
    return p1;
  }
  public void setP1(int p1)
  {
    this.p1 = p1;
  }
  public int getP2()
  {
    return p2;
  }
  public void setP2(int p2)
  {
    this.p2 = p2;
  }
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    A a = (A) o;

    if (p1 != a.p1)
    {
      return false;
    }
    return p2 == a.p2;
  }
  @Override
  public int hashCode()
  {
    int result = p1;
    result = 31 * result + p2;
    return result;
  }
}
