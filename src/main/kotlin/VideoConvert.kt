import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.VideoWriter
import org.opencv.videoio.Videoio.CAP_PROP_FRAME_COUNT
import java.io.File
import javax.swing.JFileChooser

class VideoConvert {
    val consoleUi = ConsoleUI()

    fun changeFramerate(fileChoosed: Int, fileChooser: JFileChooser) {

        val file = fileChooser.selectedFile
        val filePath = file.absolutePath
        var inputVideo = VideoCapture(filePath)

        // Get input video properties
        val fps = inputVideo.get(5)
        val width = inputVideo.get(3)
        val height = inputVideo.get(4)

        // Create output video file
        val outputVideo = VideoWriter(
            "output_video.mp4",
            VideoWriter.fourcc('m', 'p', '4', 'v'),
            25.0,
            org.opencv.core.Size(width, height),
            true
        )

        // Process input video frames
        val frame = Mat()
        while (inputVideo.read(frame)) {
            // Write processed frame to output video
            outputVideo.write(frame)

            // Delay to achieve desired frame rate
            Thread.sleep((1000 / 25).toLong())
        }

        // Release video capture and writer resources
        inputVideo.release()
        outputVideo.release()
    }

    fun convertToImg(fileChoosed: Int, fileChooser: JFileChooser) {

        if (fileChoosed == JFileChooser.APPROVE_OPTION) {
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
                if ((frameCounter % (frames / 100)).toInt() == 0) print("▇")
                if (try {
                        videoCapture.read(frame)
                    } catch (e: Exception) {
                        false
                    }
                ) {
                    if (scaleSize.height != 0.0 && scaleSize.width != 0.0) try {
                        Imgproc.resize(frame, exitFrame, scaleSize)
                    } catch (e: Exception) {
                        print(e)
                    }
                    Imgcodecs.imwrite("images\\${frameCounter}.jpg", exitFrame)
                    frameCounter++
                } else {
                    break
                }
            }
            println()
            videoCapture.release()
        }
    }

    private fun resolutionChoise(): Size {
        consoleUi.resolutionChoice()
        val choice = readln().toString()
        when (choice) {
            "1" -> return Size(256.0, 144.0)
            "2" -> return Size(426.0, 240.0)
            "3" -> return Size(480.0, 360.0)
            "4" -> return Size(640.0, 480.0)
            "5" -> return Size(1280.0, 720.0)
            "6" -> return Size(1920.0, 1080.0)
            "7" -> return Size(0.0, 0.0)
            else -> return Size(128.0, 72.0)
        }
    }

}