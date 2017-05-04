package kt1

/**
 * Created by ilya on 05.03.2017.
 */
class KtTest1 {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            if (args.size == 0) {
                println("Please provide a name as a command-line argument")
                return
            }
            println("Hello, ${args[0]}!")
            KtTest1().e1()
        }
    }
    fun e1(){
        val l=listOf(1,2,3,4,5)
        println(l)
    }
}