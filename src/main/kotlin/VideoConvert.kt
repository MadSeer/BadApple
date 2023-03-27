import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import java.io.File
import javax.swing.JFileChooser

class VideoConvert {

    fun convertToImg() {
        val fileChooser = JFileChooser()
        val imgproc = Imgproc()
        val result = fileChooser.showOpenDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            val file = fileChooser.selectedFile
            val filePath = file.absolutePath
            var videoCapture = VideoCapture(filePath)
            val directory = File("images")
            if (!directory.exists()) {
                directory.mkdir()
            }
            var frameCounter = 0
            while (videoCapture.isOpened) {
                val frame = Mat()
                val scaleSize = Size(300.0, 200.0)
                imgproc
                if (videoCapture.read(frame)) {
                    Imgcodecs.imwrite("images\\frame_${frameCounter}.jpg", frame)
                    frameCounter++
                } else {
                    break
                }
            }

            videoCapture.release()
        }
    }


}