import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun titles() {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Text("Id", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(id_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Name", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(name_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Latitude", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(lat_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Longitude", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(long_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Altitude", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Antenna\nHeight", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(ant_alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Enabled", modifier = Modifier.padding(2.dp).align(Alignment.CenterVertically).weight(checkbox_weight))
    }
}