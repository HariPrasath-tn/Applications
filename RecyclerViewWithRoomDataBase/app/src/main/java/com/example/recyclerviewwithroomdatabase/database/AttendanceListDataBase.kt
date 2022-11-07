package com.example.recyclerviewwithroomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[AttendanceList::class], version=1, exportSchema = false)
abstract class AttendanceListDataBase: RoomDatabase() {
    abstract val attendanceListDAO: AttendanceListDAO

    companion object{
        @Volatile
        private var INSTANCE: AttendanceListDataBase? = null

        fun getInstance(context:Context): AttendanceListDataBase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AttendanceListDataBase::class.java,
                        "Employee_Data"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}