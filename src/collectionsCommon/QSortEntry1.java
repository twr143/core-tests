package collectionsCommon;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by ilya on 05.02.2017.
 */
@State(Scope.Benchmark)
public class QSortEntry1
{
  static int[] a=null;

  public static void main(String[] args) throws Exception
  {
//    print(a);
//    qSort(a,true);
//    print(a);
    Options opt = new OptionsBuilder()
            .include(QSortEntry1.class.getSimpleName())
            .forks(1)
            .warmupBatchSize(1).measurementBatchSize(1)
            .build();
    new Runner(opt).run();
  }



    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testSortSingleP() {
    qSort(a,false);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testSortDoubleP() {
//      print(a);
      qSort(a,true);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testSDKArraysSort() {
//        print(a);
      Arrays.sort(a);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testQSortLineraAlex() {
  //        print(a);
      quickSortLinearAlex(a);
    }

  @Setup(Level.Invocation)
  public static void setUp()
  {
    a = produceArray(759,1000);
//    System.out.println("set up");
  }

  private static int[] produceArray(int nWaves, int n){

      int[] a = new int[n];
      for (int i = 0; i < n; i++)
      {
        a[i] = (int) Math.round(Math.abs(i * Math.sin(Math.PI * ((double) (i) * nWaves / n))));
      }
      return a;
  }

  private static void qSort(int[] a, boolean isDual){
//    System.out.println("called qsort");
    List<QSortTask> queue = new ArrayList<>();
    queue.add(new QSortTask(0,a.length));
    for (int i=0;;i++){
      if (i<queue.size()){
        if (isDual)
          qSortDualPivotAddTask(a, queue.get(i).left, queue.get(i).right, queue);
        else
          qSortAddTask(a, queue.get(i).left, queue.get(i).right, queue);
      }
      else break;
    }
//    print(a);
  }

  private static void qSortDualPivotAddTask(int[] a, int left, int right, List<QSortTask> queue){

    if (right-left>3){
      if (a[left]>a[right-1])
        swap(a, left,right-1);
      int p=a[left],q=a[right-1];
      int l=left+1,k=l,g=right-2;

      while (k<=g){
        if (a[k]<p)
          swap(a,k,l++);
        else{
          if(a[k]>=q){
            while(a[g]>q&&g>k)
              g--;
            swap(a,k,g--);
            if (a[k]<p)
              swap(a,k,l++);
          }
        }
        k++;
      }
      l--;
      g++;
      swap(a,left,l);
      swap(a,g,right-1);

//      System.out.println(" parts: ");
//      print(a,left,l);
//      print(a,l+1,g);
//      print(a,g+1,right);
//      System.out.println(" done: ");



      if (l-left>1)
            queue.add(new QSortTask(left,l));
      if (g -l > 1)
            queue.add(new QSortTask(l+1,g));
      if (right -g > 1)
            queue.add(new QSortTask(g+1,right));
    }
    else sortSmall(a,left,right);

  }


  private static void qSortAddTask(int[] a, int l, int r, List<QSortTask> queue){

    if (r-l>3){
      int pivot = a[l];
      int pivotIndex = l + 1;
      for (int i = l + 1; i < r; i++)
      {
        if (a[i] < pivot)
        {
          swap(a, pivotIndex, i);
          pivotIndex++;
        }
      }
      swap(a, l, pivotIndex - 1);

      if (pivotIndex - 2 > l)
            queue.add(new QSortTask(l,pivotIndex-1));
      if (pivotIndex  < r)
            queue.add(new QSortTask(pivotIndex,r));
    }
    else sortSmall(a,l,r);

  }
  private static void sortSmall(int[] a, int l, int r){
      if (r-l==3){
          if (a[l]>a[l+1])
            swap(a,l,l+1);
          if (a[l+1]>a[l+2])
            swap(a,l+1,l+2);
          if (a[l]>a[l+1])
            swap(a,l,l+1);
        }
        else if (r-l==2)
          if (a[l]>a[l+1])
            swap(a,l,l+1);
  }

  private static void swap(int[] a, int l, int r){
    int temp=a[l];a[l]=a[r];a[r]=temp;
  }

  private static void print(int[] a){
//    System.out.println("printing ...");
    for (int i=0;i<a.length;i++){
      System.out.print(a[i]+" ");
    }
    System.out.println();
  }

  private static void print(int[] a, int l,int r){
    for (int i=l;i<r;i++){
      System.out.print(a[i]+" ");
    }
    System.out.println();
  }

  static class QSortTask{
    int left;
    int right;
    public QSortTask(int left, int right)
    {
      this.left = left;
      this.right = right;
    }
  }

  private static void quickSortLinearAlex(int[] elements)
  {
    class QSortTask
    {
      final int from;
      // Inclusive
      final int to;

      public QSortTask(int from, int to)
      {
        this.from = from;
        this.to = to;
      }

      boolean needsProcessing()
      {
        return from < to;
      }
    }
    Deque<QSortTask> qSortTasks = new ArrayDeque<>();
    qSortTasks.add(new QSortTask(0, elements.length - 1));

    while (!qSortTasks.isEmpty())
    {
      QSortTask task = qSortTasks.pollFirst();

      if (task.needsProcessing())
      {
        int pivot = elements[task.to];

        int g = task.from;
        for (int j = task.from; j < task.to; ++j)
        {
          int element = elements[j];
          if (element < pivot)
          {
            swap(elements, g++, j);
          }
        }
        swap(elements, g, task.to);

        qSortTasks.push(new QSortTask(task.from, g - 1));
        qSortTasks.push(new QSortTask(g + 1, task.to));
      }
    }
  }
}
