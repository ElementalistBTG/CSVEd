import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
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
    onRightMouseClick: (Int) -> Unit
) {

    val checkedState =  mutableStateOf(item.enabled)
    val antennaHeight = mutableStateOf(item.antennaHeight) // we don't want to remember the state because then in every change we will have the same values (even though they would have changed)

    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
            .background(rowColor(item, selected))
            .mouseClickable(
                onClick = {
                    if (this.buttons.isSecondaryPressed) {
                        onRightMouseClick.invoke(index)
                    } else {
                        onItemSelected.invoke(!selected, index)
                    }

                })
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
            onValueChange = { newValue ->
                item.antennaHeight = newValue
                antennaHeight.value = newValue
            },
            modifier = Modifier.padding(2.dp).weight(ant_alt_weight)
        )
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Checkbox(
            checked = checkedState.value.toInt() == 1,
            onCheckedChange = {
                if (it.toString() == "true") {
                    checkedState.value = "1"
                    item.enabled = "1"
                } else {
                    checkedState.value = "0"
                    item.enabled = "0"
                }
            },
            modifier = Modifier.padding(2.dp).weight(checkbox_weight)
        )
    }

}

@Composable
fun rowColor(system: CSVUnit, selected: Boolean): Color {
    return if (system.name == endSystems || system.name == lastEntry) {
        if (selected) {
            Color(0xFFb862fc)
        } else {
            Color(0xFFddb3ff)
        }
    } else if (system.latitude == "0") {
        if (selected) {
            Color(0xFFffdb66)
        } else {
            Color(0xFFfcefb3)
        }
    } else {
        if (selected) {
            MaterialTheme.colors.secondary
        } else {
            Color.Transparent
        }

    }
}


