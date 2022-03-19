package model

data class CSVUnit(
    var id : String,
    val name : String,
    var enabled : String,
    val latitude : String,
    val longitude : String,
    val altitude : String,
    var antennaHeight : String
)
