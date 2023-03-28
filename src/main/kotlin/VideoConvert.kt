import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio.CAP_PROP_FRAME_COUNT
import java.io.File
import javax.swing.JFileChooser

class VideoConvert {
    fun convertToImg() {
        val fileChooser = JFileChooser()
        val consoleUi = ConsoleUI()
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
            consoleUi.convertingToIMG()
            while (videoCapture.isOpened) {
                val frame = Mat()
                var exitFrame = Mat()
                val scaleSize = Size(128.0, 72.0)
                if ((frameCounter%(frames/100)).toInt() == 0) print("▇")
                if (videoCapture.read(frame)) {
                    try{
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


}