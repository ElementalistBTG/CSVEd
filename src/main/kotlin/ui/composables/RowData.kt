import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.CSVUnit





@OptIn(ExperimentalDesktopApi::class)
@Composable
fun rowData(
    index: Int,
    item: CSVUnit,
    selected: Boolean,
    onItemSelected: (Boolean, Int) -> Unit,
    onRightMouseClick: () -> Unit
) {

    val checkedState = remember { mutableStateOf(item.enabled) }
    val antennaHeight = remember { mutableStateOf(item.antennaHeight) }
    var expanded by mutableStateOf(false)

    Box{
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
                .toggleable(
                    value = !selected,
                    onValueChange = {
                        onItemSelected.invoke(!selected, index)
                    }
                )
                .mouseClickable(onClick = {
                    if (this.buttons.isSecondaryPressed) {
                        onRightMouseClick.invoke()
                        expanded = true
                    }
                })
//            .onKeyEvent {
//                shiftPressed = it.isShiftPressed
//                true
//            }
                .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)

        ) {
            Text(text = item.id, modifier = Modifier.padding(2.dp).weight(id_weight))
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(text = item.name, modifier = Modifier.padding(2.dp).weight(name_weight))
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(text = item.latitude, modifier = Modifier.padding(2.dp).weight(lat_weight))
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(text = item.longitude, modifier = Modifier.padding(2.dp).weight(long_weight))
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(text = item.altitude, modifier = Modifier.padding(2.dp).weight(alt_weight))
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            BasicTextField(
                value = antennaHeight.value,
                onValueChange = {
                    item.antennaHeight = it
                    antennaHeight.value = it
                },
                modifier = Modifier.padding(2.dp).weight(ant_alt_weight)
            )
            Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Checkbox(
                checked = checkedState.value.toInt() == 1,
                onCheckedChange = { checkedState.value = if (it.toString() == "true") "1" else "0" },
                modifier = Modifier.padding(2.dp).weight(checkbox_weight)
            )
        }
        Box(
            modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd),
            contentAlignment = Alignment.TopEnd
            ){
            if (expanded) {
                println("dropdown menu called")
                val items = listOf("Copy", "Cut", "Paste", "Delete", "Clear Selection", "Move To")
                var selectedIndex by remember { mutableStateOf(0) }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, itemTitle ->
                        DropdownMenuItem(onClick = {
                            println("$itemTitle clicked")
                            selectedIndex = index
                            expanded = false

                        }) {
                            Text(text = itemTitle)
                        }
                    }
                }
            }
        }

    }

}


private fun copyItems() {

}

private fun clearSelection() {

}


