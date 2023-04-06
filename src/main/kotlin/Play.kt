import kotlinx.coroutines.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import kotlin.io.path.forEachLine
import kotlin.io.path.name

const val framerate: Long = 30
const val second: Long = 1000
val ui = ConsoleUI()

class Play {
    suspend fun play() {
        ui.cls()
        val audio = Audio()
        var countTime = second / framerate
        val files = Files.list(Paths.get("ASCII")).collect(Collectors.toList())
        var sorted = arrayListOf<Path?>()
        var iter = 0
        while (sorted.size < files.size) {
            var file = files.find { it.fileName.name.split(".")[0].toInt() == iter }
            sorted.add(iter, file)
            iter++
        }
        var frameList = LinkedList<String>()
        var writeCounter = 0
        sorted.forEach{file ->
            var out = ""
            file!!.forEachLine { out += "$it\n" }
            frameList.add(element = out, index = writeCounter)
        }
        audio.playAudio()
        var frameCounter = 0
        frameList.reversed().forEach { file ->
            CoroutineScope(Dispatchers.IO).launch {
                println(file)
                frameCounter++
                if (frameCounter%2 == 0) countTime-- else countTime = second/framerate
            }
            delay(countTime)

        }
    }

    suspend fun asyncReadFrame(file: File): String = withContext(Dispatchers.Default) {
        var out = ""
        file.forEachLine { out += "$it\n" }
        return@withContext out
    }

    fun readFrame(file: File): String {
        var out = ""
        file.forEachLine { out += "$it\n" }
        return out
    }

}