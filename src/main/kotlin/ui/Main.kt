package ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import buttons
import model.CSVUnit
import rowData
import titles
import util.testData

fun main() = application {
    Window(
        title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication
    ) {

        val viewModel = MainViewModel()
        val items by viewModel.itemsFlow.collectAsState(initial = emptyList())

        DesktopMaterialTheme {
            Column {
                //First row is for buttons
                Spacer(modifier = Modifier.padding(5.dp))
                buttons(viewModel)
                Spacer(modifier = Modifier.padding(5.dp))
                //Second Row is for titles
                titles()
                Divider(color = Color.Red, modifier = Modifier.height(3.dp))
                //Rest is for data
                LazyColumn {
                    items(items) { item ->
                        rowData(item)
                        Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }


}




