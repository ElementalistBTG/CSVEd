package ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import buttons
import model.CSVUnit
import rowData
import titles
import util.swapList
import util.testData
import java.io.File

lateinit var myList : SnapshotStateList<CSVUnit>
lateinit var pathOfOpenedFile : MutableState<String>
lateinit var file: File

fun main() = application {
    Window(
        title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication
    ) {

        pathOfOpenedFile = remember { mutableStateOf("") }
        myList = remember { mutableStateListOf<CSVUnit>() }

        DesktopMaterialTheme {
            Column {
                //First row is for buttons
                Spacer(modifier = Modifier.padding(5.dp))
                buttons(
                    onOpenClicked = { openNewFile() },
                    onSaveClicked = {},
                    onSaveAsClicked = {}
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "File: ${pathOfOpenedFile.value}")
                Spacer(modifier = Modifier.padding(5.dp))
                //Second Row is for titles
                titles()
                Divider(color = Color.Red, modifier = Modifier.height(3.dp))
                //Rest is for data
                LazyColumn {
                    items(myList) { item ->
                        rowData(item)
                        Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

fun openNewFile() {
    myList.clear()
    val openFile = EditCSV().openFile()
    file = openFile.second
    myList.swapList(openFile.first)
    pathOfOpenedFile.value = file.name
}

fun saveAsFile(file: File){
    EditCSV().saveAs(file)
}










