package ui

import androidx.compose.ui.awt.ComposeWindow
import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import model.CSVUnit
import start_directory
import java.awt.FileDialog
import java.io.File
import java.io.FilenameFilter
import java.nio.file.Path
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import kotlin.io.path.name


class EditCSV {

    private val myReader = csvReader {
        charset = "ISO-8859-7"
        delimiter = ';'
        skipMissMatchedRow = true
    }
    private val myWriter = csvWriter {
        charset = "ISO-8859-7"
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
    private var netDataList = mutableListOf<String>()
    private var unitList = mutableListOf<String>()


    fun openFile(): Pair<List<CSVUnit>, File>? {

        val fileChooser = JFileChooser(start_directory)
        fileChooser.fileFilter = csvTypeFilter
        val result = fileChooser.showOpenDialog(ComposeWindow())
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            val selectedFile = fileChooser.selectedFile
            val data = mutableListOf<CSVUnit>()
            val fileOpened = selectedFile.toString()
            val pathLength = fileOpened.length
            val unitPath = fileOpened.substring(0, pathLength - 4) + "_Unit.csv"
            val netPath = fileOpened.substring(0, pathLength - 4) + "_NetData.csv"

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
                val dataLine = readNext()
                for (i in data.indices) {
                    data[i].antennaHeight = dataLine?.get(i + 1) ?: ""
                }
            }

            return (data to selectedFile!!)
        } else {
            return null
        }

    }

    fun saveAs(path: Path) {
        val filePath = path.toFile().path

        val fileChooser = JFileChooser(start_directory)
        fileChooser.apply {
            fileFilter = csvTypeFilter
            dialogTitle = "Specify name of new file"
        }
        val result = fileChooser.showSaveDialog(ComposeWindow())
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            val selectedFile = fileChooser.selectedFile

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
                readNext()?.let { netList.add(it) }
                readNext()?.let { netList.add(it) }
                readNext()?.let { netList.add(it) }//14 Network

                val nextLine = readNext()?.first()
                val lastOccurenceUnit = nextLine?.lastIndexOf("\\")
                if (nextLine != null && lastOccurenceUnit != null) {
                    netList.add(
                        listOf(
                            nextLine.substring(
                                0,
                                lastOccurenceUnit + 1
                            ) + selectedFile.nameWithoutExtension + "_Unit.csv"
                        )
                    )//15 Unit
                }
                readNext()?.let { netList.add(it) }//16 System

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
                    )//17 NetData
                }
            }

            myWriter.writeAll(netList, selectedFile)

        }

    }
}