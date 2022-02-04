import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun buttons() {
    Row {
        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { openFile() }) {
            Text("Open File")
        }

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { save() }) {
            Text("Save")
        }

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = { saveAs() }) {
            Text("Save as...")
        }

    }
}

fun openFile() {

}

fun save() {}

fun saveAs() {}


