package com.rio.worldweather.model.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [DatabaseLocation::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    // variable that stores the instance of the interface LocationDAO
    abstract val locationDAO:LocationDAO

    companion object{
        // volatile variable in order to make the changes visible to all threads
        @Volatile
        var INSTANCE:Database? = null

        fun getInstance(context: Context):Database{
            // making the block of statements synchronized
            // to prevent multiple thread access simultaneously
            synchronized(this){
                // using kotlin smart cast for control flow statement
                var instance = INSTANCE
                // if instance not yet created the creates else simply returns the instance available
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "weather_database"
                    ).fallbackToDestructiveMigration().build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}