package ui

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import model.CSVUnit

class EditCSV {

    fun openFile():List<CSVUnit> {

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
                        id = row["Unit ID"]!!,
                        name = row["Unit name"]!!,
                        enabled = row["Enabled"]!!,
                        latitude = row["Latitude"]!!,
                        longitude = row["Longitude"]!!,
                        altitude = row["Elevation"]!!,
                        antennaHeight = ""
                    )
                )
                //println(row)
            }
        }
        //println(data)
        return data
    }


    private fun clearTable(){}

    fun save() {}

    fun saveAs() {}
}