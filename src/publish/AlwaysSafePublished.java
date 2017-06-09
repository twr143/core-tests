package publish;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilya on 21.05.2017.
 */
public class AlwaysSafePublished {
    static volatile long id;
    volatile long iid = ++id;

    public /*final or not no matter*/ Map map = new HashMap();

    public long test1;
    public long test2;
    public long test3;
    public long test4;
    public long test5;
    public long test6;
    public long test7;
    public long test8;
    public long test9;
    public long test10;


    public AlwaysSafePublished() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put("1", 1);
        test1 = 42;
        test2 = test1 + 42;
        test3 = test2 + test1;
        test4 = test2 * test1 - 42;
        test5 = test4 * test1;
        test6 = test2 * 4;
        test7 = 42;
        test8 = 42;
        test9 = test2 + test7;
        test10 = 42;
    }

    public int number() {
        return (Integer) map.get("1");
    }

    public void checkCorrect() {
        if (number() != 1) {
            throw new IllegalArgumentException("Unsafely published in a map");
        }
        if (test1 != 42
                || test2 != test1 + 42
                || test3 != test2 + test1
                || test4 != test2 * test1 - 42
                || test5 != test4 * test1
                || test6 != test2 * 4
                || test7 != 42
                || test8 != 42
                || test9 != test2 + test7
                || test10 != 42) {
            throw new IllegalArgumentException("Unsafely published longs");
        }
        if (hashCode() == 0) {
            throw new IllegalArgumentException("Unsafely published longs");
        }
        System.out.println("Correct: " + iid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlwaysSafePublished that = (AlwaysSafePublished) o;

        if (iid != that.iid) return false;
        if (test1 != that.test1) return false;
        if (test10 != that.test10) return false;
        if (test2 != that.test2) return false;
        if (test3 != that.test3) return false;
        if (test4 != that.test4) return false;
        if (test5 != that.test5) return false;
        if (test6 != that.test6) return false;
        if (test7 != that.test7) return false;
        if (test8 != that.test8) return false;
        if (test9 != that.test9) return false;
        if (map != null ? !map.equals(that.map) : that.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (iid ^ (iid >>> 32));
        result = 31 * result + (map != null ? map.hashCode() : 0);
        result = 31 * result + (int) (test1 ^ (test1 >>> 32));
        result = 31 * result + (int) (test2 ^ (test2 >>> 32));
        result = 31 * result + (int) (test3 ^ (test3 >>> 32));
        result = 31 * result + (int) (test4 ^ (test4 >>> 32));
        result = 31 * result + (int) (test5 ^ (test5 >>> 32));
        result = 31 * result + (int) (test6 ^ (test6 >>> 32));
        result = 31 * result + (int) (test7 ^ (test7 >>> 32));
        result = 31 * result + (int) (test8 ^ (test8 >>> 32));
        result = 31 * result + (int) (test9 ^ (test9 >>> 32));
        result = 31 * result + (int) (test10 ^ (test10 >>> 32));
        return result;
    }
}
