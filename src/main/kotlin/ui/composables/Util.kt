package ui.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import javax.swing.JOptionPane

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertShow(
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss },
        title = { Text("Warning") },
        confirmButton = {
            Button(
                onClick = { onDismiss }
            ) {
                Text("Ok")
            }
        },
        text = { Text("Please select an entry") })
}

fun showDialogForSingleSelection() {
    JOptionPane.showMessageDialog(
        ComposeWindow(),
        "Choose only one row first to perform action.",
        "Warning: Cannot complete action",
        JOptionPane.WARNING_MESSAGE
    )
}

fun showDialogForNullSelection() {
    JOptionPane.showMessageDialog(
        ComposeWindow(),
        "Choose at least one row first to perform action.",
        "Warning: Cannot complete action",
        JOptionPane.WARNING_MESSAGE
    )
}


fun chooseMoveIndex(): Int {

    val n: String? = JOptionPane.showInputDialog(
        ComposeWindow(),
        "Choose the id you want the items to move before: ",
        "Choose row index",
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        "0"
    ) as String?

    return if (isNumber(n)) {
        n!!.toInt() - 2
    } else {
        -1
    }
}

fun isNumber(s: String?): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
}



