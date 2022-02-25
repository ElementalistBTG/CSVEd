package ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
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
import java.nio.file.Path


lateinit var myList: SnapshotStateList<CSVUnit>
lateinit var pathOfOpenedFile: MutableState<String>
//val localClipboardManager = LocalClipboardManager.current


@OptIn(ExperimentalDesktopApi::class, ExperimentalFoundationApi::class)

fun main() = application {
    Window(
        title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication
    ) {
        pathOfOpenedFile = remember { mutableStateOf("") }
        myList = remember { mutableStateListOf<CSVUnit>() }
        val listState = rememberLazyListState() //for list items

        val selectedItems = remember { mutableStateMapOf<Int, Boolean>() }
//        val currentlySelectedItem = remember { mutableStateOf(0) }
        val onItemSelected = { selected: Boolean, index: Int ->
            selectedItems[index] = selected
        }

        var expanded by mutableStateOf(false)
        var itemRightClicked by mutableStateOf(-1)
        val onRightMouseClick = { index:Int->
            itemRightClicked = index
            expanded = true
        }

        DesktopMaterialTheme {
            Column {
                //First row is for buttons
                Spacer(modifier = Modifier.padding(5.dp))
                buttons(
                    onOpenClicked = { openNewFile() },
                    onSaveAsClicked = { saveAsFile() }
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "File: ${pathOfOpenedFile.value}")
                Spacer(modifier = Modifier.padding(5.dp))
                //Second Row is for titles
                titles()
                Divider(color = Color.Red, modifier = Modifier.height(3.dp))
                //Rest is for data

                LazyColumn(
                    state = listState
                ) {
                    itemsIndexed(myList) { index, item ->
                        val current = selectedItems[index] ?: false
                        rowData(
                            index = index,
                            item = item,
                            selected = current,
                            onItemSelected = onItemSelected,
                            onRightMouseClick = onRightMouseClick
                        )
                        Divider(color = Color.Black, modifier = Modifier.height(1.dp))
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            if (expanded && itemRightClicked == index ) {
                                println("dropdown menu called for index: $itemRightClicked")
                                val items = listOf("Copy", "Cut", "Paste", "Delete", "Clear Selection", "Move To")
                                var selectedMenuIndex by remember { mutableStateOf(0) }
                                DropdownMenu(
                                    expanded = true,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    items.forEachIndexed { index, itemTitle ->
                                        DropdownMenuItem(onClick = {
                                            println("$itemTitle clicked")
                                            selectedMenuIndex = index
                                            expanded = false

                                        }) {
                                            Text(text = itemTitle)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


private var fileToSave: Path? = null
private fun openNewFile() {
    myList.clear()
    val fileOpened = EditCSV().openFile()
    if (fileOpened != null) {
        myList.swapList(fileOpened.first)
        pathOfOpenedFile.value = fileOpened.second.name
        fileToSave = fileOpened.second.toPath()
    }
}

private fun saveAsFile() {
    if (fileToSave != null) {
        EditCSV().saveAs(fileToSave!!, myList.toList())
    }
}
















