import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import ui.EditCSV
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job


@Composable
fun buttons(
    onOpenClicked: () -> Unit,
    onSaveAsClicked: () -> Unit,
    onClearSelectionClicked: () -> Unit,
    onEnableAll: () -> Unit,
    onSearch: (String) -> Job,
    onFolderSelect: () -> Unit,
    onFindNext: () -> Job
) {
    val searchPressed = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }

    Row {
        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = onOpenClicked
        ) {
            Text("Open File")
        }

        Spacer(modifier = Modifier.padding(3.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            onClick = onSaveAsClicked
        ) {
            Text("Save as...")
        }

        Spacer(modifier = Modifier.padding(3.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onClearSelectionClicked
        ) {
            Text("Clear Selection")
        }

        Spacer(modifier = Modifier.padding(3.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onEnableAll
        ) {
            Text("Enable all")
        }

        Spacer(modifier = Modifier.padding(3.dp))

        OutlinedButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Blue
            ),
            onClick = onFolderSelect
        ) {
            Text("Default Folder...")
        }

        Spacer(modifier = Modifier.padding(3.dp))

        Box(modifier = Modifier.border(1.dp, color = Color.Red)) {
            if (searchPressed.value) {
                Row {
                    BasicTextField(
                        value = searchText.value,
                        onValueChange = { newValue ->
                            searchText.value = newValue
                        },
//                        placeholder = { Text("Search by name") },
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier.width(120.dp).height(35.dp).padding(start = 10.dp, end = 10.dp).wrapContentHeight(Alignment.CenterVertically)
                    )

                    OutlinedButton(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.LightGray,
                            contentColor = Color.Blue
                        ),
                        onClick = { onSearch.invoke(searchText.value) }
                    ) {
                        Text("Search")
                    }
                    OutlinedButton(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.LightGray,
                            contentColor = Color.Blue
                        ),
                        onClick = { onFindNext.invoke() }
                    ) {
                        Text("Find Next")
                    }
                }
            } else {
                OutlinedButton(
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.Blue
                    ),
                    onClick = {
                        searchPressed.value = true
                    }
                ) {
                    Text("Search")
                }
            }
        }

    }
}






