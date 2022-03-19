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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import buttons
import model.CSVUnit
import rowData
import titles
import ui.composables.chooseMoveIndex
import ui.composables.showDialogForNullSelection
import ui.composables.showDialogForSingleSelection
import util.swapList
import java.nio.file.Path


lateinit var myList: SnapshotStateList<CSVUnit>
lateinit var pathOfOpenedFile: MutableState<String>
val myClipBoard = mutableListOf<CSVUnit>()
val selectedItems = mutableStateMapOf<Int, Boolean>()

@OptIn(
    ExperimentalDesktopApi::class, ExperimentalFoundationApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
fun main() = application {
    Window(
        title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication,
        onKeyEvent = {
            if (it.isCtrlPressed && it.key == Key.S) {
                saveAsFile()
                true
            } else if (it.key == Key.Delete) {
                deleteSelected()
                true
            } else {
                // let other handlers receive this event
                false
            }
        }
    ) {
        pathOfOpenedFile = remember { mutableStateOf("") }
        myList = remember { mutableStateListOf<CSVUnit>() }
        val listState = rememberLazyListState() //for list items
        val onItemSelected = { selected: Boolean, index: Int ->
            selectedItems[index] = selected
        }
        var expanded by mutableStateOf(false)
        var itemRightClicked by mutableStateOf(-1)
        val onRightMouseClick = { index: Int ->
            itemRightClicked = index
            expanded = true
        }

        DesktopMaterialTheme {
            Column {
                //First row is for buttons
                Spacer(modifier = Modifier.padding(5.dp))
                buttons(
                    onOpenClicked = { openNewFile() },
                    onSaveAsClicked = { saveAsFile() },
                    onClearSelectionClicked = { clearSelection() },
                    onEnableAll = { enableAll() }
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
                            if (expanded && itemRightClicked == index) {
                                val items = listOf("Cut", "Paste before", "Delete", "Move To")
                                DropdownMenu(
                                    expanded = true,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    items.forEachIndexed { index, itemTitle ->
                                        DropdownMenuItem(onClick = {
                                            when (index) {
                                                0 -> cutSelected()
                                                1 -> pasteSelected()
                                                2 -> deleteSelected()
                                                3 -> moveSelected()
                                            }
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

fun enableAll() {
    for (item in myList) {
        item.enabled = "1"
    }
}

private fun clearSelection() {
    selectedItems.clear()
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

private fun recalculateIds() {
    for ((id, item) in myList.withIndex()) {
        item.id = (id + 1).toString()
    }
}

private fun cutSelected() {
    myClipBoard.clear()
    val selection = singleSelection()
    if (selection == -1) {
        //nothing selected
        showDialogForNullSelection()
    }else{
        for (item in selectedItems) {
            if (item.value) {
                val selectedRow = myList[item.key]
                myClipBoard.add(selectedRow)
                myList.remove(selectedRow)
            }
        }
        clearSelection()
        recalculateIds()
    }
}

private fun moveSelected() {
    val selection = singleSelection()
    if (selection == -1) {
        //nothing selected
        showDialogForNullSelection()
    }else{
        val index = chooseMoveIndex()
        //if dismissed we do nothing
        //else we do the cut -> paste to specific index
        if (index != -1) {
            cutSelected()
            createNewList(index)
            recalculateIds()
        }
    }
}

private fun pasteSelected() {
    val indexTriggered = singleSelection()
    if (indexTriggered > 0) {
        createNewList(indexTriggered)
        clearSelection()
        recalculateIds()
    } else {
        showDialogForSingleSelection()
    }
}

private fun deleteSelected() {
    val selection = singleSelection()
    if (selection == -1) {
        //nothing selected
        showDialogForNullSelection()
    }else{
        for (item in selectedItems) {
            if (item.value) {
                val selectedRow = myList[item.key]
                myList.remove(selectedRow)
            }
        }
        clearSelection()
        recalculateIds()
    }
}

private fun singleSelection(): Int {
    var firstEntry = true
    var index = -1
    for (item in selectedItems.entries) {
        if (item.value) {
            if (firstEntry) {
                firstEntry = false
                index = item.key
            } else {
                index = -2
                break
            }
        }
    }
    return index
}

private fun createNewList(index: Int) {
    myList.swapList(
        myList.subList(0, index) + myClipBoard + myList.subList(index, myList.size - 1)
    )
}

















