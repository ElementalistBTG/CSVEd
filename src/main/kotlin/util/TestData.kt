package util

import model.CSVUnit

val testData = listOf<CSVUnit>(
    CSVUnit(
        id = "1",
        name = "2ake",
        latitude = "38.0235",
        longitude = "25.033656",
        altitude = "1000.0",
        antennaHeight = "15",
        enabled = "1"
    ),
    CSVUnit(
        id = "2",
        name = "3ake",
        latitude = "38.0235",
        longitude = "25.033656",
        altitude = "1050.0",
        antennaHeight = "15",
        enabled = "0"
    ),
    CSVUnit(
        id = "3",
        name = "4ake",
        latitude = "38.0235",
        longitude = "25.033656",
        altitude = "1050.0",
        antennaHeight = "15",
        enabled = "0"
    )
)

val emptyCSV = CSVUnit(
    id = "",
    name = "",
    latitude = "",
    longitude = "",
    altitude = "",
    antennaHeight = "",
    enabled = "0"
)