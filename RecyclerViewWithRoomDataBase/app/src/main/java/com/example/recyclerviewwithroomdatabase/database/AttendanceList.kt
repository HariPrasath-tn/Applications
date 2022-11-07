package com.example.recyclerviewwithroomdatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employee_Attendance_List")
data class AttendanceList(
    @PrimaryKey(autoGenerate = true)
    var userID:Long=0,
    @ColumnInfo(name="work_type")
    var workType:String?,
    @ColumnInfo(name="check_in_time")
    var workStartTime:Long = System.currentTimeMillis(),
    @ColumnInfo(name="check_out_time")
    var workStopTime:Long = workStartTime,
    @ColumnInfo(name="attendance_status")
    var checkInStatus:Boolean = false
)
