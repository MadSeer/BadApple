import nu.pattern.OpenCV
import javax.swing.JFileChooser

suspend fun main(args: Array<String>) {

    val convertMP4 = VideoConvert()
    val convertIMG = IMGFileManipulations()
    val audio = Audio()
    val ui = ConsoleUI()
    val play = Play()

    var run = true
    while (run) {
        println(
            """
        1. Convert
        2. Play
        3. Exit
        4. Clear Screen
        
        0. Debug
    """.trimIndent()
        )

        var menu = readln()
        when (menu) {
            "1" -> {
                OpenCV.loadLocally()
                val fileChooser = JFileChooser()
                val fileChoose = fileChooser.showOpenDialog(null)
                convertMP4.convertToImg(fileChoose, fileChooser)
                convertIMG.filesList()
                convertIMG.convertAllToASCII()
                audio.separateAudio(fileChoose,fileChooser)
            }

            "2" -> {
                play.play()
            }

            "3" -> {
                run = false
            }

            "4" -> {
                ui.cls()
            }


            "0" -> {

            }
        }
    }


}