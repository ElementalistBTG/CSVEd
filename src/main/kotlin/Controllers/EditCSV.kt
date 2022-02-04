package Controllers

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import model.CSVUnit
import java.io.File
import java.awt.FileDialog

class EditCSV {

    fun openFile() {

        //val file = FileDialog()

        val myReader = csvReader {
            delimiter = ';'
            skipMissMatchedRow = true
        }

        val data = mutableListOf<CSVUnit>()

        myReader.open("C://Users/Dinos/Desktop/net_ed/new_net3_Unit.csv") {
            readNext()
            readNext()
            readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
                data.add(
                    CSVUnit(
                        id = row["Unit ID"],
                        name = row["Unit name"],
                        enabled = row["Enabled"].equals("1"),
                        latitude = row["Latitude"],
                        longitude = row["Longitude"],
                        elevation = row["Elevation"],
                        antennaHeight = ""
                    )
                )
                //println(row)
            }
        }
        //println(data)
        populateTable(data)

    }

    private fun populateTable(data : List<CSVUnit>){

    }

    private fun clearTable(){}

    fun save() {}

    fun saveAs() {}
}