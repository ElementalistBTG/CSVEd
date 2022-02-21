import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.CSVUnit

@Composable
fun rowData(
    index: Int,
    item: CSVUnit,
    selected: Boolean,
    onItemSelected: (Int,Boolean)-> Unit
) {

    val checkedState = remember { mutableStateOf(item.enabled) }
    val antennaHeight = remember { mutableStateOf(item.antennaHeight) }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
            .toggleable(
                value = selected,
                onValueChange = { onItemSelected.invoke(index,selected) }
            )
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
}


