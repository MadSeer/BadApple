import nu.pattern.OpenCV


fun main(args: Array<String>) {
    OpenCV.loadLocally()
    val convertMP4 = VideoConvert()
    val convertIMG = IMGFileManipulations()
    val play = Play()
    convertMP4.convertToImg()
    convertIMG.filesList()
    //convertIMG.displayFileNames()
    convertIMG.convertAllToASCII()
    play.play()
    println("Gotovo")


}