import ui.EditCSV
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun buttons(
    onOpenClicked: () -> Unit,
    onSaveAsClicked: () -> Unit,
    onActionsClicked: () -> Unit
) {
    Row {
        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = onOpenClicked
        ) {
            Text("Open File")
        }

        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = onSaveAsClicked
        ) {
            Text("Save as...")
        }

        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Magenta
            ),
            onClick = onActionsClicked
        ) {
            Text("Actions...")
        }

    }
}




