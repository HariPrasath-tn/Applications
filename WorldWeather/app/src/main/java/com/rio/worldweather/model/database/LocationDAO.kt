package com.rio.worldweather.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * [LocationDAO] is the interface that defines the database operations
 */
@Dao
interface LocationDAO {
    /**
     * [getAll] is the method that fetches all data from the database
     * @return LiveData<List<DatabaseLocation>>
     */
    @Query("select * from stared_locations")
    fun getAll():LiveData<List<DatabaseLocation>>

    /**
     * [insert] is the method that handle database Insertion operation
     * @param databaseLocation of type [DatabaseLocation]
     * @return unit
     */
    @Insert // here i am not handling conflict exceptions
    fun insert(databaseLocation: DatabaseLocation)

    /**
     * [getCities] is the method that get cities list from the database
     * @return List<Strings>
     */
    @Query("select cityName from stared_locations")
    fun getCities():List<String>

    /**
     * [delete] is the method that handle database deletion operation
     * @param city of type String
     */
    @Query("DELETE FROM stared_locations WHERE cityName=:city")
    fun delete(city:String)
}