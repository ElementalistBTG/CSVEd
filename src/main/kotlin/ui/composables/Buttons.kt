import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job


@Composable
fun buttons(
    onOpenClicked: () -> Unit,
    onSaveAsClicked: () -> Unit,
    onClearSelectionClicked: () -> Unit,
    onEnableAll: () -> Unit,
    onSearch: (String) -> Job,
    onFindNext: () -> Job
) {

    val searchText = remember { mutableStateOf("") }

    Row {
        primaryButton(
            onClick = onOpenClicked,
            text = "Open File"
        )
        Spacer(modifier = Modifier.padding(3.dp))
        primaryButton(
            onClick = onSaveAsClicked,
            text = "Save as..."
        )
        Spacer(modifier = Modifier.padding(3.dp))
        secondaryButton(
            onClick = onClearSelectionClicked,
            text = "Clear Selection"
        )
        Spacer(modifier = Modifier.padding(3.dp))
        secondaryButton(
            onClick = onEnableAll,
            text = "Enable all"
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Box(modifier = Modifier.border(1.dp, color = Color.Red)) {

            Row {
                BasicTextField(
                    value = searchText.value,
                    onValueChange = { newValue ->
                        searchText.value = newValue
                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.width(120.dp).height(35.dp).padding(start = 10.dp, end = 10.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
                secondaryButton(
                    onClick = { onSearch.invoke(searchText.value) },
                    text = "Search"
                )
                secondaryButton(
                    onClick = { onFindNext.invoke() },
                    text = "Find Next"
                )
            }

        }
    }
}

@Composable
private fun primaryButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.LightGray
        ),
        onClick = onClick
    ) {
        Text(text)
    }
}

@Composable
private fun secondaryButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.LightGray,
            contentColor = Color.Blue
        ),
        onClick = onClick
    ) {
        Text(text)
    }
}





