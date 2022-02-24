import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import ui.EditCSV
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun buttons(
    onOpenClicked: () -> Unit,
    onSaveAsClicked: () -> Unit
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


    }
}






