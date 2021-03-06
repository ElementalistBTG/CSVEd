package ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


import END_SYSTEMS
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import buttons
import data.EditCSV
import kotlinx.coroutines.launch
import model.CSVUnit
import rowData
import titles
import ui.composables.areYouSure
import ui.composables.chooseMoveIndexDialog
import ui.composables.deleteSystems
import ui.composables.showDialogWithMessage
import util.swapList
import java.awt.HeadlessException
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException
import java.nio.file.Path


lateinit var myList: SnapshotStateList<CSVUnit>
lateinit var nameOfOpenedFile: MutableState<String>
val myClipBoard = mutableListOf<CSVUnit>()
var endSystemsRow = "0"


val selectedItems = mutableStateMapOf<Int, Boolean>()
var shiftIsPressed = false


@OptIn(
    ExperimentalComposeUiApi::class
)
fun main() = application {
    Window(title = "Radio Mobile CSV Editor",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1000.dp, height = 600.dp),
        onKeyEvent = {
            if (it.isCtrlPressed && it.key == Key.S) {
                saveAsFile()
                true
            } else if (it.key == Key.Delete && it.type == KeyEventType.KeyUp) {
                deleteSelected()
                true
            } else if (it.key == Key.ShiftLeft && it.type == KeyEventType.KeyDown) {
                shiftIsPressed = true
                true
            } else if (it.key == Key.ShiftLeft && it.type == KeyEventType.KeyUp) {
                shiftIsPressed = false
                true
            } else {
                // let other handlers receive this event
                false
            }
        }) {
        //name of file opened
        nameOfOpenedFile = remember { mutableStateOf("") }
        //list of data
        myList = remember { mutableStateListOf<CSVUnit>() }
        val listState = rememberLazyListState() //for list items
        var lastSelectedRow = 0
        val onItemSelected = { selected: Boolean, index: Int ->
            if (shiftIsPressed) {
                if (lastSelectedRow < index) {
                    for (i in lastSelectedRow..index) {
                        selectedItems[i] = selected
                    }
                } else {
                    for (i in lastSelectedRow downTo index) {
                        selectedItems[i] = selected
                    }
                }
                lastSelectedRow = index
            } else {
                selectedItems[index] = selected
                lastSelectedRow = index
            }
        }
        var expanded by mutableStateOf(false)
        var itemRightClicked by mutableStateOf(-1)
        val onRightMouseClick = { index: Int ->
            itemRightClicked = index
            expanded = true
        }
        //system to find
        val coroutineScope = rememberCoroutineScope()
        val itemWithMatchingName = mutableListOf<Int>()
        var listIterator = itemWithMatchingName.listIterator()
        val searchSystem = { name: String ->
            coroutineScope.launch {
                itemWithMatchingName.clear()
                for (item in myList) {
                    if (item.name.contains(name, ignoreCase = true)) {
                        itemWithMatchingName.add(item.id.toInt())
                    }
                }
                if (itemWithMatchingName.isNotEmpty()) {
                    listIterator = itemWithMatchingName.listIterator()
                    listState.scrollToItem(itemWithMatchingName.first() - 1)
                }
            }
        }

        val findNext = {
            coroutineScope.launch {
                if (listIterator.hasNext()) {
                    listState.scrollToItem(listIterator.next() - 1)
                } else if (listIterator.hasPrevious()) {//ensure we don't call on empty iterator
                    listIterator = itemWithMatchingName.listIterator()
                    listState.scrollToItem(listIterator.next() - 1)
                }
            }
        }

        MaterialTheme {
            Column {
                //First row is for buttons
                Spacer(modifier = Modifier.padding(5.dp))
                buttons(
                    onOpenClicked = { openNewFile() },
                    onSaveAsClicked = { saveAsFile() },
                    onClearSelectionClicked = { clearSelection() },
                    onEnableAll = { enableAll() },
                    onSearch = searchSystem,
                    onFindNext = findNext
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "File: ${nameOfOpenedFile.value}")
                Spacer(modifier = Modifier.padding(5.dp))
                //Second Row is for titles
                titles()
                Divider(color = Color.Red, modifier = Modifier.height(3.dp))
                //Rest is for data + dropdown menu when called
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                                modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopEnd
                            ) {
                                if (expanded && itemRightClicked == index) {
                                    val items = listOf("Cut", "Paste before", "Delete", "Move To")
                                    val active = remember { mutableStateOf(false) }
                                    DropdownMenu(expanded = true,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier.background(color = if (active.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.background)
                                            .pointerMoveFilter(onEnter = {
                                                active.value = true
                                                false
                                            }, onExit = {
                                                active.value = false
                                                false
                                            })
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
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = listState
                        )
                    )
                }

            }
        }
    }
}


private fun enableAll() {
    if (areYouSure()) {
        for (item in myList) {
            item.enabled = "1"
        }
    }
}

private fun clearSelection() {
    selectedItems.clear()
}

private var fileToSave: Path? = null
private fun openNewFile() {
    myList.clear()
    clearSelection()
    val fileOpened = EditCSV().openFile()
    if (fileOpened != null) {
        myList.swapList(fileOpened.first)
        nameOfOpenedFile.value = fileOpened.second.name
        fileToSave = fileOpened.second.toPath()
    }
    recalculateIds()
}

private fun saveAsFile() {
    if (fileToSave != null) {
        EditCSV().saveAs(fileToSave!!, myList.toList())
    }
}

private fun recalculateIds() {
    for ((id, item) in myList.withIndex()) {
        item.id = (id + 1).toString()
        if (item.name == END_SYSTEMS) endSystemsRow = item.id
    }
}

private fun cutSelected() {
    myClipBoard.clear()//clear list initially (redundant because we clear after each paste/move)
    if (thereIsAtLeastOneRowSelected()) {
        performCut()
        clearSelection()
        recalculateIds()
    }
}

private fun thereIsAtLeastOneRowSelected(): Boolean {
    val selection = singleSelection()
    return if (selection == -1) {
        //nothing selected
        showDialogWithMessage("Choose at least one row first to perform action.")
        false
    } else {
        true
    }
}

private fun performCut() {
    val removeList = mutableListOf<CSVUnit>()
    for (item in selectedItems) {
        if (item.value) {
            val selectedRow = myList[item.key]
            myClipBoard.add(selectedRow)
            removeList.add(selectedRow)
        }
    }
    myList.removeAll(removeList)
    //when adding rows it is done in parallel so the entries get mixed up, so it needs to be sorted before we paste
    myClipBoard.sortBy { it.id.toInt() }
}

private fun moveSelected() {
    if (thereIsAtLeastOneRowSelected()) {
        val index = chooseMoveIndexDialog(endSystemsRow)
        //if dismissed we do nothing
        //else we do the cut -> paste to specific index
        if (index != -1) {
            //we save the row that was targeted, and later we use this! (and not the index since the indices will change after the cut)
            val itemToPasteBefore = myList[index]
            performCut()
            clearSelection()
            val indexOfItemToPasteBefore = myList.indexOf(itemToPasteBefore)
            pasteNewList(indexOfItemToPasteBefore, myClipBoard)
            recalculateIds()
            myClipBoard.clear()
        }
    }
}

private fun pasteSelected() {
    val indexTriggered = singleSelection()
    //check if single selection is made
    if (indexTriggered > -1) {
        //check if we have cut data before
        if (myClipBoard.isEmpty()) {
            //if empty that means that we want to paste from excel (or other source)
            val dataFromSystemClipboard = createCSVDataFromExcel()
            if (dataFromSystemClipboard != null) {
                pasteNewList(indexTriggered, dataFromSystemClipboard)
            }
        } else {
            //we have cut/move data from the program
            pasteNewList(indexTriggered, myClipBoard)
            myClipBoard.clear()
        }
        clearSelection()
        recalculateIds()
    } else {
        showDialogWithMessage("Choose only one row first to perform action.")
    }
}

private fun deleteSelected() {
    if (thereIsAtLeastOneRowSelected()) {
        val removeList = mutableListOf<CSVUnit>()
        for (item in selectedItems) {
            if (item.value) {
                val selectedRow = myList[item.key]
                removeList.add(selectedRow)
            }
        }
        //join selected items in a string
        val namesList = removeList.map { it.name }
        val namesListString = namesList.joinToString("\n")
        if(deleteSystems(namesListString)){
            myList.removeAll(removeList)
            clearSelection()
            recalculateIds()
        }
    }
}

private fun singleSelection(): Int {
    var firstEntry = true
    var index = -1 //this is for no selection at all
    for (item in selectedItems.entries) {
        if (item.value) {
            if (firstEntry) {
                //single selection, return the index of the selected item
                firstEntry = false
                index = item.key
            } else {
                index = -2 //multiple selections
                break
            }
        }
    }
    return index //single selection
}

private fun pasteNewList(index: Int, data: List<CSVUnit>) {
    myList.swapList(
        myList.subList(0, index) + data + myList.subList(index, myList.size)
    )
}

private fun getClipBoard(): String {
    try {
        return Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor).toString()
    } catch (e: HeadlessException) {
        showDialogWithMessage("HeadlessException: $e")
    } catch (e: UnsupportedFlavorException) {
        showDialogWithMessage("UnsupportedFlavorException: $e")
    } catch (e: IOException) {
        showDialogWithMessage("IOException: $e")
    }
    return ""
}

private fun createCSVDataFromExcel(): List<CSVUnit>? {
    val clipboardData = getClipBoard()
    //return list
    val returnList = mutableListOf<CSVUnit>()
    if (clipboardData.isEmpty()) {
        //show alert
        return null
    } else {
        val delimiter1 = "\t" //delimiter for changing cell
        val delimiter2 = "\n" //delimiter for changing row
        val splitData = clipboardData.substringBeforeLast("") //drop the last space detected
            .replace(".", ",") //make all decimals to be displayed using commas and not dots
            .split(delimiter1, delimiter2) //use the delimiters

        //we split the data we got for every 3 values (name, latitude, longitude)
        for (i in splitData.indices step 3) {
            returnList.add(
                CSVUnit(
                    id = "0",
                    name = splitData[i],
                    enabled = "1",
                    latitude = splitData[i + 1].ifEmpty { "0" },
                    longitude = splitData[i + 2].ifEmpty { "0" },
                    altitude = "0",
                    antennaHeight = "0"
                )
            )
        }
    }
    return returnList
}



















