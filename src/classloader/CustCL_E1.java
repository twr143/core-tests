package classloader;

import leaks.ClassLoaderLeakExample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Class.forName;

/**
 * Created by ilya on 13.01.2017.
 */
public class CustCL_E1
{
  static volatile boolean running = true;

  public static void main(String[] args) throws Exception {
    Object loader1Insance = load();
    Object loader2Insance = load();

    System.out.println("diff classloaders:"+loader1Insance.equals(loader2Insance));

    LoadedInChildClassLoader l1 = new LoadedInChildClassLoader();
    LoadedInChildClassLoader l2 = new LoadedInChildClassLoader();
    System.out.println("same class:"+l1.equals(l2));

    l1 = new LoadedInChildClassLoader();
    l2 = new LoadedInChildClassLoaderChild();
    System.out.println("parent and child:"+l1.equals(l2));

    l1 = new LoadedInChildClassLoaderChild();
    l2 = new LoadedInChildClassLoaderChild();
    System.out.println("both childs:"+l1.equals(l2));

  }


  static final class ChildOnlyClassLoader extends ClassLoader {
    ChildOnlyClassLoader() {
      super(CustCL_E1.class.getClassLoader());
    }

    @Override protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException {
      if (!CustCL_E1.LoadedInChildClassLoader.class.getName().equals(name)) {
        return super.loadClass(name, resolve);
      }
      try {
        Path path = Paths.get("c:\\dev\\play\\demo1\\core-tests\\out\\production\\core-tests\\classloader\\","CustCL_E1$LoadedInChildClassLoader"
            + ".class");
        byte[] classBytes = Files.readAllBytes(path);
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

  static Object load() throws Exception {
    ClassLoader childClassLoader = new CustCL_E1.ChildOnlyClassLoader();
    Class<?> childClass = forName(
            CustCL_E1.LoadedInChildClassLoader.class.getName(), true, childClassLoader);
//    return ((classloader.CustCL_E1.LoadedInChildClassLoader) childClass.newInstance());
//     ClasscastException

       return  childClass.newInstance();
  }


  static CustCL_E1.LoadedInChildClassLoader load1() throws Exception {
    ClassLoader childClassLoader = new CustCL_E1.ChildOnlyClassLoader();
    Class<?> childClass = Class.forName(
            CustCL_E1.LoadedInChildClassLoader.class.getName(), true, childClassLoader);
//    return ((classloader.CustCL_E1.LoadedInChildClassLoader) childClass.newInstance());
//     ClasscastException
    return CustCL_E1.LoadedInChildClassLoader.class.cast(childClass.newInstance());//     ClasscastException anyway

  }

  public static class LoadedInChildClassLoader {

    public LoadedInChildClassLoader() {
    }
    private String test = "test";
    public String getTest()
    {
      return test;
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

      LoadedInChildClassLoader loader = (LoadedInChildClassLoader) o;

      return test != null ? test.equals(loader.test) : loader.test == null;
    }
    @Override
    public int hashCode()
    {
      return test != null ? test.hashCode() : 0;
    }
  }
  public static class LoadedInChildClassLoaderChild extends LoadedInChildClassLoader
  {
  }


}
