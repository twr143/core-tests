package reftypes;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilya on 12.01.2017.
 */
public class refTypesE1 {

    static List<BigObject> strongList; // сильные ссылки
    static List<Reference<BigObject>> softList; // мягкие ссылки
    static List<Reference<BigObject>> weakList; // слабые ссылки
    static List<Reference<BigObject>> phantomList; // фантомные ссылки
    static ReferenceQueue<BigObject> queue; // очередь
    static List<String> loadMemoryList; // здесь будут храниться ссылки на с

    public static void main(String[] args) {
//    testSoftRefences();
        testPhantomReferences();
    }

    private static void printLists() {
        System.out.println("Strong references: ");
        for (BigObject bo : strongList) System.out.print(bo + " ");
        System.out.println();
        System.out.println("SoftReferences: ");
        printList(softList);
        System.out.println("WeakReferences: ");
        printList(weakList);
        System.out.println("PhantomReferences: ");
        printList(phantomList);
    }

    private static void printList(List<Reference<BigObject>> pList) {
        for (Reference<BigObject> ref : pList)
            System.out.print(ref.get() + " ");

    }

    private static void init() {
        strongList = new ArrayList<BigObject>();
        softList = new ArrayList<Reference<BigObject>>();
        weakList = new ArrayList<Reference<BigObject>>();
        phantomList = new ArrayList<Reference<BigObject>>();
        loadMemoryList = new ArrayList<String>();
        queue = new ReferenceQueue<BigObject>();
        for (int i = 0; i < 3; i++) {
            strongList.add(new BigObject(i));
            softList.add(new SoftReference<BigObject>(new BigObject(i)));
            weakList.add(new WeakReference<BigObject>(new BigObject(i)));
            phantomList.add(new PhantomReference<BigObject>(new BigObject(i), queue));
        }
        printLists();
    }

    private static void loadMemory() {
        for (int i = 0; i < 1200000; i++) {
            loadMemoryList.add(i + "");
        }
    }

    public static void testPhantomReferences() {
        init(); // инициализация
        System.gc(); // вызов сборщика мусора
        System.out.println("garbage collector invoked");
        printLists(); // вывод
    }

    public static void testSoftRefences() {
        init();
        System.gc();
        System.out.println("garbage collector invoked");
        printLists();
        System.out.println("memory usage increased");
        loadMemory(); // загрузка памяти
        System.out.println("loadMemoryList.size() = " + loadMemoryList.size());
        System.gc();
        System.out.println("garbage collector invoked");
        printLists();
    }
}
