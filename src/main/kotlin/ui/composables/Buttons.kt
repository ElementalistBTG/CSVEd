import ui.EditCSV
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.MainViewModel

@Composable
fun buttons(viewModel : MainViewModel) {
    val editCSVClass = EditCSV()
    Row {
        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { editCSVClass.openFile() }) {
            Text("Open File")
        }

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { editCSVClass.save() }) {
            Text("Save")
        }

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { editCSVClass.saveAs() }) {
            Text("Save as...")
        }

    }
}




