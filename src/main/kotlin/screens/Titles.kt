import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun titles() {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Text("Id", modifier = Modifier.padding(2.dp).weight(id_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Latitude", modifier = Modifier.padding(2.dp).weight(lat_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Longitude", modifier = Modifier.padding(2.dp).weight(long_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Altitude", modifier = Modifier.padding(2.dp).weight(alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Antenna Alt", modifier = Modifier.padding(2.dp).weight(ant_alt_weight))
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text("Enabled", modifier = Modifier.padding(2.dp).weight(checkbox_weight))
    }
}