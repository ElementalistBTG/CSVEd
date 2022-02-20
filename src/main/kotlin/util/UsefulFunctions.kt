package util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.awt.ComposeWindow
import javax.swing.JFileChooser
import javax.swing.JOptionPane

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}

class SaveFileChooser : JFileChooser() {
    override fun approveSelection() {
        val f = selectedFile
        if (f.exists() && dialogType == SAVE_DIALOG) {
            val result = JOptionPane.showConfirmDialog(
                ComposeWindow(),
                "The file exists, overwrite?",
                "Existing file",
                JOptionPane.YES_NO_CANCEL_OPTION
            )
            when (result) {
                JOptionPane.YES_OPTION -> {
                    super.approveSelection()
                    return
                }
                JOptionPane.NO_OPTION -> return
                JOptionPane.CLOSED_OPTION -> return
                JOptionPane.CANCEL_OPTION -> {
                    cancelSelection()
                    return
                }
            }
        }else{
            super.approveSelection()
            return
        }
    }

    companion object {
        private const val serialVersionUID = -8175471295012368922L
    }
}