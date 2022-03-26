package ui.composables

import androidx.compose.ui.awt.ComposeWindow
import javax.swing.*


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
    val n = JOptionPane.showConfirmDialog(
        ComposeWindow(),
        "Are you sure that you want to enable all systems?\n" +
                "(The systems with no real coordinates will be saved as disabled after saving)",
        "Enable all systems",
        JOptionPane.YES_NO_OPTION
    )

    return n == JOptionPane.YES_OPTION
}

private fun isNumber(s: String?): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
}



