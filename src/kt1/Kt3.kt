package kt1

import java.util.*
import java.util.stream.Collectors

/**
 * Created by ilya on 05.03.2017.
 */
fun main(ars:Array<String>){
    println("whatsup")
}

/*val WORD_DELIMITERS_REGEX = "(\\s*[,.!?;)]*\\s+[{}\\[\\](]*)|[;.]"
@Throws(Exception::class)
fun readStream(lines:List<String>) {
    val fileLines = lines//Files.lines(Paths.get(fileName), Charset.forName("UTF-8")); // for line: Files.readAllLines
    val notEmptyLines = fileLines.filter({ s -> !s.isEmpty() }) // if (!line.isEmpty()) {
    val allTokens = notEmptyLines.flatMap({ s: String -> (WORD_DELIMITERS_REGEX.split(s)) })// for token: line.split
    val notEmptyTokens = allTokens.filter({ s -> !s.isEmpty() }) // if !token.isEmpty
    val lowercasedTokens = notEmptyTokens.map({ s -> s.toLowerCase() }) // token = token.toLowerCase()
    val dictionary: HashMap<String,Integer> = HashMap();
     lowercasedTokens.forEach { k->dictionary.merge(k,1,{ (v1, v2) -> v1 + v2 })} // dictionary = new HashMap<>()
    val sorted = dictionary.entries.stream().sorted({ u, v -> v.value.compareTo(u.value) }).collect(Collectors.toList<Entry<String, Int>>())
    //    System.out.println(sorted);

} */
