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
    onSaveAsClicked: () -> Unit,
    onClearSelectionClicked: () -> Unit,
    onEnableAll: () -> Unit,
    onSearch: () -> Unit
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

//        Divider(
//            modifier = Modifier.width(5.dp), color = Color.Red
//        )
//
//        OutlinedButton(
//            colors = ButtonDefaults.textButtonColors(
//                backgroundColor = Color.LightGray,
//                contentColor = Color.Blue
//            ),
//            onClick = onSaveAsClicked
//        ) {
//            Text("Copy")
//        }
//
//        Spacer(modifier = Modifier.padding(5.dp))
//
//        OutlinedButton(
//            colors = ButtonDefaults.textButtonColors(
//                backgroundColor = Color.LightGray,
//                contentColor = Color.Blue
//            ),
//            onClick = onSaveAsClicked
//        ) {
//            Text("Cut")
//        }
//
//        Spacer(modifier = Modifier.padding(5.dp))
//
//        OutlinedButton(
//            colors = ButtonDefaults.textButtonColors(
//                backgroundColor = Color.LightGray,
//                contentColor = Color.Blue
//            ),
//            onClick = onSaveAsClicked
//        ) {
//            Text("Paste")
//        }
//
        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onClearSelectionClicked
        ) {
            Text("Clear Selection")
        }

        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onEnableAll
        ) {
            Text("Enable all")
        }

        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onSearch
        ) {
            Text("Search")
        }

    }
}






