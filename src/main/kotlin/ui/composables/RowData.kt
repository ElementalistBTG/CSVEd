import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.CSVUnit

@Composable
fun rowData(
    data: CSVUnit
) {

    val checkedState = remember { mutableStateOf(data.enabled) }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Text(text = data.id, modifier = Modifier.padding(2.dp).weight(id_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = data.name, modifier = Modifier.padding(2.dp).weight(name_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = data.latitude, modifier = Modifier.padding(2.dp).weight(lat_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = data.longitude, modifier = Modifier.padding(2.dp).weight(long_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = data.altitude, modifier = Modifier.padding(2.dp).weight(alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = data.antennaHeight, modifier = Modifier.padding(2.dp).weight(ant_alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Checkbox(
            checked = checkedState.value.toInt() == 1,
            onCheckedChange = { checkedState.value = if (it.toString() == "true") "1" else "0" },
            modifier = Modifier.padding(2.dp).weight(checkbox_weight)
        )
    }
}


