package classloader;

import static java.lang.Class.forName;

/**
 * Created by ilya on 05.04.2017.
 */

public class CustCL_E2 {

    public static void main(String[] args) throws Exception {
        Long l1 = (Long) load();
        System.out.println(l1);
        Long l2 = (Long) load();
        System.out.println("equals?:" + l1.getClass().equals(l2.getClass()));
    }

    static final class ChildOnlyClassLoader extends ClassLoader {
        ChildOnlyClassLoader() {
            super(CustCL_E2.class.getClassLoader());
        }
    }

    static Object load() throws Exception {
        ClassLoader childClassLoader = new CustCL_E2.ChildOnlyClassLoader();
        Class<?> childClass = forName(
                Long.class.getName(), true, childClassLoader);
        //    return ((classloader.CustCL_E1.LoadedInChildClassLoader) childClass.newInstance());
        //     ClasscastException

        return childClass.getDeclaredConstructor(long.class).newInstance(0L);
    }

}
