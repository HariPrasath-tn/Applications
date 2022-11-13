package com.rio.worldweather.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stared_locations")
data class DatabaseLocation(
    @PrimaryKey
    val cityName:String,
    val lat:String,
    val lon:String
)
