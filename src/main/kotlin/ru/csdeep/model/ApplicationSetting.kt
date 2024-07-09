package ru.csdeep.model

/**
 * DataBaseSetting data object.
 */
data class DataBaseSetting(
    val serveName: String,
    val databaseName: String
)

/**
 * AppSettings data object.
 */
data class ApplicationSetting(
    val dataBaseSetting: DataBaseSetting
)
