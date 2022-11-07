package com.example.recyclerviewwithroomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


/*
 * DAO Interface to access the database
 *
 * 1.@insert -> input: object of AttendanceList
 *              functionality: insert a new row of data according the dataClass's(AttendanceList) data
 * 2.@update -> input: object of the AttendanceList of Already existing data
 *              functionality: update values already inserted into the database table based on the primary key
 *
 * 3.delete -> input: id/Primary key reference of that data
 *             functionality: delete that particular record if available
 *
 *4.@Query -> in case of any non pre-defined query we can use this annotation pass any query as value
 *              eg : @Query("SELECT * FROM table_name)
 *              input : if any needed & if any passed we can mention it with ':' symbol
 *              eg : (if id passed) @Query("SELECT * FROM table_name WHERE id=:id)
 *              functionality: execute the query as the normal sql query
 */
@Dao
interface AttendanceListDAO {
    @Insert
    fun insert(attendance: AttendanceList)

    @Update
    fun update(attendance: AttendanceList)

    @Query("SELECT * FROM Employee_Attendance_List")
    fun getList():List<AttendanceList>

    @Query("SELECT * FROM Employee_Attendance_List ORDER BY userID DESC LIMIT 1")
    fun getLastWork(): AttendanceList
}