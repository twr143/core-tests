package file;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static file.MapReduceReader.parseAndCountEntriesJ;

/**
 * Created by ilya on 05.01.2017.
 */
@State(Scope.Benchmark)
public class ReaderE1
{
  // read file from disk, parse it to words, find the count of each word appearing and print them starting from the most common
  public static final String WORD_DELIMITERS_REGEX = "(\\s*[,.!?;)]*\\s+[{}\\[\\](]*)|[;.]";
  private static final String fileName = "text.txt";
  private static final int nCores = Runtime.getRuntime().availableProcessors();
  private static ExecutorService es = null;
  private static ExecutorCompletionService<Map<String,Integer>> executorCompletionService = null;
  public static List<String> lines =null;
  public static Pattern pattern = null;
  public static void main(String[] args) throws Exception
  {
   /* Options opt = new OptionsBuilder()
                    .include(ReaderE1.class.getSimpleName())
                    .forks(1)
                    .build();

            new Runner(opt).run();
     */
    //correctness

    setUp();
//    readStream();
    readFTP();
//    readColl();
//    tearDown();
//    readFJ();
    tearDown();
  }
  // parallel read through java 8 streams
//  @Benchmark
  @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public static void readStream() throws Exception{
    Stream<String> fileLines = lines.stream();//Files.lines(Paths.get(fileName), Charset.forName("UTF-8")); // for line: Files.readAllLines
      Stream<String> notEmptyLines = fileLines.filter(s -> !s.isEmpty()); // if (!line.isEmpty()) {
      Stream<String> allTokens = notEmptyLines.parallel().flatMap((String s) -> Arrays.stream(pattern.split(s)));// for token: line.split
      Stream<String> notEmptyTokens = allTokens.filter(s -> !s.isEmpty()); // if !token.isEmpty
      Stream<String> lowercasedTokens = notEmptyTokens.map(String::toLowerCase); // token = token.toLowerCase()
      Map<String, Integer> dictionary = lowercasedTokens.collect( // dictionary = new HashMap<>()
              Collectors.toMap(k -> (String) k, k -> 1, (Integer v1, Integer v2) -> v1 + v2) // dictionary.put
      );
      List<Map.Entry<String,Integer>> sorted =dictionary.entrySet().stream().sorted((u, v)->v.getValue().compareTo(u.getValue())).collect(Collectors.toList());
//    System.out.println(sorted);

  }
  //sequential read through java 7 Collections style
//  @Benchmark
  @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public static void readColl() throws Exception
  {
    Map<String, Integer> dictionary = new HashMap<>();
    for (String line : lines)
    {
      if (!line.isEmpty())
      {
        for (String token : line.split(WORD_DELIMITERS_REGEX))
        {
          if (!token.isEmpty())
          {
            token = token.toLowerCase();
              dictionary.put(token, dictionary.getOrDefault(token, 0)+1);

          }
        }
      }
    }
    Set<Map.Entry<String,Integer>> pairs = new TreeSet<>(new Comparator<Map.Entry<String, Integer>>()
    {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
      {
        int val = -o1.getValue().compareTo(o2.getValue());
        return val!=0?val:o1.getKey().compareTo(o2.getKey());
      }
    });
    for (Map.Entry<String,Integer> e:dictionary.entrySet()){
      pairs.add(new AbstractMap.SimpleEntry<String, Integer>(e.getKey(),e.getValue()));
    }
//    System.out.println(pairs);

  }
//  @Benchmark
  @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public static void read() throws Exception{
    Files.readAllLines(Paths.get(fileName));
  }


  //setUp FixedThreadPool
  @Setup
  public static void setUp() throws IOException{
    System.out.println("set up");
    if (pattern==null)
      pattern = Pattern.compile(WORD_DELIMITERS_REGEX);
    if (es==null)
      es = Executors.newFixedThreadPool(nCores);
    if (executorCompletionService==null)
      executorCompletionService = new ExecutorCompletionService<>(es);
    if (lines==null)
      lines=Files.readAllLines(Paths.get(fileName));

  }

  //shutDown FixedThreadPool
  @TearDown
  public static void tearDown(){
    es.shutdown();
  }

    //read through FixedThreadPool (FTP)
//    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void readFTP() throws InterruptedException,Exception{
      int lSize = lines.size();
      int poolSize = lSize <10?2:nCores;

      List<Future<Map<String,Integer>>> futures = new ArrayList<>();
      IntStream.range(0,poolSize).forEach(i->{
        Callable<Map<String,Integer>> task = new Callable<Map<String, Integer>>()
        {
          @Override
          public Map<String, Integer> call() throws Exception
          {

            int skip = i*lSize/poolSize;
            int limit = (i+1)*lSize/poolSize - i*lSize/poolSize;
            Map<String,Integer> appear =lines.stream().skip((skip)).limit(limit).filter(s -> !s.isEmpty()).
                    flatMap((String s) -> Arrays.stream(pattern.split(s))).filter(s -> !s.isEmpty()).
                            map(String::toLowerCase).collect(Collectors.toMap(k -> (String) k, k -> 1, (Integer v1, Integer v2) -> v1 + v2));


            return appear;
          }
        };
        futures.add(executorCompletionService.submit(task));
      });

      Map<String,Integer> futuresMerge = new HashMap<>();
      IntStream.range(0,poolSize).forEach(i->
              {
                Map<String, Integer> nodeMap;
                try
                {
                  nodeMap = executorCompletionService.take().get();
                  nodeMap.keySet().forEach(k->futuresMerge.put(k,futuresMerge.getOrDefault(k,0)+nodeMap.get(k)));
                }catch (Exception e){e.printStackTrace();}

              });

      List<Map.Entry<String,Integer>> sorted =futuresMerge.entrySet().stream().sorted((u, v)->v.getValue().compareTo(u.getValue())).collect(Collectors.toList());
//      System.out.println(sorted);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void readFJ(){
      Map<String,Integer> dictionary= parseAndCountEntriesJ(ReaderE1.lines,2000);
      List<Map.Entry<String,Integer>> sorted =dictionary.entrySet().stream().sorted((u, v)->v.getValue().compareTo(u.getValue())).collect(Collectors.toList());
//      System.out.println(sorted);
    }

}
