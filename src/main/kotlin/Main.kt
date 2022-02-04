// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val DEFAULT_WIDTH = 500
const val DEFAULT_HEIGHT = 500

@Composable
fun App() {

    var text by remember { mutableStateOf("Hello, World2!") }

    DesktopMaterialTheme {
        Column {
            //First row is for buttons
            Spacer(modifier = Modifier.padding(5.dp))
            buttons()
            Spacer(modifier = Modifier.padding(5.dp))
            //Second Row is for titles
            titles()
            Divider(color = Color.Red, modifier = Modifier.height(3.dp))
            //Rest is for data
            LazyColumn {
                //test data
                item {
                    rowData(
                        1,
                        38.0235,
                        25.033656,
                        1000.0f,
                        150.5f,
                        true
                    )
                    Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                }

                item {
                    rowData(
                        2,
                        38.0235,
                        25.03365646,
                        1000.0f,
                        150.5f,
                        false
                    )
                    Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                }

                item {
                    rowData(
                        3,
                        38.0,
                        25.033656,
                        1000.0f,
                        150.5f,
                        true
                    )
                    Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                }

            }
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
