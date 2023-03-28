import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.io.path.forEachLine

const val framerate30 = 30
const val second = 1000
class Play {
    fun play(){
        val countTime = second/framerate30
        val files = Files.list(Paths.get("ASCII")).collect(Collectors.toList())
        files.forEach{file ->
            file.forEachLine { println(it) }
            Thread.sleep(countTime.toLong())
            Runtime.getRuntime().exec("cls")
        }
    }
}