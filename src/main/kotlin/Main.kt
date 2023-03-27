import nu.pattern.OpenCV

fun main() {
    OpenCV.loadLocally()
    val convertMP4 = VideoConvert()
    val convertIMG = IMGFileManipulations()
    convertMP4.convertToImg()
    convertIMG.filesList()
    convertIMG.displayFileNames()
    //convertIMG.convertAllToASCII()
    println("Gotovo")
}