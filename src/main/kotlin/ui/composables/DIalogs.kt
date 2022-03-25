package ui.composables

import androidx.compose.ui.awt.ComposeWindow
import model.CSVUnit
import start_directory
import javax.swing.*

//fun newEntry(): CSVUnit{
//
//}

fun showDialogWithMessage(message: String) {
    JOptionPane.showMessageDialog(
        ComposeWindow(),
        message,
        "Warning: Cannot complete action",
        JOptionPane.WARNING_MESSAGE
    )
}

fun chooseMoveIndexDialog(endSystemsRow: String): Int {

    val buttonsNames = arrayOf<Any>(
        "Move before entered value", "Move after 000 End Systems",
        "Cancel"
    )

    val panel = JPanel()
    panel.add(JLabel("Choose the id you want the items to move before: "))
    val textField = JTextField(10)
    panel.add(textField)

    val result = JOptionPane.showOptionDialog(
        null, panel, "Choose row index",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, buttonsNames, null
    )
    var n = ""
    if (result == JOptionPane.YES_OPTION) {
        n = textField.text
    } else if (result == JOptionPane.NO_OPTION) {
        n = (endSystemsRow.toInt() + 1).toString()
    }
    println(n)

//    val n: String? = JOptionPane.showInputDialog(
//        ComposeWindow(),
//        "Choose the id you want the items to move before: ",
//        "Choose row index",
//        JOptionPane.PLAIN_MESSAGE,
//        null,
//        null,
//        "1"
//    ) as String?

    return if (isNumber(n)) {
        //if a number is given as input then we take the value-1 since Kotlin/Java arrays start from 0 but ids start from 1
        n.toInt() - 1
    } else {
        -1
        //return value for not a number
    }
}

fun areYouSure(): Boolean {
//default icon, custom title
    //default icon, custom title
    val n = JOptionPane.showConfirmDialog(
        ComposeWindow(),
        "Are you sure that you want to enable all systems?",
        "Enable all systems",
        JOptionPane.YES_NO_OPTION
    )

    return n == JOptionPane.YES_OPTION

}

private fun isNumber(s: String?): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
}

fun chooseFolder(): String {
    val fileChooser = JFileChooser(start_directory)
    fileChooser.apply {
        dialogTitle = "Choose default location for files"
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    }
    val result = fileChooser.showOpenDialog(ComposeWindow())
    var selectedFolder = ""
    if (result == JFileChooser.APPROVE_OPTION) {
        // user selects a file
        selectedFolder = fileChooser.selectedFile.toString()
    }
    println(selectedFolder)
    return selectedFolder
}



