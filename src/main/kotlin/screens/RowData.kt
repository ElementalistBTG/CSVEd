import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun rowData(
    id: Int,
    latitude: Double,
    longitude: Double,
    altitude: Float,
    antenna_alt: Float,
    enabled: Boolean
) {

    val checkedState = remember { mutableStateOf(enabled) }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Text(text = id.toString(), modifier = Modifier.padding(2.dp).weight(id_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = latitude.toString(), modifier = Modifier.padding(2.dp).weight(lat_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = longitude.toString(),modifier = Modifier.padding(2.dp).weight(long_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = altitude.toString(), modifier = Modifier.padding(2.dp).weight(alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(text = antenna_alt.toString(),modifier = Modifier.padding(2.dp).weight(ant_alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            modifier = Modifier.padding(2.dp).weight(checkbox_weight)
        )
    }
}


