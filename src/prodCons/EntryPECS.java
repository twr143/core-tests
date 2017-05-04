package prodCons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilya on 25.03.2017.
 */
public class EntryPECS
{

  public static void testContraVariance(List<? super Shape> list) {
              list.add(new Shape());//compiles
              list.add(new Circle());//compiles
              list.add(new Square());//compiles
              list.add(new Rectangle());//compiles
          }
  public static Shape testCoVariance(List<? extends Shape> list) {
              Shape shape= list.get(0);
              return shape;
          }
  public static void main(String[] args)
  {
     List<Shape> shapes = new ArrayList<>();
     testContraVariance(shapes);
      System.out.println(testCoVariance(shapes));

  }

}
class Shape {
    void draw() {

    }
  @Override
  public String toString()
  {
    return "Shape";
  }
}

class Circle extends Shape {
    void draw() {
    }
  @Override
  public String toString()
  {
    return "Circle";
  }
}

class Square extends Shape {
    void draw() {
    }
  @Override
  public String toString()
  {
    return "Square";
  }
}

class Rectangle extends Shape {
    void draw() {
    }
  @Override
  public String toString()
  {
    return "Rectangle";
  }

}


