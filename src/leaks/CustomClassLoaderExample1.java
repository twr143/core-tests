package leaks;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.lang.Class.forName;

/**
 * Created by ilya on 13.01.2017.
 *
 * Here we manually call ClassLoader.defineClass() in our custom child CL and try to cast / check equality in couple of ways.
 */
public class CustomClassLoaderExample1 {

    public static void main(String[] args) throws Exception {
        Object loader1Insance = load();
        Object loader2Insance = load();
        System.out.println("Two instances of our custom CL, loaded same class and are its two instances equal? " + loader1Insance.equals(loader2Insance));

        System.out.println("In case of default CL two instances are equal, right? " + new IAmLoadedByCustomCL().equals(new IAmLoadedByCustomCL()));

        System.out.println("Are parent and child by same default CL equal? " + new IAmLoadedByCustomCL().equals(new IAmLoadedByCustomCLChild()));

        System.out.println("Are two children loaded by same default CL equal? " + new IAmLoadedByCustomCLChild().equals(new IAmLoadedByCustomCLChild()));
    }

    static Object load() throws Exception {
        ClassLoader childClassLoader = new CustomLoadingCL();
        Class<?> childClass = forName(IAmLoadedByCustomCL.class.getName(), true, childClassLoader);

        try {
            IAmLoadedByCustomCL castToMePlease = (IAmLoadedByCustomCL) childClass.newInstance();
            System.out.println(castToMePlease);
        } catch (ClassCastException cce) {
            System.out.println("Loaded by our CL IAmLoadedByCustomCL class instance could not be cast to standard JVM CL loaded IAmLoadedByCustomCL class object. " +
                    cce.getMessage());
        }

        return childClass.newInstance();
    }

}

class IAmLoadedByCustomCLChild extends IAmLoadedByCustomCL {
}

final class CustomLoadingCL extends ClassLoader {
    CustomLoadingCL() {
        // Selecting Parent CL in constructor
        super(CustomClassLoaderExample1.class.getClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // All classes except our test one we load normally, using hierarchy of JVM CL-s
        if (!IAmLoadedByCustomCL.class.getName().equals(name)) {
            return super.loadClass(name, resolve);
        }

        try {
            byte[] classBytes = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("leaks\\IAmLoadedByCustomCL.class"));
            Class<?> c = defineClass(name, classBytes, 0, classBytes.length);
            if (resolve) {
                resolveClass(c);
            }
            return c;
        } catch (IOException ex) {
            throw new ClassNotFoundException("Could not load " + name, ex);
        }
    }
}