package ui

import LAST_USED_FOLDER
import androidx.compose.ui.awt.ComposeWindow
import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import model.CSVUnit
import java.io.File
import java.nio.file.Path
import java.util.*
import java.util.prefs.Preferences
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileFilter


class EditCSV {

    private val myReader = csvReader {
        charset = "ISO-8859-7"
        delimiter = ';'
        skipMissMatchedRow = true
    }
    private val myWriter = csvWriter {
        charset = "ISO-8859-7"
        delimiter = ';'
        quote {
            mode = WriteQuoteMode.ALL
        }
    }
    private val csvTypeFilter = object : FileFilter() {

        override fun accept(f: File): Boolean {
            return if (f.isDirectory) {
                true
            } else {
                val filename = f.name.lowercase(Locale.getDefault())
                filename.endsWith(".csv")
            }
        }

        override fun getDescription(): String {
            return "*.csv"
        }
    }

    private var netList = mutableListOf<List<String>>()
    private var netDataList = mutableListOf<List<String>>()
    private var unitList = mutableListOf<List<String>>()

    fun openFile(): Pair<List<CSVUnit>, File>? {
        val prefs = Preferences.userRoot().node(this.javaClass.name)
        val fileChooser = JFileChooser(prefs[LAST_USED_FOLDER, File(".").absolutePath])
        fileChooser.fileFilter = csvTypeFilter
        val result = fileChooser.showOpenDialog(ComposeWindow())
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            prefs.put(LAST_USED_FOLDER, fileChooser.selectedFile.parent)
            val selectedFile = fileChooser.selectedFile
            val data = mutableListOf<CSVUnit>()
            val fileOpened = selectedFile.toString()
            val pathLength = fileOpened.length
            val unitPath = fileOpened.substring(0, pathLength - 4) + "_Unit.csv"
            val netPath = fileOpened.substring(0, pathLength - 4) + "_NetData.csv"

            //Read Unit file
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

            //Read NetData file
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
                val dataLine = readNext()//this line has the antennas' heights
                for (i in data.indices) {
                    data[i].antennaHeight = dataLine?.get(i + 1) ?: ""
                }
            }

            if (data.last().name != "LAST ENTRY") {
                //In the end we add one more item as a last line
                data.add(
                    CSVUnit(
                        id = "2000",
                        name = "LAST ENTRY",
                        enabled = "0",
                        latitude = "0",
                        longitude = "0",
                        altitude = "0",
                        antennaHeight = "0"
                    )
                )
            }

            return (data to selectedFile!!)
        } else {
            return null
        }

    }

    fun saveAs(path: Path, dataList: List<CSVUnit>) {
        val filePath = path.toFile().path
        val prefs = Preferences.userRoot().node(this.javaClass.name)
        val fileChooser = JFileChooser(prefs[LAST_USED_FOLDER, File(".").absolutePath])
        fileChooser.apply {
            fileFilter = csvTypeFilter
            dialogTitle = "Specify name of new file"
            selectedFile = File(filePath)
        }
        val result = fileChooser.showSaveDialog(ComposeWindow())
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            val selectedFile = fileChooser.selectedFile
            prefs.put(LAST_USED_FOLDER, fileChooser.selectedFile.parent)

            saveNetCSV(filePath, selectedFile)

            saveNetDataCSV(
                filePath.substring(0, filePath.length - 4) + "_NetData.csv",
                selectedFile.absolutePath.substring(0, selectedFile.absolutePath.length - 4) + "_NetData.csv",
                dataList
            )

            saveUnitCSV(
                selectedFile.absolutePath.substring(0, selectedFile.absolutePath.length - 4) + "_Unit.csv",
                dataList
            )
        }

    }

    private fun saveNetCSV(filePath: String, selectedFile: File) {
        myReader.open(filePath) {
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(listOf(";")) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }
            readNext()?.let { netList.add(it) }//12 Network

            val nextLine = readNext()?.first()
            val lastOccurenceUnit = nextLine?.lastIndexOf("\\")
            if (nextLine != null && lastOccurenceUnit != null) {
                netList.add(
                    listOf(
                        nextLine.substring(0, lastOccurenceUnit + 1) + selectedFile.nameWithoutExtension + "_Unit.csv"
                    )
                )//Unit
            }
            readNext()?.let { netList.add(it) }//System

            val nextLine2 = readNext()?.first()
            val lastOccurenceUnit2 = nextLine2?.lastIndexOf("\\")
            if (nextLine2 != null && lastOccurenceUnit2 != null) {
                netList.add(
                    listOf(
                        nextLine2.substring(
                            0,
                            lastOccurenceUnit2 + 1
                        ) + selectedFile.nameWithoutExtension + "_NetData.csv"
                    )
                )//NetData
            }
        }

        myWriter.writeAll(netList, selectedFile)
    }

    private fun saveNetDataCSV(filePath: String, writeToFilePath: String, dataList: List<CSVUnit>) {

        val antennaHeights = mutableListOf<String>("1")
        dataList.forEach { item ->
            antennaHeights.add(item.antennaHeight)
        }


        try {
            myReader.open(filePath) {
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }//9th line
                val line = readNext()
                for (i in dataList.size..line!!.size-2) {
                    // we add 0s to the rest of the line
                    antennaHeights.add("0")
                }
                netDataList.add(antennaHeights)//adding the antennas' heights
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
                readNext()?.let { netDataList.add(it) }
            }

            myWriter.writeAll(netDataList, File(writeToFilePath))
        } catch (th: Throwable) {
            JOptionPane.showMessageDialog(null, "Error caught: $th");
        }

    }

    private fun saveUnitCSV(writeToFilePath: String, dataList: List<CSVUnit>) {
        unitList.add(listOf("Radio Mobile"))
        unitList.add(listOf("$" + "Unit"))
        unitList.add(
            listOf(
                "Unit ID",
                "Unit name",
                "Enabled",
                "Latitude",
                "Longitude",
                "Elevation",
                "Icon",
                "Forecolor",
                "Style",
                "Backcolor",
                "Text",
                "Locked"
            )
        )
        for (item in dataList) {
            var lat = item.latitude
            var lon = item.longitude
            var enabled = "1"
            if (item.latitude.isNotEmpty()) {
                if (item.latitude[0] == '0') {
                    lat = "0.11"
                    lon = "0.11"
                    enabled = "0"
                }
            }

            unitList.add(
                listOf(
                    item.id,
                    item.name,
                    enabled,
                    lat,
                    lon,
                    item.altitude,
                    "27",//icon
                    "FFFFFF",//forecolor
                    "0",//style
                    "0",//backcolor
                    "",//text
                    "0"//locked
                )
            )
        }
        try {
            myWriter.writeAll(unitList, File(writeToFilePath))
        } catch (th: Throwable) {
            JOptionPane.showMessageDialog(null, "Error caught: $th");
        }

    }
}

