import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.mouseClickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.unit.dp
import model.CSVUnit


@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
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
        Text(text = item.id, modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(id_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = item.name, modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(name_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            text = item.latitude,
            modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(lat_weight)
        )
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            text = item.longitude,
            modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(long_weight)
        )
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            text = item.altitude,
            modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(alt_weight)
        )
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        BasicTextField(
            value = antennaHeight.value,
            onValueChange = { newValue ->
                item.antennaHeight = newValue
                antennaHeight.value = newValue
            },
            singleLine = true,
            modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(ant_alt_weight)
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
    return if (system.name == END_SYSTEMS || system.name == LAST_ENTRY) {
        if (selected) {
            Color(0xFFb862fc)
        } else {
            Color(0xFFddb3ff)
        }
    } else if (system.latitude[0] == '0') {
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


