package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.AwtWindow
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import model.CSVUnit
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.nio.file.Paths

class EditCSV {


    private fun openFileDialog(window: ComposeWindow, title: String): File {
        return FileDialog(window, title, FileDialog.LOAD).apply {
            isMultipleMode = false
            file = "*.csv"
            isVisible = true
        }.files[0]
    }

    private fun saveFileDialog(window: ComposeWindow, title: String, fileName : String) {
        FileDialog(window, title, FileDialog.SAVE).apply {
            isMultipleMode = false
            file = fileName
            isVisible = true
        }
    }

    fun openFile(): Pair<List<CSVUnit>,File> {

        val file = openFileDialog(ComposeWindow(), "Choose a CSV file to open")
        val fileString = file.toString()
        val pathLength = fileString.length
        val unitPath = fileString.substring(0, pathLength - 4) + "_Unit.csv"
        val netPath = fileString.substring(0, pathLength - 4) + "_NetData.csv"

        val myReader = csvReader {
            delimiter = ';'
            skipMissMatchedRow = true
        }

        val data = mutableListOf<CSVUnit>()

        myReader.open(unitPath) {
            readNext()
            readNext()
            readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
                data.add(
                    CSVUnit(
                        id = row["Unit ID"]!!,
                        name = row["Unit name"]!!,
                        enabled = row["Enabled"]!!,
                        latitude = row["Latitude"]!!,
                        longitude = row["Longitude"]!!,
                        altitude = row["Elevation"]!!,
                        antennaHeight = ""
                    )
                )
            }
        }

        myReader.open(netPath) {
            //skip first 9 lines
            readNext()
            readNext()
            readNext()
            readNext()
            readNext()
            readNext()
            readNext()
            readNext()
            readNext()
            val line = readNext()
            for (i in data.indices) {
                data[i].antennaHeight = line?.get(i + 1) ?: ""
            }
        }

        //println(data)
        return (data to file)
    }

    fun save() {}

    fun saveAs(file: File) {
        saveFileDialog(ComposeWindow(), "Save as ?",file.name)
        val fileString = file.toString()
        val pathLength = fileString.length
        val unitPath = fileString.substring(0, pathLength - 4) + "_Unit.csv"
        val netPath = fileString.substring(0, pathLength - 4) + "_NetData.csv"
    }
}