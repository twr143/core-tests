package leaks;

/**
 * Created by AIKuznetsov on 26.05.2017.
 */
public class IAmLoadedByCustomCL {

    private String test = "test";

    public IAmLoadedByCustomCL() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IAmLoadedByCustomCL loader = (IAmLoadedByCustomCL) o;

        return test != null ? test.equals(loader.test) : loader.test == null;
    }

    @Override
    public int hashCode() {
        return test != null ? test.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "IAmLoadedByCustomCL{" +
                "test='" + test + '\'' +
                '}';
    }
}