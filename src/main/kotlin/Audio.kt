import it.sauronsoftware.jave.AudioAttributes
import it.sauronsoftware.jave.EncodingAttributes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine
import javax.swing.JFileChooser

class Audio {

    fun separateAudio(fileChoosed: Int, fileChooser: JFileChooser) {
        if (fileChoosed == JFileChooser.APPROVE_OPTION) {
            val inputVideo = fileChooser.selectedFile
            val directory = File("audio")
            if (!directory.exists()) {
                directory.mkdir()
            }

            val outputAudio = File("audio\\output_audio.wav")

            // author: https://github.com/alt440
            val encoder = it.sauronsoftware.jave.Encoder()
            val specifications = EncodingAttributes()
            specifications.setFormat("wav")
            val audioAttributes = AudioAttributes()
            audioAttributes.setVolume(256)
            audioAttributes.setCodec("pcm_s16le")
            specifications.setAudioAttributes(audioAttributes)
            encoder.encode(inputVideo, outputAudio, specifications)
        }
    }

/*    suspend fun playAsWindowsPLayer() = CoroutineScope(Dispatchers.IO).launch {
        ProcessBuilder("cmd", "/c", "audio\\output_audio.wav").inheritIO().start().waitFor()
    }*/

    suspend fun playAudio() = CoroutineScope(Dispatchers.IO).launch{
        try {
            val audioInputStream = AudioSystem.getAudioInputStream(File("audio\\output_audio.wav"))
            val audioFormat = audioInputStream.format
            val info = DataLine.Info(SourceDataLine::class.java, audioFormat)
            val line = AudioSystem.getLine(info) as SourceDataLine
            line.open(audioFormat)
            line.start()

            val bufferSize = 4096
            val buffer = ByteArray(bufferSize)

            var count = 0
            while (count != -1) {
                count = audioInputStream.read(buffer, 0, bufferSize)
                if (count != -1) {
                    line.write(buffer, 0, count)
                }
            }

            line.drain()
            line.close()
            audioInputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}
