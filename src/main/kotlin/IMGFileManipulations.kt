import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileWriter

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

    private fun arrayToASCII(img: Array<DoubleArray>, counter: Int){
        val fileASCII = File("ASCII\\frame_${counter}.txt")
        var buff: Array<Array<String>> = Array(img.size) { Array(img[0].size) { " " } }
        //if (fileASCII.exists()) {
            //try {
                val write = FileWriter("ASCII\\frame_${counter}.txt")
                img.forEachIndexed { index1, doubles ->
                    doubles.forEachIndexed { index2, d ->

                        var check:String = (img[index1][index2]/32).toInt().toString()
                        when(check){
                            "0" -> buff[index1][index2] = symbol0
                            "1" -> buff[index1][index2] = symbol1
                            "2" -> buff[index1][index2] = symbol2
                            "3" -> buff[index1][index2] = symbol3
                            "4" -> buff[index1][index2] = symbol4
                            "5" -> buff[index1][index2] = symbol5
                            "6" -> buff[index1][index2] = symbol6
                            "7" -> buff[index1][index2] = symbol7
                        }
                    }

                }
        buff.forEachIndexed { index1, doubles ->
            doubles.forEachIndexed { index2, d ->
                print(buff[index1][index2])
                write.write(buff[index1][index2])
            }
            println()
            write.write("\n")

        }
        write.flush()
    }

    fun convertAllToASCII() {
        val consoleUI = ConsoleUI()
        val directory = File("ASCII")
        if (!directory.exists()) {
            directory.mkdir()
        }
        consoleUI.convertingToTXT()
        var counter = 0
        imagesIMG.forEach { image ->
            val img = imgToGray(Imgcodecs.imread(image.path))
            arrayToASCII(img,counter)
            if ((counter%(imagesIMG.size/100)) == 0) print("▇")
            counter++
        }
        println()
    }



    companion object{
        const val symbol0: String = " "
        const val symbol1: String = "▂"
        const val symbol2: String = "▃"
        const val symbol3: String = "▄"
        const val symbol4: String = "▅"
        const val symbol5: String = "▆"
        const val symbol6: String = "▇"
        const val symbol7: String = "█"

    }

}