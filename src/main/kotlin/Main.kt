// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val DEFAULT_WIDTH = 500
const val DEFAULT_HEIGHT = 500

@Composable
@Preview
fun App() {

    var text by remember { mutableStateOf("Hello, World2!") }

    DesktopMaterialTheme {
        Row(modifier = Modifier.fillMaxSize()){
            Text("Id")
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Latitude")
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Longitude")
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Altitude")
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Antenna_Altitude")
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Enabled")
            Spacer(modifier = Modifier.padding(4.dp))
        }

    }
}

fun main() = application {
    Window(
        title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
