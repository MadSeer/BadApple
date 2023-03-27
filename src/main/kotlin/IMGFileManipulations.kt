import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileOutputStream

class IMGFileManipulations {

    private var imagesIMG = arrayListOf<File>() //List of path to every picture

    fun filesList() {
        val path = File("./images")
        val arrFilles = path.listFiles()
        arrFilles?.forEach { file -> this.imagesIMG.add(file) }
    }

    fun displayFileNames() {
        this.imagesIMG.forEach { file -> println(file) }
    }

    private fun imgToGray(image: Mat): Array<DoubleArray> {
        val grayMat = Mat(image.rows(), image.cols(), CvType.CV_8U)
        Imgproc.cvtColor(image, grayMat, Imgproc.COLOR_BGR2GRAY)
        val scaledImage = Mat()
        Core.convertScaleAbs(grayMat, scaledImage)
        val rows = scaledImage.rows()
        val cols = scaledImage.cols()
        val imageArray = Array(rows) { DoubleArray(cols) }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                imageArray[i][j] = scaledImage.get(i, j)[0]
            }
        }
        return imageArray
    }

    /*private fun arrayToASCII(img: Array<DoubleArray>, counter: Int){
        val fileASCII = File("ASCII\\frame_${counter}.txt")
        if (!fileASCII.exists()) {
            try {
                val write = FileOutputStream("ASCII\\frame_${counter}.txt")

                write.write(buffer,0,buffer.size)
            } catch (e: Exception) {
                println("Error")
            }
        }
    }*/


    fun convertAllToASCII() {
        val directory = File("ASCII")
        if (!directory.exists()) {
            directory.mkdir()
        }
        var counter = 0
        imagesIMG.forEach { image ->
            val img = imgToGray(Imgcodecs.imread(image.path))
            //arrayToASCII(img,counter)
            counter++
        }
    }

}