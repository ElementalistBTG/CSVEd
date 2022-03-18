package ui.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertShow(){
    AlertDialog(onDismissRequest = {},
        title = { Text("Warning") },
        confirmButton = {
            Button(onClick = {
            }) {
                Text("Ok")
            }
        },
        text = { Text("Please select first an entry") })
}

