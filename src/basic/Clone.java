package basic;

/**
 * Created by AIKuznetsov on 25.05.2017.
 *
 *  To return correct class, all hierarchy of classes should correctly override clone(), but it can be protected.
 *  Than Object.clone() would return correct class "shallow copy", but this is still poor design as it is not standard polymorphism.
 *  Also look at deep copying example
 *
 *  Cloneable interface do not feature method, which it should
 *  It uses Object.clone() - activated extralinguistic mechanism to create object of a correct class
 *  and pre fills it with a shallow copy of fields.
 *  Every class in a hierarchy up to an Object should implement correct clone() method and should
 *  call super.clone() in it.
 *  Any deep copy should be manually and rigorously done on each level of hierarchy, after super.clone() of this level
 *  Method still needs to be synchronized.
 *  Overridden (not final) method call in clone can lead to incorrect object initialization.
 *  Clone support could lead to a need to remove final modifiers off of some fields, which is undesirable.
 *
 *  Instead of Cloneable/clone, class should feature a copy constructor MyType(MyType src), or a more general one MyType(IType src)
 *  which is called conversion constructor.
 *
 *  Or conversion factory
 *  public static MyType newInstance(IType src);
 */
public class Clone {

    public static void main(String[] args) throws CloneNotSupportedException {
        Foo clone = new Foo(42, 4242, new Boo(424242)).clone();
        System.out.println(clone);
    }
}

class Boo implements Cloneable {

    private final int booValue;

    public Boo(int booValue) {
        this.booValue = booValue;
    }

    protected Boo clone() throws CloneNotSupportedException {
        return (Boo) super.clone();
    }

    @Override
    public String toString() {
        return "Boo{" +
                "booValue=" + booValue +
                '}';
    }
}

class Foo extends Boo {

    private final int fooValue;

    private Boo boo;

    public Foo(int booValue, int fooValue, Boo boo) {
        super(booValue);
        this.fooValue = fooValue;
        this.boo = boo;
    }

    public Foo clone() throws CloneNotSupportedException {
        Foo clone = (Foo) super.clone();
        // Deep copy
        clone.boo = boo.clone();

        return clone;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "fooValue=" + fooValue +
                "boo=" + boo+
                "} " + super.toString();
    }
}