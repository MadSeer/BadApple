import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio.CAP_PROP_FRAME_COUNT
import java.io.File
import javax.swing.JFileChooser

class VideoConvert {
    val consoleUi = ConsoleUI()
    fun convertToImg() {
        val fileChooser = JFileChooser()
        val result = fileChooser.showOpenDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            val file = fileChooser.selectedFile
            val filePath = file.absolutePath
            var videoCapture = VideoCapture(filePath)
            val directory = File("images")
            if (!directory.exists()) {
                directory.mkdir()
            }
            val frames = videoCapture.get(CAP_PROP_FRAME_COUNT)
            var frameCounter = 0
            val scaleSize = resolutionChoise()
            consoleUi.convertingToIMG()
            while (videoCapture.isOpened) {
                val frame = Mat()
                var exitFrame = Mat()
                if ((frameCounter%(frames/100)).toInt() == 0) print("▇")
                if (videoCapture.read(frame)) {
                    if(scaleSize.height != 0.0 && scaleSize.width != 0.0) try {
                        Imgproc.resize(frame, exitFrame,scaleSize)
                    } catch (e: Exception){
                        print(e)
                    }
                    Imgcodecs.imwrite("images\\frame_${frameCounter}.jpg", exitFrame)
                    frameCounter++
                } else {
                    break
                }
            }
            println()
            videoCapture.release()
        }
    }

    private fun resolutionChoise():Size{
        consoleUi.resolutionChoice()
        val choice = readln().toString()
        when(choice){
            "1" -> return Size(256.0, 144.0)
            "2" -> return Size(426.0,240.0)
            "3" -> return Size(480.0,360.0)
            "4" -> return Size(640.0, 480.0)
            "5" -> return Size(1280.0,720.0)
            "6" -> return Size(1920.0,1080.0)
            "7" -> return Size(0.0,0.0)
            else -> return Size(128.0, 72.0)
        }
    }

}